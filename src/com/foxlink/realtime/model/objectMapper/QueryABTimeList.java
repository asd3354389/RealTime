package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryByIdList;

public class QueryABTimeList implements RowMapper<QueryByIdList> {

	@Override
	public QueryByIdList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		QueryByIdList qbi = new QueryByIdList();
		qbi.setDEPID(rs.getString("depid"));
		qbi.setUSERID(rs.getString("userid"));
		qbi.setUSERNAME(rs.getString("username"));
		qbi.setCount(rs.getInt("count"));
		qbi.setSumTime(rs.getInt("sumtime"));
		return qbi;
	}

}
