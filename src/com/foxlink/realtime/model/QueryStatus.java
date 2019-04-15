package com.foxlink.realtime.model;

import java.util.Date;

public class QueryStatus {
	private String id;
	private String costId;
	private String name;
	private String depid;
	private String depname;
	private String direct;
	private String lineno;
	private String rc_no;
	private String PRIMARY_ITEM_NO;
	private String OVERTIMEDATE;
	private String bonus;
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getOVERTIMEDATE() {
		return OVERTIMEDATE;
	}
	public void setOVERTIMEDATE(String oVERTIMEDATE) {
		OVERTIMEDATE = oVERTIMEDATE;
	}
	private String OVERTIMEDATEEnd;
	private String uploadId;
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
	public String getOVERTIMEDATEEnd() {
		return OVERTIMEDATEEnd;
	}
	public void setOVERTIMEDATEEnd(String oVERTIMEDATEEnd) {
		OVERTIMEDATEEnd = oVERTIMEDATEEnd;
	}
	private String SHIFT;
	private String WORKCONTENT;
	private String OVERTIMEHOURS;
	private String OVERTIMEINTERVAL;
	private String APPLICATION_PERSON;
	private String APPLICATION_ID;
	private String APPLICATION_DEP;
	private String APPLICATION_TEL;
	private String REASON;
	private String WORKSHOPNO;
	private int ISEXCEPTION;
	private int OVERTIMETYPE;
	private int NOTESSTATES;
	private Date UPDATE_TIME;
	private Date BACKTIME;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getDirect() {
		return direct;
	}
	public void setDirect(String direct) {
		this.direct = direct;
	}
	public String getLineno() {
		return lineno;
	}
	public void setLineno(String lineno) {
		this.lineno = lineno;
	}
	public String getRc_no() {
		return rc_no;
	}
	public void setRc_no(String rc_no) {
		this.rc_no = rc_no;
	}
	public String getPRIMARY_ITEM_NO() {
		return PRIMARY_ITEM_NO;
	}
	public void setPRIMARY_ITEM_NO(String pRIMARY_ITEM_NO) {
		PRIMARY_ITEM_NO = pRIMARY_ITEM_NO;
	}
	
	
	public String getSHIFT() {
		return SHIFT;
	}
	public void setSHIFT(String sHIFT) {
		SHIFT = sHIFT;
	}
	public String getWORKCONTENT() {
		return WORKCONTENT;
	}
	public void setWORKCONTENT(String wORKCONTENT) {
		WORKCONTENT = wORKCONTENT;
	}
	public String getOVERTIMEHOURS() {
		return OVERTIMEHOURS;
	}
	public void setOVERTIMEHOURS(String oVERTIMEHOURS) {
		OVERTIMEHOURS = oVERTIMEHOURS;
	}
	public String getOVERTIMEINTERVAL() {
		return OVERTIMEINTERVAL;
	}
	public void setOVERTIMEINTERVAL(String oVERTIMEINTERVAL) {
		OVERTIMEINTERVAL = oVERTIMEINTERVAL;
	}
	public String getAPPLICATION_PERSON() {
		return APPLICATION_PERSON;
	}
	public void setAPPLICATION_PERSON(String aPPLICATION_PERSON) {
		APPLICATION_PERSON = aPPLICATION_PERSON;
	}
	public String getAPPLICATION_ID() {
		return APPLICATION_ID;
	}
	public void setAPPLICATION_ID(String aPPLICATION_ID) {
		APPLICATION_ID = aPPLICATION_ID;
	}
	public String getAPPLICATION_DEP() {
		return APPLICATION_DEP;
	}
	public void setAPPLICATION_DEP(String aPPLICATION_DEP) {
		APPLICATION_DEP = aPPLICATION_DEP;
	}
	public String getAPPLICATION_TEL() {
		return APPLICATION_TEL;
	}
	public void setAPPLICATION_TEL(String aPPLICATION_TEL) {
		APPLICATION_TEL = aPPLICATION_TEL;
	}
	public String getREASON() {
		return REASON;
	}
	public void setREASON(String rEASON) {
		REASON = rEASON;
	}
	public String getWORKSHOPNO() {
		return WORKSHOPNO;
	}
	public void setWORKSHOPNO(String wORKSHOPNO) {
		WORKSHOPNO = wORKSHOPNO;
	}
	public int getISEXCEPTION() {
		return ISEXCEPTION;
	}
	public void setISEXCEPTION(int iSEXCEPTION) {
		ISEXCEPTION = iSEXCEPTION;
	}
	public int getOVERTIMETYPE() {
		return OVERTIMETYPE;
	}
	public void setOVERTIMETYPE(int oVERTIMETYPE) {
		OVERTIMETYPE = oVERTIMETYPE;
	}
	public int getNOTESSTATES() {
		return NOTESSTATES;
	}
	public void setNOTESSTATES(int nOTESSTATES) {
		NOTESSTATES = nOTESSTATES;
	}
	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	public Date getBACKTIME() {
		return BACKTIME;
	}
	public void setBACKTIME(Date bACKTIME) {
		BACKTIME = bACKTIME;
	}
	public String getCostId() {
		return costId;
	}
	public void setCostId(String costId) {
		this.costId = costId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
