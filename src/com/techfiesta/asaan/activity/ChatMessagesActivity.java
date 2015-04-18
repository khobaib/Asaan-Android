package com.techfiesta.asaan.activity;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.GetChatMessagesForStoreOrRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.Storeendpoint.SaveChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessage;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatMessagesAndUsers;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatUser;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.ChatMessageAdapter;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.broadcastreceiver.PushNotificationReceiver;
import com.techfiesta.asaan.model.UserPicture;
import com.techfiesta.asaan.utility.AsaanUtility;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatMessagesActivity extends BaseActivity {
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
	
	private static final int SELECT_PICTURE = 1;
	private static final int CAMERA_REQUEST=2;

    private int TEXT_MESSAGE=0;
    private int PIC_MESSAGE=1;
	private Uri outputFileUri;
	UserPicture userPic;
	Bitmap picture;
	PushNotificationReceiver pushNotificationReceiver = new PushNotificationReceiver() {
		public void onReceive(android.content.Context context, android.content.Intent intent) {

		
			new GetChatMessagesForRoomFromServer().execute();
		};
	};
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_chat_message);
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		lvChat=(ListView)findViewById(R.id.lv_chats);
		ivAttach=(ImageView)findViewById(R.id.iv_attatch);
		edtChatBox=(EditText)findViewById(R.id.et_chatbox);
		tvSend=(TextView)findViewById(R.id.tv_send);
		ivAttach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAttachmentDialog();
				
			}
		});
		tvSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(!edtChatBox.getText().toString().equals(""))
				sendMessage(edtChatBox.getText().toString(),TEXT_MESSAGE);
				
			}
		});
		new GetChatMessagesForRoomFromServer().execute();
	}
	@Override
	protected void onResume() {
		registerReceiver(pushNotificationReceiver,new IntentFilter(getResources().getString(R.string.intent_filter_push)));
		super.onResume();
	}
	private void  showAttachmentDialog()
	{
		final Dialog dialog=new Dialog(ChatMessagesActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_attatchment);
		ArrayList<String> list=new ArrayList<>();
		list.add("Take Photo");
		list.add("Choose from existing photos.");
		list.add("Cancel");
		
		ListView lv=(ListView)dialog.findViewById(R.id.lv_attatch);
		SimpleListAdapter adapter=new SimpleListAdapter(ChatMessagesActivity.this,R.layout.row_simple_list,list);
		
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
				
				if (position == 0) {
					prepareCamera();
					
				} else if (position == 1) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
				}
				dialog.dismiss();
			}
		});
		
	}
	void prepareCamera()
	{
		final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/"; 
        File newdir = new File(dir); 
        newdir.mkdirs();
        String file = dir+System.currentTimeMillis()+".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {}       

        outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent,CAMERA_REQUEST);
	}
	private void sendMessage(String msg,int type)
	{
		ChatMessage chatMessage=new ChatMessage();
		chatMessage.setRoomId(AsaanUtility.selectedChatMessage.getRoomId());
		if(type==TEXT_MESSAGE)
		    chatMessage.setTxtMessage(msg);
		else
			if (type == PIC_MESSAGE) {
			chatMessage.setFileMessage(msg);
			String defMessage = getResources().getString(R.string.default_pic_message);
			chatMessage.setTxtMessage(defMessage);
		}

		new SaveChatMessageInServer(chatMessage).execute();
			
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
				AsaanUtility.simpleAlert(ChatMessagesActivity.this,"An error has occured!.");
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
				adapter=new ChatMessageAdapter(ChatMessagesActivity.this,chatList,userHashMap);
				lvChat.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				}
				else
				   adapter.notifyDataSetChanged();
			
				new SmoothScrollingAsynkTask().execute();
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
				AsaanUtility.simpleAlert(ChatMessagesActivity.this,"An error has occured!.");
			else
			{
				edtChatBox.setText("");
				new GetChatMessagesForRoomFromServer().execute();
				if(userList!=null && userList.size()>0)
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
	@Override
	public void onStop() {
		unregisterReceiver(pushNotificationReceiver);
		super.onStop();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				userPic = new UserPicture(selectedImageUri,getContentResolver());
				try {
					picture = userPic.getBitmap();
					if (picture != null)
					{
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
						saveImageInParse(stream.toByteArray());
						
					}
					else
						Log.e("Bitmap","bitmap null");
						
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
			Log.e("msg", "picture taken");
			userPic = new UserPicture(outputFileUri,getContentResolver());
			try {
				picture = userPic.getBitmap();
				if (picture != null)
				{
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
					saveImageInParse(stream.toByteArray());
					
				}
				else
					Log.e("Bitmap","bitmap null");
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private class SmoothScrollingAsynkTask extends AsyncTask<Void,Void, Void>
	{

		@Override
		protected void onPreExecute() {
			Log.e("scroll","started");
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... params) {
			if(chatList.size()>1)
			{
			lvChat.setSelection(chatList.size()-1);
			lvChat.smoothScrollToPosition(chatList.size()-1);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			Log.e("scroll","finished");
			super.onPostExecute(result);
		}
	}
	private void saveImageInParse(byte[] bytes)
	{
		
		String filename=AsaanUtility.USER_ID+System.currentTimeMillis()+"-picture.jpg";
		final ParseFile parseFile=new ParseFile(filename,bytes);
		parseFile.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if(e==null)
				{
					Log.e("PIC URL",parseFile.getUrl());
					sendMessage(parseFile.getUrl(),PIC_MESSAGE);
				}
				else
				{
					Log.e("PIC URL Error","error");
				}
				
			}
		});
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
