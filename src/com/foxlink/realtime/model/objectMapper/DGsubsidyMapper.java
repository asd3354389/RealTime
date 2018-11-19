package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.DGsubsidy;

public class DGsubsidyMapper  implements RowMapper<DGsubsidy> {
	@Override
	public DGsubsidy mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		DGsubsidy dgsubsidy =new DGsubsidy();
		dgsubsidy.setEmpId(rs.getString(1));
		dgsubsidy.setName(rs.getString(2));
		dgsubsidy.setDepId(rs.getString(3));
		dgsubsidy.setCostId(rs.getString(4));
		dgsubsidy.setSwipeCardTime(rs.getString(5));
		dgsubsidy.setSwipeCardTime2(rs.getString(6));
		dgsubsidy.setClassStart(rs.getString(7));
		return dgsubsidy;
	}
}
