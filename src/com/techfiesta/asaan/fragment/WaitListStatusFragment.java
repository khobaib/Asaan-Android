package com.techfiesta.asaan.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techfiesta.asaan.R;

public class WaitListStatusFragment extends Fragment{
	private TextView tvStatus,tvPartyAhead,tvEstTime,tvLeaveLine;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_wait_list_status,null,false);
		tvStatus=(TextView)v.findViewById(R.id.tv_status);
		tvPartyAhead=(TextView)v.findViewById(R.id.tv_party_ahead);
		tvEstTime=(TextView)v.findViewById(R.id.tv_est_wait_time);
		tvLeaveLine=(TextView)v.findViewById(R.id.tv_leave_line);
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

}
