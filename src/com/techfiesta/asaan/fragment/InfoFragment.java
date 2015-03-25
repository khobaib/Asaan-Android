package com.techfiesta.asaan.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.google.android.gms.maps.MapFragment;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;
import com.techfiesta.asaan.utility.AsaanUtility;

public class InfoFragment extends Fragment {

	private TextView tvAddress, tvPhone, tvWebUrl,tvFaceBook,tvTwitter,tvtName,tvCuisineType,tvExecutiveChef,tvTrophies,tvSpeciality;
	Store store;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_restaurant_info4, container, false);
		tvtName=(TextView)v.findViewById(R.id.txtViewResName);
		tvCuisineType=(TextView)v.findViewById(R.id.txtViewCuiType);
		tvExecutiveChef=(TextView)v.findViewById(R.id.txtViewExChef);
		tvTrophies=(TextView)v.findViewById(R.id.txtViewTrophies);
		tvAddress=(TextView)v.findViewById(R.id.txtViewAddress);
		tvPhone=(TextView)v.findViewById(R.id.txtViewPhone);
		tvWebUrl=(TextView)v.findViewById(R.id.txtViewWebURL);
		tvFaceBook=(TextView)v.findViewById(R.id.txtViewFbURL);
		tvTwitter=(TextView)v.findViewById(R.id.txtViewTwURL);
		tvSpeciality=(TextView)v.findViewById(R.id.txtViewResSpecial);
		
		store=AsaanUtility.selectedStore;
		
		tvAddress.setText((""+store.getAddress()).equals("null")?"":store.getAddress());
		tvPhone.setText((""+store.getPhone()).equals("null")?"":store.getPhone());
		tvWebUrl.setText((""+store.getWebSiteUrl()).equals("null")?"":store.getWebSiteUrl());
		tvFaceBook.setText((""+store.getFbUrl()).equals("")?"":store.getFbUrl());
		tvTwitter.setText((""+store.getTwitterUrl()).equals("null")?"":store.getTwitterUrl());
		
		tvtName.setText((""+store.getName()).equals("null")?"":store.getName());
		tvCuisineType.setText((""+store.getType()).equals("null")?"":store.getType());
		tvExecutiveChef.setText((""+store.getExecutiveChef()).equals("null")?"":store.getExecutiveChef());
		tvSpeciality.setText((""+store.getDescription()).equals("null")?"":store.getDescription());
		
		setTrophiesText();
		
		return v;
	}
	private void setTrophiesText()
	{
		List<String> trophies=store.getTrophies();
		String tr="";
		int size=trophies.size();
		for(int i=0;i<size-1;i++)
		{
			tr+=trophies.get(i)+",";
		}
		if(size>0)
			tr+=trophies.get(size-1);
		
		tvTrophies.setText(tr);
			
		
	}

}
