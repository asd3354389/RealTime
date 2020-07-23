package com.foxlink.realtime.model.objectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.QueryDGsubsidyStatus;
public class QueryDGsubsidyStatusMapper implements RowMapper<QueryDGsubsidyStatus>{
   /* private String ID; //工號
	private String COSTID; //費用代碼
	private String NAME; //姓名
	private String DEPTID; //部門代碼
	private String DIRECT; //直間接
	private String SHIFT;  //班別
	private String OVERTIMEDATE; //日期
	private String BONUS;  //頂崗時數
*/
	@Override
	public QueryDGsubsidyStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		QueryDGsubsidyStatus queryStatus=new QueryDGsubsidyStatus();
		queryStatus.setID(rs.getString("ID"));
		queryStatus.setNAME(rs.getString("NAME"));
		queryStatus.setDEPTID(rs.getString("DEPTID"));
		queryStatus.setCOSTID(rs.getString("COSTID"));
		queryStatus.setDIRECT(rs.getString("DIRECT"));
		queryStatus.setOVERTIMEDATE(rs.getString("OVERTIMEDATE"));
		queryStatus.setSHIFT(rs.getString("SHIFT"));
		queryStatus.setBONUS(rs.getString("BONUS"));
		return queryStatus;
	}
}
