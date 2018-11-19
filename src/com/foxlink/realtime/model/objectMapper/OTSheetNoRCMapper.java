package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.OverTimeSheet;

public class OTSheetNoRCMapper implements RowMapper<OverTimeSheet> {

	@Override
	public OverTimeSheet mapRow(ResultSet rs, int rowNum) throws SQLException {
		OverTimeSheet otSheet=new OverTimeSheet();
		otSheet.setWorkshopNo(rs.getString("WORKSHOPNO"));
		otSheet.setSDate(rs.getString("SDATE"));
		otSheet.setClassNo(rs.getString("CLASS_NO"));
		otSheet.setLineNo(rs.getString("PROD_LINE_CODE"));
		otSheet.setCheckState(rs.getString("CHECKSTATE"));
		otSheet.setMount(rs.getInt("MOUNT"));
		otSheet.setTotalManPower(rs.getInt("TOTAL"));
		return otSheet;
	}

}
