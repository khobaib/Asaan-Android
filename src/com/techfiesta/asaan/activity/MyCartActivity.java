package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DStore;
import asaan.dao.DStoreDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.ModItemDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.PlaceOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.PlaceOrderArguments;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.MyCartListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.HTMLFaxOrder;
import com.techfiesta.asaan.utility.NestedListView;
import com.techfiesta.asaan.utility.XMLPosOrder;


public class MyCartActivity extends Activity {

	private static final int ALERT_TYPE_PLACE_ORDER = 1;
	private static final int ALERT_TYPE_CANCEL_ORDER = 2;

	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	private ModItemDao modItemDao;
	private DStoreDao dStoreDao;
	 
	 private ActionBar actionBar;

	private NestedListView lvOrder;
	private MyCartListAdapter adapter;
	List<AddItem> orderList;

	private ProgressDialog pDialog;
	private Button bEdit,btnPlaceOrde,btnCancelOrder,btnPlus;
	private TextView tvStoreName, tvSubtotal, tvGratuity, tvTax, tvTotal, tvAmountDue,tvDeliveryTime;
	private int subtotalAmount;
	private RelativeLayout rlDiscount;
	private int MYCART_ACTIVITY_INDENTIFIER=100;
	private int REQUEST_CODE=1;
	private int RESULT_CODE=2;
	private long one_hour_in_mili=1000*60*60;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		lvOrder = (NestedListView) findViewById(R.id.lv_item_list);

		tvStoreName = (TextView) findViewById(R.id.tv_store_name);
		tvSubtotal = (TextView) findViewById(R.id.tv_subtotal_amount);
		tvGratuity = (TextView) findViewById(R.id.tv_gratuity_amount);
		tvTax = (TextView) findViewById(R.id.tv_tax_amount);
		tvTotal = (TextView) findViewById(R.id.tv_total_amount);
		tvAmountDue = (TextView) findViewById(R.id.tv_due_amount);
		tvDeliveryTime=(TextView)findViewById(R.id.tv_delivery_time);
		 btnPlaceOrde=(Button)findViewById(R.id.b_place_order);
		 btnCancelOrder=(Button)findViewById(R.id.b_calcel_order);

		pDialog = new ProgressDialog(this);
		

		bEdit = (Button) findViewById(R.id.b_edit);
		bEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MyCartActivity.this, EditCartActivity.class));
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}
		});
		btnPlus=(Button)findViewById(R.id.b_add);
		btnPlus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MyCartActivity.this,MenuActivityNew.class);
				long id=AsaanUtility.getCurrentOrderedStoredId(MyCartActivity.this);
				Store store=new Store();
				store.setId(id);
				
				int order_type=getOrderType();
				intent.putExtra(Constants.ORDER_TYPE,order_type);
				
				AsaanUtility.selectedStore=store;
				startActivity(intent);
				
			}
		});
		btnPlaceOrde.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickPlaceOrder(v);
				
			}
		});
		btnCancelOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickCancelOrder(v);
				
			}
		});
		rlDiscount=(RelativeLayout)findViewById(R.id.rl_discount);
		rlDiscount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MyCartActivity.this,DiscountActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				
			}
		});
    
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initDatabase();
		orderList = addItemDao.queryBuilder().list();
		Log.e(">>>", "List count" + addItemDao.count());
		closeDatabase();
		
		if(orderList.size()==0)
		{
		       finish();
		       return;
		}
		
        setStorName();
		subtotalAmount = 0;
		for (AddItem item : orderList) {
			subtotalAmount += item.getPrice();
		}
		String subtotal = "$" + String.format("%.2f", ((double) subtotalAmount / 100));
		tvSubtotal.setText(subtotal);

		double gratuity = ((double) subtotalAmount * 0.15) / 100;
		tvGratuity.setText("$" + String.format("%.2f", gratuity));

		double tax = 0.00;
		tvTax.setText("$" + String.format("%.2f", tax));

		double total = ((double) subtotalAmount / 100) + gratuity + tax;
		tvTotal.setText("$" + String.format("%.2f", total));

		tvAmountDue.setText("$" + String.format("%.2f", total));
		long estimatedtime=getEstimatedTime();
		tvDeliveryTime.setText(getFormattedTime(estimatedtime));

		adapter = new MyCartListAdapter(MyCartActivity.this, orderList, Constants.MY_CART_ACTIVITY);
		lvOrder.setAdapter(adapter);
		lvOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				AddItem addItem=orderList.get(position);
				long estimatedtime=getEstimatedTime();
				Intent intent=new Intent(MyCartActivity.this,OrderItemActivity.class);
				intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_POS_ID,addItem.getItem_id());
				intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_PRICE,addItem.getPrice());
				intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, convertHasModifiers(addItem.getHasModifiers()));
				intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,addItem.getItem_name());
				intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION,"");
				intent.putExtra(Constants.ORDER_TYPE,getOrderType());
				intent.putExtra(Constants.ESTIMATED_TIME,estimatedtime);
				intent.putExtra(Constants.KEY_FROM_ACTIVITY,1);
				intent.putExtra(Constants.KEY_QUANTITY,addItem.getQuantity());
				
				setSelectedStore(addItem);
				
				startActivity(intent);
			}
		});
	}
	private int getOrderType()
	{
		if(orderList.size()!=0)
		   return orderList.get(0).getOrder_type();
		return 1;
	}
	private boolean convertHasModifiers(int x)
	{
		return x==1?true:false;
	}

	private void setSelectedStore(AddItem addItem)
	{
		Store store=new Store();
		store.setId((long)addItem.getStore_id());
		store.setName(addItem.getStore_name());
		AsaanUtility.selectedStore=store;
	}


	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		modItemDao = daoSession.getModItemDao();
		dStoreDao=daoSession.getDStoreDao();
		
		
	}
	private void closeDatabase()
	{
		daoSession.getDatabase().close();
	}

	public void onClickPlaceOrder(View v){
		showAlert(ALERT_TYPE_PLACE_ORDER);
	}

	public void onClickCancelOrder(View v) {
		showAlert(ALERT_TYPE_CANCEL_ORDER);
	}

	private void showAlert(final int alertType) {
		AlertDialog Alert = new AlertDialog.Builder(this).create();
		Alert.setMessage("Confirm?");

		Alert.setButton(Constants.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				if (alertType == ALERT_TYPE_CANCEL_ORDER) {
					deleteFromDatabase();
					Intent i = new Intent(MyCartActivity.this, StoreListActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.prev_slide_out, R.anim.prev_slide_in);
					finish();

				} else {
					if(AsaanUtility.defCard==null)
					{
						Intent intent=new Intent(MyCartActivity.this,PaymentInfoActivity.class);
						intent.putExtra(Constants.KEY_FROM_ACTIVITY,MYCART_ACTIVITY_INDENTIFIER);
						startActivityForResult(intent,REQUEST_CODE);
					}
					else
					   new RemotePlaceOrderTask().execute();
				}
			}
		});
		if (alertType == ALERT_TYPE_CANCEL_ORDER) {
			Alert.setButton(Constants.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			});
		} else {
			Alert.setButton(Constants.BUTTON_NEGATIVE, "Maybe Later", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			});
		}
		Alert.show();
	}

	private long getOrderedStoreId() {
		return AsaanUtility.getCurrentOrderedStoredId(MyCartActivity.this);
	}
	private class RemotePlaceOrderTask extends AsyncTask<String, Void, Void> {

		private boolean error=false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setMessage("Please wait we are taking your order...");
			pDialog.show();
			
		}

		@Override
		protected Void doInBackground(String... params) {
			
			StoreOrder storeOrder=new StoreOrder();
			long id=getOrderedStoreId();
			
			initDatabase();
			DStore dStore=getOrderedStoreFromDatabase(id);
			closeDatabase();
			
			
			int guestSize=AsaanUtility.getCurrentPartySize(getApplicationContext());
			storeOrder.setGuestCount(guestSize);
			storeOrder.setOrderMode(getOrderType());
			storeOrder.setStoreId(id);
			storeOrder.setStoreName(orderList.get(0).getStore_name());
			storeOrder.setSubTotal((long)subtotalAmount);
			long tax=0;
			storeOrder.setTax(tax);
			double gratuity =  (subtotalAmount * 0.15) / 100;
			long lDeliveryFee =0;
			try{
				lDeliveryFee = (long)dStore.getDeliveryFee();
				storeOrder.setDeliveryFee(lDeliveryFee);
			}
			catch(Exception e)
			{	
				Log.e("order info failed","dStore.getDeliveryFee() failed.");
			}
			
			storeOrder.setServiceCharge((long)gratuity);
			long total=subtotalAmount+(long)gratuity+tax;
			storeOrder.setFinalTotal(total);
			
			PlaceOrderArguments orderArguments=new PlaceOrderArguments();
			if(AsaanUtility.defCard!=null)
			{
				orderArguments.setUserId(AsaanUtility.defCard.getUserId());
				orderArguments.setCardid(AsaanUtility.defCard.getCardId());
				orderArguments.setCustomerId(AsaanUtility.defCard.getProviderCustomerId());
			}
			//may need to change
			
			HTMLFaxOrder htmlFaxOrder=new HTMLFaxOrder();
			storeOrder.setOrderHTML(htmlFaxOrder.getOrderHTML(orderList));
			
			XMLPosOrder xmlPosOrder=new XMLPosOrder();
			storeOrder.setOrderDetails(xmlPosOrder.getXMlPOSOrder(guestSize, (long)subtotalAmount,(long) tax,(long)gratuity,lDeliveryFee,(long)total, orderList,"",-1,AsaanUtility.defCard.getProvider(),AsaanUtility.defCard.getLast4()));
			
			orderArguments.setOrder(storeOrder);
			try {
				PlaceOrder placeOrder=SplashActivity.mStoreendpoint.placeOrder(orderArguments);
				HttpHeaders httpHeaders = placeOrder.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				StoreOrder order=placeOrder.execute();
				if(order!=null)
				   Log.e("order Rresponse",order.toPrettyString());
			} catch (IOException e) {
			
				e.printStackTrace();
				error=true;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (error)
				AsaanUtility.simpleAlert(MyCartActivity.this, "An error occured");
			else {
				deleteFromDatabase();
				showDialogOrderPosted();
			}
		}
	}
	private DStore getOrderedStoreFromDatabase(Long id)
	{
		List<DStore> list=dStoreDao.queryBuilder().list();
		int size=list.size();
		for(int i=0;i<size;i++)
		{
			if(list.get(i).getId()==id)
			 return list.get(i);
		}
		
	 return null;
	}
	public long getEstimatedTime()
	{
		int size=orderList.size();
		long max=0;
		for(int i=0;i<size;i++)
		{
			if(orderList.get(i).getEstimated_time()>max)
				max=orderList.get(i).getEstimated_time();
		}
		long curTime=System.currentTimeMillis();
		if(curTime+one_hour_in_mili>max)
			max=curTime+one_hour_in_mili;
	  return max;
	}
	private String getFormattedTime(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String sTime = sdf.format(new Date(rawTime));
		return sTime;
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}
	
	private void deleteFromDatabase() {
		try {			
			initDatabase();
			addItemDao.deleteAll();
			modItemDao.deleteAll();
			closeDatabase();		
		}
		catch(Exception e)
		{
			 Log.e("Database Error","Failed to delete old orders");
		}
		
		AsaanUtility.setCurrentOrderdStoreId(MyCartActivity.this, -1);
		adapter.notifyDataSetChanged();
	}
	private void showDialogOrderPosted() {
		AlertDialog.Builder bld = new AlertDialog.Builder(MyCartActivity.this);
		bld.setTitle("Order Received!");
		bld.setMessage("Order is taken.");
		bld.setCancelable(false);
		bld.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(MyCartActivity.this, StoreListActivity.class);
				
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
			}
		});
		bld.create().show();
	}
	private void setStorName()
	{
		String shopName=orderList.get(0).getStore_name();
		tvStoreName.setText(shopName);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CODE && resultCode==RESULT_CODE)
		{
			new RemotePlaceOrderTask().execute();
		}
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	public boolean onOptionsItemSelected(MenuItem item) {

		 if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}

	
}
