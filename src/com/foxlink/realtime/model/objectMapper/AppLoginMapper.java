package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.AppLogin;

public class AppLoginMapper implements RowMapper<AppLogin>{

	@Override
	public AppLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		AppLogin al = new AppLogin();
		al.setCom_name(rs.getString("com_name"));
		al.setIp(rs.getString("com_ip"));
		al.setCostid(rs.getString("duty_costid"));
		al.setId(rs.getString("duty_person"));
		al.setTel(rs.getString("duty_tel"));
		al.setControl_type(rs.getString("control_except"));
		return al;
	}

}
