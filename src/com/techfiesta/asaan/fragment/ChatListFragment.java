package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatMessagesForStoreOrRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatUsersForRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessagesAndUsers;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUserArray;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.adapter.ChatMessageAdapter;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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

public class ChatListFragment extends Fragment {
	private ImageView ivAttach;
	private EditText edtChatBox;
	private TextView tvSend;
	private ListView lvChat;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	List<ChatMessage> chatList;
	ChatMessageAdapter adapter;
	long one_day_in_mili=1000*60*60*24;
	private HashMap<Long,ChatUser> userHashMap=new HashMap<Long,ChatUser>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_chat_list,null,false);
		lvChat=(ListView)v.findViewById(R.id.lv_chats);
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new GetChatMessagesForRoomFromServer().execute();
		
		lvChat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AsaanUtility.selectedChatMessage=chatList.get(position);
				loadChatMessageFragment();
			}
		});
	}
	private void loadChatMessageFragment()
	{
		
		ChatMessageFragment chatMessageFragment=new ChatMessageFragment();
		FragmentTransaction  ft=getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container,chatMessageFragment);
		ft.commit();
	}
	private class GetChatMessagesForRoomFromServer extends AsyncTask<Void,Void,Void>
	{
		private boolean error=false;
		ChatMessagesAndUsers chatMessagesAndUsers;

		@Override
		protected Void doInBackground(Void... params) {
			try {
			GetChatMessagesForStoreOrRoom getChatUsersForRoom=SplashActivity.mStoreendpoint.getChatMessagesForStoreOrRoom(0,true,50,System.currentTimeMillis()-7*one_day_in_mili,AsaanUtility.selectedStore.getId());
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
				if(chatMessagesAndUsers.getChatUsers()!=null)
					createUserHashMap();
				if(chatMessagesAndUsers.getChatMessages()!=null)
					setAdapter();
			}
		}
		private void setAdapter()
		{
			chatList=chatMessagesAndUsers.getChatMessages();
			Collections.reverse(chatList);
			adapter=new ChatMessageAdapter(getActivity(),chatList,userHashMap);
			lvChat.setAdapter(adapter);
			lvChat.smoothScrollToPosition(chatList.size()-1);
			
		}
		private void createUserHashMap()
		{
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
	
	

}
