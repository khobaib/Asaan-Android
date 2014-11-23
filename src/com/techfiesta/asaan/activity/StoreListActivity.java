package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
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
import android.widget.Toast;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DStore;
import asaan.dao.DStoreDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;
import asaan.dao.Trophies;
import asaan.dao.TrophiesDao;
import asaan.dao.DaoMaster.OpenHelper;


import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreCollection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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

public class StoreListActivity extends FragmentActivity implements 
		GooglePlayServicesClient.OnConnectionFailedListener,LocationListener,OnMarkerClickListener,OnInfoWindowClickListener,GooglePlayServicesClient.ConnectionCallbacks{
	private GoogleMap mMap;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	int test_count=0;

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
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private DStoreDao dStoreDao;
	private TrophiesDao trophiesDao;
	private LocationClient locationClient;
	private LocationRequest locationRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
     //init database
		initDatabase();
		Log.e("stop", "oncreate");
		View viewToLoad = LayoutInflater.from(StoreListActivity.this).inflate(R.layout.activity_restaurant_list,
				null);
		StoreListActivity.this.setContentView(viewToLoad);

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
				Intent intent = new Intent(StoreListActivity.this, StoreDetailsActivityNew.class);
				// intent.putExtra("store",json);

				startActivity(intent);

			}
		});

		if(isUpDatedInLast24Hours())
		{
			//load from local db
			Log.e("status","loading from local db");
			loadStoresFromDatabase();
			if (mLocation == null) {
				storeListAdapter = new StoreListAdapter(StoreListActivity.this, storeList);
			} else {
				storeListAdapter = new StoreListAdapter(StoreListActivity.this, storeList, mLocation);
			}
			storeListView.setAdapter(storeListAdapter);
			setupMarker();
			
		}
		else
		{
			Log.e("status","loading from from server");
		new GetStroreInfoFromServer().execute();
		}

	}
	private void initDatabase()
	{
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        dStoreDao = daoSession.getDStoreDao();
		trophiesDao=daoSession.getTrophiesDao();
	}

	private boolean isUpDatedInLast24Hours()
	{
		long lastTime=AsaanUtility.getlastUpdatedTime(StoreListActivity.this);
		long diff=System.currentTimeMillis()-lastTime;
		if(diff>(24*60*60*1000))
		{
			return false;
		}
		else
			return true;
	}
	private void loadStoresFromDatabase()
	{
		List<Store> tempStoreList=new ArrayList<Store>();
		List<DStore> dstList=dStoreDao.queryBuilder().list();
		for(int i=0;i<dstList.size();i++)
		{
			
		    DStore dStore=dstList.get(i);
			Store store=new Store();
			store.setAddress(dStore.getAddress());
			store.setBackgroundImageUrl(dStore.getBackgroundImageUrl());
			store.setBackgroundThumbnailUrl(dStore.getBackgroundThumbnailUrl());
			store.setBeaconId(dStore.getBeaconId());
			store.setBssid(dStore.getBssid());
			store.setCity(dStore.getCity());
			store.setCreatedDate(dStore.getCreatedDate());
			store.setDeliveryDistance(dStore.getDeliveryDistance());
			store.setDescription(dStore.getDescription());
			store.setFbUrl(dStore.getFbUrl());
			store.setGplusUrl(dStore.getGplusUrl());
			store.setHours(dStore.getHours());
			Log.e("address",dStore.getAddress());
			store.setId(dStore.getId());
			store.setIsActive(dStore.getIsActive());
			store.setLat(dStore.getLat());
			store.setLng(dStore.getLng());
			store.setModifiedDate(dStore.getModifiedDate());
			store.setName(dStore.getName());
			store.setPhone(dStore.getPhone());
			store.setPriceRange(dStore.getPriceRange());
			store.setProvidesCarryout(dStore.getProvidesCarryout());
			store.setProvidesDelivery(dStore.getProvidesDelivery());
			store.setRewardsDescription(dStore.getRewardsDescription());
			store.setRewardsRate(dStore.getRewardsRate());
			store.setSsid(dStore.getSsid());
			store.setState(dStore.getState());
			store.setSubType(dStore.getSubType());
			store.setTwitterUrl(dStore.getTwitterUrl());
			store.setType(dStore.getType());
			store.setWebSiteUrl(dStore.getWebSiteUrl());
			store.setZip(dStore.getZip());
			
			List<Trophies> trophies=dStore.getTrophiesList();
			List<String> list=new ArrayList<>();
			if(trophies!=null)
			{
				for(int j=0;j<trophies.size();j++)
					list.add(trophies.get(j).getName());
					
			}
			store.setTrophies(list);
			tempStoreList.add(store);
			
		}
		storeList=tempStoreList;
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
			if (mLocation == null) {
				storeListAdapter = new StoreListAdapter(StoreListActivity.this, storeList);
			} else {
				storeListAdapter = new StoreListAdapter(StoreListActivity.this, storeList, mLocation);
			}
			storeListView.setAdapter(storeListAdapter);
			saveStoreToDatabase();
			setupMarker();
			super.onPostExecute(result);
		}
		private void saveStoreToDatabase()
		{
			dStoreDao.deleteAll();
			trophiesDao.deleteAll();
			for(int i=0;i<storeList.size();i++)
			{
				Store store=storeList.get(i);
				DStore dStore=new DStore();
				dStore.setAddress(store.getAddress());
				dStore.setBackgroundImageUrl(store.getBackgroundImageUrl());
				dStore.setBackgroundThumbnailUrl(store.getBackgroundThumbnailUrl());
				dStore.setBeaconId(store.getBeaconId());
				dStore.setBssid(store.getBssid());
				dStore.setCity(store.getCity());
				dStore.setCreatedDate(store.getCreatedDate());
				dStore.setDeliveryDistance(store.getDeliveryDistance());
				dStore.setDescription(store.getDescription());
				dStore.setFbUrl(store.getFbUrl());
				dStore.setGplusUrl(store.getGplusUrl());
				dStore.setHours(store.getHours());
				Log.e("address",store.getAddress());
				dStore.setId(store.getId());
				dStore.setIsActive(store.getIsActive());
				dStore.setLat(store.getLat());
				dStore.setLng(store.getLng());
				dStore.setModifiedDate(store.getModifiedDate());
				dStore.setName(store.getName());
				dStore.setPhone(store.getPhone());
				dStore.setPriceRange(store.getPriceRange());
				dStore.setProvidesCarryout(store.getProvidesCarryout());
				dStore.setProvidesDelivery(store.getProvidesDelivery());
				dStore.setRewardsDescription(store.getRewardsDescription());
				dStore.setRewardsRate(store.getRewardsRate());
				dStore.setSsid(store.getSsid());
				dStore.setState(store.getState());
				dStore.setSubType(store.getSubType());
				dStore.setTwitterUrl(store.getTwitterUrl());
				dStore.setType(store.getType());
				dStore.setWebSiteUrl(store.getWebSiteUrl());
				dStore.setZip(store.getZip());

				dStoreDao.insert(dStore);
				if(store.getTrophies()!=null)
				{
					for(int j=0;j<store.getTrophies().size();j++)
					{
						long rownum=trophiesDao.insert(new Trophies(store.getTrophies().get(j),store.getId()));
						Log.e("status",rownum+"");
					}
				}
			}
			AsaanUtility.seLastUpDatedTime(StoreListActivity.this,System.currentTimeMillis());


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

			//isLocation = setUserLocation();

		}

		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		startLocationTracking();
		//locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

	}
	private void startLocationTracking()
	{
		locationClient=new LocationClient(this,this, this);
		locationRequest=LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 10 seconds
		locationRequest.setInterval(10000);
		// Set the fastest update interval to 3 second
		locationRequest.setFastestInterval(3000);
		//locationRequest.setSmallestDisplacement(MIN_DISTANCE);
		locationClient.connect();
		
		
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

	private boolean setUserLocation() {
		if (AsaanUtility.hasInternet(StoreListActivity.this)) {
			AsaanUtility.checkLocationAccess(StoreListActivity.this);
			if (AsaanUtility.getLocation()) {
				mLocation = AsaanUtility.mLocation;
				if (mLocation != null) {
					Log.d(">>>>", "location=" + mLocation.getLatitude());
					// LatLng mlatLong = new LatLng(mLocation.getLatitude(),
					// mLocation.getLongitude());
					// mMarker = mMap.addMarker(new
					// MarkerOptions().position(mlatLong).title("Hey!"));
					//
					// mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mlatLong,
					// 14));
					return true;
				} else {
					Log.d(">>>>>", "Couldn't get location");
					return false;
				}
			}
		} else
			AsaanUtility.simpleAlert(StoreListActivity.this, "Please check your internet connection");
		return false;
	}

	public float getDistance(Location storeLocation) {
		return mLocation.distanceTo(storeLocation);
	}
	@Override
	public void onLocationChanged(Location location) {
		
		test_count=test_count+1;
		Toast.makeText(StoreListActivity.this, "location changed.", Toast.LENGTH_SHORT).show();
		AsaanUtility.mLocation=location;
		
		mLocation=location;
		storeListAdapter.setLocation(mLocation);
		
		storeListAdapter.notifyDataSetChanged();
		
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
		mMap.animateCamera(cameraUpdate);


		
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void onDestroy() {
		if(locationClient!=null && locationRequest!=null)
			locationClient.removeLocationUpdates(this);
		super.onDestroy();
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		locationClient.requestLocationUpdates(locationRequest,this);
		
	}
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	
}
