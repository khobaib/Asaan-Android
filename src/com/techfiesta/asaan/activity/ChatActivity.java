package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatMessagesForStoreOrRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeamCollection;
//import com.google.android.gms.internal.ms;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.R.bool;
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
import android.view.Menu;
import android.view.MenuItem;
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

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatMessagesForStoreOrRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessagesAndUsers;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeam;
import com.google.android.gms.internal.er;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.ChatMessageAdapter;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.broadcastreceiver.PushNotificationReceiver;
import com.techfiesta.asaan.fragment.StoreListFragment;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class ChatActivity extends BaseActivity{
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private ListView lvChat;
	List<ChatMessage> chatList;
	ChatMessageAdapter adapter;
	long one_day_in_mili = 1000 * 60 * 60 * 24;
	private HashMap<Long, ChatUser> userHashMap = new HashMap<Long, ChatUser>();
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_chat_list);
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		lvChat = (ListView) findViewById(R.id.lv_chats);
		lvChat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AsaanUtility.selectedChatMessage = chatList.get(position);
				loadChatMessagesActivity(true);
			}
		});
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
					loadChatMessagesActivity(false);

				} else {
					StoreChatTeam storeChatTeam = checkForMemberShips(chatRoomsAndStoreChatMemberships
							.getStoreChatMemberships());
					if (storeChatTeam != null) {
						
						AsaanUtility.USER_ID = storeChatTeam.getUserId();
						loadChats();
						
					} else {
						new SaveChatRoomInServer().execute();
					}

				}
			}
					
		}
	
	}
	private void loadChatMessagesActivity(boolean keepHistory)
	{
		Intent intent=new Intent(ChatActivity.this,ChatMessagesActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		if(!keepHistory)
			finish();
	}
	private void loadChats()
	{
		new GetChatMessagesForRoomFromServer().execute();
		
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
				AsaanUtility.USER_ID = chatRoom.getUserId();
				loadChatMessagesActivity(false);
			}
		}
	}
	private class GetChatMessagesForRoomFromServer extends AsyncTask<Void, Void, Void> {
		private boolean error = false;
		ChatMessagesAndUsers chatMessagesAndUsers;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				GetChatMessagesForStoreOrRoom getChatUsersForRoom = SplashActivity.mStoreendpoint
						.getChatMessagesForStoreOrRoom(0, true, 50, System.currentTimeMillis() - 7 * one_day_in_mili,
								AsaanUtility.selectedStore.getId());
				HttpHeaders httpHeaders = getChatUsersForRoom.getRequestHeaders();
				httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				chatMessagesAndUsers = getChatUsersForRoom.execute();
				if (chatMessagesAndUsers != null)
					Log.e("msg", chatMessagesAndUsers.toPrettyString());
			} catch (IOException e) {
				error = true;
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (error)
				AsaanUtility.simpleAlert(ChatActivity.this, "An error has occured!.");
			else {
				if (chatMessagesAndUsers.getChatUsers() != null)
					createUserHashMap();
				if (chatMessagesAndUsers.getChatMessages() != null)
					setAdapter();
			}
		}

		private void setAdapter() {
			chatList = chatMessagesAndUsers.getChatMessages();
			Collections.reverse(chatList);
			adapter = new ChatMessageAdapter(ChatActivity.this, chatList, userHashMap);
			lvChat.setAdapter(adapter);
			lvChat.smoothScrollToPosition(chatList.size() - 1);

		}

		private void createUserHashMap() {
			if (chatMessagesAndUsers.getChatUsers() != null) {
				int size = chatMessagesAndUsers.getChatUsers().size();
				for (int i = 0; i < size; i++) {
					if (!userHashMap.containsKey(chatMessagesAndUsers.getChatUsers().get(i).getUserId()))
						userHashMap.put(chatMessagesAndUsers.getChatUsers().get(i).getUserId(), chatMessagesAndUsers
								.getChatUsers().get(i));
				}
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
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
