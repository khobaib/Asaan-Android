package com.techfiesta.asaan.model;

import com.parse.ParseFile;
import com.parse.ParseObject;

public class Store {
	ParseObject storeObject;
	ParseFile storeImage;
	
	public Store(ParseObject storeObject, ParseFile storeImage) {
		super();
		this.storeObject = storeObject;
		this.storeImage = storeImage;
	}
	
	public ParseObject getStoreObject() {
		return storeObject;
	}

	public void setStoreObject(ParseObject storeObject) {
		this.storeObject = storeObject;
	}

	public ParseFile getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(ParseFile storeImage) {
		this.storeImage = storeImage;
	}

	
	
	

}
