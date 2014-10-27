package com.techfiesta.asaan.fragment;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InfoFragment  extends Fragment{

	private TextView tvAddress,tvPhone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v=inflater.inflate(R.layout.fragment_info,container,false);
		 tvAddress=(TextView)v.findViewById(R.id.tv_address);
		 tvPhone=(TextView)v.findViewById(R.id.tv_phone);
		 tvAddress.setText(AsaanUtility.selectedStore.getAddress());
		 tvPhone.setText(AsaanUtility.selectedStore.getPhone());
		 
		 return v;
	}
}
