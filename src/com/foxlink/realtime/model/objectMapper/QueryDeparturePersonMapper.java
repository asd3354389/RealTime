package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.DeparturePerson;

public class QueryDeparturePersonMapper implements RowMapper<DeparturePerson>{
	@Override
	public DeparturePerson mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		DeparturePerson departurePerson = new DeparturePerson();	
		departurePerson.setEmp_id(rs.getString("emp_id"));
		departurePerson.setName(rs.getString("name"));
		return departurePerson;
	}
}
