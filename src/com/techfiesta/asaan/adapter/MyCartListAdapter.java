package com.techfiesta.asaan.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import asaan.dao.AddItem;

import com.techfiesta.asaan.R;

public class MyCartListAdapter extends ArrayAdapter<AddItem> {

	private Context mContext;

	public MyCartListAdapter(Context context, int resource, List<AddItem> objects) {
		super(context, resource, objects);
		this.mContext = context;


	}

	private class ViewHolder {
		TextView tvItemName;
		TextView tvItemQty;
		TextView tvItemPrice;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_order, null);
			holder = new ViewHolder();
			holder.tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
			holder.tvItemQty = (TextView) convertView.findViewById(R.id.tv_item_qty);
			holder.tvItemPrice = (TextView) convertView.findViewById(R.id.tv_item_price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AddItem item = getItem(position);
		holder.tvItemName.setText(item.getItem_name());
		holder.tvItemQty.setText(item.getQuantity() + "");

		String price = "$" + String.format("%.2f", ((double) item.getPrice() / 100));
		holder.tvItemPrice.setText(price);
		// List<ModItem> list = item.getMod_items();
		// for (int i = 0; i < list.size(); i++) {
		// holder.tvItemName.setText(list.get(i).getItem_id() + "  " +
		// list.get(i).getQuantity() + "\n");
		// }
		return convertView;
	}

}
