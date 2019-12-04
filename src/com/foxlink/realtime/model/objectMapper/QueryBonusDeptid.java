package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.BonusDeptid;

public class QueryBonusDeptid implements RowMapper<BonusDeptid> {

	@Override
	public BonusDeptid mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BonusDeptid bd = new BonusDeptid();
		bd.setDeptid(rs.getString("deptid"));
		bd.setBonus_Rule(rs.getString("bonus_rule"));
		bd.setModify_Allowed(rs.getString("modify_allowed"));
		return bd;
	}

}
