package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.PlaceOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.MyCartListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.NestedListView;

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

	private NestedListView lvOrder;
	private MyCartListAdapter adapter;
	List<AddItem> orderList;

	private ProgressDialog pDialog;
	private Button bEdit;
	private TextView tvStoreName, tvSubtotal, tvGratuity, tvTax, tvTotal, tvAmountDue;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		lvOrder = (NestedListView) findViewById(R.id.lv_item_list);

		tvStoreName = (TextView) findViewById(R.id.tv_store_name);
		tvSubtotal = (TextView) findViewById(R.id.tv_subtotal_amount);
		tvGratuity = (TextView) findViewById(R.id.tv_gratuity_amount);
		tvTax = (TextView) findViewById(R.id.tv_tax_amount);
		tvTotal = (TextView) findViewById(R.id.tv_total_amount);
		tvAmountDue = (TextView) findViewById(R.id.tv_due_amount);

		pDialog = new ProgressDialog(this);
		

		bEdit = (Button) findViewById(R.id.b_edit);
		bEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MyCartActivity.this, EditCartActivity.class));
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}
		});
       long id=AsaanUtility.getCurrentOrderedStoredId(MyCartActivity.this);
       new GetStoreDetails(id).execute();
	   initDatabaseAndPopuateList();


	}

	@Override
	protected void onResume() {
		super.onResume();

		orderList = addItemDao.queryBuilder().list();
		Log.e(">>>", "List count" + addItemDao.count());

		int subtotalAmount = 0;
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

	private class RemotePlaceOrderTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setMessage("Please wait we are taking your order...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
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
			deleteAllPostedOrders();
			if (pDialog.isShowing())
				pDialog.dismiss();
			showDialogOrderPosted();
		}

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
	private class GetStoreDetails extends AsyncTask<Void,Void,Void>
	{
		long storeId;
		public GetStoreDetails(long id)
		{
			this.storeId=id;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.setMessage("please wait....");
			pDialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			try {
				AsaanUtility.selectedStore= SplashActivity.mStoreendpoint.getStore(storeId).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			tvStoreName.setText(AsaanUtility.selectedStore.getName());
			if(pDialog.isShowing())
				pDialog.dismiss();
		}
		
	}
}
