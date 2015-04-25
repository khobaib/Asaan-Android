package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetOrderReviewsForStore;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetStoreOrdersForCurrentUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetStoreOrdersForCurrentUserAndStore;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.OrderReviewListAndCount;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrderListAndCount;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.OrderHistoryDetailsActivity;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.adapter.OrderHistoryAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AllHistoryFragment extends Fragment{
	
	private int FIRST_POSITION=0;
	private int MAX_RESULT=50;
	private TextView tvName;
	private String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pdDialog;
	private OrderHistoryAdapter orderHistoryAdapter=null;
	private List<StoreOrder> storeOrders=null;
	private ListView lvOrders;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.frag_restaurant_history4,null,false);
		tvName=(TextView)v.findViewById(R.id.txtViewResName);
		lvOrders=(ListView)v.findViewById(R.id.lstViewOrders);
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvName.setText("All Restaurants");
		pdDialog=new ProgressDialog(getActivity());
		pdDialog.setMessage("Please wait...");
		if(AsaanUtility.hasInternet(getActivity()))
			new GetAllOrdersForCurrentUserFromServer().execute();
		else
			AsaanUtility.simpleAlert(getActivity(), getResources().getString(R.string.internet_alert));
		lvOrders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AsaanUtility.selectedStoreOrder=storeOrders.get(position);
				Intent intent=new Intent(getActivity(),OrderHistoryDetailsActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				
			}
		});

		
	}
	
	private class GetAllOrdersForCurrentUserFromServer extends AsyncTask<Void,Void,Void>
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
		GetStoreOrdersForCurrentUser getStoreOrdersForCurrentUser=SplashActivity.mStoreendpoint.getStoreOrdersForCurrentUser(FIRST_POSITION,MAX_RESULT);
			 HttpHeaders httpHeaders = getStoreOrdersForCurrentUser.getRequestHeaders();
			 httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
			storeOrderListAndCount= getStoreOrdersForCurrentUser.execute();
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
				storeOrders=storeOrderListAndCount.getOrders();
				setUpAdapter();
			}
				
			super.onPostExecute(result);
		}
		
	}
	private void setUpAdapter()
	{
		if(storeOrders!=null)
		{
		orderHistoryAdapter=new OrderHistoryAdapter(getActivity(), storeOrders);
		lvOrders.setAdapter(orderHistoryAdapter);
		}
	}
}
