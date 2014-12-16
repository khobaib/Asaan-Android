package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.PlaceOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MyCartActivity.DownloadFilesTask;
import com.techfiesta.asaan.adapter.MyCartListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;
import com.techfiesta.asaan.utility.NestedListView;

public class EditCartActivity extends Activity {
	
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
	private Button bDone;
	private TextView tvStoreName, tvSubtotal, tvGratuity, tvTax, tvTotal, tvAmountDue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		bDone = (Button) findViewById(R.id.b_edit);
		bDone.setText("Done");
		bDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
			}
		});
		
		lvOrder = (NestedListView) findViewById(R.id.lv_items);

		tvStoreName = (TextView) findViewById(R.id.tv_store_name);
		tvSubtotal = (TextView) findViewById(R.id.tv_subtotal_amount);
		tvGratuity = (TextView) findViewById(R.id.tv_gratuity_amount);
		tvTax = (TextView) findViewById(R.id.tv_tax_amount);
		tvTotal = (TextView) findViewById(R.id.tv_total_amount);
		tvAmountDue = (TextView) findViewById(R.id.tv_due_amount);
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait we are taking your order...");

		initDatabaseAndPopuateList();

		tvStoreName.setText(AsaanUtility.selectedStore.getName());
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		updateCartInfo();
	}
	
	private String getOrderString()
	  {
		  String orderXML = "<POSREQUEST token=\"1234567890\"><CHECKREQUESTS><ADDCHECK ORDERMODE=\""
					+ Constants.ORDER_TYPE_DELIVERY + "\">" + "<ITEMREQUESTS>";

			for (AddItem addItem : orderList) {
				orderXML += "<ADDITEM QTY =\"" + addItem.getQuantity() + "\" ITEMID=\"" + addItem.getItem_id()
						+ "\">";

				for (ModItem mod : addItem.getMod_items())
					orderXML += "<MODITEM ITEMID=\"" + mod.getItem_id() + "\">";
				orderXML += "</ADDITEM>";
			}
			orderXML += "</ITEMREQUESTS></ADDCHECK></CHECKREQUESTS></POSREQUEST>";
			
			return orderXML;

	  }
	public void updateCartInfo(){
		orderList = addItemDao.queryBuilder().list();
		
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
		
		adapter = new MyCartListAdapter(EditCartActivity.this, orderList, Constants.EDIT_CART_ACTIVITY);
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

	public void onClickCancelOrder(View v){
		showAlert(ALERT_TYPE_CANCEL_ORDER);
	}


	private void showAlert(final int alertType){
		AlertDialog Alert = new AlertDialog.Builder(this).create();		          
		Alert.setMessage("Confirm?");	

		Alert.setButton(Constants.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				if(alertType == ALERT_TYPE_CANCEL_ORDER){
					// finish to store list & delete the order data from db
				} else {
					String orderString=getOrderString();
					new  DownloadFilesTask().execute(orderString);
				}
			}
		});
		if(alertType == ALERT_TYPE_CANCEL_ORDER){
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
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			String strOrderFor = "Nirav";
			String strOrderReadyTime = "4:45PM";
			String strNote = "Please make it spicy - no Peanuts Please";
			String strOrder = "" + "<CHECKREQUESTS>" + "<ADDCHECK EXTCHECKID=\"" + strOrderFor + "\" READYTIME=\""
					+ strOrderReadyTime + "\" NOTE=\"" + strNote + "\" ORDERMODE=\"@ORDER_MODE\" >" + "<ITEMREQUESTS>"
					+ "<ADDITEM QTY=\"1\" ITEMID=\"7007\" FOR=\"Nirav\" >" + "<MODITEM ITEMID=\"90204\" />"
					+ "</ADDITEM>" + "<ADDITEM QTY=\"1\" ITEMID=\"7007\" FOR=\"Khobaib\" >"
					+ "<MODITEM QTY=\"1\" ITEMID=\"90204\" />" + "<MODITEM QTY=\"1\" ITEMID=\"90201\" />"
					+ "<MODITEM QTY=\"1\" ITEMID=\"90302\" />" + "<MODITEM QTY=\"1\" ITEMID=\"91501\" />"
					+ "</ADDITEM>" + "</ITEMREQUESTS>" + "</ADDCHECK>" + "</CHECKREQUESTS>";

			PlaceOrder PlaceOrderReq;
			try {
				PlaceOrderReq = SplashActivity.mStoreendpoint.placeOrder((long) 1, 1, strOrder);
				HttpHeaders headers = PlaceOrderReq.getRequestHeaders();
				headers.put(USER_AUTH_TOKEN_HEADER_NAME, params[0]);
				StoreOrder order = PlaceOrderReq.execute();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
		}
	}
	class DownloadFilesTask extends AsyncTask<String, Void, Long> {
		@Override
		protected Long doInBackground(String... xmlstr) {

			// int count = xmlstr.length;

			final HttpClient httpClient = new DefaultHttpClient();

			final HttpPost httpPost = new HttpPost("http://98.213.233.241:81");
			// HttpPost httpPost = new HttpPost("http://192.168.1.30:81");
			// HttpPost httpPost = new HttpPost("98.213.233.241", 81, "http");
			// httpPost.setHeader(HTTP.CONTENT_TYPE,"text/xml;charset=UTF-8");
			// httpPost.setHeader(HTTP.,"text/xml; charset-utf8");
			// httpPost.setHeader(HTTP.CONTENT_LEN,
			// Integer.toString(xmlstr[0].length()));
			try {
				// Add your data
				StringEntity se = new StringEntity(xmlstr[0], HTTP.UTF_8);
				se.setContentType("text/xml");
				httpPost.setEntity(se);

				// Execute HTTP Post Request
				HttpResponse response = httpClient.execute(httpPost);

				HttpEntity entity = response.getEntity();
				String responseString = EntityUtils.toString(entity, "UTF-8");
				System.out.println(responseString);
			} catch (final ClientProtocolException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			} catch (final Exception e) {
				e.printStackTrace();
			}

			return (long) 0;
		}

		@Override
		protected void onPostExecute(Long feed) {
			// TODO: check this.exception
			// TODO: do something with the feed
		}
	}	

}
