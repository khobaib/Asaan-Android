package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import asaan.dao.DStore;
import asaan.dao.DStoreDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.Trophies;
import asaan.dao.TrophiesDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreCollection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.NavDrawerAdapter;
import com.techfiesta.asaan.adapter.StoreListAdapter;
import com.techfiesta.asaan.model.NavMenuItem;
import com.techfiesta.asaan.utility.AsaanApplication;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class StoreListActivity extends FragmentActivity {
	


	Context mContext;
	private ListView storeListView;
	private StoreListAdapter storeListAdapter;
	private StoreCollection storeCollection;
	private List<Store> storeList;
	private int INITIAL_POSITION = 0;
	private int MAX_RESULT = 10;
	private int ONE_DAY_DELAY = 24 * 60 * 60 * 1000;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private DStoreDao dStoreDao;
	private TrophiesDao trophiesDao;
	
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerAdapter navDrawerAdapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<NavMenuItem> drawerItemList;

	AsaanApplication appInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.activity_store_list);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		if(getActionBar()!=null)
		{
		getActionBar().setTitle("");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		ImageView imageView = (ImageView) findViewById(android.R.id.home);
		imageView.setPadding(-12,-12,-12,-12);
		}
		
		setDrawerListItems();
		navDrawerAdapter=new NavDrawerAdapter(StoreListActivity.this,R.layout.nav_menu_row,drawerItemList);
		mDrawerList.setAdapter(navDrawerAdapter);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.slidingmenu, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();
		// init database
		/*initDatabase();
		Log.e("stop", "oncreate");
		View viewToLoad = LayoutInflater.from(StoreListActivity.this).inflate(R.layout.fragment_store_list, null);
		StoreListActivity.this.setContentView(viewToLoad);

		appInstance = (AsaanApplication) getApplication();

		storeListView = (ListView) findViewById(R.id.lvRestaurantList);
		storeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Store store = storeList.get(position);
				// String json=convertModelStoreToJsonStrig(store);
				AsaanUtility.selectedStore = store;
				appInstance.setPARENT_PAGE(Constants.FROM_STORE_LIST);
				Intent intent = new Intent(StoreListActivity.this, StoreDetailsActivityNew.class);
				// intent.putExtra("store",json);

				startActivity(intent);

			}
		});

		if (isUpDatedInLast24Hours()) {
			// load from local db
			Log.e("status", "loading from local db");
			loadStoresFromDatabase();
			
			storeListAdapter = new StoreListAdapter(StoreListActivity.this, storeList);
			
			storeListView.setAdapter(storeListAdapter);

		} else {
			Log.e("status", "loading from from server");
			new GetStroreInfoFromServer().execute();
		}*/
		

	}

	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		dStoreDao = daoSession.getDStoreDao();
		trophiesDao = daoSession.getTrophiesDao();
	}

	private boolean isUpDatedInLast24Hours() {
		long lastTime = AsaanUtility.getlastUpdatedTime(StoreListActivity.this);
		long diff = System.currentTimeMillis() - lastTime;
		if (diff > ONE_DAY_DELAY) {
			return false;
		} else
			return true;
	}

	private void loadStoresFromDatabase() {
		List<Store> tempStoreList = new ArrayList<Store>();
		List<DStore> dstList = dStoreDao.queryBuilder().list();
		for (int i = 0; i < dstList.size(); i++) {

			DStore dStore = dstList.get(i);
			Store store = new Store();
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
			Log.e("address", dStore.getAddress());
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

			List<Trophies> trophies = dStore.getTrophiesList();
			List<String> list = new ArrayList<>();
			if (trophies != null) {
				for (int j = 0; j < trophies.size(); j++)
					list.add(trophies.get(j).getName());

			}
			store.setTrophies(list);
			tempStoreList.add(store);

		}
		storeList = tempStoreList;
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
	private class GetStroreInfoFromServer extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				storeCollection = SplashActivity.mStoreendpoint.getStores(INITIAL_POSITION, MAX_RESULT).execute();
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
			
			storeListAdapter = new StoreListAdapter(StoreListActivity.this, storeList);
			
			storeListView.setAdapter(storeListAdapter);
			saveStoreToDatabase();
			super.onPostExecute(result);
		}

		private void saveStoreToDatabase() {
			dStoreDao.deleteAll();
			trophiesDao.deleteAll();
			for (int i = 0; i < storeList.size(); i++) {
				Store store = storeList.get(i);
				DStore dStore = new DStore();
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
				Log.e("address", store.getAddress());
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
				if (store.getTrophies() != null) {
					for (int j = 0; j < store.getTrophies().size(); j++) {
						long rownum = trophiesDao.insert(new Trophies(store.getTrophies().get(j), store.getId()));
						Log.e("status", rownum + "");
					}
				}
			}
			AsaanUtility.seLastUpDatedTime(StoreListActivity.this, System.currentTimeMillis());

		}

	}

	

	
	@Override
	protected void onResume() {
		super.onResume();
		Log.e("MSG", "on resume");
		// setupMap();

	}
	private void setDrawerListItems()
	{
		drawerItemList=new ArrayList<NavMenuItem>();
		drawerItemList.add(new NavMenuItem("Stores",R.drawable.stores));
		drawerItemList.add(new NavMenuItem("Profile",R.drawable.profile));
		drawerItemList.add(new NavMenuItem("Chat History",R.drawable.chathistory));
		drawerItemList.add(new NavMenuItem("Wait List Status",R.drawable.tableseated));
		drawerItemList.add(new NavMenuItem("Pending Orders",R.drawable.pendingorders));
		drawerItemList.add(new NavMenuItem("Order History",R.drawable.orderhistory));
		drawerItemList.add(new NavMenuItem("Logout",R.drawable.logout));
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return true;
	}

}
