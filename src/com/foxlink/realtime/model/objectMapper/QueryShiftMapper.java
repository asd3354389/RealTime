package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryShift;

public class QueryShiftMapper implements RowMapper<QueryShift> {

	@Override
	public QueryShift mapRow(ResultSet rs, int rowNum) throws SQLException {
		QueryShift queryShift = new QueryShift();
		queryShift.setId(rs.getString("id"));
		queryShift.setCostid(rs.getString("costid"));
		queryShift.setName(rs.getString("name"));
		queryShift.setDepid(rs.getString("depid"));
		queryShift.setClass_start(rs.getString("class_start"));
		queryShift.setClass_end(rs.getString("class_end"));
		queryShift.setEmp_date(rs.getString("emp_date"));
		queryShift.setUpdate_time(rs.getString("update_time"));
		queryShift.setClass_no(rs.getString("class_no"));
		return queryShift;
	}

}
