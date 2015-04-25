package com.techfiesta.asaan.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreOrder;
import com.techfiesta.asaan.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrderHistoryAdapter extends ArrayAdapter<StoreOrder> {
	
	private Context mContext;
	public OrderHistoryAdapter(Context context, List<StoreOrder> list) {
		super(context, R.layout.row_order_history, list);
		this.mContext = context;

	}
	private class ViewHolder {
		TextView tvName;
		TextView tvDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_order_history, null);

			holder = new ViewHolder();
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		StoreOrder storeOrder=getItem(position);
		holder.tvName.setText(storeOrder.getStoreName());
		holder.tvDate.setText(getFormattedDate(storeOrder.getCreatedDate()));
			
		return convertView;
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}


}
