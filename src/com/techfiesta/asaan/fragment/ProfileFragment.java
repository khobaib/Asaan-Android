package com.techfiesta.asaan.fragment;

import com.asaan.server.com.asaan.server.endpoint.userendpoint.Userendpoint.GetCurrentUser;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.techfiesta.asaan.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileFragment extends Fragment{
	
	private TextView tvSave;
	private EditText etFirstName,etLastName,etEmail,etTip,etPhone,etPaymentInfo,etFacebookProfile;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.frag_profile4,null,false);
		
		tvSave=(TextView)v.findViewById(R.id.tvSave);
		etFirstName=(EditText)v.findViewById(R.id.etViewFirstName);
		etLastName=(EditText)v.findViewById(R.id.etViewLastName);
		etEmail=(EditText)v.findViewById(R.id.etViewEmail);
		etPhone=(EditText)v.findViewById(R.id.etViewPhone);
		etTip=(EditText)v.findViewById(R.id.etViewTip);
		etFacebookProfile=(EditText)v.findViewById(R.id.etViewFbProf);
		
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser user=ParseUser.getCurrentUser();
				String firstName=etFirstName.getText().toString();
				user.put("firstName",firstName);
				String lastName=etLastName.getText().toString();
				user.put("lastName",lastName);
				
				user.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e==null)
						{
							Log.e("response","user updated");
						}
						else
							Log.e("response",e.toString());
						
						
					}
				});
				
			}
		});
	}
}
