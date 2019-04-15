package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.OTCardBD;

public class QueryOTCardBdMapper implements RowMapper<OTCardBD>{

	@Override
	public OTCardBD mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		OTCardBD otcardbd = new OTCardBD();
		otcardbd.setD_CardID(rs.getString("d_cardid"));
		otcardbd.setDmp_id(rs.getString("dmp_id"));
		otcardbd.setName(rs.getString("name"));
		otcardbd.setDefault_WorkShop(rs.getString("default_workshopno"));
		otcardbd.setEnable(rs.getString("enabled"));
		return otcardbd;
	}

}
