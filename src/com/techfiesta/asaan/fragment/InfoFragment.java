package com.techfiesta.asaan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;
import com.techfiesta.asaan.utility.AsaanUtility;

public class InfoFragment extends Fragment {

	private TextView tvAddress, tvPhone, tvName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_info_2, container, false);
		tvAddress = (TextView) v.findViewById(R.id.tv_address);
		tvPhone = (TextView) v.findViewById(R.id.tv_phone);
		tvName = (TextView) v.findViewById(R.id.tv_name);
		tvAddress.setText(AsaanUtility.selectedStore.getAddress());
		tvPhone.setText(AsaanUtility.selectedStore.getPhone());
		tvName.setText(AsaanUtility.selectedStore.getName());

		return v;
	}

	@Override
	public void onDestroyView() {
		if (!StoreDetailsActivityNew.isBackButtonPressed) {
			try {
				MapFragment fragment = (MapFragment) getActivity()
						.getFragmentManager().findFragmentById(R.id.map);
				if (fragment != null)
					getFragmentManager().beginTransaction().remove(fragment)
							.commit();

			} catch (IllegalStateException e) {
				// handle this situation because you are necessary will get
				// an exception here :-(
			}
		}
		super.onDestroyView();
	}
}
