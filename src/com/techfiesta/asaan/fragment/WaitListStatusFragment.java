package com.techfiesta.asaan.fragment;


import java.io.IOException;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetStoreWaitListQueueEntryForCurrentUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveStoreWaitlistQueueEntry;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreWaitListQueue;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreWaitListQueueAndPosition;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MyCartActivity;
import com.techfiesta.asaan.activity.PaymentInfoActivity;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.activity.StoreListActivity;
import com.techfiesta.asaan.activity.WaitListConfirmActivity;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class WaitListStatusFragment extends Fragment{
	
	private TextView tvStatus,tvStatus2,tvPartyAhead,tvEstTime,tvLeaveLine;
	private String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pdDialog;
	private StoreWaitListQueueAndPosition storeWaitListQueueAndPosition;
	private int FLAG_CANCEL_ENTRY=3;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_wait_list_status,null,false);
		tvStatus=(TextView)v.findViewById(R.id.tv_status);
		tvPartyAhead=(TextView)v.findViewById(R.id.tv_party_ahead);
		tvEstTime=(TextView)v.findViewById(R.id.tv_est_wait_time);
		tvLeaveLine=(TextView)v.findViewById(R.id.tv_leave_line);
		tvStatus2=(TextView)v.findViewById(R.id.tv_status_2);
		tvLeaveLine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAlert();
				
			}
		});
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pdDialog=new ProgressDialog(getActivity());
		pdDialog.setMessage("Please wait...");
		pdDialog.setCancelable(false);
		new GetWaitListQueueForCurrentUer().execute();
		
	}
	private class GetWaitListQueueForCurrentUer extends AsyncTask<Void,Void,Void>
	{
		
		boolean error=false;

		@Override
		protected void onPreExecute() {
			pdDialog.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			 try {
			GetStoreWaitListQueueEntryForCurrentUser getStoreWaitListQueueEntryForCurrentUser=SplashActivity.mStoreendpoint.getStoreWaitListQueueEntryForCurrentUser();
			HttpHeaders httpHeaders = getStoreWaitListQueueEntryForCurrentUser.getRequestHeaders();
			httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
			 storeWaitListQueueAndPosition = getStoreWaitListQueueEntryForCurrentUser.execute();
			 if(storeWaitListQueueAndPosition!=null)
				 Log.e("response wait list status:", storeWaitListQueueAndPosition.toPrettyString());
			} catch (IOException e) {
				error=true;
				e.printStackTrace();
			}
			 
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(pdDialog.isShowing())
				pdDialog.dismiss();
			if (error)
				AsaanUtility.simpleAlert(getActivity(), getResources().getString(R.string.error_alert));
			else {
				updateUI();
			}
			super.onPostExecute(result);
		}
		private void updateUI()
		{
			StoreWaitListQueue storeWaitListQueue=storeWaitListQueueAndPosition.getQueueEntry();
			if(storeWaitListQueue==null)
			{
				tvStatus.setText("Your Wait-list Status");
				tvPartyAhead.setText("You have not joined any wait-lists.");
				tvEstTime.setVisibility(View.INVISIBLE);
				tvStatus2.setVisibility(View.INVISIBLE);
				tvLeaveLine.setVisibility(View.INVISIBLE);
			}
			else
			{
				if(storeWaitListQueue.getEstTimeMax()==null)
					tvEstTime.setVisibility(View.INVISIBLE);
				else
					tvEstTime.setText("Estimated Wait Time: "+storeWaitListQueue.getEstTimeMax());
				if(storeWaitListQueueAndPosition.getPartiesBeforeEntry()!=null)
					  tvPartyAhead.setText("Parties Ahead of You :"+storeWaitListQueueAndPosition.getPartiesBeforeEntry());
			}
		}

	}
	private class RemoveWaitListQueue extends AsyncTask<Void,Void,Void>
	{

		private StoreWaitListQueue storeWaitListQueue;
    	private boolean error=false;
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pdDialog.show();
    		storeWaitListQueue=storeWaitListQueueAndPosition.getQueueEntry();
    		storeWaitListQueue.setStatus(FLAG_CANCEL_ENTRY);
  
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
			if(pdDialog.isShowing())
				pdDialog.dismiss();
			if (error)
				AsaanUtility.simpleAlert(getActivity(), getResources().getString(R.string.error_alert));
			else {
				StoreListFragment strFragment = new StoreListFragment();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.frame_container, strFragment);
				// ft.addToBackStack(null);
				ft.commit();

			}
    	
    }
		
	}
	private void showAlert() {
		AlertDialog Alert = new AlertDialog.Builder(getActivity()).create();
		Alert.setMessage("Confirm?");

		Alert.setButton(Constants.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				new RemoveWaitListQueue().execute();
				
			}
		});
			Alert.setButton(Constants.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			});
		Alert.show();
	}

	

}
