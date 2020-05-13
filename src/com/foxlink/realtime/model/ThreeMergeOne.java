package com.foxlink.realtime.model;

public class ThreeMergeOne {
	private String EmpId;
	private String Name;
	private String SwipeCardTime;
	private String Record_Type;
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSwipeCardTime() {
		return SwipeCardTime;
	}
	public void setSwipeCardTime(String swipeCardTime) {
		SwipeCardTime = swipeCardTime;
	}
	public String getRecord_Type() {
		return Record_Type;
	}
	public void setRecord_Type(String record_Type) {
		Record_Type = record_Type;
	}
	@Override
	public String toString() {
		return "ThreeMergeOne [EmpId=" + EmpId + ", Name=" + Name + ", SwipeCardTime=" + SwipeCardTime
				+ ", Record_Type=" + Record_Type + "]";
	}
	
	
	
}
