package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.PlaceOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.google.api.client.http.HttpHeaders;
import com.techfiesta.asaan.R;
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

	private NestedListView lvOrder;
	private MyCartListAdapter adapter;
	List<AddItem> OrderList;

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
		lvOrder = (NestedListView) findViewById(R.id.lv_item_list);

		tvStoreName = (TextView) findViewById(R.id.tv_store_name);
		tvSubtotal = (TextView) findViewById(R.id.tv_subtotal_amount);
		tvGratuity = (TextView) findViewById(R.id.tv_gratuity_amount);
		tvTax = (TextView) findViewById(R.id.tv_tax_amount);
		tvTotal = (TextView) findViewById(R.id.tv_total_amount);
		tvAmountDue = (TextView) findViewById(R.id.tv_due_amount);

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait we are taking your order...");

		initDatabase();

		tvStoreName.setText(AsaanUtility.selectedStore.getName());
	}

	@Override
	protected void onResume() {
		super.onResume();

		updateCartInfo();
	}

	public void updateCartInfo() {
		OrderList = addItemDao.queryBuilder().list();

		int subtotalAmount = 0;
		for (AddItem item : OrderList) {
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

		adapter = new MyCartListAdapter(EditCartActivity.this, OrderList, Constants.EDIT_CART_ACTIVITY);
		lvOrder.setAdapter(adapter);
	}

	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		// modItemDao = daoSession.getModItemDao();
	}

	public void onClickPlaceOrder(View v) {
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
					Intent i = new Intent(EditCartActivity.this, StoreListActivity.class);
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

	private void deletefromDatabase() {
		initDatabase();
		addItemDao.deleteAll();
		AsaanUtility.setCurrentOrderdStoreId(EditCartActivity.this, -1);
		adapter.notifyDataSetChanged();
	}

}
