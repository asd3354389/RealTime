package com.foxlink.realtime.model;

public class IdentifiedOverTime {
	private String Id;
	private String Name;
	private String DepID;
	private String CostID;
	private String Direct;
	private String OverTimeDate;
	private String OverTimeInterval;
	private String OverTimeHours;
	private int OverTimeType;
	private int NoteStates;
	private String Reason;
	private String Bonus;
	
	public String getBonus() {
		return Bonus;
	}
	public void setBonus(String bonus) {
		Bonus = bonus;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDepID() {
		return DepID;
	}
	public void setDepID(String depID) {
		DepID = depID;
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
	public String getOverTimeDate() {
		return OverTimeDate;
	}
	public void setOverTimeDate(String overTimeDate) {
		OverTimeDate = overTimeDate;
	}
	public String getOverTimeInterval() {
		return OverTimeInterval;
	}
	public void setOverTimeInterval(String overTimeInterval) {
		OverTimeInterval = overTimeInterval;
	}
	public String getOverTimeHours() {
		return OverTimeHours;
	}
	public void setOverTimeHours(String overTimeHours) {
		OverTimeHours = overTimeHours;
	}
	public int getOverTimeType() {
		return OverTimeType;
	}
	public void setOverTimeType(int overTimeType) {
		OverTimeType = overTimeType;
	}
	public int getNoteStates() {
		return NoteStates;
	}
	public void setNoteStates(int noteStates) {
		NoteStates = noteStates;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	@Override
	public String toString() {
		return "IdentifiedOverTime [Id=" + Id + ", Name=" + Name + ", DepID=" + DepID + ", CostID=" + CostID
				+ ", Direct=" + Direct + ", OverTimeDate=" + OverTimeDate + ", OverTimeInterval=" + OverTimeInterval
				+ ", OverTimeHours=" + OverTimeHours + ", OverTimeType=" + OverTimeType + ", NoteStates=" + NoteStates
				+ ", Reason=" + Reason + ", Bonus=" + Bonus + "]";
	}
	
	
}
