package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.WorkshopNoRestInfo;

public class QueryWRestInfoMapper implements RowMapper<WorkshopNoRestInfo>{

	@Override
	public WorkshopNoRestInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		WorkshopNoRestInfo workshopNoRestInfo = new WorkshopNoRestInfo();
		workshopNoRestInfo.setWorkshopno(rs.getString("workshopno"));
		workshopNoRestInfo.setRest_start1(rs.getString("rest_start1"));
		workshopNoRestInfo.setRest_end1(rs.getString("rest_end1"));
		workshopNoRestInfo.setRest_start2(rs.getString("rest_start2"));
		workshopNoRestInfo.setRest_end2(rs.getString("rest_end2"));
		workshopNoRestInfo.setRest_start3(rs.getString("rest_start3"));
		workshopNoRestInfo.setRest_end3(rs.getString("rest_end3"));
		workshopNoRestInfo.setRest_start4(rs.getString("rest_start4"));
		workshopNoRestInfo.setRest_end4(rs.getString("rest_end4"));
		return workshopNoRestInfo;
	}

}
