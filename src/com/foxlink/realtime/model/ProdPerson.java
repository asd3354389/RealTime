package com.foxlink.realtime.model;

import java.util.Arrays;

public class ProdPerson {
	
	private String PLAN_DATE_YEAR;
	private String SHIFT;
	private String COSTID;
	private String DEPID;
	private String PROD_NAME;
	private String PROD_CODE;
	private String NUMBER_OF_PEOPLE;
	private String PLAN_DATE_WEEK;
	private String DataList[];
	public String getPLAN_DATE_YEAR() {
		return PLAN_DATE_YEAR;
	}
	public void setPLAN_DATE_YEAR(String pLAN_DATE_YEAR) {
		PLAN_DATE_YEAR = pLAN_DATE_YEAR;
	}
	public String getSHIFT() {
		return SHIFT;
	}
	public void setSHIFT(String sHIFT) {
		SHIFT = sHIFT;
	}
	public String getDEPID() {
		return DEPID;
	}
	public void setDEPID(String dEPID) {
		DEPID = dEPID;
	}
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getNUMBER_OF_PEOPLE() {
		return NUMBER_OF_PEOPLE;
	}
	public void setNUMBER_OF_PEOPLE(String nUMBER_OF_PEOPLE) {
		NUMBER_OF_PEOPLE = nUMBER_OF_PEOPLE;
	}
	public String[] getDataList() {
		return DataList;
	}
	public void setDataList(String[] dataList) {
		DataList = dataList;
	}
	public String getPLAN_DATE_WEEK() {
		return PLAN_DATE_WEEK;
	}
	public void setPLAN_DATE_WEEK(String pLAN_DATE_WEEK) {
		PLAN_DATE_WEEK = pLAN_DATE_WEEK;
	}
	public String getCOSTID() {
		return COSTID;
	}
	public void setCOSTID(String cOSTID) {
		COSTID = cOSTID;
	}
	@Override
	public String toString() {
		return "ProdPerson [PLAN_DATE_YEAR=" + PLAN_DATE_YEAR + ", SHIFT=" + SHIFT + ", COSTID=" + COSTID + ", DEPID="
				+ DEPID + ", PROD_NAME=" + PROD_NAME + ", PROD_CODE=" + PROD_CODE + ", NUMBER_OF_PEOPLE="
				+ NUMBER_OF_PEOPLE + ", PLAN_DATE_WEEK=" + PLAN_DATE_WEEK + ", DataList=" + Arrays.toString(DataList)
				+ "]";
	}
}
