package com.techfiesta.asaan.activity;





import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.CustomAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ResturantListActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener,LocationListener{
	private GoogleMap mMap;
	protected Marker mMarker;
	Context mContext;
	Location mLocation;
	boolean isLocation;
	private ListView resListView;
	private CustomAdapter adapter;
	private List<Store> storeList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		Log.e("stop","oncreate");
		View viewToLoad = LayoutInflater.from(ResturantListActivity.this).inflate(R.layout.activity_restaurant_list, null);
		ResturantListActivity.this.setContentView(viewToLoad);
		setupMap();
		//setContentView(R.layout.activity_restaurant_list);
		//stores = new ArrayList<Store>();
		//adapter = new CustomAdapter(ResturantListActivity.this);
		resListView = (ListView) findViewById(R.id.lvRestaurantList);
		//resListView.setAdapter(adapter);
		//adapter.loadObjects();
		
		//fetchStoreinfo();  //it did not work either
		
		new GetStroreInfoFromServer().execute();


	}
	
	/*public void getStoreImage(final ParseObject ob){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("StoreImages");
        
		  
		  query.whereEqualTo("store",ob);
		  
		  query.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject imageObject, ParseException e) {
				if(e==null){
					
					Store tmpStore = new Store(ob, imageObject.getParseFile("image"));
					Log.d("SUCESS", "got storeimage file" +imageObject.getParseFile("image").getName());
					stores.add(tmpStore);
				}else{
					Log.e("??", "Couldn't retrive image");
				}
				
			}
		});
	}
	
	private void fetchStoreinfo(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> storeObjects, ParseException e) {
				if(e==null){
					Log.d("SUCCESS", "got store object"+storeObjects.size());
					for(int i=0; i<storeObjects.size(); i++){
						Log.e("STORE", storeObjects.get(i).getObjectId());
						getStoreImage(storeObjects.get(i));
					}
					/*for(ListIterator<ParseObject> li = storeObjects.listIterator();li.hasNext();){
						if(li.next()!=null){
							Log.e("STORE", li.next().getObjectId());
							getStoreImage(li.next());
						}
					}*/
			/*	}else{
					
				//}
				
			}
		});
			
		
	}*/
	private void getStoreInfo()
	{
		
	}
	private class GetStroreInfoFromServer extends AsyncTask<Void,Void,Void>
	{
		

		
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Log.e("stop","here");
				storeList=AsaanMainActivity.mStoreendpoint.getStores().execute().getItems();
				Log.e("Size",""+storeList.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
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

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}


	
	
		
}
	
	



