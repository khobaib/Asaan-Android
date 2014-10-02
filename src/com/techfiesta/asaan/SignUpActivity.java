package com.techfiesta.asaan;



import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class SignUpActivity extends Activity{
	EditText etFirstname;
	EditText etLastname;
	EditText etEmail;
	EditText etPass;
	
	Button btnSigunup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		etFirstname = (EditText) findViewById(R.id.etFirstName);
		etLastname = (EditText) findViewById(R.id.etLastName);
		etEmail = (EditText) findViewById(R.id.etMail);
		etPass = (EditText) findViewById(R.id.etPassword);
		
		btnSigunup = (Button) findViewById(R.id.btnSignUp);
		
		
	}
	
	public void onClickSignUp(View v){
		final String firstName = etFirstname.getText().toString().trim();
		final String lastName = etLastname.getText().toString().trim();
		final String emailId = etEmail.getText().toString().trim();
		final String passWord = etPass.getText().toString().trim();
		
		ParseQuery<ParseUser> query =  ParseUser.getQuery();
		query.whereEqualTo("email", emailId);
		query.getFirstInBackground(new GetCallback<ParseUser>() {

			@Override
			public void done(ParseUser object, ParseException e) {
				if(object==null){
					Log.d(">>>", "Doesn't exist email ");
					ParseUser pUser = new ParseUser();
					pUser.setUsername(emailId);
					pUser.setEmail(emailId);
					pUser.setPassword(passWord);
					pUser.put("firstName", firstName);
					pUser.put("lastName", lastName);
					
					pUser.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
						if(e==null){
							Log.d("SUCCESS", "Successfull signup");
						}else{
							Log.d("FAIL", "Failed to Sign up this user");
						}
							
						}
					});
			    	
				}else{
					 Log.d(">>>", "Already existing email =");
				    	
				}
				
			}
		});
		
		
		
	}

}
