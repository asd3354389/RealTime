package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Emp;

public class EmpInfoMapper implements RowMapper<Emp>{

	@Override
	public Emp mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Emp emp = new Emp();
		emp.setEmpNo(rs.getString("id"));
		emp.setEmpName(rs.getString("name"));
		emp.setDeptNo(rs.getString("depid"));
		emp.setCostID(rs.getString("costid"));
		emp.setDeptid(rs.getString("deptid"));
		emp.setCardID(rs.getString("cardid"));
		emp.setUpdateDate(rs.getString("updatedate"));
		// TODO Auto-generated method stub
		return emp;
	}

}
