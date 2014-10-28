package com.techfiesta.asaan.utility;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MenuHierarchy
{
	@Getter
	@Setter
    protected long asaanStoreId;

	@Getter
	@Setter
    protected long posId;
	
	@Getter
	@Setter
    protected String posName;
	
	@Getter
	@Setter
    protected String dob;
	
	@Getter
	@Setter
    protected String address1;
	
	@Getter
	@Setter
    protected String address2;

	@Getter
	@Setter
    protected long asaanTenderId;

	@Getter
	@Setter
    protected long orderModeDelivery;

	@Getter
	@Setter
    protected long orderModeCarryout;

	@Getter
	@Setter
    protected long orderModeEatin;
    
    List<Menu> menus;
    public List<Menu> getMenus()
    {
    	if (menus == null)
    		menus = new ArrayList<Menu>();
     		
    	return menus;
    }
    
    List<SubMenu> subMenus;
    public List<SubMenu> getSubMenus()
    {
    	if (subMenus == null)
    		subMenus = new ArrayList<SubMenu>();
     		
    	return subMenus;
    }
    
    List<MenuItem> items;
    public List<MenuItem> getItems()
    {
    	if (items == null)
    		items = new ArrayList<MenuItem>();
     		
    	return items;
    }
    
    List<ModifierGroup> modifierGroups;
    public List<ModifierGroup> getModifierGroups()
    {
    	if (modifierGroups == null)
    		modifierGroups = new ArrayList<ModifierGroup>();
     		
    	return modifierGroups;
    }
	
	List<MenuTax> taxes;
    public List<MenuTax> getTaxes()
    {
    	if (taxes == null)
    		taxes = new ArrayList<MenuTax>();
     		
    	return taxes;
    }
   
    public static class Menu
    {
    	@Getter
    	@Setter
    	protected long id;
    	
    	@Getter
    	@Setter
    	protected String description;
    	
    	List<Long> subMenuIds;
        public List<Long> getSubMenuIds()
        {
        	if (subMenuIds == null)
        		subMenuIds = new ArrayList<Long>();
         		
        	return subMenuIds;
        }
    	
    	List<MenuTime> menuTimes;
        public List<MenuTime> getMenuTimes()
        {
        	if (menuTimes == null)
        		menuTimes = new ArrayList<MenuTime>();
         		
        	return menuTimes;
        }
    }
    
    public static class SubMenu
    {
    	@Getter
    	@Setter
        protected long id;

    	@Getter
    	@Setter
        protected String description;

        protected List<SubMenu_Item_Price> menuItemIds;
        public List<SubMenu_Item_Price> getMenuItemIds()
        {
        	if (menuItemIds == null)
        		menuItemIds = new ArrayList<SubMenu_Item_Price>();
         		
        	return menuItemIds;
        }
    	
    	public static class SubMenu_Item_Price
    	{
        	@Getter
        	@Setter
        	protected long menuItemId;
        	@Getter
        	@Setter
        	protected long menuItemPrice;
    	}
    }
    
    public static class MenuItem
    {
    	@Getter
    	@Setter
        protected long id;
    	@Getter
    	@Setter
        protected String shortdesc;
    	@Getter
    	@Setter
        protected String longdesc;
    	@Getter
    	@Setter
        protected long price;
    	@Getter
    	@Setter
        protected long taxId;
        protected List<Long> modifierGroupIds;
        public List<Long> getModifierGroupIds()
        {
        	if (modifierGroupIds == null)
        		modifierGroupIds = new ArrayList<Long>();
         		
        	return modifierGroupIds;
        }
    }
    
    public static class ModifierGroup
    {
    	@Getter
    	@Setter
        protected long id;
    	@Getter
    	@Setter
        protected String shortdesc;
    	@Getter
    	@Setter
        protected String longdesc;
    	@Getter
    	@Setter
        protected long minimum;
    	@Getter
    	@Setter
        protected long maximum;
        protected List<Modifier> modifiers;
        public List<Modifier> getModifiers()
        {
        	if (modifiers == null)
        		modifiers = new ArrayList<Modifier>();
         		
        	return modifiers;
        }
    }
    
    public static class Modifier
    {
    	@Getter
    	@Setter
        protected long id;
    	@Getter
    	@Setter
        protected String shortdesc;
    	@Getter
    	@Setter
        protected String longdesc;
    	@Getter
    	@Setter
        protected long weighting;
    	@Getter
    	@Setter
        protected long price;
    }
    
    public static class MenuTime
    {
    	@Getter
    	@Setter
        protected String period;
    	@Getter
    	@Setter
        protected long starttime;
    	@Getter
    	@Setter
        protected long stoptime;
    }
    
    public static class MenuTax
    {
    	@Getter
    	@Setter
        protected long id;
    	@Getter
    	@Setter
        protected String desc;
    	@Getter
    	@Setter
        protected long taxrate;
    	@Getter
    	@Setter
        protected long exclusive;
    	@Getter
    	@Setter
        protected long minimumAmt;
    	@Getter
    	@Setter
        protected long inclusive;
    }
}
