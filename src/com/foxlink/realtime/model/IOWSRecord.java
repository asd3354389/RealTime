package com.foxlink.realtime.model;

public class IOWSRecord {
	private String Emp_id;
	private String D_Cardid;
	private String WorkShopNo;
	private String SwipeCardTime;
	private String Direction;
	private String Record_Status;
	private String name;
	private String depId;
	private String costId;
	public String getEmp_id() {
		return Emp_id;
	}
	public void setEmp_id(String emp_id) {
		Emp_id = emp_id;
	}
	
	public String getD_Cardid() {
		return D_Cardid;
	}
	public void setD_Cardid(String d_Cardid) {
		D_Cardid = d_Cardid;
	}
	public String getWorkShopNo() {
		return WorkShopNo;
	}
	public void setWorkShopNo(String workShopNo) {
		WorkShopNo = workShopNo;
	}
	public String getSwipeCardTime() {
		return SwipeCardTime;
	}
	public void setSwipeCardTime(String swipeCardTime) {
		SwipeCardTime = swipeCardTime;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	
	public String getRecord_Status() {
		return Record_Status;
	}
	public void setRecord_Status(String record_Status) {
		Record_Status = record_Status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
