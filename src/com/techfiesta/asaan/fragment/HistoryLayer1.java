package com.techfiesta.asaan.fragment;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.StoreDetailsActivity;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HistoryLayer1  extends Fragment{
	private Button btn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("MSG","on create History layer 1");
		View v=inflater.inflate(R.layout.fragment_history_demo_1,container,false);
		btn=(Button)v.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentTransaction ft=getFragmentManager().beginTransaction();
				ft.hide(HistoryLayer1.this);
				ft.add(R.id.content_frame,new HistoryLayer2());
				ft.commit();
			}
		});
		((StoreDetailsActivityNew) getActivity()).historyTabStack.push(HistoryLayer1.this);
		return v;
	}
	@Override
	public void onResume() {
		Log.e("MSG","on resume History layer 1");
		super.onResume();
	}

}
