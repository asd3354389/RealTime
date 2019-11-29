package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryByIdList;

import oracle.net.aso.e;

public class QueryABReason implements RowMapper<QueryByIdList> {

	@Override
	public QueryByIdList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		QueryByIdList qbi = new QueryByIdList();
		qbi.setROWID(rs.getString("id"));
		qbi.setCOSTID(rs.getString("costid"));
		qbi.setDEPID(rs.getString("depid"));
		qbi.setUSERID(rs.getString("userid"));
		qbi.setUSERNAME(rs.getString("username"));
		qbi.setEXCEPTION_DATE(rs.getString("exception_date"));
		qbi.setSHIFT(rs.getString("shift"));
		qbi.setEXCEPTION_INTERVAL(rs.getString("exception_interval"));
		qbi.setEXCEPTION_TIME(rs.getString("exception_time"));
		qbi.setEXCEPTION_REASON(rs.getString("exception_reason"));
		return qbi;
	}

}
