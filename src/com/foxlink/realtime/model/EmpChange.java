package com.foxlink.realtime.model;

public class EmpChange {
	private String ID;
	private String NAME;
	private String NoCard;
	private String Status;
	
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getNoCard() {
		return NoCard;
	}
	public void setNoCard(String noCard) {
		NoCard = noCard;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	@Override
	public String toString() {
		return "EmpChange [ID=" + ID + ", NAME=" + NAME + ", NoCard=" + NoCard + ", Status=" + Status + "]";
	}
	
}
