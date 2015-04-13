package com.techfiesta.asaan.utility;

import java.util.ArrayList;
import java.util.List;

import asaan.dao.AddItem;

public class XMLPosOrder {
	
	private String begingString="<POSRESPONSE> <GETCHECKDETAILS> <CHECK GUESTCOUNT=\"%s\" TABLENUMBER=\"0\" SUBTOTAL=\"%s\" TAX=\"%s\" SERVICECHARGES=\"%s\" COMPLETETOTAL=\"%s\" DELIVERY=\"%s\"> <ENTRIES>";
	private String tableRowXml="<ENTRY ID=\"%d\" QUANTITY=\"%s\" PRICE=\"%s\" DISP_NAME=\"%s\" ITEMID=\"%ld\" OPTION=\"%s\" />";
	private String disCount="<DISCOUNTS DESC=\"%s\" AMOUNT=\"%s\"/>";
	private String payment="<PAYMENTS BRAND=\"--CARDTYPE--\" LASTFOUR=\"--CARDLASTFOUR--\" />";
	private String end="</CHECK></GETCHECKDETAILS></POSRESPONSE>";
	
	private String createBeginingString(int guestCount,long subTotal,long tax,long serviceCharge,long deliveryCharge,long Total)
	{
		return String.format(begingString,guestCount,subTotal,tax,serviceCharge,deliveryCharge,Total);
	
	}
	private String createTableRows(List<AddItem> orderlist)
	{
		String tableRows="";
		int size=orderlist.size();
		for(int i=0;i<size;i++)
		{
			String modItem="";
			if(orderlist.get(i).getMod_items()!=null && orderlist.get(i).getMod_items().size()>0)
			{
				modItem=orderlist.get(i).getMod_items().get(0).getName();
			}
			
			String tempStr=String.format(tableRowXml,i+1,orderlist.get(i).getQuantity(),orderlist.get(i).getPrice(),orderlist.get(i).getItem_name(),orderlist.get(i).getItem_id(),modItem );
			tableRows+=tempStr;
		}
		return tableRows;
	}
	private String createDiscount(String discountTitle,int discountAmount)
	{
		
		if(discountAmount>0)
			return String.format(disCount,discountTitle,discountAmount);
		else
			return "<DISCOUNTS />";
	}
	private String createPayment(String brand,String last4)
	{
		return String.format(payment,brand,last4);
		
	}

	public String getXMlPOSOrder(int guestCount, long subTotal, long tax, long serviceCharge, long deliveryCharge,
			long Total, List<AddItem> orderList, String discountTitle, int discountAmount, String brand, String last4) {
		return createBeginingString(guestCount, subTotal, tax, serviceCharge, deliveryCharge, Total)
				+ createTableRows(orderList) + createDiscount(discountTitle, discountAmount)
				+ createPayment(brand, last4) + end;
	}

}
