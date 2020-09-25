package com.foxlink.realtime.model;

public class AccessAB {
	private String id;
	private String name;
	private String costid;
	private String deptid;
	private String depid;
	private String deptname;
	private String interval;
	private String time;
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
	public String getCostid() {
		return costid;
	}
	public void setCostid(String costid) {
		this.costid = costid;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "AccessAB [id=" + id + ", name=" + name + ", costid=" + costid + ", deptid=" + deptid + ", depid="
				+ depid + ", deptname=" + deptname + ", interval=" + interval + ", time=" + time + "]";
	}
	
	
}
