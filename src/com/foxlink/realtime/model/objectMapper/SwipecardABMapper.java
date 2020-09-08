package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.SwipecardAB;

public class SwipecardABMapper implements RowMapper<SwipecardAB>{

	@Override
	public SwipecardAB mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SwipecardAB result = new SwipecardAB();
		result.setId(rs.getString(1));
		result.setName(rs.getString(2));
		result.setCostid(rs.getString(3));
		result.setDeptid(rs.getString(4));
		result.setDepid(rs.getString(5));
		result.setDepname(rs.getString(6));
		result.setOvertime15(rs.getString(7));
		result.setMorethen7(rs.getString(8));
		result.setOutwork15(rs.getString(9));
		result.setCount(rs.getInt(10));
		return result;
	}

}
