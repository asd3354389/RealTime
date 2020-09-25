package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.AccessAB;

public class AccessABMapper implements RowMapper<AccessAB>{

	@Override
	public AccessAB mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		AccessAB accessAB = new AccessAB();
		accessAB.setId(rs.getString("id"));
		accessAB.setName(rs.getString("name"));
		accessAB.setCostid(rs.getString("costid"));
		accessAB.setDeptid(rs.getString("deptid"));
		accessAB.setDepid(rs.getString("depid"));
		accessAB.setDeptname(rs.getString("depname"));
		accessAB.setInterval(rs.getString("exception_interval"));
		accessAB.setTime(rs.getString("exception_time"));
		return accessAB;
	}

}
