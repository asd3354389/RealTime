package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.ProdAllLine;

public class QueryProdAllLine implements RowMapper<ProdAllLine>{

	@Override
	public ProdAllLine mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ProdAllLine proALine = new ProdAllLine();
		proALine.setRecordId(rs.getString("recordid"));
		proALine.setCostId(rs.getString("costid"));
		proALine.setShift(rs.getString("shift"));
		proALine.setTransition_Reason(rs.getString("transition_reason"));
		proALine.setProd_Out_Name(rs.getString("prod_out_name"));
		proALine.setProd_Out_Code(rs.getString("prod_out_code"));
		proALine.setTransition_Out_Number(rs.getString("transition_out_number"));
		proALine.setType_In(rs.getString("type_in"));
		proALine.setProd_In_Name(rs.getString("prod_in_name"));
		proALine.setProd_In_Code(rs.getString("prod_in_code"));
		proALine.setTransition_In_Number(rs.getString("transition_in_number"));
		proALine.setInsert_Date(rs.getString("insert_date"));
		return proALine;
	}

}
