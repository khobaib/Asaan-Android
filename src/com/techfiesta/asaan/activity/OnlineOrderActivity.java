package com.techfiesta.asaan.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanApplication;
import com.techfiesta.asaan.utility.Constants;

public class OnlineOrderActivity extends Activity {

	Button carryoutNext;
	Button deliverNext;

	Button plusTime;
	Button minusTime;

	Button plusPeople;
	Button minusPeople;

	TextView DeliveryTime;
	TextView People;

	Long currentTime;
	static Long deliverTime;
	static int numPeople = 1;

	AsaanApplication appInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_order);

		appInstance = (AsaanApplication) getApplication();

		DeliveryTime = (TextView) findViewById(R.id.tv_delivery_time);
		People = (TextView) findViewById(R.id.tv_num_of_people);
		plusTime = (Button) findViewById(R.id.tv_time_greater);
		minusTime = (Button) findViewById(R.id.tv_time_less);
		plusPeople = (Button) findViewById(R.id.tv_people_greater);
		minusPeople = (Button) findViewById(R.id.tv_people_less);

		carryoutNext = (Button) findViewById(R.id.tv_carry_out_next);
		deliverNext = (Button) findViewById(R.id.tv_delivery_next);

		// plusTime.setOnClickListener(this);
		// minusTime.setOnClickListener(this);
		// plusPeople.setOnClickListener(this);
		// minusPeople.setOnClickListener(this);

		currentTime = System.currentTimeMillis();
		deliverTime = currentTime + 60 * 60 * 1000;

		DeliveryTime.setText(getFormattedTime(deliverTime));

		plusTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickTimePlus(v);

			}
		});

		carryoutNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				appInstance.setPARENT_PAGE(Constants.FROM_ONLINE_ORDER);
				Intent in = new Intent(OnlineOrderActivity.this, MenuActivity.class);
				startActivity(in);

			}
		});

		deliverNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				appInstance.setPARENT_PAGE(Constants.FROM_ONLINE_ORDER);
				Intent in = new Intent(OnlineOrderActivity.this, MenuActivity.class);
				startActivity(in);

			}
		});
	}

	public String getFormattedTime(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String sTime = sdf.format(new Date(rawTime));
		return sTime;
	}

	public void onClickTimeMinus(View v) {
		Log.d(">>>", "onClickTimeMinus");
		if (deliverTime <= System.currentTimeMillis()) {
			// do nothing
		} else {
			deliverTime -= 60 * 1000;
			DeliveryTime.setText(getFormattedTime(deliverTime));
		}
	}

	public void onClickTimePlus(View v) {
		Log.d(">>>", "onClickTimePlus");
		deliverTime += 60 * 1000;
		DeliveryTime.setText(getFormattedTime(deliverTime));

	}

	public void onClickPeopleMinus(View v) {
		Log.d(">>>", "onClickPeopleMinus");
		if (numPeople <= 1) {
			// do nothing
		} else {
			numPeople--;
			People.setText("" + numPeople);
		}

	}

	public void onClickPeoplePlus(View v) {

		Log.d(">>>", "onClickPeoplePlus");

		numPeople++;
		People.setText("" + numPeople);

	}

}
