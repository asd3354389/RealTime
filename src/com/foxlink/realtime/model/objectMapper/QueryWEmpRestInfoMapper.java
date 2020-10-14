package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.WorkshopEmpRestInfo;

public class QueryWEmpRestInfoMapper implements RowMapper<WorkshopEmpRestInfo>{
	@Override
	public WorkshopEmpRestInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		WorkshopEmpRestInfo workshopNoRestInfo = new WorkshopEmpRestInfo();
		workshopNoRestInfo.setEMP_ID(rs.getString("EMP_ID"));
		workshopNoRestInfo.setCLASS_NO(rs.getString("CLASS_NO"));
		workshopNoRestInfo.setREST_START1(rs.getString("REST_START1"));
		workshopNoRestInfo.setREST_END1(rs.getString("REST_END1"));
		workshopNoRestInfo.setREST_START2(rs.getString("REST_START2"));
		workshopNoRestInfo.setREST_END2(rs.getString("REST_END2"));
		return workshopNoRestInfo;
	}
}
