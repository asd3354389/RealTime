package com.foxlink.realtime.model;

public class WorkShopStatus {
	private String WorkShopNo;
	private String LineNo;
	private String Status;
	private String Update_Time;
	private String Update_UserId;
	private String Enabled;
	public String getWorkShopNo() {
		return WorkShopNo;
	}
	public void setWorkShopNo(String workShopNo) {
		WorkShopNo = workShopNo;
	}
	public String getLineNo() {
		return LineNo;
	}
	public void setLineNo(String lineNo) {
		LineNo = lineNo;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getUpdate_Time() {
		return Update_Time;
	}
	public void setUpdate_Time(String update_Time) {
		Update_Time = update_Time;
	}
	public String getUpdate_UserId() {
		return Update_UserId;
	}
	public void setUpdate_UserId(String update_UserId) {
		Update_UserId = update_UserId;
	}
	
	public String getEnabled() {
		return Enabled;
	}
	public void setEnabled(String enabled) {
		Enabled = enabled;
	}
	@Override
	public String toString() {
		return "WorkShopStatus [WorkShopNo=" + WorkShopNo + ", LineNo=" + LineNo + ", Status=" + Status
				+ ", Update_Time=" + Update_Time + ", Update_UserId=" + Update_UserId + ", Enabled=" + Enabled + "]";
	}
	
	
}
