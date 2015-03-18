package com.techfiesta.asaan.activity;

import java.io.IOException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenusAndMenuItems;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.MyTabListener;

public class MenuActivity extends Activity {
	private static String TAG_TAB_PREFIX = "Menu_Tab_For_";

	public static MenusAndMenuItems menusAndMenuItems;
	public static StoreMenuItem selectedMenuItem = null;

	private long INITIAL_POSITION = 0;
	private int MAX_RESULT = 20;
	private long storeId = -1;
	private Bundle bundle=new Bundle();

	ActionBar actionBar = null;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		setContentView(R.layout.activity_menu);
		initDatabase();
		storeId = AsaanUtility.selectedStore.getId();
		new GetMenu().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_cart:
			if (addItemDao.count() <= 0) {
				AsaanUtility.simpleAlert(MenuActivity.this, "You have no order");
			} else {
				Intent intent = new Intent(MenuActivity.this, MyCartActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		// modItemDao = daoSession.getModItemDao();
	}

	private class GetMenu extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				menusAndMenuItems = SplashActivity.mStoreendpoint.getStoreMenuHierarchyAndItems(storeId,
						Constants.MENU_TYPE_DINE_IN, MAX_RESULT).execute();
				Log.e("menu_size", "" + menusAndMenuItems.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			for (StoreMenuHierarchy smh : menusAndMenuItems.getMenusAndSubmenus()) {
				if (smh.getLevel() == 0) // Menu Level
				{
					Bundle bundle = new Bundle();
					bundle.putLong(Constants.BUNDLE_KEY_MENU_ID, smh.getMenuPOSId());
					MyTabListener<MenuItemsFragment> tabListener = new MyTabListener<MenuItemsFragment>(
							MenuActivity.this, TAG_TAB_PREFIX + smh.getName(), MenuItemsFragment.class, bundle);
					Tab tab = actionBar.newTab().setText(smh.getName()).setTabListener(tabListener);
					actionBar.addTab(tab);
				}
			}
			super.onPostExecute(result);
		}

	}

}
