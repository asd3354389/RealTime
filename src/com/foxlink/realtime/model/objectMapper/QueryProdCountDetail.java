package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Prod;

public class QueryProdCountDetail implements RowMapper<Prod> {


	@Override
	public Prod mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Prod prod = new Prod();
		prod.setShift(rs.getString("shift"));
		prod.setSum_Of_People(rs.getInt("sum_of_people"));
		return prod;
	}

}
