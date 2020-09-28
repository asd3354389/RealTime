package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.EmpIpBinding;

public class QueryEmpIPBindingMapper implements RowMapper<EmpIpBinding>{
	  
	@Override
	public EmpIpBinding mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		EmpIpBinding empIpBinding = new EmpIpBinding();
		empIpBinding.setDeviceIP(rs.getString("deviceIP"));
		empIpBinding.setEmp_id(rs.getString("emp_id"));
		return empIpBinding;
	}

}
