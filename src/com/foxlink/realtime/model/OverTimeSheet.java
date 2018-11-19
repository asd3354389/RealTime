package com.foxlink.realtime.model;

import java.sql.Date;

public class OverTimeSheet {
	private String WorkshopNo;
	private String LineNo;
	private String SDate;
	private String ClassNo;
	private String RCNo;
	private String CheckState;
	private String StdManPower;
	private String PrimaryItemNo;
	private int Mount;
	private int TotalManPower;
	
	public String getWorkshopNo() {
		return WorkshopNo;
	}
	public void setWorkshopNo(String workshopNo) {
		WorkshopNo = workshopNo;
	}
	
	public String getSDate() {
		return SDate;
	}
	public void setSDate(String sDate) {
		SDate = sDate;
	}
	
	public String getClassNo() {
		return ClassNo;
	}
	public void setClassNo(String classNo) {
		ClassNo = classNo;
	}
	
	public String getRCNo() {
		return RCNo;
	}
	public void setRCNo(String rCNo) {
		RCNo = rCNo;
	}
	
	public String getCheckState() {
		return CheckState;
	}
	public void setCheckState(String checkState) {
		CheckState = checkState;
	}
	public String getStdManPower() {
		return StdManPower;
	}
	public void setStdManPower(String stdManPower) {
		StdManPower = stdManPower;
	}
	public String getPrimaryItemNo() {
		return PrimaryItemNo;
	}
	public void setPrimaryItemNo(String primaryItemNo) {
		PrimaryItemNo = primaryItemNo;
	}
	public int getMount() {
		return Mount;
	}
	public void setMount(int mount) {
		Mount = mount;
	}
	public int getTotalManPower() {
		return TotalManPower;
	}
	public void setTotalManPower(int totalManPower) {
		TotalManPower = totalManPower;
	}
	public String getLineNo() {
		return LineNo;
	}
	public void setLineNo(String lineNo) {
		LineNo = lineNo;
	}
}