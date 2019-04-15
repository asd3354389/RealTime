package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.Employee;


public class QueryEmpyloeeMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Employee emp = new Employee();
		emp.setEmpNo(rs.getString("id"));
		emp.setEmpName(rs.getString("name"));
		emp.setDeptNo(rs.getString("depid"));
		emp.setCostID(rs.getString("costid"));
		emp.setJob_Title(rs.getString("Job_Title"));
		emp.setJob_Name(rs.getString("Job_Name"));
		emp.setLine_Personnel(rs.getString("Line_Personnel"));
		// TODO Auto-generated method stub
		return emp;
	}

}
