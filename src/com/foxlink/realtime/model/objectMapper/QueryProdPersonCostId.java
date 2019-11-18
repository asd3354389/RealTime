package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.foxlink.realtime.model.ProdPersonCostId;

public class QueryProdPersonCostId implements RowMapper<ProdPersonCostId>{
	@Override
	public ProdPersonCostId mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		ProdPersonCostId exce = new ProdPersonCostId();
		
		exce.setCOSTID(rs.getString("COSTID"));
		return exce;
	}
}
