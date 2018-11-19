package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.SearchRawRecordInfo;

public class SearchRawRecordInfoMapper implements RowMapper<SearchRawRecordInfo>{
	@Override
	public SearchRawRecordInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SearchRawRecordInfo searchInfo=new SearchRawRecordInfo();
		searchInfo.setEmpId(rs.getString(1));
		searchInfo.setName(rs.getString(2));
		searchInfo.setDepId(rs.getString(3));
		searchInfo.setCostId(rs.getString(4));
		searchInfo.setSwipeCardTime(rs.getString(5));
		searchInfo.setSwipeCardIpAddress(rs.getString(6));
		return searchInfo;
	}

}
