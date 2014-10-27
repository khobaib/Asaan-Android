package com.techfiesta.asaan.adapter;

import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.techfiesta.asaan.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StoreListAdapter  extends ArrayAdapter<Store>{

	private Context mContext;
	private List<Store> storeList;
	public StoreListAdapter(Context context,List<Store> stores) {
		super(context,R.layout.restaurant_item_row,stores);
		this.mContext=context;
		this.storeList=stores;		
	}
	private class ViewHolder
	{
		ImageView ivPhoto;
		TextView tvName;
		TextView tvDescription;
		TextView tvDistance;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.restaurant_item_row,null);
			holder = new ViewHolder();
			holder.ivPhoto=(ImageView)convertView.findViewById(R.id.ivLogoRestaurantItem);
			holder.tvName=(TextView)convertView.findViewById(R.id.tvNameRestaurantItem);
			holder.tvDescription=(TextView)convertView.findViewById(R.id.tvTypeRestaurantItem);
			holder.tvDistance=(TextView)convertView.findViewById(R.id.tvDistanceRestaurantItem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Store store=storeList.get(position);
		holder.tvName.setText(store.getName());
		holder.tvDescription.setText(store.getDescription());
		return convertView;
	}

	

}
