package com.techfiesta.asaan.broadcastreceiver;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.api.client.json.Json;
import com.parse.Parse;
import com.parse.ParsePushBroadcastReceiver;
import com.techfiesta.asaan.activity.ChatActivity;
import com.techfiesta.asaan.interfaces.NotificationListner;
import com.techfiesta.asaan.utility.AsaanApplication;

public class PushNotificationReceiver extends ParsePushBroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("MSG","on receive");
		String intentAction = intent.getAction();
		JSONObject pushData = null;
		if ("com.parse.push.intent.RECEIVE".equals(intentAction)) {
			
			try {
				pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
			} catch (JSONException e) {
				Log.e("com.parse.ParsePushReceiver", "Unexpected JSONException when receiving push data: ");
			}
			
		}
		Intent i=new Intent("com.asaan");
		i.putExtra("msg",pushData.toString());
		
		Log.e("MSG","here");
		context.sendBroadcast(i);
	}
	
 
}
