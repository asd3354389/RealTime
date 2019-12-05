package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ManPowerStatus;

public class QueryManPowerStatus implements RowMapper<ManPowerStatus>{

	@Override
	public ManPowerStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ManPowerStatus mps = new ManPowerStatus();
		mps.setCount(rs.getInt("count"));
		mps.setDepid(rs.getString("depid"));
		mps.setClass_No(rs.getString("class_no"));
		return mps;
	}

}
