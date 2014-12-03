package com.techfiesta.asaan.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.model.UserPicture;
import com.techfiesta.asaan.utility.AsaanUtility;

public class ProfileActivity extends Activity {
	private ImageView ProfilePicture;
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

	private String selectedImagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();

		setContentView(R.layout.activity_profile);
		FirstName = (EditText) findViewById(R.id.et_first_name);
		LastName = (EditText) findViewById(R.id.et_last_name);
		Phone = (EditText) findViewById(R.id.et_phone);
		ProfilePicture = (ImageView) findViewById(R.id.iv_propic_add);
		btnSave = (Button) findViewById(R.id.btn_save);
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
		ProfilePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

			}
		});

	}

	private void saveUserData() {
		ParseUser user = ParseUser.getCurrentUser();
		user.put("firstName", firstName);
		user.put("lastName", lastName);
		user.put("phone", phoneNumber);
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
		user.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					AsaanUtility.simpleAlert(ProfileActivity.this, "Profile Updated");
					Intent intent = new Intent(ProfileActivity.this, PaymentInfoActivity.class);
					startActivity(intent);
				} else {
					Log.e("error", "updating user failed" + e.toString());
					AsaanUtility.simpleAlert(ProfileActivity.this, "Error In Updating profile");
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				userPic = new UserPicture(selectedImageUri, getContentResolver());
				try {
					picture = userPic.getBitmap();
					if (picture != null)
						selectedImagePath = userPic.getPath();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ProfilePicture.setImageBitmap(picture);
				System.out.println(selectedImagePath);
				// profilePhotoUrl = selectedImagePath;

			}
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

}
