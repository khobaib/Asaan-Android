package com.techfiesta.asaan.fragment;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuLayer2  extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("MSG","on create menu layer 2");
		View v=inflater.inflate(R.layout.fragment_menu_demo_2,container,false);
		((StoreDetailsActivityNew) getActivity()).menuTabStack.push(MenuLayer2.this);
		return v;
	}
	@Override
	public void onResume() {
		Log.e("MSG","on resume Menu layer 2");
		super.onResume();
	}

}
