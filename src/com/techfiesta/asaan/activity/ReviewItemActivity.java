package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveStoreItemReviews;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveStoreOrderReview;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ItemReview;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ItemReviewsArray;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.OrderReview;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeam;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.OrderHistoryItemAdapter;
import com.techfiesta.asaan.adapter.ReviewMenuItemAdapter;
import com.techfiesta.asaan.fragment.ReviewFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import asaan.dao.AddItem;

public class ReviewItemActivity extends BaseActivity {
	private ArrayList<AddItem> orderdItems = new ArrayList<AddItem>();
	private ListView lvOrderItems;
	private TextView tvName;
	String experience;
	int foodLike, ServiceLike;
	ArrayList<ItemReview> list = new ArrayList<>();
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ProgressDialog pdDialog;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_review);
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		lvOrderItems = (ListView) findViewById(R.id.lvItems);
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(AsaanUtility.selectedStoreOrder.getStoreName());
		pdDialog=new ProgressDialog(ReviewItemActivity.this);
		pdDialog.setMessage("Please wait...");
		getIntentData();
		createOrderItemList(AsaanUtility.selectedStoreOrder.getOrderDetails());
	}

	private void getIntentData() {
		Intent intent = getIntent();
		experience = intent.getStringExtra(Constants.ORDER_EXPERIENCE);
		foodLike = intent.getIntExtra(Constants.ORDER_FOOD_LIKE, -1);
		ServiceLike = intent.getIntExtra(Constants.ORDER_SERVICE_LIKE, -1);

	}

	private void createOrderItemList(String xmlDetails) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlDetails));

			Document doc = dBuilder.parse(is);

			NodeList entries = doc.getElementsByTagName("ENTRY");
			for (int i = 0; i < entries.getLength(); i++) {
				AddItem addItem = new AddItem();
				Element line = (Element) entries.item(i);
				String name = line.getAttribute("DISP_NAME");
				Log.e("ITEM_NAME", "" + name);

				addItem.setItem_name(name);
				long ITEMID = Long.valueOf(line.getAttribute("ITEMID"));
				addItem.setItem_id((int) ITEMID);
				orderdItems.add(addItem);

			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReviewMenuItemAdapter adapter = new ReviewMenuItemAdapter(ReviewItemActivity.this, orderdItems);
		lvOrderItems.setAdapter(adapter);

	}

	private class PostOrderReviewInServer extends AsyncTask<Void, Void, Void> {
		private boolean error = false;
		@Override
		protected void onPreExecute() {
			pdDialog.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			OrderReview orderReview = new OrderReview();
			if (experience != null)
				orderReview.setComments(experience);
			if (foodLike != -1)
				orderReview.setFoodLike(foodLike);
			if (ServiceLike != -1)
				orderReview.setServiceLike(ServiceLike);
			if(experience!=null || foodLike!=0 && ServiceLike!=0)
			{
				orderReview.setOrderId(AsaanUtility.selectedStoreOrder.getId());
				orderReview.setStoreId(AsaanUtility.selectedStoreOrder.getStoreId());
				
				try {
					SaveStoreOrderReview saveStoreOrderReview = SplashActivity.mStoreendpoint
							.saveStoreOrderReview(orderReview);
					HttpHeaders httpHeaders = saveStoreOrderReview.getRequestHeaders();
					httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
					orderReview = saveStoreOrderReview.execute();
					if (orderReview != null)
						Log.e("Order Review", orderReview.toPrettyString());
				} catch (IOException e) {
					error = true;
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(error)
			{
				if(pdDialog.isShowing())
					pdDialog.dismiss();
				AsaanUtility.simpleAlert(ReviewItemActivity.this, getResources().getString(R.string.error_alert));
			}
			else
				new PostOrderItemReviewInServer().execute();
			super.onPostExecute(result);
		}

	}

	private class PostOrderItemReviewInServer extends AsyncTask<Void, Void, Void> {
		private boolean error = false;

		@Override
		protected Void doInBackground(Void... params) {
			ItemReviewsArray itemReviewsArray = new ItemReviewsArray();

			for (int i = 0; i < orderdItems.size(); i++)
				if (orderdItems.get(i).getHasModifiers() != null) {
					ItemReview itemReview = new ItemReview();
					itemReview.setItemName(orderdItems.get(i).getItem_name());
					itemReview.setItemLike(orderdItems.get(i).getHasModifiers());
					itemReview.setMenuItemPOSId(orderdItems.get(i).getItem_id());
					itemReview.setStoreId(AsaanUtility.selectedStoreOrder.getStoreId());
					itemReview.setOrderId(AsaanUtility.selectedStoreOrder.getId());

					list.add(itemReview);
				}
			itemReviewsArray.setItemReviews(list);

			if (list.size() > 0) {
				try {
					SaveStoreItemReviews saveStoreItemReviews = SplashActivity.mStoreendpoint
							.saveStoreItemReviews(itemReviewsArray);
					//Log.e("request",saveStoreItemReviews.toString());
					HttpHeaders httpHeaders = saveStoreItemReviews.getRequestHeaders();
					httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
					saveStoreItemReviews.execute();
				} catch (IOException e) {
					error = false;
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(pdDialog.isShowing())
				pdDialog.dismiss();
			if(error)
				AsaanUtility.simpleAlert(ReviewItemActivity.this, getResources().getString(R.string.error_alert));
			else
				AsaanUtility.simpleAlert(ReviewItemActivity.this,"Review Posted.");
			super.onPostExecute(result);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.activity_review, menu);
		MenuItem item = menu.findItem(R.id.action_review);
		item.setTitle("Done");
		// else
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title

		if (item.getItemId() == R.id.action_review) {
			new PostOrderReviewInServer().execute();

		}
		else
			if(item.getItemId()==android.R.id.home)
			{
				finish();
				overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
			}
		return true;
	}

}
