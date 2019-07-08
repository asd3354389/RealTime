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
		searchInfo.setEmpId(rs.getString("empId"));
		searchInfo.setName(rs.getString("name"));
		searchInfo.setDepId(rs.getString("depId"));
		searchInfo.setCostId(rs.getString("costId"));
		searchInfo.setSwipeCardTime(rs.getString("swipeCardTime"));
		searchInfo.setSwipeCardIpAddress(rs.getString("swipeCardIpAddress"));
		searchInfo.setDeptid(rs.getString("deptid"));
		return searchInfo;
	}

}
