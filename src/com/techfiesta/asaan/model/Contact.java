package com.techfiesta.asaan.model;

public class Contact {
	private String name;
	private String phone;
	private long id;
	public Contact(String name, String phone, long id) {
		this.name = name;
		this.phone = phone;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setId(long id) {
		this.id = id;
	}

}
