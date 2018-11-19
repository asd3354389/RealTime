package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.AssistantInfo;

public class AssistantInfoMapper  implements RowMapper<AssistantInfo>{

	@Override
	public AssistantInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		AssistantInfo assistantInfo=new AssistantInfo();
		assistantInfo.setASSISTANTCOSTID(rs.getString(1));
		assistantInfo.setASSISTANTNAME(rs.getString(2));
		assistantInfo.setASSISTANTID(rs.getString(3));
		assistantInfo.setASSISTANTDEP(rs.getString(4));
		assistantInfo.setASSISTANTTEL(rs.getString(5));
		assistantInfo.setASSISTANTEMAIL(rs.getString(6));
		assistantInfo.setCREATE_DATE(rs.getDate(7));
		assistantInfo.setUPDATE_USER(rs.getString(8));
		assistantInfo.setENABLED(rs.getInt(9));
		return assistantInfo;
	}

}
