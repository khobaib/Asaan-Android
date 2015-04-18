package com.techfiesta.asaan.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreItemStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuFlowFragment;
import com.techfiesta.asaan.interfaces.ForwardBackWardClickListner;
import com.techfiesta.asaan.utility.Constants;

public class MenuFlowActivity extends Activity implements ForwardBackWardClickListner{
	public List<MenuItemAndStats> allItems ;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_flow);
		actionBar=getActionBar();
		actionBar.setTitle("");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		allItems = new ArrayList<MenuItemAndStats>();
		for (int i=0;i<MenuActivityNew.menusAndMenuItems.getMenuItems().size();i++) {
			MenuItemAndStats item=MenuActivityNew.menusAndMenuItems.getMenuItems().get(i);
			if (item.getMenuItem().getLevel()==2)
			{
				allItems.add(item);
				Log.e("name",""+item.getMenuItem().getShortDescription());
			}
			
		}
		int position=getIntent().getIntExtra(Constants.BUNDLE_KEY_ITEM_POSITION,0);
		moveForward(position);
	}
	@Override
	public void moveForward(int position) {
		Log.e("MSG", "GOT"+(position));
		if(position<allItems.size())
		{
		 FragmentTransaction ft=getFragmentManager().beginTransaction();
         MenuFlowFragment menuFlowFragment=new MenuFlowFragment();
         Bundle bundle=new Bundle();
         bundle.putInt(Constants.BUNDLE_KEY_ITEM_POSITION,position);
         StoreMenuItem sItem=allItems.get(position).getMenuItem();
         StoreItemStats storeItemStats=allItems.get(position).getStats();
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID, sItem.getMenuItemPOSId());
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_PRICE,sItem.getPrice());
         bundle.putBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, sItem.getHasModifiers());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,sItem.getShortDescription());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, sItem.getLongDescription());
         bundle.putString(Constants.BUNDLE_KEY_IMAGE_URL, sItem.getImageUrl());
         if(storeItemStats!=null)
         {
        	 long orders=storeItemStats.getOrders();
        	 long likes=storeItemStats.getLikes();
        	 long disLikes=storeItemStats.getDislikes();
        	 
        	 if(likes>0)
        	 {
        		 String likeStats=""+(likes*100)/(likes+disLikes)+"%("+(likes+disLikes)+")";
        		 bundle.putString(Constants.BUNDLE_KEY_NUMBER_OF_LIKES, likeStats);
        		 bundle.putBoolean(Constants.BUNDLE_KEY_HAS_STATS, true);
        		 bundle.putLong(Constants.BUNDLE_KEY_NUMBER_OF_ORDER, orders);
        	 }
        	 
        	 
         }
         bundle.putInt(Constants.ORDER_TYPE,getOrderType());
         setActionBarHeader(position);
         menuFlowFragment.setArguments(bundle);
         ft.replace(R.id.frame_container,menuFlowFragment);
         ft.commit();
		}
		
	}
	private int getOrderType()
	{
		int oType=-1;
		Intent intent=getIntent();
		if(intent!=null)
		{
			
			oType=intent.getIntExtra(Constants.ORDER_TYPE,-1);
		}
		return oType;
	}
	@Override
	public void moveBackward(int position) {
		
		Log.e("MSG", "GOT"+(position));
		if(position>=0)
		{
		 FragmentTransaction ft=getFragmentManager().beginTransaction();
         MenuFlowFragment menuFlowFragment=new MenuFlowFragment();
         Bundle bundle=new Bundle();
         bundle.putInt(Constants.BUNDLE_KEY_ITEM_POSITION,position);
         StoreMenuItem sItem=allItems.get(position).getMenuItem();
         StoreItemStats storeItemStats=allItems.get(position).getStats();
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID, sItem.getMenuItemPOSId());
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_PRICE,sItem.getPrice());
         bundle.putBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, sItem.getHasModifiers());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,sItem.getShortDescription());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, sItem.getLongDescription());
         bundle.putString(Constants.BUNDLE_KEY_IMAGE_URL, sItem.getImageUrl());
         if(storeItemStats!=null)
         {
        	 long orders=storeItemStats.getOrders();
        	 long likes=storeItemStats.getLikes();
        	 long disLikes=storeItemStats.getDislikes();
        	 
        	 if(likes>0)
        	 {
        		 String likeStats=""+(likes*100)/(likes+disLikes)+"%("+(likes+disLikes)+")";
        		 bundle.putString(Constants.BUNDLE_KEY_NUMBER_OF_LIKES, likeStats);
        		 bundle.putBoolean(Constants.BUNDLE_KEY_HAS_STATS, true);
        		 bundle.putLong(Constants.BUNDLE_KEY_NUMBER_OF_ORDER, orders);
        	 }
        	 
        	 
         }
        
         setActionBarHeader(position);
         menuFlowFragment.setArguments(bundle);
         ft.replace(R.id.frame_container,menuFlowFragment);
         ft.commit();
		}
	}
	private void setActionBarHeader(int counter)
	{
		Log.e("MSG", "aBarcounter"+counter);
		actionBar.setTitle((counter+1)+" of "+ (allItems.size()));
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
