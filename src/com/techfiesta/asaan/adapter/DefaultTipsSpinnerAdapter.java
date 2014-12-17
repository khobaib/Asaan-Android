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

public class DefaultTipsSpinnerAdapter extends ArrayAdapter<Integer> {
	private Context mContext;

	public DefaultTipsSpinnerAdapter(Context context, int textViewResourceId, ArrayList<Integer> objects) {
		super(context, textViewResourceId, objects);
		this.mContext = context;

	}

	private class ViewHolder {
		TextView tv;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getDropDownView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.df_tips_spinner_row, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_quantity);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv.setText("" + getItem(position));

		return convertView;

	}
}
