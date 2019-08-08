package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.EmpInOTPendingSheet;

public class CalOvertimeMapper implements RowMapper<EmpInOTPendingSheet> {

	@Override
	public EmpInOTPendingSheet mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmpInOTPendingSheet overTime=new EmpInOTPendingSheet();
		SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		overTime.setEmpID(rs.getString("ID"));
		overTime.setEmpName(rs.getString("NAME"));
		overTime.setDeptID(rs.getString("DEPID"));
		overTime.setDeptName(rs.getString("DEPNAME"));
		overTime.setDirect(rs.getString("DIRECT"));
		overTime.setCostID(rs.getString("COSTID"));
		overTime.setSwipeCardDate(rs.getString("YD"));
		overTime.setCheckState(rs.getInt("CHECKSTATE"));
		overTime.setOnDutyTime(desiredFormat.format(rs.getTimestamp("SWIPECARDTIME")));
		overTime.setOffDutyTime(desiredFormat.format(rs.getTimestamp("SWIPECARDTIME2")));
		overTime.setOverTimeHour(rs.getString("OVERTIMEHOUR"));
		overTime.setOverTimeInterval(rs.getString("OVERTIMEINTERVAL"));
		overTime.setBONUS(rs.getString("BONUS"));
		return overTime;
	}
	
	

}
