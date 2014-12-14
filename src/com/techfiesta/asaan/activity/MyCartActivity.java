package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.MyCartListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.NestedListView;

public class MyCartActivity extends Activity {

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
	List<AddItem> OrderList;

	private TextView tvStoreName, tvSubtotal, tvGratuity, tvTax, tvTotal, tvAmountDue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		lvOrder = (NestedListView) findViewById(R.id.lv_items);

		tvStoreName = (TextView) findViewById(R.id.tv_store_name);
		tvSubtotal = (TextView) findViewById(R.id.tv_subtotal_amount);
		tvGratuity = (TextView) findViewById(R.id.tv_gratuity_amount);
		tvTax = (TextView) findViewById(R.id.tv_tax_amount);
		tvTotal = (TextView) findViewById(R.id.tv_total_amount);
		tvAmountDue = (TextView) findViewById(R.id.tv_due_amount);

		initDatabase();
		OrderList = addItemDao.queryBuilder().list();

		tvStoreName.setText(AsaanUtility.selectedStore.getName());

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

		adapter = new MyCartListAdapter(getApplicationContext(), R.layout.row_order, OrderList);
		lvOrder.setAdapter(adapter);

	}

	private void ConvertToXml() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element root = document.createElement("CHECKREQUESTS");
			root.setAttribute("ADDCHECK EXTCHECKID", "Nirav");
			root.setAttribute("READYTIME", "4:45PM");
			root.setAttribute("NOTE", "Please make it spicy - no Peanuts Please");
			root.setAttribute("ORDERMODE", "1");
			document.appendChild(root);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		modItemDao = daoSession.getModItemDao();
	}

	private class RemotePlaceOrderTask extends AsyncTask<String, Void, Void> {

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

	}
}
