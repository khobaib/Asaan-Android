package com.techfiesta.asaan.fragment;

import java.io.IOException;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetOrderReviewsForStore;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetStoreOrdersForCurrentUserAndStore;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.OrderReviewListAndCount;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrderListAndCount;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoryFragment extends Fragment{
	
	private int FIRST_POSITION=0;
	private int MAX_RESULT=50;
	private TextView tvName;
	private String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pdDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.frag_restaurant_history4,null,false);
		tvName=(TextView)v.findViewById(R.id.txtViewResName);
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvName.setText(AsaanUtility.selectedStore.getName());
		pdDialog=new ProgressDialog(getActivity());
		pdDialog.setMessage("Please wait...");
		if(AsaanUtility.hasInternet(getActivity()))
			new GetOrdersForCurrentUserFromServer().execute();
		else
			AsaanUtility.simpleAlert(getActivity(), getResources().getString(R.string.internet_alert));
			
		
	}
	
	private class GetOrdersForCurrentUserFromServer extends AsyncTask<Void,Void,Void>
	{
		boolean error=false;

		StoreOrderListAndCount storeOrderListAndCount=null;
		@Override
		protected void onPreExecute() {
			pdDialog.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			try {
			 GetStoreOrdersForCurrentUserAndStore getStoreOrdersForCurrentUserAndStore=	SplashActivity.mStoreendpoint.getStoreOrdersForCurrentUserAndStore(FIRST_POSITION,MAX_RESULT,AsaanUtility.selectedStore.getId());
			 HttpHeaders httpHeaders = getStoreOrdersForCurrentUserAndStore.getRequestHeaders();
			 httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
			storeOrderListAndCount= getStoreOrdersForCurrentUserAndStore.execute();
			if(storeOrderListAndCount!=null)
				Log.e("store_list_count",storeOrderListAndCount.toPrettyString());
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
				
			}
				
			super.onPostExecute(result);
		}
		
	}
}
