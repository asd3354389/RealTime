package com.foxlink.realtime.model;

public class JobInfo {
	private String Job_Code;
	private String Job_Desc;
	public String getJob_Code() {
		return Job_Code;
	}
	public void setJob_Code(String job_Code) {
		Job_Code = job_Code;
	}
	public String getJob_Desc() {
		return Job_Desc;
	}
	public void setJob_Desc(String job_Desc) {
		Job_Desc = job_Desc;
	}
	@Override
	public String toString() {
		return "JobInfo [Job_Code=" + Job_Code + ", Job_Desc=" + Job_Desc + "]";
	}
	
}
