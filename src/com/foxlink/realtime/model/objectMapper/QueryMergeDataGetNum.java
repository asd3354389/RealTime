package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ThreeMergeOneGetNum;

public class QueryMergeDataGetNum implements RowMapper<ThreeMergeOneGetNum> {

	@Override
	public ThreeMergeOneGetNum mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ThreeMergeOneGetNum tmogn = new ThreeMergeOneGetNum();
		tmogn.setEMP_ID(rs.getString("emp_id"));
		tmogn.setName(rs.getString("name"));
		return tmogn;
	}

}
