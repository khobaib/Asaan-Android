package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
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
import com.techfiesta.asaan.fragment.StoreListFragment;
import com.techfiesta.asaan.model.NavMenuItem;
import com.techfiesta.asaan.utility.AsaanApplication;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class StoreListActivity extends FragmentActivity {
	


	Context mContext;
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
		}
		
		setDrawerListItems();
		navDrawerAdapter=new NavDrawerAdapter(StoreListActivity.this,R.layout.nav_menu_row,drawerItemList);
		mDrawerList.setAdapter(navDrawerAdapter);
		
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				
			}
		});
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.sliding_menu_bg, // nav menu toggle icon
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
		
		StoreListFragment strFragment=new StoreListFragment();
		FragmentTransaction  ft=getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container,strFragment);
		ft.addToBackStack(null);
		ft.commit();
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
