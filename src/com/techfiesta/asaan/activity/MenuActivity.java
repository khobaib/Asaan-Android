package com.techfiesta.asaan.activity;

import java.io.IOException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenusAndMenuItems;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.MyTabListener;

public class MenuActivity extends Activity {
	private static String TAG_TAB_PREFIX = "Menu_Tab_For_";

	public static MenusAndMenuItems menusAndMenuItems;

	private long INITIAL_POSITION = 0;
	private int MAX_RESULT = 20;
	private long storeId = -1;

	ActionBar actionBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		setContentView(R.layout.activity_menu);

		storeId = AsaanUtility.selectedStore.getId();
		new GetMenu().execute();
	}

	private class GetMenu extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				menusAndMenuItems = AsaanMainActivity.mStoreendpoint.getStoreMenuHierarchyAndItems(storeId,
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
