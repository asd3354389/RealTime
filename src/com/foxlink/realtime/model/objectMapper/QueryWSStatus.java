package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.WorkShopStatus;

public class QueryWSStatus implements RowMapper<WorkShopStatus> {

	@Override
	public WorkShopStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		WorkShopStatus wss = new WorkShopStatus();
		wss.setWorkShopNo(rs.getString("WorkShopNo"));
		wss.setLineNo(rs.getString("LineNo"));
		wss.setStatus(rs.getString("Status"));
		wss.setEnabled(rs.getString("Enabled"));
		return wss;
	}

}
