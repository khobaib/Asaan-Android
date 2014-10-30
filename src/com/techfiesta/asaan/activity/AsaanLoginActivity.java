package com.techfiesta.asaan.activity;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;

public class AsaanLoginActivity extends Activity implements OnClickListener{
	EditText userName;
	EditText passWord;
	Button Login;
	Button FBLogin;
	ParseUser currentUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		userName = (EditText) findViewById(R.id.etEmailLogIn);
		passWord = (EditText) findViewById(R.id.etPasswordLogIn);
		Login = (Button) findViewById(R.id.btnLogIn);
		FBLogin = (Button) findViewById(R.id.btnFbLogin);
		Login.setOnClickListener(this);
		FBLogin.setOnClickListener(this);
		
		 try {
			 PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
		        for (Signature signature : info.signatures) {
		            MessageDigest md = MessageDigest.getInstance("SHA");
		            md.update(signature.toByteArray());
		            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
		            }
		    } catch (NameNotFoundException e) {
		    	e.printStackTrace();

		    } catch (NoSuchAlgorithmException e) {
		    	e.printStackTrace();
		    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(ParseFacebookUtils.getSession()!=null){
			 ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
			}
	}
	
	private void launchActivity(Class<?> launchingClass) {
		Intent intent = new Intent(this, launchingClass);
		startActivity(intent);
		finish();
		}
	private void LoginUser(){
		String name = userName.getText().toString();
		String password = passWord.getText().toString();
		ParseUser.logInInBackground(name, password, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(user!=null){
					
					currentUser = ParseUser.getCurrentUser();
					Log.d(">>","login success"+ currentUser.getEmail());
					launchActivity(StoreListActivity.class);
				}else{
					Log.d(">>", "login failed");
				}
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		Log.e(">>>", "onclick"+v.getId());
		//Class<?> launchingClass = null;
		if(v.getId() == R.id.btnLogIn){
			LoginUser();
		}else if(v.getId() == R.id.btnFbLogin){
			onClickFBLogin(v);
		}
		//launchActivity(launchingClass);
		
	}
	
	public void onClickFBLogin(View v) {
		Log.e(">>>>>>>>>>", "On fb login click");
		List<String> permissions = Arrays.asList("public_profile","email");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(user ==null){
					Log.d("FAILURE", "User denied for facebook login");
				}else if(user.isNew()){
					//call facebook parse session here and get me object data and put to parse user
					Log.d("SUCEESS", "New Facebook logged in user");
					
					
					setUserDataforFB();
				}else{
					//1.call facebook parse session here and get me object data and put to parse user 
					
					Log.d("SUCESS", "Already existing user for facebook login" );
					
					setUserDataforFB( );
					
				}
				
			}
		});

	}
	
	private void setUserDataforFB(){
		Session session = ParseFacebookUtils.getSession();
		if(session!=null && session.isOpened()){
			makeMeRequest();
			
		}
		
		currentUser = ParseUser.getCurrentUser();
		if(currentUser!=null){
			launchActivity(StoreListActivity.class);
		}
		
	}
	
	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					

					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							//JSONObject userObj = user.getInnerJSONObject();
							try {
								//populate data from user profile
								String 	firstName = user.getFirstName();
								String lastName = user.getLastName();
								String email = (String) user.getProperty("email");
								URL profilePhotoUrl = new URL("https://graph.facebook.com/"+user.getId()+"/picture?type=small");
								//String mUrl = userObj.getJSONObject("picture").getJSONObject("data").getString("url");
								
								// Save the user profile info in a user property
								 ParseUser localcurrentUser = ParseUser
										.getCurrentUser();
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
							/*if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d("ERROR",
										"The facebook session was invalidated.");
							} else {*/
								Log.d("ERROR",
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							//}
						}
						
					}
				});
		request.executeAsync();

	}

}
