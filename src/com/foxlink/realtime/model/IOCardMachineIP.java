package com.foxlink.realtime.model;

public class IOCardMachineIP {
	private String Deviceip;
	private String WorkShopNo;
	private String WorkShop_Desc;
	private String Direction;
	private String Enabled;
	private String IS_SPECIAL;
	
	public String getDeviceip() {
		return Deviceip;
	}
	public void setDeviceip(String deviceip) {
		Deviceip = deviceip;
	}
	public String getWorkShopNo() {
		return WorkShopNo;
	}
	public void setWorkShopNo(String workShopNo) {
		WorkShopNo = workShopNo;
	}
	public String getWorkShop_Desc() {
		return WorkShop_Desc;
	}
	public void setWorkShop_Desc(String workShop_Desc) {
		WorkShop_Desc = workShop_Desc;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public String getEnabled() {
		return Enabled;
	}
	public void setEnabled(String enabled) {
		Enabled = enabled;
	}
	
	public String getIS_SPECIAL() {
		return IS_SPECIAL;
	}
	public void setIS_SPECIAL(String iS_SPECIAL) {
		IS_SPECIAL = iS_SPECIAL;
	}
	@Override
	public String toString() {
		return "IOCardMachineIP [Deviceip=" + Deviceip + ", WorkShopNo=" + WorkShopNo + ", WorkShop_Desc="
				+ WorkShop_Desc + ", Direction=" + Direction + ", Enabled=" + Enabled + ", IS_SPECIAL=" + IS_SPECIAL
				+ "]";
	}
	
}
