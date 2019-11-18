package com.foxlink.realtime.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryInfoByIdList {
	private String DEPID;
	private String DEPNAME;
	private String USERID;
	private String USERNAME;
	private String SWIPEDATETIME;
	private String SWIPEDOOR;
	private String INSERT_DATETIME;
	public String getDEPID() {
		return DEPID;
	}
	public void setDEPID(String dEPID) {
		DEPID = dEPID;
	}
	public String getDEPNAME() {
		return DEPNAME;
	}
	public void setDEPNAME(String dEPNAME) {
		DEPNAME = dEPNAME;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getSWIPEDATETIME() {
		return SWIPEDATETIME;
	}
	public void setSWIPEDATETIME(String sWIPEDATETIME) {
		
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = null;
		//String str="2012-05-24 11:44:34";
		Date d;
		try {
			d = formatter.parse(sWIPEDATETIME);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			date = format.format(d);
			System.out.println("date==="+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		SWIPEDATETIME = sWIPEDATETIME;
	}
	public String getSWIPEDOOR() {
		return SWIPEDOOR;
	}
	public void setSWIPEDOOR(String sWIPEDOOR) {
		SWIPEDOOR = sWIPEDOOR;
	}
	public String getINSERT_DATETIME() {
		return INSERT_DATETIME;
	}
	public void setINSERT_DATETIME(String iNSERT_DATETIME) {
		INSERT_DATETIME = iNSERT_DATETIME;
	}
	@Override
	public String toString() {
		return "QueryInfoByIdList [DEPID=" + DEPID + ", DEPNAME=" + DEPNAME + ", USERID=" + USERID + ", USERNAME="
				+ USERNAME + ", SWIPEDATETIME=" + SWIPEDATETIME + ", SWIPEDOOR=" + SWIPEDOOR + ", INSERT_DATETIME="
				+ INSERT_DATETIME + "]";
	}
	
}
