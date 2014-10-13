package com.techfiesta.asaan.adapter;

import com.techfiesta.asaan.fragment.SignupCardinfoFragment;
import com.techfiesta.asaan.fragment.SignupInvitecontactsFragment;
import com.techfiesta.asaan.fragment.SignupProfileFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SignupPagerAdapter extends FragmentPagerAdapter{

	public SignupPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos){
		case 0:
			return new SignupProfileFragment();
		case 1:
			return new SignupCardinfoFragment();
		case 2:
			return new SignupInvitecontactsFragment();
			default:
				return null;
		}
			
		
	}

	@Override
	public int getCount() {
		
		return 3;
	}

}
