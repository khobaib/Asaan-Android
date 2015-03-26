package com.techfiesta.asaan.fragment;

import java.net.InterfaceAddress;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenuItemAndStats;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MenuFlowActivity;
import com.techfiesta.asaan.interfaces.ForwardBackWardClickListner;
import com.techfiesta.asaan.utility.Constants;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuFlowFragment extends Fragment{
	private ImageView ivIteImage;
	private TextView tvLongDes,tvShortDes,tvPrice;
	private Button btnForward,btnBackWard;
	ForwardBackWardClickListner forwardBackWardClickListner=null;
	
	int menuItemPrice = 0;
	String menuItemShortDesc, menuItemLongDesc;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v=inflater.inflate(R.layout.fragment_menu_flow,null,false);
           ivIteImage=(ImageView)v.findViewById(R.id.iv_item_image);
           tvShortDes=(TextView)v.findViewById(R.id.tv_short_desc);
           tvLongDes=(TextView)v.findViewById(R.id.tv_long_desc);
           tvPrice=(TextView)v.findViewById(R.id.tv_item_price);
           btnForward=(Button)v.findViewById(R.id.btn_forward);
           btnBackWard=(Button)v.findViewById(R.id.btn_backward);
           
          
    
           return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		forwardBackWardClickListner=(MenuFlowActivity)getActivity();
		
		btnForward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int counter=getArguments().getInt("item_position");
				Log.e("MSG", "clicked"+(counter+1));
				forwardBackWardClickListner.moveForward(counter+1);
				
				
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
		
		tvShortDes.setText(menuItemShortDesc);
		
	}

}
