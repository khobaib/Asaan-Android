package com.techfiesta.asaan.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReserveConfirmActivity extends Activity implements OnClickListener{
	private TextView tvName,tvPeople,tvDay,tvDate,tvTime;
	private Button btnPeoplePlus,btnPeopleMinus,btnDayPlus,btnDayMinus,btnTimePlus,btnTimeMinus;
	private RelativeLayout rlSendReservation;
	private long one_day=1000*60*60*24;
	private long fifteen_min=15*60*1000;
	private long one_hour=60*60*1000;
	private int curDay=-1;
	private long curTime=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserve4);
		tvName=(TextView)findViewById(R.id.txtViewResName);
		rlSendReservation=(RelativeLayout)findViewById(R.id.relLay4);
		tvPeople=(TextView)findViewById(R.id.txtViewPeople);
		tvDay=(TextView)findViewById(R.id.txtViewDay);
		tvDate=(TextView)findViewById(R.id.txtViewDate);
		tvTime=(TextView)findViewById(R.id.txtViewTime);
		
		btnPeoplePlus=(Button)findViewById(R.id.btnIncPeople);
		btnPeopleMinus=(Button)findViewById(R.id.btnDecPeople);	
		btnDayPlus=(Button)findViewById(R.id.btnIncDay);
		btnDayMinus=(Button)findViewById(R.id.btnDecDay);
		btnTimePlus=(Button)findViewById(R.id.btnIncTime);
		btnTimeMinus=(Button)findViewById(R.id.btnDecTime);
		
		
		btnPeoplePlus.setOnClickListener(this);
		btnPeopleMinus.setOnClickListener(this);
		btnDayMinus.setOnClickListener(this);
		btnDayPlus.setOnClickListener(this);
		btnTimePlus.setOnClickListener(this);
		btnTimeMinus.setOnClickListener(this);
		rlSendReservation.setOnClickListener(this);
		
		setCurrentDateAndTime();
		tvName.setText(AsaanUtility.selectedStore.getName());
		
	}

	private void setCurrentDateAndTime()
	{
		Time time=new Time();
		time.setToNow();
		curDay=time.weekDay;
		tvDay.setText(getDay(curDay));
		
		curTime=System.currentTimeMillis();
		tvDate.setText(getFormattedDate(curTime));
		curTime=curTime+2*one_hour;
		tvTime.setText(getFormattedTime(curTime));
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
		else  if(v.getId()==R.id.btnIncDay)
		{
			
			curDay+=1;
			tvDay.setText(getDay(curDay%7));
			
			curTime+=one_day;
			tvDate.setText(getFormattedDate(curTime));
			
			
		}
		else if(v.getId()==R.id.btnDecDay)
		{
			if((curTime-one_day)<(System.currentTimeMillis()+2*one_hour)){
				//do nothing
			}
			else{
				curDay=curDay-1;
				tvDay.setText(getDay(curDay%7));
				
				curTime-=one_day;
				tvDate.setText(getFormattedDate(curTime));
			}
		}
		else if(v.getId()==R.id.btnIncTime)
		{
			curTime+= fifteen_min;
			tvTime.setText(getFormattedTime(curTime));
		}
		else
			if(v.getId()==R.id.btnDecTime)
			{
				if(curTime-fifteen_min<(System.currentTimeMillis()+2*one_hour))
				{
					//do nothing
				}
				else{
				curTime-=fifteen_min;
				tvTime.setText(getFormattedTime(curTime));
				}
			}
			else if(v.getId()==R.id.relLay4)
			{
				Log.e("Click","Send reservation clicked");
			}
			
		
	}
	private String getDay(int day)
	{
		if(day==0)
		  return "Sunday";
		else if(day==1)
			  return "Monday";
		else if(day==2)
			  return "TuesDay";
		else if(day==3)
			  return "WednesDay";
		else if(day==4)
			  return "ThursDay";
		else if(day==5)
			  return "FriDay";
		else 
			  return "SaturDay";
		
		
	}
	public String getFormattedTime(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String sTime = sdf.format(new Date(rawTime));
		return sTime;
	}
	public String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}


}
