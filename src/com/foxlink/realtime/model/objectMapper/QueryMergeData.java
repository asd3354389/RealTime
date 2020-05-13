package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ThreeMergeOne;

public class QueryMergeData implements RowMapper<ThreeMergeOne> {

	@Override
	public ThreeMergeOne mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ThreeMergeOne threeMergeOne = new ThreeMergeOne();
		threeMergeOne.setEmpId(rs.getString("emp_id"));
		threeMergeOne.setName(rs.getString("name"));
		threeMergeOne.setSwipeCardTime(rs.getString("swipecardtime"));
		threeMergeOne.setRecord_Type(rs.getString("record_type"));
		return threeMergeOne;
	}

}
