package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.FourteenRO;


public class FourteenROMapper implements RowMapper<FourteenRO>{

	@Override
	public FourteenRO mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		FourteenRO fourteenRO = new FourteenRO();
		fourteenRO.setCostid(rs.getString("Costid"));
		fourteenRO.setStart_date(rs.getString("start_date"));
		fourteenRO.setEnd_date(rs.getString("end_date"));
		return fourteenRO;
	}

}
