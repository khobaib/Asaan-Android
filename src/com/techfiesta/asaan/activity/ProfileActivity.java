package com.techfiesta.asaan.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.SimpleListAdapter;
import com.techfiesta.asaan.model.UserPicture;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ProfileActivity extends BaseActivity {
	private ImageView ivProfilePic;
	private EditText FirstName;
	private EditText LastName;
	private EditText Phone;
	private Button btnSave;

	String profilePhotoUrl;
	String firstName;
	String lastName;
	String phoneNumber;
	String passWord;
	Bitmap picture;

	UserPicture userPic;
	
	private static final int SELECT_PICTURE = 1;
	private static final int CAMERA_REQUEST=2;


	private String selectedImagePath;
	private ProgressDialog pdialog;
	 Uri outputFileUri;
	 private boolean isNewprofilePicAdded=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();

		setContentView(R.layout.activity_profile);
		FirstName = (EditText) findViewById(R.id.et_first_name);
		LastName = (EditText) findViewById(R.id.et_last_name);
		Phone = (EditText) findViewById(R.id.et_phone);
		ivProfilePic = (ImageView) findViewById(R.id.iv_propic_add);
		btnSave = (Button) findViewById(R.id.b_save);
		//btnSkip = (Button) findViewById(R.id.b_skip);
		
		pdialog = new ProgressDialog(ProfileActivity.this);
		pdialog.setMessage("Loading...");
		
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				firstName = FirstName.getText().toString();
				lastName = LastName.getText().toString();

				phoneNumber = Phone.getText().toString();

				if (firstName == null || firstName.length() == 0) {
					// alert
					AsaanUtility.simpleAlert(ProfileActivity.this, "Please provide First Name");
				} else if (lastName == null || lastName.length() == 0) {
					// alert
					AsaanUtility.simpleAlert(ProfileActivity.this, "Please provide Last Name");
				} else if (phoneNumber == null || validatePhoneNumber(phoneNumber) == false) {
					// alert
					AsaanUtility.simpleAlert(ProfileActivity.this, "Please provide valid phone Number");
				} else {
					saveUserData();
				}

			}
		});
		/*btnSkip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ProfileActivity.this,PaymentInfoActivity.class);
				startActivity(intent);
				
			}
		});*/
		ivProfilePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showOptionsDialog();

			}
		});
    setUpUI();
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
		  FirstName.setText(user.getString("firstName"));
		  LastName.setText(user.getString("lastName"));
	  }
	  
  }
	private void saveUserData() {
		ParseUser user = null;
		try {
			user = ParseUser.getCurrentUser();
		}
		catch(Exception e)
		{
			Log.e("Parse", "Fail to get user.");
		}

	  
		//
		if (selectedImagePath != null) {
			File picFile = new File(selectedImagePath);
			byte[] data = new byte[(int) picFile.length()];
			FileInputStream fileInputStream;
			try {

				fileInputStream = new FileInputStream(picFile);
				fileInputStream.read(data);
				ParseFile pFile = new ParseFile(picFile.getName(), data);

				user.put("picture", pFile);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		pdialog.show();
		
		if(user != null)
        {
			user.put("firstName", firstName);
			user.put("lastName", lastName);
			user.put("phone", phoneNumber);	  
			user.saveInBackground(new SaveCallback() {
	
				@Override
				public void done(ParseException e) {
					pdialog.dismiss();
					if (e == null) {
	//					AsaanUtility.simpleAlert(ProfileActivity.this, "Profile Updated");
						Intent intent = new Intent(ProfileActivity.this, StoreListActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
						intent=new Intent(getResources().getString(R.string.intent_filter_finish));
						sendBroadcast(intent);
					} else {
						Log.e("error", "updating user failed" + e.toString());
						AsaanUtility.simpleAlert(ProfileActivity.this, "Error In Updating profile");
					}
	
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
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			 if (resultCode == Activity.RESULT_OK) {
					if (requestCode == SELECT_PICTURE) {
						Uri selectedImageUri = data.getData();
						userPic = new UserPicture(selectedImageUri, getContentResolver());
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
		           
					userPic = new UserPicture(outputFileUri,getContentResolver());
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
	private static boolean validatePhoneNumber(String phoneNo) {

		/*
		 * 1234567890 123-456-7890 123-456-7890 x1234 123-456-7890 ext1234
		 * (123)-456-7890 123.456.7890 123 456 7890
		 */
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}"))
			return true;
		else if (phoneNo.matches("\\d{11}"))
			return true;
		// else if(phoneNo.matches("\\+\\88\\d{11}")) return true;
		// else if(phoneNo.matches("\\+\\88\\d{10}")) return true;
		// validating phone number with -, or spaces
		else if (phoneNo.matches("\\d{3}[-\\s]\\d{3}[-\\s]\\d{4}"))
			return true;
		// validating phone number with extension length from 3 to 5
		else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
			return true;
		// validating phone number where area code is in braces ()
		else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return true;
		// return false if nothing matches the input
		else
			return false;

	}
	 private void  showOptionsDialog()
		{
			final Dialog dialog=new Dialog(ProfileActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_attatchment);
			ArrayList<String> list=new ArrayList<>();
			list.add("Take Photo");
			list.add("Choose from existing photos.");
			list.add("Cancel");
			
			ListView lv=(ListView)dialog.findViewById(R.id.lv_attatch);
			SimpleListAdapter adapter=new SimpleListAdapter(ProfileActivity.this,R.layout.row_simple_list,list);
			
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

}
