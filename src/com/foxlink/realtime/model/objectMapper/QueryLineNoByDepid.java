package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.LineNoByDepid;

public class QueryLineNoByDepid implements RowMapper<LineNoByDepid> {

	@Override
	public LineNoByDepid mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		LineNoByDepid lnbd = new LineNoByDepid();
		lnbd.setWorkShopNo(rs.getString("WorkShopNo"));
		lnbd.setLineNo(rs.getString("LineNo"));
		lnbd.setDepid(rs.getString("Depid"));
		return lnbd;
	}

}
