package com.foxlink.realtime.model;

public class EmpInOTIdentifiedSheet  {
	private String EmpID;
	private String EmpName;
	private String DeptID;
	private String CostID;
	private String Direct;
	private String OTDate;
	private String OTInterval;
	private String OTHours;
	private String NotesStates;
	private String Reason;
	private String Bonus;
	
	public String getBonus() {
		return Bonus;
	}
	public void setBonus(String bonus) {
		Bonus = bonus;
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
	public String getCostID() {
		return CostID;
	}
	public void setCostID(String costID) {
		CostID = costID;
	}
	public String getDirect() {
		return Direct;
	}
	public void setDirect(String direct) {
		Direct = direct;
	}
	public String getOTDate() {
		return OTDate;
	}
	public void setOTDate(String oTDate) {
		OTDate = oTDate;
	}
	public String getOTInterval() {
		return OTInterval;
	}
	public void setOTInterval(String oTInterval) {
		OTInterval = oTInterval;
	}
	public String getOTHours() {
		return OTHours;
	}
	public void setOTHours(String oTHours) {
		OTHours = oTHours;
	}
	public String getNotesStates() {
		return NotesStates;
	}
	public void setNotesStates(String notesStates) {
		NotesStates = notesStates;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	@Override
	public String toString() {
		return "EmpInOTIdentifiedSheet [EmpID=" + EmpID + ", EmpName=" + EmpName + ", DeptID=" + DeptID + ", CostID="
				+ CostID + ", Direct=" + Direct + ", OTDate=" + OTDate + ", OTInterval=" + OTInterval + ", OTHours="
				+ OTHours + ", NotesStates=" + NotesStates + ", Reason=" + Reason + ", Bonus=" + Bonus + "]";
	}

	
	
	
}
