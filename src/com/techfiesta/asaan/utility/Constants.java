package com.techfiesta.asaan.utility;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static Map<Long, MenuHierarchy.SubMenu> subMenuMap = new HashMap<Long, MenuHierarchy.SubMenu>();
	public static Map<Long, MenuHierarchy.MenuItem> menuItemMap = new HashMap<Long, MenuHierarchy.MenuItem>();
	public static Map<Long, MenuHierarchy.ModifierGroup> modGrpMap = new HashMap<Long, MenuHierarchy.ModifierGroup>();
	public static Map<Long, MenuHierarchy.MenuTax> menuTaxMap = new HashMap<Long, MenuHierarchy.MenuTax>();

	public static final Long ROW_TYPE_MENU = 0L;
	public static final Long ROW_TYPE_SUBMENU = 1L;
	public static final Long ROW_TYPE_MENUITEM = 2L;

	public static final int MENU_TYPE_DINE_IN = 0; // 0 = dine-in, 1 =
														// carry-out
	//public static final long MENU_TYPE_CARRYOUT_DELIVERY = 1L;
	public static int ORDER_TYPE_DELIVERY = 1;
	public static int ORDER_TYPE_CARRYOUT = 2;// 0 = dine-in, 1
																// = carry-out

	public static final long STORE_ID = 1L;

	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";

	public static final String BUNDLE_KEY_MENU_ID = "BUNDLE_KEY_MENU_ID";
	public static final String BUNDLE_KEY_MENUITEM_POS_ID = "BUNDLE_KEY_MENUITEM_POS_ID";
	public static final String BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION = "BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION = "BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_PRICE = "BUNDLE_KEY_MENUITEM_PRICE";
	public static final String BUNDLE_KEY_MENUITEM_HAS_MODIFIERS = "BUNDLE_KEY_MENUITEM_HAS_MODIFIERS";
	public static final String BUNDLE_KEY_ORDER_DETAILS_POSITION = "BUNDLE_KEY_ORDER_DETAILS_POSITION";
	public static final String BUNDLE_KEY_ORDER_DETAILS_AVAILABLE = "BUNDLE_KEY_ORDER_DETAILS_AVAILABLE";
	public static final String BUNDLE_KEY_MODIFIERGRP_ID = "BUNDLE_KEY_MODIFIERGRP_ID";

}
