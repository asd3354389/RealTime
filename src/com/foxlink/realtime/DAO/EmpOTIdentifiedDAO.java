package com.foxlink.realtime.DAO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.foxlink.realtime.model.EmpInOTIdentifiedSheet;
import com.foxlink.realtime.model.objectMapper.IdentifiedOverTimeMapper;
import oracle.jdbc.internal.OracleTypes;

public class EmpOTIdentifiedDAO extends DAO<EmpInOTIdentifiedSheet> {
	private static Logger logger=Logger.getLogger(EmpOTIdentifiedDAO.class);
	
	public EmpOTIdentifiedDAO() {
		super();
	}

	@Override
	public boolean AddNewRecord(EmpInOTIdentifiedSheet newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpInOTIdentifiedSheet updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
		
	public List<EmpInOTIdentifiedSheet> FindAllRecords(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String ClassNo,Date OTDate,boolean isAbnormal){
		List<EmpInOTIdentifiedSheet> IdentifiedEmpList=null;
		SimpleJdbcCall call=null;
		String procedureName="";
		Map<String,Object> executeResult=null;
		try {
			if(isAbnormal) {
				//異常（無上刷or無下刷）
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_ABN_IDENTIFIED_OVERTIME").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VASSISTANT_ACCOUNT",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSDate",OracleTypes.DATE),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("IDENTIFIED_OVERTIME_CURSOR",OracleTypes.CURSOR,new IdentifiedOverTimeMapper()));
				
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VASSISTANT_ACCOUNT", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VSDate", OTDate)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
			}
			else {
				//正常情況
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_IDENTIFIED_OVERTIME").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VOVERTIMEDATE",OracleTypes.DATE),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("IDENTIFIED_OVERTIME_CURSOR",OracleTypes.CURSOR,new IdentifiedOverTimeMapper()));
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VASSISTANT_ID", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VOVERTIMEDATE", OTDate)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);	
			}
			IdentifiedEmpList=(List<EmpInOTIdentifiedSheet>) executeResult.get("IDENTIFIED_OVERTIME_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed",ex);
		}
		return IdentifiedEmpList;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
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
	public List<EmpInOTIdentifiedSheet> FindRecords(EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<EmpInOTIdentifiedSheet> FindAllRecordsByDepid(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String ClassNo,Date OTDate,boolean isAbnormal) {
		List<EmpInOTIdentifiedSheet> IdentifiedEmpList=null;
		SimpleJdbcCall call=null;
		String procedureName="";
		Map<String,Object> executeResult=null;
		try {
			if(isAbnormal) {
				//異常（無上刷or無下刷）
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_ABN_IDENTIFIED_OVERTIME_D").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VASSISTANT_ACCOUNT",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSDate",OracleTypes.DATE),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("IDENTIFIED_OVERTIME_CURSOR",OracleTypes.CURSOR,new IdentifiedOverTimeMapper()));
				
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VASSISTANT_ACCOUNT", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VSDate", OTDate)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
			}
			else {
				//正常情況
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_IDENTIFIED_OVERTIME_D").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VOVERTIMEDATE",OracleTypes.DATE),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("IDENTIFIED_OVERTIME_CURSOR",OracleTypes.CURSOR,new IdentifiedOverTimeMapper()));
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VASSISTANT_ID", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VOVERTIMEDATE", OTDate)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);	
			}
			IdentifiedEmpList=(List<EmpInOTIdentifiedSheet>) executeResult.get("IDENTIFIED_OVERTIME_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecordsByDepid is failed",ex);
		}
		return IdentifiedEmpList;
	}


}
