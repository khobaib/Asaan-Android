package com.techfiesta.asaan.activity;


import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class LocationActivity extends Activity {
	private GoogleMap map;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		
		actionBar=getActionBar();
		actionBar.setTitle("Back");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.mapRestaurantLocation);
		map=mapFragment.getMap();
	}
	private void setUpMap()
	{
		Store store=AsaanUtility.selectedStore;
		double lat=store.getLat();
		double lng=store.getLng();
		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(15)
				.build();
	
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(store.getName());
		map.addMarker(marker);
	}

}
