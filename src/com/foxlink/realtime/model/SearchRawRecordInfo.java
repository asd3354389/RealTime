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
	
	public SearchRawRecordInfo() {
		
	}
	
	public SearchRawRecordInfo(String empId,String name,String depId,String costId,String swipeCardTime,String swipeCardIpAddress) {
		this.setEmpId(empId);
		this.setName(name);
		this.setDepId(depId);
		this.setCostId(costId);
		this.setSwipeCardTime(swipeCardTime);
		this.setSwipeCardIpAddress(swipeCardIpAddress);
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
