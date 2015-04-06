package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DStoreDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.PlaceOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.google.android.gms.internal.dd;
import com.google.android.gms.internal.it;
import com.google.android.gms.internal.mi;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.MyCartListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.NestedListView;

import de.greenrobot.dao.query.WhereCondition;

public class MyCartActivity extends Activity {

	private static final int ALERT_TYPE_PLACE_ORDER = 1;
	private static final int ALERT_TYPE_CANCEL_ORDER = 2;

	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	private AddItem addItem;
	private ModItem modItem;
	 private ModItemDao modItemDao;
	 
	 private ActionBar actionBar;

	private NestedListView lvOrder;
	private MyCartListAdapter adapter;
	List<AddItem> orderList;

	private ProgressDialog pDialog;
	private Button bEdit,btnPlaceOrde,btnCancelOrder,btnPlus;
	private TextView tvStoreName, tvSubtotal, tvGratuity, tvTax, tvTotal, tvAmountDue,tvDeliveryTime;
	private int subtotalAmount;
	private String endString="";
	private RelativeLayout rlDiscount;
	
	private String beginingString = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
			"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> <html xmlns=\"http://www.w3.org/1999/xhtml\">" +
			" <head>" +
			" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> " +
			"<title>New Savoir Order</title> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> " +
			"</head> " +
			"<body style=\"margin: 0; padding: 0; \"> <table align=\"center\" border=\"0\" cellpadding=\"10px\" cellspacing=\"0\" width=\"900px\">" +
			" <tr> <td align=\"right\" style=\"padding: 10px 10px 10px 10px;\">" +
			"<img src=\"http://static1.squarespace.com/static/54ce8734e4b08a9c05c30098/t/54e545d5e4b052bf9dad58df/1425522195820/?format=1500w\" /> " +
			"</td> </tr>" +
			" <tr> <td align=\"center\" style=\"font-family: Arial, sans-serif; font-size: 24px; padding: 10px 10px 10px 10px;\"> <b>New Savoir Order</b> </td>" +
			" </tr> <tr> <td style=\"font-family: Arial, sans-serif; font-size: 14px;\"> " +
			"<table border=\"0\" cellpadding=\"10px\" cellspacing=\"0\" width=\"100%%\"> " +
			"<tr> <td width=\"60%%\" valign=\"top\">Name: <b>%s</b></td> " +
			"<td width=\"40%%\" valign=\"top\">To: <b>%s</b></td> </tr> " +
			"<tr> <td width=\"60%%\" valign=\"top\">Phone: <b>%s</b></td>" +
			" <td width=\"40%%\" valign=\"top\">Order #: <b>%s</b></td> </tr> " +
			"<tr> <td width=\"60%%\" valign=\"top\">Email: <b>%s</b></td>" +
			" <td width=\"40%%\" valign=\"top\">Order Type: <b>%s</b></td> </tr>" +
			" <tr> <td width=\"60%%\" valign=\"top\">Address: <b>%s</b></td> " +
			"<td width=\"40%%\" valign=\"top\">Placed: <b>%s</b></td> </tr>" +
			" <tr> <td width=\"60%%\" valign=\"top\"></td> " +
			"<td width=\"40%%\" valign=\"top\">Prepaid: <b>%s</b></td> </tr> " +
			"<tr> <td width=\"60%%\" valign=\"top\">" +
			"</td> <td width=\"40%%\" style=\"font-family: Arial, sans-serif; font-size: 24px;\" valign=\"top\">Expected Time: <b><i><u>%s</u></i></b></td> " +
			"</tr> </table> </td> </tr> " +
			"<tr> <td style=\"font-family: Arial, sans-serif; font-size: 14px;\"> <table border=\"1\" cellpadding=\"10px\" cellspacing=\"0\" width=\"100%%\">" +
			" <tr> <th width=\"35%%\" valign=\"top\">Product</th> <th width=\"20%%\" valign=\"top\">Options</th> <th width=\"20%%\" valign=\"top\">Notes</th> <th width=\"10%%\" valign=\"top\">Quantity</th> <th width=\"15%%\" valign=\"top\">Total</th> </tr>";
	private String table_row="<tr> <td width=\"35%%\" valign=\"top\"><b></b><br>%s</td>" +
			" <td width=\"20%%\" valign=\"top\">%s</td> " +
			"<td width=\"20%%\" valign=\"top\">%s</td>" +
			" <td width=\"10%%\" valign=\"top\" align=\"right\">%s</td> " +
			"<td width=\"15%%\" valign=\"top\" align=\"right\">%s</td> </tr>";
	
	
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
				if(orderList.size()!=0)
				{
					store.setName(orderList.get(0).getStore_name());
					int order_type=orderList.get(0).getOrder_type();
					intent.putExtra(Constants.ORDER_TYPE,order_type);
				}
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
       long id=AsaanUtility.getCurrentOrderedStoredId(MyCartActivity.this);
	   initDatabaseAndPopuateList();


	}

	@Override
	protected void onResume() {
		super.onResume();
		orderList = addItemDao.queryBuilder().list();
		Log.e(">>>", "List count" + addItemDao.count());
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
		long max=orderList.get(0).getEstimated_time();
		
		for(int i=1;i<orderList.size();i++)
			if(max<orderList.get(i).getEstimated_time())
				max=orderList.get(i).getEstimated_time();
		tvDeliveryTime.setText(getFormattedTime(max));

		adapter = new MyCartListAdapter(MyCartActivity.this, orderList, Constants.MY_CART_ACTIVITY);
		lvOrder.setAdapter(adapter);
	}

	


	private void initDatabaseAndPopuateList() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		modItemDao = daoSession.getModItemDao();
		orderList = addItemDao.queryBuilder().list();
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
					deletefromDatabase();
					Intent i = new Intent(MyCartActivity.this, StoreListActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.prev_slide_out, R.anim.prev_slide_in);
					finish();

				} else {
					if(AsaanUtility.defCard==null)
					{
						Intent intent=new Intent(MyCartActivity.this,PaymentInfoActivity.class);
						startActivity(intent);
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

	private long getStoreId() {
		return AsaanUtility.getCurrentOrderedStoredId(MyCartActivity.this);
	}
	private int getOrderType()
	{
		return orderList.get(0).getOrder_type();
	}
	private class RemotePlaceOrderTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setMessage("Please wait we are taking your order...");
			pDialog.show();
			
		}

		@Override
		protected Void doInBackground(String... params) {
			
			StoreOrder storeOrder=new StoreOrder();
			
			storeOrder.setGuestCount(AsaanUtility.getCurrentPartySize(getApplicationContext()));
			storeOrder.setOrderMode(getOrderType());
			storeOrder.setStoreId(getStoreId());
			storeOrder.setStoreName(orderList.get(0).getStore_name());
			storeOrder.setSubTotal((long)subtotalAmount);
			long tax=0;
			storeOrder.setTax(tax);
			double gratuity =  (subtotalAmount * 0.15) / 100;
			storeOrder.setDeliveryFee((long)gratuity);
			storeOrder.setServiceCharge((long)0);
			storeOrder.setFinalTotal(subtotalAmount+(long)gratuity+tax);
			
			
			/*String strOrderFor = "Khobaib";
			String strOrderReadyTime = "4:45PM";
			String strNote = "Please make it spicy - no Peanuts Please";
			String strOrder = "" + "<CHECKREQUESTS>" + "<ADDCHECK EXTCHECKID=\"" + strOrderFor + "\" READYTIME=\""
					+ strOrderReadyTime + "\" NOTE=\"" + strNote + "\" ORDERMODE=\"@ORDER_MODE\" >" + "<ITEMREQUESTS>"
					+ getOrderString()+ "</ITEMREQUESTS>" + "</ADDCHECK>" + "</CHECKREQUESTS>";
			
			Log.e("Order String", strOrder);

			PlaceOrder PlaceOrderReq;
			try {
				String token = ParseUser.getCurrentUser().getString("authToken");

				PlaceOrderReq = SplashActivity.mStoreendpoint.placeOrder((long) 1, 1, strOrder);
				HttpHeaders headers = PlaceOrderReq.getRequestHeaders();
				headers.put(USER_AUTH_TOKEN_HEADER_NAME, token);
				StoreOrder order = PlaceOrderReq.execute();
				


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			//deleteAllPostedOrders();
			if (pDialog.isShowing())
				pDialog.dismiss();
			
			showDialogOrderPosted();
		}
		private void setUpBeginingString()
		{
			String userName=ParseUser.getCurrentUser().get("firstName")+" "+ ParseUser.getCurrentUser().get("lastName");
			String to=AsaanUtility.selectedStore.getName();
			String phone="phone";
			if(ParseUser.getCurrentUser().get("phone")!=null)
			   phone=ParseUser.getCurrentUser().get("phone").toString();
			String order="ORDER ID";
			String email=ParseUser.getCurrentUser().getEmail();
			String orderType="TEMP";
			String address="";
			if(ParseUser.getCurrentUser().get("address")!=null)
				address=ParseUser.getCurrentUser().get("address").toString();
			long mili=System.currentTimeMillis();
			String placed=getFormattedDate(mili)+" at "+getFormattedTime(mili);
			String prepaid="YES";
			String expctedTime="PLACEHOLDER";
			//Log.e("STRING",userName+to+phone+order+email+orderType+address+placed+expctedTime);
			
			beginingString=String.format(beginingString,userName,to,phone,order,email,orderType,address,placed,prepaid,expctedTime);
			Log.e("STRING",beginingString);
		}
		private void createRowsStrings()
		{
			int i,size=orderList.size();
			for(i=0;i<size;i++)
			{
				String options="";
			   if(orderList.get(i).getMod_items()!=null && orderList.get(i).getMod_items().size()>0)
				   options=orderList.get(i).getMod_items().get(0).getName();
			   
				String row=String.format(table_row,orderList.get(i).getItem_name(),options,orderList.get(i).getNotes(),orderList.get(i).getQuantity(),orderList.get(i).getPrice());
				beginingString+=row;
			}
			
		}
		private String getOrderHTML()
		{
			setUpBeginingString();
			createRowsStrings();
			return beginingString+endString;
		}
		

	}
	public String getFormattedTime(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String sTime = sdf.format(new Date(rawTime));
		return sTime;
	}
	public String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}

	private void deletefromDatabase() {
		addItemDao.deleteAll();
		AsaanUtility.setCurrentOrderdStoreId(MyCartActivity.this, -1);
		adapter.notifyDataSetChanged();
	}
	private void deleteAllPostedOrders()
	{
		// deleting all orders
		OpenHelper helper = new DaoMaster.DevOpenHelper(MyCartActivity.this, "asaan-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		AddItemDao addItemDao = daoSession.getAddItemDao();
		ModItemDao modItemDao = daoSession.getModItemDao();
		addItemDao.deleteAll();
		modItemDao.deleteAll();
		AsaanUtility.setCurrentOrderdStoreId(MyCartActivity.this, -1);
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
