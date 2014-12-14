package com.techfiesta.asaan.utility;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MyTabListener<T extends Fragment> implements TabListener {
	private final Activity mActivity;
	private final String mTag;
	private final Class<T> mClass; // Fragment class
	private final Bundle mBundle;

	private Fragment mFragment; // current fragment

	public MyTabListener(Activity activity, String tag, Class<T> clz,
			Bundle bundle) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mBundle = bundle;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		final Fragment preInitializedFragment = mActivity.getFragmentManager()
				.findFragmentByTag(mTag);
		if (preInitializedFragment == null) {
			mFragment = Fragment.instantiate(mActivity, mClass.getName(),
					mBundle);
			ft.add(android.R.id.content, mFragment, mTag);
		} else
			ft.attach(preInitializedFragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		final Fragment preInitializedFragment = mActivity.getFragmentManager()
				.findFragmentByTag(mTag);

		if (preInitializedFragment != null)
			ft.detach(preInitializedFragment);
		else if (mFragment != null)
			ft.detach(mFragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
	}
}
