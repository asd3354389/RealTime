package com.foxlink.realtime.model;

import javax.xml.crypto.Data;

public class Prod {
	private String RecordId;
	private String CostId;
	private String Depid;
	private String Shift;
	private String Prod_Name;
	private String Prod_Code;
	private String Status;
	private String Depid_Go;
	private String Reason;
	private String Number_Of_People;
	private String WorkDate;
	private int Sum_Of_People;
	
	public String getRecordId() {
		return RecordId;
	}
	public void setRecordId(String recordId) {
		RecordId = recordId;
	}
	public String getCostId() {
		return CostId;
	}
	public void setCostId(String costId) {
		CostId = costId;
	}
	public String getDepid() {
		return Depid;
	}
	public void setDepid(String depid) {
		Depid = depid;
	}
	public String getShift() {
		return Shift;
	}
	public void setShift(String shift) {
		Shift = shift;
	}
	public String getProd_Name() {
		return Prod_Name;
	}
	public void setProd_Name(String prod_Name) {
		Prod_Name = prod_Name;
	}
	public String getProd_Code() {
		return Prod_Code;
	}
	public void setProd_Code(String prod_Code) {
		Prod_Code = prod_Code;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getDepid_Go() {
		return Depid_Go;
	}
	public void setDepid_Go(String depid_Go) {
		Depid_Go = depid_Go;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public String getNumber_Of_People() {
		return Number_Of_People;
	}
	public void setNumber_Of_People(String number_Of_People) {
		Number_Of_People = number_Of_People;
	}
	
	public String getWorkDate() {
		return WorkDate;
	}
	public void setWorkDate(String workDate) {
		WorkDate = workDate;
	}
	
	public int getSum_Of_People() {
		return Sum_Of_People;
	}
	public void setSum_Of_People(int sum_Of_People) {
		Sum_Of_People = sum_Of_People;
	}
	@Override
	public String toString() {
		return "Prod [RecordId=" + RecordId + ", CostId=" + CostId + ", Depid=" + Depid + ", Shift=" + Shift
				+ ", Prod_Name=" + Prod_Name + ", Prod_Code=" + Prod_Code + ", Status=" + Status + ", Depid_Go="
				+ Depid_Go + ", Reason=" + Reason + ", Number_Of_People=" + Number_Of_People + ", WorkDate=" + WorkDate
				+ ", Sum_Of_People=" + Sum_Of_People + "]";
	}
	
	
}
