package com.techfiesta.asaan.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MenuActivityNew;
import com.techfiesta.asaan.activity.MenuItemDetailsActivity;
import com.techfiesta.asaan.activity.PlaceOrderActivity;
import com.techfiesta.asaan.adapter.MenuItemsAdapter;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AmountConversionUtils;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.MenuHierarchy.MenuItem;

@SuppressLint("NewApi")
public class MenuItemsFragment extends Fragment {
	private static final Logger logger = Logger.getLogger(MenuItemsFragment.class.getName());

	public static final String BUNDLE_KEY_MENU_ID = "BUNDLE_KEY_MENU_ID";
	public static final String BUNDLE_KEY_MENUITEM_POS_ID = "BUNDLE_KEY_MENUITEM_POS_ID";
	public static final String BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION = "BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION = "BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_PRICE = "BUNDLE_KEY_MENUITEM_PRICE";
	public static final String BUNDLE_KEY_MENUITEM_HAS_MODIFIERS = "BUNDLE_KEY_MENUITEM_HAS_MODIFIERS";
	public static final String BUNDLE_KEY_ORDER_DETAILS_POSITION = "BUNDLE_KEY_ORDER_DETAILS_POSITION";
	public static final String BUNDLE_KEY_ORDER_DETAILS_AVAILABLE = "BUNDLE_KEY_ORDER_DETAILS_AVAILABLE";
	public static final String BUNDLE_KEY_MODIFIERGRP_ID = "BUNDLE_KEY_MODIFIERGRP_ID";
	public static final String BUNDLE_KEY_MODIFIERGRP_INDEX = "BUNDLE_KEY_MODIFIERGRP_INDEX";

	// protected boolean pauseOnScroll = false;
	// protected boolean pauseOnFling = false;
	private StickyListHeadersListView mListView;

	// protected static ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View v=inflater.inflate(R.layout.menuitems_fragment,null,false);
			mListView=(StickyListHeadersListView)v.findViewById(R.id.sticky_list);
			return v;
		}
			
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			final Bundle bundle = this.getArguments();
			long menuPOSId = 0;
			if (bundle != null)
				menuPOSId = bundle.getLong(BUNDLE_KEY_MENU_ID);
			List<MenuItemAndStats> allItems = new ArrayList<MenuItemAndStats>();
			List<MenuItemAndStats> allsections=new ArrayList<MenuItemAndStats>();
			ArrayList<Integer> indexList=new ArrayList<Integer>();
			for (int i=0;i<MenuActivityNew.menusAndMenuItems.getMenuItems().size();i++) {
				MenuItemAndStats item=MenuActivityNew.menusAndMenuItems.getMenuItems().get(i);
				if (item.getMenuItem().getMenuPOSId()== menuPOSId && item.getMenuItem().getLevel()==2)
					allItems.add(item);
				if (item.getMenuItem().getMenuPOSId()== menuPOSId && item.getMenuItem().getLevel()==1)
				{
					indexList.add(i-allsections.size());
					allsections.add(item);
					String name=getName(item.getMenuItem().getSubMenuPOSId());
					item.getMenuItem().setShortDescription(name);
					Log.e("POS",""+(i-allsections.size()));
				}
				
			}
			MenuItemsAdapter adapter=new MenuItemsAdapter(getActivity(),allItems,allsections,indexList);
			mListView.setAdapter(adapter);
			
		}
		public String getName(int subMenuPosId)
		{
			for(int i=0;i<MenuActivityNew.menusAndMenuItems.getMenusAndSubmenus().size();i++)
			{
				StoreMenuHierarchy stHierarchy=MenuActivityNew.menusAndMenuItems.getMenusAndSubmenus().get(i);
				if(stHierarchy.getLevel()==Constants.ROW_TYPE_SUBMENU &&  stHierarchy.getSubMenuPOSId()==subMenuPosId)
					return stHierarchy.getName();
			}
			return "";
			
		}

	@Override
	public void onConfigurationChanged(final Configuration newConfig) {
		// don't reload the current page when the orientation is changed
		logger.log(Level.WARNING, "onConfigurationChanged() Called");
		super.onConfigurationChanged(newConfig);
	}

}
