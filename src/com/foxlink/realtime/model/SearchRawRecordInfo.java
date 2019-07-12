package com.foxlink.realtime.model;

import java.io.Serializable;

public class SearchRawRecordInfo  implements Serializable {
	private static final long serialVersionUID=1L;
	private String empId;
	private String name;
	private String depId;
	private String costId;
	private String swipeCardTime;
	private String swipeCardIpAddress;
	private String deptid;
	@Override
	public String toString() {
		return "SearchRawRecordInfo [empId=" + empId + ", name=" + name + ", depId=" + depId + ", costId=" + costId
				+ ", swipeCardTime=" + swipeCardTime + ", swipeCardIpAddress=" + swipeCardIpAddress + ", deptid="
				+ deptid + "]";
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public SearchRawRecordInfo() {
		
	}
	
	public SearchRawRecordInfo(String empId,String name,String depId,String costId,String swipeCardTime,String swipeCardIpAddress,String deptid) {
		this.setEmpId(empId);
		this.setName(name);
		this.setDepId(depId);
		this.setCostId(costId);
		this.setSwipeCardTime(swipeCardTime);
		this.setSwipeCardIpAddress(swipeCardIpAddress);
		this.setDeptid(deptid);
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getCostId() {
		return costId;
	}

	public void setCostId(String costId) {
		this.costId = costId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSwipeCardTime() {
		return swipeCardTime;
	}

	public void setSwipeCardTime(String swipeCardTime) {
		this.swipeCardTime = swipeCardTime;
	}

	public String getSwipeCardIpAddress() {
		return swipeCardIpAddress;
	}

	public void setSwipeCardIpAddress(String swipeCardIpAddress) {
		this.swipeCardIpAddress = swipeCardIpAddress;
	}

}
