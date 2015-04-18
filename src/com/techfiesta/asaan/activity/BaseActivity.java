package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BaseActivity  extends Activity{
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
			
		}
	};
	@Override
	protected void onResume() {
		registerReceiver(broadcastReceiver,new IntentFilter(getResources().getString(R.string.intent_filter_finish)));
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}


}
