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
		otcardbd.setCostId(rs.getString("CostId"));
		otcardbd.setDefault_WorkShop(rs.getString("default_workshopno"));
		otcardbd.setEnabled(rs.getString("enabled"));
		otcardbd.setDepid(rs.getString("costid"));
		return otcardbd;
	}

}
