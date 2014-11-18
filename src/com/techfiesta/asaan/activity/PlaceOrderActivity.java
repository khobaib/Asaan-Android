package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemModifiersAndGroups;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItemModifier;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItemModifierGroup;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.AsaanUtility;

public class PlaceOrderActivity extends Activity
{
	ListView mListView = null;
	int menuItemPOSId = 0;
	long menuItemPrice = 0;
	String menuItemShortDesc, menuItemLongDesc;
	MenuModGrpsAdapter mAdapter = null;

	private static final Logger logger = Logger.getLogger(PlaceOrderActivity.class.getName());
	public static final int SELECTED_MODIFIERS_RESULT_CODE = 9600;
	public static final String SELECTED_MODIFIERS = "SELECTED_MODIFIERS";
	public static final String SELECTED_MODIFIERS_DESC = "SELECTED_MODIFIERS_DESC";
	public static final String SELECTED_MODIFIERS_PRICE = "SELECTED_MODIFIERS_PRICE";

	public static MenuItemModifiersAndGroups menuItemModifiersAndGroups;

	ImageView imgFood = null;
	TextView txtName = null;
	TextView txtPrice = null;
	TextView txtDesc = null;
	TextView txtPlus = null;
	TextView txtMinus = null;
	TextView txtQuantity = null;
	TextView txtSpecialInstructions = null;
	Button btnPlaceOrder = null;

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_modifier_group);

		Bundle bundle = this.getIntent().getExtras();
		menuItemPOSId = bundle.getInt(MenuItemsFragment.BUNDLE_KEY_MENUITEM_POS_ID);
		menuItemPrice = bundle.getLong(MenuItemsFragment.BUNDLE_KEY_MENUITEM_PRICE);
		menuItemShortDesc = bundle.getString(MenuItemsFragment.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION);
		menuItemLongDesc = bundle.getString(MenuItemsFragment.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION);
		boolean menuItemHasModifiers = bundle.getBoolean(MenuItemsFragment.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, false);

		initUI();

		if (menuItemHasModifiers == false)
			return;
		new RemoteLoadMenuTask().execute();
		//		try {
		//			Task.callInBackground(new Callable<MenuItemModifiersAndGroups>() {
		//				@Override
		//				public MenuItemModifiersAndGroups call() throws IOException
		//				{
		//					return MainActivity.mStoreendpoint
		//							.getStoreMenuItemModifiers(MainActivity.STORE_ID, menuItemPOSId).execute();
		//				}
		//			}).continueWith(new Continuation<MenuItemModifiersAndGroups, Void>() {
		//				@Override
		//				public Void then(Task<MenuItemModifiersAndGroups> task) throws Exception
		//				{
		//					if (task.isCancelled()) {
		//						logger.log(Level.WARNING, "Cancelled: Get Modifiers for Menu " + menuItemPOSId);
		//						// the save was cancelled.
		//					} else if (task.isFaulted()) {
		//						// the save failed.
		//						Exception error = task.getError();
		//						logger.log(Level.SEVERE, "FAILED: Get Modifiers for Menu " + menuItemPOSId + ". Error Message = " + error.getLocalizedMessage());
		//						error.printStackTrace();
		//					} else {
		//						// the object was saved successfully.
		//						menuItemModifiersAndGroups = task.getResult();
		//
		//						mAdapter = new MenuModGrpsAdapter();
		//						mAdapter.setup(MenuModGrpActivity.this, menuItemPOSId);
		//						mListView.setAdapter(mAdapter);
		//						mListView.invalidate();
		//					}
		//					return null;
		//				}
		//			});
		//		} catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode != SELECTED_MODIFIERS_RESULT_CODE || resultCode != RESULT_OK)
			return;

		ArrayList<Integer> modSelections = data.getIntegerArrayListExtra(SELECTED_MODIFIERS);
		String modDesc = data.getExtras().getString(SELECTED_MODIFIERS_DESC);
		long modPrice = data.getExtras().getLong(SELECTED_MODIFIERS_PRICE);
		if (modSelections == null || modSelections.size() == 0)
			return;
		mAdapter.setModGroupSelections(modSelections, modDesc, modPrice);
		setDescAndPriceWithChoices();
	}

	private void initUI()
	{
		imgFood = (ImageView) findViewById(R.id.image_food_item);
		txtName = (TextView) findViewById(R.id.txt_item_name);
		txtPrice = (TextView) findViewById(R.id.txt_item_price);
		txtDesc = (TextView) findViewById(R.id.txt_item_desc);

		txtPlus = (TextView) findViewById(R.id.txt_plus);
		txtMinus = (TextView) findViewById(R.id.txt_minus);
		txtQuantity = (TextView) findViewById(R.id.txt_quantity);
		txtSpecialInstructions = (TextView) findViewById(R.id.txt_special_instructions);
		mListView = (ListView) findViewById(R.id.list_menu_options);
		btnPlaceOrder = (Button) findViewById(R.id.btn_place_order);

		txtPlus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				String quantity = (String) txtQuantity.getText();
				Integer intQuant = Integer.parseInt(quantity);
				intQuant++;
				quantity = String.format(Locale.US, "%d", intQuant);
				txtQuantity.setText(quantity);
				setDescAndPriceWithChoices();
			}
		});

		txtMinus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				String quantity = (String) txtQuantity.getText();
				Integer intQuant = Integer.parseInt(quantity);
				if (intQuant > 1) {
					intQuant--;
					quantity = String.format(Locale.US, "%d", intQuant);
					txtQuantity.setText(quantity);
					setDescAndPriceWithChoices();
				}
			}
		});
		if (TextUtils.isEmpty(menuItemShortDesc) == false)
			txtName.setText(menuItemShortDesc);
		else
			txtName.setText("");

		txtPrice.setText(AsaanUtility.formatCentsToCurrency(menuItemPrice));
		String strQuantity = String.format(Locale.US, "%d", 1);
		txtQuantity.setText(strQuantity);
		txtDesc.setText("");
		txtSpecialInstructions.setText("");

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				mAdapter.listItemClick(position);
			}

		});

		btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				//				TextView txtPrice = (TextView)
				//						findViewById(R.id.txt_item_price);
				//				TextView txtDesc = (TextView)
				//						findViewById(R.id.txt_item_desc);
				//				TextView txtQuantity = (TextView)
				//						findViewById(R.id.txt_quantity);
				//				String strPrice = txtPrice.getText().toString();
				//				MainActivity.OrderItem oi = new MainActivity.OrderItem();
				//				oi.mi = mMenuItem;
				//				oi.basePrice = menuItemPriceFromSubMenu != 0 ?
				//						menuItemPriceFromSubMenu : mMenuItem.getPrice();
				//				String strQuantity = (String) txtQuantity.getText();
				//				oi.quantity = Long.parseLong(strQuantity);
				//				oi.finalPrice =
				//						AmountConversionUtils.parseAmountToCents(strPrice);
				//				oi.description = txtDesc.getText().toString();
				//				oi.selectedMods = mAdapter.getSelectedModifiers();
				//				oi.specialInstructions =
				//						txtSpecialInstructions.getText().toString();
				//				MainActivity.orderedItems.add(oi);
				finish();
			}
		});
	}

	public void setDescAndPriceWithChoices()
	{
		mAdapter.updatePriceAndDesc();

		TextView txtPrice = (TextView) findViewById(R.id.txt_item_price);
		TextView txtDesc = (TextView) findViewById(R.id.txt_item_desc);
		TextView txtQuantity = (TextView) findViewById(R.id.txt_quantity);

		String strQuantity = (String) txtQuantity.getText();
		long finalPrice = (menuItemPrice + mAdapter.getFinalPrice()) * Long.parseLong(strQuantity);

		txtPrice.setText(AsaanUtility.formatCentsToCurrency(finalPrice));
		txtDesc.setText(mAdapter.getFinalDesc());
	}

	public static class MenuModGrpsAdapter extends BaseAdapter
	{
		WeakReference<PlaceOrderActivity> weakActivity;
		List<ModifierGroup> modifierGroups = new ArrayList<ModifierGroup>();

		int listItemClickPosition = 0;

		static class ViewHolder
		{
			public TextView txtName;
			public TextView txtDescription;
		}

		static class ModifierGroup
		{
			public long posId;
			public String posShortDesc;
			public String posLongDesc;
			ArrayList<Integer> selectedModifiers;
			String selectedModifierDesc;
			long selectedModifierPrice;
		}

		@SuppressLint("UseSparseArrays")
		public void setup(PlaceOrderActivity menuModGrpActivity, long menuItemPOSId)
		{
			weakActivity = new WeakReference<PlaceOrderActivity>(menuModGrpActivity);
			if (PlaceOrderActivity.menuItemModifiersAndGroups == null ||
					PlaceOrderActivity.menuItemModifiersAndGroups.getModifierGroups() == null ||
					PlaceOrderActivity.menuItemModifiersAndGroups.getModifiers() == null)
				return;
			for (StoreMenuItemModifierGroup storeMenuItemModifierGroup : PlaceOrderActivity.menuItemModifiersAndGroups.getModifierGroups())
			{
				if (storeMenuItemModifierGroup.getMenuItemPOSId() == menuItemPOSId)
				{
					ModifierGroup modifierGroup = new ModifierGroup();
					modifierGroup.posId = storeMenuItemModifierGroup.getModifierGroupPOSId();
					modifierGroup.selectedModifiers = new ArrayList<Integer>();
					for (StoreMenuItemModifier modifier : PlaceOrderActivity.menuItemModifiersAndGroups.getModifiers())
					{
						if (modifier.getModifierGroupPOSId() == modifierGroup.posId)
						{
							modifierGroup.posShortDesc = modifier.getModifierGroupShortDescription();
							modifierGroup.posLongDesc = modifier.getModifierGroupLongDescription();
							break;
						}
					}
					modifierGroups.add(modifierGroup);
				}
			}
		}

		public void updatePriceAndDesc()
		{
			finalDesc = " ";
			finalPrice = 0;
			for (ModifierGroup modifierGroup : modifierGroups)
			{
				if (modifierGroup.selectedModifiers == null)
					continue;
				finalPrice += modifierGroup.selectedModifierPrice;
				if (finalDesc.contentEquals(" "))
					finalDesc = modifierGroup.selectedModifierDesc;
				else
					finalDesc += ", " + modifierGroup.selectedModifierDesc;
			}
		}

		public void setModGroupSelections(ArrayList<Integer> modSelections, String modDesc, long modPrice)
		{
			ModifierGroup modifierGroup = modifierGroups.get(listItemClickPosition);
			modifierGroup.selectedModifiers = modSelections;
			modifierGroup.selectedModifierDesc = modDesc;
			modifierGroup.selectedModifierPrice = modPrice;
		}

		public void listItemClick(int position)
		{
			/*listItemClickPosition = position;
			long selectedModGrpId = getItem(position).posId;
			Intent intent = new Intent(getActivity(), MenuModifierActivity.class);
			intent.putExtra(MenuItemsFragment.BUNDLE_KEY_MODIFIERGRP_ID, selectedModGrpId);
			ModifierGroup modifierGroup = modifierGroups.get(listItemClickPosition);
			intent.putIntegerArrayListExtra(PlaceOrderActivity.SELECTED_MODIFIERS, modifierGroup.selectedModifiers);
			getActivity().startActivityForResult(intent, SELECTED_MODIFIERS_RESULT_CODE);*/
		}

		PlaceOrderActivity getActivity()
		{
			return weakActivity.get();
		}

		@Getter
		long finalPrice;

		@Getter
		String finalDesc;

		@Override
		public int getCount()
		{
			return modifierGroups.size();
		}

		@Override
		public ModifierGroup getItem(int position)
		{
			return modifierGroups.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View rowView = convertView;
			if (rowView == null) {
				rowView = View.inflate(getActivity(), R.layout.menu_modifier_group_row, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.txtName = (TextView) rowView.findViewById(R.id.txt_item_name);
				viewHolder.txtDescription = (TextView) rowView.findViewById(R.id.txt_item_desc);
				rowView.setTag(viewHolder);
			}
			ViewHolder holder = (ViewHolder) rowView.getTag();

			holder.txtName.setText(getItem(position).posShortDesc);
			if (TextUtils.isEmpty(getItem(position).posLongDesc) == false)
			{
				holder.txtDescription.setVisibility(View.VISIBLE);
				holder.txtDescription.setText(getItem(position).posLongDesc);
			}
			else
				holder.txtDescription.setVisibility(View.GONE);

			return rowView;
		}

	}

	private class RemoteLoadMenuTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			long startTime = new Date().getTime();
			try
			{
				menuItemModifiersAndGroups = AsaanMainActivity.mStoreendpoint
						.getStoreMenuItemModifiers(AsaanMainActivity.STORE_ID, menuItemPOSId).execute();
				logger.log(Level.INFO, "execute elapsed Time = " + (new Date().getTime() - startTime));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			mAdapter = new MenuModGrpsAdapter();
			mAdapter.setup(PlaceOrderActivity.this, menuItemPOSId);
			mListView.setAdapter(mAdapter);
			//			mListView.invalidate();
			super.onPostExecute(result);
		}
	}
}
