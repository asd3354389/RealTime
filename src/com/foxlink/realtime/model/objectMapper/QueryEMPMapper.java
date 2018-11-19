package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Emp;

public class QueryEMPMapper implements RowMapper<Emp>{

	@Override
	public Emp mapRow(ResultSet rs, int rowNum) throws SQLException {
		Emp emp = new Emp();
		emp.setEmpNo(rs.getString("id"));
		emp.setEmpName(rs.getString("name"));
		emp.setDeptNo(rs.getString("depid"));
		emp.setCostID(rs.getString("costid"));
		emp.setJob_Title(rs.getString("Job_Title"));
		emp.setJob_Name(rs.getString("Job_Name"));
		// TODO Auto-generated method stub
		return emp;
	}

}
