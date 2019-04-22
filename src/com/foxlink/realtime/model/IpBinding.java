package com.foxlink.realtime.model;

public class IpBinding {

	//電腦IP
	private String DEVICEIP;
	//費用代碼
	private String DEPTID;
	//工號
	private String UPDATE_USERID;
	//是否生效
	private String ENABLED;
	//app表的IP地址
	private String COM_IP;
	//app表的负责人
	private String DUTY_PERSON;
	//費用代碼
	private String COSTID;
	//助理id
	private String USERNAME;
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getCOSTID() {
		return COSTID;
	}
	public void setCOSTID(String cOSTID) {
		COSTID = cOSTID;
	}
	public String getCOM_IP() {
		return COM_IP;
	}
	public void setCOM_IP(String cOM_IP) {
		COM_IP = cOM_IP;
	}
	public String getDUTY_PERSON() {
		return DUTY_PERSON;
	}
	public void setDUTY_PERSON(String dUTY_PERSON) {
		DUTY_PERSON = dUTY_PERSON;
	}
	public String getDEVICEIP() {
		return DEVICEIP;
	}
	public void setDEVICEIP(String dEVICEIP) {
		DEVICEIP = dEVICEIP;
	}
	public String getDEPTID() {
		return DEPTID;
	}
	public void setDEPTID(String dEPTID) {
		DEPTID = dEPTID;
	}
	public String getUPDATE_USERID() {
		return UPDATE_USERID;
	}
	public void setUPDATE_USERID(String uPDATE_USERID) {
		UPDATE_USERID = uPDATE_USERID;
	}
	public String getENABLED() {
		return ENABLED;
	}
	public void setENABLED(String eNABLED) {
		ENABLED = eNABLED;
	}
}
