package com.foxlink.realtime.model;

public class SignOverTime {
	private String id;
	private String name;
	private String depid;
	private String class_no;
	private String overtimeDate;
	private String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	public String getClass_no() {
		return class_no;
	}
	public void setClass_no(String class_no) {
		this.class_no = class_no;
	}
	public String getOvertimeDate() {
		return overtimeDate;
	}
	public void setOvertimeDate(String overtimeDate) {
		this.overtimeDate = overtimeDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SignOverTime [id=" + id + ", name=" + name + ", depid=" + depid + ", class_no=" + class_no
				+ ", overtimeDate=" + overtimeDate + ", status=" + status + "]";
	}
	
	
	
	
	
}
