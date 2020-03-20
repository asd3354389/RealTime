package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.FourteenRO;
import com.foxlink.realtime.model.FourteenROP;

public class FourteenROPMapper implements RowMapper<FourteenROP>{

	@Override
	public FourteenROP mapRow(ResultSet rs, int rowNum) throws SQLException {
		FourteenROP fourteenRO = new FourteenROP();
		fourteenRO.setId(rs.getString("id"));
		fourteenRO.setStart_date(rs.getString("start_date"));
		fourteenRO.setEnd_date(rs.getString("end_date"));
		return fourteenRO;
	}

}
