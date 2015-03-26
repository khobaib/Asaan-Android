package com.techfiesta.asaan.activity;

import java.util.ArrayList;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuFlowFragment;
import com.techfiesta.asaan.interfaces.ForwardBackWardClickListner;
import com.techfiesta.asaan.utility.Constants;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MenuFlowActivity extends Activity implements ForwardBackWardClickListner{
	public List<MenuItemAndStats> allItems ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_flow);
		allItems = new ArrayList<MenuItemAndStats>();
		for (int i=0;i<MenuActivityNew.menusAndMenuItems.getMenuItems().size();i++) {
			MenuItemAndStats item=MenuActivityNew.menusAndMenuItems.getMenuItems().get(i);
			if (item.getMenuItem().getLevel()==2)
			{
				allItems.add(item);
				Log.e("name",""+item.getMenuItem().getShortDescription());
			}
			
		}
		 FragmentTransaction ft=getFragmentManager().beginTransaction();
         MenuFlowFragment menuFlowFragment=new MenuFlowFragment();
         Bundle bundle=new Bundle();
         bundle.putInt("item_position",0);
         StoreMenuItem sItem=allItems.get(0).getMenuItem();
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID, sItem.getMenuItemPOSId());
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_PRICE,sItem.getPrice());
         bundle.putBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, sItem.getHasModifiers());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,sItem.getShortDescription());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, sItem.getLongDescription());
         menuFlowFragment.setArguments(bundle);
         ft.replace(R.id.frame_container,menuFlowFragment);
         ft.commit();
	}
	@Override
	public void moveForward(int position) {
		Log.e("MSG", "GOT"+(position));
		 FragmentTransaction ft=getFragmentManager().beginTransaction();
         MenuFlowFragment menuFlowFragment=new MenuFlowFragment();
         Bundle bundle=new Bundle();
         StoreMenuItem sItem=allItems.get(position).getMenuItem();
         bundle.putInt("item_position",position);
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID, sItem.getMenuItemPOSId());
         bundle.putInt(Constants.BUNDLE_KEY_MENUITEM_PRICE,sItem.getPrice());
         bundle.putBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, sItem.getHasModifiers());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,sItem.getShortDescription());
         bundle.putString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, sItem.getLongDescription());
         menuFlowFragment.setArguments(bundle);
         ft.replace(R.id.frame_container,menuFlowFragment);
         ft.commit();
		
	}
	@Override
	public void moveBackward(int position) {
		// TODO Auto-generated method stub
		
	}

}
