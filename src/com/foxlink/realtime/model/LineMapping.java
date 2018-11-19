package com.foxlink.realtime.model;

public class LineMapping {
	private String ORG_NAME;
	private int MES_PDLINE_ID;
	private String MES_PDLINE_NAME;
	private String MES_PDLINE_DESC;
	private String RT_LINENO;
	private int STD_MAN_POWER;
	private int ENABLED;
	public String getORG_NAME() {
		return ORG_NAME;
	}
	public void setORG_NAME(String oRG_NAME) {
		ORG_NAME = oRG_NAME;
	}
	public int getMES_PDLINE_ID() {
		return MES_PDLINE_ID;
	}
	public void setMES_PDLINE_ID(int mES_PDLINE_ID) {
		MES_PDLINE_ID = mES_PDLINE_ID;
	}
	public String getMES_PDLINE_NAME() {
		return MES_PDLINE_NAME;
	}
	public void setMES_PDLINE_NAME(String mES_PDLINE_NAME) {
		MES_PDLINE_NAME = mES_PDLINE_NAME;
	}
	public String getMES_PDLINE_DESC() {
		return MES_PDLINE_DESC;
	}
	public void setMES_PDLINE_DESC(String mES_PDLINE_DESC) {
		MES_PDLINE_DESC = mES_PDLINE_DESC;
	}
	public String getRT_LINENO() {
		return RT_LINENO;
	}
	public void setRT_LINENO(String rT_LINENO) {
		RT_LINENO = rT_LINENO;
	}
	public int getSTD_MAN_POWER() {
		return STD_MAN_POWER;
	}
	public void setSTD_MAN_POWER(int sTD_MAN_POWER) {
		STD_MAN_POWER = sTD_MAN_POWER;
	}
	public int getENABLED() {
		return ENABLED;
	}
	public void setENABLED(int eNABLED) {
		ENABLED = eNABLED;
	}
	@Override
	public String toString() {
		return "LineMapping [ORG_NAME=" + ORG_NAME + ", MES_PDLINE_ID=" + MES_PDLINE_ID + ", MES_PDLINE_NAME="
				+ MES_PDLINE_NAME + ", MES_PDLINE_DESC=" + MES_PDLINE_DESC + ", RT_LINENO=" + RT_LINENO
				+ ", STD_MAN_POWER=" + STD_MAN_POWER + ", ENABLED=" + ENABLED + "]";
	}
	
	
	
	
}
