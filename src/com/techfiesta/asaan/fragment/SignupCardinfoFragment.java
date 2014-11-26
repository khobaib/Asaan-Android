package com.techfiesta.asaan.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.NothingSelectedSpinnerAdapter;
import com.techfiesta.asaan.communication.Communicator;

public class SignupCardinfoFragment extends Fragment {

	public static final String PUBLISHABLE_KEY = "pk_test_hlpADPUOWaxn6uN0aATgLivW";
	EditText CardNumber;
	EditText CVC;
	Spinner Month;
	Spinner Year;
	Button SaveCard;
	EditText DefaultTip;
	Button SaveTip;

	ImageView NEXT2;

	int expMonth, expYear;
	String cardNumber;
	String cardCVC;
	Communicator comm;

	public SignupCardinfoFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.sign_up_frag_2, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Activity mActivity = getActivity();
		CardNumber = (EditText) mActivity.findViewById(R.id.etCardNumber);
		CVC = (EditText) mActivity.findViewById(R.id.etCVC);
		Year = (Spinner) mActivity.findViewById(R.id.spYear);
		Month = (Spinner) mActivity.findViewById(R.id.spMonth);
		SaveCard = (Button) mActivity.findViewById(R.id.btnSaveCardInfoSU);
		DefaultTip = (EditText) mActivity.findViewById(R.id.etDefaultTipSignUp);
		SaveTip = (Button) mActivity.findViewById(R.id.btnSaveTipInfoSu);
		comm = (Communicator) getActivity();

		updateMonth_YearSpinners();

		Month.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position > 1) {
					expMonth = Integer.parseInt(parent.getSelectedItem().toString());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		Year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position > 1) {
					expYear = Integer.parseInt(parent.getSelectedItem().toString());
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		SaveCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cardNumber = CardNumber.getText().toString();
				cardCVC = CVC.getText().toString();
				saveCreditCard();

			}
		});

		SaveTip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tip = DefaultTip.getText().toString();
				System.out.println(tip);
				// save it to respective parse class when this field is added
			}
		});
		NEXT2 = (ImageView) mActivity.findViewById(R.id.ivForward);
		NEXT2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				comm.respond(2);

			}
		});

	}

	public void saveCreditCard() {

		Card card = new Card(cardNumber, expMonth, expYear, cardCVC);

		boolean validation = card.validateCard();
		if (validation) {
			// startProgress();
			new Stripe().createToken(card, PUBLISHABLE_KEY, new TokenCallback() {
				public void onSuccess(Token token) {

					System.out.println("" + token.getId());
					saveTokenInGAE(token);
					//saveToken(token);
				}

				public void onError(Exception error) {
					handleError(error.getLocalizedMessage());
					// finishProgress();
				}
			});
		} else if (!card.validateNumber()) {
			handleError("The card number that you entered is invalid");
		} else if (!card.validateExpiryDate()) {
			handleError("The expiration date that you entered is invalid");
		} else if (!card.validateCVC()) {
			handleError("The CVC code that you entered is invalid");
		} else {
			handleError("The card details that you entered are invalid");
		}
	}

	private void saveToken(Token token) {
		System.out.println(token.getCard().getFingerprint());
		Card tokCard = token.getCard();
		if (tokCard != null) {
			ParseObject strCard = new ParseObject("UserStripeCard");
			// strCard.put("address_city", tokCard.getAddressCity());
			// strCard.put("address_country", tokCard.getAddressCountry());
			// strCard.put("address_line1", tokCard.getAddressLine1());
			// strCard.put("address_line2", tokCard.getAddressLine2());
			// strCard.put("address_state", tokCard.getAddressState());
			// strCard.put("address_zip", tokCard.getAddressZip());
			// strCard.put("brand", tokCard.getType());
			strCard.put("country", tokCard.getCountry());
			strCard.put("exp_month", tokCard.getExpMonth());
			strCard.put("exp_year", tokCard.getExpYear());
			strCard.put("fingerprint", tokCard.getFingerprint());
			// strCard.put("funding", null);
			strCard.put("last4", tokCard.getLast4());
			// strCard.put("name", tokCard.getName());
			// strCard.put("stripeCustomer", tokCard.get);
			// strCard.put("cvc_check", tokCard.getCVC());
			if (ParseUser.getCurrentUser() != null) {
				strCard.put("user", ParseUser.getCurrentUser());
			}

			strCard.saveInBackground();
		}
	}
private void  saveTokenInGAE(Token token)
{
	Card card=token.getCard();
	JsonObject cardObj=new JsonObject();
	cardObj.addProperty("accessToken", "FROM PARSE USER");
	cardObj.addProperty("address",card.getAddressLine1());	
	cardObj.addProperty("brand",card.getType());
    cardObj.addProperty("city", card.getAddressCity());
     cardObj.addProperty("country", card.getCountry());
     
	
	
}
	private void updateMonth_YearSpinners() {
		ArrayList<String> monthList = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			monthList.add("" + i);
		}
		ArrayAdapter<String> sMonthAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview,
				monthList);
		Month.setAdapter(new NothingSelectedSpinnerAdapter(sMonthAdapter, R.layout.row_spinner_nothing_selected,
				getActivity()));
		sMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayList<String> yearList = new ArrayList<String>();
		int thisYear = 2014; // here we will take current time year and replace
								// with it
		for (int i = 1; i <= 30; i++) {

			yearList.add("" + thisYear);
			thisYear++;
		}
		ArrayAdapter<String> sYearAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_textview, yearList);
		Year.setAdapter(new NothingSelectedSpinnerAdapter(sYearAdapter, R.layout.row_spinner_nothing_selected,
				getActivity()));
		sYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	}

	private void handleError(String error) {
		DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
		fragment.show(getChildFragmentManager(), "error");
	}

}
