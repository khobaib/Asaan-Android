package com.techfiesta.asaan.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.OrderReview;
import com.techfiesta.asaan.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewsAdapter extends ArrayAdapter<OrderReview>{
	private Context mContext;

	public ReviewsAdapter(Context context, List<OrderReview> list) {
		super(context, R.layout.row_past_review, list);
		this.mContext = context;

	}

	private class ViewHolder {
		ImageView ivFood;
		ImageView ivService;
		TextView tvDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_past_review, null);

			holder = new ViewHolder();
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			holder.ivFood = (ImageView) convertView.findViewById(R.id.iv_food_like);
			holder.ivService = (ImageView) convertView.findViewById(R.id.iv_service_like);
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		OrderReview orderReview=getItem(position);
		if(orderReview.getFoodLike()==0)
		{
			holder.ivFood.setImageResource(R.drawable.dislike);
		}
		else
			holder.ivFood.setImageResource(R.drawable.like);
		
		if(orderReview.getServiceLike()==0)
		{
			holder.ivService.setImageResource(R.drawable.dislike);
		}
		else
			holder.ivService.setImageResource(R.drawable.like);
		holder.tvDate.setText(getFormattedDate(orderReview.getCreatedDate()));
			
		return convertView;
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}

}
