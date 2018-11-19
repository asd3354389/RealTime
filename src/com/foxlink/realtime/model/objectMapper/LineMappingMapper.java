package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.LineMapping;

public class LineMappingMapper implements RowMapper<LineMapping>{

	@Override
	public LineMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		LineMapping lineMapping = new LineMapping();
		lineMapping.setORG_NAME(rs.getString("ORG_NAME"));
		lineMapping.setMES_PDLINE_ID(rs.getInt("MES_PDLINE_ID"));
		lineMapping.setMES_PDLINE_NAME(rs.getString("MES_PDLINE_NAME"));
		lineMapping.setMES_PDLINE_DESC(rs.getString("MES_PDLINE_DESC"));
		lineMapping.setRT_LINENO(rs.getString("RT_LINENO"));
		lineMapping.setSTD_MAN_POWER(rs.getInt("STD_MAN_POWER"));
		
		
		return lineMapping;
	}

}
