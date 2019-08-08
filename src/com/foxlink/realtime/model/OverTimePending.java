package com.foxlink.realtime.model;

public class OverTimePending {
	private String ID;
	private String Bonus;
	private String OverTimeDate;

	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getBonus() {
		return Bonus;
	}
	public void setBonus(String bonus) {
		Bonus = bonus;
	}	
	public String getOverTimeDate() {
		return OverTimeDate;
	}
	public void setOverTimeDate(String overTimeDate) {
		OverTimeDate = overTimeDate;
	}
	@Override
	public String toString() {
		return "OverTimePending [ID=" + ID + ", Bonus=" + Bonus + ", OverTimeDate=" + OverTimeDate + "]";
	}

}
