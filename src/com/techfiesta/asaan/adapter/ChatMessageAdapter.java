package com.techfiesta.asaan.adapter;

import java.util.HashMap;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.google.android.gms.internal.lv;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

	long userId = -1;
	private Context mContext;
	private HashMap<Long, ChatUser> hashMap;
	long last_shown_msg_time = 0;
	long last_shown_user_id = 0;
	long five_min_in_mili = 1000 * 60 * 5;
	ImageLoader imageLoader;

	public ChatMessageAdapter(Context context, List<ChatMessage> list, HashMap<Long, ChatUser> userHashMap) {
		super(context, R.layout.row_chat, list);
		this.mContext = context;
		this.hashMap = userHashMap;
		imageLoader=new ImageLoader((Activity)context);
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		long id = getItem(position).getUserId();
		if (id == AsaanUtility.USER_ID)
			return 0;
		else
			return 1;

	}

	private class ViewHolder {
		TextView tvName;
		TextView tvDate;
		TextView tvMsg;
		ImageView ivPp;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			int id=getItemViewType(position);
			if (id == 0)
				convertView = mInflater.inflate(R.layout.row_chat_2, null);
			else
				convertView = mInflater.inflate(R.layout.row_chat, null);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tvMsg = (TextView) convertView.findViewById(R.id.tv_message);
			holder.ivPp = (ImageView) convertView.findViewById(R.id.iv_profile);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ChatMessage chatMessage = getItem(position);
		long time = chatMessage.getModifiedDate() - last_shown_msg_time;
		if (time < 0)
			time = -1 * time;
		if ((time < five_min_in_mili)) {
			last_shown_msg_time = chatMessage.getModifiedDate();
			holder.tvDate.setText("" + five_min_in_mili);
		}
		holder.tvMsg.setText(chatMessage.getTxtMessage());
		
		if (position - 1 >= 0) {
			ChatMessage temp=getItem(position-1);
			Log.e("MSG",""+temp.getUserId()+chatMessage.getUserId());
			if(temp.getUserId()==chatMessage.getUserId())
			{
			   holder.tvName.setVisibility(View.INVISIBLE);
			   Log.e("MSG","invisible");
			}
			else
			{
				holder.tvName.setVisibility(View.VISIBLE);
				holder.tvName.setText(""+chatMessage.getUserId());
			}
			
		} else if (position == 0) {
			ChatUser temp = hashMap.get(chatMessage.getUserId());
			holder.tvName.setText(temp.getName());
		}
		ChatUser chatUser = hashMap.get(chatMessage.getUserId());
		if(chatUser.getProfilePhotoUrl()!=null || !chatUser.getProfilePhotoUrl().equals("undefined") || !chatUser.getProfilePhotoUrl().equals("") )
			imageLoader.DisplayImage(chatUser.getProfilePhotoUrl(),holder.ivPp);
			
		return convertView;
	}

}
