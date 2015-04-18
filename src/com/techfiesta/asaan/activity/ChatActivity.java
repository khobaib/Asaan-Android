package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeam;
import com.google.android.gms.internal.er;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.broadcastreceiver.PushNotificationReceiver;
import com.techfiesta.asaan.fragment.ChatListFragment;
import com.techfiesta.asaan.fragment.ChatMessageFragment;
import com.techfiesta.asaan.fragment.StoreListFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class ChatActivity extends Activity{
	private ImageView ivAttach;
	private EditText edtChatBox;
	private TextView tvSend;
	private ListView lvChat;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	ArrayList<String> chatList=new ArrayList<String>();
	SimpleListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		new GetStoreChatRoomsAndMemberships().execute();
	}
	@Override
	protected void onResume() {
		/*registerReceiver(pushNotificationReceiver,new IntentFilter("com.asaan"));*/
		super.onResume();
	}

	private class GetStoreChatRoomsAndMemberships extends AsyncTask<Void,Void,Void>
	{
   
     private boolean error=false;
     ChatRoomsAndStoreChatMemberships chatRoomsAndStoreChatMemberships;
     @Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		Log.e("chat","executing");
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
			if(error)
				AsaanUtility.simpleAlert(ChatActivity.this,"An error occured.");
			if (chatRoomsAndStoreChatMemberships != null) {
				ChatRoom chatRoom=isMemberOfChatroom(chatRoomsAndStoreChatMemberships.getChatRooms());
				if (chatRoom!=null) {
					ChatMessage chatMessage=new ChatMessage();
					chatMessage.setRoomId(chatRoom.getId());
					AsaanUtility.USER_ID = chatRoom.getUserId();
					AsaanUtility.selectedChatMessage=chatMessage;
					loadChatMessageFragment();

				} else {
					StoreChatTeam storeChatTeam = checkForMemberShips(chatRoomsAndStoreChatMemberships
							.getStoreChatMemberships());
					if (storeChatTeam != null) {
						
						AsaanUtility.USER_ID = storeChatTeam.getUserId();
						loadChatListFragment();
						
					} else {
						new SaveChatRoomInServer().execute();
					}

				}
			}
					
		}
	
	}
	public void loadChatMessageFragment()
	{
		ChatMessageFragment chatMessageFragment=new ChatMessageFragment();
		FragmentTransaction  ft=getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container,chatMessageFragment);
		ft.commit();
	}
	private void loadChatListFragment()
	{
		ChatListFragment chatListFragment=new ChatListFragment();
		FragmentTransaction  ft=getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container,chatListFragment);
		ft.addToBackStack(null);
		ft.commit();
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
				AsaanUtility.simpleAlert(ChatActivity.this,"An error has occured!.");
			else
			{
				ChatMessage chatMessage=new ChatMessage();
				chatMessage.setRoomId(chatRoom.getId());
				AsaanUtility.selectedChatMessage=chatMessage;
				loadChatMessageFragment();
			}
		}
	}
	
}
