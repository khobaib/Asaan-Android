package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreCollection;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.StoreListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ResturantListActivity extends FragmentActivity implements LocationListener, OnMarkerClickListener,
		OnInfoWindowClickListener {
	private GoogleMap mMap;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;

	private HashMap<Marker, Store> restaurantMarkerMap;

	protected Marker mMarker;
	Context mContext;
	Location mLocation;
	boolean isLocation;
	private ListView storeListView;
	private StoreListAdapter storeListAdapter;
	private StoreCollection storeCollection;
	private List<Store> storeList;
	private int INITIAL_POSITION = 0;
	private int MAX_RESULT = 10;
	private TextView tvBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();

		Log.e("stop", "oncreate");
		View viewToLoad = LayoutInflater.from(ResturantListActivity.this).inflate(R.layout.activity_restaurant_list,
				null);
		ResturantListActivity.this.setContentView(viewToLoad);

		tvBack = (TextView) findViewById(R.id.tvBack);
		tvBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		setupMap();

		storeListView = (ListView) findViewById(R.id.lvRestaurantList);
		storeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Store store = storeList.get(position);
				// String json=convertModelStoreToJsonStrig(store);
				AsaanUtility.selectedStore = store;
				Intent intent = new Intent(ResturantListActivity.this, StoreDetailsActivity.class);
				// intent.putExtra("store",json);

				startActivity(intent);

			}
		});

		new GetStroreInfoFromServer().execute();

	}

	// private String convertModelStoreToJsonStrig(Store store) {
	// ObjectMapper objectMapper = new ObjectMapper();
	// // objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
	// StringWriter stringWriter = new StringWriter();
	// try {
	// objectMapper.writeValue(stringWriter, store);
	// } catch (JsonGenerationException e) {
	//
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// return stringWriter.toString();
	// }

	private void getStoreInfo() {

	}

	private class GetStroreInfoFromServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				storeCollection = AsaanMainActivity.mStoreendpoint.getStores(INITIAL_POSITION, MAX_RESULT).execute();
				Log.e("StoreList Size", "" + storeCollection.getItems().size());
			} catch (IOException e) {

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// setting list
			storeList = storeCollection.getItems();
			storeListAdapter = new StoreListAdapter(ResturantListActivity.this, storeList);
			storeListView.setAdapter(storeListAdapter);
			setupMarker();
			super.onPostExecute(result);
		}

	}

	private void setupMap() {

		// if (mMap == null) {
		// // Try to obtain the map from the SupportMapFragment.
		// mMap = ((SupportMapFragment)
		// getSupportFragmentManager().findFragmentById(R.id.map))
		// .getMap();
		// // Check if we were successful in obtaining the map.
		// if (mMap != null) {
		// setUpMap();
		// }
		// }

		if (mMap == null) {

			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap.getUiSettings().setZoomControlsEnabled(false);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setMyLocationEnabled(true);
			mMap.getUiSettings().setZoomControlsEnabled(true);

			isLocation = setUserLocation();

		}

		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void setupMarker() {
		restaurantMarkerMap = new HashMap<Marker, Store>();
		Log.d(">>>>>>>", "-------- how many RESTAURANTS? = " + storeList.size());
		for (int i = 0; i < storeList.size(); i++) {
			Store store = storeList.get(i);
			LatLng restaurantPosition = new LatLng(store.getLat(), store.getLng());
			Marker marker = mMap.addMarker(new MarkerOptions().position(restaurantPosition).title(store.getName()));
			restaurantMarkerMap.put(marker, store);
		}
	}

	// fetch all resturants info here

	private boolean setUserLocation() {
		if (AsaanUtility.hasInternet(ResturantListActivity.this)) {
			AsaanUtility.checkLocationAccess(ResturantListActivity.this);
			if (AsaanUtility.getLocation()) {
				mLocation = AsaanUtility.mLocation;
				if (mLocation != null) {
					Log.d(">>>>", "location=" + mLocation.getLatitude());
					LatLng mlatLong = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
					mMarker = mMap.addMarker(new MarkerOptions().position(mlatLong).title("Hey!"));

					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mlatLong, 14));
					return true;
				} else {
					Log.d(">>>>>", "Couldn't get location");
					return false;
				}
			}
		} else
			AsaanUtility.simpleAlert(ResturantListActivity.this, "Please check your internet connection");
		return false;

	}

	public float getDistance(Location storeLocation) {
		return mLocation.distanceTo(storeLocation);
	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
		mMap.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);
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

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		AsaanUtility.selectedStore = restaurantMarkerMap.get(marker);
		Intent intent = new Intent(ResturantListActivity.this, StoreDetailsActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	// @Override
	// public void onConnectionFailed(ConnectionResult result) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onConnected(Bundle connectionHint) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onDisconnected() {
	// // TODO Auto-generated method stub
	//
	// }

}
