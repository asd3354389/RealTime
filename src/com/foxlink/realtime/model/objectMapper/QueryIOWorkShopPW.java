package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.IOWorkShopPW;

public class QueryIOWorkShopPW implements RowMapper<IOWorkShopPW>{

	@Override
	public IOWorkShopPW mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		IOWorkShopPW ioWorkShopPW = new IOWorkShopPW();
		ioWorkShopPW.setEmp_id(rs.getString("Emp_id"));
		ioWorkShopPW.setWorkShopNo(rs.getString("WorkShopNo"));
		ioWorkShopPW.setStart_Date(rs.getString("Start_Date"));
		ioWorkShopPW.setEnd_Date(rs.getString("End_Date"));
		ioWorkShopPW.setEnabled(rs.getString("Enabled"));
		return ioWorkShopPW;
	}

}
