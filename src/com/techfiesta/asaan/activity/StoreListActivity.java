package com.techfiesta.asaan.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;

import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.NavDrawerAdapter;
import com.techfiesta.asaan.fragment.ChatHistoryFragment;
import com.techfiesta.asaan.fragment.PendingOrderFragment;
import com.techfiesta.asaan.fragment.ProfileFragment;
import com.techfiesta.asaan.fragment.StoreListFragment;
import com.techfiesta.asaan.fragment.WaitListStatusFragment;
import com.techfiesta.asaan.model.NavMenuItem;
import com.techfiesta.asaan.utility.AsaanApplication;

public class StoreListActivity extends FragmentActivity {
	


	Context mContext;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerAdapter navDrawerAdapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<NavMenuItem> drawerItemList;
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	private AddItem addItem;
	
	AsaanApplication appInstance;
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
			
		}
	};

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.activity_store_list);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		mDrawerList.setFitsSystemWindows(true);
		if(getActionBar()!=null)
		{
		getActionBar().setTitle("");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		}
		
		setDrawerListItems();
		
		
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				boolean closeDrawer=true;
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				if (position == 0) {
					StoreListFragment strFragment = new StoreListFragment();
					ft.replace(R.id.frame_container, strFragment);
				} else if (position == 1) {
					ProfileFragment profileFragment = new ProfileFragment();
					ft.replace(R.id.frame_container, profileFragment);

				} 
				else if (position == 2) {
					ChatHistoryFragment chatHistoryFragment=new ChatHistoryFragment();
					ft.replace(R.id.frame_container, chatHistoryFragment);

				}else if (position == 3) {

					WaitListStatusFragment waitListStatusFragment=new WaitListStatusFragment();
					ft.replace(R.id.frame_container, waitListStatusFragment);
				} else if (position == 4) {
					closeDrawer = checkPendingOrders();
					if (closeDrawer) {
						PendingOrderFragment pFragment = new PendingOrderFragment();
						ft.replace(R.id.frame_container, pFragment);
					}

				} else if (position == 6) {

					ParseUser.logOut();
					Intent intent = new Intent(StoreListActivity.this, LoginChooserActivity.class);
					
					startActivity(intent);
					intent=new Intent(getResources().getString(R.string.intent_filter_finish));
					sendBroadcast(intent);
				}
				ft.addToBackStack(null);
				ft.commit();
				if(closeDrawer)
				   mDrawerLayout.closeDrawer(Gravity.LEFT);
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
		//ft.addToBackStack(null);
		ft.commit();
		
	}
	private boolean checkPendingOrders()
	{
		if(addItemDao.count()>0)
			 return true;
		else
			return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		initDatabase();
		registerReceiver(broadcastReceiver,new IntentFilter(getResources().getString(R.string.intent_filter_finish)));
		Log.e("MSG", "on resume");
		navDrawerAdapter=new NavDrawerAdapter(StoreListActivity.this,R.layout.nav_menu_row,drawerItemList);
		mDrawerList.setAdapter(navDrawerAdapter);
		invalidateOptionsMenu();
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
	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		
	}
	private void closeDatabase()
	{
		daoMaster.getDatabase().close();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		if(addItemDao.count()>0)
			getMenuInflater().inflate(R.menu.activity_menu, menu);
		else
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
		if(item.getItemId()==R.id.action_cart)
		{
			Intent intent=new Intent(StoreListActivity.this,MyCartActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			
		}
		return true;
	}
	@Override
	protected void onStop() {
		closeDatabase();
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}
}
