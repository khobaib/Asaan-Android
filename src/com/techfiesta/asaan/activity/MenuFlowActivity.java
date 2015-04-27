package com.techfiesta.asaan.activity;

import java.io.IOException;
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
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStatsCollection;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreItemStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuFlowFragment;
import com.techfiesta.asaan.interfaces.ForwardBackWardClickListner;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MenuFlowActivity extends Activity implements ForwardBackWardClickListner{

	public List<MenuItemAndStats> allItems ;
	private ActionBar actionBar;
	int iCurrentPos = 0;
	private int MAX_RESULT = 50;
	MenuItemAndStatsCollection menuItemAndStatsCollection=null;
	boolean bLoading  = false;
	int iMenuPosId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_flow);
		actionBar=getActionBar();
		actionBar.setTitle("");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		allItems = MenuActivityNew.currentMenuItemAndStats;
		int position=getIntent().getIntExtra(Constants.BUNDLE_KEY_ITEM_POSITION,0);
		iMenuPosId = getIntent().getIntExtra(Constants.BUNDLE_KEY_CURRENT_MENU_POSID,0);
		moveForward(position);
	}
	@Override
	public void moveForward(int position) {
		Log.e("MSG", "GOT"+(position));
		iCurrentPos = position;
		if(position<allItems.size())
		{
		 FragmentTransaction ft=getFragmentManager().beginTransaction();
         MenuFlowFragment menuFlowFragment=new MenuFlowFragment();
         Bundle bundle=new Bundle();
         bundle.putInt(Constants.BUNDLE_KEY_ITEM_POSITION,position);
         
         MenuItemAndStats menuItem = allItems.get(position);
         if(menuItem != null)
         {
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
         }
         else
         {
	         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID, 0);
	         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_PRICE,0);
	         bundle.putBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, false);
	         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,"Loading ...");
	         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, "Loading ...");
	         bundle.putString(Constants.BUNDLE_KEY_IMAGE_URL, null);
	         
	         if(bLoading==false)
	         {
	        	 bLoading = true;
	        	 new GetMoreMenuItemAndStatsForMenuFlow().execute();
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
		iCurrentPos = position;
		if(position>=0)
		{
		 FragmentTransaction ft=getFragmentManager().beginTransaction();
         MenuFlowFragment menuFlowFragment=new MenuFlowFragment();
         Bundle bundle=new Bundle();
         bundle.putInt(Constants.BUNDLE_KEY_ITEM_POSITION,position);
         
         MenuItemAndStats menuItem = allItems.get(position);
         if(menuItem != null)
         {
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
         }
         else
         {
        	 bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID, 0);
	         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_PRICE,0);
	         bundle.putBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, false);
	         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,"Loading ...");
	         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, "Loading ...");
	         bundle.putString(Constants.BUNDLE_KEY_IMAGE_URL, null);  	
	         
	         if(bLoading==false)
	         {
	        	 bLoading = true;
	        	 new GetMoreMenuItemAndStatsForMenuFlow().execute();
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

	
	public int getSectionForPosition(int position) {
		for(int i=0;i<MenuActivityNew.currentSectionIndexList.size();i++)
			if(position<MenuActivityNew.currentSectionIndexList.get(i))
				return i;
		return MenuActivityNew.currentSectionIndexList.size()-1;
	}
	
	private  class GetMoreMenuItemAndStatsForMenuFlow extends AsyncTask<Void,Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			 try { 
				 
				 
				 Log.e("params", "store id: "+AsaanUtility.selectedStore.getId()+" Root Menu Id: "+iMenuPosId);
				int isections = getSectionForPosition(iCurrentPos);
				menuItemAndStatsCollection = SplashActivity.mStoreendpoint
						.getMenuItemAndStatsForMenu(iCurrentPos+isections+1,
								MAX_RESULT, iMenuPosId,AsaanUtility.selectedStore.getId()).execute();
			if(menuItemAndStatsCollection!=null && menuItemAndStatsCollection.getItems()!=null)
			{
				Log.e("MenuItemAndStatsForMenu", "Item Array Size: "+menuItemAndStatsCollection.getItems().size());
			}
			else
			{
				Log.e("MenuItemAndStatsForMenu", "No items return. ");
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			int startAddPos =0;	
			try
			{ 
				if(menuItemAndStatsCollection !=null && (menuItemAndStatsCollection.getItems() != null))
				{
					for(int i=0;  i<menuItemAndStatsCollection.getItems().size(); i++)
					{
						if(menuItemAndStatsCollection.getItems().get(i).getMenuItem().getLevel()==2)
						{
							allItems.set(iCurrentPos+startAddPos, menuItemAndStatsCollection.getItems().get(i));
							startAddPos++;
						}
					}
				}				
				moveForward(iCurrentPos);
			}
			catch(Exception e)
			{
				Log.e("GetMoreMenuItemAndStatsForMenu", "onPostExecute failed.");
			}			
			bLoading = false;		
		}
	}
}
