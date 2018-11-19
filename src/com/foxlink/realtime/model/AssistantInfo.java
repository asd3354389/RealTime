package com.foxlink.realtime.model;

import java.io.Serializable;
import java.sql.Date;

public class AssistantInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String ASSISTANTCOSTID;
	private String ASSISTANTNAME;
	private String ASSISTANTID;
	private String ASSISTANTDEP;
	private String ASSISTANTTEL;
	private String ASSISTANTEMAIL;
	private Date CREATE_DATE;
	private String UPDATE_USER;
	private int ENABLED;
	
	
	public AssistantInfo() {
		
	}
	
	public AssistantInfo(String AssistantCostID,String AssistantName,String AssistantID,String AssistantDep,String AssistantTel,String AssistantEmail,Date CreateDate,String UpdateUser,int Enabled) {
		this.setASSISTANTCOSTID(AssistantCostID);
		this.setASSISTANTNAME(AssistantName);
		this.setASSISTANTID(AssistantID);
		this.setASSISTANTDEP(AssistantDep);
		this.setASSISTANTTEL(AssistantTel);
		this.setASSISTANTEMAIL(AssistantEmail);
		this.setCREATE_DATE(CreateDate);
		this.setUPDATE_USER(UpdateUser);
		this.setENABLED(Enabled);
	}


	public String getASSISTANTCOSTID() {
		return ASSISTANTCOSTID;
	}


	public void setASSISTANTCOSTID(String aSSISTANTCOSTID) {
		ASSISTANTCOSTID = aSSISTANTCOSTID;
	}


	public String getASSISTANTNAME() {
		return ASSISTANTNAME;
	}


	public void setASSISTANTNAME(String aSSISTANTNAME) {
		ASSISTANTNAME = aSSISTANTNAME;
	}


	public String getASSISTANTID() {
		return ASSISTANTID;
	}


	public void setASSISTANTID(String aSSISTANTID) {
		ASSISTANTID = aSSISTANTID;
	}


	public String getASSISTANTDEP() {
		return ASSISTANTDEP;
	}


	public void setASSISTANTDEP(String aSSISTANTDEP) {
		ASSISTANTDEP = aSSISTANTDEP;
	}


	public String getASSISTANTTEL() {
		return ASSISTANTTEL;
	}


	public void setASSISTANTTEL(String aSSISTANTTEL) {
		ASSISTANTTEL = aSSISTANTTEL;
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

	public String getASSISTANTEMAIL() {
		return ASSISTANTEMAIL;
	}

	public void setASSISTANTEMAIL(String aSSISTANTEMAIL) {
		ASSISTANTEMAIL = aSSISTANTEMAIL;
	}
	
	
	
}
