package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatMessagesForStoreOrRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessagesAndUsers;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUserArray;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeam;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SendCallback;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.ChatActivity;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.adapter.ChatMessageAdapter;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.broadcastreceiver.PushNotificationReceiver;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Dialog;
import android.app.Fragment;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatMessageFragment extends Fragment {
	private ImageView ivAttach;
	private EditText edtChatBox;
	private TextView tvSend;
	private ListView lvChat;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	List<ChatMessage> chatList=new ArrayList<ChatMessage>();
	ChatMessageAdapter adapter=null;
	long one_day_in_mili=1000*60*60*24;
	private HashMap<Long,ChatUser> userHashMap=new HashMap<Long,ChatUser>();
	private List<ChatUser> userList;
	PushNotificationReceiver pushNotificationReceiver = new PushNotificationReceiver() {
		public void onReceive(android.content.Context context, android.content.Intent intent) {

		
			new GetChatMessagesForRoomFromServer().execute();
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_chat_message,null,false);
		lvChat=(ListView)v.findViewById(R.id.lv_chats);
		ivAttach=(ImageView)v.findViewById(R.id.iv_attatch);
		edtChatBox=(EditText)v.findViewById(R.id.et_chatbox);
		tvSend=(TextView)v.findViewById(R.id.tv_send);
		ivAttach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAttachmentDialog();
				
			}
		});
		tvSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendMessage(edtChatBox.getText().toString());
				
			}
		});
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new GetChatMessagesForRoomFromServer().execute();
	}
	@Override
	public void onResume() {
		super.onResume();
		getActivity().registerReceiver(pushNotificationReceiver,new IntentFilter("com.asaan"));
	}
	private void  showAttachmentDialog()
	{
		final Dialog dialog=new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_attatchment);
		ArrayList<String> list=new ArrayList<>();
		list.add("Take Photo");
		list.add("Choose from existing photos.");
		list.add("Cancel");
		
		ListView lv=(ListView)dialog.findViewById(R.id.lv_attatch);
		SimpleListAdapter adapter=new SimpleListAdapter(getActivity(),R.layout.row_simple_list,list);
		
		lv.setAdapter(adapter);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();

		wlp.gravity = Gravity.BOTTOM;
		//wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(wlp);
		dialog.show();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.e("HLW",""+position);
				if (position == 0) {

				} else if (position == 1) {

				} else if (position == 2)
					dialog.dismiss();

			}
		});
		
	}
	private void sendMessage(String msg)
	{
		if(!msg.equals(""))
		{
			ChatMessage chatMessage=new ChatMessage();
			chatMessage.setTxtMessage(msg);
			chatMessage.setRoomId(AsaanUtility.selectedChatMessage.getRoomId());
			
			new SaveChatMessageInServer(chatMessage).execute();
		}
			
	}
	
	private class GetChatMessagesForRoomFromServer extends AsyncTask<Void,Void,Void>
	{
		private boolean error=false;
		ChatMessagesAndUsers chatMessagesAndUsers;

		@Override
		protected Void doInBackground(Void... params) {
			try {
			GetChatMessagesForStoreOrRoom getChatUsersForRoom=SplashActivity.mStoreendpoint.getChatMessagesForStoreOrRoom(0,false,50,getLastModifiedTime(),AsaanUtility.selectedChatMessage.getRoomId());
			HttpHeaders httpHeaders=getChatUsersForRoom.getRequestHeaders();
			httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
			chatMessagesAndUsers=getChatUsersForRoom.execute();
			if(chatMessagesAndUsers!=null)
				Log.e("msg",chatMessagesAndUsers.toPrettyString());
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
				AsaanUtility.simpleAlert(getActivity(),"An error has occured!.");
			else
			{
				if(chatMessagesAndUsers.getChatMessages()!=null)
					addMessagesToChatist();
				if(chatMessagesAndUsers.getChatUsers()!=null)
					createUserHashMap();
				setAdapter();
				
					
			}
		}
		private long getLastModifiedTime()
		{
			if(chatList.size()==0)
				return 0;
			else
				return  chatList.get(chatList.size()-1).getModifiedDate();
		}
		private void setAdapter()
		{
			
			Log.e("SIZE",""+chatList.size());
				if(adapter==null)
				{
				adapter=new ChatMessageAdapter(getActivity(),chatList,userHashMap);
				lvChat.setAdapter(adapter);
				}
				else
				   adapter.notifyDataSetChanged();
			lvChat.smoothScrollToPosition(chatList.size()-1);
			
		}
		private void addMessagesToChatist()
		{
			if(chatList.size()==0)
				Collections.reverse(chatMessagesAndUsers.getChatMessages());
			int size=chatMessagesAndUsers.getChatMessages().size();
			for(int i=0;i<size;i++)
			{
				chatList.add(chatMessagesAndUsers.getChatMessages().get(i));
			}
		}
		private void createUserHashMap()
		{
			userList=chatMessagesAndUsers.getChatUsers();
			if(chatMessagesAndUsers.getChatUsers()!=null)
			{
			   int size=chatMessagesAndUsers.getChatUsers().size();
			   for(int i=0;i<size;i++)
			   {
				   if(!userHashMap.containsKey(chatMessagesAndUsers.getChatUsers().get(i).getUserId()))
						   userHashMap.put(chatMessagesAndUsers.getChatUsers().get(i).getUserId(),chatMessagesAndUsers.getChatUsers().get(i));
			   }
			   
			}
		}
		
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
			if(error)
				AsaanUtility.simpleAlert(getActivity(),"An error has occured!.");
			else
			{
				addMessageToList(chatMessage);
				sendPush(chatMessage);
				
			}
			
		}
		
	}
	private void sendPush(ChatMessage message)
	{
		ParseQuery pushQuery=ParseInstallation.getQuery();
		
		int size=userList.size();
		ArrayList<String> objectIdList=new ArrayList<>();
		String curnt_user_object_id=ParseUser.getCurrentUser().getObjectId().toString();
		for(int i=0;i<size;i++)
		{
			if(!curnt_user_object_id.equals(userList.get(i).getObjectId()))
			{
				Log.e("object Id",userList.get(i).getObjectId());
				objectIdList.add(userList.get(i).getObjectId());
			}
		}
		pushQuery.whereContainedIn("objectId",objectIdList);
		ParsePush parsePush=new ParsePush();
		parsePush.setQuery(pushQuery);
		parsePush.setMessage(message.getTxtMessage());
		parsePush.sendInBackground(new SendCallback() {
			
			@Override
			public void done(ParseException e) {
				if(e==null)
				{
					Log.e("PUSH","successfull");
				}
				else
					e.printStackTrace();
				
			}
		});
		
	}
	public void addMessageToList(ChatMessage message) {
		
		chatList.add(message);
		adapter.notifyDataSetChanged();
		edtChatBox.setText("");
		lvChat.smoothScrollByOffset(chatList.size()-1);
	}
	@Override
	public void onStop() {
		getActivity().unregisterReceiver(pushNotificationReceiver);
		super.onStop();
	}

}
