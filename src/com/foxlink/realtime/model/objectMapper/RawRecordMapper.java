package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.RawRecord;

public class RawRecordMapper  implements RowMapper<RawRecord> {
	@Override
	public RawRecord mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		RawRecord rawRecord =new RawRecord();
		rawRecord.setID(rs.getString(1));
		rawRecord.setCARDID(rs.getString(2));
		rawRecord.setSWIPECARDTIME(rs.getString(3));
		rawRecord.setRECORD_STATUS(rs.getString(4));
		rawRecord.setIP_ADDRESS(rs.getString(5));		
		return rawRecord;
	}
}
