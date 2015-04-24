package com.techfiesta.asaan.utility;

import java.util.ArrayList;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;

public class AsaanMenuItemsHolder {
	public AsaanMenuItemsHolder()
	{
		menuItemAndStats = new ArrayList<MenuItemAndStats>();
	}	
	public ArrayList<MenuItemAndStats> menuItemAndStats;
}
