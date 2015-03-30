package com.techfiesta.asaan.activity;

import java.io.IOException;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenusAndMenuItems;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.MyTabListener;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.DaoMaster.OpenHelper;

public class MenuActivityNew  extends Activity{
	
	private int MAX_RESULT=50;
	private long  storeId=11;
	public static MenusAndMenuItems menusAndMenuItems;
	private ActionBar actionBar;
	private ProgressDialog pdDialog;
	private int order_type=-1;
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_new);
		actionBar=getActionBar();
		actionBar.setTitle("Back");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		pdDialog=new ProgressDialog(MenuActivityNew.this);
		getOrderType();
		new GetMenu().execute();
	}
	private void getOrderType()
	{
		Intent intent=getIntent();
		if(intent!=null)
		{
			order_type=intent.getIntExtra(Constants.ORDER_TYPE,-1);
		}
	}
	private class GetMenu extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdDialog.setMessage("loading......");
			pdDialog.show();
		}
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
					bundle.putInt(Constants.ORDER_TYPE,order_type);
					MyTabListener<MenuItemsFragment> tabListener = new MyTabListener<MenuItemsFragment>(
							MenuActivityNew.this,smh.getName(), MenuItemsFragment.class, bundle);
					Tab tab = actionBar.newTab().setText(smh.getName()).setTabListener(tabListener);
					actionBar.addTab(tab);
				}
				if(pdDialog.isShowing())
				   pdDialog.dismiss();
			}
			super.onPostExecute(result);
		}

	}
	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		initDatabase();
		if(addItemDao.count()>0)
			getMenuInflater().inflate(R.menu.activity_menu, menu);
		else
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==R.id.action_cart)
		{
			Intent intent=new Intent(MenuActivityNew.this,MyCartActivity.class);
			startActivity(intent);
		}
		return true;
	}

}
