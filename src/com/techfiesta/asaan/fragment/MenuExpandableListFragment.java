package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenusAndMenuItems;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.google.android.gms.internal.it;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.AsaanMainActivity;
import com.techfiesta.asaan.activity.MenuActivity;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;
import com.techfiesta.asaan.adapter.ExpandableMenuListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.MyTabListener;

import android.app.Fragment;
import android.app.ActionBar.Tab;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class MenuExpandableListFragment extends Fragment{
	
	public static MenusAndMenuItems menusAndMenuItems;
	
	private ExpandableListView elvMenu;
	private ArrayList<String> groupList=new ArrayList<>();
	private HashMap<String,ArrayList<String>> childListMap=new HashMap<String,ArrayList<String>>();
	private ExpandableMenuListAdapter expListAdapter;
	private int MAX_RESULT = 10;
	private long storeId = -1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_expandable_menu_list,container,false);
		elvMenu=(ExpandableListView)v.findViewById(R.id.elv_menu);
		storeId=AsaanUtility.selectedStore.getId();
		((StoreDetailsActivityNew) getActivity()).menuTabStack.push(MenuExpandableListFragment.this);
		new GetMenu().execute();
		return v;
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
			populateGroupList();
			expListAdapter=new ExpandableMenuListAdapter(getActivity(),groupList,childListMap);
			elvMenu.setAdapter(expListAdapter);
			super.onPostExecute(result);
		}

	}
	private void populateGroupList()
	{
		for (StoreMenuHierarchy smh : menusAndMenuItems.getMenusAndSubmenus()) {
			if (smh.getLevel() == 0) // Menu Level
			{
				populateChildHashMap(smh.getName(),smh.getMenuPOSId());
			}
		}
	}
	private void  populateChildHashMap(String name,long menuPosId)
	{
		ArrayList<String> childList=new ArrayList<>();
		for (StoreMenuItem item :menusAndMenuItems.getMenuItems()) {
			if (item.getMenuPOSId() == menuPosId && item.getLevel()==Constants.ROW_TYPE_SUBMENU)
				childList.add(item.getShortDescription());
		}
		if(childList.size()>0)
		{
			childListMap.put(name, childList);
			groupList.add(name);
			Log.e("name",name+childList.size());
		}
	}

}
