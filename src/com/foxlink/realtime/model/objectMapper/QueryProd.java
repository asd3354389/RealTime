package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Prod;

public class QueryProd implements RowMapper<Prod> {

	@Override
	public Prod mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Prod prod = new Prod();
		prod.setRecordId(rs.getString("recordid"));
		prod.setCostId(rs.getString("costid"));
		prod.setDepid(rs.getString("depid"));
		prod.setShift(rs.getString("shift"));
		prod.setProd_Name(rs.getString("prod_name"));
		prod.setProd_Code(rs.getString("prod_code"));
		prod.setDepid_Go(rs.getString("depid_go"));
		prod.setReason(rs.getString("reason"));
		prod.setNumber_Of_People(rs.getString("number_of_people"));
//		prod.setWorkDate(rs.getString("workdate"));
		return prod;
	}

}
