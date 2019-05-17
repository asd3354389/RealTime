package com.foxlink.realtime.model;

public class ExceptionCost {
	private String WorkShopNo;
	private String CosttId;
	private String Enabled;
	private String O_WorkShopNo;
	private String O_CosttId;
	public String getWorkShopNo() {
		return WorkShopNo;
	}
	public void setWorkShopNo(String workShopNo) {
		WorkShopNo = workShopNo;
	}	
	public String getCosttId() {
		return CosttId;
	}
	public void setCosttId(String costtId) {
		CosttId = costtId;
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
	public String getO_CosttId() {
		return O_CosttId;
	}
	public void setO_CosttId(String o_CosttId) {
		O_CosttId = o_CosttId;
	}
	@Override
	public String toString() {
		return "ExceptionCost [WorkShopNo=" + WorkShopNo + ", CosttId=" + CosttId + ", Enabled=" + Enabled
				+ ", O_WorkShopNo=" + O_WorkShopNo + ", O_CosttId=" + O_CosttId + "]";
	}
	
	
}
