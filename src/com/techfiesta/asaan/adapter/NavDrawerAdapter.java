package com.techfiesta.asaan.adapter;

import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetReviewForCurrentUserAndOrder;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.model.NavMenuItem;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.DaoMaster.OpenHelper;

public class NavDrawerAdapter extends ArrayAdapter<NavMenuItem>{

	private Context mContext;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
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
    	if(!checkPendingOrders() && position==4)
    		holder.tvName.setTextColor(mContext.getResources().getColor(R.color.ash));
            		
		
		return convertView;
	}
	private boolean checkPendingOrders()
	{
		initDatabase();
		if(addItemDao.count()>0)
			 return true;
		else
			return false;
	}
	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		
	}
	

}
