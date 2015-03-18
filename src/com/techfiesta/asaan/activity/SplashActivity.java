package com.techfiesta.asaan.activity;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint;
import com.asaan.server.com.asaan.server.endpoint.userendpoint.Userendpoint;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.CloudEndpointUtils;

public class SplashActivity extends Activity {

	public static final long STORE_ID = 1L;
	public static Storeendpoint mStoreendpoint;
	public static Userendpoint mUserendpoint;
	Context mContext;
	ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();

		setContentView(R.layout.splash);
		mContext = SplashActivity.this;
		buildStoreEndpoint();
		buildUserEndpoint();
		// deleteCurrentUser();
		trytoLoadMainScreen(mContext);

	}

	private void trytoLoadMainScreen(Context context) {
		if (AsaanUtility.hasInternet(context)) {
			new LoadMainScreen().execute();

		} else {
			internetAvailabilityAlert(context, "Please Check your internet connection");
		}

	}

	// will use this class to do app loading related stuffs in background
	public class LoadMainScreen extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {

			currentUser = ParseUser.getCurrentUser();
			// for testing

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				Intent i = null;
				if (currentUser == null) {
					i = new Intent(SplashActivity.this, LoginChooserActivity.class);
				} else {
					i = new Intent(SplashActivity.this,MenuActivityNew.class);
				}

				startActivity(i);

				// close this activity
				finish();
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}

		}
	}

	private void buildStoreEndpoint() {
		Storeendpoint.Builder storeEndpointBuilder;
		storeEndpointBuilder = new Storeendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest arg0) throws IOException {
						// TODO Auto-generated method stub

					}
				});
		storeEndpointBuilder.setApplicationName("Asaan");
		mStoreendpoint = CloudEndpointUtils.updateBuilder(storeEndpointBuilder).build();
	}

	private void buildUserEndpoint() {
		Userendpoint.Builder userEndpointBuilder;
		userEndpointBuilder = new Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest arg0) throws IOException {
						// TODO Auto-generated method stub

					}
				});
		userEndpointBuilder.setApplicationName("Asaan");
		mUserendpoint = CloudEndpointUtils.updateBuilder(userEndpointBuilder).build();
	}

	private void deleteCurrentUser() {
		// for sign up flow deleting user
		ParseUser user = ParseUser.getCurrentUser();
		/*
		 * if(user!=null) { user.deleteInBackground(new DeleteCallback() {
		 * 
		 * @Override public void done(ParseException e) { Intent i= new
		 * Intent(AsaanSplashActivity.this, AsaanMainActivity.class);
		 * startActivity(i); // close this activity finish();
		 * overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		 * 
		 * } }); } else
		 */
		{
			Intent i = new Intent(SplashActivity.this, LoginChooserActivity.class);
			startActivity(i);
			// close this activity
			finish();
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}

	}

	private void internetAvailabilityAlert(Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		bld.setNegativeButton("Retry", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				trytoLoadMainScreen(mContext);
			}
		});

		bld.create().show();
	}
}
