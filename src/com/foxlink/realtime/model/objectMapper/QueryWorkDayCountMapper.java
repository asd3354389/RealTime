package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryWorkDayCount;

public class QueryWorkDayCountMapper implements RowMapper<QueryWorkDayCount> {

	@Override
	public QueryWorkDayCount mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		QueryWorkDayCount qwdc = new QueryWorkDayCount();
		qwdc.setId(rs.getString("id"));
		qwdc.setName(rs.getString("name"));
		qwdc.setDepid(rs.getString("depid"));
		qwdc.setCostid(rs.getString("costid"));
		qwdc.setStart_Date(rs.getString("start_Date"));
		qwdc.setEnd_Date(rs.getString("end_Date"));
		qwdc.setContinuous_Days(rs.getString("continuous_Days"));
		return qwdc;
	}

}
