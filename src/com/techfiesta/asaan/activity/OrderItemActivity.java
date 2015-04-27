package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemModifiersAndGroups;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItemModifier;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItemModifierGroup;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class OrderItemActivity extends BaseActivity {
	ListView mListView = null;
	int menuItemPOSId = 0;
	int menuItemPrice = 0;
	String menuItemShortDesc, menuItemLongDesc;
	MenuModGrpsAdapter mAdapter = null;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	private AddItem addItem;
	private ModItem modItem;
	private ModItemDao modItemDao;

	private boolean menuItemHasModifiers;

	private static final Logger logger = Logger.getLogger(OrderItemActivity.class.getName());
	public static final int SELECTED_MODIFIERS_RESULT_CODE = 9600;
	public static final String SELECTED_MODIFIERS = "SELECTED_MODIFIERS";
	public static final String SELECTED_MODIFIERS_DESC = "SELECTED_MODIFIERS_DESC";
	public static final String SELECTED_MODIFIERS_PRICE = "SELECTED_MODIFIERS_PRICE";

	public static MenuItemModifiersAndGroups menuItemModifiersAndGroups;

	
	TextView txtName = null;
	TextView txtPrice = null;
	TextView txtPlus = null;
	TextView txtMinus = null;
	TextView txtQuantity = null;
	TextView Title;
	EditText txtSpecialInstructions;
	Button btnPlaceOrder = null;
    private ActionBar actionBar;
    private long curTime=-1;
    private int order_type=-1;
    private int from_activity=-1;
    private int quantity=1;
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_item);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		getIntentData();
		initUI();
		if (menuItemHasModifiers)
		   new RemoteLoadMenuTask().execute();
		
	}
	private void getIntentData()
	{
		Bundle bundle = getIntent().getExtras();
		menuItemPOSId = bundle.getInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID);
		menuItemPrice = bundle.getInt(Constants.BUNDLE_KEY_MENUITEM_PRICE);
		Log.e("PRICE", "" + menuItemPrice + "  " + menuItemPOSId);
		menuItemShortDesc = bundle.getString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION);
		menuItemLongDesc = bundle.getString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION);
		menuItemHasModifiers = bundle.getBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, false);
		curTime=bundle.getLong(Constants.ESTIMATED_TIME,-1);
		order_type=bundle.getInt(Constants.ORDER_TYPE,-1);
		from_activity=bundle.getInt(Constants.KEY_FROM_ACTIVITY,-1);
		quantity=bundle.getInt(Constants.KEY_QUANTITY,1);
		actionBar.setTitle(menuItemShortDesc);
	
	}
	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		modItemDao = daoSession.getModItemDao();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

	private void initUI() {
	
		txtName = (TextView) findViewById(R.id.txt_item_name);
		txtPrice = (TextView) findViewById(R.id.txt_item_price);
		

		txtPlus = (TextView) findViewById(R.id.txt_plus);
		txtMinus = (TextView) findViewById(R.id.txt_minus);
		txtQuantity = (TextView) findViewById(R.id.txt_quantity);
		
		txtSpecialInstructions = (EditText) findViewById(R.id.et_special_instructions);
		mListView = (ListView) findViewById(R.id.list_menu_options);
		btnPlaceOrder = (Button) findViewById(R.id.btn_place_order);

		btnPlaceOrder.setText(btnPlaceOrder.getText() + " " + AsaanUtility.formatCentsToCurrency(menuItemPrice));

		txtPlus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
			public void onClick(View v) {
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
		String strQuantity = String.format(Locale.US, "%d",quantity);
		txtQuantity.setText(strQuantity);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mAdapter.listItemClick(position);
			}

		});

		btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int current = AsaanUtility.getCurrentOrderedStoredId(OrderItemActivity.this);
				if (current == AsaanUtility.selectedStore.getId().intValue() || current == -1) {
					initDatabase();
					long count = addItemDao.count();
					int quantity = Integer.parseInt(txtQuantity.getText().toString());
					int total_cost;
					if (menuItemHasModifiers) {
						total_cost = (int) (menuItemPrice + mAdapter.getFinalPrice()) * quantity;
						Log.e("MSG", "" + mAdapter.getFinalPrice());
					} else
						total_cost = (menuItemPrice) * quantity;
					
					addItem=new AddItem();
					addItem.setStore_id(AsaanUtility.selectedStore.getId().intValue());
					addItem.setStore_name(AsaanUtility.selectedStore.getName());
					addItem.setPrice(total_cost);
					addItem.setQuantity(quantity);
					addItem.setItem_name(menuItemShortDesc);
					addItem.setItem_id(menuItemPOSId);
					addItem.setNotes(txtSpecialInstructions.getText().toString());
					addItem.setEstimated_time(curTime);
					addItem.setOrder_type(order_type);
					if(menuItemHasModifiers)
						addItem.setHasModifiers(1);
					else
						addItem.setHasModifiers(0);
					
					//addItem = new AddItem(count + 1, AsaanUtility.selectedStore.getId().intValue(), total_cost,
						//	menuItemShortDesc, quantity, menuItemPOSId, txtSpecialInstructions.getText().toString());
					addItemDao.insert(addItem);
					
					
					// to do
					if (menuItemHasModifiers) {
						ArrayList<ModItem> list = mAdapter.getSelecteedModifiersList((int)getMaxID()+1);
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								modItemDao.insert(list.get(i));
							}
						}
					}
					toast("Order saved");
					AsaanUtility.setCurrentOrderdStoreId(OrderItemActivity.this, AsaanUtility.selectedStore.getId()
							.intValue());
					finish();
				} else
					alert(OrderItemActivity.this,
							"Already have saved order from other restaurant.Delete previous entries?");

				// code for adding sub items;

			}
		});
	}
private void saveItem()
{
	
}
private void updateItem()
{
	
}
	private void toast(String str) {
		Toast.makeText(OrderItemActivity.this, str, Toast.LENGTH_LONG).show();
	}
	private long getMaxID()
	{
		List<AddItem> list=addItemDao.queryBuilder().list();
		long max=list.get(0).getId();
		AddItem addItem=list.get(0);
		for(int i=0;i<list.size();i++)
		{
			if(max<list.get(i).getId())
			{
				 max=list.get(i).getId();
				 addItem=list.get(i);
			}
		}
		return addItem.getId();
	}

	public void setDescAndPriceWithChoices() {
		if (menuItemHasModifiers && mAdapter!=null)
			mAdapter.updatePriceAndDesc();

		// TextView txtPrice = (TextView) findViewById(R.id.txt_item_price);
		// TextView txtDesc = (TextView) findViewById(R.id.txt_item_desc);
		TextView txtQuantity = (TextView) findViewById(R.id.txt_quantity);

		String strQuantity = (String) txtQuantity.getText();
		long finalPrice;
		if (menuItemHasModifiers && mAdapter!=null)
			finalPrice = (menuItemPrice + mAdapter.getFinalPrice()) * Long.parseLong(strQuantity);
		else
			finalPrice = (menuItemPrice) * Long.parseLong(strQuantity);

		// txtPrice.setText(AsaanUtility.formatCentsToCurrency(finalPrice));
		btnPlaceOrder.setText("Add to Order " + AsaanUtility.formatCentsToCurrency(finalPrice));
		txtPrice.setText("Add to Order " + AsaanUtility.formatCentsToCurrency(finalPrice));
		// if (menuItemHasModifiers)
		// txtDesc.setText(mAdapter.getFinalDesc());
	}

	public static class MenuModGrpsAdapter extends BaseAdapter {
		WeakReference<OrderItemActivity> weakActivity;
		List<ModifierGroup> modifierGroups = new ArrayList<ModifierGroup>();

		int listItemClickPosition = 0;
       long finalPrice = 0;
       String finalDesc="";
		static class ViewHolder {
			public TextView txtName;
			public TextView txtDescription;
		}

		static class ModifierGroup {
			public long posId;
			public String posShortDesc;
			public String posLongDesc;
			ArrayList<Integer> selectedModifiers;
			String selectedModifierDesc;
			long selectedModifierPrice;
		}

		@SuppressLint("UseSparseArrays")
		public void setup(OrderItemActivity menuModGrpActivity, long menuItemPOSId) {
			weakActivity = new WeakReference<OrderItemActivity>(menuModGrpActivity);
			if (OrderItemActivity.menuItemModifiersAndGroups == null
					|| OrderItemActivity.menuItemModifiersAndGroups.getModifierGroups() == null
					|| OrderItemActivity.menuItemModifiersAndGroups.getModifiers() == null)
				return;
			for (StoreMenuItemModifierGroup storeMenuItemModifierGroup : OrderItemActivity.menuItemModifiersAndGroups
					.getModifierGroups()) {
				if (storeMenuItemModifierGroup.getMenuItemPOSId() == menuItemPOSId) {
					ModifierGroup modifierGroup = new ModifierGroup();
					modifierGroup.posId = storeMenuItemModifierGroup.getModifierGroupPOSId();
					modifierGroup.selectedModifiers = new ArrayList<Integer>();
					for (StoreMenuItemModifier modifier : OrderItemActivity.menuItemModifiersAndGroups.getModifiers()) {
						if (modifier.getModifierGroupPOSId() == modifierGroup.posId) {
							modifierGroup.posShortDesc = modifier.getShortDescription();
							modifierGroup.posLongDesc = modifier.getLongDescription();
							break;
						}
					}
					modifierGroups.add(modifierGroup);
				}
			}
		}

		public void updatePriceAndDesc() {
			finalDesc = " ";
			finalPrice = 0;
			for (ModifierGroup modifierGroup : modifierGroups) {
				if (modifierGroup.selectedModifiers == null)
					continue;
				finalPrice += modifierGroup.selectedModifierPrice;
				if (finalDesc.contentEquals(" "))
					finalDesc = modifierGroup.selectedModifierDesc;
				else
					finalDesc += ", " + modifierGroup.selectedModifierDesc;
			}
		}

		private ArrayList<ModItem> getSelecteedModifiersList(int parent_id) {
			ArrayList<ModItem> modItemList = new ArrayList<ModItem>();
			for (ModifierGroup modifierGroup : modifierGroups) {
				if (modifierGroup.selectedModifiers == null)
					continue;
				ModItem modItem = new ModItem(-1, (int) modifierGroup.posId, parent_id,
						modifierGroup.selectedModifierDesc, (int) modifierGroup.selectedModifierPrice);
				modItemList.add(modItem);

			}

			return modItemList;
		}

		public void setModGroupSelections(ArrayList<Integer> modSelections, String modDesc, long modPrice) {
			ModifierGroup modifierGroup = modifierGroups.get(listItemClickPosition);
			modifierGroup.selectedModifiers = modSelections;
			modifierGroup.selectedModifierDesc = modDesc;
			modifierGroup.selectedModifierPrice = modPrice;
		}

		public void listItemClick(int position) {
			Log.e(">>>>", "position = " + position);
			listItemClickPosition = position;
			long selectedModGrpId = getItem(position).posId;

			Intent intent = new Intent(getActivity(), MenuModifierActivity.class);
			intent.putExtra(MenuItemsFragment.BUNDLE_KEY_MODIFIERGRP_ID, selectedModGrpId);
			ModifierGroup modifierGroup = modifierGroups.get(listItemClickPosition);
			intent.putIntegerArrayListExtra(OrderItemActivity.SELECTED_MODIFIERS, modifierGroup.selectedModifiers);
			getActivity().startActivityForResult(intent, SELECTED_MODIFIERS_RESULT_CODE);

		}

		OrderItemActivity getActivity() {
			return weakActivity.get();
		}

		private long getFinalPrice()
		{
		 return finalPrice;

		}
		private String getFinalDescription()
		{
		return finalDesc;
		}

		@Override
		public int getCount() {
			return modifierGroups.size();
		}

		@Override
		public ModifierGroup getItem(int position) {
			return modifierGroups.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				rowView = View.inflate(getActivity(), R.layout.row_menu_modifier_group, null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.txtName = (TextView) rowView.findViewById(R.id.txt_item_name);
				viewHolder.txtDescription = (TextView) rowView.findViewById(R.id.txt_item_desc);
				rowView.setTag(viewHolder);
			}
			ViewHolder holder = (ViewHolder) rowView.getTag();

			holder.txtName.setText(getItem(position).posShortDesc);
			if (TextUtils.isEmpty(getItem(position).posLongDesc) == false) {
				holder.txtDescription.setVisibility(View.VISIBLE);
				holder.txtDescription.setText(getItem(position).posLongDesc);
			} else
				holder.txtDescription.setVisibility(View.GONE);

			return rowView;
		}

	}

	private class RemoteLoadMenuTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			long startTime = new Date().getTime();
			try {

				//Log.e(">>>>>>>", "store id = " + MainActivity.STORE_ID + " menuItemPOSId = " + menuItemPOSId);
				menuItemModifiersAndGroups = SplashActivity.mStoreendpoint.getStoreMenuItemModifiers(menuItemPOSId, AsaanUtility.selectedStore.getId()).execute();
				logger.log(Level.INFO, "execute elapsed Time = " + (new Date().getTime() - startTime));
//				Log.e("size", "" + menuItemModifiersAndGroups.getModifiers().size());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mAdapter = new MenuModGrpsAdapter();
			mAdapter.setup(OrderItemActivity.this, menuItemPOSId);
			mListView.setAdapter(mAdapter);
			// mListView.invalidate();
			super.onPostExecute(result);
		}
	}

	private void alert(final Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteDatabase();
				dialog.dismiss();
			}
		});
		bld.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});

		bld.create().show();
	}
	@Override
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}
	private void deleteDatabase() {
		initDatabase();
		addItemDao.deleteAll();
		modItemDao.deleteAll();
		AsaanUtility.setCurrentOrderdStoreId(OrderItemActivity.this, -1);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		initDatabase();
		if(addItemDao.count()>0)
			getMenuInflater().inflate(R.menu.activity_menu, menu);
		else
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==R.id.action_cart)
		{
			Intent intent=new Intent(OrderItemActivity.this,MyCartActivity.class);
			startActivity(intent);
		}
		else if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}
}
