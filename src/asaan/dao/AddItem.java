package asaan.dao;

import java.util.List;
import asaan.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ADD_ITEM.
 */
public class AddItem {

    private Long id;
    private int store_id;
    private int price;
    /** Not-null value. */
    private String item_name;
    private int quantity;
    private int item_id;
    /** Not-null value. */
    private String order_for;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient AddItemDao myDao;

    private List<ModItem> mod_items;

    public AddItem() {
    }

    public AddItem(Long id) {
        this.id = id;
    }

    public AddItem(Long id, int store_id, int price, String item_name, int quantity, int item_id, String order_for) {
        this.id = id;
        this.store_id = store_id;
        this.price = price;
        this.item_name = item_name;
        this.quantity = quantity;
        this.item_id = item_id;
        this.order_for = order_for;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAddItemDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /** Not-null value. */
    public String getItem_name() {
        return item_name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    /** Not-null value. */
    public String getOrder_for() {
        return order_for;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setOrder_for(String order_for) {
        this.order_for = order_for;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<ModItem> getMod_items() {
        if (mod_items == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ModItemDao targetDao = daoSession.getModItemDao();
            List<ModItem> mod_itemsNew = targetDao._queryAddItem_Mod_items(id);
            synchronized (this) {
                if(mod_items == null) {
                    mod_items = mod_itemsNew;
                }
            }
        }
        return mod_items;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetMod_items() {
        mod_items = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
