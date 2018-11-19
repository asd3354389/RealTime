package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.OverTimeSheet;

public class OTSheetMapper implements RowMapper<OverTimeSheet> {

	@Override
	public OverTimeSheet mapRow(ResultSet rs, int arg1) throws SQLException {
		OverTimeSheet otSheet=new OverTimeSheet();
		otSheet.setWorkshopNo(rs.getString("WORKSHOPNO"));
		otSheet.setSDate(rs.getString("SDATE"));
		otSheet.setClassNo(rs.getString("CLASS_NO"));
		otSheet.setRCNo(rs.getString("RC_NO"));
		otSheet.setLineNo(rs.getString("PROD_LINE_CODE"));
		otSheet.setCheckState(rs.getString("CHECKSTATE"));
		otSheet.setStdManPower(rs.getString("STD_MAN_POWER"));
		otSheet.setPrimaryItemNo(rs.getString("PRIMARY_ITEM_NO"));
		otSheet.setMount(rs.getInt("MOUNT"));
		otSheet.setTotalManPower(rs.getInt("TOTAL"));
		return otSheet;
	}

}
