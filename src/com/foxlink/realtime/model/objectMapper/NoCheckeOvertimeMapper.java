package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.DGsubsidy;

public class NoCheckeOvertimeMapper implements RowMapper<DGsubsidy>{

	@Override
	public DGsubsidy mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		DGsubsidy dgsubsidy =new DGsubsidy();
		dgsubsidy.setEmpId(rs.getString("ID"));
		dgsubsidy.setName(rs.getString("Name"));
		dgsubsidy.setDeptId(rs.getString("deptid"));
		dgsubsidy.setCostId(rs.getString("costID"));
		dgsubsidy.setSwipeCardTime(rs.getString("SwipeCardTime"));
		dgsubsidy.setSwipeCardTime2(rs.getString("SwipeCardTime2"));
		dgsubsidy.setSwipeCardDate(rs.getString("swipe_date"));
		return dgsubsidy;
	}

}
