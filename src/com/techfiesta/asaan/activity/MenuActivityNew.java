package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenusAndMenuItems;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuStats;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.AsaanMenuHolder;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.MyTabListener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.DaoMaster.OpenHelper;

public class MenuActivityNew  extends Activity{
	
	private int MAX_RESULT=50;
	private long  storeId=-1;
	public static MenusAndMenuItems menusAndMenuItems;
	public static HashMap<Integer, AsaanMenuHolder> menuMap;
	private ActionBar actionBar;
	private ProgressDialog pdDialog;
	private int order_type=-1;
	private long curTime=1;
	
	
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_new);
		//setUpCustomActionBar();
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		pdDialog=new ProgressDialog(MenuActivityNew.this);
		getOrderType();
		if(menuMap==null)
			menuMap = new HashMap<Integer, AsaanMenuHolder>();
		new GetMenu().execute();
	}
	
	private void setUpCustomActionBar()
	{
		initDatabase();
		actionBar=getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setCustomView(R.layout.custom_action_bar);
		RelativeLayout relBack=(RelativeLayout)findViewById(R.id.rellay1);
		relBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ImageView ivCart=(ImageView)findViewById(R.id.iv_cart);
		ivCart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MenuActivityNew.this,MyCartActivity.class);
				startActivity(intent);
				
			}
		});
		if(addItemDao.count()<=0)
			ivCart.setVisibility(View.GONE);
		TextView tvTitle=(TextView)findViewById(R.id.tv_title);
		tvTitle.setVisibility(View.GONE);
			
			
	}
	private void getOrderType()
	{
		Intent intent=getIntent();
		if(intent!=null)
		{
			order_type=intent.getIntExtra(Constants.ORDER_TYPE,-1);
			curTime=intent.getLongExtra(Constants.ESTIMATED_TIME,-1);
			
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
				storeId=AsaanUtility.selectedStore.getId().intValue();
				menusAndMenuItems = SplashActivity.mStoreendpoint.getStoreMenuHierarchyAndItems(MAX_RESULT,
						Constants.MENU_TYPE_DINE_IN, storeId).execute();
				
				
				Log.e("menu_size", "" + menusAndMenuItems.size()+menusAndMenuItems.toPrettyString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if(menusAndMenuItems.getMenusAndSubmenus()==null)
			{
				Log.e("MSG","menu and submenu null");
			}
			 
			for (int i=0;i<menusAndMenuItems.getMenusAndSubmenus().size();i++) {
				StoreMenuHierarchy smh=menusAndMenuItems.getMenusAndSubmenus().get(i);
				if (smh.getLevel() == 0) // Menu Level
				{
					Bundle bundle = new Bundle();
					bundle.putLong(Constants.BUNDLE_KEY_MENU_ID, smh.getMenuPOSId());
					bundle.putInt(Constants.ORDER_TYPE,order_type);
					bundle.putLong(Constants.ESTIMATED_TIME,curTime);
					
					MyTabListener<MenuItemsFragment> tabListener = new MyTabListener<MenuItemsFragment>(
							MenuActivityNew.this,smh.getName(), MenuItemsFragment.class, bundle);
					Log.e("MENU",smh.getName());
					Tab tab = actionBar.newTab().setText(smh.getName()).setTabListener(tabListener);
					actionBar.addTab(tab);
				}	
				if(pdDialog.isShowing())
				   pdDialog.dismiss();
			}			
			for (int i=0;i<menusAndMenuItems.getMenusAndSubmenus().size();i++) 
			{
				StoreMenuHierarchy smh=menusAndMenuItems.getMenusAndSubmenus().get(i);
				if (smh.getLevel() == 0) // Menu Level
				{
					AsaanMenuHolder menuHolder = new AsaanMenuHolder();
					
					menuMap.put(smh.getMenuPOSId(), menuHolder);
					menuHolder.menu = smh;
					//re-enumerate through the list for submenu array
					int j;
					int totalItemCount = 0;
					for (j=0;j<menusAndMenuItems.getMenusAndSubmenus().size();j++) 
					{
						
						StoreMenuHierarchy subMenu=menusAndMenuItems.getMenusAndSubmenus().get(j);
						Log.e("Get Submenu", "subMenu name:"+subMenu.getName() + " PosId:" + subMenu.getMenuPOSId() + " Count:" +subMenu.getMenuItemCount());
						if((subMenu.getLevel()==1) && subMenu.getMenuPOSId().equals(smh.getMenuPOSId()) && (subMenu.getMenuItemCount()>0))
						{
							//it is a submenu,add it to the arraylist
							menuHolder.subMenus.add(subMenu);
							totalItemCount += subMenu.getMenuItemCount();
						}					
					}
					
					List<MenuItemAndStats> listMenuitems = menusAndMenuItems.getMenuItems();
					int itemCount = listMenuitems.size();
					int realMenuItemCount = 0;
					for(j=0; j<itemCount; j++)
					{
						if(listMenuitems.get(j).getMenuItem().getLevel()==2)
						{
							menuHolder.menuItemHolder.menuItemAndStats.add(listMenuitems.get(j));
							Log.e("name and id: " + realMenuItemCount ,""+listMenuitems.get(j).getMenuItem().getShortDescription());
							realMenuItemCount++;
						}
					}
					for(j=realMenuItemCount; j<totalItemCount; j++)
					{
						menuHolder.menuItemHolder.menuItemAndStats.add(null);
					}
					
					List<StoreMenuStats> listMenuStats = menusAndMenuItems.getMenuStats();
					StoreMenuStats stMenuStats;
					if(listMenuStats != null)
					{
						for(j=0; j<listMenuStats.size(); j++)
						{
							stMenuStats = listMenuStats.get(j);
							String str = stMenuStats.getMenuPOSId() + "_" + stMenuStats.getSubMenuPOSId();
							menuHolder.mapMenuStats.put(str, stMenuStats);
						}
					}
				}
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
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
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
		else if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}
}
