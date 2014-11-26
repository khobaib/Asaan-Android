package com.techfiesta.asaan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.SignupPagerAdapter;
import com.techfiesta.asaan.communication.Communicator;
import com.viewpagerindicator.CirclePageIndicator;

public class SignUpActivity extends FragmentActivity implements Communicator {
	// EditText etFirstname;
	// EditText etLastname;
	// EditText etEmail;
	// EditText etPass;
	//
	// Button btnSigunup;

	ViewPager pager;
	private CirclePageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_signup);
		setContentView(R.layout.activity_sign_up_frag_holder);

		pager = (ViewPager) findViewById(R.id.view_pager_SignUp);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);

		FragmentPagerAdapter adapter = new SignupPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		indicator.setViewPager(pager);

		// etFirstname = (EditText) findViewById(R.id.etFirstName);
		// etLastname = (EditText) findViewById(R.id.etLastName);
		// etEmail = (EditText) findViewById(R.id.etMail);
		// etPass = (EditText) findViewById(R.id.etPassword);
		//
		// btnSigunup = (Button) findViewById(R.id.btnSignUp);

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public void respond(int position) {

		pager.setCurrentItem(position);

	}

	/*
	 * public void onClickSignUp(View v){ final String firstName =
	 * etFirstname.getText().toString().trim(); final String lastName =
	 * etLastname.getText().toString().trim(); final String emailId =
	 * etEmail.getText().toString().trim(); final String passWord =
	 * etPass.getText().toString().trim();
	 * 
	 * ParseQuery<ParseUser> query = ParseUser.getQuery();
	 * query.whereEqualTo("email", emailId); query.getFirstInBackground(new
	 * GetCallback<ParseUser>() {
	 * 
	 * @Override public void done(ParseUser object, ParseException e) {
	 * if(object==null){ Log.d(">>>", "Doesn't exist email "); ParseUser pUser =
	 * new ParseUser(); pUser.setUsername(emailId); pUser.setEmail(emailId);
	 * pUser.setPassword(passWord); pUser.put("firstName", firstName);
	 * pUser.put("lastName", lastName);
	 * 
	 * pUser.signUpInBackground(new SignUpCallback() {
	 * 
	 * @Override public void done(ParseException e) { if(e==null){
	 * Log.d("SUCCESS", "Successfull signup"); }else{ Log.d("FAIL",
	 * "Failed to Sign up this user"); }
	 * 
	 * } });
	 * 
	 * }else{ Log.d(">>>", "Already existing email =");
	 * 
	 * }
	 * 
	 * } });
	 * 
	 * 
	 * 
	 * }
	 */

	public void onClickNext(View v) {
		startActivity(new Intent(this, StoreListActivity.class));
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

}
