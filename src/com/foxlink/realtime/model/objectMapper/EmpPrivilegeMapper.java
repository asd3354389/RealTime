package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.EmpPrivilege;

public class EmpPrivilegeMapper implements RowMapper<EmpPrivilege>{

	@Override
	public EmpPrivilege mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		EmpPrivilege empPrivilege = new EmpPrivilege();
		empPrivilege.setId(rs.getString("id"));
		empPrivilege.setPrivilege_Level(rs.getString("privilege_level"));
		return empPrivilege;
	}

}
