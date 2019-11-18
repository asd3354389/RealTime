package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryByIdList;


public class QueryByIdListMap implements RowMapper<QueryByIdList>{
	@Override
	public QueryByIdList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		QueryByIdList exce = new QueryByIdList();
		exce.setEXCEPTION_DATE(rs.getString("EXCEPTION_DATE"));
		exce.setCOSTID(rs.getString("COSTID"));
	    exce.setDEPID(rs.getString("DEPID"));
		exce.setSHIFT(rs.getString("SHIFT"));
		exce.setUSERID(rs.getString("USERID"));
		exce.setUSERNAME(rs.getString("USERNAME"));
		exce.setEXCEPTION_INTERVAL(rs.getString("EXCEPTION_INTERVAL"));
		exce.setEXCEPTION_REASON(rs.getString("EXCEPTION_REASON"));
		return exce;
	}
}
