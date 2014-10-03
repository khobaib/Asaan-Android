package com.techfiesta.asaan.utility;



import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AsaanUtility {
	public static Location mLocation = null;
	private static GPSTracker mGps = null;

	
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


}
