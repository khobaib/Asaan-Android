package com.techfiesta.asaan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.techfiesta.asaan.R;

public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

	public CustomAdapter(Context context) {
		// Use the QueryFactory to construct a PQA that will only show
		
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {
				ParseQuery query = new ParseQuery("Store");
				return query;
			}
		});
	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.restaurant_item_row, null);
		}

		super.getItemView(object, v, parent);

		// Add and download the image
	ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.ivRestaurantImageRD);
		/*ParseFile imageFile = object.getParseFile("image");
		if (imageFile != null) {
			todoImage.setParseFile(imageFile);
			todoImage.loadInBackground();
		}*/
		
		loadImage(object, todoImage);
		Log.e("Inside Adapter", "name "+object.getString("name"));

		// Add the title view
		TextView titleTextView = (TextView) v.findViewById(R.id.tvNameRestaurantItem);
		titleTextView.setText(object.getString("name"));

		TextView resTypeTextView = (TextView) v.findViewById(R.id.tvTypeRestaurantItem);
		resTypeTextView.setText(object.getString("type"));
		return v;
	}
	
	private void loadImage(ParseObject pObj,final ParseImageView todoImage){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("StoreImages");
        
		  // Restrict to cases where the author is the current user.
		  query.whereEqualTo("store", pObj.getObjectId());
		         
		  // Run the query 
		  query.getFirstInBackground(new GetCallback<ParseObject>() {
			
		@Override
		public void done(ParseObject storeImageObject, ParseException e) {
			if(e==null){
				
				ParseFile imageFile = storeImageObject.getParseFile("image");
				if (imageFile != null) {
					todoImage.setParseFile(imageFile);
					todoImage.loadInBackground();
				}
			}else{
				Log.d(">>>>", "couldn't retrive image data");
			}
		}

			
		});
		  
	
	}
}
