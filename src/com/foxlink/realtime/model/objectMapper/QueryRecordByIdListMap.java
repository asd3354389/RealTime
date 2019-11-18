package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.QueryRecordByIdList;

public class QueryRecordByIdListMap implements RowMapper<QueryRecordByIdList>{
	@Override
	public QueryRecordByIdList mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		// TODO Auto-generated method stub
		QueryRecordByIdList exce = new QueryRecordByIdList();
		exce.setEMP_ID(rs.getString("EMP_ID"));
		exce.setNAME(rs.getString("NAME"));
	    exce.setDEPID(rs.getString("DEPID"));
		exce.setSWIPE_DATE(rs.getString("SWIPE_DATE"));
		exce.setSWIPECARDTIME(rs.getString("SWIPECARDTIME"));
		exce.setSWIPECARDTIME2(rs.getString("SWIPECARDTIME2"));
		exce.setSHIFT(rs.getString("SHIFT"));
		
		return exce;
	}
}
