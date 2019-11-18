package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ChangeEmployee;


public class QueryChangeEmployee implements RowMapper<ChangeEmployee>{
	@Override
	public ChangeEmployee mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		ChangeEmployee exce = new ChangeEmployee();
		exce.setEMP_ID(rs.getString("EMP_ID"));
		exce.setSWIPE_DATE(rs.getString("SWIPE_DATE"));
		exce.setNAME(rs.getString("NAME"));
		return exce;
	}
}
