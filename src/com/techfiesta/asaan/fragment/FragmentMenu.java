package com.techfiesta.asaan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techfiesta.asaan.R;

public class FragmentMenu extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.frag_restaurant_history, container,
				false);
		return v;
	}

}
