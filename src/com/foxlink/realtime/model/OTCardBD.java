package com.foxlink.realtime.model;

public class OTCardBD {
	private String D_CardID;
	private String O_CardID;
	private String Dmp_id;
    private String Deptid;
	private String Depid;
	private String Default_WorkShop;
	private String Enabled;
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
	public String getDepid() {
		return Depid;
	}
	public void setDepid(String depid) {
		Depid = depid;
	}
	public String getDefault_WorkShop() {
		return Default_WorkShop;
	}
	public void setDefault_WorkShop(String default_WorkShop) {
		Default_WorkShop = default_WorkShop;
	}
	
	public String getEnabled() {
		return Enabled;
	}
	public void setEnabled(String enabled) {
		Enabled = enabled;
	}
	public String getUpdate_UserId() {
		return Update_UserId;
	}
	public void setUpdate_UserId(String update_UserId) {
		Update_UserId = update_UserId;
	}
	public String getDeptid() {
		return Deptid;
	}
	public void setDeptid(String deptid) {
		Deptid = deptid;
	}
	
	public String getO_CardID() {
		return O_CardID;
	}
	public void setO_CardID(String o_CardID) {
		O_CardID = o_CardID;
	}
	@Override
	public String toString() {
		return "OTCardBD [D_CardID=" + D_CardID + ", O_CardID=" + O_CardID + ", Dmp_id=" + Dmp_id + ", Deptid=" + Deptid
				+ ", Depid=" + Depid + ", Default_WorkShop=" + Default_WorkShop + ", Enabled=" + Enabled
				+ ", Update_UserId=" + Update_UserId + "]";
	}
	
	
}
