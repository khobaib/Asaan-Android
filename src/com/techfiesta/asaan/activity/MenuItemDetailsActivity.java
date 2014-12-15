package com.techfiesta.asaan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItem;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.lazylist.ImageLoader;
import com.techfiesta.asaan.utility.AmountConversionUtils;

public class MenuItemDetailsActivity extends Activity {

	public static final String BUNDLE_KEY_MENUITEM_POS_ID = "BUNDLE_KEY_MENUITEM_POS_ID";
	public static final String BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION = "BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION = "BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION";
	public static final String BUNDLE_KEY_MENUITEM_PRICE = "BUNDLE_KEY_MENUITEM_PRICE";
	public static final String BUNDLE_KEY_MENUITEM_HAS_MODIFIERS = "BUNDLE_KEY_MENUITEM_HAS_MODIFIERS";
	public static final String BUNDLE_KEY_ORDER_DETAILS_AVAILABLE = "BUNDLE_KEY_ORDER_DETAILS_AVAILABLE";

	TextView shortDescription;
	TextView longDescription;
	TextView itemPrice;
	ImageView itemImage;
	ImageLoader imageLoader;
	Button AddtoOrder;
	StoreMenuItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item_details);

		shortDescription = (TextView) findViewById(R.id.tv_short_desc);
		longDescription = (TextView) findViewById(R.id.tv_long_desc);
		itemPrice = (TextView) findViewById(R.id.tv_item_price);
		itemImage = (ImageView) findViewById(R.id.iv_item_image);
		AddtoOrder = (Button) findViewById(R.id.b_add_to_order);
		imageLoader = new ImageLoader(MenuItemDetailsActivity.this);

		item = MenuActivity.selectedMenuItem;
		if (item != null) {
			shortDescription.setText(item.getShortDescription());
			longDescription.setText(item.getLongDescription());
			itemPrice.setText(AmountConversionUtils.formatCentsToCurrency(item.getPrice()));
			imageLoader.DisplayImage(item.getImageUrl(), itemImage);
		}

		AddtoOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(MenuItemDetailsActivity.this, PlaceOrderActivity.class);
				intent.putExtra(BUNDLE_KEY_MENUITEM_POS_ID, item.getMenuItemPOSId());
				intent.putExtra(BUNDLE_KEY_MENUITEM_SHORT_DESCRIPTION, item.getShortDescription());
				intent.putExtra(BUNDLE_KEY_MENUITEM_LONG_DESCRIPTION, item.getLongDescription());
				intent.putExtra(BUNDLE_KEY_MENUITEM_PRICE, item.getPrice());
				Log.e("PRICE", "" + item.getPrice());
				intent.putExtra(BUNDLE_KEY_MENUITEM_HAS_MODIFIERS, item.getHasModifiers());

				intent.putExtra(BUNDLE_KEY_ORDER_DETAILS_AVAILABLE, false);
				startActivity(intent);

			}
		});

	}

}
