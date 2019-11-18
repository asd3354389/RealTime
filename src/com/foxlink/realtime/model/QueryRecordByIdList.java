package com.foxlink.realtime.model;


//實時門禁
public class QueryRecordByIdList {
	private String EMP_ID;
	private String NAME;
	private String DEPID;
	private String SWIPE_DATE;
	private String SWIPECARDTIME;
	private String SWIPECARDTIME2;
	private String SHIFT;
	public String getEMP_ID() {
		return EMP_ID;
	}
	public void setEMP_ID(String eMP_ID) {
		EMP_ID = eMP_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getDEPID() {
		return DEPID;
	}
	public void setDEPID(String dEPID) {
		DEPID = dEPID;
	}
	public String getSWIPE_DATE() {
		return SWIPE_DATE;
	}
	public void setSWIPE_DATE(String sWIPE_DATE) {
		SWIPE_DATE = sWIPE_DATE;
	}
	public String getSWIPECARDTIME() {
		return SWIPECARDTIME;
	}
	public void setSWIPECARDTIME(String sWIPECARDTIME) {
		SWIPECARDTIME = sWIPECARDTIME;
	}
	public String getSWIPECARDTIME2() {
		return SWIPECARDTIME2;
	}
	public void setSWIPECARDTIME2(String sWIPECARDTIME2) {
		SWIPECARDTIME2 = sWIPECARDTIME2;
	}
	public String getSHIFT() {
		return SHIFT;
	}
	public void setSHIFT(String sHIFT) {
		SHIFT = sHIFT;
	}
	@Override
	public String toString() {
		return "QueryRecordByIdList [EMP_ID=" + EMP_ID + ", NAME=" + NAME + ", DEPID=" + DEPID + ", SWIPE_DATE="
				+ SWIPE_DATE + ", SWIPECARDTIME=" + SWIPECARDTIME + ", SWIPECARDTIME2=" + SWIPECARDTIME2 + ", SHIFT="
				+ SHIFT + "]";
	}
	
	

}
