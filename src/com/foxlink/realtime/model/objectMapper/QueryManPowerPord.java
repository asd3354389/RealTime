package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ManPowerStatus;

public class QueryManPowerPord implements RowMapper<ManPowerStatus> {

	@Override
	public ManPowerStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ManPowerStatus mps = new ManPowerStatus();
		mps.setCostId(rs.getString("costid"));
		mps.setShift(rs.getString("shift"));
		mps.setStatus(rs.getString("Status"));
		mps.setCount(rs.getInt("count"));
		return mps;
	}

}
