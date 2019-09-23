package com.foxlink.realtime.model;

public class EmpPrivilege {
	private String id;
	private String privilege_Level;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrivilege_Level() {
		return privilege_Level;
	}
	public void setPrivilege_Level(String privilege_Level) {
		this.privilege_Level = privilege_Level;
	}
	@Override
	public String toString() {
		return "EmpPrivilege [id=" + id + ", privilege_Level=" + privilege_Level + "]";
	}
	
}
