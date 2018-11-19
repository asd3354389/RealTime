package com.foxlink.realtime.DAO;

import java.sql.Date;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.foxlink.realtime.model.IdentifiedOverTime;
import com.foxlink.realtime.model.objectMapper.IdentifiedOverTimeMapper;

import oracle.jdbc.OracleTypes;


public class IdentifiedOverTimeDAO extends DAO<IdentifiedOverTime> {
	private static Logger logger=Logger.getLogger(IdentifiedOverTimeDAO.class);

	public IdentifiedOverTimeDAO() {
		super();
	}
	
	@Override
	public List<IdentifiedOverTime> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<IdentifiedOverTime> FindAllRecords(String WorkShopNO,String RCNO,String ClassNO,String CostID,Date QueryDate){
		SimpleJdbcCall call=null;
		Map<String, Object> executeResult=null;
		List<IdentifiedOverTime> identifiedOverTimeRecords=null;
		try {
			call=new SimpleJdbcCall(jdbcTemplate).
					withProcedureName("SHOW_IDENTIFIED_OVERTIME").
					declareParameters(new SqlParameter("VOVERTIMEDATE",Types.DATE),
							new SqlParameter("VRC_NO",Types.VARCHAR),
							new SqlParameter("VCOST_ID",Types.VARCHAR),
							new SqlParameter("VCLASS_NO",Types.VARCHAR),
							new SqlParameter("VWORK_SHOP_NO",Types.VARCHAR),
							new SqlOutParameter("IDENTIFIED_OVERTIME_CURSOR",OracleTypes.CURSOR,new IdentifiedOverTimeMapper()));
			executeResult=call.execute(new MapSqlParameterSource("VOVERTIMEDATE",QueryDate),
					new MapSqlParameterSource("VRC_NO",RCNO),
					new MapSqlParameterSource("VCOST_ID",CostID),
					new MapSqlParameterSource("VCLASS_NO",ClassNO),
					new MapSqlParameterSource("VWORK_SHOP_NO",WorkShopNO));
			identifiedOverTimeRecords=(List<IdentifiedOverTime>) executeResult.get("IDENTIFIED_OVERTIME_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecords(SHOW_IDENTIFIED_OVERTIME) is failed",ex);
		}
		return identifiedOverTimeRecords;
	}

	@Override
	public boolean AddNewRecord(IdentifiedOverTime newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(IdentifiedOverTime updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiedOverTime> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public List<IdentifiedOverTime> FindRecords(IdentifiedOverTime t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdentifiedOverTime> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			IdentifiedOverTime t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, IdentifiedOverTime t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
