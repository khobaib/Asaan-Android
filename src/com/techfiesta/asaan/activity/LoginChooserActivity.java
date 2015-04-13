package com.techfiesta.asaan.activity;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;

public class LoginChooserActivity extends Activity {

	private static final String TAG = LoginChooserActivity.class.getSimpleName();

	Context mContext;
	ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();

		setContentView(R.layout.activity_login_chooser);
		mContext = LoginChooserActivity.this;

		Typeface tf = Typeface.createFromAsset(getAssets(), "font/helvetica_neue_thn.ttf");
		TextView Title = (TextView) findViewById(R.id.tv_title);
		Title.setTypeface(tf);
		

	}

	private void launchActivity(Class<?> launchingClass) {
		Intent intent = new Intent(this, launchingClass);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}

	public void onClickSignup(View v) {
		Class<?> launchingClass = SignUpActivity.class;
		// Class<?> launchingClass = ProfileActivity.class;
		launchActivity(launchingClass);
	}

	public void onClickLogin(View v) {
		Class<?> launchingClass = LoginActivity.class;
		launchActivity(launchingClass);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (ParseFacebookUtils.getSession() != null) {
			ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
		}
	}

	public void onClickFBLogin(View v) {
		Log.e(">>>>>>>>>>", "On fb login click");
		List<String> permissions = Arrays.asList("public_profile", "email");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (user == null) {
					Log.d("FAILURE", "User denied for facebook login");
				} else if (user.isNew()) {
					// call facebook parse session here and get me object data
					// and put to parse user
					Log.d("SUCEESS", "New Facebook logged in user");

					setUserDataforFB();
				} else {
					// 1.call facebook parse session here and get me object data
					// and put to parse user

					Log.d("SUCESS", "Already existing user for facebook login");

					// setUserDataforFB();
					currentUser = ParseUser.getCurrentUser();
					if (currentUser != null) {
						launchActivity(StoreListActivity.class);
						finish();
					}

				}

			}
		});

	}

	private void setUserDataforFB() {
		Session session = ParseFacebookUtils.getSession();
		if (session != null && session.isOpened()) {
			Log.e(">>>>>>", "session opened");
			makeMeRequest();

		}

		currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			launchActivity(StoreListActivity.class);
			finish();
		}

	}

	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, com.facebook.Response response) {
				if (user != null) {
					// JSONObject userObj = user.getInnerJSONObject();
					try {
						// populate data from user profile
						String firstName = user.getFirstName();
						String lastName = user.getLastName();
						String email = (String) user.getProperty("email");
						URL profilePhotoUrl = new URL("https://graph.facebook.com/" + user.getId()
								+ "/picture?type=small");
						// String mUrl =
						// userObj.getJSONObject("picture").getJSONObject("data").getString("url");

						// Save the user profile info in a user property
						ParseUser localcurrentUser = ParseUser.getCurrentUser();
						localcurrentUser.put("firstName", firstName);
						localcurrentUser.put("lastName", lastName);
						localcurrentUser.put("email", email);
						localcurrentUser.put("profilePhotoUrl", profilePhotoUrl.toString());
						localcurrentUser.saveInBackground();
						Log.d("SUCESS", "Data set success");

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (response.getError() != null) {
					/*
					 * if ((response.getError().getCategory() ==
					 * FacebookRequestError .Category.AUTHENTICATION_RETRY) ||
					 * (response.getError().getCategory() ==
					 * FacebookRequestError
					 * .Category.AUTHENTICATION_REOPEN_SESSION)) {
					 * Log.d("ERROR", "The facebook session was invalidated.");
					 * } else {
					 */
					Log.d("ERROR", "Some other error: " + response.getError().getErrorMessage());
					// }
				}

			}

		});
		request.executeAsync();

	}

	
}
