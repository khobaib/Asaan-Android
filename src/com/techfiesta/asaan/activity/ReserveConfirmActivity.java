package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeam;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ReserveConfirmActivity extends BaseActivity implements OnClickListener{
	private TextView tvName,tvPeople,tvDay,tvDate,tvTime;
	private Button btnPeoplePlus,btnPeopleMinus,btnDayPlus,btnDayMinus,btnTimePlus,btnTimeMinus,btnReserve;
	private long one_day=1000*60*60*24;
	private long fifteen_min=15*60*1000;
	private long one_hour=60*60*1000;
	private int curDay=-1;
	private long curTime=-1;  
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private long roomId=-1;
	private ProgressDialog pdDialog;
	private ActionBar actionBar;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserve4);
		actionBar=getActionBar();
		actionBar.setTitle("Back");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		tvName=(TextView)findViewById(R.id.txtViewResName);
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
		btnReserve=(Button)findViewById(R.id.btnReserveReq);
		
		
		btnPeoplePlus.setOnClickListener(this);
		btnPeopleMinus.setOnClickListener(this);
		btnDayMinus.setOnClickListener(this);
		btnDayPlus.setOnClickListener(this);
		btnTimePlus.setOnClickListener(this);
		btnTimeMinus.setOnClickListener(this);
		btnReserve.setOnClickListener(this);
		
		setCurrentDateAndTime();
		tvName.setText(AsaanUtility.selectedStore.getName());
		
		pdDialog=new ProgressDialog(ReserveConfirmActivity.this);
		pdDialog.setMessage("Please wait...");
		
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
			else if(v.getId()==R.id.btnReserveReq)
			{
				Log.e("Click","Send reservation clicked");
				if(AsaanUtility.hasInternet(ReserveConfirmActivity.this))
				   new GetStoreChatRoomsAndMemberships().execute();
				else
					AsaanUtility.simpleAlert(ReserveConfirmActivity.this,getResources().getString(R.string.internet_alert));
				
			}
			
		
	}
	private class GetStoreChatRoomsAndMemberships extends AsyncTask<Void,Void,Void>
	{
   
     private boolean error=false;
     ChatRoomsAndStoreChatMemberships chatRoomsAndStoreChatMemberships;
     @Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		Log.e("chat","executing");
    		pdDialog.show();
    	}
		@Override
		protected Void doInBackground(Void... params) {
			GetChatRoomsAndMembershipsForUser getChatRoomsAndMembershipsForUser;
			try {
				 getChatRoomsAndMembershipsForUser=SplashActivity.mStoreendpoint.getChatRoomsAndMembershipsForUser();
				 HttpHeaders httpHeaders=getChatRoomsAndMembershipsForUser.getRequestHeaders();
				 httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				chatRoomsAndStoreChatMemberships= getChatRoomsAndMembershipsForUser.execute();
				if(chatRoomsAndStoreChatMemberships!=null)
				 Log.e("chat",chatRoomsAndStoreChatMemberships.toPrettyString());
				
			} catch (IOException e) {
				e.printStackTrace();
				error=true;
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (error)
				AsaanUtility.simpleAlert(ReserveConfirmActivity.this, "An error occured.");
			
			
			StoreChatTeam storeChatTeam = checkForMemberShips(chatRoomsAndStoreChatMemberships
					.getStoreChatMemberships());
			if (storeChatTeam != null) {
                   AsaanUtility.simpleAlert(ReserveConfirmActivity.this,"Cannot send a reservation note to your own restaurant.");

			} else {
				ChatRoom chatRoom = isMemberOfChatroom(chatRoomsAndStoreChatMemberships.getChatRooms());
				if (chatRoom != null) {
					roomId=chatRoom.getId();
					if(roomId!=-1)
					{
						ChatMessage chatMessage=new ChatMessage();
						chatMessage.setTxtMessage(getReservationMessage());
						chatMessage.setRoomId(roomId);
						if (AsaanUtility.hasInternet(ReserveConfirmActivity.this))
							new SaveChatMessageInServer(chatMessage).execute();
						else
							AsaanUtility.simpleAlert(ReserveConfirmActivity.this,
									getResources().getString(R.string.internet_alert));
						
					}
				}
				else
				{
					if (AsaanUtility.hasInternet(ReserveConfirmActivity.this))
						new SaveChatRoomInServer().execute();
					else
						AsaanUtility.simpleAlert(ReserveConfirmActivity.this,
								getResources().getString(R.string.internet_alert));
					
					
				}
			}

		}
	
	}
	private class SaveChatRoomInServer extends AsyncTask<Void,Void,Void>
	{
		private ChatRoom chatRoom;
		private boolean error=false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			chatRoom=new ChatRoom();
			chatRoom.setStoreId(AsaanUtility.selectedStore.getId());
			chatRoom.setName(AsaanUtility.selectedStore.getName());
			
		
		}

		@Override
		protected Void doInBackground(Void... params) {
			SaveChatRoom saveChatRoom;
			try {
				saveChatRoom=SplashActivity.mStoreendpoint.saveChatRoom(chatRoom);
				HttpHeaders httpHeaders=saveChatRoom.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				chatRoom=saveChatRoom.execute();
				Log.e("response",chatRoom.toPrettyString()+"date"+chatRoom.getModifiedDate()+"id"+chatRoom.getId());
			} catch (IOException e) {
				error=true;
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(error)
				AsaanUtility.simpleAlert(ReserveConfirmActivity.this,"An error has occured!.");
			else
			{ 	roomId=chatRoom.getId();
				if(roomId!=-1)
				{
					ChatMessage chatMessage=new ChatMessage();
					chatMessage.setTxtMessage(getReservationMessage());
					chatMessage.setRoomId(roomId);
					if (AsaanUtility.hasInternet(ReserveConfirmActivity.this))
						new SaveChatMessageInServer(chatMessage).execute();
					else
						AsaanUtility.simpleAlert(ReserveConfirmActivity.this,
								getResources().getString(R.string.internet_alert));
					
				}
			}
		}
	}
	private String getReservationMessage()
	{
		ParseUser user=ParseUser.getCurrentUser();
		String firstName=user.getString("firstName");
		String lastName=user.getString("lastName");
		String phone=user.getString("phone");
		String partySize=tvPeople.getText().toString();
		String date=tvDate.getText().toString();
		String time=tvTime.getText().toString();
		String reservationString="New Reservation Request:"+"Name: "+firstName+" "+lastName+"Phone: "+phone+" Party Size: "+partySize+"Date: "+date+"Time: "+time;
		return reservationString;
	}
	private class SaveChatMessageInServer extends AsyncTask<Void,Void,Void>
	{
		boolean error=false;
		ChatMessage chatMessage;
		public SaveChatMessageInServer(ChatMessage chatMessage) {
			this.chatMessage=chatMessage;
		}
		@Override
		protected Void doInBackground(Void... params) {
			try {
			SaveChatMessage saveChatMessage=SplashActivity.mStoreendpoint.saveChatMessage(chatMessage);
			HttpHeaders httpHeaders=saveChatMessage.getRequestHeaders();
			httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
			   chatMessage=saveChatMessage.execute();
			} catch (IOException e) {
				error=true;
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(pdDialog.isShowing())
				pdDialog.dismiss();
			if(error)
				AsaanUtility.simpleAlert(ReserveConfirmActivity.this,"An error has occured!.");
			else
				AsaanUtility.simpleAlert(ReserveConfirmActivity.this,createReserveConfirmString());
			
		}
		
	}
	private String createReserveConfirmString()
	{
		return "Thank you- Your reservation request has been sent.If you need to make changes please call "+ AsaanUtility.selectedStore.getPhone()+".";
	}
	private StoreChatTeam checkForMemberShips(List<StoreChatTeam> chatTeams)
	{
		StoreChatTeam storeChatTeam=null;
		if(chatTeams==null)
		   return storeChatTeam;
		int size=chatTeams.size();
		for(int i=0;i<size;i++)
		{
			if(chatTeams.get(i).getStoreId()==AsaanUtility.selectedStore.getId())
			{
				storeChatTeam=chatTeams.get(i);
				
			}
		}
		return storeChatTeam;
	}
	private ChatRoom isMemberOfChatroom(List<ChatRoom> rooms)
	{  ChatRoom chatRoom=null;
		if(rooms==null)
			return chatRoom;
		int size=rooms.size();
		for(int i=0;i<size;i++)
		{
			if(rooms.get(i).getStoreId()==AsaanUtility.selectedStore.getId())
			{
				return rooms.get(i);
			}
		}
		return chatRoom;
		
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
	public boolean onOptionsItemSelected(MenuItem item) {

		 if(item.getItemId()==android.R.id.home)
		{
			finish();
			overridePendingTransition(R.anim.prev_slide_in, R.anim.prev_slide_out);
		}
		return true;
	}


}
