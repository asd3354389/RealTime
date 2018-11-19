package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.GetDepid;

public class GetDepidMapper implements RowMapper<GetDepid> {

	@Override
	public GetDepid mapRow(ResultSet rs, int arg1) throws SQLException {
		GetDepid depids=new GetDepid();
		//depid.setCostid(rs.getString("costid"));
		depids.setDepid(rs.getString("depid"));
		return depids;
	}

}
