package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.IOWSRecord;

public class QueryIOWSRecord implements RowMapper<IOWSRecord>{

	@Override
	public IOWSRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		IOWSRecord iowsRecord = new IOWSRecord();
		iowsRecord.setEmp_id(rs.getString("Emp_id"));
		iowsRecord.setD_Cardid(rs.getString("D_Cardid"));
		iowsRecord.setWorkShopNo(rs.getString("WorkShopNo"));
		iowsRecord.setSwipeCardTime(rs.getString("SwipeCardTime"));
		iowsRecord.setDirection(rs.getString("Direction"));
		iowsRecord.setName(rs.getString("Name"));
		iowsRecord.setDepId(rs.getString("Depid"));
		iowsRecord.setCostId(rs.getString("Costid"));
		return iowsRecord;
	}

}
