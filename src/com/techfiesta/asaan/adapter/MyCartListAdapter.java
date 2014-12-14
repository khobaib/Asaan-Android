package com.techfiesta.asaan.adapter;

import java.util.ArrayList;
import java.util.List;

import com.techfiesta.asaan.R;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.ModItem;

public class MyCartListAdapter extends ArrayAdapter<AddItem> {

	private Context mcContext;

	public MyCartListAdapter(Context context, int resource, List<AddItem> objects) {
		super(context, resource, objects);
		this.mcContext = context;

	}

	private class ViewHolder {
		TextView tvH;
		TextView tv;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mcContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.lv_row, null);
			holder = new ViewHolder();
			holder.tvH = (TextView) convertView.findViewById(R.id.tvH);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AddItem item = getItem(position);
		holder.tvH.setText("Store Id:" + item.getStore_id() + "\nPrice: " + item.getPrice() + "\nName: "
				+ item.getItem_id() + "\n" + "Quantity: " + item.getQuantity() + "\n comment: " + item.getOrder_for());
		List<ModItem> list = item.getMod_items();
		for (int i = 0; i < list.size(); i++) {
			holder.tv.setText(list.get(i).getItem_id() + "  " + list.get(i).getQuantity() + "\n");
		}
		return convertView;
	}

}
