package com.techfiesta.asaan.activity;

import java.io.IOException;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetStoreWaitListQueue;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveStoreWaitlistQueueEntry;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreWaitListQueue;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreWaitListQueueCollection;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WaitListConfirmActivity extends Activity implements OnClickListener{
	private TextView tvPeople;
	private Button btnPeoplePlus,btnPeopleMinus,btnGetInLine;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pDialog;
	private int FLAG_GET_WAIT_LIST_QUEUE=1;
	private int FLAG_SAVE_QUEUE_IN_SERVER=2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waitlist4);
		pDialog=new ProgressDialog(WaitListConfirmActivity.this);
		pDialog.setMessage("Please wait...");
		btnPeoplePlus=(Button)findViewById(R.id.btnIncPeople);
		btnPeopleMinus=(Button)findViewById(R.id.btnDecPeople);	
		
		tvPeople=(TextView)findViewById(R.id.txtViewPeople);
		btnGetInLine=(Button)findViewById(R.id.btnGetInLine);
		
		btnPeoplePlus.setOnClickListener(this);
		btnPeopleMinus.setOnClickListener(this);
		btnGetInLine.setOnClickListener(this);
		
		loadStoreWaitListQueueFromServer();
		
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnIncPeople)
		{
			String val= tvPeople.getText().toString();
			tvPeople.setText(""+(Integer.valueOf(val)+1));
		}
		else if(v.getId()==R.id.btnDecPeople)
			{
				String val= tvPeople.getText().toString();
				int people=Integer.valueOf(val);
				if(people>1)
				  tvPeople.setText(""+(people-1));
			}
		else if(v.getId()==R.id.btnGetInLine)
		{
			saveWaitListEntry();
		}
		
	}
	private class GetWaitListQueueFromSever extends AsyncTask<Void,Void,Void>
	{
		@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog.show();
			}
     private StoreWaitListQueueCollection storeWaitListQueueCollection;
		@Override
		protected Void doInBackground(Void... params) {
			GetStoreWaitListQueue getStoreWaitListQueue;
			try {
				getStoreWaitListQueue=SplashActivity.mStoreendpoint.getStoreWaitListQueue(AsaanUtility.selectedStore.getId());
				HttpHeaders httpHeaders=getStoreWaitListQueue.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				storeWaitListQueueCollection=getStoreWaitListQueue.execute();
				Log.e("response",storeWaitListQueueCollection.toPrettyString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pDialog!=null)
				pDialog.dismiss();
		}
		
	}
    private class SaveWaitListQueueInServer extends AsyncTask<Void,Void,Void>
    {
    	StoreWaitListQueue storeWaitListQueue=new StoreWaitListQueue();
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog.show();
    		storeWaitListQueue.setPartySize(Integer.getInteger(tvPeople.getText().toString()));
    		storeWaitListQueue.setStatus(1);
    		storeWaitListQueue.setStoreId(AsaanUtility.selectedStore.getId());
    	}

		@Override
		protected Void doInBackground(Void... params) {
			SaveStoreWaitlistQueueEntry saveStoreWaitlistQueueEntry;
			try {
				saveStoreWaitlistQueueEntry=SplashActivity.mStoreendpoint.saveStoreWaitlistQueueEntry(storeWaitListQueue);
				HttpHeaders httpHeaders=saveStoreWaitlistQueueEntry.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				storeWaitListQueue=saveStoreWaitlistQueueEntry.execute();
				Log.e("MSG",storeWaitListQueue.toPrettyString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
		}
    	
    }
	private void internetAvailabilityAlert(Context context, String message,final int flag) {
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
				if(flag==FLAG_GET_WAIT_LIST_QUEUE)
				  loadStoreWaitListQueueFromServer();
				else if(flag==FLAG_SAVE_QUEUE_IN_SERVER)
					saveWaitListEntry();
				
			}
		});

		bld.create().show();
	}

	private void loadStoreWaitListQueueFromServer()
	{
		if(AsaanUtility.hasInternet(WaitListConfirmActivity.this))
	        new GetWaitListQueueFromSever().execute();
		else
			internetAvailabilityAlert(WaitListConfirmActivity.this, "Please Check your internet connection",FLAG_GET_WAIT_LIST_QUEUE);

		
	}
	private void saveWaitListEntry()
	{
		if(AsaanUtility.hasInternet(WaitListConfirmActivity.this))
	        new SaveWaitListQueueInServer().execute();
		else
			internetAvailabilityAlert(WaitListConfirmActivity.this, "Please Check your internet connection",FLAG_SAVE_QUEUE_IN_SERVER);

	}
	

}
