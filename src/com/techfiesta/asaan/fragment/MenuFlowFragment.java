package com.techfiesta.asaan.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MenuFlowActivity;
import com.techfiesta.asaan.activity.OrderItemActivity;
import com.techfiesta.asaan.interfaces.ForwardBackWardClickListner;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AmountConversionUtils;
import com.techfiesta.asaan.utility.Constants;

public class MenuFlowFragment extends Fragment{
	private ImageView ivIteImage;
	private TextView tvLongDes,tvShortDes,tvPrice,tvOrders,tvLikes,tvAddOrder;
	private Button btnForward,btnBackWard;
	ForwardBackWardClickListner forwardBackWardClickListner=null;
	
	int menuItemPrice = 0;
	String menuItemShortDesc, menuItemLongDesc,numLikes;
	long numOrders;
	int menuItemPosId;
	int orderType=-1;
	long timeEstimatedDelivery;
	boolean hasModifiers;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v=inflater.inflate(R.layout.fragment_menu_flow,null,false);
           ivIteImage=(ImageView)v.findViewById(R.id.iv_item_image);
           tvShortDes=(TextView)v.findViewById(R.id.tv_short_desc);
           tvLongDes=(TextView)v.findViewById(R.id.tv_long_desc);
           tvPrice=(TextView)v.findViewById(R.id.tv_item_price);
           btnForward=(Button)v.findViewById(R.id.btn_forward);
           btnBackWard=(Button)v.findViewById(R.id.btn_backward);
           tvOrders=(TextView)v.findViewById(R.id.tv_num_of_order_title);
           tvLikes=(TextView)v.findViewById(R.id.tv_num_of_like);
           tvAddOrder=(TextView)v.findViewById(R.id.tv_add_order);
           
          
    
           return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		forwardBackWardClickListner=(MenuFlowActivity)getActivity();
		
		btnForward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int counter=getArguments().getInt(Constants.BUNDLE_KEY_ITEM_POSITION);
				Log.e("MSG", "forward clicked"+(counter+1));
				forwardBackWardClickListner.moveForward(counter+1);
				
				
			}
		});
		btnBackWard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int counter=getArguments().getInt(Constants.BUNDLE_KEY_ITEM_POSITION);
				Log.e("MSG", counter+"backward clicked"+(counter-1));
				forwardBackWardClickListner.moveBackward(counter-1);
				
				
			}
		});
		
		getIntentDataAndSetTexts();
	}
	private void getIntentDataAndSetTexts()
	{
		Bundle bundle = getArguments();
		
		menuItemPrice = bundle.getInt(Constants.BUNDLE_KEY_MENUITEM_PRICE);
		
		menuItemShortDesc = bundle.getString(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION);
		menuItemLongDesc = bundle.getString(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION);
		menuItemPosId=bundle.getInt(Constants.BUNDLE_KEY_MENUITEM_POS_ID,-1);
		hasModifiers=bundle.getBoolean(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS,false);
		String imageUrl=bundle.getString(Constants.BUNDLE_KEY_IMAGE_URL,"");
		boolean hasStats=bundle.getBoolean(Constants.BUNDLE_KEY_HAS_STATS,false);
		if(hasStats)
		{
			numLikes=bundle.getString(Constants.BUNDLE_KEY_NUMBER_OF_LIKES);
			tvLikes.setText(numLikes);
			numOrders=bundle.getLong(Constants.BUNDLE_KEY_NUMBER_OF_ORDER);
			if(numOrders>0)
			{
				tvOrders.setText(""+numOrders+"people ordered today");
			}
			else
				tvOrders.setVisibility(View.GONE);
			
		}
		else
		{
			tvOrders.setVisibility(View.GONE);
			tvLikes.setVisibility(View.GONE);
		}
		if(!imageUrl.equals(""))
		{
			ImageLoader imageLoader=new ImageLoader(getActivity());
			imageLoader.DisplayImage(imageUrl,ivIteImage);
		}
		tvPrice.setText(AmountConversionUtils.formatCentsToCurrency(menuItemPrice));
		tvShortDes.setText(menuItemShortDesc);
		tvLongDes.setText(menuItemLongDesc);
		orderType=bundle.getInt(Constants.ORDER_TYPE,-1);
		timeEstimatedDelivery = bundle.getLong(Constants.ESTIMATED_TIME,-1);
		if(orderType==-1)
		{
			tvAddOrder.setVisibility(View.INVISIBLE);
		}
		else
		{
			tvAddOrder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					Intent intent=new Intent(getActivity(),OrderItemActivity.class);
					intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_POS_ID,menuItemPosId);
					intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_PRICE,menuItemPrice);
					intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, hasModifiers);
					intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION,menuItemShortDesc);
					intent.putExtra(Constants.BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION,menuItemLongDesc);
					intent.putExtra(Constants.ORDER_TYPE,orderType);
					intent.putExtra(Constants.ESTIMATED_TIME,timeEstimatedDelivery);
					startActivity(intent);
				}
			});
		}
		
		
		
	}

}
