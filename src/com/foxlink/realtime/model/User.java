package com.foxlink.realtime.model;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String USERNAME;
	private String PASSWORD;
	private String CHINESENAME;
	private String ASSISTANT_ID;
	private String DEPARTMENTCODE;
	private String COSTID;
	private String PHONE_TEL;
	private Date CREATE_DATE;
	private String UPDATE_USER;
	private int ENABLED;
	private Date CHPASS_TIME;
	private String QUERY_COSTID;
	private String ROLE;
	
	public User() {
		
	}
	
	public User(String UserName,String Password,String ChineseName,String AssistantID,String DepartmentCode,String CostID,String PhoneTEL,Date CreateDate,String UpdateUser,int Enabled,Date ChpassTime,String QueryCostID,String Role) {
		this.setUSERNAME(UserName);
		this.setPASSWORD(Password);
		this.setCHINESENAME(ChineseName);
		this.setASSISTANT_ID(AssistantID);
		this.setDEPARTMENTCODE(DepartmentCode);
		this.setCOSTID(CostID);
		this.setPHONE_TEL(PhoneTEL);
		this.setCREATE_DATE(CreateDate);
		this.setUPDATE_USER(UpdateUser);
		this.setENABLED(Enabled);
		this.setCHPASS_TIME(ChpassTime);
		this.setQUERY_COSTID(QueryCostID);
		this.setROLE(Role);
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getCHINESENAME() {
		return CHINESENAME;
	}

	public void setCHINESENAME(String cHINESENAME) {
		CHINESENAME = cHINESENAME;
	}

	public String getASSISTANT_ID() {
		return ASSISTANT_ID;
	}

	public void setASSISTANT_ID(String aSSISTANT_ID) {
		ASSISTANT_ID = aSSISTANT_ID;
	}

	public String getDEPARTMENTCODE() {
		return DEPARTMENTCODE;
	}

	public void setDEPARTMENTCODE(String dEPARTMENTCODE) {
		DEPARTMENTCODE = dEPARTMENTCODE;
	}

	public String getCOSTID() {
		return COSTID;
	}

	public void setCOSTID(String cOSTID) {
		COSTID = cOSTID;
	}

	public String getPHONE_TEL() {
		return PHONE_TEL;
	}

	public void setPHONE_TEL(String pHONE_TEL) {
		PHONE_TEL = pHONE_TEL;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getUPDATE_USER() {
		return UPDATE_USER;
	}

	public void setUPDATE_USER(String uPDATE_USER) {
		UPDATE_USER = uPDATE_USER;
	}

	public int getENABLED() {
		return ENABLED;
	}

	public void setENABLED(int eNABLED) {
		ENABLED = eNABLED;
	}

	public Date getCHPASS_TIME() {
		return CHPASS_TIME;
	}

	public void setCHPASS_TIME(Date cHPASS_TIME) {
		CHPASS_TIME = cHPASS_TIME;
	}

	public String getQUERY_COSTID() {
		return QUERY_COSTID;
	}

	public void setQUERY_COSTID(String qUERY_COSTID) {
		QUERY_COSTID = qUERY_COSTID;
	}

	public String getROLE() {
		return ROLE;
	}

	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}

}
