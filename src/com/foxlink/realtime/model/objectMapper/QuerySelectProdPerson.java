package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.SelectProdPerson;


public class QuerySelectProdPerson implements RowMapper<SelectProdPerson>{
	@Override
	public SelectProdPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SelectProdPerson exce = new SelectProdPerson();
		//exce.setRECORDID(rs.getString("RECORDID"));
		//exce.setRECORDID(rs.getString("RECORDID"));
		exce.setPLAN_DATE_YEAR(rs.getString("PLAN_DATE_YEAR"));
		//exce.setSHIFT(rs.getString("SHIFT"));
		exce.setDEPID(rs.getString("DEPID"));
		//exce.setPROD_NAME(rs.getString("PROD_NAME"));
		//exce.setPROD_CODE(rs.getString("PROD_CODE"));
		//exce.setNUMBER_OF_PEOPLE(rs.getString("NUMBER_OF_PEOPLE"));
		exce.setPLAN_DATE_WEEK(rs.getString("PLAN_DATE_WEEK"));
		exce.setCOSTID(rs.getString("COSTID"));
		return exce;
	}
}
