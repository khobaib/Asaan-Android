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
import android.widget.ImageView;
import android.widget.TextView;
import asaan.dao.AddItem;

public class OrderHistoryItemAdapter extends ArrayAdapter<AddItem> {
	
	private Context mContext;
	public OrderHistoryItemAdapter(Context context, List<AddItem> list) {
		super(context, R.layout.row_order_history_item, list);
		this.mContext = context;

	}
	private class ViewHolder {
		TextView tvName;
		TextView tvQuantity;
		TextView tvPrice;
		ImageView ivLike;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_order_history_item, null);

			holder = new ViewHolder();
			holder.tvQuantity = (TextView) convertView.findViewById(R.id.tv_item_qty);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_name);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_item_price);
			holder.ivLike = (ImageView) convertView.findViewById(R.id.iv_like);
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		AddItem addItem=getItem(position);
		holder.tvName.setText(addItem.getItem_name());
		holder.tvQuantity.setText(""+addItem.getQuantity());
		holder.tvPrice.setText("$"+((double)addItem.getPrice())/100);
			
		return convertView;
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}


}
