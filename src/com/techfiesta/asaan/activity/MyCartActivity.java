package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.core.Main;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.PlaceOrder;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.google.android.gms.internal.in;
import com.google.api.client.http.HttpHeaders;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.MyCartListAdapter;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;
import asaan.dao.DaoMaster.OpenHelper;

public class MyCartActivity extends Activity {
	
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	 private SQLiteDatabase db;
		private DaoMaster daoMaster;
		private DaoSession daoSession;
		private AddItemDao addItemDao;
		private AddItem addItem;
		private ModItem modItem;
		private ModItemDao modItemDao;
		private ListView lvMyCart;
		private MyCartListAdapter adapter;
		List<AddItem> OrderList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_cart);
		lvMyCart=(ListView)findViewById(R.id.lv_orders);
		initDatabase();
		OrderList=addItemDao.queryBuilder().list();
		adapter=new MyCartListAdapter(getApplicationContext(),R.layout.lv_row,OrderList);
		lvMyCart.setAdapter(adapter);
		
	}
	private void ConvertToXml()
	{
		DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
			Document document=documentBuilder.newDocument();
			
			Element root= document.createElement("CHECKREQUESTS");
			root.setAttribute("ADDCHECK EXTCHECKID", "Nirav");
			root.setAttribute("READYTIME","4:45PM");
			root.setAttribute("NOTE","Please make it spicy - no Peanuts Please");
			root.setAttribute("ORDERMODE","1");
			document.appendChild(root);
			
			
	
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initDatabase()
	{
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        addItemDao = daoSession.getAddItemDao();
		modItemDao=daoSession.getModItemDao();
	}
	private class RemotePlaceOrderTask extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... params)
		{
			String strOrderFor = "Nirav";
			String strOrderReadyTime = "4:45PM";
			String strNote = "Please make it spicy - no Peanuts Please";
			String strOrder = "" + "<CHECKREQUESTS>" + "<ADDCHECK EXTCHECKID=\"" + strOrderFor + "\" READYTIME=\"" + strOrderReadyTime + "\" NOTE=\""
					+ strNote + "\" ORDERMODE=\"@ORDER_MODE\" >" + "<ITEMREQUESTS>" + "<ADDITEM QTY=\"1\" ITEMID=\"7007\" FOR=\"Nirav\" >"
					+ "<MODITEM ITEMID=\"90204\" />" + "</ADDITEM>" + "<ADDITEM QTY=\"1\" ITEMID=\"7007\" FOR=\"Khobaib\" >"
					+ "<MODITEM QTY=\"1\" ITEMID=\"90204\" />" + "<MODITEM QTY=\"1\" ITEMID=\"90201\" />" + "<MODITEM QTY=\"1\" ITEMID=\"90302\" />"
					+ "<MODITEM QTY=\"1\" ITEMID=\"91501\" />" + "</ADDITEM>" + "</ITEMREQUESTS>" + "</ADDCHECK>" + "</CHECKREQUESTS>";

			PlaceOrder PlaceOrderReq;
			try
			{
				PlaceOrderReq = AsaanSplashActivity.mStoreendpoint.placeOrder((long)1,1, strOrder);
				HttpHeaders headers = PlaceOrderReq.getRequestHeaders();
				headers.put(USER_AUTH_TOKEN_HEADER_NAME, params[0]);
				StoreOrder order = PlaceOrderReq.execute();
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}
}
