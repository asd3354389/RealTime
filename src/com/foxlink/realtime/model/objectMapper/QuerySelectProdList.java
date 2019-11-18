package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.SelectProdList;




public class QuerySelectProdList implements RowMapper<SelectProdList>{
	@Override
	public SelectProdList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SelectProdList exce = new SelectProdList();
		exce.setRECORDID(rs.getString("RECORDID"));
		exce.setPLAN_DATE_YEAR(rs.getString("PLAN_DATE_YEAR"));
	    exce.setSHIFT(rs.getString("SHIFT"));
		exce.setDEPID(rs.getString("DEPID"));
		exce.setPROD_NAME(rs.getString("PROD_NAME"));
		exce.setPROD_CODE(rs.getString("PROD_CODE"));
		exce.setNUMBER_OF_PEOPLE(rs.getString("NUMBER_OF_PEOPLE"));
		exce.setPLAN_DATE_WEEK(rs.getString("PLAN_DATE_WEEK"));
		return exce;
	}
}
