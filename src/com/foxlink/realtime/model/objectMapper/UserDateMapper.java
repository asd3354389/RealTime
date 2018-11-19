package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.UserDate;

public class UserDateMapper implements RowMapper<UserDate>{

	@Override
	public UserDate mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		UserDate userDate = new UserDate();
		userDate.setUserName(rs.getString("USERNAME"));
		userDate.setPassword(rs.getString("PASSWORD"));
		userDate.setRole(rs.getString("ROLE"));
		return userDate;
	}

}
