package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.Holiday;

public class HolidaysMapper implements RowMapper<Holiday>{

	@Override
	public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Holiday holiday = new Holiday();
		holiday.setHolidayDate(rs.getString("holiday_date"));
		holiday.setHolidayType(rs.getString("holiday_type"));
		return holiday;
	}

	

}
