package com.foxlink.realtime.model;

public class ExceptionCost {
	private String WorkShopNo;
	private String CostId;
	private String Enabled;
	private String O_WorkShopNo;
	private String O_CostId;
	public String getWorkShopNo() {
		return WorkShopNo;
	}
	public void setWorkShopNo(String workShopNo) {
		WorkShopNo = workShopNo;
	}	
	public String getCostId() {
		return CostId;
	}
	public void setCostId(String costId) {
		CostId = costId;
	}
	public String getEnabled() {
		return Enabled;
	}
	public void setEnabled(String enabled) {
		Enabled = enabled;
	}	
	public String getO_WorkShopNo() {
		return O_WorkShopNo;
	}
	public void setO_WorkShopNo(String o_WorkShopNo) {
		O_WorkShopNo = o_WorkShopNo;
	}
	public String getO_CostId() {
		return O_CostId;
	}
	public void setO_CostId(String o_CostId) {
		O_CostId = o_CostId;
	}
	@Override
	public String toString() {
		return "ExceptionCost [WorkShopNo=" + WorkShopNo + ", CostId=" + CostId + ", Enabled=" + Enabled
				+ ", O_WorkShopNo=" + O_WorkShopNo + ", O_CostId=" + O_CostId + "]";
	}
	
	
}
