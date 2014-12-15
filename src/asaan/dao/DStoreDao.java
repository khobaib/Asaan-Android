package asaan.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import asaan.dao.DStore;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table Stores.
 */
public class DStoreDao extends AbstractDao<DStore, Long> {

	public static final String TABLENAME = "Stores";

	/**
	 * Properties of entity DStore.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Address = new Property(0, String.class, "address", false, "ADDRESS");
		public final static Property BackgroundImageUrl = new Property(1, String.class, "backgroundImageUrl", false,
				"BACKGROUND_IMAGE_URL");
		public final static Property BackgroundThumbnailUrl = new Property(2, String.class, "backgroundThumbnailUrl",
				false, "BACKGROUND_THUMBNAIL_URL");
		public final static Property BeaconId = new Property(3, Long.class, "beaconId", false, "BEACON_ID");
		public final static Property Bssid = new Property(4, String.class, "bssid", false, "BSSID");
		public final static Property City = new Property(5, String.class, "city", false, "CITY");
		public final static Property CreatedDate = new Property(6, Long.class, "createdDate", false, "CREATED_DATE");
		public final static Property DeliveryDistance = new Property(7, Integer.class, "deliveryDistance", false,
				"DELIVERY_DISTANCE");
		public final static Property Description = new Property(8, String.class, "description", false, "DESCRIPTION");
		public final static Property FbUrl = new Property(9, String.class, "fbUrl", false, "FB_URL");
		public final static Property GplusUrl = new Property(10, String.class, "gplusUrl", false, "GPLUS_URL");
		public final static Property Hours = new Property(11, String.class, "hours", false, "HOURS");
		public final static Property Id = new Property(12, long.class, "id", true, "_id");
		public final static Property IsActive = new Property(13, Boolean.class, "isActive", false, "IS_ACTIVE");
		public final static Property Lat = new Property(14, Double.class, "lat", false, "LAT");
		public final static Property Lng = new Property(15, Double.class, "lng", false, "LNG");
		public final static Property ModifiedDate = new Property(16, Long.class, "modifiedDate", false, "MODIFIED_DATE");
		public final static Property Name = new Property(17, String.class, "name", false, "NAME");
		public final static Property Phone = new Property(18, String.class, "phone", false, "PHONE");
		public final static Property PriceRange = new Property(19, Integer.class, "priceRange", false, "PRICE_RANGE");
		public final static Property ProvidesCarryout = new Property(20, Boolean.class, "providesCarryout", false,
				"PROVIDES_CARRYOUT");
		public final static Property ProvidesDelivery = new Property(21, Boolean.class, "providesDelivery", false,
				"PROVIDES_DELIVERY");
		public final static Property RewardsDescription = new Property(22, String.class, "rewardsDescription", false,
				"REWARDS_DESCRIPTION");
		public final static Property RewardsRate = new Property(23, Integer.class, "rewardsRate", false, "REWARDS_RATE");
		public final static Property Ssid = new Property(24, String.class, "ssid", false, "SSID");
		public final static Property State = new Property(25, String.class, "state", false, "STATE");
		public final static Property SubType = new Property(26, String.class, "subType", false, "SUB_TYPE");
		public final static Property TwitterUrl = new Property(27, String.class, "twitterUrl", false, "TWITTER_URL");
		public final static Property Type = new Property(28, String.class, "type", false, "TYPE");
		public final static Property WebSiteUrl = new Property(29, String.class, "webSiteUrl", false, "WEB_SITE_URL");
		public final static Property Zip = new Property(30, String.class, "zip", false, "ZIP");
	};

	private DaoSession daoSession;

	public DStoreDao(DaoConfig config) {
		super(config);
	}

	public DStoreDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
		this.daoSession = daoSession;
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "'Stores' (" + //
				"'ADDRESS' TEXT," + // 0: address
				"'BACKGROUND_IMAGE_URL' TEXT," + // 1: backgroundImageUrl
				"'BACKGROUND_THUMBNAIL_URL' TEXT," + // 2:
														// backgroundThumbnailUrl
				"'BEACON_ID' INTEGER," + // 3: beaconId
				"'BSSID' TEXT," + // 4: bssid
				"'CITY' TEXT," + // 5: city
				"'CREATED_DATE' INTEGER," + // 6: createdDate
				"'DELIVERY_DISTANCE' INTEGER," + // 7: deliveryDistance
				"'DESCRIPTION' TEXT," + // 8: description
				"'FB_URL' TEXT," + // 9: fbUrl
				"'GPLUS_URL' TEXT," + // 10: gplusUrl
				"'HOURS' TEXT," + // 11: hours
				"'_id' INTEGER PRIMARY KEY NOT NULL ," + // 12: id
				"'IS_ACTIVE' INTEGER," + // 13: isActive
				"'LAT' REAL," + // 14: lat
				"'LNG' REAL," + // 15: lng
				"'MODIFIED_DATE' INTEGER," + // 16: modifiedDate
				"'NAME' TEXT," + // 17: name
				"'PHONE' TEXT," + // 18: phone
				"'PRICE_RANGE' INTEGER," + // 19: priceRange
				"'PROVIDES_CARRYOUT' INTEGER," + // 20: providesCarryout
				"'PROVIDES_DELIVERY' INTEGER," + // 21: providesDelivery
				"'REWARDS_DESCRIPTION' TEXT," + // 22: rewardsDescription
				"'REWARDS_RATE' INTEGER," + // 23: rewardsRate
				"'SSID' TEXT," + // 24: ssid
				"'STATE' TEXT," + // 25: state
				"'SUB_TYPE' TEXT," + // 26: subType
				"'TWITTER_URL' TEXT," + // 27: twitterUrl
				"'TYPE' TEXT," + // 28: type
				"'WEB_SITE_URL' TEXT," + // 29: webSiteUrl
				"'ZIP' TEXT);"); // 30: zip
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'Stores'";
		db.execSQL(sql);
	}

	/** @inheritdoc */
	@Override
	protected void bindValues(SQLiteStatement stmt, DStore entity) {
		stmt.clearBindings();

		String address = entity.getAddress();
		if (address != null) {
			stmt.bindString(1, address);
		}

		String backgroundImageUrl = entity.getBackgroundImageUrl();
		if (backgroundImageUrl != null) {
			stmt.bindString(2, backgroundImageUrl);
		}

		String backgroundThumbnailUrl = entity.getBackgroundThumbnailUrl();
		if (backgroundThumbnailUrl != null) {
			stmt.bindString(3, backgroundThumbnailUrl);
		}

		Long beaconId = entity.getBeaconId();
		if (beaconId != null) {
			stmt.bindLong(4, beaconId);
		}

		String bssid = entity.getBssid();
		if (bssid != null) {
			stmt.bindString(5, bssid);
		}

		String city = entity.getCity();
		if (city != null) {
			stmt.bindString(6, city);
		}

		Long createdDate = entity.getCreatedDate();
		if (createdDate != null) {
			stmt.bindLong(7, createdDate);
		}

		Integer deliveryDistance = entity.getDeliveryDistance();
		if (deliveryDistance != null) {
			stmt.bindLong(8, deliveryDistance);
		}

		String description = entity.getDescription();
		if (description != null) {
			stmt.bindString(9, description);
		}

		String fbUrl = entity.getFbUrl();
		if (fbUrl != null) {
			stmt.bindString(10, fbUrl);
		}

		String gplusUrl = entity.getGplusUrl();
		if (gplusUrl != null) {
			stmt.bindString(11, gplusUrl);
		}

		String hours = entity.getHours();
		if (hours != null) {
			stmt.bindString(12, hours);
		}
		stmt.bindLong(13, entity.getId());

		Boolean isActive = entity.getIsActive();
		if (isActive != null) {
			stmt.bindLong(14, isActive ? 1l : 0l);
		}

		Double lat = entity.getLat();
		if (lat != null) {
			stmt.bindDouble(15, lat);
		}

		Double lng = entity.getLng();
		if (lng != null) {
			stmt.bindDouble(16, lng);
		}

		Long modifiedDate = entity.getModifiedDate();
		if (modifiedDate != null) {
			stmt.bindLong(17, modifiedDate);
		}

		String name = entity.getName();
		if (name != null) {
			stmt.bindString(18, name);
		}

		String phone = entity.getPhone();
		if (phone != null) {
			stmt.bindString(19, phone);
		}

		Integer priceRange = entity.getPriceRange();
		if (priceRange != null) {
			stmt.bindLong(20, priceRange);
		}

		Boolean providesCarryout = entity.getProvidesCarryout();
		if (providesCarryout != null) {
			stmt.bindLong(21, providesCarryout ? 1l : 0l);
		}

		Boolean providesDelivery = entity.getProvidesDelivery();
		if (providesDelivery != null) {
			stmt.bindLong(22, providesDelivery ? 1l : 0l);
		}

		String rewardsDescription = entity.getRewardsDescription();
		if (rewardsDescription != null) {
			stmt.bindString(23, rewardsDescription);
		}

		Integer rewardsRate = entity.getRewardsRate();
		if (rewardsRate != null) {
			stmt.bindLong(24, rewardsRate);
		}

		String ssid = entity.getSsid();
		if (ssid != null) {
			stmt.bindString(25, ssid);
		}

		String state = entity.getState();
		if (state != null) {
			stmt.bindString(26, state);
		}

		String subType = entity.getSubType();
		if (subType != null) {
			stmt.bindString(27, subType);
		}

		String twitterUrl = entity.getTwitterUrl();
		if (twitterUrl != null) {
			stmt.bindString(28, twitterUrl);
		}

		String type = entity.getType();
		if (type != null) {
			stmt.bindString(29, type);
		}

		String webSiteUrl = entity.getWebSiteUrl();
		if (webSiteUrl != null) {
			stmt.bindString(30, webSiteUrl);
		}

		String zip = entity.getZip();
		if (zip != null) {
			stmt.bindString(31, zip);
		}
	}

	@Override
	protected void attachEntity(DStore entity) {
		super.attachEntity(entity);
		entity.__setDaoSession(daoSession);
	}

	/** @inheritdoc */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.getLong(offset + 12);
	}

	/** @inheritdoc */
	@Override
	public DStore readEntity(Cursor cursor, int offset) {
		DStore entity = new DStore(
		//
				cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // address
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // backgroundImageUrl
				cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // backgroundThumbnailUrl
				cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // beaconId
				cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // bssid
				cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // city
				cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // createdDate
				cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // deliveryDistance
				cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // description
				cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // fbUrl
				cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // gplusUrl
				cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // hours
				cursor.getLong(offset + 12), // id
				cursor.isNull(offset + 13) ? null : cursor.getShort(offset + 13) != 0, // isActive
				cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14), // lat
				cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15), // lng
				cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16), // modifiedDate
				cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // name
				cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // phone
				cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19), // priceRange
				cursor.isNull(offset + 20) ? null : cursor.getShort(offset + 20) != 0, // providesCarryout
				cursor.isNull(offset + 21) ? null : cursor.getShort(offset + 21) != 0, // providesDelivery
				cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // rewardsDescription
				cursor.isNull(offset + 23) ? null : cursor.getInt(offset + 23), // rewardsRate
				cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // ssid
				cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // state
				cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // subType
				cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // twitterUrl
				cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // type
				cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // webSiteUrl
				cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30) // zip
		);
		return entity;
	}

	/** @inheritdoc */
	@Override
	public void readEntity(Cursor cursor, DStore entity, int offset) {
		entity.setAddress(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
		entity.setBackgroundImageUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
		entity.setBackgroundThumbnailUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
		entity.setBeaconId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
		entity.setBssid(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
		entity.setCity(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
		entity.setCreatedDate(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
		entity.setDeliveryDistance(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
		entity.setDescription(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
		entity.setFbUrl(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
		entity.setGplusUrl(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
		entity.setHours(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
		entity.setId(cursor.getLong(offset + 12));
		entity.setIsActive(cursor.isNull(offset + 13) ? null : cursor.getShort(offset + 13) != 0);
		entity.setLat(cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14));
		entity.setLng(cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15));
		entity.setModifiedDate(cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16));
		entity.setName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
		entity.setPhone(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
		entity.setPriceRange(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
		entity.setProvidesCarryout(cursor.isNull(offset + 20) ? null : cursor.getShort(offset + 20) != 0);
		entity.setProvidesDelivery(cursor.isNull(offset + 21) ? null : cursor.getShort(offset + 21) != 0);
		entity.setRewardsDescription(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
		entity.setRewardsRate(cursor.isNull(offset + 23) ? null : cursor.getInt(offset + 23));
		entity.setSsid(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
		entity.setState(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
		entity.setSubType(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
		entity.setTwitterUrl(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
		entity.setType(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
		entity.setWebSiteUrl(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
		entity.setZip(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
	}

	/** @inheritdoc */
	@Override
	protected Long updateKeyAfterInsert(DStore entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/** @inheritdoc */
	@Override
	public Long getKey(DStore entity) {
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
