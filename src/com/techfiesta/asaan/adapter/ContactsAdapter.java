package com.techfiesta.asaan.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.ChatRoom;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.OrderReview;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.model.Contact;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact>{
	private Context mContext;

	public ContactsAdapter(Context context, List<Contact> list) {
		super(context, R.layout.row_contacts, list);
		this.mContext = context;

	}

	private class ViewHolder {
		ImageView ivImage;
		TextView tvName;
		TextView tvPhone;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		//if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_contacts, null);

			holder = new ViewHolder();
			holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_contact);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
			convertView.setTag(holder);
		//}
		//else holder = (ViewHolder) convertView.getTag();
		Contact contact=getItem(position);
		Bitmap bmp=getContactPhoto(contact.getId());
		if(bmp!=null)
		{
			holder.ivImage.setImageBitmap(bmp);
		}
		else
			holder.ivImage.setBackgroundResource(R.drawable.recommend_friend);
		holder.tvName.setText(contact.getName());
		holder.tvPhone.setText(contact.getPhone());
			
		return convertView;
	}
	public Bitmap getContactPhoto(long contactId) {
		if (contactId == -1)
			return null;
		Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
				contactId);
		Uri photoUri = Uri.withAppendedPath(contactUri,
				Contacts.Photo.CONTENT_DIRECTORY);
		Cursor cursor = mContext.getContentResolver().query(photoUri,
				new String[] { Contacts.Photo.PHOTO }, null, null, null);
		if (cursor == null) {
			return null;
		}
		try {
			Bitmap thumbnail = null;
			if (cursor.moveToFirst()) {
				byte[] data = cursor.getBlob(0);
				if (data != null) {
					thumbnail = BitmapFactory.decodeByteArray(data, 0,
							data.length);
				}
			}
			return thumbnail;
		} finally {
			cursor.close();
		}
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}

}
