package com.foxlink.realtime.model;

import java.sql.Date;

public class WorkShop {
	private String ID;
	private String WORKSHOPNO;
	private String LINENO;
	private int LINESIZE;
	private Date CREATE_DATE;
	private String UPDATE_USER;
	private int ENABLED;
	
	public WorkShop() {
		
	}
	
	public WorkShop(String Id,String WorkShopNO,String LineNo,int LineSize,Date CreateDate,String UpdateUser,int Enabled) {
		this.setID(Id);
		this.setWORKSHOPNO(WorkShopNO);
		this.setLINENO(LineNo);
		this.setLINESIZE(LineSize);
		this.setCREATE_DATE(CreateDate);
		this.setUPDATE_USER(UpdateUser);
		this.setENABLED(Enabled);
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public String getWORKSHOPNO() {
		return WORKSHOPNO;
	}

	public void setWORKSHOPNO(String wORKSHOPNO) {
		WORKSHOPNO = wORKSHOPNO;
	}

	public String getLINENO() {
		return LINENO;
	}

	public void setLINENO(String lINENO) {
		LINENO = lINENO;
	}

	public int getLINESIZE() {
		return LINESIZE;
	}

	public void setLINESIZE(int lINESIZE) {
		LINESIZE = lINESIZE;
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
	
}
