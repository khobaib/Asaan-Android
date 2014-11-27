package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.asaan.server.com.asaan.server.endpoint.userendpoint.Userendpoint.SaveUserCard;
import com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.NothingSelectedSpinnerAdapter;
import com.techfiesta.asaan.fragment.ErrorDialogFragment;

public class PaymentInfoActivity extends Activity {

	UserCard userCard;
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
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_frag_2);

		CardNumber = (EditText) findViewById(R.id.etCardNumber);
		CVC = (EditText) findViewById(R.id.etCVC);
		Year = (Spinner) findViewById(R.id.spYear);
		Month = (Spinner) findViewById(R.id.spMonth);
		SaveCard = (Button) findViewById(R.id.btnSaveCardInfoSU);
		DefaultTip = (EditText) findViewById(R.id.etDefaultTipSignUp);
		SaveTip = (Button) findViewById(R.id.btnSaveTipInfoSu);

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

				if (ParseUser.getCurrentUser() == null) {
					Log.e("MSG", "NOT SIGNED UP");
					ParseUser user = new ParseUser();
					user.setUsername("Tanzeer");
					user.setEmail("hossaintanzeer@gmail.com");
					user.setPassword("123");
					user.put("phone", "01674740627");
					user.signUpInBackground(new SignUpCallback() {

						@Override
						public void done(ParseException e) {

							if (e == null) {
								cardNumber = CardNumber.getText().toString();
								cardCVC = CVC.getText().toString();
								saveCreditCard();
							}
						}
					});
				} else {
					Log.e("MSG", "SIGNED UP" + ParseUser.getCurrentUser().getString("authToken"));
					cardNumber = CardNumber.getText().toString();
					cardCVC = CVC.getText().toString();
					saveCreditCard();
				}
				// saveCreditCard();

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
					// saveToken(token);
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

	private void saveTokenInGAE(Token token) {
		Card card = token.getCard();
		/*
		 * JsonObject cardObj=new JsonObject();
		 * cardObj.addProperty("accessToken", "FROM PARSE USER");
		 * cardObj.addProperty("address",card.getAddressLine1());
		 * cardObj.addProperty("brand",card.getType());
		 * cardObj.addProperty("city", card.getAddressCity());
		 * cardObj.addProperty("country", card.getCountry()); //
		 * cardObj.addProperty("createdDate", ); //
		 * cardObj.addProperty("currency", card.get);
		 * //cardObj.addProperty("default",); cardObj.addProperty("exp_month",
		 * card.getExpMonth()); cardObj.addProperty("exp_year",
		 * card.getExpYear()); //cardObj.addProperty("fundingType", );
		 * cardObj.addProperty("id", card.getFingerprint());
		 * cardObj.addProperty("last4", card.getLast4());
		 * //cardObj.addProperty("modifiedDate",); cardObj.addProperty("name",
		 * card.getName()); //cardObj.addProperty("provider",); //
		 * cardObj.addProperty("providerCustomerId",); //
		 * cardObj.addProperty("refreshToken",); cardObj.addProperty("state",
		 * card.getAddressState()); // cardObj.addProperty("userId",);
		 * cardObj.addProperty("zip", card.getAddressZip());
		 */
		userCard = new UserCard();
		userCard.setAccessToken(token.getId());
		userCard.setAddress(card.getAddressLine1());
		userCard.setBrand(card.getType());
		userCard.setCity(card.getAddressCity());
		userCard.setCountry(card.getAddressCountry());
		userCard.setExpMonth(card.getExpMonth());
		userCard.setExpYear(card.getExpYear());
		// userCard.setId(""+card.getFingerprint());
		userCard.setLast4(card.getLast4());
		userCard.setName(card.getName());
		userCard.setState(card.getAddressState());
		userCard.setZip(card.getAddressZip());

		new PostCardInfo().execute();

	}

	private void updateMonth_YearSpinners() {
		ArrayList<String> monthList = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			monthList.add("" + i);
		}
		ArrayAdapter<String> sMonthAdapter = new ArrayAdapter<String>(PaymentInfoActivity.this,
				R.layout.spinner_textview, monthList);
		Month.setAdapter(new NothingSelectedSpinnerAdapter(sMonthAdapter, R.layout.row_spinner_nothing_selected,
				PaymentInfoActivity.this));
		sMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayList<String> yearList = new ArrayList<String>();
		int thisYear = 2014; // here we will take current time year and replace
								// with it
		for (int i = 1; i <= 30; i++) {

			yearList.add("" + thisYear);
			thisYear++;
		}
		ArrayAdapter<String> sYearAdapter = new ArrayAdapter<String>(PaymentInfoActivity.this,
				R.layout.spinner_textview, yearList);
		Year.setAdapter(new NothingSelectedSpinnerAdapter(sYearAdapter, R.layout.row_spinner_nothing_selected,
				PaymentInfoActivity.this));
		sYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	}

	private void handleError(String error) {
		DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
		// fragment.show(getFragmentManager(), "error");
	}

	private class PostCardInfo extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			SaveUserCard saveUserCard;
			try {
				saveUserCard = AsaanSplashActivity.mUserendpoint.saveUserCard(userCard);
				HttpHeaders httpHeaders = saveUserCard.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				saveUserCard.execute();
				Log.e("MSG", "Posting");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

	}

}
