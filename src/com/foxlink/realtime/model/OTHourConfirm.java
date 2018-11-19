package com.foxlink.realtime.model;

public class OTHourConfirm  {
	private String ClassNo;
	private String RCNo;
	private String WorkshopNo;
	private String LineNo;
	private String OverTimeDate;
	private int CheckState;
	private String AssistantID;
	private String AssistantAccount;
	private int OverTimeType;
	private int OverTimeType1;
	
	private String ItemNumber;
	private String SelectedEmps;
	private int IsAbnormal;
	private String WorkContent;
	
	public String getClassNo() {
		return ClassNo;
	}
	public void setClassNo(String classNo) {
		ClassNo = classNo;
	}
	
	public String getRCNo() {
		return RCNo;
	}
	public void setRCNo(String rCNo) {
		RCNo = rCNo;
	}
	
	public String getWorkshopNo() {
		return WorkshopNo;
	}
	public void setWorkshopNo(String workshopNo) {
		WorkshopNo = workshopNo;
	}
	public int getCheckState() {
		return CheckState;
	}
	public void setCheckState(int checkState) {
		CheckState = checkState;
	}
	public String getAssistantID() {
		return AssistantID;
	}
	public void setAssistantID(String assistantID) {
		AssistantID = assistantID;
	}
	public int getOverTimeType() {
		return OverTimeType;
	}
	public int getOverTimeType1() {
		return OverTimeType1;
	}
	public void setOverTimeType1(int overTimeType1) {
		OverTimeType1 = overTimeType1;
	}
	public void setOverTimeType(int overTimeType) {
		OverTimeType = overTimeType;
	}
	public String getItemNumber() {
		return ItemNumber;
	}
	public void setItemNumber(String itemNumber) {
		ItemNumber = itemNumber;
	}

	public int getIsAbnormal() {
		return IsAbnormal;
	}
	public void setIsAbnormal(int isAbnormal) {
		IsAbnormal = isAbnormal;
	}
	public String getWorkContent() {
		return WorkContent;
	}
	public void setWorkContent(String workContent) {
		WorkContent = workContent;
	}
	public String getAssistantAccount() {
		return AssistantAccount;
	}
	public void setAssistantAccount(String assistantAccount) {
		AssistantAccount = assistantAccount;
	}
	public String getOverTimeDate() {
		return OverTimeDate;
	}
	public void setOverTimeDate(String overTimeDate) {
		OverTimeDate = overTimeDate;
	}
	public String getSelectedEmps() {
		return SelectedEmps;
	}
	public void setSelectedEmps(String selectedEmps) {
		SelectedEmps = selectedEmps;
	}
	public String getLineNo() {
		return LineNo;
	}
	public void setLineNo(String lineNo) {
		LineNo = lineNo;
	}
	
	
}
