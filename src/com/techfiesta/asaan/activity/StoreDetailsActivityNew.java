package com.techfiesta.asaan.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.HistoryFragment;
import com.techfiesta.asaan.fragment.InfoFragment;
import com.techfiesta.asaan.fragment.ReviewFragment;

public class StoreDetailsActivityNew extends FragmentActivity implements TabListener {

	ActionBar actionBar = null;
	InfoFragment infoFragment;
	ReviewFragment reviewFragment;
	HistoryFragment historyFragment;
	//public static boolean isBackButtonPressed = false;
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_store_details);
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		infoFragment = new InfoFragment();
		reviewFragment = new ReviewFragment();
		historyFragment=new HistoryFragment();
		

		setTabs();
		
		// setInitialInfoFragment();

	}

	private void setTabs() {
		Tab historyTab = actionBar.newTab();
		historyTab.setText("History");
		historyTab.setTabListener(this);

		Tab infoTab= actionBar.newTab();
		infoTab.setText("Info");
		infoTab.setTabListener(this);

		Tab reviewTab = actionBar.newTab();
		reviewTab.setText("Reviews");
		reviewTab.setTabListener(this);
		
		actionBar.addTab(historyTab,0,false);
		actionBar.addTab(infoTab,1,true);
		actionBar.addTab(reviewTab,2,false);
		
		

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		if (position == 0) {
			ft.replace(R.id.content_frame,historyFragment);
			// ft.commit();
		} else if (position == 1) {
			ft.replace(R.id.content_frame, infoFragment);
		} else  if (position == 2) {
			ft.replace(R.id.content_frame, reviewFragment);
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onResume() {
		registerReceiver(broadcastReceiver,new IntentFilter(getResources().getString(R.string.intent_filter_finish)));
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		//super.onBackPressed();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item) {

       if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}
	
}
