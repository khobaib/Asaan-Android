package com.techfiesta.asaan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;

public class LoginActivity extends Activity {

	EditText userEmail;
	EditText passWord;
	Button Login;
	ParseUser currentUser;
	private ProgressDialog pDialog;

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
					launchActivity(StoreListActivity.class);
				} else {
					Log.d(">>", "login failed");
				}

			}
		});
	}

}
