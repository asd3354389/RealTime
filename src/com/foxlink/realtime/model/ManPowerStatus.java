package com.foxlink.realtime.model;

public class ManPowerStatus {
	private int Count;
	private String CostId;
	private String Depid;
	private String Class_No;
	private String ID;
	private String Name;
	private String Status;
	private String shift;
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public String getDepid() {
		return Depid;
	}
	public void setDepid(String depid) {
		Depid = depid;
	}

	public String getClass_No() {
		return Class_No;
	}
	public void setClass_No(String class_No) {
		Class_No = class_No;
	}
	
	public String getCostId() {
		return CostId;
	}
	public void setCostId(String costId) {
		CostId = costId;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	@Override
	public String toString() {
		return "ManPowerStatus [Count=" + Count + ", CostId=" + CostId + ", Depid=" + Depid + ", Class_No=" + Class_No
				+ ", ID=" + ID + ", Name=" + Name + ", Status=" + Status + ", shift=" + shift + "]";
	}
	
	
}
