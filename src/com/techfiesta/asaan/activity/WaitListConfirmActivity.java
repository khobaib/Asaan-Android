package com.techfiesta.asaan.activity;

import com.techfiesta.asaan.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WaitListConfirmActivity extends Activity implements OnClickListener{
	private TextView tvPeople;
	private Button btnPeoplePlus,btnPeopleMinus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waitlist4);
		btnPeoplePlus=(Button)findViewById(R.id.btnIncPeople);
		btnPeopleMinus=(Button)findViewById(R.id.btnDecPeople);	
		
		tvPeople=(TextView)findViewById(R.id.txtViewPeople);
		
		btnPeoplePlus.setOnClickListener(this);
		btnPeopleMinus.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnIncPeople)
		{
			String val= tvPeople.getText().toString();
			tvPeople.setText(""+(Integer.valueOf(val)+1));
		}
		else if(v.getId()==R.id.btnDecPeople)
			{
				String val= tvPeople.getText().toString();
				int people=Integer.valueOf(val);
				if(people>1)
				  tvPeople.setText(""+(people-1));
			}
		
	}

}
