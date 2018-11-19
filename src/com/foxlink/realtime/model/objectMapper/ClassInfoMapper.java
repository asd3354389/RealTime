package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ClassNO;

public class ClassInfoMapper implements RowMapper<ClassNO> {

	@Override
	public ClassNO mapRow(ResultSet rs, int arg1) throws SQLException {
		ClassNO classNo=new ClassNO();
		classNo.setClassStart(rs.getString(1));
		classNo.setRestStart1(rs.getString(2));
		classNo.setRestEnd1(rs.getString(3));
		classNo.setRestStart2(rs.getString(4));
		classNo.setRestEnd2(rs.getString(5));
		classNo.setClassEnd(rs.getString(6));
		classNo.setOvertimeStart(rs.getString(7));
		return classNo;
	}

}
