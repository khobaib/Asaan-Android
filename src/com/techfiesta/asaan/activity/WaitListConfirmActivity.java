package com.techfiesta.asaan.activity;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetStoreWaitListQueue;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveStoreWaitlistQueueEntry;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreWaitListQueue;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreWaitListQueueCollection;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

public class WaitListConfirmActivity extends BaseActivity implements OnClickListener{
	private TextView tvPeople;
	private Button btnPeoplePlus,btnPeopleMinus,btnGetInLine;
	private TextView tvPartyAhead2,tvPartyAhead4,tvPartyAhead5orMore,tvWaitTime2,tvWaitTime4,tvWaitTime5orMore;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pDialog;
	private int FLAG_GET_WAIT_LIST_QUEUE=1;
	private int FLAG_SAVE_QUEUE_IN_SERVER=2;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waitlist4);
		actionBar=getActionBar();
		actionBar.setTitle("Back");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		pDialog=new ProgressDialog(WaitListConfirmActivity.this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		btnPeoplePlus=(Button)findViewById(R.id.btnIncPeople);
		btnPeopleMinus=(Button)findViewById(R.id.btnDecPeople);	
		
		tvPeople=(TextView)findViewById(R.id.txtViewPeople);
		btnGetInLine=(Button)findViewById(R.id.btnGetInLine);
		
		tvPartyAhead2=(TextView)findViewById(R.id.txtViewPAheadSize1);
		tvPartyAhead4=(TextView)findViewById(R.id.txtViewPAheadSize2);
		tvPartyAhead5orMore=(TextView)findViewById(R.id.txtViewPAheadSize3);
		
		tvWaitTime2=(TextView)findViewById(R.id.txtViewWtTimePSize1);
		tvWaitTime4=(TextView)findViewById(R.id.txtViewWtTimePSize2);
		tvWaitTime5orMore=(TextView)findViewById(R.id.txtViewWtTimePSize3);
		
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
		private boolean error=false;
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
				error=true;
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pDialog!=null)
				pDialog.dismiss();
			if(error)
				AsaanUtility.simpleAlert(WaitListConfirmActivity.this,getResources().getString(R.string.error_alert));
			else
			{
				//update ui
				if(storeWaitListQueueCollection!=null)
					updateUI();
				
			}
		}
		private void updateUI()
		{
			if(storeWaitListQueueCollection.getItems()!=null)
			{
				int partySize2=0;
				int partySize4=0;
				int partySize5orMore=0;
				int size=storeWaitListQueueCollection.getItems().size();
				for (int i = 0; i < size; i++) {
					if (storeWaitListQueueCollection.getItems().get(i).getPartySize() < 3)
						partySize2++;
					else if (storeWaitListQueueCollection.getItems().get(i).getPartySize() < 5)
						partySize4++;
					else
						partySize5orMore++;
				}
				tvPartyAhead2.setText(""+partySize2);
				tvPartyAhead4.setText(""+partySize4);
				tvPartyAhead5orMore.setText(""+partySize5orMore);
				
				int total=partySize2+partySize4+partySize5orMore;
				 
	             if (total == 0)
	                 tvWaitTime2.setText("15");
	             else
	            	 tvWaitTime2.setText(""+(total*2+15)+" - "+(total*2+30));
	                
	             if (total == 0)
	            	 tvWaitTime4.setText("15");
	             else
	            	 tvWaitTime4.setText(""+(total*2+15)+" - "+(total*2+45));
	             if (total == 0)
	            	 tvWaitTime5orMore.setText("15");
	             else
	            	 tvWaitTime5orMore.setText(""+(total*2+15)+" - "+(total*2+45));
			}
		}
		
	}
	
    private class SaveWaitListQueueInServer extends AsyncTask<Void,Void,Void>
    {
    	StoreWaitListQueue storeWaitListQueue=new StoreWaitListQueue();
    	private boolean error=false;
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
				error=true;
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if(error)
				AsaanUtility.simpleAlert(WaitListConfirmActivity.this,createWaitListErrorString());
			else
				AsaanUtility.simpleAlert(WaitListConfirmActivity.this,createWaitListConfrimString());
		}
    	
    }
    private String createWaitListConfrimString()
    {
    	return "Thank you- Your reservation request has been sent.If you need to make changes please call "+ AsaanUtility.selectedStore.getPhone()+".";
    }
    private String createWaitListErrorString()
    {
    	return "We were unable to reach "+AsaanUtility.selectedStore.getName()+" and place you on their wait list. We're really sorry. Please call "+AsaanUtility.selectedStore.getName()+" directly at "+AsaanUtility.selectedStore.getPhone()+".";
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
			internetAvailabilityAlert(WaitListConfirmActivity.this,getResources().getString(R.string.internet_alert),FLAG_GET_WAIT_LIST_QUEUE);

		
	}
	private void saveWaitListEntry()
	{
		if(AsaanUtility.hasInternet(WaitListConfirmActivity.this))
	        new SaveWaitListQueueInServer().execute();
		else
			internetAvailabilityAlert(WaitListConfirmActivity.this, getResources().getString(R.string.internet_alert),FLAG_SAVE_QUEUE_IN_SERVER);

	}
	public boolean onOptionsItemSelected(MenuItem item) {

		 if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}
	

}
