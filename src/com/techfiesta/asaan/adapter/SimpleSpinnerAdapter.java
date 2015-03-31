package com.techfiesta.asaan.adapter;

import java.util.ArrayList;

import com.techfiesta.asaan.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleSpinnerAdapter extends ArrayAdapter<String> {
	private Context mContext;
	public SimpleSpinnerAdapter(Context context, int textViewResourceId,
			ArrayList<String> objects) {
		super(context, textViewResourceId, objects);
		this.mContext=context;
		
	}
	private class ViewHolder {
		TextView tv;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_spinner_submenu, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_quantity);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv.setText(""+getItem(position));
			
		
		return convertView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_spinner_submenu, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_quantity);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv.setText(""+getItem(position));
			
		
		return convertView;
		
	}
}
