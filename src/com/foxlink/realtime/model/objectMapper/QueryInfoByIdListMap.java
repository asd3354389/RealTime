package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.QueryInfoByIdList;


public class QueryInfoByIdListMap implements RowMapper<QueryInfoByIdList>{
	@Override
	public QueryInfoByIdList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		QueryInfoByIdList exce = new QueryInfoByIdList();
		exce.setDEPID(rs.getString("DEPID"));
		exce.setDEPNAME(rs.getString("DEPNAME"));
	    exce.setUSERID(rs.getString("USERID"));
		exce.setUSERNAME(rs.getString("USERNAME"));
		exce.setSWIPEDATETIME(rs.getString("SWIPEDATETIME"));
		exce.setSWIPEDOOR(rs.getString("SWIPEDOOR"));
		exce.setINSERT_DATETIME(rs.getString("INSERT_DATETIME"));
		return exce;
	}
}
