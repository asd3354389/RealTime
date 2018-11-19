package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QuerySwipeCard;

public class QuerySCMapper implements RowMapper<QuerySwipeCard> {

	@Override
	public QuerySwipeCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuerySwipeCard querySwipeCard=new QuerySwipeCard();
		querySwipeCard.setId(rs.getString("id"));
		//querySwipeCard.setEmp_id(rs.getString("emp_id"));
		querySwipeCard.setDepid(rs.getString("depid"));
		querySwipeCard.setName(rs.getString("name"));
		querySwipeCard.setCostid(rs.getString("costid"));
		querySwipeCard.setSwipeCardTime(rs.getString("SwipeCardTime"));
		querySwipeCard.setSwipeCardTime2(rs.getString("SwipeCardTime2"));
		querySwipeCard.setWorkshopNo(rs.getString("WorkshopNo"));
		querySwipeCard.setSwipe_date(rs.getString("swipe_date"));
		/*querySwipeCard.setTimeStart(rs.getString("TimeStart"));
		querySwipeCard.setTimeEnd(rs.getString("TimeEnd"));*/
		return querySwipeCard;
	}

	

}
