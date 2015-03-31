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
import android.widget.RelativeLayout;
import android.widget.TextView;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.ModItemDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreStats;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.ChatActivity;
import com.techfiesta.asaan.activity.MenuActivity;
import com.techfiesta.asaan.activity.MenuActivityNew;
import com.techfiesta.asaan.activity.OnlineOrderActivity;
import com.techfiesta.asaan.activity.ReserveActivity;
import com.techfiesta.asaan.activity.StoreListActivity;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AsaanUtility;

public class StoreListAdapter extends ArrayAdapter<Store> {

	private Context mContext;
	private List<Store> storeList;
	private List<StoreStats> storeStatsList;
	private ImageLoader imageLoader;

	public StoreListAdapter(Context context, List<Store> stores,List<StoreStats> storeStats) {
		super(context, R.layout.row_restaurant_list, stores);
		this.mContext = context;
		this.storeList = stores;
		this.storeStatsList=storeStats;
		imageLoader = new ImageLoader((Activity) context);
	}

	

	private class ViewHolder {
		// ParseImageView ivPhoto;
		ImageView ivPhoto;
		TextView tvName;
		TextView tvThrophy;
		TextView tvSubType;
		Button btnCall;
		Button btnChat;
		Button btnReserve;
		Button btnMenu;
		Button btnorder;
		TextView tvLikes;
		TextView tvVisitors;
		ImageView ivLikes;
		ImageView ivVisitors;
		RelativeLayout rl_stats;

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
			holder.btnChat=(Button)convertView.findViewById(R.id.b_chat);
			holder.btnReserve = (Button) convertView.findViewById(R.id.b_reserve);
			holder.btnMenu = (Button) convertView.findViewById(R.id.b_menu);
			holder.btnorder = (Button) convertView.findViewById(R.id.b_online_order);
			holder.rl_stats=(RelativeLayout)convertView.findViewById(R.id.rl_stats);
			holder.tvLikes=(TextView)convertView.findViewById(R.id.tv_likes);
			holder.tvVisitors=(TextView)convertView.findViewById(R.id.tv_visitors);
			holder.ivVisitors=(ImageView)convertView.findViewById(R.id.iv_visitors);
			holder.ivLikes=(ImageView)convertView.findViewById(R.id.iv_likes);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Store store = storeList.get(position);

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
		if(store.getClaimed()==null)
			 Log.e("MSG","null"+store.getName());
		if(store.getClaimed()!=null && store.getClaimed())
		{
			holder.btnChat.setVisibility(View.VISIBLE);
			holder.btnMenu.setVisibility(View.VISIBLE);
			holder.btnorder.setVisibility(View.VISIBLE);
			
			holder.btnChat.setText("Chat");
			holder.btnMenu.setText("Menu");
			holder.btnorder.setText("Online\nOrder");
			holder.btnReserve.setText("Reserve");
			

			holder.btnMenu.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View arg0) {
					Log.e("MSG>>>", "btn menu clicked");
					Store selectedStore = getItem(position);
					if (AsaanUtility.getCurrentOrderedStoredId(mContext) == selectedStore.getId().intValue()
							|| AsaanUtility.getCurrentOrderedStoredId(mContext) == -1) {
						AsaanUtility.selectedStore = selectedStore;
						Intent intent = new Intent(mContext, MenuActivityNew.class);
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
			
			holder.btnReserve.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(((Button)v).getText().equals("Reserve"))
					{
						Intent intent=new Intent(mContext,ReserveActivity.class);
						AsaanUtility.selectedStore=store;
						mContext.startActivity(intent);
						
					}
						
					
				}
			});
			holder.btnChat.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,ChatActivity.class);
					AsaanUtility.selectedStore=store;
					mContext.startActivity(intent);
					
				}
			});
		}
		else
		{
			holder.btnChat.setText("");
			holder.btnMenu.setText("");
			holder.btnorder.setText("");
			holder.btnReserve.setText("Claim\nStore");
		}

		StoreStats storeStats=getStats(store.getId());
		if(storeStats==null)
		{
			holder.rl_stats.setVisibility(View.GONE);
		}
		else
		{
			holder.rl_stats.setVisibility(View.VISIBLE);
			if(storeStats.getVisits()>0)
			{
				holder.tvVisitors.setVisibility(View.VISIBLE);
				holder.ivVisitors.setVisibility(View.VISIBLE);
		        holder.tvVisitors.setText(""+storeStats.getVisits());
			}
			else
			{
				holder.tvVisitors.setVisibility(View.GONE);
				holder.ivVisitors.setVisibility(View.GONE);
			}
			long count=storeStats.getFoodDislikes()+storeStats.getFoodLikes()+storeStats.getServiceDislikes()+storeStats.getServiceLikes();
			if(count>0)
			{
				holder.tvLikes.setVisibility(View.VISIBLE);
			   holder.ivLikes.setVisibility(View.VISIBLE);
			   long percent=((storeStats.getFoodLikes()+storeStats.getServiceLikes())*100)/count;
	           holder.tvLikes.setText(percent+"%"+"("+count/2+")");
				
			}
			else
			{
				holder.tvLikes.setVisibility(View.GONE);
				holder.ivLikes.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	private StoreStats getStats(long id)
	{
		if(storeStatsList!=null)
		{
		int size=storeStatsList.size();
		for(int i=0;i<size;i++)
		{
			if(storeStatsList.get(i).getStoreId()==id)
			     return storeStatsList.get(i);
		}
		}
		return null;
	}
	/*
	 * private void downLo adBgImageFromPrase(final ParseImageView iv,String
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
