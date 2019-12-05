package com.foxlink.realtime.model;

public class AppLogin {
	private String com_name;
	private String ip;
	private String costid;
	private String id;
	private String tel;
	private String control_type;
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCostid() {
		return costid;
	}
	public void setCostid(String costid) {
		this.costid = costid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getControl_type() {
		return control_type;
	}
	public void setControl_type(String control_type) {
		this.control_type = control_type;
	}
	@Override
	public String toString() {
		return "AppLogin [com_name=" + com_name + ", ip=" + ip + ", costid=" + costid + ", id=" + id + ", tel=" + tel
				+ ", control_type=" + control_type + "]";
	}
	
	
}
