package com.techfiesta.asaan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.techfiesta.asaan.R;

public class ResetPasswordActivity extends Activity {

	EditText etMail;

	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);

		etMail = (EditText) findViewById(R.id.et_email);

		pDialog = new ProgressDialog(ResetPasswordActivity.this);

		etMail.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					String resetMail = null;
					resetMail = etMail.getText().toString();
					if (resetMail == null || !resetMail.endsWith(".com")) {
						Toast.makeText(ResetPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();

					} else {
						resetPasswordinParse(resetMail);
					}
				}
				return false;
			}
		});

	}

	private void resetPasswordinParse(String email) {
		pDialog.setMessage("Sending request");
		pDialog.show();
		ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.e(">>", "Reset success");
					pDialog.dismiss();
					Toast.makeText(ResetPasswordActivity.this,
							"Password Reset Successful!Please check your email to change password", Toast.LENGTH_SHORT)
							.show();
					overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
					finish();
				} else {
					Log.e(">>", "Reset failure");
					Toast.makeText(ResetPasswordActivity.this, "Sorry.Couldn't reset password", Toast.LENGTH_SHORT)
							.show();
					finish();
				}

			}
		});
	}
}
