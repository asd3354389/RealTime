package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ChangeEmployee;
import com.foxlink.realtime.model.EmpChange;

public class QueryEmployee implements RowMapper<EmpChange>{
	@Override
	public EmpChange mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		EmpChange exce = new EmpChange();
		exce.setNAME(rs.getString("NAME"));
		exce.setID(rs.getString("ID"));
		return exce;
	}
}
