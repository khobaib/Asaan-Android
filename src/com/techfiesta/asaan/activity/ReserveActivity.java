package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReserveActivity extends Activity {
	
	private TextView tvName;
	private RelativeLayout rlReserve,rlWaitList;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserve_waitlist4);
		actionBar=getActionBar();
		actionBar.setTitle("Back");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		tvName=(TextView)findViewById(R.id.txtViewResName);
		rlReserve=(RelativeLayout)findViewById(R.id.relLay1);
		rlWaitList=(RelativeLayout)findViewById(R.id.relLay2);
		
		tvName.setText(AsaanUtility.selectedStore.getName());
		rlReserve.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(AsaanUtility.selectedStore.getProvidesReservation())
				{
				Intent intent=new Intent(ReserveActivity.this,ReserveConfirmActivity.class);
				startActivity(intent);
				}
			}
		});
		rlWaitList.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(AsaanUtility.selectedStore.getProvidesWaitlist())
						{
						Intent intent=new Intent(ReserveActivity.this,WaitListConfirmActivity.class);
						startActivity(intent);
						}
					}
				});
						
				
			}

}
