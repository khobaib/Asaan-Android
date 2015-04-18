package com.techfiesta.asaan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.techfiesta.asaan.R;

public class SearchEmployeeActivity  extends BaseActivity{
	private TextView tvAddEmployee,tvSearch;
	private EditText etPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_employees);
		tvAddEmployee=(TextView)findViewById(R.id.tv_add_emp_to_chat);
		tvSearch=(TextView)findViewById(R.id.tv_search);
		etPhone=(EditText)findViewById(R.id.et_phone);
		
		tvSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		etPhone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!s.toString().startsWith("+1"))
					etPhone.setText("+1"+s);
				Log.e("TEXT",s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
