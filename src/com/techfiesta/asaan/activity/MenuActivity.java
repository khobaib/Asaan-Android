package com.techfiesta.asaan.activity;

import java.io.IOException;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.MenusAndMenuItems;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MenuActivity extends Activity{
	private MenusAndMenuItems menusAndMenuItems;
	private long INITIAL_POSITION=0;
	private int MAX_RESULT=10;
	private long storeId=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		storeId=AsaanUtility.selectedStore.getId();
		new GetMenu().execute();
	}
	private class GetMenu extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			try {
				menusAndMenuItems = AsaanMainActivity.mStoreendpoint.getStoreMenuHierarchyAndItems(storeId,INITIAL_POSITION,MAX_RESULT).execute();
				Log.e("menu_size",""+menusAndMenuItems.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}

}
