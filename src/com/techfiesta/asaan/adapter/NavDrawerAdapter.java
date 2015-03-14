package com.techfiesta.asaan.adapter;

import java.util.List;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.model.NavMenuItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerAdapter extends ArrayAdapter<NavMenuItem>{

	private Context mContext;
	public NavDrawerAdapter(Context context, int resource, List<NavMenuItem> objects) {
		super(context, resource, objects);
		mContext=context;
		
	}
	private class ViewHolder{
		TextView tvName;
		ImageView ivIcon;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    		if (convertView == null) {
    			if(position==0)
    			    convertView = mInflater.inflate(R.layout.nav_menu_row_1, null);
    			else
    				 convertView = mInflater.inflate(R.layout.nav_menu_row, null);
    			
    			holder = new ViewHolder();
    			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
    			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);

    			convertView.setTag(holder);
    		} else {
    			holder = (ViewHolder) convertView.getTag();
    		}
    	holder.tvName.setText(getItem(position).getName());
    	holder.ivIcon.setImageResource(getItem(position).getId());
            		
		
		return convertView;
	}

}
