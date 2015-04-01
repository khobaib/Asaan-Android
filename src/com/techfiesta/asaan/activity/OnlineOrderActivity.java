package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoSession;
import asaan.dao.DaoMaster.OpenHelper;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanApplication;
import com.techfiesta.asaan.utility.AsaanUtility;
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
	
	RelativeLayout rl_carryout;
	RelativeLayout rl_delivary;

	Long currentTime;
	static Long deliverTime;
	static int numPeople = 1;

	AsaanApplication appInstance;
    private ActionBar actionBar;
    private long one_hour=60*60*1000;
    private long fifteen_min=15*60*1000;
    
    private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AddItemDao addItemDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_order);

		appInstance = (AsaanApplication) getApplication();

		actionBar=getActionBar();
		actionBar.setTitle("Back");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		
		DeliveryTime = (TextView) findViewById(R.id.tv_delivery_time);
		People = (TextView) findViewById(R.id.tv_num_of_people);
		plusTime = (Button) findViewById(R.id.tv_time_greater);
		minusTime = (Button) findViewById(R.id.tv_time_less);
		plusPeople = (Button) findViewById(R.id.tv_people_greater);
		minusPeople = (Button) findViewById(R.id.tv_people_less);

		carryoutNext = (Button) findViewById(R.id.tv_carry_out_next);
		deliverNext = (Button) findViewById(R.id.tv_delivery_next);
		
		rl_carryout=(RelativeLayout)findViewById(R.id.rl_carryout);
		rl_delivary=(RelativeLayout)findViewById(R.id.rl_delivery);

		// plusTime.setOnClickListener(this);
		// minusTime.setOnClickListener(this);
		// plusPeople.setOnClickListener(this);
		// minusPeople.setOnClickListener(this);

		currentTime = System.currentTimeMillis();
		deliverTime = currentTime + one_hour;

		DeliveryTime.setText(getFormattedTime(deliverTime));

		plusTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickTimePlus(v);

			}
		});
		minusTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickTimeMinus(v);
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
		
		rl_carryout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(OnlineOrderActivity.this,MenuActivityNew.class);
				intent.putExtra(Constants.ORDER_TYPE,Constants.ORDER_TYPE_CARRYOUT);
				startActivity(intent);
				
			}
		});
		rl_delivary.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(OnlineOrderActivity.this,MenuActivityNew.class);
				intent.putExtra(Constants.ORDER_TYPE,Constants.ORDER_TYPE_DELIVERY);
				startActivity(intent);
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
		if ((deliverTime-fifteen_min) < (System.currentTimeMillis()+one_hour)) {
			// do nothing
		} else {
			deliverTime -= fifteen_min;
			DeliveryTime.setText(getFormattedTime(deliverTime));
		}
	}

	public void onClickTimePlus(View v) {
		Log.d(">>>", "onClickTimePlus");
		deliverTime += fifteen_min;
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
	private void initDatabase() {
		OpenHelper helper = new DaoMaster.DevOpenHelper(this, "asaan-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		addItemDao = daoSession.getAddItemDao();
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		initDatabase();
		if(addItemDao.count()>0)
			getMenuInflater().inflate(R.menu.activity_menu, menu);
		else
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==R.id.action_cart)
		{
			Intent intent=new Intent(OnlineOrderActivity.this,MyCartActivity.class);
			startActivity(intent);
		}
		else if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}
	

}
