package com.techfiesta.asaan.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.util.Log;
import asaan.dao.AddItem;

import com.parse.ParseUser;

public class HTMLFaxOrder {
	private String beginingString = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
			"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> <html xmlns=\"http://www.w3.org/1999/xhtml\">" +
			" <head>" +
			" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> " +
			"<title>New Savoir Order</title> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /> " +
			"</head> " +
			"<body style=\"margin: 0; padding: 0; \"> <table align=\"center\" border=\"0\" cellpadding=\"10px\" cellspacing=\"0\" width=\"900px\">" +
			" <tr> <td align=\"right\" style=\"padding: 10px 10px 10px 10px;\">" +
			"<img src=\"http://static1.squarespace.com/static/54ce8734e4b08a9c05c30098/t/54e545d5e4b052bf9dad58df/1425522195820/?format=1500w\" /> " +
			"</td> </tr>" +
			" <tr> <td align=\"center\" style=\"font-family: Arial, sans-serif; font-size: 24px; padding: 10px 10px 10px 10px;\"> <b>New Savoir Order</b> </td>" +
			" </tr> <tr> <td style=\"font-family: Arial, sans-serif; font-size: 14px;\"> " +
			"<table border=\"0\" cellpadding=\"10px\" cellspacing=\"0\" width=\"100%%\"> " +
			"<tr> <td width=\"60%%\" valign=\"top\">Name: <b>%s</b></td> " +
			"<td width=\"40%%\" valign=\"top\">To: <b>%s</b></td> </tr> " +
			"<tr> <td width=\"60%%\" valign=\"top\">Phone: <b>%s</b></td>" +
			" <td width=\"40%%\" valign=\"top\">Order #: <b>%s</b></td> </tr> " +
			"<tr> <td width=\"60%%\" valign=\"top\">Email: <b>%s</b></td>" +
			" <td width=\"40%%\" valign=\"top\">Order Type: <b>%s</b></td> </tr>" +
			" <tr> <td width=\"60%%\" valign=\"top\">Address: <b>%s</b></td> " +
			"<td width=\"40%%\" valign=\"top\">Placed: <b>%s</b></td> </tr>" +
			" <tr> <td width=\"60%%\" valign=\"top\"></td> " +
			"<td width=\"40%%\" valign=\"top\">Prepaid: <b>%s</b></td> </tr> " +
			"<tr> <td width=\"60%%\" valign=\"top\">" +
			"</td> <td width=\"40%%\" style=\"font-family: Arial, sans-serif; font-size: 24px;\" valign=\"top\">Expected Time: <b><i><u>%s</u></i></b></td> " +
			"</tr> </table> </td> </tr> " +
			"<tr> <td style=\"font-family: Arial, sans-serif; font-size: 14px;\"> <table border=\"1\" cellpadding=\"10px\" cellspacing=\"0\" width=\"100%%\">" +
			" <tr> <th width=\"35%%\" valign=\"top\">Product</th> <th width=\"20%%\" valign=\"top\">Options</th> <th width=\"20%%\" valign=\"top\">Notes</th> <th width=\"10%%\" valign=\"top\">Quantity</th> <th width=\"15%%\" valign=\"top\">Total</th> </tr>";
	private String table_row="<tr> <td width=\"35%%\" valign=\"top\"><b></b><br>%s</td>" +
								   "<td width=\"20%%\" valign=\"top\">%s</td> " +
								   "<td width=\"20%%\" valign=\"top\">%s</td>" +
								   "<td width=\"10%%\" valign=\"top\" align=\"right\">%s</td> " +
								   "<td width=\"15%%\" valign=\"top\" align=\"right\">%s</td> </tr>";
	
	private String table_row1="<tr> <td width=\"35%%\" valign=\"top\"><br><b>%s</b></td>" +
								   "<td width=\"20%%\" valign=\"top\"></td> " +
								   "<td width=\"20%%\" valign=\"top\"></td>" +
								   "<td width=\"10%%\" valign=\"top\" align=\"right\"></td> " +
								   "<td width=\"15%%\" valign=\"top\" align=\"right\">%s</td> </tr>";

	private String endString="</table> </td> </tr> </table> </body> </html>";
	private int iOrderType = 1;
	private long timeEstimated;
	private void setUpBeginingString()
	{
		ParseUser user = null;
		try {
			user = ParseUser.getCurrentUser();
		}
		catch(Exception e)
		{
			Log.e("Parse", "Fail to get user.");
		}
		
		String userName = "NA";
		String phone="phone";
		String email = "NA";
		String address="none";
		if(user != null)
		{
			userName=user.get("firstName")+" "+ user.get("lastName");
			if(user.get("phone")!=null)
				phone=user.get("phone").toString();
			email = user.getEmail();
			if(user.get("address")!=null)
				address=user.get("address").toString();
		}
		String to= "none";
		if(AsaanUtility.selectedStore!=null)
			to =AsaanUtility.selectedStore.getName();
		String order="ORDER_ID";		
		String orderType;
		if(iOrderType==2)
			orderType = "Carryout Order";
		else
			orderType = "Delivery Order";	
		long mili=System.currentTimeMillis();
		String placed=getFormattedDate(mili)+" at "+getFormattedTime(mili);
		String prepaid="YES";
		String expctedTime= getFormattedTime(timeEstimated);
		//Log.e("STRING",userName+to+phone+order+email+orderType+address+placed+expctedTime);
		
		beginingString=String.format(beginingString,userName,to,phone,order,email,orderType,address,placed,prepaid,expctedTime);
		//Log.e("STRING",beginingString);
	}
	private void createRowsStrings(List<AddItem> orderList)
	{
		int i,size=orderList.size();
		for(i=0;i<size;i++)
		{
			String options="";
			try{
		   if(orderList.get(i).getMod_items()!=null && orderList.get(i).getMod_items().size()>0)
			   options=orderList.get(i).getMod_items().get(0).getName();
			}
			catch(Exception e){}
			String row ="";
			try{
			//row =String.format(table_row,orderList.get(i).getItem_name(),options,orderList.get(i).getNotes(),orderList.get(i).getQuantity(),orderList.get(i).getPrice());
				row = getRowWithNumber(orderList.get(i).getItem_name(), options, orderList.get(i).getNotes(), orderList.get(i).getQuantity(), orderList.get(i).getPrice());
			}
			catch(Exception e){}
			beginingString+=row;
		}
		
	}
	public String getOrderHTML(List<AddItem> orderList, int guestCount, long subTotal, long tax, long serviceCharge, long deliveryCharge,
			long Total, String discountTitle, int discountAmount, String brand, String last4, int iOType, long timeEstimated)
	{
		iOrderType = iOType;
		this.timeEstimated = timeEstimated;
		setUpBeginingString();
		createRowsStrings(orderList);
		
		String midStr = getRow1WithNumber("SubTotal", subTotal);
		midStr += getRow1WithNumber("Tax", tax);
		midStr += getRow1WithNumber("Gratuity", serviceCharge);
		midStr += getRow1WithNumber("Delivery Fee", deliveryCharge);
		midStr += getRow1WithNumber(discountTitle, discountAmount);
		midStr += getRow1WithNumber("Total", Total);
		
		return beginingString+ midStr+ endString;
	}
	
	private String getFormattedTime(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String sTime = sdf.format(new Date(rawTime));
		return sTime;
	}
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}
	
	private String getRowWithNumber(String strDesc, String strOpts, String strNotes, int iQuantity, long iValue)
	{
		String strTemp = "", strValue = "";
		if(strDesc==null || strDesc.length()==0 || iQuantity==0)
			return strTemp;
		
		strValue =String.format("$%.2f", ((double)iValue)/100 );
		String strQty = String.format("%d", iQuantity);
		strTemp = String.format(table_row, strDesc, strOpts, strNotes, strQty, strValue);
		return strTemp;
	}
	
	private String getRow1WithNumber(String strDesc, long iValue)
	{
		String strTemp = "", strValue = "";
		if(strDesc==null || strDesc.length()==0 || iValue==0)
			return strTemp;
		
		strValue =String.format("$%.2f", ((double)iValue)/100 );
		strTemp = String.format(table_row1, strDesc, strValue);
		return strTemp;
	}
}
