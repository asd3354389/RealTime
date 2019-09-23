package com.foxlink.realtime.model;

public class IOWorkShopPW {
	private String Emp_id;
	private String WorkShopNo;
	private String Start_Date;
	private String End_Date;
	private String Enabled;
	private String CardId;
	private String Remark;
	public String getCardId() {
		return CardId;
	}
	public void setCardId(String cardId) {
		CardId = cardId;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getEmp_id() {
		return Emp_id;
	}
	public void setEmp_id(String emp_id) {
		Emp_id = emp_id;
	}
	public String getWorkShopNo() {
		return WorkShopNo;
	}
	public void setWorkShopNo(String workShopNo) {
		WorkShopNo = workShopNo;
	}
	public String getStart_Date() {
		return Start_Date;
	}
	public void setStart_Date(String start_Date) {
		Start_Date = start_Date;
	}
	public String getEnd_Date() {
		return End_Date;
	}
	public void setEnd_Date(String end_Date) {
		End_Date = end_Date;
	}
	public String getEnabled() {
		return Enabled;
	}
	public void setEnabled(String enabled) {
		Enabled = enabled;
	}
	@Override
	public String toString() {
		return "IOWorkShopPW [Emp_id=" + Emp_id + ", WorkShopNo=" + WorkShopNo + ", Start_Date=" + Start_Date
				+ ", End_Date=" + End_Date + ", Enabled=" + Enabled + ", CardId=" + CardId + ", Remark=" + Remark + "]";
	}
	
	
}
