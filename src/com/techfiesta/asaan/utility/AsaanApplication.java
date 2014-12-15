package com.techfiesta.asaan.utility;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AsaanApplication extends Application {

	// for parse asaan-server settings
	private static final String ASAAN_APPLICATION_ID = "uX5Pxp1cPWJUbhl4qp5REJskOqDIp33tfMcSu1Ac";
	private static final String ASAAN_CLIENT_KEY = "4cad0RAqv53bvlmgiTgnOScuJVk7IY28XeH4Mes5";

	// fb settings
	private static final String FB_APP_ID = "1460267094262357";

	private static Context context;
	protected SharedPreferences SP;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, ASAAN_APPLICATION_ID, ASAAN_CLIENT_KEY);

		ParseFacebookUtils.initialize(FB_APP_ID);

		context = getApplicationContext();
		initializeSharedPreference();

	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public static Context getAppContext() {
		return context;
	}

	private void initializeSharedPreference() {
		this.SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

}
