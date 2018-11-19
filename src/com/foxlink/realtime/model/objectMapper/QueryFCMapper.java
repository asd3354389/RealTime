package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryForgetCard;

public class QueryFCMapper implements RowMapper<QueryForgetCard> {

	@Override
	public QueryForgetCard mapRow(ResultSet rs, int arg1) throws SQLException {
		QueryForgetCard queryForgetCard=new QueryForgetCard();
		queryForgetCard.setCostid(rs.getString("costid"));
		queryForgetCard.setFcDate(rs.getString("FcDate"));
		//queryForgetCard.setStartDate(rs.getString("startDate"));
		queryForgetCard.setId(rs.getString("id"));
		queryForgetCard.setDepid(rs.getString("depid"));
		queryForgetCard.setName(rs.getString("name"));
		queryForgetCard.setIsonwork(rs.getString("isonwork"));
		return queryForgetCard;
	}

	

}
