package com.techfiesta.asaan.utility;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.techfiesta.asaan.R;

public class AsaanApplication extends Application {

	// for parse asaan-server settings
	private static final String ASAAN_APPLICATION_ID = "uX5Pxp1cPWJUbhl4qp5REJskOqDIp33tfMcSu1Ac";
	private static final String ASAAN_CLIENT_KEY = "4cad0RAqv53bvlmgiTgnOScuJVk7IY28XeH4Mes5";

	// fb settings
	private static final String FB_APP_ID = "1460267094262357";

	private static Context context;
	protected SharedPreferences SP;

	int PARENT_PAGE;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, ASAAN_APPLICATION_ID, ASAAN_CLIENT_KEY);

		 ParseFacebookUtils.initialize(getResources().getString(R.string.fb_asaan_app_id));
		//ParseFacebookUtils.initialize(getResources().getString(R.string.fb_asaan_app_id_rishta));

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

	public int getPARENT_PAGE() {
		return PARENT_PAGE;
	}

	public void setPARENT_PAGE(int pARENT_PAGE) {
		PARENT_PAGE = pARENT_PAGE;
	}

}
