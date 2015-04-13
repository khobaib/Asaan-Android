package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.PaymentInfoActivity;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.model.UserPicture;

public class ProfileFragment extends Fragment{
	private static final int SELECT_PICTURE = 1;
	private static final int CAMERA_REQUEST=2;
	private TextView tvSave,tvPayment;
	private EditText etFirstName,etLastName,etEmail,etTip,etPhone,etPaymentInfo,etFacebookProfile;
	private ProgressDialog pdDialog;
	private ImageView ivProfilePic;
	
	UserPicture userPic;
	Bitmap picture;

	private String selectedImagePath;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.frag_profile4,null,false);
		
		tvSave=(TextView)v.findViewById(R.id.tvSave);
		etFirstName=(EditText)v.findViewById(R.id.etViewFirstName);
		etLastName=(EditText)v.findViewById(R.id.etViewLastName);
		etEmail=(EditText)v.findViewById(R.id.etViewEmail);
		etPhone=(EditText)v.findViewById(R.id.etViewPhone);
		etTip=(EditText)v.findViewById(R.id.etViewTip);
		etFacebookProfile=(EditText)v.findViewById(R.id.etViewFbProf);
		ivProfilePic=(ImageView)v.findViewById(R.id.imgViewProfPic);
		tvPayment=(TextView)v.findViewById(R.id.txtViewPayInfo);
		pdDialog=new ProgressDialog(getActivity());
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pdDialog.show();
				ParseUser user=ParseUser.getCurrentUser();
				String firstName=etFirstName.getText().toString();
				user.put("firstName",firstName);
				String lastName=etLastName.getText().toString();
				user.put("lastName",lastName);
				
				user.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e==null)
						{
							Log.e("response","user updated");
						}
						else
							Log.e("response",e.toString());
						
						pdDialog.dismiss();
					}
				});
				
			}
		});
		ivProfilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//showOptionsDialog();
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
				
				
			}
		});
		tvPayment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(),PaymentInfoActivity.class);
				startActivity(intent);
				
			}
		});
		setUpUI();
	}
	 private void setUpUI()
	  {
		  ParseUser user=ParseUser.getCurrentUser();
		  etFirstName.setText(user.getString("firstName"));
		  etLastName.setText(user.getString("lastName"));
		  
	  }
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == Activity.RESULT_OK) {
				if (requestCode == SELECT_PICTURE) {
					Uri selectedImageUri = data.getData();
					userPic = new UserPicture(selectedImageUri, getActivity().getContentResolver());
					try {
						picture = userPic.getBitmap();
						if (picture != null)
							selectedImagePath = userPic.getPath();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ivProfilePic.setImageBitmap(picture);
					System.out.println(selectedImagePath);
					// profilePhotoUrl = selectedImagePath;

				}
			}
		 if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {  
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            ivProfilePic.setImageBitmap(photo);
	        }  
		super.onActivityResult(requestCode, resultCode, data);
	}
	 private void  showOptionsDialog()
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

			//wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
			window.setAttributes(wlp);
			dialog.show();
			
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					if (position == 2) {
						dialog.dismiss();
					} else if (position == 0) {
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		                startActivityForResult(cameraIntent, CAMERA_REQUEST);
					} else if (position == 1)
					{
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
					}

				}
			});
			
		}
}
