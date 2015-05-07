package com.techfiesta.asaan.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.userendpoint.Userendpoint.GetUserCards;
import com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCardCollection;
import com.google.api.client.http.HttpHeaders;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.PaymentInfoActivity;
import com.techfiesta.asaan.activity.SplashActivity;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.model.UserPicture;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class ProfileFragment extends Fragment{
	private static final int SELECT_PICTURE = 1;
	private static final int CAMERA_REQUEST=2;
	private TextView tvSave,tvPayment,tvTips;
	private EditText etFirstName,etLastName,etEmail,etPhone;
	private SeekBar sbTips;
	private ProgressDialog pdDialog;
	private ImageView ivProfilePic;
	private ImageLoader imageLoader;
	private boolean isNewprofilePicAdded=false;
	
	private UserPicture userPic;
	private Bitmap picture;

	private String selectedImagePath;
	  Uri outputFileUri;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.frag_profile4,null,false);
		
		tvSave=(TextView)v.findViewById(R.id.tvSave);
		etFirstName=(EditText)v.findViewById(R.id.etViewFirstName);
		etLastName=(EditText)v.findViewById(R.id.etViewLastName);
		etEmail=(EditText)v.findViewById(R.id.etViewEmail);
		etPhone=(EditText)v.findViewById(R.id.etViewPhone);
		tvTips=(TextView)v.findViewById(R.id.txtViewLblTip);
		sbTips=(SeekBar)v.findViewById(R.id.seekBarTips);
		ivProfilePic=(ImageView)v.findViewById(R.id.imgViewProfPic);
		tvPayment=(TextView)v.findViewById(R.id.txtViewPayInfo);
		pdDialog=new ProgressDialog(getActivity());
		
		sbTips.setMax(50);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageLoader=new ImageLoader(getActivity());
		tvSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pdDialog.setMessage("Please wait....");
				pdDialog.show();

				if (isNewprofilePicAdded) {
					if (picture != null) {
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
						saveImageInParse(stream.toByteArray());
					}
				} else
					saveUserInParse("");

			}
		});
		ivProfilePic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showOptionsDialog();
				   
				   
				}
				
		});
		tvPayment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(),PaymentInfoActivity.class);
				startActivity(intent);
				
			}
		});
		sbTips.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(progress<15)
				{
					sbTips.setProgress(15);
					tvTips.setText("Tips(15%)");
				}
				else
				 tvTips.setText("Tips("+progress+"%)");
			}
		});
		setUpUI();
	}
	private void saveImageInParse(byte[] bytes)
	{
		ParseUser user = null;
		try {
			user = ParseUser.getCurrentUser();
		}
		catch(Exception e)
		{
			Log.e("Parse", "Fail to get user.");
		}
		String filename= null;
		if(user != null)
		{
			filename = user.getObjectId()+System.currentTimeMillis()+"-picture.jpg";
		}
		if(filename !=null)
		{
			final ParseFile parseFile=new ParseFile(filename,bytes);
			parseFile.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					if(e==null)
					{
						Log.e("PIC URL",parseFile.getUrl());
						saveUserInParse(parseFile.getUrl());
						
					}
					else
					{
						Log.e("PIC URL Error","error");
					}
					
				}
			});
		}
	}
	private void saveUserInParse(String url)
	{
		ParseUser user = null;
		try {
			user = ParseUser.getCurrentUser();
		}
		catch(Exception e)
		{
			Log.e("Parse", "Fail to get user.");
		}

		if(user != null)
		{
			String firstName=etFirstName.getText().toString();
			user.put("firstName",firstName);
			String lastName=etLastName.getText().toString();
			user.put("lastName",lastName);
			String phone=etPhone.getText().toString();
			user.put("phone",phone);
			int tips=sbTips.getProgress();
			user.put("tip",""+tips);
			if(!url.equals(""))
				user.put("profilePhotoUrl",url);
			
			user.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					if(e==null)
					{
						Log.e("response","user updated");
						AsaanUtility.simpleAlert(getActivity(),"Your profile is updated.");
					}
					else
						Log.e("response",e.toString());
					if(pdDialog.isShowing())
						pdDialog.dismiss();
				}
			});
		}
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
	 private void setUpUI()
	  {
		 ParseUser user = null;
			try {
				user = ParseUser.getCurrentUser();
			}
			catch(Exception e)
			{
				Log.e("Parse", "Fail to get user.");
			}
		if(user != null)
		{
		  etFirstName.setText(user.getString("firstName"));
		  etLastName.setText(user.getString("lastName"));
		  etEmail.setText(user.getEmail());
		  etPhone.setText(user.getString("phone"));
		  int tips;
		  if(user.getString("tip")==null)
			  tips=15;
		  else	  
		    tips=Integer.valueOf(user.getString("tip"));
		  if(tips>15)
		  {
		     sbTips.setProgress(tips);
		     tvTips.setText("Tips("+tips+"%)");
		  }
		  else
		  {
			  tvTips.setText("Tips(15%)");
			  sbTips.setProgress(15);
		  }
		  Log.e("MSG",user.getString("tip")+"");
		  imageLoader.DisplayImage(user.getString("profilePhotoUrl"), ivProfilePic);
		  
		  if(AsaanUtility.defCard != null)
			{
			  tvPayment.setText("Last 4 digits of the credit card: " + AsaanUtility.defCard.getLast4());
			}
			else
			{
				new GetUserCardsFromServerInProfile().execute();
			}
		} 
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
						
						e.printStackTrace();
					}
                    
					isNewprofilePicAdded=true;
					ivProfilePic.setImageBitmap(picture);
					System.out.println(selectedImagePath);
					// profilePhotoUrl = selectedImagePath;

				}
			}
		 if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {  
	            Log.e("msg","picture saved");
	           
				userPic = new UserPicture(outputFileUri, getActivity().getContentResolver());
				try {
					picture = userPic.getBitmap();
					if (picture != null)
						selectedImagePath = userPic.getPath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isNewprofilePicAdded=true;
				ivProfilePic.setImageBitmap(picture);
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
					
					 if (position == 0) {
						prepareCamera();
					} else if (position == 1)
					{
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
					}
					dialog.dismiss();

				}
			});
			
		}
	 
	 private class GetUserCardsFromServerInProfile extends AsyncTask<Void,Void,Void>
		{
			
			@Override
			protected void onPreExecute() {
				
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				GetUserCards getUserCards;
				try {
					getUserCards=SplashActivity.mUserendpoint.getUserCards();
					HttpHeaders httpHeaders = getUserCards.getRequestHeaders();
					httpHeaders.put(Constants.USER_AUTH_TOKEN_HEADER_NAME, ParseUser.getCurrentUser().getString("authToken"));
					UserCardCollection userCardCollection= getUserCards.execute();
					if(userCardCollection!=null)
					{
						Log.e("Response",userCardCollection.toPrettyString());
						if(userCardCollection.getItems()!= null && userCardCollection.getItems().size()>=0)
						{
							AsaanUtility.defCard=userCardCollection.getItems().get(0);
						}
						else
							AsaanUtility.defCard=null;
					}
					else
						AsaanUtility.defCard=null;
						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				if(AsaanUtility.defCard != null)
				{
					tvPayment.setText("Last 4 digits of the credit card: " + AsaanUtility.defCard.getLast4());
				}
				
			}
		}
}
