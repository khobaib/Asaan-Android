package com.techfiesta.asaan.activity;

import java.util.Stack;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.FragmentMenu;
import com.techfiesta.asaan.fragment.HistoryLayer1;
import com.techfiesta.asaan.fragment.InfoFragment;
import com.techfiesta.asaan.fragment.MenuExpandableListFragment;
import com.techfiesta.asaan.fragment.MenuLayer1;
import com.techfiesta.asaan.fragment.ReviewFragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

public class StoreDetailsActivityNew extends FragmentActivity implements
		TabListener {

	ActionBar actionBar = null;
	InfoFragment infoFragment=new InfoFragment();
	ReviewFragment reviewFragment=new ReviewFragment();
	public Stack<Fragment> menuTabStack = new Stack<Fragment>();
	public Stack<Fragment> historyTabStack = new Stack<Fragment>();
	private int curPosition =-1;
	private int flag=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_store_details);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setTabs();


	}

	private void setTabs() {
		Tab infoTab = actionBar.newTab();
		infoTab.setText("Info");
		infoTab.setTabListener(this);

		Tab menuab = actionBar.newTab();
		menuab.setText("Menu");
		menuab.setTabListener(this);

		Tab reviewTab = actionBar.newTab();
		reviewTab.setText("Reviews");
		reviewTab.setTabListener(this);

		Tab historyTab = actionBar.newTab();
		historyTab.setText("History");
		historyTab.setTabListener(this);

		actionBar.addTab(infoTab);
		actionBar.addTab(historyTab);
		actionBar.addTab(menuab);
		actionBar.addTab(reviewTab);

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		hidePreviousFragment();
		int position = tab.getPosition();
		
		if (position == 0) {
			if(curPosition==-1)
				ft.replace(R.id.content_frame,infoFragment);
			else
			 ft.show(infoFragment);
			
			// ft.commit();
		} else if (position == 1) {
			Log.e("Size",""+historyTabStack.size());
			if (historyTabStack.size() == 0)
				ft.add(R.id.content_frame, new HistoryLayer1());
			else {
				Fragment fragment = historyTabStack.peek();
				ft.show(fragment);
				Log.e(".......","showing");
				// ft.commit();
			}
		} else if (position == 2) {
			Log.e("Size",""+menuTabStack.size());
			if (menuTabStack.size() == 0)
				ft.add(R.id.content_frame, new MenuExpandableListFragment());
			else {
				Fragment fragment = menuTabStack.peek();
				ft.show(fragment);
			}
			
		} else if (position == 3) {
			if(flag==-1)
			{
				ft.add(R.id.content_frame, reviewFragment);
				flag=1;
			}
			else
			  ft.show(reviewFragment);
			
		}
		curPosition = position;

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
		//super.onBackPressed();

		if(curPosition==0)
		{
			finish();
		}
		else if(curPosition==1)
		{
			Log.e("Size",""+historyTabStack.size());
			if(historyTabStack.size()>1)
			{
				
				Fragment curFragment=historyTabStack.pop();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.hide(curFragment);
				Fragment newFragment=historyTabStack.peek();
				ft.show(newFragment);
				ft.commit();
				
			}
			else
				finish();
		}
		else if(curPosition==2)
		{
			Log.e("Size",""+menuTabStack.size());
			if(menuTabStack.size()>1)
			{
				//Log.e("Size",""+menuTabStack.size());
				Fragment curFragment=menuTabStack.pop();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.hide(curFragment);
				Fragment newFragment=menuTabStack.peek();
				ft.show(newFragment);
				ft.commit();
				
			}
			else
				finish();
			
		}
		else if(curPosition==3)
		{
			finish();
		}
	}

	private void hidePreviousFragment()
	{
		if (curPosition == 0) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Log.e(".......","hiding");
			ft.hide(infoFragment);
			ft.commit();
		} else if (curPosition == 1) {
			if (historyTabStack.size() > 0) {
				Fragment fragment = historyTabStack.peek();
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.hide(fragment);
				ft.commit();
			}
		} else if (curPosition == 2) {
			if (menuTabStack.size() > 0) {
				Fragment fragment = menuTabStack.peek();
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.hide(fragment);
				ft.commit();
			}
		} else if (curPosition == 3) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.hide(reviewFragment);
			ft.commit();
		}

	}

}
