package com.techfiesta.asaan.fragment;

import java.io.IOException;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetOrderReviewsForStore;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.OrderReviewListAndCount;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.utility.AsaanUtility;


public class ReviewFragment extends Fragment {
	private int FIRST_POSITION=0;
	private int MAX_RESULT=50;
	private String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pdDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_restaurant_reviews4, container, false);
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		pdDialog=new ProgressDialog(getActivity());
		pdDialog.setMessage("Please wait...");
		if(AsaanUtility.hasInternet(getActivity()))
			   new GetReviewsFromServer().execute();
			else
				AsaanUtility.simpleAlert(getActivity(),"Please check your internet connection.");
	}
	 private class GetReviewsFromServer extends AsyncTask<Void,Void,Void>
	 {
		 private boolean error=false;

		 @Override
		protected void onPreExecute() {
			pdDialog.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			      try {
				GetOrderReviewsForStore getOrderReviewsForStore=SplashActivity.mStoreendpoint.getOrderReviewsForStore(FIRST_POSITION,MAX_RESULT,AsaanUtility.selectedStore.getId());
				HttpHeaders httpHeaders = getOrderReviewsForStore.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				OrderReviewListAndCount orderReviewListAndCount = getOrderReviewsForStore.execute();
				  if(orderReviewListAndCount!=null)
				     Log.e("MSG",getOrderReviewsForStore.toString());
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
			if(error)
			 AsaanUtility.simpleAlert(getActivity(),"An error occured.");
			else
			{
				
			}
		}
		 
	 }

}
