package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.ModItem;
import asaan.dao.ModItemDao;
import asaan.dao.DaoMaster.OpenHelper;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class OrderDetailsActivity extends Activity {
	private OrderDetailsAdapter mAdapter;
	public List<AddItem> orderList;

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	private AddItem addItem;
	private ModItem modItem;
	private ModItemDao modItemDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.order_details);
		final Button btnPlaceOrder = (Button) this.findViewById(R.id.btn_place_order);
		final ListView listView = (ListView) this.findViewById(R.id.list_ordered_items);
		initDatabaseAndPopuateList();
		mAdapter = new OrderDetailsAdapter();
		mAdapter.setup(this);
		listView.setAdapter(mAdapter);

		btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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

				new DownloadFilesTask().execute(orderXML);
			}
		});
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

	private class OrderDetailsAdapter extends BaseAdapter {
		WeakReference<OrderDetailsActivity> weakActivity;

		OrderDetailsActivity getActivity() {
			return weakActivity.get();
		}

		public void setup(OrderDetailsActivity activity) {
			weakActivity = new WeakReference<OrderDetailsActivity>(activity);
		}

		private class ViewHolder {
			public TextView txtName;
			public TextView txtDescription;
			public TextView txtPrice;
			public TextView txtQuantity;
			public TextView txtInstructions;
		}

		@Override
		public int getCount() {
			return orderList.size();
		}

		@Override
		public AddItem getItem(int position) {
			// TODO Auto-generated method stub
			return orderList.get(position);
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
				final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.order_details_row, null);
				final ViewHolder viewHolder = new ViewHolder();
				viewHolder.txtName = (TextView) rowView.findViewById(R.id.txt_item_name);
				viewHolder.txtDescription = (TextView) rowView.findViewById(R.id.txt_item_description);
				viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.txt_item_price);
				viewHolder.txtQuantity = (TextView) rowView.findViewById(R.id.txt_item_quantity);
				viewHolder.txtInstructions = (TextView) rowView.findViewById(R.id.txt_item_instructions);
				rowView.setTag(viewHolder);
			}
			final ViewHolder holder = (ViewHolder) rowView.getTag();

			final AddItem addItem = orderList.get(position);

			holder.txtName.setText(addItem.getItem_name());
			holder.txtPrice.setText(AsaanUtility.formatCentsToCurrency(addItem.getPrice()));
			holder.txtQuantity.setText("" + addItem.getQuantity());

			if (addItem.getOrder_for().equals("")) {
				holder.txtInstructions.setText("");
				holder.txtInstructions.setVisibility(View.GONE);

			} else {
				holder.txtInstructions.setText(addItem.getOrder_for());
				holder.txtInstructions.setVisibility(View.VISIBLE);
			}
			return rowView;
		}

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