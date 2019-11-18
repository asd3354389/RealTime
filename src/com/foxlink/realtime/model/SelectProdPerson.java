package com.foxlink.realtime.model;

public class SelectProdPerson {

	private String PLAN_DATE_YEAR;
	private String DEPID;
	private String PLAN_DATE_WEEK;
	private String COSTID;
	public String getCOSTID() {
		return COSTID;
	}
	public void setCOSTID(String cOSTID) {
		COSTID = cOSTID;
	}
	
	public String getPLAN_DATE_YEAR() {
		return PLAN_DATE_YEAR;
	}
	public void setPLAN_DATE_YEAR(String pLAN_DATE_YEAR) {
		PLAN_DATE_YEAR = pLAN_DATE_YEAR;
	}
	
	public String getDEPID() {
		return DEPID;
	}
	public void setDEPID(String dEPID) {
		DEPID = dEPID;
	}
	
	public String getPLAN_DATE_WEEK() {
		return PLAN_DATE_WEEK;
	}
	public void setPLAN_DATE_WEEK(String pLAN_DATE_WEEK) {
		PLAN_DATE_WEEK = pLAN_DATE_WEEK;
	}
}
