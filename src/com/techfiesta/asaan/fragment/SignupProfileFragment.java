package com.techfiesta.asaan.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.communication.Communicator;
import com.techfiesta.asaan.model.UserPicture;
import com.techfiesta.asaan.utility.AsaanUtility;

public class SignupProfileFragment extends Fragment{
	
	ImageView ProfilePicture;
	EditText FirstName;
	EditText LastName;
	EditText Email;
	EditText Password;
	EditText Phone;
	Button BirthDay;
	ImageView NEXT;
	
	String profilePhotoUrl;
	String firstName;
	String lastName;
	String eMail;
	String phoneNumber;
	String passWord;
	Bitmap picture;
	
	UserPicture userPic;
	
	Communicator communicate;
	
	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath;


	public SignupProfileFragment() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.sign_up_frag_1, container , false);
		if(savedInstanceState!=null){
			UserPicture userPhoto = (UserPicture) savedInstanceState.getSerializable("picture");
			
			try {
				picture = userPhoto.getBitmap();
				if(picture!=null) profilePhotoUrl = userPhoto.getPath();
				ImageView Profile = (ImageView) view.findViewById(R.id.ivProfilePicAdd);
				Profile.setImageBitmap(picture);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if(userPic!=null) outState.putSerializable("picture", userPic);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	
		 if (resultCode == Activity.RESULT_OK) {
			/* if (requestCode == SELECT_PICTURE) {
		            Uri selectedImageUri = data.getData();
		            Log.d("URI VAL", "selectedImageUri = " + selectedImageUri.toString());
		            selectedImagePath = getPath(selectedImageUri);

		            if(selectedImagePath!=null){         
		                // IF LOCAL IMAGE, NO MATTER IF ITS DIRECTLY FROM GALLERY (EXCEPT PICASSA ALBUM),
		                // OR OI/ASTRO FILE MANAGER. EVEN DROPBOX IS SUPPORTED BY THIS BECAUSE DROPBOX DOWNLOAD THE IMAGE 
		                // IN THIS FORM - file:///storage/emulated/0/Android/data/com.dropbox.android/...
		                System.out.println("local image"); 
		            }
		            else{
		                System.out.println("picasa image!");
		                loadPicasaImageFromGallery(selectedImageUri);
		            }
		            
		            profilePhotoUrl = selectedImagePath;
		            System.out.println(profilePhotoUrl);
		        }*/
		        if (requestCode == SELECT_PICTURE) {
		            Uri selectedImageUri = data.getData();
		            userPic = new UserPicture(selectedImageUri, getActivity().getContentResolver());
		            try {
						 picture = userPic.getBitmap();
						 if(picture!=null) selectedImagePath = userPic.getPath();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		          
		            ProfilePicture.setImageBitmap(picture);
		            System.out.println(selectedImagePath);
		            profilePhotoUrl = selectedImagePath;

		        }
		    }
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		communicate = (Communicator) getActivity();
		NEXT =  (ImageView) getActivity().findViewById(R.id.ivForward);
		
		FirstName = (EditText) getActivity().findViewById(R.id.etFirstNameSignUp);
		LastName = (EditText) getActivity().findViewById(R.id.etLastNameSignUp);
		Email = (EditText) getActivity().findViewById(R.id.etEmailSignUp);
		Password = (EditText) getActivity().findViewById(R.id.etPasswordSignUp);
		Phone = (EditText) getActivity().findViewById(R.id.etPhoneNoSignUp);
		ProfilePicture = (ImageView) getActivity().findViewById(R.id.ivProfilePicAdd);
		ProfilePicture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setType("image/*");
	            intent.setAction(Intent.ACTION_GET_CONTENT);
	            startActivityForResult(Intent.createChooser(intent,
	                    "Select Picture"), SELECT_PICTURE);
				
			}
		});
		
		NEXT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				firstName = FirstName.getText().toString();
				lastName = LastName.getText().toString();
				eMail = Email.getText().toString();
				phoneNumber = Phone.getText().toString();
				passWord = Password.getText().toString();
				
				if(firstName==null || firstName.length()==0){
					//alert
					AsaanUtility.simpleAlert(getActivity(), "Please provide First Name");
				}else if(lastName==null || lastName.length()==0){
					//alert
					AsaanUtility.simpleAlert(getActivity(), "Please provide Last Name");
				}else if(eMail==null || !eMail.endsWith(".com")){
					//alert
					AsaanUtility.simpleAlert(getActivity(), "Please provide valid e-mail");
				}else if(phoneNumber==null || validatePhoneNumber(phoneNumber)==false ){
					//alert
					AsaanUtility.simpleAlert(getActivity(), "Please provide valid phone Number");
				}else if(passWord == null || passWord.length()==0){
					//alert
					AsaanUtility.simpleAlert(getActivity(), "Please provide a password");
				}else if(profilePhotoUrl==null){
					profilePhotoUrl ="";
					AsaanUtility.simpleAlert(getActivity(), "Please choose a profile picture");
				}else{
					SignupUser();
				}
				
				
			}
		});
		
	}
	

	protected void SignupUser() {
		
		ParseUser pUser = new ParseUser();
		pUser.setUsername(eMail);
		pUser.setEmail(eMail);
		pUser.setPassword(passWord);
		pUser.put("firstName", firstName);
		pUser.put("lastName", lastName);
		pUser.put("profilePhotoUrl", profilePhotoUrl);
		
		pUser.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
			if(e==null){
				Log.d("SUCEESS", " "+firstName + "user signup");
				communicate.respond(1);
			}else{
				Log.e("FAILURE"," "+firstName + "something went wrong in signup"+ " "+e.getMessage());
				communicate.respond(1);
			}
				
			}
		});
		
		
		
	}
	
	/*-----------------------------------------------------------***-----------------------------
	 * provided from http://stackoverflow.com/a/17285119
	 * 
	 * 
	 */
	// NEW METHOD FOR PICASA IMAGE LOAD
	private void loadPicasaImageFromGallery(final Uri uri) {
	    String[] projection = {  MediaColumns.DATA, MediaColumns.DISPLAY_NAME };
	    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
	    if(cursor != null) {
	        cursor.moveToFirst();

	        int columnIndex = cursor.getColumnIndex(MediaColumns.DISPLAY_NAME);
	        if (columnIndex != -1) {
	        	String filepath = cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));
	        	try {
					selectedImagePath = java.net.URLDecoder.decode(filepath, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            new Thread(new Runnable() {
	                // NEW THREAD BECAUSE NETWORK REQUEST WILL BE MADE THAT WILL BE A LONG PROCESS & BLOCK UI
	                // IF CALLED IN UI THREAD 
	                public void run() {
	                    try {
	                        Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
	                        // THIS IS THE BITMAP IMAGE WE ARE LOOKING FOR.
	                    } catch (Exception ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }).start();
	        }
	    }
	    cursor.close();
	}


	public String getPath(Uri uri) {
	    String[] projection = {  MediaColumns.DATA};
	    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
	    if(cursor != null) {
	        //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
	        //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
	        cursor.moveToFirst();
	        int columnIndex = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	        String filePath = cursor.getString(columnIndex);
	        cursor.close();
	        return filePath;
	    }
	    else 
	        return uri.getPath();               // FOR OI/ASTRO/Dropbox etc
	}
	/*
	 * 
	 * ----------------------------------------------------------------------------**--------------
	 */
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
	
	
}
