package com.rays.pro4.Bean;

public class CustomerBean extends BaseBean {

	private String clientName;
	private String location;
	private String contactNo;
	private String importance;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return clientName;
	}
}
