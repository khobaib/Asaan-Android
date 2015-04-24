package com.techfiesta.asaan.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStatsCollection;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreItemStats;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuHierarchy;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MenuActivityNew;
import com.techfiesta.asaan.activity.MenuFlowActivity;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.interfaces.ScrollToIndexListener;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AmountConversionUtils;
import com.techfiesta.asaan.utility.AsaanMenuHolder;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.Spinner;
import android.widget.TextView;

public class MenuItemsAdapter extends ArrayAdapter<MenuItemAndStats> implements StickyListHeadersAdapter,SectionIndexer{

	private Context mContext;
	private List<MenuItemAndStats> menuItemAndStats;
	List<StoreMenuHierarchy> sectionsList;
	ArrayList<Integer> sectionIndexList;
	ImageLoader imageLoader;
	private int orderType;
	ScrollToIndexListener scrollToIndexListener=null;
	AsaanMenuHolder menuHolder;
	ArrayList<String> strHeadings;
	int menuPOSId;
	int firstRequestPos = 0;
	private int MAX_RESULT = 50;
	MenuItemAndStatsCollection menuItemAndStatsCollection=null;
	boolean bLoading = false;
	public MenuItemsAdapter(Context context,int menuPOSId, List<MenuItemAndStats> objects, int order_type,MenuItemsFragment menuItemsFragment)
	{		
	   super(context, R.layout.menu_item2, objects);
	   menuHolder = MenuActivityNew.menuMap.get(menuPOSId);
	   this.menuPOSId = menuPOSId;
	   
	   this.mContext=context;
	   this.menuItemAndStats=objects;
	   
	   sectionsList = menuHolder.subMenus;
	   sectionIndexList=new ArrayList<Integer>();
	   int sumPosition =0;
	   for( int i=0; i<sectionsList.size(); i++)
	   {	
		   if(sectionsList.get(i).getMenuItemCount()>0)
		   {
			   sumPosition +=sectionsList.get(i).getMenuItemCount();		   
			   Log.e("MenuItemsAdapter", "Submenu Name:" + sectionsList.get(i).getName() + " item counts: " + sectionsList.get(i).getMenuItemCount() );
			   sectionIndexList.add(sumPosition);
		   }
	   }
	   Log.e("indexlist",""+sectionIndexList.size());
	   imageLoader=new ImageLoader((Activity)context);
	   this.orderType=order_type;
	   scrollToIndexListener=menuItemsFragment;
	   
	   strHeadings = new ArrayList<String>();
	   for(int i=0;i<sectionsList.size();i++)
		{
		   if(sectionsList.get(i).getMenuItemCount()>0)
		   {
			   strHeadings.add(sectionsList.get(i).getName());
				Log.e("SECTION",sectionsList.get(i).getName());
		   }
		}
	}

	@Override
	public Object[] getSections() {		
		return strHeadings.toArray();
	}
	public ArrayList<String> getSectionsHeaders() {
		return strHeadings;
	}
	@Override
	public int getPositionForSection(int sectionIndex) {
		if(sectionIndex<=0)
			return 0;
		
		if(sectionIndex >= sectionIndexList.size() )
			return sectionIndexList.get(sectionIndexList.size()-1);
	
		return sectionIndexList.get(sectionIndex-1);
	}

	@Override
	public int getSectionForPosition(int position) {
		for(int i=0;i<sectionIndexList.size();i++)
			if(position<sectionIndexList.get(i))
				return i;
		return sectionIndexList.size()-1;
	}
	static class ViewHolder {
		public ImageView imgFood;
		public TextView txtName;
		public TextView txtPrice;
		public TextView txtDesc;
		public ImageView imgVegetarian;
		public ImageView imgSpicy;
		public ImageView imgGlutenFree;
		
		TextView tvLikes;
		TextView tvVisitors;
		ImageView ivLikes;
		ImageView ivVisitors;
		RelativeLayout rl_stats;
	}

	static class ViewHolder2 {
		// public ImageView imgGroup;
		public TextView txtGroupName;
		public Spinner spinner;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		MenuItemAndStats  menuItemAndStat = menuItemAndStats.get(position);
		StoreMenuItem storeMenuItem = null;
		StoreItemStats storeItemStats = null;
		if(menuItemAndStat!=null)
		{			
			storeMenuItem=menuItemAndStat.getMenuItem();
			storeItemStats=menuItemAndStat.getStats();
		}
		else
		{
			if(bLoading==false)
			{
				bLoading = true;
				firstRequestPos = position;
				new GetMoreMenuItemAndStatsForMenu().execute();
			}
		}
		
		ViewHolder viewHolder = null;
		
		Log.e("ItemName",""+ ((storeMenuItem!=null)? storeMenuItem.getShortDescription():"loading ...") + " position: " + position);
		
		if (rowView == null )
		{
				rowView = View.inflate(getContext(), R.layout.menu_item2, null);
				viewHolder = new ViewHolder();
				viewHolder.imgFood = (ImageView) rowView.findViewById(R.id.image_food_item);
				viewHolder.txtName = (TextView) rowView.findViewById(R.id.txt_item_name);
				viewHolder.txtPrice = (TextView) rowView.findViewById(R.id.tv_item_price);
				viewHolder.txtDesc = (TextView) rowView.findViewById(R.id.txt_item_desc);
				viewHolder.imgVegetarian = (ImageView) rowView.findViewById(R.id.image_vegetarian);
				viewHolder.imgSpicy = (ImageView) rowView.findViewById(R.id.image_spicy);
				viewHolder.imgGlutenFree = (ImageView) rowView.findViewById(R.id.image_glutenfree);
				viewHolder.rl_stats=(RelativeLayout)rowView.findViewById(R.id.rl_stats);
				viewHolder.tvLikes=(TextView)rowView.findViewById(R.id.tv_likes);
				viewHolder.tvVisitors=(TextView)rowView.findViewById(R.id.tv_visitors);
				viewHolder.ivVisitors=(ImageView)rowView.findViewById(R.id.iv_visitors);
				viewHolder.ivLikes=(ImageView)rowView.findViewById(R.id.iv_likes);
				rowView.setTag(viewHolder);
		}
		
		else
			viewHolder = (ViewHolder) rowView.getTag();

			
			viewHolder.txtName.setText((storeMenuItem!=null) ? storeMenuItem.getShortDescription() : "loading ...");
			viewHolder.txtDesc.setText((storeMenuItem!=null) ? storeMenuItem.getLongDescription() : "loading ...");
			if( viewHolder.txtPrice!=null)
				viewHolder.txtPrice.setText(AmountConversionUtils.formatCentsToCurrency((storeMenuItem!=null) ? storeMenuItem.getPrice() : 0));
			if(storeMenuItem!=null)
				imageLoader.DisplayImage(storeMenuItem.getThumbnailUrl(), viewHolder.imgFood);
			if(storeItemStats!=null)
			{
				viewHolder.rl_stats.setVisibility(View.VISIBLE);
				Log.e("null check","storestats not null");
				if(storeItemStats.getOrders()>0)
				{
					viewHolder.tvVisitors.setVisibility(View.VISIBLE);
					viewHolder.ivVisitors.setVisibility(View.VISIBLE);
					viewHolder.tvVisitors.setText(""+storeItemStats.getOrders());
				}
				else
				{
					viewHolder.tvVisitors.setVisibility(View.GONE);
					viewHolder.ivVisitors.setVisibility(View.GONE);
				}
				long count=storeItemStats.getDislikes()+storeItemStats.getLikes();
				if(count>0)
				{
					viewHolder.tvLikes.setVisibility(View.VISIBLE);
					viewHolder.ivLikes.setVisibility(View.VISIBLE);
				   long percent=((storeItemStats.getLikes())*100)/count;
				   viewHolder.tvLikes.setText(percent+"%"+"("+count+")");
					
				}
				else
				{
					viewHolder.tvLikes.setVisibility(View.GONE);
					viewHolder.ivLikes.setVisibility(View.GONE);
				}
			}
			else
			{
				viewHolder.tvVisitors.setVisibility(View.GONE);
				viewHolder.ivVisitors.setVisibility(View.GONE);
				viewHolder.tvLikes.setVisibility(View.GONE);
				viewHolder.ivLikes.setVisibility(View.GONE);
				
			}
			// holder.imgFood.setVisibility(View.GONE);

			viewHolder.imgFood.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(mContext, MenuFlowActivity.class);
					i.putExtra(Constants.BUNDLE_KEY_ITEM_POSITION,position);
					i.putExtra(Constants.ORDER_TYPE,orderType);
					mContext.startActivity(i);
					

				}
			});

		return rowView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder2 viewHolder=null;
		int sectionPos=getSectionForPosition(position);
		if (rowView == null)
		{
			rowView = View.inflate(getContext(), R.layout.menu_item_group2, null);
			 viewHolder = new ViewHolder2();
			viewHolder.txtGroupName = (TextView) rowView.findViewById(R.id.menu_category_title);
            viewHolder.spinner=(Spinner)rowView.findViewById(R.id.sp_submenu);
			rowView.setTag(viewHolder);	
		}
		else{
			 viewHolder = (ViewHolder2) rowView.getTag();
		}
		
		viewHolder.txtGroupName.setText(sectionsList.get(sectionPos).getName());
		
		SimpleSpinnerAdapter simpleAdapter=new SimpleSpinnerAdapter(mContext,R.layout.row_spinner_submenu,getSectionsHeaders());
        viewHolder.spinner.setAdapter(new NothingSelectedSpinnerAdapter(simpleAdapter,R.layout.row_simple_list, mContext));
       viewHolder.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(position>0)
				{
				int moveToPosition=getPositionForSection(position-1);
				Log.e("move to position", ""+moveToPosition);
				scrollToIndexListener.scrollToPosition(moveToPosition);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
    
		return rowView;
	}

	@Override
	public long getHeaderId(int position) {
		return getSectionForPosition(position);
	}

	
	private  class GetMoreMenuItemAndStatsForMenu extends AsyncTask<Void,Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			 try { 
				 Log.e("params", "store id: "+AsaanUtility.selectedStore.getId()+" Root Menu Id: "+menuPOSId);
				int isections = getSectionForPosition(firstRequestPos);
				menuItemAndStatsCollection = SplashActivity.mStoreendpoint
						.getMenuItemAndStatsForMenu(firstRequestPos+isections+1,
								MAX_RESULT, menuPOSId,AsaanUtility.selectedStore.getId()).execute();
			if(menuItemAndStatsCollection!=null && menuItemAndStatsCollection.getItems()!=null)
			{
				Log.e("MenuItemAndStatsForMenu", "Item Array Size: "+menuItemAndStatsCollection.getItems().size());
			}
			else
			{
				Log.e("MenuItemAndStatsForMenu", "No items return. ");
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			int startAddPos =0;
			
			try{ 
			if(menuItemAndStatsCollection !=null && (menuItemAndStatsCollection.getItems() != null))
			{
				for(int i=0;  i<menuItemAndStatsCollection.getItems().size(); i++)
				{
					if(menuItemAndStatsCollection.getItems().get(i).getMenuItem().getLevel()==2)
					{
						menuItemAndStats.set(firstRequestPos+startAddPos, menuItemAndStatsCollection.getItems().get(i));
						startAddPos++;
					}
				}
				notifyDataSetChanged();
			}
			
			}
			catch(Exception e)
			{
				Log.e("GetMoreMenuItemAndStatsForMenu", "onPostExecute failed.");
			}
			
			bLoading = false;
		}
	}
}
