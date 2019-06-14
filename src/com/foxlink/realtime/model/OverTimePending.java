package com.foxlink.realtime.model;

public class OverTimePending {
	private int ID;
	private String Bonus;
	private String OverTimeDate;

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
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
