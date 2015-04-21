package com.techfiesta.asaan.utility;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;
import asaan.dao.AddItem;

public class XMLPosOrder {
	
	private String begingString="<POSRESPONSE> <GETCHECKDETAILS> <CHECK GUESTCOUNT=\"%s\" TABLENUMBER=\"0\" SUBTOTAL=\"%s\" TAX=\"%s\" SERVICECHARGES=\"%s\" COMPLETETOTAL=\"%s\" DELIVERY=\"%s\"> <ENTRIES>";
	private String tableRowXml="<ENTRY ID=\"%d\" QUANTITY=\"%s\" PRICE=\"%s\" DISP_NAME=\"%s\" ITEMID=\"%ld\" OPTION=\"%s\" />";
	private String disCount="<DISCOUNTS DESC=\"%s\" AMOUNT=\"%s\"/>";
	private String payment="<PAYMENTS BRAND=\"--CARDTYPE--\" LASTFOUR=\"--CARDLASTFOUR--\" />";
	private String end="</CHECK></GETCHECKDETAILS></POSRESPONSE>";
	
	private String strXmlFaxOrder ="<POSRESPONSE><GETCHECKDETAILS INTCHECKID=\"0\"> <CHECK ID=\"0\" TABLENUMBER=\"1\" SUBTOTAL=\"0\" TAX=\"0\" SERVICECHARGES=\"0\" DELIVERY=\"0\" COMPLETETOTAL=\"0\" GUESTCOUNT=\"1\" EMPLOYEE=\"0\" EMPLOYEECHECKNAME=\"None\">" +
					"<ENTRIES></ENTRIES><DISCOUNTS DISP_NAME=\"Discount\" AMOUNT=\"0\"/><PAYMENTS></PAYMENTS></CHECK></GETCHECKDETAILS></POSRESPONSE>";

	 
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
			try{
			if(orderlist.get(i).getMod_items()!=null && orderlist.get(i).getMod_items().size()>0)
			{
				modItem=orderlist.get(i).getMod_items().get(0).getName();
			}}
			catch(Exception e){}
			
			String tempStr="";
			try{
			tempStr=String.format(tableRowXml,i+1,orderlist.get(i).getQuantity(),orderlist.get(i).getPrice(),orderlist.get(i).getItem_name(),orderlist.get(i).getItem_id(),modItem );
			}
			catch(Exception e){};
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
	
	public String getXMLFaxOrder(int guestCount, long subTotal, long tax, long serviceCharge, long deliveryCharge,
			long Total, List<AddItem> orderlist, String discountTitle, int discountAmount, String brand, String last4){
		
		String strResult = "";
		
		try {
			String strTemp;
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			InputSource is=new InputSource(new StringReader(strXmlFaxOrder));
			Document dom=builder.parse(is);
			  
			NodeList nlist = dom.getElementsByTagName("CHECK");
			Node node = nlist.item(0);
			NamedNodeMap map = node.getAttributes();
			
			//1) guest count
			Node n1 = map.getNamedItem("GUESTCOUNT");
			strTemp = Long.toString(guestCount);
			n1.setNodeValue(strTemp);
			//2) sub total
			n1 = map.getNamedItem("SUBTOTAL");
			strTemp = Double.toString((double)subTotal/100);
			n1.setNodeValue(strTemp);
			//3) tax
			n1 = map.getNamedItem("TAX");
			strTemp = Double.toString((double)tax/100);
			n1.setNodeValue(strTemp);
			//4) service charge
			n1 = map.getNamedItem("SERVICECHARGES");
			strTemp = Double.toString((double)serviceCharge/100);
			n1.setNodeValue(strTemp);
			//5) service charge
			n1 = map.getNamedItem("DELIVERY");
			strTemp = Double.toString((double)deliveryCharge/100);
			n1.setNodeValue(strTemp);
			//6) Total
			n1 = map.getNamedItem("COMPLETETOTAL");
			strTemp = Double.toString((double)Total/100);
			n1.setNodeValue(strTemp);
			
			//now set up discounts
			nlist = dom.getElementsByTagName("DISCOUNTS");
			node = nlist.item(0);
			map = node.getAttributes();
			//1) discount title
			if(discountTitle.length()>0)
			{
				n1 = map.getNamedItem("DISP_NAME");
				n1.setNodeValue(discountTitle);
			}
			//2) discount amount
			n1 = map.getNamedItem("AMOUNT");
			strTemp = Double.toString((double)discountAmount/100);
			n1.setNodeValue(strTemp);
			
			//now set up discounts
			nlist = dom.getElementsByTagName("ENTRIES");
			node = nlist.item(0);
			Attr att;
			
			int size=orderlist.size();
			for(int i=0;i<size;i++)
			{
				Node newNode;
				Element el = dom.createElement("ENTRY");
				newNode = node.appendChild(el);
				
				//create ENTRY attributEs
				// <ENTRY ID="002130006" ENTRYTYPE="0" ORDERMODE="0" QUANTITY="1" PRICE="19.95" DISP_NAME="N.Y. Strip" ITEMID="7001" />
				//1) ID
				att = dom.createAttribute("ID");
				att.setValue("0");
				newNode.getAttributes().setNamedItem(att);
				//2) QUANTITY
				att = dom.createAttribute("QUANTITY");
				att.setValue(Long.toString(orderlist.get(i).getQuantity()));
				newNode.getAttributes().setNamedItem(att);
				//3) QUANTITY
				att = dom.createAttribute("PRICE");
				att.setValue(Double.toString((double)(orderlist.get(i).getPrice())/100));
				newNode.getAttributes().setNamedItem(att);
				//4) QUANTITY
				att = dom.createAttribute("DISP_NAME");
				att.setValue(orderlist.get(i).getItem_name());
				newNode.getAttributes().setNamedItem(att);
				//5) QUANTITY
				att = dom.createAttribute("ITEMID");
				att.setValue(Long.toString(orderlist.get(i).getItem_id()));
				newNode.getAttributes().setNamedItem(att);
				//6) ENTRYTYPE
				att = dom.createAttribute("ENTRYTYPE");
				att.setValue("0");
				newNode.getAttributes().setNamedItem(att);
				//7) ORDERMODE
				att = dom.createAttribute("ORDERMODE");
				att.setValue("0");
				newNode.getAttributes().setNamedItem(att);
				//8) PARENTENTRY
				att = dom.createAttribute("PARENTENTRY");
				att.setValue("002130001");
				newNode.getAttributes().setNamedItem(att);
				//9) OPTION
				String modItem = "";
				try{
				if(orderlist.get(i).getMod_items()!=null && orderlist.get(i).getMod_items().size()>0)
				{
					modItem=orderlist.get(i).getMod_items().get(0).getName();
				}}
				catch(Exception e){}
				if(modItem.length()>0)
				{
					att = dom.createAttribute("OPTION");
					att.setValue(modItem);
					newNode.getAttributes().setNamedItem(att);
				}
			}
			
			//Credit card
			nlist = dom.getElementsByTagName("PAYMENTS");
			node = nlist.item(0);
			//1) BRAND 
			if(brand!=null && brand.length()>0)
			{
				att = dom.createAttribute("BRAND");
				att.setValue(brand);
				node.getAttributes().setNamedItem(att);
			}
			//2) Last 4 digits of a credit card
			if(last4!=null && last4.length()>0)
			{
				att = dom.createAttribute("LASTFOUR");
				att.setValue(last4);
				node.getAttributes().setNamedItem(att);
			}
			
			//generate the string without header
			DOMSource domSource = new DOMSource(dom);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();		
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(domSource, result);
			strResult =  writer.toString();
			}
			catch (  SAXException e) {
			}
			catch (  ParserConfigurationException e) {
			}
			catch (  Exception e) {
			}
		Log.d("XML Fax Order", strResult);
		return strResult;
	}

}
