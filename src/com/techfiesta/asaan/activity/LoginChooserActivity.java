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
import android.view.Window;
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
		// buildStoreEndpoint();
		// buildUserEndpoint();

		// ParseUser.logOut(); //for testing purpose everytime logging out user
		// data from app. in real context won't call this only when log out
		// button pressed will be called this

		// Login = (TextView) findViewById(R.id.tv_login);
		// SignUp = (TextView) findViewById(R.id.tv_signup);
		// Login.setOnClickListener(this);
		// SignUp.setOnClickListener(this);

		/*
		 * mGoogleApiClient = new GoogleApiClient.Builder(this)
		 * .addConnectionCallbacks(this) .addOnConnectionFailedListener(this)
		 * .addApi(Plus.API) .addScope(Plus.SCOPE_PLUS_LOGIN) .build();
		 * btngPlusLogin = (SignInButton)findViewById(R.id.sign_in_button); //
		 * btngPlusLogin.setOnClickListener(this);
		 * btngPlusLogin.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if (v.getId() ==
		 * R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
		 * mSignInClicked = true; btngPlusLogin.performClick(); //
		 * mGoogleApiClient.connect(); resolveSignInError(); }
		 * 
		 * } });
		 */

	}

	/*
	 * private void buildStoreEndpoint() { Storeendpoint.Builder
	 * storeEndpointBuilder; storeEndpointBuilder = new
	 * Storeendpoint.Builder(AndroidHttp.newCompatibleTransport(), new
	 * JacksonFactory(), new HttpRequestInitializer() {
	 * 
	 * @Override public void initialize(HttpRequest arg0) throws IOException {
	 * // TODO Auto-generated method stub
	 * 
	 * } }); storeEndpointBuilder.setApplicationName("Asaan"); mStoreendpoint =
	 * CloudEndpointUtils.updateBuilder(storeEndpointBuilder).build(); }
	 * 
	 * private void buildUserEndpoint() { Userendpoint.Builder
	 * userEndpointBuilder; userEndpointBuilder = new
	 * Userendpoint.Builder(AndroidHttp.newCompatibleTransport(), new
	 * JacksonFactory(), new HttpRequestInitializer() {
	 * 
	 * @Override public void initialize(HttpRequest arg0) throws IOException {
	 * // TODO Auto-generated method stub
	 * 
	 * } }); userEndpointBuilder.setApplicationName("Asaan"); mUserendpoint =
	 * CloudEndpointUtils.updateBuilder(userEndpointBuilder).build(); }
	 */
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
					}

				}

			}
		});

	}

	private void setUserDataforFB() {
		Session session = ParseFacebookUtils.getSession();
		if (session != null && session.isOpened()) {

			makeMeRequest();

		}

		currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			launchActivity(StoreListActivity.class);
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

	/*
	 * @Override protected void onStart() { // TODO Auto-generated method stub
	 * super.onStart();
	 * 
	 * mGoogleApiClient.connect(); }
	 * 
	 * 
	 * @Override protected void onStop() { // TODO Auto-generated method stub
	 * super.onStop(); if (mGoogleApiClient.isConnected()) {
	 * mGoogleApiClient.disconnect(); } }
	 * 
	 * public void onConnectionFailed(ConnectionResult result) {
	 * if(!result.hasResolution()){
	 * GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
	 * 0).show(); return; } if (!mIntentInProgress) { // Store the
	 * ConnectionResult so that we can use it later when the user clicks //
	 * 'sign-in'. mConnectionResult = result;
	 * 
	 * if (mSignInClicked) { // The user has already clicked 'sign-in' so we
	 * attempt to resolve all // errors until the user is signed in, or they
	 * cancel. resolveSignInError(); } } }
	 * 
	 * 
	 * A helper method to resolve the current ConnectionResult error. private
	 * void resolveSignInError() { if (mConnectionResult.hasResolution()) { try
	 * { mIntentInProgress = true;
	 * startIntentSenderForResult(mConnectionResult.getResolution
	 * ().getIntentSender(), RC_SIGN_IN, null, 0, 0, 0); } catch
	 * (SendIntentException e) { // The intent was canceled before it was sent.
	 * Return to the default // state and attempt to connect to get an updated
	 * ConnectionResult. mIntentInProgress = false; mGoogleApiClient.connect();
	 * } } }
	 * 
	 * 
	 * 
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { // TODO Auto-generated method stub
	 * super.onActivityResult(requestCode, resultCode, data);
	 * 
	 * 
	 * if (requestCode == RC_SIGN_IN) { if (resultCode != RESULT_OK) {
	 * mSignInClicked = false; }
	 * 
	 * mIntentInProgress = false;
	 * 
	 * if (!mGoogleApiClient.isConnecting()) { mGoogleApiClient.connect(); } }
	 * 
	 * if(ParseFacebookUtils.getSession()!=null){
	 * ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data); }
	 * }
	 * 
	 * public void onConnected(Bundle connectionHint) { // We've resolved any
	 * connection errors. mGoogleApiClient can be used to // access Google APIs
	 * on behalf of the user. mSignInClicked = false;
	 * 
	 * Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	 * getProfileInformation();
	 * 
	 * }
	 * 
	 * private void getProfileInformation() { try { if
	 * (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) { Person
	 * currentPerson = Plus.PeopleApi .getCurrentPerson(mGoogleApiClient); final
	 * String personName = currentPerson.getDisplayName(); final String
	 * personPhotoUrl = currentPerson.getImage().getUrl(); String
	 * personGooglePlusProfile = currentPerson.getUrl(); final String email =
	 * Plus.AccountApi.getAccountName(mGoogleApiClient); final String userId =
	 * currentPerson.getId(); Name userName = currentPerson.getName(); final
	 * String firstName = userName.getGivenName(); final String lastName =
	 * userName.getFamilyName();
	 * 
	 * final String passWord = userId;
	 * 
	 * Log.e(TAG, "Name: " + personName + ", plusProfile: " +
	 * personGooglePlusProfile + ", email: " + email + ", Image: " +
	 * personPhotoUrl + ", userid: "+ userId); // checkMailExist(email);
	 * ParseQuery<ParseUser> query = ParseUser.getQuery();
	 * query.whereEqualTo("email", email); query.getFirstInBackground(new
	 * GetCallback<ParseUser>() {
	 * 
	 * @Override public void done(ParseUser object, ParseException e) {
	 * if(object==null){ Log.d(">>>", "Doesn't exist email "); isExist =false;
	 * ParseUser pUser = new ParseUser(); pUser.setUsername(personName);
	 * pUser.setPassword(passWord); pUser.setEmail(email);
	 * 
	 * pUser.put("firstName", firstName); pUser.put("lastName", lastName);
	 * pUser.put("profilePhotoUrl", personPhotoUrl);
	 * 
	 * pUser.signUpInBackground(new SignUpCallback() {
	 * 
	 * @Override public void done(ParseException e) { if(e==null){
	 * Log.d("SUCCESSS", "google+ sign up successfull");
	 * loginUser(personName,passWord); }else{ Log.d("FAILURE",
	 * "google+ sign up failed.so this user is already existing");
	 * loginUser(personName,passWord); }
	 * 
	 * } });
	 * 
	 * }else{ Log.d(">>>", "Already existing email ="); isExist= true;
	 * signOutFromGplus(); }
	 * 
	 * } });
	 * 
	 * 
	 * 
	 * //txtName.setText(personName); // txtEmail.setText(email);
	 * 
	 * // by default the profile url gives 50x50 px image only // we can replace
	 * the value with whatever dimension we want by // replacing sz=X //
	 * personPhotoUrl = personPhotoUrl.substring(0, // personPhotoUrl.length() -
	 * 2) // + PROFILE_PIC_SIZE; // // new
	 * LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
	 * 
	 * } else { Toast.makeText(getApplicationContext(),
	 * "Person information is null", Toast.LENGTH_LONG).show(); } } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public void onConnectionSuspended(int cause) {
	 * mGoogleApiClient.connect(); }
	 * 
	 * 
	 * public void onClickFBLogin(View v) { Log.e(">>>>>>>>>>",
	 * "On fb login click"); List<String> permissions =
	 * Arrays.asList("public_profile","email");
	 * ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
	 * 
	 * @Override public void done(ParseUser user, ParseException e) { if(user
	 * ==null){ Log.d("FAILURE", "User denied for facebook login"); }else
	 * if(user.isNew()){ //call facebook parse session here and get me object
	 * data and put to parse user Log.d("SUCEESS",
	 * "New Facebook logged in user");
	 * 
	 * isLogin = true; setUserDataforFB(); }else{ //1.call facebook parse
	 * session here and get me object data and put to parse user //2.if doesn't
	 * exist already this checking will be done later Log.d("SUCESS",
	 * "Already existing user for facebook login" ); isLogin =true;
	 * setUserDataforFB( );
	 * 
	 * }
	 * 
	 * } });
	 * 
	 * }
	 * 
	 * private void setUserDataforFB(){ Session session =
	 * ParseFacebookUtils.getSession(); if(session!=null && session.isOpened()){
	 * makeMeRequest();
	 * 
	 * }
	 * 
	 * }
	 * 
	 * private void makeMeRequest() { Request request =
	 * Request.newMeRequest(ParseFacebookUtils.getSession(), new
	 * Request.GraphUserCallback() { public void onCompleted(GraphUser user,
	 * Response response) { if (user != null) { //JSONObject userObj =
	 * user.getInnerJSONObject(); try { //populate data from user profile String
	 * firstName = user.getFirstName(); String lastName = user.getLastName();
	 * String email = (String) user.getProperty("email"); URL profilePhotoUrl =
	 * new
	 * URL("https://graph.facebook.com/"+user.getId()+"/picture?type=small");
	 * //String mUrl =
	 * userObj.getJSONObject("picture").getJSONObject("data").getString("url");
	 * 
	 * // Save the user profile info in a user property ParseUser currentUser =
	 * ParseUser .getCurrentUser(); currentUser.put("firstName", firstName);
	 * currentUser.put("lastName", lastName); currentUser.put("email", email);
	 * currentUser.put("profilePhotoUrl", profilePhotoUrl.toString());
	 * currentUser.saveInBackground(); Log.d("SUCESS", "Data set success");
	 * setLoginVisibility(isLogin);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * } else if (response.getError() != null) { if
	 * ((response.getError().getCategory() ==
	 * FacebookRequestError.Category.AUTHENTICATION_RETRY) ||
	 * (response.getError().getCategory() ==
	 * FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
	 * Log.d("ERROR", "The facebook session was invalidated."); } else {
	 * Log.d("ERROR", "Some other error: " + response.getError()
	 * .getErrorMessage()); } } } }); request.executeAsync();
	 * 
	 * }
	 * 
	 * public void onClickLogOut(View v) {
	 * 
	 * ParseUser.logOut(); signOutFromGplus(); isLogin =false;
	 * setLoginVisibility(isLogin);
	 * 
	 * }
	 *//**
	 * Sign-out from google
	 * */
	/*
	 * private void signOutFromGplus() { if (mGoogleApiClient.isConnected()) {
	 * Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	 * mGoogleApiClient.disconnect(); mGoogleApiClient.connect();
	 * findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	 * 
	 * } } public void onClickLogin(View v) { Log.e(">>>>>>", "Login clicked");
	 * 
	 * LayoutInflater inflater = (LayoutInflater) getLayoutInflater(); View
	 * loginView = inflater.inflate(R.layout.login_dialog, null, false); final
	 * AlertDialog alert = new
	 * AlertDialog.Builder(AsaanMainActivity.this).create();
	 * alert.setView(loginView, 0, 0, 0, 0); alert.setCancelable(false);
	 * 
	 * final EditText etUserName = (EditText)
	 * loginView.findViewById(R.id.etUserName); final EditText etPassword =
	 * (EditText) loginView.findViewById(R.id.etPassword);
	 * 
	 * Button OK = (Button) loginView.findViewById(R.id.btnLogin);
	 * OK.setOnClickListener(new OnClickListener() {
	 * 
	 * public void onClick(View v) { String user =
	 * etUserName.getText().toString(); String pass =
	 * etPassword.getText().toString();
	 * 
	 * alert.dismiss();
	 * 
	 * loginUser(user, pass); }
	 * 
	 * });
	 * 
	 * alert.show();
	 * 
	 * }
	 * 
	 * public void loginUser(String username,String password){
	 * ParseUser.logInInBackground(username, password, new LogInCallback() {
	 * 
	 * @Override public void done(ParseUser user, ParseException e) { if (user
	 * != null) { Log.d("SUCCESS", user + " is logged in"); isLogin=true;
	 * setLoginVisibility(isLogin); } else { Log.d("FAILURE",
	 * "Failed to login"); e.printStackTrace(); }
	 * 
	 * } }); }
	 * 
	 * private void checkMailExist(String email){ ParseQuery<ParseUser> query =
	 * ParseUser.getQuery(); query.whereEqualTo("email", email);
	 * query.getFirstInBackground(new GetCallback<ParseUser>() {
	 * 
	 * @Override public void done(ParseUser object, ParseException e) {
	 * if(object==null){ Log.d(">>>", "Doesn't exist email "); isExist =false;
	 * }else{ Log.d(">>>", "Already existing email ="); isExist= true; }
	 * 
	 * } });
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * protected void setLoginVisibility(boolean loginStatus) {
	 * if(loginStatus==true){ btngPlusLogin.setVisibility(View.GONE);
	 * btnSignup.setVisibility(View.GONE);
	 * findViewById(R.id.btnLogin).setVisibility(View.GONE);
	 * findViewById(R.id.btn_fb_login).setVisibility(View.GONE);
	 * findViewById(R.id.btnLogout).setVisibility(View.VISIBLE); }else{
	 * btngPlusLogin.setVisibility(View.VISIBLE);
	 * btnSignup.setVisibility(View.VISIBLE);
	 * findViewById(R.id.btnLogin).setVisibility(View.VISIBLE);
	 * findViewById(R.id.btn_fb_login).setVisibility(View.VISIBLE);
	 * findViewById(R.id.btnLogout).setVisibility(View.GONE); }
	 * 
	 * }
	 */
}
