package com.techfiesta.asaan.activity;

import java.io.IOException;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techfiesta.asaan.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class StoreDetailsActivity  extends FragmentActivity{
	
	private Store store;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);
		getIntentData();
	}
	private void getIntentData()
	{
		Intent intent=getIntent();
		String json=intent.getStringExtra("store");
		Log.e("store",json);
		
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			store=objectMapper.readValue(json,Store.class);
		} catch (JsonParseException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		Log.e("store",store.getName());
	}

}
