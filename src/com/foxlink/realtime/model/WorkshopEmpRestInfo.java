package com.foxlink.realtime.model;

public class WorkshopEmpRestInfo {
	private String EMP_ID;
	private String CLASS_NO;
	private String REST_START1;
	private String REST_END1;
	private String REST_START2;
	private String REST_END2;

	

	public String getEMP_ID() {
		return EMP_ID;
	}



	public void setEMP_ID(String eMP_ID) {
		EMP_ID = eMP_ID;
	}



	public String getCLASS_NO() {
		return CLASS_NO;
	}



	public void setCLASS_NO(String cLASS_NO) {
		CLASS_NO = cLASS_NO;
	}



	public String getREST_START1() {
		return REST_START1;
	}



	public void setREST_START1(String rEST_START1) {
		REST_START1 = rEST_START1;
	}



	public String getREST_END1() {
		return REST_END1;
	}



	public void setREST_END1(String rEST_END1) {
		REST_END1 = rEST_END1;
	}



	public String getREST_START2() {
		return REST_START2;
	}



	public void setREST_START2(String rEST_START2) {
		REST_START2 = rEST_START2;
	}



	public String getREST_END2() {
		return REST_END2;
	}



	public void setREST_END2(String rEST_END2) {
		REST_END2 = rEST_END2;
	}



	@Override
	public String toString() {
		return "WorkshopEmpRestInfo [EMP_ID=" + EMP_ID + ", CLASS_NO=" + CLASS_NO + ", REST_START1=" + REST_START1
				+ ", REST_END1=" + REST_END1 + ", REST_START2=" + REST_START2 + ", REST_END2=" + REST_END2 + "]";
	}


}
