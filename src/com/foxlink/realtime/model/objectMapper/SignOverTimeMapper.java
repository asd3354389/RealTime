package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.SignOverTime;

public class SignOverTimeMapper implements RowMapper<SignOverTime>{

	@Override
	public SignOverTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		SignOverTime SignOT = new SignOverTime();
		SignOT.setId(rs.getString("id"));
		SignOT.setName(rs.getString("name"));
		SignOT.setDepid(rs.getString("depid"));
		SignOT.setClass_no(rs.getString("class_no"));
		SignOT.setOvertimeDate(rs.getString("OvertimeDate"));
		SignOT.setStatus(rs.getString("status"));
		// TODO Auto-generated method stub
		return SignOT;
	}

}
