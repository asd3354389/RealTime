package com.foxlink.realtime.model;

public class EmpOT15min {
	/*ce.id 工號,
    ce.Name 姓名,
    ce.costid 費用代碼,
    ce.depid 部門代碼,
    ce.depname 部門名稱,
    cs.class_no 班別,
    cs.swipecardtime 上刷,
    cs.swipecardtime2 下刷,
    c.class_start 標準上班起時,*/
	private String id;
	private String Name;
	private String costid;
	private String depid;
	private String depname;
	private String class_no;
	private String class_start;
	private int goWorkAdvance;
	private String overtime_start;
	private int outWorkOvertime;
	private String swipecardtimeg;
	private String swipecardtimeo;
	public String getSwipecardtimeg() {
		return swipecardtimeg;
	}



	public void setSwipecardtimeg(String swipecardtimeg) {
		this.swipecardtimeg = swipecardtimeg;
	}



	public String getSwipecardtimeo() {
		return swipecardtimeo;
	}



	public void setSwipecardtimeo(String swipecardtimeo) {
		this.swipecardtimeo = swipecardtimeo;
	}



	private String TimeStart;
	private String TimeEnd;
	
	

	public String getTimeEnd() {
		return TimeEnd;
	}



	public void setTimeEnd(String timeEnd) {
		TimeEnd = timeEnd;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return Name;
	}



	public void setName(String name) {
		Name = name;
	}



	public String getCostid() {
		return costid;
	}



	public void setCostid(String costid) {
		this.costid = costid;
	}



	public String getDepid() {
		return depid;
	}



	public void setDepid(String depid) {
		this.depid = depid;
	}



	public String getDepname() {
		return depname;
	}



	public void setDepname(String depname) {
		this.depname = depname;
	}



	public String getClass_no() {
		return class_no;
	}



	public void setClass_no(String class_no) {
		this.class_no = class_no;
	}



	public String getTimeStart() {
		return TimeStart;
	}



	public void setTimeStart(String timeStart) {
		TimeStart = timeStart;
	}



	public String getClass_start() {
		return class_start;
	}



	public void setClass_start(String class_start) {
		this.class_start = class_start;
	}



	public int getGoWorkAdvance() {
		return goWorkAdvance;
	}



	public void setGoWorkAdvance(int goWorkAdvance) {
		this.goWorkAdvance = goWorkAdvance;
	}



	public String getOvertime_start() {
		return overtime_start;
	}



	public void setOvertime_start(String overtime_start) {
		this.overtime_start = overtime_start;
	}



	public int getOutWorkOvertime() {
		return outWorkOvertime;
	}



	public void setOutWorkOvertime(int outWorkOvertime) {
		this.outWorkOvertime = outWorkOvertime;
	}



	@Override
	public String toString() {
		return "EmpOT15min [id=" + id + ", Name=" + Name + ", costid=" + costid + ", depid=" + depid + ", depname="
				+ depname + ", class_no=" + class_no + ", class_start=" + class_start + ", goWorkAdvance="
				+ goWorkAdvance + ", overtime_start=" + overtime_start + ", outWorkOvertime=" + outWorkOvertime
				+ ", swipecardtimeg=" + swipecardtimeg + ", swipecardtimeo=" + swipecardtimeo + ", TimeStart="
				+ TimeStart + ", TimeEnd=" + TimeEnd + "]";
	}




	
	
}
