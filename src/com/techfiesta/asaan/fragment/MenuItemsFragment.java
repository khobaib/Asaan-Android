package com.techfiesta.asaan.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MenuActivity;
import com.techfiesta.asaan.activity.PlaceOrderActivity;
import com.techfiesta.asaan.utility.AmountConversionUtils;

@SuppressLint("NewApi")
public class MenuItemsFragment extends ListFragment {
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

	// protected boolean pauseOnScroll = false;
	// protected boolean pauseOnFling = false;
	static ListView mListView;
	protected static ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Bundle bundle = this.getArguments();
		long menuPOSId = 0;
		if (bundle != null)
			menuPOSId = bundle.getLong(BUNDLE_KEY_MENU_ID);
		List<StoreMenuItem> allItems = new ArrayList<StoreMenuItem>();
		for (StoreMenuItem item : MenuActivity.menusAndMenuItems.getMenuItems()) {
			if (item.getMenuPOSId() == menuPOSId)
				allItems.add(item);
		}
		final MenuFragmentAdapter adapter = new MenuFragmentAdapter(getActivity(), allItems);
		setListAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		// applyScrollListener();
	}

	//
	// private void applyScrollListener()
	// {
	// mListView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
	// pauseOnScroll, pauseOnFling));
	// }

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		logger.log(Level.INFO, "onViewCreated() Called");
		this.setEmptyText(getString(R.string.title_no_menu_available));
		mListView = this.getListView();
	}

	@Override
	public void onConfigurationChanged(final Configuration newConfig) {
		// don't reload the current page when the orientation is changed
		logger.log(Level.WARNING, "onConfigurationChanged() Called");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		((MenuFragmentAdapter) mListView.getAdapter()).listItemClick(l, v, position, id);
		
	}

	public static class MenuFragmentAdapter extends BaseAdapter {
		private static final int ROWDATA_TYPE_SUBMENU = 1;
		private static final int ROWDATA_TYPE_MENU_ITEM = 2;

		private List<StoreMenuItem> allItems = null;

		@Getter
		private Context context = null;

		static class ViewHolder {
			public ImageView imgFood;
			public TextView txtName;
			public TextView txtPrice;
			public TextView txtDesc;
			public TextView txtLike;
			public TextView txtOrderedToday;
			public ImageView imgLike;
			public ImageView imgVegetarian;
			public ImageView imgSpicy;
			public ImageView imgGlutenFree;
		}

		static class ViewHolder2 {
			public ImageView imgGroup;
			public TextView txtGroupName;
		}

		public MenuFragmentAdapter(final Context context, List<StoreMenuItem> allItems) {
			this.allItems = allItems;
			this.context = context;
		}

		public void listItemClick(ListView l, View v, int position, long id) {
			final StoreMenuItem menuItem = allItems.get(position);
			if (menuItem != null) {
				 final Intent intent = new Intent(context,
				 PlaceOrderActivity.class);
				 intent.putExtra(BUNDLE_KEY_MENUITEM_POS_ID,
				 menuItem.getMenuItemPOSId());
				 intent.putExtra(BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,
				 menuItem.getShortDescription());
				 intent.putExtra(BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION,
				 menuItem.getLongDescription());
				 intent.putExtra(BUNDLE_KEY_MENUITEM_PRICE,
				 menuItem.getPrice());
				 Log.e("PRICE",""+menuItem.getPrice());
				 intent.putExtra(BUNDLE_KEY_MENUITEM_HAS_MODIFIERS,
				 menuItem.getHasModifiers());
				 intent.putExtra(BUNDLE_KEY_ORDER_DETAILS_AVAILABLE, false);
				 context.startActivity(intent);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.e("ALL ITEMS SIZE",""+allItems.size());
			return allItems.size();
		}

		@Override
		public StoreMenuItem getItem(int position) {
			return allItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			final StoreMenuItem storeMenuItem = allItems.get(position);
			if (rowView == null
					|| ((Integer) rowView.getTag(R.id.menu_category_title)).intValue() != storeMenuItem.getLevel())
				if (storeMenuItem.getLevel() == ROWDATA_TYPE_SUBMENU) {
					rowView = View.inflate(getContext(), R.layout.menu_item_group, null);
					final ViewHolder2 viewHolder2 = new ViewHolder2();
					viewHolder2.imgGroup = (ImageView) rowView.findViewById(R.id.img_menu_category_finder);
					viewHolder2.txtGroupName = (TextView) rowView.findViewById(R.id.menu_category_title);

					rowView.setTag(viewHolder2);
					rowView.setTag(R.id.menu_category_title, ROWDATA_TYPE_SUBMENU);
				} else {
					rowView = View.inflate(getContext(), R.layout.menu_item, null);
					final ViewHolder viewHolder = new ViewHolder();
					viewHolder.imgFood = (ImageView) rowView.findViewById(R.id.image_food_item);
					viewHolder.txtName = (TextView) rowView.findViewById(R.id.txt_item_name);
					viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.txt_item_price);
					viewHolder.txtDesc = (TextView) rowView.findViewById(R.id.txt_item_desc);
					viewHolder.txtLike = (TextView) rowView.findViewById(R.id.txt_item_ranking);
					viewHolder.txtOrderedToday = (TextView) rowView.findViewById(R.id.txt_item_ordered_today);
					viewHolder.imgLike = (ImageView) rowView.findViewById(R.id.image_like);
					viewHolder.imgVegetarian = (ImageView) rowView.findViewById(R.id.image_vegetarian);
					viewHolder.imgSpicy = (ImageView) rowView.findViewById(R.id.image_spicy);
					viewHolder.imgGlutenFree = (ImageView) rowView.findViewById(R.id.image_glutenfree);

					rowView.setTag(viewHolder);
					rowView.setTag(R.id.menu_category_title, ROWDATA_TYPE_MENU_ITEM);
				}
			if (storeMenuItem.getLevel() == ROWDATA_TYPE_SUBMENU) {
				final ViewHolder2 holder2 = (ViewHolder2) rowView.getTag();

				holder2.txtGroupName.setText(storeMenuItem.getShortDescription()); // Submenu
																					// name
				holder2.imgGroup.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// final List<String> allHierarchyTitles = new
						// ArrayList<String>();
						// for (final RowData i : allItems)
						// if (i.type == ROWDATA_TYPE_SUBMENU)
						// allHierarchyTitles.add(((MenuHierarchy.SubMenu)
						// i.obj).getDescription());
						//
						// final AlertDialog.Builder builder = new
						// AlertDialog.Builder(getActivity()).setTitle(R.string.menu_category).setItems(
						// allHierarchyTitles.toArray(new
						// String[allHierarchyTitles.size()]), new
						// DialogInterface.OnClickListener()
						// {
						// @Override
						// public void onClick(DialogInterface dialog, int
						// which)
						// {
						// final String selectedHierarchyName =
						// allHierarchyTitles.get(which);
						// for (final RowData i : allItems)
						// if (i.type == ROWDATA_TYPE_SUBMENU
						// && TextUtils.equals(selectedHierarchyName,
						// ((MenuHierarchy.SubMenu) i.obj).getDescription()) ==
						// true)
						// {
						// final int selectedRowPos = allItems.indexOf(i);
						// mListView.setSelection(selectedRowPos);
						// }
						// }
						// });
						// builder.create().show();
					}
				});
			} else {
				final ViewHolder holder = (ViewHolder) rowView.getTag();

				Log.e("short descriptipn", storeMenuItem.getShortDescription());
				holder.txtName.setText(storeMenuItem.getShortDescription());
				holder.txtDesc.setText(storeMenuItem.getLongDescription());
				holder.txtPrice.setText(AmountConversionUtils.formatCentsToCurrency(storeMenuItem.getPrice()));
				holder.imgFood.setVisibility(View.GONE);
			}

			return rowView;
		}
	}
}
