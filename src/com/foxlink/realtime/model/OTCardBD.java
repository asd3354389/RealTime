package com.foxlink.realtime.model;

public class OTCardBD {
	private String D_CardID;
	private String Dmp_id;
	private String name;
	private String Default_WorkShop;
	private String Enable;
	private String Update_UserId;
	public String getD_CardID() {
		return D_CardID;
	}
	public void setD_CardID(String d_CardID) {
		D_CardID = d_CardID;
	}
	public String getDmp_id() {
		return Dmp_id;
	}
	public void setDmp_id(String dmp_id) {
		Dmp_id = dmp_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefault_WorkShop() {
		return Default_WorkShop;
	}
	public void setDefault_WorkShop(String default_WorkShop) {
		Default_WorkShop = default_WorkShop;
	}
	public String getEnable() {
		return Enable;
	}
	public void setEnable(String enable) {
		Enable = enable;
	}	
	public String getUpdate_UserId() {
		return Update_UserId;
	}
	public void setUpdate_UserId(String update_UserId) {
		Update_UserId = update_UserId;
	}
	@Override
	public String toString() {
		return "OTCardBD [D_CardID=" + D_CardID + ", Dmp_id=" + Dmp_id + ", name=" + name + ", Default_WorkShop="
				+ Default_WorkShop + ", Enable=" + Enable + ", Update_UserId=" + Update_UserId + "]";
	}
	
	
}
