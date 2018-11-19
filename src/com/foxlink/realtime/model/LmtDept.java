package com.foxlink.realtime.model;

import java.io.Serializable;
import java.sql.Date;

public class LmtDept  implements Serializable {
	private String DEPID;
	private String DEPTNAME;
	private String COSTID;
	private String DEPTNAME2;
	public String getDEPID() {
		return DEPID;
	}
	public void setDEPID(String dEPID) {
		DEPID = dEPID;
	}
	public String getDEPTNAME() {
		return DEPTNAME;
	}
	public void setDEPTNAME(String dEPTNAME) {
		DEPTNAME = dEPTNAME;
	}
	public String getCOSTID() {
		return COSTID;
	}
	public void setCOSTID(String cOSTID) {
		COSTID = cOSTID;
	}
	public String getDEPTNAME2() {
		return DEPTNAME2;
	}
	public void setDEPTNAME2(String dEPTNAME2) {
		DEPTNAME2 = dEPTNAME2;
	}
}
