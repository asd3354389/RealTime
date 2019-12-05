package com.foxlink.realtime.model;

public class ProdAllLine {
	private String RecordId;
	private String CostId;
	private String Shift;
	private String Transition_Reason;
	private String Prod_Out_Name;
	private String Prod_Out_Code;
	private String Transition_Out_Number;
	private String Type_In;
	private String Prod_In_Name;
	private String Prod_In_Code;
	private String Transition_In_Number;
	private String Insert_Date;
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
	public String getShift() {
		return Shift;
	}
	public void setShift(String shift) {
		Shift = shift;
	}
	public String getTransition_Reason() {
		return Transition_Reason;
	}
	public void setTransition_Reason(String transition_Reason) {
		Transition_Reason = transition_Reason;
	}
	public String getProd_Out_Name() {
		return Prod_Out_Name;
	}
	public void setProd_Out_Name(String prod_Out_Name) {
		Prod_Out_Name = prod_Out_Name;
	}
	public String getProd_Out_Code() {
		return Prod_Out_Code;
	}
	public void setProd_Out_Code(String prod_Out_Code) {
		Prod_Out_Code = prod_Out_Code;
	}
	public String getTransition_Out_Number() {
		return Transition_Out_Number;
	}
	public void setTransition_Out_Number(String transition_Out_Number) {
		Transition_Out_Number = transition_Out_Number;
	}
	public String getType_In() {
		return Type_In;
	}
	public void setType_In(String type_In) {
		Type_In = type_In;
	}
	public String getProd_In_Name() {
		return Prod_In_Name;
	}
	public void setProd_In_Name(String prod_In_Name) {
		Prod_In_Name = prod_In_Name;
	}
	public String getProd_In_Code() {
		return Prod_In_Code;
	}
	public void setProd_In_Code(String prod_In_Code) {
		Prod_In_Code = prod_In_Code;
	}
	public String getTransition_In_Number() {
		return Transition_In_Number;
	}
	public void setTransition_In_Number(String transition_In_Number) {
		Transition_In_Number = transition_In_Number;
	}
	
	public String getInsert_Date() {
		return Insert_Date;
	}
	public void setInsert_Date(String insert_Date) {
		Insert_Date = insert_Date;
	}
	@Override
	public String toString() {
		return "ProdAllLine [RecordId=" + RecordId + ", CostId=" + CostId + ", Shift=" + Shift + ", Transition_Reason="
				+ Transition_Reason + ", Prod_Out_Name=" + Prod_Out_Name + ", Prod_Out_Code=" + Prod_Out_Code
				+ ", Transition_Out_Number=" + Transition_Out_Number + ", Type_In=" + Type_In + ", Prod_In_Name="
				+ Prod_In_Name + ", Prod_In_Code=" + Prod_In_Code + ", Transition_In_Number=" + Transition_In_Number
				+ ", Insert_Date=" + Insert_Date + "]";
	}

	
}
