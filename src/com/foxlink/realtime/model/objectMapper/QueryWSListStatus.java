package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.WSListStatus;

public class QueryWSListStatus implements RowMapper<WSListStatus>{

	@Override
	public WSListStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		WSListStatus wsls = new WSListStatus();
		wsls.setReason_No(rs.getString("Reason_No"));
		wsls.setReason_Class(rs.getString("Reason_Class"));
		wsls.setReason_Desc(rs.getString("Reason_Desc"));
		return wsls;
	}

}
