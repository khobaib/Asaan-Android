package com.techfiesta.asaan.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.techfiesta.asaan.R;

public class ExpandableMenuListAdapter extends BaseExpandableListAdapter{

	private Context mContext;
	private ArrayList<String> groupList;
	private HashMap<String,ArrayList<String>> childListMap;
	public ExpandableMenuListAdapter(Context context,ArrayList<String> groupList,HashMap<String,ArrayList<String>> childListMap)
	{
		this.mContext=context;
		this.groupList=groupList;
		this.childListMap=childListMap;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childListMap.get(groupList.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastchild, View convertView,
			ViewGroup parent) {
		 final String childText = (String) getChild(groupPosition, childPosition);
		 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.elv_list_row_child, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView
	                .findViewById(R.id.tv_child);
	 
	        txtListChild.setText(childText);
	        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childListMap.get(groupList.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpaned, View convertView, ViewGroup parent) {
		 String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.elv_list_row_group, null);
	        }
	 
	        TextView tvGropu = (TextView) convertView
	                .findViewById(R.id.tv_group);
	        
	        tvGropu.setText(headerTitle);
	        
	        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
