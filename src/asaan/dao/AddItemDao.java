package asaan.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import asaan.dao.AddItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table ADD_ITEM.
 */
public class AddItemDao extends AbstractDao<AddItem, Long> {

	public static final String TABLENAME = "ADD_ITEM";

	/**
	 * Properties of entity AddItem.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id", true, "_id");
		public final static Property Store_id = new Property(1, int.class, "store_id", false, "STORE_ID");
		public final static Property Price = new Property(2, int.class, "price", false, "PRICE");
		public final static Property Item_name = new Property(3, String.class, "item_name", false, "ITEM_NAME");
		public final static Property Quantity = new Property(4, int.class, "quantity", false, "QUANTITY");
		public final static Property Item_id = new Property(5, int.class, "item_id", false, "ITEM_ID");
		public final static Property Order_for = new Property(6, String.class, "order_for", false, "ORDER_FOR");
	};

	private DaoSession daoSession;

	public AddItemDao(DaoConfig config) {
		super(config);
	}

	public AddItemDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
		this.daoSession = daoSession;
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'ADD_ITEM' (" + //
				"'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
				"'STORE_ID' INTEGER NOT NULL ," + // 1: store_id
				"'PRICE' INTEGER NOT NULL ," + // 2: price
				"'ITEM_NAME' TEXT NOT NULL ," + // 3: item_name
				"'QUANTITY' INTEGER NOT NULL ," + // 4: quantity
				"'ITEM_ID' INTEGER NOT NULL ," + // 5: item_id
				"'ORDER_FOR' TEXT NOT NULL );"); // 6: order_for
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ADD_ITEM'";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, AddItem entity) {
		stmt.clearBindings();

		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}
		stmt.bindLong(2, entity.getStore_id());
		stmt.bindLong(3, entity.getPrice());
		stmt.bindString(4, entity.getItem_name());
		stmt.bindLong(5, entity.getQuantity());
		stmt.bindLong(6, entity.getItem_id());
		stmt.bindString(7, entity.getOrder_for());
	}

	@Override
	protected void attachEntity(AddItem entity) {
		super.attachEntity(entity);
		entity.__setDaoSession(daoSession);
	}

	/** @inheritdoc */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	/** @inheritdoc */
	@Override
	public AddItem readEntity(Cursor cursor, int offset) {
		AddItem entity = new AddItem( //
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.getInt(offset + 1), // store_id
				cursor.getInt(offset + 2), // price
				cursor.getString(offset + 3), // item_name
				cursor.getInt(offset + 4), // quantity
				cursor.getInt(offset + 5), // item_id
				cursor.getString(offset + 6) // order_for
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, AddItem entity, int offset) {
		entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
		entity.setStore_id(cursor.getInt(offset + 1));
		entity.setPrice(cursor.getInt(offset + 2));
		entity.setItem_name(cursor.getString(offset + 3));
		entity.setQuantity(cursor.getInt(offset + 4));
		entity.setItem_id(cursor.getInt(offset + 5));
		entity.setOrder_for(cursor.getString(offset + 6));
	}

	/** @inheritdoc */
	@Override
	protected Long updateKeyAfterInsert(AddItem entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/** @inheritdoc */
	@Override
	public Long getKey(AddItem entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	/** @inheritdoc */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}
