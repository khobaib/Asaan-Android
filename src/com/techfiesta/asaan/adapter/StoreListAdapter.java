package com.techfiesta.asaan.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.ModItemDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MenuActivity;
import com.techfiesta.asaan.activity.OnlineOrderActivity;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AsaanUtility;

public class StoreListAdapter extends ArrayAdapter<Store> {

	private Context mContext;
	private List<Store> storeList;
	private ImageLoader imageLoader;

	public StoreListAdapter(Context context, List<Store> stores) {
		super(context, R.layout.row_restaurant_list, stores);
		this.mContext = context;
		this.storeList = stores;
		imageLoader = new ImageLoader((Activity) context);
	}

	

	private class ViewHolder {
		// ParseImageView ivPhoto;
		ImageView ivPhoto;
		TextView tvName;
		TextView tvThrophy;
		TextView tvSubType;
		Button btnCall;
		Button btnReserve;
		Button btnMenu;
		Button btnorder;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_restaurant_list, null);
			holder = new ViewHolder();
			holder.ivPhoto = (ImageView) convertView.findViewById(R.id.restaurant_bg_image);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_restaurant_name);
			holder.tvThrophy = (TextView) convertView.findViewById(R.id.tv_first_trophy);
			holder.tvSubType = (TextView) convertView.findViewById(R.id.tv_subtype);
			holder.btnCall = (Button) convertView.findViewById(R.id.b_call);
			holder.btnReserve = (Button) convertView.findViewById(R.id.b_reserve);
			holder.btnMenu = (Button) convertView.findViewById(R.id.b_menu);
			holder.btnorder = (Button) convertView.findViewById(R.id.b_online_order);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Store store = storeList.get(position);

		// Log.e("url", "image url for id " + store.getId() + " = "
		// + ((store.getBackgroundImageUrl() == null) ? "null" :
		// store.getBackgroundImageUrl()));

		imageLoader.DisplayImage(store.getBackgroundImageUrl(), holder.ivPhoto);
		// downLoadBgImageFromPrase(holder.ivPhoto,store.getBackgroundImageUrl());

		holder.tvName.setText(store.getName());
		holder.tvSubType.setText(store.getSubType());
		if (store.getTrophies() != null && store.getTrophies().size() > 0) {
			holder.tvThrophy.setText(store.getTrophies().get(0));
		}

		holder.btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.e("MSG>>>", "btn menu clicked");
				Store selectedStore = getItem(position);
				if (AsaanUtility.getCurrentOrderedStoredId(mContext) == selectedStore.getId().intValue()
						|| AsaanUtility.getCurrentOrderedStoredId(mContext) == -1) {
					AsaanUtility.selectedStore = selectedStore;
					Intent intent = new Intent(mContext, MenuActivity.class);
					mContext.startActivity(intent);
				} else {
					alert(mContext, "Already have saved order from other restaurant.Delete all orders?");
				}

			}
		});
		holder.btnorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Store selectedStore = getItem(position);
				if (AsaanUtility.getCurrentOrderedStoredId(mContext) == selectedStore.getId().intValue()
						|| AsaanUtility.getCurrentOrderedStoredId(mContext) == -1) {
					AsaanUtility.selectedStore = selectedStore;
					Intent intent = new Intent(mContext, OnlineOrderActivity.class);
					mContext.startActivity(intent);
				} else {
					alert(mContext, "Already have saved order from other restaurant.Delete all orders?");
				}

				// int current = store.getId().intValue();
				// int savedId =
				// AsaanUtility.getCurrentOrderedStoredId(mContext);
				// Log.e("size", store.getId().intValue() + " saved  " +
				// savedId);
				// if (current == savedId) {
				// AsaanUtility.selectedStore = getItem(position);
				// Intent intent = new Intent(mContext, MyCartActivity.class);
				// mContext.startActivity(intent);
				// } else if (savedId == -1) {
				// simpleAlert(mContext, "You have no orders.");
				// } else
				// alert(mContext,
				// "Already have saved order from other restaurant.Delete all orders?");

			}
		});

		return convertView;
	}

	/*
	 * private void downLoadBgImageFromPrase(final ParseImageView iv,String
	 * objectId) { ParseQuery<ParseObject>
	 * query=ParseQuery.getQuery("PictureFiles");
	 * query.whereEqualTo("objectId",objectId); query.findInBackground(new
	 * FindCallback<ParseObject>() {
	 * 
	 * @Override public void done(List<ParseObject> list, ParseException e) {
	 * if(e==null && list.size()>0){ ParseObject obj=list.get(0); ParseFile
	 * file=obj.getParseFile("picture_file"); iv.setParseFile(file);
	 * iv.loadInBackground();
	 * 
	 * }
	 * 
	 * } });
	 * 
	 * }
	 */
	private void alert(final Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// deleting all orders
				OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "asaan-db", null);
				SQLiteDatabase db = helper.getWritableDatabase();
				DaoMaster daoMaster = new DaoMaster(db);
				DaoSession daoSession = daoMaster.newSession();
				AddItemDao addItemDao = daoSession.getAddItemDao();
				ModItemDao modItemDao = daoSession.getModItemDao();
				addItemDao.deleteAll();
				modItemDao.deleteAll();
				AsaanUtility.setCurrentOrderdStoreId(mContext, -1);
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

	private void simpleAlert(final Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setNeutralButton("Ok", null);

		bld.create().show();
	}
}
