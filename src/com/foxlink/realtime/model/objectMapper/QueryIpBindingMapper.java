package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.foxlink.realtime.model.IpBinding;

public class QueryIpBindingMapper implements RowMapper<IpBinding>{
	@Override
	public IpBinding mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		IpBinding ioc = new IpBinding();
		ioc.setDEVICEIP(rs.getString("DEVICEIP"));
		ioc.setDEPTID(rs.getString("DEPTID"));
		ioc.setUPDATE_USERID(rs.getString("UPDATE_USERID"));
		ioc.setENABLED(rs.getString("ENABLED"));
		return ioc;
	}
	
}
