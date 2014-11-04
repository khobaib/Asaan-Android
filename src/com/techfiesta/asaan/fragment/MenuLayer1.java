package com.techfiesta.asaan.fragment;

import com.google.android.gms.internal.bt;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuLayer1  extends Fragment{
	private Button btn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("MSG","on create menu layer 1");
		View v=inflater.inflate(R.layout.fragment_menu_demo_1,container,false);
		btn=(Button)v.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentTransaction ft=getFragmentManager().beginTransaction();
				ft.hide(MenuLayer1.this);
				ft.add(R.id.content_frame,new MenuLayer2());
				ft.commit();
			}
		});
		((StoreDetailsActivityNew) getActivity()).menuTabStack.push(MenuLayer1.this);
		return v;
	}
	@Override
	public void onResume() {
		Log.e("MSG","on resume Menu layer 1");
		super.onResume();
	}

}
