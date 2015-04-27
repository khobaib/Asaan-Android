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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import asaan.dao.AddItem;

public class ReviewMenuItemAdapter extends ArrayAdapter<AddItem> {
	
	private Context mContext;
	public ReviewMenuItemAdapter(Context context, List<AddItem> list) {
		super(context, R.layout.row_item_review, list);
		this.mContext = context;

	}
	private class ViewHolder {
		TextView tvName;
		SeekBar seekBar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_item_review, null);

			holder = new ViewHolder();
			holder.seekBar = (SeekBar) convertView.findViewById(R.id.sbFood);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvFood);
			
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		final AddItem addItem=getItem(position);
		holder.tvName.setText(addItem.getItem_name());
		holder.seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress > 50)
					addItem.setHasModifiers(1);
				else if (progress < 50)
					addItem.setHasModifiers(0);

			}
		});
			
		return convertView;
	}


}
