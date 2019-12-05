package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.IpBinding;

public class IpBindingCostSCMapper implements RowMapper<IpBinding>{

	@Override
	public IpBinding mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		IpBinding ioc = new IpBinding();
		ioc.setDEVICEIP(rs.getString("deviceip"));
		ioc.setCOSTID(rs.getString("costid"));
		return ioc;
	}

}
