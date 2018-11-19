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
import com.foxlink.realtime.model.EmpInOTPendingSheet;
import com.foxlink.realtime.model.objectMapper.CalOvertimeMapper;
import com.foxlink.realtime.model.objectMapper.PendingOvertimeMapper;

import oracle.jdbc.internal.OracleTypes;

public class EmpOTPendingDAO extends DAO<EmpInOTPendingSheet> {
	private static Logger logger=Logger.getLogger(EmpOTPendingDAO.class);
	
	public EmpOTPendingDAO() {
		super();
	}
	
	@Override
	public boolean AddNewRecord(EmpInOTPendingSheet newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpInOTPendingSheet updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpInOTPendingSheet> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<EmpInOTPendingSheet> FindAllRecords(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String ClassNo,
			Date SwipeCardDate,boolean isAbnormal){
		List<EmpInOTPendingSheet> OTPendingEmps=null;
		Map<String,Object> executeResult=null;
		SimpleJdbcCall call=null;
		try {
			if(isAbnormal) {
				//異常狀況(忘卡)
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_ABN_PENDING_OVERTIME").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSDate",OracleTypes.DATE),
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VUNSELECT_EMPS",OracleTypes.VARCHAR),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("PENDING_OVERTIME_CURSOR",OracleTypes.CURSOR,new CalOvertimeMapper()));
				
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VSDate", SwipeCardDate)
						.addValue("VASSISTANT_ID", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VUNSELECT_EMPS", null)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
			}
			else {
				//正常情況
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_PENDING_OVERTIME").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSWIPECARDTIME",OracleTypes.DATE),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("PENDING_OVERTIME_CURSOR",OracleTypes.CURSOR,new PendingOvertimeMapper()));
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VASSISTANT_ID", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VSWIPECARDTIME", SwipeCardDate)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
			}
			OTPendingEmps=(List<EmpInOTPendingSheet>) executeResult.get("PENDING_OVERTIME_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed",ex);
		}
		return OTPendingEmps;
	}
	
	public List<EmpInOTPendingSheet> GetCalOverTime(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String AssistantId,String ClassNo,int CheckState,
			Date OverTimeDate,int OverTimeType,String ItemNumber,int isAbnormal){
		List<EmpInOTPendingSheet> CalOverTimeEmps=null;
		Map<String,Object> executeResult=null;
		SimpleJdbcCall call=null;
		try {

				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_OVERTIME_CAL_RESULTS").
						declareParameters(new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSDate",OracleTypes.DATE),
								new SqlParameter("VCHECK_STATE",OracleTypes.NUMBER),		
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),	
								new SqlParameter("VASSISTANT_ACCOUNT",OracleTypes.VARCHAR),	
								new SqlParameter("VOT_TYPE",OracleTypes.NUMBER),
								new SqlParameter("VITEM_NUMBER",OracleTypes.VARCHAR),
								new SqlParameter("VIS_ABNORMAL",OracleTypes.NUMBER),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("OVERTIME_CAL_RESULTS_CURSOR",OracleTypes.CURSOR,new CalOvertimeMapper()));
				
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VCLASS_NO", ClassNo)
						.addValue("VRC_NO", RCNo)					
						.addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VSDate", OverTimeDate)
						.addValue("VCHECK_STATE", CheckState)
						.addValue("VASSISTANT_ID", AssistantId)
						.addValue("VASSISTANT_ACCOUNT", AssistantAccount)
						.addValue("VOT_TYPE", OverTimeType)
						.addValue("VITEM_NUMBER", ItemNumber)
				        .addValue("VIS_ABNORMAL", isAbnormal)
				        .addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
				CalOverTimeEmps=(List<EmpInOTPendingSheet>) executeResult.get("OVERTIME_CAL_RESULTS_CURSOR");
		}
		catch(Exception ex) {
			logger.error("GetCalOverTime is failed",ex);
		}
		return CalOverTimeEmps;
	}

	@Override
	public List<EmpInOTPendingSheet> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		
		return null;
	}

	

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;

	}

	

	@Override
	public List<EmpInOTPendingSheet> FindRecords(EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpInOTPendingSheet> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<EmpInOTPendingSheet> FindAllRecordsByDepid(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String ClassNo,
			Date SwipeCardDate,boolean isAbnormal) {
		List<EmpInOTPendingSheet> OTPendingEmps=null;
		Map<String,Object> executeResult=null;
		SimpleJdbcCall call=null;
		try {
			if(isAbnormal) {
				//異常狀況(忘卡)
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_ABN_PENDING_OVERTIME_D").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSDate",OracleTypes.DATE),
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VUNSELECT_EMPS",OracleTypes.VARCHAR),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("PENDING_OVERTIME_CURSOR",OracleTypes.CURSOR,new CalOvertimeMapper()));
				
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VSDate", SwipeCardDate)
						.addValue("VASSISTANT_ID", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VUNSELECT_EMPS", null)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
			}
			else {
				//正常情況
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_PENDING_OVERTIME_D").
						declareParameters(new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
								new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSWIPECARDTIME",OracleTypes.DATE),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("PENDING_OVERTIME_CURSOR",OracleTypes.CURSOR,new PendingOvertimeMapper()));
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VRC_NO", RCNo)
						.addValue("VASSISTANT_ID", AssistantAccount)
						.addValue("VCLASS_NO", ClassNo)
						.addValue("VSWIPECARDTIME", SwipeCardDate)
						.addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
			}
			OTPendingEmps=(List<EmpInOTPendingSheet>) executeResult.get("PENDING_OVERTIME_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecordsByDepid is failed",ex);
		}
		return OTPendingEmps;
	}

	public List<EmpInOTPendingSheet> GetCalOverTimeByDepid(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String AssistantId,String ClassNo,int CheckState,
			Date OverTimeDate,int OverTimeType,String ItemNumber,int isAbnormal) {
		List<EmpInOTPendingSheet> CalOverTimeEmps=null;
		Map<String,Object> executeResult=null;
		SimpleJdbcCall call=null;
		try {

				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("SHOW_OVERTIME_CAL_RESULTS_D").
						declareParameters(new SqlParameter("VCLASS_NO",OracleTypes.VARCHAR),
								new SqlParameter("VRC_NO",OracleTypes.VARCHAR),
								new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
								new SqlParameter("VSDate",OracleTypes.DATE),
								new SqlParameter("VCHECK_STATE",OracleTypes.NUMBER),		
								new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),	
								new SqlParameter("VASSISTANT_ACCOUNT",OracleTypes.VARCHAR),	
								new SqlParameter("VOT_TYPE",OracleTypes.NUMBER),
								new SqlParameter("VITEM_NUMBER",OracleTypes.VARCHAR),
								new SqlParameter("VIS_ABNORMAL",OracleTypes.NUMBER),
								new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
								new SqlOutParameter("OVERTIME_CAL_RESULTS_CURSOR",OracleTypes.CURSOR,new CalOvertimeMapper()));
				
				MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VCLASS_NO", ClassNo)
						.addValue("VRC_NO", RCNo)					
						.addValue("VWORK_SHOP_NO", WorkshopNo)
						.addValue("VSDate", OverTimeDate)
						.addValue("VCHECK_STATE", CheckState)
						.addValue("VASSISTANT_ID", AssistantId)
						.addValue("VASSISTANT_ACCOUNT", AssistantAccount)
						.addValue("VOT_TYPE", OverTimeType)
						.addValue("VITEM_NUMBER", ItemNumber)
				        .addValue("VIS_ABNORMAL", isAbnormal)
				        .addValue("VLINE_ID", LineNo);
				executeResult=call.execute(paramMap);
				CalOverTimeEmps=(List<EmpInOTPendingSheet>) executeResult.get("OVERTIME_CAL_RESULTS_CURSOR");
		}
		catch(Exception ex) {
			logger.error("GetCalOverTime is failed",ex);
		}
		return CalOverTimeEmps;
	}

	
	

}
