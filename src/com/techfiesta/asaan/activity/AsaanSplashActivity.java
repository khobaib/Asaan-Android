package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


public class AsaanSplashActivity extends Activity{
	Context mContext;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash_screen);
	mContext = AsaanSplashActivity.this;
	
	trytoLoadMainScreen(mContext);
	
	
}

private void trytoLoadMainScreen(Context context) {
	if(AsaanUtility.hasInternet(context)){
		new LoadMainScreen().execute();
		
	}else{
		internetAvailabilityAlert(context, "Please Check your internet connection");
	}
	
}

// will use this class  to do app loading related stuffs in background
public class LoadMainScreen extends AsyncTask<Void, Void, Boolean>{

	
	@Override
	protected Boolean doInBackground(Void... params) {
	
		return true;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result){
			
			Intent i =  new Intent(AsaanSplashActivity.this, AsaanMainActivity.class);
			startActivity(i);

			// close this activity
			finish();
			
		}
		
	}
}

private void internetAvailabilityAlert(Context context, String message) {
	AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
	// bld.setTitle(context.getResources().getText(R.string.app_name));
	bld.setTitle(R.string.app_name);
	bld.setMessage(message);
	bld.setCancelable(false);
	bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			finish();
		}
	});
	bld.setNegativeButton("Retry", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			trytoLoadMainScreen(mContext);
		}
	});

	bld.create().show();
}
}
