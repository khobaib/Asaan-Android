package com.techfiesta.asaan.utility;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static Map<Long, MenuHierarchy.SubMenu> subMenuMap = new HashMap<Long, MenuHierarchy.SubMenu>();
	public static Map<Long, MenuHierarchy.MenuItem> menuItemMap = new HashMap<Long, MenuHierarchy.MenuItem>();
	public static Map<Long, MenuHierarchy.ModifierGroup> modGrpMap = new HashMap<Long, MenuHierarchy.ModifierGroup>();
	public static Map<Long, MenuHierarchy.MenuTax> menuTaxMap = new HashMap<Long, MenuHierarchy.MenuTax>();

	public static final int ROW_TYPE_MENU = 0;
	public static final int ROW_TYPE_SUBMENU = 1;
	public static final int ROW_TYPE_MENUITEM = 2;

	public static final int MENU_TYPE_DINE_IN = 0; // 0 = dine-in, 1 =
													// carry-out
	// public static final long MENU_TYPE_CARRYOUT_DELIVERY = 1L;
	public static int ORDER_TYPE_DELIVERY = 1;
	public static int ORDER_TYPE_CARRYOUT = 2;// 0 = dine-in, 1
												// = carry-out

	public static final long STORE_ID = 1L;

	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	public static final String ORDER_TYPE = "order_type";
	public static final String ESTIMATED_TIME = "estimated_time";
	public static final String PARTY_SIZE = "party_size";
	public static final String KEY_FROM_ACTIVITY = "key_form_activity";
	public static final String KEY_QUANTITY = "key_quantity";
	public static final String BUNDLE_KEY_MENU_ID = "BUNDLE_KEY_MENU_ID";
	public static final String BUNDLE_KEY_MENUITEM_POS_ID = "BUNDLE_KEY_MENUITEM_POS_ID";
	public static final String BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION = "BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION = "BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_PRICE = "BUNDLE_KEY_MENUITEM_PRICE";
	public static final String BUNDLE_KEY_MENUITEM_HAS_MODIFIERS = "BUNDLE_KEY_MENUITEM_HAS_MODIFIERS";
	public static final String BUNDLE_KEY_ORDER_DETAILS_POSITION = "BUNDLE_KEY_ORDER_DETAILS_POSITION";
	public static final String BUNDLE_KEY_ORDER_DETAILS_AVAILABLE = "BUNDLE_KEY_ORDER_DETAILS_AVAILABLE";
	public static final String BUNDLE_KEY_MODIFIERGRP_ID = "BUNDLE_KEY_MODIFIERGRP_ID";
	public static final String BUNDLE_KEY_NUMBER_OF_ORDER = "BUNDLE_KEY_NUMBER_OF_ORDER";
	public static final String BUNDLE_KEY_NUMBER_OF_LIKES="BUNDLE KEY_NUMBER_OF_LIKES";
	public static final String BUNDLE_KEY_ITEM_POSITION = "BUNDLE_KEY_ITEM_POSITION";
	public static final String BUNDLE_KEY_HAS_STATS = "BUNDLE_KEY_HAS_STATS";
	public static final String BUNDLE_KEY_IMAGE_URL = "BUNDLE_KEY_IMAGE_URL";
	public static final String BUNDLE_KEY_CURRENT_MENU_POSID = "BUNDLE_KEY_CURRENT_MENU_POSID";


	public static final int MY_CART_ACTIVITY = 1;
	public static final int EDIT_CART_ACTIVITY = 2;

	public static final int BUTTON_POSITIVE = -1;
	public static final int BUTTON_NEGATIVE = -2;

	public static final int FROM_STORE_LIST = 11;
	public static final int FROM_ONLINE_ORDER = 12;

}
