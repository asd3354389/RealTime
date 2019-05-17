package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ExceptionCost;

public class QueryExceptionCost implements RowMapper<ExceptionCost>{

	@Override
	public ExceptionCost mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ExceptionCost exce = new ExceptionCost();
		exce.setWorkShopNo(rs.getString("WorkShopNo"));
		exce.setCosttId(rs.getString("CosttId"));
		exce.setEnabled(rs.getString("Enabled"));
		return exce;
	}

}
