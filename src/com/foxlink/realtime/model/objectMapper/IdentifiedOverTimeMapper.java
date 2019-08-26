package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.IdentifiedOverTime;

public class IdentifiedOverTimeMapper implements RowMapper<IdentifiedOverTime> {
	@Override
	public IdentifiedOverTime mapRow(ResultSet rs, int arg1) throws SQLException {
		IdentifiedOverTime overTime=new IdentifiedOverTime();
		overTime.setId(rs.getString("id"));
		overTime.setName(rs.getString("Name"));
		overTime.setDepID(rs.getString("depid"));
		overTime.setCostID(rs.getString("costid"));
		overTime.setDirect(rs.getString("direct"));
		overTime.setOverTimeDate(rs.getString("overtimeDate"));
		overTime.setOverTimeInterval(rs.getString("overtimeInterval"));
		overTime.setOverTimeHours(rs.getString("overtimeHours"));
		overTime.setOverTimeType(rs.getInt("overtimetype"));
		overTime.setNoteStates(rs.getInt("notesStates"));
		overTime.setReason(rs.getString("Reason"));
		overTime.setBonus(rs.getString("bonus"));
		return overTime;
	}

}
