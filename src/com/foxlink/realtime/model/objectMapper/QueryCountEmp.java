package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ManPowerStatus;

public class QueryCountEmp implements RowMapper<ManPowerStatus> {

	@Override
	public ManPowerStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ManPowerStatus mps = new ManPowerStatus();
		mps.setID(rs.getString("id"));
		mps.setName(rs.getString("name"));
		mps.setDepid(rs.getString("depid"));
		mps.setCostId(rs.getString("costid"));
		mps.setStatus(rs.getString("status"));
		mps.setClass_No(rs.getString("class_no"));
		return mps;
	}

}
