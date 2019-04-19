package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.IOCardMachineIP;

public class QueryIOCardMaIPMapper implements RowMapper<IOCardMachineIP> {

	@Override
	public IOCardMachineIP mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		IOCardMachineIP ioc = new IOCardMachineIP();
		ioc.setDeviceip(rs.getString("Deviceip"));
		ioc.setWorkShopNo(rs.getString("WorkShopNo"));
		ioc.setWorkShop_Desc(rs.getString("WorkShop_Desc"));
		ioc.setDirection(rs.getString("Direction"));
		ioc.setEnabled(rs.getString("Enabled"));
		return ioc;
	}

}
