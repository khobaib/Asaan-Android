package com.techfiesta.asaan.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.google.android.gms.maps.MapFragment;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.MapViewActivity;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AsaanUtility;

public class InfoFragment extends Fragment {

	private TextView tvName, tvSubType, tvExecutive, tvTrophies, tvDescription, tvAddress, tvPhone, tvWeb, tvFb,
			tvTwitter;
	private ImageView ivStore;
	private RelativeLayout rlMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_info_new, container, false);
		ivStore = (ImageView) v.findViewById(R.id.iv_store_info);
		tvName = (TextView) v.findViewById(R.id.tv_store_name);
		tvSubType = (TextView) v.findViewById(R.id.tv_cuisine);
		tvExecutive = (TextView) v.findViewById(R.id.tv_chef);
		tvTrophies = (TextView) v.findViewById(R.id.tv_trophies);
		tvDescription = (TextView) v.findViewById(R.id.tv_store_description);
		tvAddress = (TextView) v.findViewById(R.id.tv_address);
		tvPhone = (TextView) v.findViewById(R.id.tv_phone);
		tvWeb = (TextView) v.findViewById(R.id.tv_web);
		tvFb = (TextView) v.findViewById(R.id.tv_fb);
		tvTwitter = (TextView) v.findViewById(R.id.tv_twitter);

		rlMap = (RelativeLayout) v.findViewById(R.id.rl_view_map);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Store store = AsaanUtility.selectedStore;
		Log.e(">>>", "Store id" + store.getId());
		tvName.setText(store.getName());
		tvSubType.setText(store.getSubType());
		tvDescription.setText(store.getDescription());
		tvAddress.setText(store.getAddress());
		tvPhone.setText(store.getPhone());
		tvWeb.setText(store.getWebSiteUrl());
		tvFb.setText(store.getFbUrl());
		tvTwitter.setText(store.getTwitterUrl());
		ImageLoader imageLoader = new ImageLoader(getActivity());
		imageLoader.DisplayImage(store.getBackgroundImageUrl(), ivStore);

		rlMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), MapViewActivity.class);
				getActivity().startActivity(i);
				getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}
		});
	}

	@Override
	public void onDestroyView() {
		/*
		 * if (!StoreDetailsActivityNew.isBackButtonPressed) { try { MapFragment
		 * fragment = (MapFragment)
		 * getActivity().getFragmentManager().findFragmentById(R.id.map); if
		 * (fragment != null)
		 * getFragmentManager().beginTransaction().remove(fragment).commit();
		 * 
		 * } catch (IllegalStateException e) { // handle this situation because
		 * you are necessary will get // an exception here :-( } }
		 */
		super.onDestroyView();
	}
}
