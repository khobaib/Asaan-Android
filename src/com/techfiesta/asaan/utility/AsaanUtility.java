package com.techfiesta.asaan.utility;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard;
import com.techfiesta.asaan.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.WindowManager.BadTokenException;

public class AsaanUtility {
	private static final NumberFormat FORMAT_CURRENCY = NumberFormat.getCurrencyInstance();
	private static final String CURRENT_ORDERED_STORE_ID = "current_ordered_store_id";
	private static final String LAST_UPDATE_TIME_IN_MILLIS = "last_update_time_in milis";
	private static final int CURRENT_INVALID_STORE = -1;
	public static Location mLocation = null;
	private static GPSTracker mGps = null;
	public static Store selectedStore = null;
	public static UserCard defCard=null;

	public static boolean hasInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String formatCentsToCurrency(long p_value) {
		BigDecimal v_bigDec = new BigDecimal(p_value);
		v_bigDec = v_bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
		v_bigDec = v_bigDec.movePointLeft(2);
		return FORMAT_CURRENCY.format(v_bigDec.doubleValue());
	}

	public static boolean getLocation() {
		if (mGps == null) {
			mGps = new GPSTracker(AsaanApplication.getAppContext());
		}

		mLocation = mGps.getLocation();
		if (mGps.canGetLocation()) {
			mLocation = mGps.getLocation();
			return true;
		} else {
			// setMockLocation();
			return false;
		}

		// return mLocation;
	}

	public static void setMockLocation() {
		mLocation = new Location("test location");
		mLocation.setLatitude(22.30954893);
		mLocation.setLongitude(114.22330331);
	}

	public static void checkLocationAccess(Context mContext) {
		if (mGps == null) {
			// mGps = new GPSTracker(PurksApplication.getAppContext());
			mGps = new GPSTracker(mContext);
		}
		if (mGps.canGetLocation()) {
			mLocation = mGps.getLocation();
		} else {
			mGps.showSettingsAlert();
		}
	}

	public static void stopUsingGPS() {
		if (mGps != null && mGps.isGPSEnabled)
			mGps.stopUsingGPS();
	}

	public static void simpleAlert(Context context, String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		bld.setTitle("Asaan");
		bld.setMessage(message);
		bld.setCancelable(false);
		bld.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		bld.create().show();
	}

	public static void setCurrentOrderdStoreId(Context context, int id) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putInt(CURRENT_ORDERED_STORE_ID, id);
		editor.commit();
	}
	public static void setPartySize(Context context, int size) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putInt(Constants.PARTY_SIZE, size);
		editor.commit();
	}
	public static int getCurrentPartySize(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.PARTY_SIZE, 1);
	}
	public static int getCurrentOrderedStoredId(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(CURRENT_ORDERED_STORE_ID, -1);
	}

	public static void seLastUpDatedTime(Context context, long time) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putLong(LAST_UPDATE_TIME_IN_MILLIS, time);
		editor.commit();
	}

	public static long getlastUpdatedTime(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(LAST_UPDATE_TIME_IN_MILLIS, 0);
	}

	public static ProgressDialog createProgressDialog(Context mContext) {
		ProgressDialog dialog = new ProgressDialog(mContext);
		try {
			dialog.show();
		} catch (BadTokenException e) {

		}
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.progress_dialog);
		// dialog.setMessage(Message);
		return dialog;
	}

}
