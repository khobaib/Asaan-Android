package com.techfiesta.asaan.utility;

import java.util.ArrayList;
import java.util.HashMap;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuStats;

public class AsaanMenuHolder {
	public AsaanMenuHolder() {
		subMenus = new ArrayList<StoreMenuHierarchy>();
		menuItemHolder = new AsaanMenuItemsHolder();
		mapMenuStats = new HashMap<String, StoreMenuStats>();
	}
	
	public StoreMenuHierarchy menu;
	public ArrayList<StoreMenuHierarchy> subMenus;
	public AsaanMenuItemsHolder menuItemHolder;
	public HashMap<String, StoreMenuStats> mapMenuStats;
	
}
