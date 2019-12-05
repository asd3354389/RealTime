package com.foxlink.realtime.model;

public class BonusDeptid {
	private String Costid;
	private String DeptName_s;
	private String Deptid;
	private String DeptName;
	private String Bonus_Rule;
	private String Modify_Allowed;
	public String getCostid() {
		return Costid;
	}
	public void setCostid(String costid) {
		Costid = costid;
	}
	public String getDeptName_s() {
		return DeptName_s;
	}
	public void setDeptName_s(String deptName_s) {
		DeptName_s = deptName_s;
	}
	public String getDeptid() {
		return Deptid;
	}
	public void setDeptid(String deptid) {
		Deptid = deptid;
	}
	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	public String getBonus_Rule() {
		return Bonus_Rule;
	}
	public void setBonus_Rule(String bonus_Rule) {
		Bonus_Rule = bonus_Rule;
	}
	public String getModify_Allowed() {
		return Modify_Allowed;
	}
	public void setModify_Allowed(String modify_Allowed) {
		Modify_Allowed = modify_Allowed;
	}
	@Override
	public String toString() {
		return "BonusDeptid [Costid=" + Costid + ", DeptName_s=" + DeptName_s + ", Deptid=" + Deptid + ", DeptName="
				+ DeptName + ", Bonus_Rule=" + Bonus_Rule + ", Modify_Allowed=" + Modify_Allowed + "]";
	}
	
	
}
