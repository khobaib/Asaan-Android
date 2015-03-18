package com.techfiesta.asaan.adapter;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AmountConversionUtils;
import com.techfiesta.asaan.utility.Constants;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class MenuItemsAdapter extends ArrayAdapter<MenuItemAndStats> implements StickyListHeadersAdapter,SectionIndexer{

	private Context mContext;
	private List<MenuItemAndStats> menuItemAndStats;
	List<MenuItemAndStats> sectionsList;
	ArrayList<Integer> sectionIndexList;
	ImageLoader imageLoader;
	public MenuItemsAdapter(Context context,List<MenuItemAndStats> objects,List<MenuItemAndStats> allsections,ArrayList<Integer> indexList)
	{		super(context,R.layout.menu_item2,objects);
	   this.mContext=context;
	   this.menuItemAndStats=objects;
	   this.sectionsList=allsections;
	   this.sectionIndexList=indexList;
	   Log.e("indexlist",""+sectionIndexList.size());
	   imageLoader=new ImageLoader((Activity)context);
		
	}
	/*private ArrayList<MenuItemAndStats> createSections()
	{
		ArrayList<MenuItemAndStats> sections=new ArrayList<>();
		for(int i=0;i<menuItemAndStats.size();i++)
	       if(menuItemAndStats.get(i).getMenuItem().getLevel()==Constants.ROW_TYPE_SUBMENU)
	       {
	    	   sections.add(menuItemAndStats.get(i));
	    	  
	       }
		 Log.e("Sections",""+sections.size());
		return sections;
	}
	private ArrayList<Integer> createSectionIndex()
	{
		ArrayList<Integer> list=new ArrayList<Integer>();
		list.add(0);
		int subMenuPosId=menuItemAndStats.get(0).getMenuItem().getSubMenuPOSId();
		for(int i=1;i<menuItemAndStats.size();i++)
			if(menuItemAndStats.get(i).getMenuItem().getSubMenuPOSId()!=subMenuPosId)
			{
				list.add(i);
				subMenuPosId=menuItemAndStats.get(i).getMenuItem().getSubMenuPOSId();
			}
		return list;
			
	
	}*/
	@Override
	public Object[] getSections() {
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<sectionsList.size();i++)
		{
			list.add(sectionsList.get(i).getMenuItem().getShortDescription());
			Log.e("SECTION",sectionsList.get(i).getMenuItem().getShortDescription());
		}
		return list.toArray();
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		
		if(sectionIndex >= sectionIndexList.size() )
			return sectionIndexList.get(sectionIndexList.size()-1);
		else if(sectionIndex<0)
			return sectionIndexList.get(0);
		else
		  return sectionIndexList.get(sectionIndex);
	}

	@Override
	public int getSectionForPosition(int position) {
		for(int i=0;i<sectionIndexList.size();i++)
			if(position<sectionIndexList.get(i))
				return i-1;
		return sectionIndexList.size()-1;
	}
	static class ViewHolder {
		public ImageView imgFood;
		public TextView txtName;
		public TextView txtPrice;
		public TextView txtDesc;
		// public TextView txtLike;
		// public TextView txtOrderedToday;
		// public ImageView imgLike;
		public ImageView imgVegetarian;
		public ImageView imgSpicy;
		public ImageView imgGlutenFree;
	}

	static class ViewHolder2 {
		// public ImageView imgGroup;
		public TextView txtGroupName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		final MenuItemAndStats  menuItemAndStat = menuItemAndStats.get(position);
		StoreMenuItem storeMenuItem=menuItemAndStat.getMenuItem();
		if (rowView == null )
		{
				rowView = View.inflate(getContext(), R.layout.menu_item2, null);
				final ViewHolder viewHolder = new ViewHolder();
				viewHolder.imgFood = (ImageView) rowView.findViewById(R.id.image_food_item);
				viewHolder.txtName = (TextView) rowView.findViewById(R.id.txt_item_name);
				viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.txt_item_price);
				viewHolder.txtDesc = (TextView) rowView.findViewById(R.id.txt_item_desc);
				viewHolder.imgVegetarian = (ImageView) rowView.findViewById(R.id.image_vegetarian);
				viewHolder.imgSpicy = (ImageView) rowView.findViewById(R.id.image_spicy);
				viewHolder.imgGlutenFree = (ImageView) rowView.findViewById(R.id.image_glutenfree);
				rowView.setTag(viewHolder);
			}
		
		else {
			final ViewHolder holder = (ViewHolder) rowView.getTag();

			Log.e("short descriptipn",""+ storeMenuItem.getMenuName());
			holder.txtName.setText(storeMenuItem.getShortDescription());
			holder.txtDesc.setText(storeMenuItem.getLongDescription());
			holder.txtPrice.setText(AmountConversionUtils.formatCentsToCurrency(storeMenuItem.getPrice()));
			imageLoader.DisplayImage(storeMenuItem.getThumbnailUrl(), holder.imgFood);
			// holder.imgFood.setVisibility(View.GONE);

			holder.imgFood.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				/*	MenuActivity.selectedMenuItem = storeMenuItem;
					Intent i = new Intent(context, MenuItemDetailsActivity.class);
					i.putExtra("selected_menu_item", storeMenuItem.toString());
					context.startActivity(i);
					Log.d("???", "Food image clicked" + storeMenuItem.getLongDescription());
*/
				}
			});
		}

		return rowView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder2 viewHolder=null;
		int sectionPos=getSectionForPosition(position);
		StoreMenuItem storeMenuItem=sectionsList.get(sectionPos).getMenuItem();
		Log.e("header",""+"header called"+sectionsList.get(sectionPos).getMenuItem().getShortDescription()+sectionPos+" pos"+position);
		if (rowView == null)
		{
			rowView = View.inflate(getContext(), R.layout.menu_item_group2, null);
			 viewHolder = new ViewHolder2();
			viewHolder.txtGroupName = (TextView) rowView.findViewById(R.id.menu_category_title);

			rowView.setTag(viewHolder);	
		}
		else{
			 viewHolder = (ViewHolder2) rowView.getTag();
		}
		
		viewHolder.txtGroupName.setText(storeMenuItem.getShortDescription());
         
    
		return rowView;
	}

	@Override
	public long getHeaderId(int position) {
		int pos=getSectionForPosition(position);
		/*Log.e("position",""+position); 
		Log.e("sec_position",""+pos);
		Log.e("MSG",""+getPositionForSection(pos));*/
		return getPositionForSection(pos);
	}

}
