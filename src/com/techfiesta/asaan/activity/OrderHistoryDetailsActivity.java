package com.techfiesta.asaan.activity;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.android.gms.internal.lv;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.adapter.OrderHistoryItemAdapter;
import com.techfiesta.asaan.utility.AsaanUtility;
import com.techfiesta.asaan.utility.NestedListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import asaan.dao.AddItem;

public class OrderHistoryDetailsActivity extends BaseActivity{
	
	private double subtotal,gratutity,tax,amountDue;
	private TextView tvStoreName, tvSubtotal, tvGratuity, tvTax, tvAmountDue,tvSummary;
	private ArrayList<AddItem> orderdItems=new ArrayList<AddItem>();
	private NestedListView nestedListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_order_details);
		tvStoreName = (TextView) findViewById(R.id.tv_store_name);
		tvSubtotal = (TextView) findViewById(R.id.tv_subtotal_amount);
		tvGratuity = (TextView) findViewById(R.id.tv_gratuity_amount);
		tvTax = (TextView) findViewById(R.id.tv_tax_amount);
		tvAmountDue = (TextView) findViewById(R.id.tv_due_amount);
		tvSummary=(TextView)findViewById(R.id.tv_summary);
		
		nestedListView=(NestedListView)findViewById(R.id.lv_item_list);
		
		tvStoreName.setText(AsaanUtility.selectedStoreOrder.getStoreName());
		tvSummary.setText("Order Summary -"+getFormattedDate(AsaanUtility.selectedStoreOrder.getCreatedDate()));
		ParseOrdersFromXml(AsaanUtility.selectedStoreOrder.getOrderDetails());
	} 
	private String getFormattedDate(Long rawTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy");
		String sDate = sdf.format(new Date(rawTime));
		return sDate;
	}

	private void ParseOrdersFromXml(String xmlDetails) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlDetails));

			Document doc = dBuilder.parse(is);
			 NodeList nodes = doc.getElementsByTagName("CHECK");
			 Element line = (Element) nodes.item(0);
			subtotal= Double.valueOf(line.getAttribute("SUBTOTAL"));
			tax=Double.valueOf(line.getAttribute("TAX"));
			gratutity=Double.valueOf(line.getAttribute("SERVICECHARGES"));
			amountDue=Double.valueOf(line.getAttribute("COMPLETETOTAL"));
			tvSubtotal.setText("$"+subtotal);
			tvTax.setText("$"+tax);
			tvGratuity.setText("$"+gratutity);
			tvAmountDue.setText("$"+amountDue);
			
			createOrderItemList(doc);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createOrderItemList(Document doc)
	{
		NodeList entries=doc.getElementsByTagName("ENTRY");
		for(int i=0;i<entries.getLength();i++)
		{
			AddItem addItem=new AddItem();
			 Element line = (Element) entries.item(i);
			String name=line.getAttribute("DISP_NAME");
			Log.e("ITEM_NAME", ""+name);
			addItem.setItem_name(name);
			int q=Integer.parseInt(line.getAttribute("QUANTITY"));
			addItem.setQuantity(q);
			
			int price=Integer.parseInt(line.getAttribute("PRICE"));
			addItem.setPrice(price);
			
			orderdItems.add(addItem);
			
		}
		OrderHistoryItemAdapter adapter=new OrderHistoryItemAdapter(OrderHistoryDetailsActivity.this,orderdItems);
		nestedListView.setAdapter(adapter);
	}
	private String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	  }

}