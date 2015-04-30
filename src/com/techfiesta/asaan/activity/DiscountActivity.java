package com.techfiesta.asaan.activity;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreDiscountCollection;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

public class DiscountActivity extends BaseActivity{

	private ActionBar actionBar;
	private ProgressDialog pdDialog;
	private EditText edtDiscountCode;
	private int RESULT_CODE_DISCOUNT=100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discount_code);
		
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		pdDialog=new ProgressDialog(DiscountActivity.this);
		pdDialog.setMessage("Please wait...");
		
		edtDiscountCode=(EditText)findViewById(R.id.et_code);
		edtDiscountCode.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId==event.KEYCODE_ENTER || (actionId == EditorInfo.IME_ACTION_DONE) || (actionId ==event.ACTION_DOWN)) 
				{
					new GetDisCountCodes().execute();
				}
				return false;
			}
		});
	}
	private class GetDisCountCodes extends AsyncTask<Void,Void,Void>
	{
		private StoreDiscountCollection storeDiscountCollection;
		private boolean error=false;

		@Override
		protected void onProgressUpdate(Void... values) {
			pdDialog.show();
			super.onProgressUpdate(values);
		}
		@Override
		protected Void doInBackground(Void... params) {
			 try {
				storeDiscountCollection=SplashActivity.mStoreendpoint.getStoreDiscounts(AsaanUtility.selectedStore.getId()).execute();
				if(storeDiscountCollection!=null)
					Log.e("discounts", storeDiscountCollection.toPrettyString());
			} catch (IOException e) {
				error=true;
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			if(pdDialog.isShowing())
				pdDialog.dismiss();
			if (error)
				AsaanUtility.simpleAlert(DiscountActivity.this, getResources().getString(R.string.error_alert));
			else {
				if(storeDiscountCollection.getItems()!=null)
				{
				int size=storeDiscountCollection.getItems().size(); 
				for(int i=0;i<size;i++)
				{
					String code=storeDiscountCollection.getItems().get(i).getCode();
					if(code.equalsIgnoreCase(edtDiscountCode.getText().toString()))
					{
						
						Intent intent=new Intent();
						intent.putExtra("discount",storeDiscountCollection.getItems().get(i).getTitle()+" "+ storeDiscountCollection.getItems().get(i).getValue());
						setResult(RESULT_CODE_DISCOUNT,intent);
						finish();
						overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
						
					}
					else
					{
						AsaanUtility.simpleAlert(DiscountActivity.this, getResources().getString(R.string.alert_discount_code_mismatch));
					}
				}
				}
			}
				
			super.onPostExecute(result);
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}
}
