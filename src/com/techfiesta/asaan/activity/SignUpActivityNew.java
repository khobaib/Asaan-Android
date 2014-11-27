package com.techfiesta.asaan.activity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.techfiesta.asaan.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivityNew extends Activity{
	private EditText edtEmail;
	private EditText edtPass;
	private Button btnSignUp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup_new);
		edtEmail=(EditText)findViewById(R.id.edt_email);
		edtPass=(EditText)findViewById(R.id.edt_pass);
		btnSignUp=(Button)findViewById(R.id.btn_signup);
		
		btnSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.e("MSG","on click sign up");
				String email=edtEmail.getText().toString();
				String pass=edtPass.getText().toString();
				if(email.equals(""))
				{
					alert("Please enter email");
				}
				else if(pass.equals(""))
				{
					alert("Please enter password");
				}
				else{
					signUpInparse(email, pass);
				 }
				
			}
		});
		
	}
	private void signUpInparse(String email,String pass)
	{
		ParseUser user=new ParseUser();
		user.setEmail(email);
		user.setPassword(pass);
		user.setUsername(email);
		user.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e) {
				
				if(e==null)
				{
					//go to profile activity
					Intent intent=new Intent(SignUpActivityNew.this,ProfileActivity.class);
					startActivity(intent);
				}
				else
				{
					alert("Error in sign up!");
				}
			}
		});
		
	}
	
	private void alert(String msg)
	{
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivityNew.this);

		// Setting Dialog Title
		alertDialog.setTitle("Asaan");

		// Setting Dialog Message
		alertDialog.setMessage(msg);
		alertDialog.setNeutralButton("Ok",null);
		alertDialog.create().show();
	}

}
