package com.foxlink.realtime.model;

public class Employee {
	private String EmpNo;
	private String EmpName;
	private String DeptNo;
	private String DeptName;
	private String Direct;
	private String CardID;
	private String CostID;
	private int Permission;
	private int IsOnWork;
	private String Job_Title;
	private String Job_Name;
	private String Line_Personnel;
	
	public String getEmpNo() {
		return EmpNo;
	}
	public String getEmpName() {
		return EmpName;
	}
	public void setEmpName(String empName) {
		EmpName = empName;
	}
	public String getDeptNo() {
		return DeptNo;
	}
	public void setDeptNo(String deptNo) {
		DeptNo = deptNo;
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
	public String getCardID() {
		return CardID;
	}
	public void setCardID(String cardID) {
		CardID = cardID;
	}
	public String getCostID() {
		return CostID;
	}
	public void setCostID(String costID) {
		CostID = costID;
	}
	public int getPermission() {
		return Permission;
	}
	public void setPermission(int permission) {
		Permission = permission;
	}
	public int getIsOnWork() {
		return IsOnWork;
	}
	public void setIsOnWork(int isOnWork) {
		IsOnWork = isOnWork;
	}
	public String getJob_Title() {
		return Job_Title;
	}
	public void setJob_Title(String job_Title) {
		Job_Title = job_Title;
	}
	public String getJob_Name() {
		return Job_Name;
	}
	public void setJob_Name(String job_Name) {
		Job_Name = job_Name;
	}
	public void setEmpNo(String empNo) {
		EmpNo = empNo;
	}
	public String getLine_Personnel() {
		return Line_Personnel;
	}
	public void setLine_Personnel(String line_Personnel) {
		Line_Personnel = line_Personnel;
	}
	@Override
	public String toString() {
		return "Employee [EmpNo=" + EmpNo + ", EmpName=" + EmpName + ", DeptNo=" + DeptNo + ", DeptName=" + DeptName
				+ ", Direct=" + Direct + ", CardID=" + CardID + ", CostID=" + CostID + ", Permission=" + Permission
				+ ", IsOnWork=" + IsOnWork + ", Job_Title=" + Job_Title + ", Job_Name=" + Job_Name + ", Line_Personnel="
				+ Line_Personnel + "]";
	}
	
	
}
