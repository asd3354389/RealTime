package com.foxlink.realtime.model;

import java.io.Serializable;

public class DGsubsidy  implements Serializable {
	private static final long serialVersionUID=1L;
	private String EmpId;
	private String Name;
	private String DepId;
	private String CostId;
	private String SwipeCardTime;
	private String SwipeCardTime2;
	private String ClassStart;
	
    public DGsubsidy() {
		
	}
	
	public DGsubsidy(String empId,String name,String costId,String depId,String swipeCardTime,String swipeCardTime2,String classStart) {
		this.setEmpId(empId);
		this.setName(name);
		this.setDepId(depId);
		this.setCostId(costId);
		this.setSwipeCardTime(swipeCardTime);
		this.setSwipeCardTime2(swipeCardTime2);
		this.setClassStart(classStart);
	}

	public String getEmpId() {
		return EmpId;
	}

	public void setEmpId(String empId) {
		EmpId = empId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDepId() {
		return DepId;
	}

	public void setDepId(String depId) {
		DepId = depId;
	}

	public String getCostId() {
		return CostId;
	}

	public void setCostId(String costId) {
		CostId = costId;
	}

	public String getSwipeCardTime() {
		return SwipeCardTime;
	}

	public void setSwipeCardTime(String swipeCardTime) {
		SwipeCardTime = swipeCardTime;
	}

	public String getSwipeCardTime2() {
		return SwipeCardTime2;
	}

	public void setSwipeCardTime2(String swipeCardTime2) {
		SwipeCardTime2 = swipeCardTime2;
	}

	public String getClassStart() {
		return ClassStart;
	}

	public void setClassStart(String classStart) {
		ClassStart = classStart;
	}

}
