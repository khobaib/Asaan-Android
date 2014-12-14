package asaan.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import asaan.dao.Trophies;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table TROPHIES.
 */
public class TrophiesDao extends AbstractDao<Trophies, Void> {

	public static final String TABLENAME = "TROPHIES";

	/**
	 * Properties of entity Trophies.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Name = new Property(0, String.class,
				"name", false, "NAME");
		public final static Property Store_id = new Property(1, Long.class,
				"store_id", false, "STORE_ID");
		public final static Property Id = new Property(2, long.class, "id",
				true, "_id");
	};

	private Query<Trophies> dStore_TrophiesListQuery;

	public TrophiesDao(DaoConfig config) {
		super(config);
	}

	public TrophiesDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'TROPHIES' (" + //
				"'NAME' TEXT NOT NULL ," + // 0: name
				"'STORE_ID' INTEGER," + // 1: store_id
				"'_id' INTEGER PRIMARY KEY NOT NULL );"); // 2: id
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "")
				+ "'TROPHIES'";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, Trophies entity) {
		stmt.clearBindings();
		stmt.bindString(1, entity.getName());

		Long store_id = entity.getStore_id();
		if (store_id != null) {
			stmt.bindLong(2, store_id);
		}
	}

	/** @inheritdoc */
	@Override
	public Void readKey(Cursor cursor, int offset) {
		return null;
	}

	/** @inheritdoc */
	@Override
	public Trophies readEntity(Cursor cursor, int offset) {
		Trophies entity = new Trophies( //
				cursor.getString(offset + 0), // name
				cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1) // store_id
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, Trophies entity, int offset) {
		entity.setName(cursor.getString(offset + 0));
		entity.setStore_id(cursor.isNull(offset + 1) ? null : cursor
				.getLong(offset + 1));
	}

	/** @inheritdoc */
	@Override
	protected Void updateKeyAfterInsert(Trophies entity, long rowId) {
		// Unsupported or missing PK type
		return null;
	}

	/** @inheritdoc */
	@Override
	public Void getKey(Trophies entity) {
		return null;
	}

	/** @inheritdoc */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

	/**
	 * Internal query to resolve the "trophiesList" to-many relationship of
	 * DStore.
	 */
	public List<Trophies> _queryDStore_TrophiesList(long id) {
		synchronized (this) {
			if (dStore_TrophiesListQuery == null) {
				QueryBuilder<Trophies> queryBuilder = queryBuilder();
				queryBuilder.where(Properties.Id.eq(null));
				dStore_TrophiesListQuery = queryBuilder.build();
			}
		}
		Query<Trophies> query = dStore_TrophiesListQuery.forCurrentThread();
		query.setParameter(0, id);
		return query.list();
	}

}
