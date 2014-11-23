package com.techfiesta.asaan.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteAbortException;
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
import com.techfiesta.asaan.activity.MyCartActivity;
import com.techfiesta.asaan.activity.PlaceOrderActivity;
import com.techfiesta.asaan.utility.AsaanUtility;

public class StoreListAdapter extends ArrayAdapter<Store> {

	private Context mContext;
	private List<Store> storeList;
	private Location mLocation;

	public StoreListAdapter(Context context, List<Store> stores) {
		super(context, R.layout.restaurant_item_row, stores);
		this.mContext = context;
		this.storeList = stores;
		this.mLocation = null;
	}
   public void setLocation(Location location)
   {
	   this.mLocation=location;
   }
	public StoreListAdapter(Context context, List<Store> stores, Location location) {
		super(context, R.layout.restaurant_item_row, stores);
		this.mContext = context;
		this.storeList = stores;
		this.mLocation = location;
	}

	private class ViewHolder {
		ImageView ivPhoto;
		TextView tvName;
		TextView tvDescription;
		TextView tvDistance;
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
			convertView = mInflater.inflate(R.layout.restaurant_item_row, null);
			holder = new ViewHolder();
			holder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivLogoRestaurantItem);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvNameRestaurantItem);
			holder.tvDescription = (TextView) convertView.findViewById(R.id.tvTypeRestaurantItem);
			holder.tvDistance = (TextView) convertView.findViewById(R.id.tvDistanceRestaurantItem);
			holder.btnCall=(Button)convertView.findViewById(R.id.btn_call);
			holder.btnReserve=(Button)convertView.findViewById(R.id.btn_reserve);
			holder.btnMenu=(Button)convertView.findViewById(R.id.btn_menu);
			holder.btnorder=(Button)convertView.findViewById(R.id.btn_order);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Store store = storeList.get(position);
		holder.tvName.setText(store.getName());
		holder.tvDescription.setText(store.getDescription());

		if (mLocation != null) {
			Location shopLoc = new Location("Shop Location");
			shopLoc.setLatitude(store.getLat());
			shopLoc.setLongitude(store.getLng());
			final double distInMeter = shopLoc.distanceTo(this.mLocation);
			Log.d(">>>>>>", "dist in meter =" + distInMeter);
			long distInMile = (long) (distInMeter / 1609.344);
			holder.tvDistance.setText(distInMile + "mile");
		}
		holder.btnMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.e("MSG>>>","btn menu clicked");
				AsaanUtility.selectedStore=getItem(position);
				Intent intent=new Intent(mContext,MenuActivity.class);
				mContext.startActivity(intent);
				
			}
		});
		holder.btnorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Store store=getItem(position);

				int current=store.getId().intValue();
				int savedId=AsaanUtility.getCurrentOrderedStoredId(mContext);
				Log.e("size",store.getId().intValue()+" saved  "+savedId);
				if(current==savedId)
				{
				AsaanUtility.selectedStore=getItem(position);
				Intent intent=new Intent(mContext,MyCartActivity.class);
				mContext.startActivity(intent);
				}
				else if(savedId==-1)
				{
					simpleAlert(mContext,"You have no orders.");
				}
				else
					alert(mContext,"Already have saved order from other restaurant.Delete all orders?");
				
				
			}
		});

		return convertView;
	}
	private void alert(final Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//deleting all orders
				OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "asaan-db", null);
		        SQLiteDatabase db = helper.getWritableDatabase();
		        DaoMaster  daoMaster = new DaoMaster(db);
		        DaoSession daoSession = daoMaster.newSession();
		        AddItemDao addItemDao = daoSession.getAddItemDao();
				ModItemDao modItemDao=daoSession.getModItemDao();
				addItemDao.deleteAll();
				modItemDao.deleteAll();
				AsaanUtility.setCurrentOrderdStoreId(mContext,-1);
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
		AlertDialog.Builder bld = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setNeutralButton("Ok",null);

		bld.create().show();
	}
}
