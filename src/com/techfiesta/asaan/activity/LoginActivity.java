package com.techfiesta.asaan.activity;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.asaan.server.com.asaan.server.endpoint.userendpoint.Userendpoint.GetUserCards;
import com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCardCollection;
import com.google.api.client.http.HttpHeaders;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

public class LoginActivity extends Activity {

	EditText userEmail;
	EditText passWord;
	Button Login;
	ParseUser currentUser;
	private ProgressDialog pDialog;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();
		setContentView(R.layout.activity_login);

		userEmail = (EditText) findViewById(R.id.et_email);
		passWord = (EditText) findViewById(R.id.et_pass);
		Login = (Button) findViewById(R.id.b_login);

		pDialog = new ProgressDialog(LoginActivity.this);
		pDialog.setMessage("Logging in...");

		Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginUser();

			}
		});
	}

	private void launchActivity(Class<?> launchingClass) {
		Intent intent = new Intent(this, launchingClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void LoginUser() {
		String name = userEmail.getText().toString();
		String password = passWord.getText().toString();
		pDialog.show();
		ParseUser.logInInBackground(name, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (pDialog.isShowing())
					pDialog.dismiss();
				if (user != null) {

					currentUser = ParseUser.getCurrentUser();
					Log.d(">>", "login success" + currentUser.getEmail());				
					new GetUserCardsFromServerInLoginActivity().execute();
				} else {
					Log.d("login failed", e.getMessage());				
				}

			}
		});
	}
	
	private class GetUserCardsFromServerInLoginActivity extends AsyncTask<Void,Void,Void>
	{
		
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			GetUserCards getUserCards;
			try {
				getUserCards=SplashActivity.mUserendpoint.getUserCards();
				HttpHeaders httpHeaders = getUserCards.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				UserCardCollection userCardCollection= getUserCards.execute();
				if(userCardCollection!=null)
				{
					Log.e("Response",userCardCollection.toPrettyString());
					if(userCardCollection.getItems()!= null && userCardCollection.getItems().size()>=0)
					{
						AsaanUtility.defCard=userCardCollection.getItems().get(0);
					}
					else
						AsaanUtility.defCard=null;
				}
				else
					AsaanUtility.defCard=null;
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(ParseUser.getCurrentUser() != null)
			{
				Intent i = new Intent(LoginActivity.this,StoreListActivity.class);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			}
		}
	}
}
