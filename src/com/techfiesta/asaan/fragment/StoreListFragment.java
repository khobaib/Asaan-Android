package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import asaan.dao.DStore;
import asaan.dao.DStoreDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;
import asaan.dao.Trophies;
import asaan.dao.TrophiesDao;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreCollection;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreStatsCollection;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.activity.StoreDetailsActivityNew;
import com.techfiesta.asaan.adapter.StoreListAdapter;
import com.techfiesta.asaan.utility.AsaanApplication;
import com.techfiesta.asaan.utility.AsaanUtility;

public class StoreListFragment extends Fragment {
	private ListView storeListView;
	private StoreListAdapter storeListAdapter;
	private StoreCollection storeCollection;
	private StoreStatsCollection storeStatsCollection;
	private List<Store> storeList;
	private int INITIAL_POSITION = 0;
	private int MAX_RESULT = 50;
	private int ONE_DAY_DELAY = 24 * 60 * 60 * 1000;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private DStoreDao dStoreDao;
	private TrophiesDao trophiesDao;

	AsaanApplication appInstance;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_store_list, null, false);
		storeListView = (ListView) v.findViewById(R.id.lvRestaurantList);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// init database
		initDatabase();

		appInstance = (AsaanApplication) getActivity().getApplication();

		storeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Store store = storeList.get(position);
				
				 AsaanUtility.selectedStore = store;
				
				Intent intent = new Intent(getActivity(),StoreDetailsActivityNew.class);
				 startActivity(intent);
				 

			}
		});
  
		loadStores();
		

	}
	private void loadStores()
	{
		if (isUpDatedInLast24Hours()) {
			// load from local db
			Log.e("status", "loading from local db");
			loadStoresFromDatabase(); 
			if(AsaanUtility.hasInternet(getActivity()))
                   new GetStoreStatsFromServer().execute();
			else
				internetAvailabilityAlert(getActivity(),"Please Check your internet connection.");
				

		} else {
			Log.e("status", "loading from from server");
			if(AsaanUtility.hasInternet(getActivity()))
			    new GetStroreInfoFromServer().execute();
			else
				internetAvailabilityAlert(getActivity(),"Please Check your internet connection.");
		}
	}

	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		dStoreDao = daoSession.getDStoreDao();
		trophiesDao = daoSession.getTrophiesDao();
	}
	private void closeDatabase()
	{
		daoSession.getDatabase().close();
	}

	private boolean isUpDatedInLast24Hours() {
		long lastTime = AsaanUtility.getlastUpdatedTime(getActivity());
		long diff = System.currentTimeMillis() - lastTime;
		if (diff > ONE_DAY_DELAY) {
			return false;
		} else
			return true;
	}

	private void loadStoresFromDatabase() {
		
		List<Store> tempStoreList = new ArrayList<Store>();
		List<DStore> dstList = dStoreDao.queryBuilder().list();
		for (int i = 0; i < dstList.size(); i++) {

			DStore dStore = dstList.get(i);
			Store store = new Store();
			store.setAddress(dStore.getAddress());
			store.setBackgroundImageUrl(dStore.getBackgroundImageUrl());
			store.setBackgroundThumbnailUrl(dStore.getBackgroundThumbnailUrl());
			store.setBeaconId(dStore.getBeaconId());
			store.setBssid(dStore.getBssid());
			store.setCity(dStore.getCity());
			store.setClaimed(dStore.getClaimed());
			store.setCosineLat(dStore.getCosineLat());
			store.setCosineLng(dStore.getCosineLng());
			store.setCreatedDate(dStore.getCreatedDate());
			store.setDeliveryDistance(dStore.getDeliveryDistance());
			store.setDeliveryFee(dStore.getDeliveryFee());
			store.setExecutiveChef(dStore.getExecutiveChef());
			store.setDescription(dStore.getDescription());
			store.setFbUrl(dStore.getFbUrl());
			store.setGplusUrl(dStore.getGplusUrl());
			store.setHours(dStore.getHours());
			Log.e("address", dStore.getAddress());
			store.setId(dStore.getId());
			store.setIsActive(dStore.getIsActive());
			store.setLat(dStore.getLat());
			store.setLng(dStore.getLng());
			store.setMinOrderAmtForDelivery(dStore.getMinOrderAmtForDelivery());
			store.setModifiedDate(dStore.getModifiedDate());
			store.setName(dStore.getName());
			store.setPhone(dStore.getPhone());
			store.setPriceRange(dStore.getPriceRange());
			store.setProvidesCarryout(dStore.getProvidesCarryout());
			store.setProvidesChat(dStore.getProvidesChat());
			store.setProvidesDelivery(dStore.getProvidesDelivery());
			store.setProvidesPreOrder(dStore.getProvidesPreOrder());
			store.setProvidesPosIntegration(dStore.getProvidesPosIntegration());
			store.setProvidesReservation(dStore.getProvidesReservation());
			store.setProvidesWaitlist(dStore.getProvidesWaitlist());
			store.setRewardsDescription(dStore.getRewardsDescription());
			store.setRewardsRate(dStore.getRewardsRate());
			store.setSineLat(dStore.getSinLat());
			store.setSineLng(dStore.getSinLng());
			store.setSsid(dStore.getSsid());
			store.setState(dStore.getState());
			store.setSubType(dStore.getSubType());
			store.setTwitterUrl(dStore.getTwitterUrl());
			store.setType(dStore.getType());
			store.setWebSiteUrl(dStore.getWebSiteUrl());
			store.setZip(dStore.getZip());

			List<Trophies> trophies = dStore.getTrophiesList();
			List<String> list = new ArrayList<>();
			if (trophies != null) {
				for (int j = 0; j < trophies.size(); j++)
					list.add(trophies.get(j).getName());

			}
			store.setTrophies(list);
			tempStoreList.add(store);

		}
		storeList = tempStoreList;
	}

	private class GetStroreInfoFromServer extends AsyncTask<Void, Void, Void> {

		private boolean error=false;
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				storeCollection = SplashActivity.mStoreendpoint.getStores(INITIAL_POSITION, MAX_RESULT).execute();
				Log.e("StoreList Size", "" + storeCollection.getItems().size());
			} catch (IOException e) {

				e.printStackTrace();
				error=true;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// setting list
			if(error)
			{
				AsaanUtility.simpleAlert(getActivity(),"An error occured.");
				Log.e("POST","load info from server");
				//new GetStoreStatsFromServer().execute();
			}
			else
			{
			storeList = storeCollection.getItems();
			saveStoreToDatabase();
			new GetStoreStatsFromServer().execute();
			}
			super.onPostExecute(result);
		}

		private void saveStoreToDatabase() {
			dStoreDao.deleteAll();
			trophiesDao.deleteAll();
			for (int i = 0; i < storeList.size(); i++) {
				Store store = storeList.get(i);
				DStore dStore = new DStore();
				dStore.setAddress(store.getAddress());
				dStore.setBackgroundImageUrl(store.getBackgroundImageUrl());
				dStore.setBackgroundThumbnailUrl(store.getBackgroundThumbnailUrl());
				dStore.setBeaconId(store.getBeaconId());
				dStore.setBssid(store.getBssid());
				dStore.setCity(store.getCity());
				dStore.setClaimed(store.getClaimed());
				dStore.setCosineLat(store.getCosineLat());
				dStore.setCosineLng(store.getCosineLng());
				dStore.setCreatedDate(store.getCreatedDate());
				dStore.setDeliveryDistance(store.getDeliveryDistance());
				dStore.setDeliveryFee(store.getDeliveryFee());
				dStore.setExecutiveChef(store.getExecutiveChef());
				dStore.setDescription(store.getDescription());
				dStore.setFbUrl(store.getFbUrl());
				dStore.setGplusUrl(store.getGplusUrl());
				dStore.setHours(store.getHours());
				//Log.e("address", store.getAddress());
				dStore.setId(store.getId());
				dStore.setIsActive(store.getIsActive());
				dStore.setLat(store.getLat());
				dStore.setLng(store.getLng());
				dStore.setMinOrderAmtForDelivery(store.getMinOrderAmtForDelivery());
				dStore.setModifiedDate(store.getModifiedDate());
				dStore.setName(store.getName());
				dStore.setPhone(store.getPhone());
				dStore.setPriceRange(store.getPriceRange());
				dStore.setProvidesCarryout(store.getProvidesCarryout());
				dStore.setProvidesChat(store.getProvidesChat());
				dStore.setProvidesDelivery(store.getProvidesDelivery());
				dStore.setProvidesPreOrder(store.getProvidesPreOrder());
				dStore.setProvidesPosIntegration(store.getProvidesPosIntegration());
				dStore.setProvidesReservation(store.getProvidesReservation());
				dStore.setProvidesWaitlist(store.getProvidesWaitlist());
				dStore.setRewardsDescription(store.getRewardsDescription());
				dStore.setRewardsRate(store.getRewardsRate());
				dStore.setSinLat(store.getSineLat());
				dStore.setSinLng(store.getSineLng());
				dStore.setSsid(store.getSsid());
				dStore.setState(store.getState());
				dStore.setSubType(store.getSubType());
				dStore.setTwitterUrl(store.getTwitterUrl());
				dStore.setType(store.getType());
				dStore.setWebSiteUrl(store.getWebSiteUrl());
				dStore.setZip(store.getZip());

				dStoreDao.insert(dStore);
				if (store.getTrophies() != null) {
					for (int j = 0; j < store.getTrophies().size(); j++) {
						long rownum = trophiesDao.insert(new Trophies(store.getTrophies().get(j), store.getId()));
						Log.e("status", rownum + "");
					}
				}
			}
			AsaanUtility.seLastUpDatedTime(getActivity(), System.currentTimeMillis());

		}

	}
	private class GetStoreStatsFromServer  extends AsyncTask<Void,Void,Void>
	{
		private boolean error=false;

		@Override
		protected Void doInBackground(Void... params) {
			 try {
				storeStatsCollection=SplashActivity.mStoreendpoint.getStatsForAllStores(INITIAL_POSITION, MAX_RESULT).execute();
				Log.e("MSG","statlist size: "+storeStatsCollection.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error=true;
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(error)
				AsaanUtility.simpleAlert(getActivity(),"An error occured.");
			else
			{
			storeListAdapter = new StoreListAdapter(getActivity(), storeList,storeStatsCollection.getItems());
			storeListView.setAdapter(storeListAdapter);
			closeDatabase();
			}
		}
		
	}
	private void internetAvailabilityAlert(Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		// bld.setTitle(context.getResources().getText(R.string.app_name));
		bld.setTitle(R.string.app_name);
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getActivity().finish();
			}
		});
		bld.setNegativeButton("Retry", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				loadStores();
			}
		});

		bld.create().show();
	}


}
