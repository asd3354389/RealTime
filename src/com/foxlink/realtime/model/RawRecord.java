package com.foxlink.realtime.model;

import java.io.Serializable;

public class RawRecord  implements Serializable {
	private static final long serialVersionUID=1L;
	private String ID;
	private String CARDID;
	private String SWIPECARDTIME;
	private String RECORD_STATUS;
	private String IP_ADDRESS;	
	
    public RawRecord() {
		
	}
	
	public RawRecord(String empId,String CardId,String SwipeCardTime,String RecordStatus,String SwipeCardIp) {
		this.setID(empId);
		this.setCARDID(CardId);
		this.setSWIPECARDTIME(SwipeCardTime);
		this.setRECORD_STATUS(RecordStatus);
		this.setIP_ADDRESS(SwipeCardIp);
	}

	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCARDID() {
		return CARDID;
	}
	public void setCARDID(String cARDID) {
		CARDID = cARDID;
	}
	public String getSWIPECARDTIME() {
		return SWIPECARDTIME;
	}
	public void setSWIPECARDTIME(String sWIPECARDTIME) {
		SWIPECARDTIME = sWIPECARDTIME;
	}
	public String getRECORD_STATUS() {
		return RECORD_STATUS;
	}
	public void setRECORD_STATUS(String rECORD_STATUS) {
		RECORD_STATUS = rECORD_STATUS;
	}
	public String getIP_ADDRESS() {
		return IP_ADDRESS;
	}
	public void setIP_ADDRESS(String iP_ADDRESS) {
		IP_ADDRESS = iP_ADDRESS;
	}	
}
