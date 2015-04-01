package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClaimStoreActivity extends Activity{
	private TextView tvName,tvSaveEmployee,tvAddEmployee,tvEdit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_claim_store);
		tvName=(TextView)findViewById(R.id.tv_name);
		tvSaveEmployee=(TextView)findViewById(R.id.tv_save_employees);
		tvAddEmployee=(TextView)findViewById(R.id.tv_add);
		tvEdit=(TextView)findViewById(R.id.tv_Edit);
		
		tvName.setText(AsaanUtility.selectedStore.getName());
		tvAddEmployee.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ClaimStoreActivity.this,SearchEmployeeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				
			}
		});
		
		tvEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		tvSaveEmployee.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}

}
