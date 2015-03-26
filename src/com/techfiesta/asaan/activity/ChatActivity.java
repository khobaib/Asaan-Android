package com.techfiesta.asaan.activity;

import java.util.ArrayList;

import lombok.core.Main;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.SimpleListAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity{
	private ImageView ivAttach;
	private EditText edtChatBox;
	private TextView tvSend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
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
				
				
			}
		});
	}
	
	private void  showAttachmentDialog()
	{
		final Dialog dialog=new Dialog(ChatActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_attatchment);
		ArrayList<String> list=new ArrayList<>();
		list.add("Take Photo");
		list.add("Choose from existing photos.");
		list.add("Cancel");
		
		ListView lv=(ListView)dialog.findViewById(R.id.lv_attatch);
		SimpleListAdapter adapter=new SimpleListAdapter(ChatActivity.this,R.layout.row_simple_list,list);
		
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

				} else if (position == 1) {

				} else if (position == 2)
					dialog.dismiss();

			}
		});
		
	}

}
