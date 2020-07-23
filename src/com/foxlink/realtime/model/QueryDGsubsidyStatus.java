/*package com.foxlink.realtime.model;

public class QueryDGsubsidyStatus {

}*/
package com.foxlink.realtime.model;

import java.util.Date;
//員工號	姓名	部門代碼	費用代碼	直間接	日期	班別	頂崗時數
public class QueryDGsubsidyStatus {
	private String ID; //工號
	private String COSTID; //費用代碼
	private String NAME; //姓名
	private String DEPTID; //部門代碼
	private String DIRECT; //直間接
	private String SHIFT;  //班別
	private String OVERTIMEDATE; //日期
	private String BONUS;  //頂崗時數
	private String OVERTIMEDATEEnd;
	public String getOVERTIMEDATEEnd() {
		return OVERTIMEDATEEnd;
	}
	public void setOVERTIMEDATEEnd(String oVERTIMEDATEEnd) {
		OVERTIMEDATEEnd = oVERTIMEDATEEnd;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCOSTID() {
		return COSTID;
	}
	public void setCOSTID(String cOSTID) {
		COSTID = cOSTID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getDEPTID() {
		return DEPTID;
	}
	public void setDEPTID(String dEPTID) {
		DEPTID = dEPTID;
	}
	public String getDIRECT() {
		return DIRECT;
	}
	public void setDIRECT(String dIRECT) {
		DIRECT = dIRECT;
	}
	public String getSHIFT() {
		return SHIFT;
	}
	public void setSHIFT(String sHIFT) {
		SHIFT = sHIFT;
	}
	public String getOVERTIMEDATE() {
		return OVERTIMEDATE;
	}
	public void setOVERTIMEDATE(String oVERTIMEDATE) {
		OVERTIMEDATE = oVERTIMEDATE;
	}
	public String getBONUS() {
		return BONUS;
	}
	public void setBONUS(String bONUS) {
		BONUS = bONUS;
	}
	
	
}
