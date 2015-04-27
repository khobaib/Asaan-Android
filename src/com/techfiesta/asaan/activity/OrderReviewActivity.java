package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class OrderReviewActivity extends BaseActivity {
	private SeekBar seekBarFood, seekBarServce;
	private EditText edtExp;
	int foodLike = -1;
	int serviceLike = -1;
	TextView tvName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_review);
		seekBarFood = (SeekBar) findViewById(R.id.sbFood);
		seekBarServce = (SeekBar) findViewById(R.id.sbService);
		edtExp = (EditText) findViewById(R.id.etExp);
		tvName=(TextView)findViewById(R.id.tvName);
		tvName.setText(AsaanUtility.selectedStoreOrder.getStoreName());
		seekBarFood.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress > 50)
					foodLike = 1;
				else if (progress < 50)
					foodLike = 0;

			}
		});
		seekBarServce.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (progress > 50)
					serviceLike = 1;
				else if (progress < 50)
					serviceLike = 0;

			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.activity_review, menu);
		MenuItem item = menu.findItem(R.id.action_review);
		item.setTitle("Next");
		// else
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title

		if (item.getItemId() == R.id.action_review) {
			Intent intent = new Intent(OrderReviewActivity.this, ReviewItemActivity.class);
			intent.putExtra(Constants.ORDER_FOOD_LIKE, foodLike);
			intent.putExtra(Constants.ORDER_SERVICE_LIKE, serviceLike);
			if(!edtExp.getText().toString().equals(""))	
				intent.putExtra(Constants.ORDER_EXPERIENCE,edtExp.getText().toString());
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

		}
		return true;
	}

}
