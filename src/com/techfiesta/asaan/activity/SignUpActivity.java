package com.techfiesta.asaan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.techfiesta.asaan.R;

public class SignUpActivity extends Activity {
	private EditText edtEmail;
	private EditText edtPass;
	private Button btnSave;
	private ProgressDialog pdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();

		setContentView(R.layout.activity_signup);
		edtEmail = (EditText) findViewById(R.id.et_email);
		edtPass = (EditText) findViewById(R.id.et_pass);
		btnSave = (Button) findViewById(R.id.b_save);

		pdialog = new ProgressDialog(SignUpActivity.this);
		pdialog.setMessage("Loading...");

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.e("MSG", "on click sign up");
				String email = edtEmail.getText().toString();
				String pass = edtPass.getText().toString();
				if (email == null || email.equals("") || !email.endsWith(".com")) {
					alert("Please enter email address.");
				} else if (pass == null || pass.length() < 8) {
					alert("Password muct be atleast 8 characters.");
				} else {
					signUpInparse(email, pass);
				}

			}
		});

	}

	private void signUpInparse(String email, String pass) {
		ParseUser user = new ParseUser();
		user.setEmail(email);
		user.setPassword(pass);
		user.setUsername(email);

		pdialog.show();
		user.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e) {

				if (e == null) {
					// go to profile activity
					Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
					startActivity(intent);
				} else {
					alert("Error in sign up!");
					// for testing
					// Intent intent = new Intent(SignUpActivityNew.this,
					// ProfileActivity.class);
					// startActivity(intent);
				}
				if (pdialog.isShowing()) {
					pdialog.dismiss();
				}
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { // Back key pressed
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void alert(String msg) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Savoir");

		// Setting Dialog Message
		alertDialog.setMessage(msg);
		alertDialog.setNeutralButton("Ok", null);
		alertDialog.create().show();
	}

}
