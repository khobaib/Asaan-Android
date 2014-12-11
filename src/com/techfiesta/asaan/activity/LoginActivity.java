package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LoginActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.activity_login_2);
	}

}
