package com.techfiesta.asaan.adapter;

import java.util.HashMap;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.lazylist.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatHistoryAdapter extends ArrayAdapter<ChatRoom> {
	private Context mContext;

	public ChatHistoryAdapter(Context context, List<ChatRoom> list) {
		super(context, R.layout.row_chat, list);
		this.mContext = context;

	}

	private class ViewHolder {
		TextView tvName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_simple_list, null);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvop);
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		holder.tvName.setText(getItem(position).getName());
		return convertView;
	}
}
