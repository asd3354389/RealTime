package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Prod;

public class QueryProdDepid implements RowMapper<Prod> {


	@Override
	public Prod mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Prod prod = new Prod();
		prod.setDepid(rs.getString("depid"));
		prod.setShift(rs.getString("shift"));
		prod.setNumber_Of_People(rs.getString("number_of_people"));
		return prod;
	}

}
