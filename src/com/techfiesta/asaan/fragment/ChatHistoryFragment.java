package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatRoomsAndMembershipsForUser;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoomsAndStoreChatMemberships;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseUser;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ChatHistoryFragment  extends Fragment{
	
	private ListView lvChats;
	private static String USER_AUTH_TOKEN_HEADER_NAME = "asaan-auth-token";
	ArrayList<String> chatRoomList=new ArrayList<String>();
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
		new GetStoreChatRoomsAndMemberships().execute();
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
			if(pdDialog.isShowing())
				pdDialog.dismiss();
			if(error)
			  AsaanUtility.simpleAlert(getActivity(),"An error occured.");
			else
			{
				if(chMemberships!=null)
				{ 
					int size=chMemberships.getChatRooms().size();
					for(int i=0;i<size;i++)
					    chatRoomList.add(chMemberships.getChatRooms().get(i).getName());
					setAdapter();
				}
			}
			
		}
	}
	private void setAdapter()
	{
		SimpleListAdapter simpleListAdapter=new SimpleListAdapter(getActivity(),R.layout.row_simple_list,chatRoomList);
		lvChats.setAdapter(simpleListAdapter);
	}

}
