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

public class IdentifiedAbnOverTimeDAO extends DAO<IdentifiedOverTime> {
	private static Logger logger=Logger.getLogger(IdentifiedAbnOverTimeDAO.class);
	
	public IdentifiedAbnOverTimeDAO() {
		super();
	}

	@Override
	public List<IdentifiedOverTime> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<IdentifiedOverTime> FindAllRecords(String RCNO,String ClassNO,String CostID,String WorkShopNO,Date QueryDate){
		SimpleJdbcCall call=null;
		Map<String, Object> executeResult=null;
		List<IdentifiedOverTime> identifiedAbnormalOverTimeRecords=null;
		try {
			call=new SimpleJdbcCall(jdbcTemplate).
					withProcedureName("SHOW_ABN_IDENTIFIED_OVERTIME").
					declareParameters(new SqlParameter("VSDate",Types.DATE),
							new SqlParameter("VRC_NO",Types.VARCHAR),
							new SqlParameter("VCOST_ID",Types.VARCHAR),
							new SqlParameter("VCLASS_NO",Types.VARCHAR),
							new SqlParameter("VWORK_SHOP_NO ",Types.VARCHAR),
							new SqlOutParameter("ABNORMAL_OVERTIME_CURSOR",OracleTypes.CURSOR,new IdentifiedOverTimeMapper()));
			executeResult=call.execute(new MapSqlParameterSource("VSDate",QueryDate),
					new MapSqlParameterSource("VRC_NO",RCNO),
					new MapSqlParameterSource("VCOST_ID",CostID),
					new MapSqlParameterSource("VCLASS_NO",ClassNO));
			identifiedAbnormalOverTimeRecords=(List<IdentifiedOverTime>) executeResult.get("ABNORMAL_OVERTIME_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecords(SHOW_ABN_IDENTIFIED_OVERTIME) is failed",ex);
		}
		return identifiedAbnormalOverTimeRecords;
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
