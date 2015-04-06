package com.techfiesta.asaan.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import asaan.dao.AddItem;
import asaan.dao.AddItemDao;
import asaan.dao.DaoMaster;
import asaan.dao.DaoMaster.OpenHelper;
import asaan.dao.DaoSession;

import com.techfiesta.asaan.R;
import com.techfiesta.asaan.activity.EditCartActivity;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.Constants;

public class MyCartListAdapter extends ArrayAdapter<AddItem> {

	private Context mContext;
	int fromActivity;

	public MyCartListAdapter(Context context, List<AddItem> objects, int fromActivity) {
		super(context, R.layout.row_order, objects);
		this.mContext = context;
		this.fromActivity = fromActivity;
	}

	private class ViewHolder {
		TextView tvItemName;
		TextView tvItemQty;
		TextView tvItemPrice;
		ImageView ivDeleteItem;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_order, null);
			holder = new ViewHolder();
			holder.tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
			holder.tvItemQty = (TextView) convertView.findViewById(R.id.tv_item_qty);
			holder.tvItemPrice = (TextView) convertView.findViewById(R.id.tv_item_price);

			holder.ivDeleteItem = (ImageView) convertView.findViewById(R.id.iv_delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (fromActivity == Constants.EDIT_CART_ACTIVITY) {
			// Log.e(">>>>", "fromactivity = EDIT_CART_ACTIVITY");
			holder.ivDeleteItem.setVisibility(View.VISIBLE);
		} else {
			// Log.e(">>>>", "fromactivity = MY_CART_ACTIVITY");
			holder.ivDeleteItem.setVisibility(View.GONE);
		}

		AddItem item = getItem(position);

		holder.ivDeleteItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAlert(position);

			}
		});

		holder.tvItemName.setText(item.getItem_name());
		holder.tvItemQty.setText(item.getQuantity() + "");

		String price = "$" + String.format("%.2f", ((double) item.getPrice() / 100));
		holder.tvItemPrice.setText(price);
		// List<ModItem> list = item.getMod_items();
		// for (int i = 0; i < list.size(); i++) {
		// holder.tvItemName.setText(list.get(i).getItem_id() + "  " +
		// list.get(i).getQuantity() + "\n");
		// }
		return convertView;
	}

	private void showAlert(final int position) {
		AlertDialog Alert = new AlertDialog.Builder(mContext).create();
		Alert.setMessage("Are you sure?");

		Alert.setButton(Constants.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				removeItemAndUpdateCartInfo(position);
			}
		});

		Alert.setButton(Constants.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});
		Alert.show();
	}

	private void removeItemAndUpdateCartInfo(int position) {
		AddItem item = getItem(position);
		remove(item);
		notifyDataSetChanged();
		AsaanUtility.setCurrentOrderdStoreId(mContext, -1);
		AsaanUtility.setPartySize(mContext,1);

		OpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "asaan-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		AddItemDao addItemDao = daoSession.getAddItemDao();
		addItemDao.delete(item);

		((EditCartActivity) mContext).updateCartInfo();
	}

}
