package com.techfiesta.asaan.activity;





import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.CustomAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ResturantListActivity extends FragmentActivity {
	private GoogleMap mMap;
	protected Marker mMarker;
	Context mContext;
	Location mLocation;
	boolean isLocation;
	private ListView resListView;
	private CustomAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		

		View viewToLoad = LayoutInflater.from(ResturantListActivity.this).inflate(R.layout.activity_restaurant_list, null);
		ResturantListActivity.this.setContentView(viewToLoad);
		setupMap();
		//setContentView(R.layout.activity_restaurant_list);
		
		adapter = new CustomAdapter(ResturantListActivity.this);
		resListView = (ListView) findViewById(R.id.lvRestaurantList);
		resListView.setAdapter(adapter);
		adapter.loadObjects();
		
		


	}
	
	private void setupMap(){
		
		if(mMap==null){
			

			mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap.getUiSettings().setZoomControlsEnabled(false);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setMyLocationEnabled(true);
			mMap.getUiSettings().setZoomControlsEnabled(true);
			
			isLocation= setUserLocation();
			
			
			
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	//fetch all resturants info here
	
	private boolean setUserLocation() {
		if(AsaanUtility.hasInternet(ResturantListActivity.this)){
			AsaanUtility.checkLocationAccess(ResturantListActivity.this);
			if(AsaanUtility.getLocation()){
				mLocation = AsaanUtility.mLocation;
				if(mLocation!=null){
				Log.d(">>>>", "location="+mLocation.getLatitude());
				 LatLng mlatLong = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
			        mMarker = mMap.addMarker(new MarkerOptions().position(mlatLong).title("Hey!"));
			        
			            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mlatLong, 14));
			            return true;
			        }else{
			        	Log.d(">>>>>", "Couldn't get location");
			        	return false;
			        }
			}
		}else AsaanUtility.simpleAlert(ResturantListActivity.this, "Please check your internet connection");
		return false;
		
	}
	
	public float getDistance(Location storeLocation){
		return mLocation.distanceTo(storeLocation);
	}


	
	
		
}
	
	



