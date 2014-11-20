package com.techfiesta.asaan.activity;


import lombok.core.Main;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.FragmentMenu;
import com.techfiesta.asaan.fragment.InfoFragment;
import com.techfiesta.asaan.fragment.ReviewFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class StoreDetailsActivityNew extends FragmentActivity implements TabListener{

	ActionBar actionBar = null;
	InfoFragment infoFragment;
	ReviewFragment reviewFragment;
	FragmentMenu fragmentMenu;
	public static boolean isBackButtonPressed=false;
	
	public StoreDetailsActivityNew() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_store_details);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		infoFragment=new InfoFragment();
		reviewFragment=new ReviewFragment();
		fragmentMenu=new FragmentMenu();
		
		isBackButtonPressed=false;
		
       setTabs();
		//setInitialInfoFragment();
		

		
	}
	private void setTabs()
	{
		Tab infoTab=actionBar.newTab();
		infoTab.setText("Info");
		infoTab.setTabListener(this);
		
		
		Tab menuab=actionBar.newTab();
		menuab.setText("Menu");
		menuab.setTabListener(this);
		
		
		Tab reviewTab=actionBar.newTab();
		reviewTab.setText("Reviews");
		reviewTab.setTabListener(this);
		
		
		Tab historyTab=actionBar.newTab();
		historyTab.setText("History");
		historyTab.setTabListener(this);
		
		actionBar.addTab(infoTab);
		actionBar.addTab(historyTab);
		actionBar.addTab(menuab);
		actionBar.addTab(reviewTab);

		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		int position=tab.getPosition();
		if(position==0)
		{
			ft.replace(R.id.content_frame,infoFragment);
			//ft.commit();
		}
		else if(position==1)
		{
			ft.replace(R.id.content_frame,fragmentMenu);
		}
		else if(position==2)
		{
			Intent intent = new Intent(StoreDetailsActivityNew.this, MenuActivity.class);
			startActivity(intent);
		}
		else if(position==3)
		{
			ft.replace(R.id.content_frame,reviewFragment);
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
	public void onBackPressed() {
		isBackButtonPressed=true;
		super.onBackPressed();
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.my_cart)
		{
			Intent intent=new Intent(StoreDetailsActivityNew.this,OrderDetailsActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	

}
