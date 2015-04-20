package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.asaan.server.com.asaan.server.endpoint.userendpoint.Userendpoint.SaveUserCard;
import com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.DefaultTipsSpinnerAdapter;
import com.techfiesta.asaan.fragment.ErrorDialogFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class PaymentInfoActivity extends BaseActivity {

	UserCard userCard;
	//public static final String PUBLISHABLE_KEY = "pk_test_hlpADPUOWaxn6uN0aATgLivW";
	public static final String PUBLISHABLE_KEY = "pk_test_4Ns4Xp8GtdBUxhiUKDi4RMTa";
	EditText CardNumber;
	EditText CVC;
	EditText Zip;
	private Button btnSave;
	private Button btnSkip;
	EditText etMonth;
	// Button SaveTip;
	Spinner defaultTipSpinner;
	EditText etYear;

	ImageView NEXT2;

	int expMonth, expYear;
	String cardNumber;
	String cardCVC;
	String zip;
//	int month;
//	int year;
	int tips;
	private ProgressDialog pDialog;
	private int RESPONSE_CODE=2;
	

	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_info);

		CardNumber = (EditText) findViewById(R.id.et_card_number);
		CVC = (EditText) findViewById(R.id.et_cvc);
		Zip = (EditText) findViewById(R.id.et_zip);
		etMonth = (EditText) findViewById(R.id.et_month);
		etYear = (EditText) findViewById(R.id.et_year);

		btnSave = (Button) findViewById(R.id.b_save);
		
		pDialog = new ProgressDialog(PaymentInfoActivity.this);
		pDialog.setMessage("Please Wait...");
		
		defaultTipSpinner = (Spinner) findViewById(R.id.s_tip_selector);
		createDefaultTipSpinner();

		// updateMonth_YearSpinners();

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                 pDialog.show();
				if (ParseUser.getCurrentUser() != null) {
					

					Log.e("MSG", "SIGNED UP" + ParseUser.getCurrentUser().getString("authToken"));
					cardNumber = CardNumber.getText().toString();
					cardCVC = CVC.getText().toString();
					zip = Zip.getText().toString();
					expMonth = Integer.parseInt(etMonth.getText().toString());
					expYear = Integer.parseInt(etYear.getText().toString());
					tips = Integer.parseInt(defaultTipSpinner.getSelectedItem().toString());
					saveDefaultTips();
					saveCreditCard();
				} else {
					AsaanUtility.simpleAlert(PaymentInfoActivity.this, "User not logged in.");
				}

			}
		});

	}

	private void saveDefaultTips() {
		ParseUser user = ParseUser.getCurrentUser();
		user.put("tip",""+ tips);
		user.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.e("MSG", "Default Tips Updated");
				}

			}
		});
	}

	private void createDefaultTipSpinner() {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 3; i < 21; i++) {
			list.add(i * 5);
		}
		//ArrayAdapter<Integer> adapter = new ArrayAdapter<>(PaymentInfoActivity.this,
			//	android.R.layout.simple_spinner_dropdown_item, list);
		DefaultTipsSpinnerAdapter adapter=new DefaultTipsSpinnerAdapter(PaymentInfoActivity.this,android.R.layout.simple_spinner_dropdown_item, list);
		defaultTipSpinner.setAdapter(adapter);
		
		
	}

	public void saveCreditCard() {
		
		
		Log.e(">>>>>", "cardnumber = " + cardNumber);
		Log.e(">>>>>", "expMonth = " + expMonth);
		Log.e(">>>>>", "expYear = " + expYear);
		Log.e(">>>>>", "cardCVC = " + cardCVC);
		Card card = new Card(cardNumber, expMonth, expYear, cardCVC);

		boolean validation = card.validateCard();
		Log.e(">>>", "validation = " + validation);
		if (validation) {
			// startProgress();
			new Stripe().createToken(card, PUBLISHABLE_KEY, new TokenCallback() {
				public void onSuccess(Token token) {

					Log.e("MSG",""+ token.getId());
					saveTokenInGAE(token);
					// saveToken(token);
				}

				public void onError(Exception error) {
					Log.e("state", "error");
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
		Log.e("state", "inside GAE");
		
		Card card = token.getCard();
		userCard = new UserCard();
		userCard.setAccessToken(token.getId());
		userCard.setAddress(card.getAddressLine1());
		//userCard.setBrand(card.getType());
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

	private void handleError(String error) {
		DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
		// fragment.show(getFragmentManager(), "error");
	}

	private class PostCardInfo extends AsyncTask<Void, Void, Void> {

		private boolean exceptionStatus=false;
		@Override
		protected Void doInBackground(Void... arg0) {

			SaveUserCard saveUserCard;
			UserCard responseUserCard =null;
			try {
				saveUserCard = SplashActivity.mUserendpoint.saveUserCard(userCard);
				HttpHeaders httpHeaders = saveUserCard.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				responseUserCard = saveUserCard.execute();
				Log.e("MSG", responseUserCard.toPrettyString()+"created_date" + responseUserCard.getCreatedDate() + "mod_date" + responseUserCard.getModifiedDate());
				//pDialog.dismiss();
				if(responseUserCard.getId() != null)
				{
					/*Intent intent=new Intent(PaymentInfoActivity.this,StoreListActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();*/
				}
				else
				{
					Log.e("error", "error in saving card");
					//AsaanUtility.simpleAlert(PaymentInfoActivity.this, "Updating Payment info failed");
				}
			} catch (IOException e1) {
				exceptionStatus=true;
				e1.printStackTrace();
				
			}
			

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pDialog!=null && pDialog.isShowing())
				pDialog.dismiss();
			if(exceptionStatus)
			{
				AsaanUtility.simpleAlert(PaymentInfoActivity.this, "An error occured.");
			}
			else{
				Intent i=getIntent();
				
				int code=i.getIntExtra(Constants.KEY_FROM_ACTIVITY,-1);
				if(code!=-1)
				{
					setResult(RESPONSE_CODE);
					finish();
					overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
				}
				
			}
		}

	}

}
