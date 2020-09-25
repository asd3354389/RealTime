package com.foxlink.realtime.model;

public class DeparturePerson {
	private String emp_id;
	private String name;
	private String oldEmp_id;
	public String getOldEmp_id() {
		return oldEmp_id;
	}
	public void setOldEmp_id(String oldEmp_id) {
		this.oldEmp_id = oldEmp_id;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
