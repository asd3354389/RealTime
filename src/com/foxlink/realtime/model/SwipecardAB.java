package com.foxlink.realtime.model;

public class SwipecardAB {
	private String id;
	private String name;
	private String costid;
	private String deptid;
	private String depid;
	private String depname;
	//下班超15分鐘刷卡次數
	private String overtime15;
	//下班超15分鐘未刷卡次數
	private String morethen7;
	//超7或14休一次數
	private String outwork15;
	private int count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOvertime15() {
		return overtime15;
	}
	public void setOvertime15(String overtime15) {
		this.overtime15 = overtime15;
	}
	public String getMorethen7() {
		return morethen7;
	}
	public void setMorethen7(String morethen7) {
		this.morethen7 = morethen7;
	}
	public String getOutwork15() {
		return outwork15;
	}
	public void setOutwork15(String outwork15) {
		this.outwork15 = outwork15;
	}
	@Override
	public String toString() {
		return "SwipecardAB [id=" + id + ", name=" + name + ", costid=" + costid + ", deptid=" + deptid + ", depid="
				+ depid + ", depname=" + depname + ", overtime15=" + overtime15 + ", morethen7="
				+ morethen7 + ", outwork15=" + outwork15 + ", count=" + count + "]";
	}
}
