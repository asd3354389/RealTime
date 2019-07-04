package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ClassNoRestInfo;

public class QueryCRestInfoMapper implements RowMapper<ClassNoRestInfo> {

	@Override
	public ClassNoRestInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ClassNoRestInfo classNoRestInfo = new ClassNoRestInfo();
		classNoRestInfo.setCostId(rs.getString("COSTID"));
		classNoRestInfo.setClass_No(rs.getString("CLASS_NO"));
		classNoRestInfo.setRest_Start_F(rs.getString("SUB_REST_START1"));
		classNoRestInfo.setRest_End_F(rs.getString("SUB_REST_END1"));
		classNoRestInfo.setRest_Start_S(rs.getString("SUB_REST_START2"));
		classNoRestInfo.setRest_End_S(rs.getString("SUB_REST_END2"));
		return classNoRestInfo;
	}

}
