package com.techfiesta.asaan.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techfiesta.asaan.R;

public class SimpleListAdapter extends ArrayAdapter<String> {

	private Context mContext;
	public SimpleListAdapter(Context context, int resource, ArrayList<String> objects) {
		super(context, resource, objects);
		this.mContext=context;
	}
	private class ViewHolder{
		TextView tvName;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    		if (convertView == null) {
    			
    				 convertView = mInflater.inflate(R.layout.row_simple_list, null);
    			
    			holder = new ViewHolder();
    			holder.tvName = (TextView) convertView.findViewById(R.id.tvop);
  
    			convertView.setTag(holder);
    		} else {
    			holder = (ViewHolder) convertView.getTag();
    		}
    	holder.tvName.setText(getItem(position));
  
            		
		
		return convertView;
	}

}
