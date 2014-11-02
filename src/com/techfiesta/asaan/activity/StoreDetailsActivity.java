package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreImage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreImageCollection;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.InfoFragment;
import com.techfiesta.asaan.utility.AsaanUtility;

public class StoreDetailsActivity extends FragmentActivity implements OnClickListener {

	private Store store;
	private Button btnInfo, btnMenu, btnReviews, btnHistory;
	private TextView tvBack;
	private List<StoreImage> storeImageList;
	private StoreImageCollection storeImageCollection;
	private int INITIAL_POSITION = 0;
	private int MAX_RESULT = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);
		tvBack = (TextView) findViewById(R.id.tvBack);
		tvBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnInfo = (Button) findViewById(R.id.btn_info);
		btnMenu = (Button) findViewById(R.id.btn_menu);
		btnHistory = (Button) findViewById(R.id.btn_history);
		btnReviews = (Button) findViewById(R.id.btn_reviews);
		store = AsaanUtility.selectedStore;
		new GetStoreImages().execute();
		loadFisrtFragment();
		btnHistory.setOnClickListener(this);
		btnInfo.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		btnReviews.setOnClickListener(this);
	}

	private void loadFisrtFragment() {
		InfoFragment infoFragment = new InfoFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.content_frame, infoFragment);
		fragmentTransaction.commit();

	}

	private class GetStoreImages extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Log.e("storeid",""+store.getId());
				storeImageCollection=AsaanMainActivity.mStoreendpoint.getStoreImages(store.getId(),INITIAL_POSITION,MAX_RESULT).execute();
				//if(storeImageCollection!=null)
					//Log.e("storeimage Size",""+storeImageCollection.getItems().size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
		}
		
	}
	private void getIntentData() {
		Intent intent = getIntent();
		String json = intent.getStringExtra("store");
		Log.e("store", json);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			store = objectMapper.readValue(json.getBytes(), Store.class);
		} catch (JsonParseException e) {

			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		Log.e("store", store.getName());
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_info) {

		} else if (v.getId() == R.id.btn_history) {

		} else if (v.getId() == R.id.btn_reviews) {

		} else if (v.getId() == R.id.btn_menu) {
			Intent intent = new Intent(StoreDetailsActivity.this, MenuActivity.class);
			startActivity(intent);
		}

	}

}
