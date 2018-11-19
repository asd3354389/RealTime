package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		User user =new User();
		user.setUSERNAME(rs.getString(1));
		user.setPASSWORD(rs.getString(2));
		user.setCHINESENAME(rs.getString(3));
		user.setASSISTANT_ID(rs.getString(4));
		user.setDEPARTMENTCODE(rs.getString(5));
		user.setCOSTID(rs.getString(6));
		user.setPHONE_TEL(rs.getString(7));
		user.setCREATE_DATE(rs.getDate(8));
		user.setUPDATE_USER(rs.getString(9));
		user.setENABLED(rs.getInt(10));
		user.setCHPASS_TIME(rs.getDate(11));
		user.setQUERY_COSTID(rs.getString(12));
		user.setROLE(rs.getString(13));
		return user;
	}

}
