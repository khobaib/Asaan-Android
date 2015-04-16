package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.Store;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreChatTeam;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.ReserveConfirmActivity;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.adapter.ChatHistoryAdapter;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ChatHistoryFragment  extends Fragment{
	
	private ListView lvChats;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	private List<ChatRoom> chatRoomList;
	private List<StoreChatTeam> storeChatTeams=null;
	private ProgressDialog pdDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_chat_history,null,false);
		lvChats=(ListView)v.findViewById(R.id.lv_chatList);
		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pdDialog=new ProgressDialog(getActivity());
		pdDialog.setMessage("please wait....");
		if(AsaanUtility.hasInternet(getActivity()))
			   new GetStoreChatRoomsAndMemberships().execute();
			else
				AsaanUtility.simpleAlert(getActivity(),getResources().getString(R.string.internet_alert));
		lvChats.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ChatRoom chatRoom=chatRoomList.get(position);
				setSelecteStore(chatRoom);
				StoreChatTeam storeChatTeam = checkForMemberShips(storeChatTeams,chatRoom.getStoreId());
				if(storeChatTeam!=null)
				{
					AsaanUtility.USER_ID = storeChatTeam.getUserId();
					loadChatListFragment();
					
					
				}
				else
				{
				ChatMessage chatMessage=new ChatMessage();
				chatMessage.setRoomId(chatRoom.getId());
				AsaanUtility.USER_ID = chatRoom.getUserId();
				AsaanUtility.selectedChatMessage=chatMessage;
				loadChatMessageFragment();
				}
			
				
			}
		});
		
	}
	private void addMembershipStoresToChatRoom()
	{
		int size=storeChatTeams.size();
		for(int i=0;i<size;i++)
		{
			StoreChatTeam storeChatTeam=storeChatTeams.get(i);
			ChatRoom chatRoom=new ChatRoom();
			chatRoom.setId(storeChatTeam.getId());
			chatRoom.setStoreId(storeChatTeam.getStoreId());
			chatRoom.setName(storeChatTeam.getStoreName());
			chatRoom.setUserId(storeChatTeam.getUserId());
			
			chatRoomList.add(chatRoom);
			
		}
	}
	private StoreChatTeam checkForMemberShips(List<StoreChatTeam> chatTeams,long storeId)
	{
		StoreChatTeam storeChatTeam=null;
		if(chatTeams==null)
		   return storeChatTeam;
		int size=chatTeams.size();
		for(int i=0;i<size;i++)
		{
			if(chatTeams.get(i).getStoreId()==storeId)
			{
				storeChatTeam=chatTeams.get(i);
				
			}
		}
		return storeChatTeam;
	}
	private void loadChatListFragment()
	{
		ChatListFragment chatListFragment=new ChatListFragment();
		FragmentTransaction  ft=getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container,chatListFragment);
		ft.addToBackStack(null);
		ft.commit();
	}
	public void loadChatMessageFragment()
	{
		ChatMessageFragment chatMessageFragment=new ChatMessageFragment();
		FragmentTransaction  ft=getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container,chatMessageFragment);
		ft.commit();
	}
	private void setSelecteStore(ChatRoom chatRoom)
	{
		Store store=new Store();
		store.setId(chatRoom.getStoreId());
		store.setName(chatRoom.getName());
		AsaanUtility.selectedStore=store;
	}
	private class GetStoreChatRoomsAndMemberships extends AsyncTask<Void,Void,Void>
	{
     private ChatRoomsAndStoreChatMemberships chMemberships;
     private boolean error=false;
     @Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pdDialog.show();
    	}
		@Override
		protected Void doInBackground(Void... params) {
			GetChatRoomsAndMembershipsForUser getChatRoomsAndMembershipsForUser;
			try {
				 getChatRoomsAndMembershipsForUser=SplashActivity.mStoreendpoint.getChatRoomsAndMembershipsForUser();
				 HttpHeaders httpHeaders=getChatRoomsAndMembershipsForUser.getRequestHeaders();
				 httpHeaders.put(USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
				 chMemberships= getChatRoomsAndMembershipsForUser.execute();
				if(chMemberships!=null)
					Log.e("chat",chMemberships.toPrettyString());
				
			} catch (IOException e) {
				error=true;
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pdDialog.isShowing())
				pdDialog.dismiss();
			if (error)
				AsaanUtility.simpleAlert(getActivity(), getResources().getString(R.string.error_alert));
			else {
				if (chMemberships.getChatRooms() != null) {
					chatRoomList = chMemberships.getChatRooms();
					
				}
				if (chMemberships.getStoreChatMemberships() != null) {

					storeChatTeams = chMemberships.getStoreChatMemberships();
					addMembershipStoresToChatRoom();
				}
				if(chatRoomList!=null)
					setAdapter();
			}

		}
	}
	private void setAdapter()
	{
		ChatHistoryAdapter chatHistoryAdapter=new ChatHistoryAdapter(getActivity(),chatRoomList);
		lvChats.setAdapter(chatHistoryAdapter);
	}

}
