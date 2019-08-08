package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ClassNO;

public class ClassNoMapper implements RowMapper<ClassNO> {

	@Override
	public ClassNO mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ClassNO classNo=new ClassNO();
		classNo.setRestStart1(rs.getString("Rest_Start1"));
		classNo.setRestEnd1(rs.getString("Rest_End1"));
		classNo.setRestStart2(rs.getString("Rest_Start2"));
		classNo.setRestEnd2(rs.getString("Rest_End2"));
		return classNo;
	}

}
