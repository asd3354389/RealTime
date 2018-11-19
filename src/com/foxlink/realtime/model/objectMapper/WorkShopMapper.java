package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.WorkShop;

public class WorkShopMapper implements RowMapper<WorkShop> {

	@Override
	public WorkShop mapRow(ResultSet rs, int arg1) throws SQLException {
		WorkShop workShop=new WorkShop();
		workShop.setID(rs.getString(1));
		workShop.setWORKSHOPNO(rs.getString(2));
		workShop.setLINENO(rs.getString(3));
		workShop.setLINESIZE(rs.getInt(4));
		workShop.setCREATE_DATE(rs.getDate(5));
		workShop.setUPDATE_USER(rs.getString(6));
		workShop.setENABLED(rs.getInt(7));
		return workShop;
	}

}
