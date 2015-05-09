package com.techfiesta.asaan.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AsaanUtility;

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
		ImageView ivImage;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		//if (convertView == null) {
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
			holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);

			convertView.setTag(holder);
		//} else {
		//	holder = (ViewHolder) convertView.getTag();
		//}
    
		ChatMessage chatMessage = getItem(position);
		ChatUser chatUser = hashMap.get(chatMessage.getUserId());
		long time = chatMessage.getCreatedDate() - last_shown_msg_time;
		if (time < 0)
			time = -1 * time;
		if ((time >five_min_in_mili)) {
			last_shown_msg_time = chatMessage.getCreatedDate();
			holder.tvDate.setVisibility(View.VISIBLE);
			holder.tvDate.setText(getFormattedDate(chatMessage.getCreatedDate())+" "+getFormattedTime(chatMessage.getCreatedDate()));
		}
		else
			holder.tvDate.setVisibility(View.INVISIBLE);
		
		
			
			
			
			
		  Log.e("MSG",""+time);
		String defMessage = mContext.getResources().getString(R.string.default_pic_message);
		if (chatMessage.getTxtMessage().equals(defMessage)) {
			holder.tvMsg.setVisibility(View.INVISIBLE);
			holder.ivImage.setVisibility(View.VISIBLE);
			imageLoader.DisplayImage(chatMessage.getFileMessage(), holder.ivImage);
		} else {
			holder.tvMsg.setVisibility(View.VISIBLE);
			holder.ivImage.setVisibility(View.INVISIBLE);
			holder.tvMsg.setText(chatMessage.getTxtMessage());
		}

		if (position - 1 >= 0) {
			ChatMessage temp=getItem(position-1);
			
			long prevID=temp.getUserId();
			long curID=chatMessage.getUserId();
			
			if(prevID==curID)
			{
			   holder.tvName.setVisibility(View.INVISIBLE);
			 
			}
			else
			{
				holder.tvName.setVisibility(View.VISIBLE);
				
				holder.tvName.setText(""+chatUser.getName());
			}
			
		} else if (position == 0) {
			ChatUser temp = hashMap.get(chatMessage.getUserId());
			holder.tvName.setVisibility(View.VISIBLE);
			holder.tvName.setText(temp.getName());
		}
		if(chatUser.getProfilePhotoUrl()!=null && !chatUser.getProfilePhotoUrl().equals("undefined") && !chatUser.getProfilePhotoUrl().equals("") )
			imageLoader.DisplayImage(chatUser.getProfilePhotoUrl(),holder.ivPp);
		
		return convertView;
	}
	private String getFormattedTime(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String sTime = sdf.format(new Date(rawTime));
		return sTime;
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}

}
