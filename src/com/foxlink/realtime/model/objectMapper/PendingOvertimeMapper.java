package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.EmpInOTPendingSheet;

public class PendingOvertimeMapper implements RowMapper<EmpInOTPendingSheet> {

	@Override
	public EmpInOTPendingSheet mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmpInOTPendingSheet overTime=new EmpInOTPendingSheet();
		SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		overTime.setEmpID(rs.getString(1));
		overTime.setEmpName(rs.getString(2));
		overTime.setDeptID(rs.getString(3));
		overTime.setDirect(rs.getString(4));
		overTime.setCostID(rs.getString(5));
		overTime.setSwipeCardDate(rs.getString(6));
		overTime.setCheckState(rs.getInt(7));
		overTime.setOnDutyTime(desiredFormat.format(rs.getTimestamp(8)));
		overTime.setOffDutyTime(desiredFormat.format(rs.getTimestamp(9)));
		return overTime;
	}
	
	

}
