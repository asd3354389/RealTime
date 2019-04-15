package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryStatus;

public class QueryOTMapper implements RowMapper<QueryStatus>{

	@Override
	public QueryStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		QueryStatus queryStatus=new QueryStatus();
		queryStatus.setId(rs.getString("id"));
		queryStatus.setName(rs.getString("name"));
		queryStatus.setDepid(rs.getString("Depid"));
		queryStatus.setCostId(rs.getString("costId"));
		queryStatus.setDirect(rs.getString("direct"));
		queryStatus.setOVERTIMEDATE(rs.getString("OVERTIMEDATE"));
		queryStatus.setSHIFT(rs.getString("SHIFT"));
		queryStatus.setWORKCONTENT(rs.getString("WORKCONTENT"));
		queryStatus.setOVERTIMEHOURS(rs.getString("OVERTIMEHOURS"));
		queryStatus.setOVERTIMETYPE(rs.getInt("OVERTIMETYPE"));
		queryStatus.setOVERTIMEINTERVAL(rs.getString("OVERTIMEINTERVAL"));
		queryStatus.setAPPLICATION_PERSON(rs.getString("APPLICATION_PERSON"));
		queryStatus.setAPPLICATION_ID(rs.getString("APPLICATION_ID"));
		queryStatus.setNOTESSTATES(rs.getInt("NOTESSTATES"));
		queryStatus.setREASON(rs.getString("REASON"));
		queryStatus.setBACKTIME(rs.getDate("BACKTIME"));
		queryStatus.setWORKSHOPNO(rs.getString("WORKSHOPNO"));
		queryStatus.setBonus(rs.getString("BONUS"));
	
		return queryStatus;
	}
	
}