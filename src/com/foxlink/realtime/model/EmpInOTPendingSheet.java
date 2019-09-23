package com.foxlink.realtime.model;

import java.sql.Date;

public class EmpInOTPendingSheet {
	private String EmpID;
	private String EmpName;
	private String DeptID;
	private String DeptName;
	private String Direct;
	private String CostID;
	private String SwipeCardDate;
	private int CheckState;
	private String OnDutyTime;
	private String OffDutyTime;
	private String OverTimeHour;
	private String OverTimeInterval;
	private String BONUS;
	
	public String getBONUS() {
		return BONUS;
	}
	public void setBONUS(String bONUS) {
		BONUS = bONUS;
	}
	public String getEmpID() {
		return EmpID;
	}
	public void setEmpID(String empID) {
		EmpID = empID;
	}
	public String getEmpName() {
		return EmpName;
	}
	public void setEmpName(String empName) {
		EmpName = empName;
	}
	public String getDeptID() {
		return DeptID;
	}
	public void setDeptID(String deptID) {
		DeptID = deptID;
	}
	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	public String getDirect() {
		return Direct;
	}
	public void setDirect(String direct) {
		Direct = direct;
	}
	public String getCostID() {
		return CostID;
	}
	public void setCostID(String costID) {
		CostID = costID;
	}
	public String getSwipeCardDate() {
		return SwipeCardDate;
	}
	public void setSwipeCardDate(String swipeCardDate) {
		SwipeCardDate = swipeCardDate;
	}
	public int getCheckState() {
		return CheckState;
	}
	public void setCheckState(int checkState) {
		CheckState = checkState;
	}
	public String getOnDutyTime() {
		return OnDutyTime;
	}
	public void setOnDutyTime(String onDutyTime) {
		OnDutyTime = onDutyTime;
	}
	public String getOffDutyTime() {
		return OffDutyTime;
	}
	public void setOffDutyTime(String offDutyTime) {
		OffDutyTime = offDutyTime;
	}
	public String getOverTimeHour() {
		return OverTimeHour;
	}
	public void setOverTimeHour(String overTimeHour) {
		OverTimeHour = overTimeHour;
	}
	public String getOverTimeInterval() {
		return OverTimeInterval;
	}
	public void setOverTimeInterval(String overTimeInterval) {
		OverTimeInterval = overTimeInterval;
	}
}
