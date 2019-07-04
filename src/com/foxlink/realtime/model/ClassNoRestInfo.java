package com.foxlink.realtime.model;

public class ClassNoRestInfo {
	private String CostId;
	private String Class_No;
	private String Rest_Start_F;
	private String Rest_End_F;
	private String Rest_Start_S;
	private String Rest_End_S;
	public String getCostId() {
		return CostId;
	}
	public void setCostId(String costId) {
		CostId = costId;
	}
	public String getClass_No() {
		return Class_No;
	}
	public void setClass_No(String class_No) {
		Class_No = class_No;
	}
	public String getRest_Start_F() {
		return Rest_Start_F;
	}
	public void setRest_Start_F(String rest_Start_F) {
		Rest_Start_F = rest_Start_F;
	}
	public String getRest_End_F() {
		return Rest_End_F;
	}
	public void setRest_End_F(String rest_End_F) {
		Rest_End_F = rest_End_F;
	}
	public String getRest_Start_S() {
		return Rest_Start_S;
	}
	public void setRest_Start_S(String rest_Start_S) {
		Rest_Start_S = rest_Start_S;
	}
	public String getRest_End_S() {
		return Rest_End_S;
	}
	public void setRest_End_S(String rest_End_S) {
		Rest_End_S = rest_End_S;
	}
	@Override
	public String toString() {
		return "ClassNoRestInfo [CostId=" + CostId + ", Class_No=" + Class_No + ", Rest_Start_F=" + Rest_Start_F
				+ ", Rest_End_F=" + Rest_End_F + ", Rest_Start_S=" + Rest_Start_S + ", Rest_End_S=" + Rest_End_S + "]";
	}

	
}
