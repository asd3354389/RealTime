package com.foxlink.realtime.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.foxlink.realtime.model.EmpInOTPendingSheet;
import com.foxlink.realtime.model.OTHourConfirm;
import com.foxlink.realtime.model.OverTimePending;
import com.foxlink.realtime.model.OverTimeSheet;
import com.foxlink.realtime.model.objectMapper.OTSheetMapper;
import com.foxlink.realtime.model.objectMapper.OTSheetNoRCMapper;
import com.foxlink.realtime.util.CommonUtils;
import com.google.gson.JsonElement;

import oracle.jdbc.OracleTypes;

public class OTDAO extends DAO<OverTimeSheet> {
	private static Logger logger=Logger.getLogger(OTDAO.class);
	
	public OTDAO() {
		super();
	}
	
	public List<OverTimeSheet> FindAllRecords(int[] checkState,String RCNO,String WorkshopNO,String LineNo,
			Date StartDate,Date EndDate,String AssistantID,boolean isAbnormal){
		List<OverTimeSheet> OTSheets=null;
		String ProcedureName="";
		SimpleJdbcCall call=null;
		Map<String,Object> executeResult=null;
		try {
			if(isAbnormal) {
				//忘卡情況
				if (checkState[0]==0 && checkState[1]==9) {
					//未審核
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_ABN_NORC_PENDING_LIST";
					}
					else {
						ProcedureName="SHOW_ABN_RC_PENDING_LIST";
					}
				}
				else {
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_ABN_NORC_IDENTIFIED_LIST";
					}
					else {
						ProcedureName="SHOW_ABN_RC_IDENTIFIED_LIST";
					}
				}
			}
			else {
				//正常情況
				if (checkState[0]==0 && checkState[1]==9) {
					//未審核
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_NO_RCNO_PENDING_LIST";
					}
					else {
						ProcedureName="SHOW_RCNO_PENDING_LIST";
					}
				}
				else {
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_NO_RCNO_IDENTIFIED_LIST";
					}
					else {
						ProcedureName="SHOW_RCNO_IDENTIFIED_LIST";
					}
				}
			}
			
			if(RCNO.isEmpty() || RCNO==null) {
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName(ProcedureName).declareParameters(
						new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
						new SqlParameter("VSDate",OracleTypes.DATE),
						new SqlParameter("VEDate",OracleTypes.DATE),
						new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
						new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
						new SqlOutParameter("IDENTIFIED_LIST_CURSOR",OracleTypes.CURSOR,new OTSheetNoRCMapper()));
			}
			else {
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName(ProcedureName).declareParameters(
						new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
						new SqlParameter("VSDate",OracleTypes.DATE),
						new SqlParameter("VEDate",OracleTypes.DATE),
						new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
						new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
						new SqlOutParameter("IDENTIFIED_LIST_CURSOR",OracleTypes.CURSOR,new OTSheetMapper()));
			}
			
			System.out.println(WorkshopNO+"----"+StartDate+"----"+EndDate+"----"+AssistantID+"----"+LineNo);
			MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNO)
					.addValue("VSDate", StartDate)
					.addValue("VEDate", EndDate)
					.addValue("VASSISTANT_ID", AssistantID)
					.addValue("VLINE_ID", LineNo);
			executeResult=call.execute(paramMap);
			OTSheets=(List<OverTimeSheet>) executeResult.get("IDENTIFIED_LIST_CURSOR");
		}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed",ex);
		}
		return OTSheets;
	}
			
	/*做Employee的加班時數審核*/
	public boolean ConfirmEmpOTInfos(OTHourConfirm otHourConfirm,int isAbNormal) {
		boolean isConfirmed=false;
		SimpleJdbcCall call=null;
		Map<String, Object> executeResult=null;
		try {
			call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("CAL_PENDING_OVERTIME").
					declareParameters(new SqlParameter("VCLASS_NO",Types.VARCHAR),
							new SqlParameter("VRC_NO",Types.VARCHAR),
							new SqlParameter("VWORK_SHOP_NO",Types.VARCHAR),
							new SqlParameter("VSDate",Types.DATE),
							new SqlParameter("VCHECK_STATE",Types.NUMERIC),
							new SqlParameter("VASSISTANT_ID",Types.VARCHAR),
							new SqlParameter("VASSISTANT_ACCOUNT",Types.VARCHAR),
							new SqlParameter("VOT_TYPE",Types.NUMERIC),
							new SqlParameter("VOT_TYPE1",Types.NUMERIC),
							new SqlParameter("VITEM_NUMBER",Types.VARCHAR),
							new SqlParameter("VIS_ABNORMAL",Types.NUMERIC),
							new SqlParameter("VSELECTED_EMPIDS",Types.VARCHAR),
							new SqlParameter("VWORK_CONTENT",Types.VARCHAR),
							new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
							new SqlOutParameter("VEXECUTE_RESULT",OracleTypes.VARCHAR));
			MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VCLASS_NO", otHourConfirm.getClassNo())
					.addValue("VRC_NO", otHourConfirm.getRCNo())
					.addValue("VWORK_SHOP_NO", otHourConfirm.getWorkshopNo())
					.addValue("VSDate", new CommonUtils().ConvertString2SQLDate(otHourConfirm.getOverTimeDate()))
					.addValue("VCHECK_STATE", otHourConfirm.getCheckState())
					.addValue("VASSISTANT_ID",otHourConfirm.getAssistantID())
					.addValue("VASSISTANT_ACCOUNT",otHourConfirm.getAssistantAccount())
					.addValue("VOT_TYPE", otHourConfirm.getOverTimeType())
					.addValue("VOT_TYPE1", otHourConfirm.getOverTimeType1())
					.addValue("VITEM_NUMBER",otHourConfirm.getItemNumber())
					.addValue("VIS_ABNORMAL",isAbNormal)
					.addValue("VSELECTED_EMPIDS",otHourConfirm.getSelectedEmps())
					.addValue("VWORK_CONTENT",otHourConfirm.getWorkContent())
					.addValue("VLINE_ID", otHourConfirm.getLineNo());
			
			executeResult=call.execute(paramMap);
			
			if(executeResult.get("VEXECUTE_RESULT")==null || ((String)executeResult.get("VEXECUTE_RESULT")).isEmpty()) {
				isConfirmed=true;
			}
		}
		catch(Exception ex) {
			logger.error("ConfirmEmpOTInfos are failed",ex);
		}
		return isConfirmed;
	}
	
	/*做Employee的加班時數審核*/
	public boolean ConfirmEmpOTInfosByDepid(OTHourConfirm otHourConfirm,int isAbNormal) {
		boolean isConfirmed=false;
		SimpleJdbcCall call=null;
		Map<String, Object> executeResult=null;
		try {
			call=new SimpleJdbcCall(jdbcTemplate).withProcedureName("CAL_PENDING_OVERTIME_D").
					declareParameters(new SqlParameter("VCLASS_NO",Types.VARCHAR),
							new SqlParameter("VRC_NO",Types.VARCHAR),
							new SqlParameter("VWORK_SHOP_NO",Types.VARCHAR),
							new SqlParameter("VSDate",Types.DATE),
							new SqlParameter("VCHECK_STATE",Types.NUMERIC),
							new SqlParameter("VASSISTANT_ID",Types.VARCHAR),
							new SqlParameter("VASSISTANT_ACCOUNT",Types.VARCHAR),
							new SqlParameter("VOT_TYPE",Types.NUMERIC),
							new SqlParameter("VOT_TYPE1",Types.NUMERIC),
							new SqlParameter("VITEM_NUMBER",Types.VARCHAR),
							new SqlParameter("VIS_ABNORMAL",Types.NUMERIC),
							new SqlParameter("VSELECTED_EMPIDS",Types.VARCHAR),
							new SqlParameter("VWORK_CONTENT",Types.VARCHAR),
							new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
							new SqlOutParameter("VEXECUTE_RESULT",OracleTypes.VARCHAR));
			MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VCLASS_NO", otHourConfirm.getClassNo())
					.addValue("VRC_NO", otHourConfirm.getRCNo())
					.addValue("VWORK_SHOP_NO", otHourConfirm.getWorkshopNo())
					.addValue("VSDate", new CommonUtils().ConvertString2SQLDate(otHourConfirm.getOverTimeDate()))
					.addValue("VCHECK_STATE", otHourConfirm.getCheckState())
					.addValue("VASSISTANT_ID",otHourConfirm.getAssistantID())
					.addValue("VASSISTANT_ACCOUNT",otHourConfirm.getAssistantAccount())
					.addValue("VOT_TYPE", otHourConfirm.getOverTimeType())
					.addValue("VOT_TYPE1", otHourConfirm.getOverTimeType1())
					.addValue("VITEM_NUMBER",otHourConfirm.getItemNumber())
					.addValue("VIS_ABNORMAL",isAbNormal)
					.addValue("VSELECTED_EMPIDS",otHourConfirm.getSelectedEmps())
					.addValue("VWORK_CONTENT",otHourConfirm.getWorkContent())
					.addValue("VLINE_ID", otHourConfirm.getLineNo());
			
			executeResult=call.execute(paramMap);
			
			if(executeResult.get("VEXECUTE_RESULT")==null || ((String)executeResult.get("VEXECUTE_RESULT")).isEmpty()) {
				isConfirmed=true;
			}
		}
		catch(Exception ex) {
			logger.error("ConfirmEmpOTInfos are failed",ex);
		}
		return isConfirmed;
	}

	@Override
	public boolean AddNewRecord(OverTimeSheet newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(OverTimeSheet updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<OverTimeSheet> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OverTimeSheet> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
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
	public List<OverTimeSheet> FindRecords(OverTimeSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OverTimeSheet> FindRecord(String userDataCostId, int currentPage, int totalRecord, OverTimeSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, OverTimeSheet t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<OverTimeSheet> FindAllRecordsByDepid(int[] checkState,String RCNO,String WorkshopNO,String LineNo,
			Date StartDate,Date EndDate,String AssistantID,boolean isAbnormal) {
		List<OverTimeSheet> OTSheets=null;
		String ProcedureName="";
		SimpleJdbcCall call=null;
		Map<String,Object> executeResult=null;
		try {
			if(isAbnormal) {
				//忘卡情況
				if (checkState[0]==0 && checkState[1]==9) {
					//未審核
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_ABN_NORC_PENDING_LIST_D";
						System.out.println("--忘卡--未审核--无指示单--");
					}
					else {
						ProcedureName="SHOW_ABN_RC_PENDING_LIST_D";
						System.out.println("--忘卡--未审核--有指示单--");
					}
				}
				else {
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_ABN_NORC_IDENTIFIED_LISTD";
						System.out.println("--忘卡--已审核--无指示单--");
					}
					else {
						ProcedureName="SHOW_ABN_RC_IDENTIFIED_LIST_D";
						System.out.println("--忘卡--已审核--有指示单--");
					}
				}
			}
			else {
				//正常情況
				if (checkState[0]==0 && checkState[1]==9) {
					//未審核
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_NO_RCNO_PENDING_LIST_D";
						System.out.println("--正常刷卡--未审核--无指示单--");
					}
					else {
						ProcedureName="SHOW_RCNO_PENDING_LIST_D";
						System.out.println("--正常刷卡--未审核--有指示单--");
					}
				}
				else {
					if(RCNO.isEmpty() || RCNO ==null) {
						ProcedureName="SHOW_NO_RCNO_IDENTIFIED_LIST_D";
						System.out.println("--正常刷卡--已审核--无指示单--");
					}
					else {
						ProcedureName="SHOW_RCNO_IDENTIFIED_LIST_D";
						System.out.println("--正常刷卡--已审核--有指示单--");
					}
				}
			}
			if(RCNO.isEmpty() || RCNO==null) {
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName(ProcedureName).declareParameters(
						new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
						new SqlParameter("VSDate",OracleTypes.DATE),
						new SqlParameter("VEDate",OracleTypes.DATE),
						new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
						new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
						new SqlOutParameter("IDENTIFIED_LIST_CURSOR",OracleTypes.CURSOR,new OTSheetNoRCMapper()));
			}
			else {
				call=new SimpleJdbcCall(jdbcTemplate).withProcedureName(ProcedureName).declareParameters(
						new SqlParameter("VWORK_SHOP_NO",OracleTypes.VARCHAR),
						new SqlParameter("VSDate",OracleTypes.DATE),
						new SqlParameter("VEDate",OracleTypes.DATE),
						new SqlParameter("VASSISTANT_ID",OracleTypes.VARCHAR),
						new SqlParameter("VLINE_ID",OracleTypes.VARCHAR),
						new SqlOutParameter("IDENTIFIED_LIST_CURSOR",OracleTypes.CURSOR,new OTSheetMapper()));
			}
			
			System.out.println(WorkshopNO+"---"+StartDate+"---"+EndDate+"---"+AssistantID+"---"+LineNo);
			MapSqlParameterSource paramMap=new MapSqlParameterSource().addValue("VWORK_SHOP_NO", WorkshopNO)
					.addValue("VSDate", StartDate)
					.addValue("VEDate", EndDate)
					.addValue("VASSISTANT_ID", AssistantID)
					.addValue("VLINE_ID", LineNo);
			executeResult=call.execute(paramMap);
			OTSheets=(List<OverTimeSheet>) executeResult.get("IDENTIFIED_LIST_CURSOR");
			}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed",ex);
		}
		return OTSheets;
	}

	public boolean checkDeptIdExistence(String deptid) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from SWIPE.BONUS_DEPT where deptid =? and Modify_Allowed='Y'";
    	try {      	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { deptid },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}

	public List<String> FindAllRecordsByDepid() {
		// TODO Auto-generated method stub
		List<String>AllDept = null;
		String sSQL="select deptid from SWIPE.BONUS_DEPT WHERE Modify_Allowed='Y'";
		try {
			AllDept=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			  logger.error("Find BonusDeptid TotalRecord are failed ",ex);
    		  ex.printStackTrace();
		}
		return AllDept;
	}

	public int updateBonus(String updateUser, OverTimePending[] overTimePending) {
		// TODO Auto-generated method stub
		String sSQL = "Update SWIPE.NOTES_OVERTIME_STATE SET Bonus = ? where ID = ? and OverTimeDate = ?";
		int result = 0;
		try {
			jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, overTimePending[i].getBonus());
					ps.setString(2, overTimePending[i].getID());
					ps.setString(3, overTimePending[i].getOverTimeDate());
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return overTimePending.length;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改頂崗津貼失敗，原因："+e);
			e.printStackTrace();
			result=1;
		}
		return result;
	}

	public List<String> checkModifyEmpA(String[] empList) {
		// TODO Auto-generated method stub
		List<String>AllDept = null;
		String sSQL="select a.id from csr_employee a,BONUS_DEPT b "
				+ "where (a.deptid = b.deptid or a.costid = b.costid) and a.isonwork = '0' and b.bonus_rule='A' and b.modify_allowed = 'Y'"
				+ " and  a.id in (";
		try {
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < empList.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(empList[i]).append("'");
			}
			sSQL+=idsStr+")";
			AllDept=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			  logger.error("Find BonusDeptid TotalRecord are failed ",ex);
    		  ex.printStackTrace();
		}
		return AllDept;
	}

	public List<String> checkModifyEmpB(String[] empList) {
		// TODO Auto-generated method stub
		List<String>AllDept = null;
		String sSQL="select a.id from csr_employee a,BONUS_DEPT b "
				+ "where (a.deptid = b.deptid or a.costid = b.costid) and a.isonwork = '0' and b.bonus_rule='B' and b.modify_allowed = 'Y'"
				+ " and  a.id in (";
		try {
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < empList.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(empList[i]).append("'");
			}
			sSQL+=idsStr+")";
			AllDept=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			  logger.error("Find BonusDeptid TotalRecord are failed ",ex);
    		  ex.printStackTrace();
		}
		return AllDept;
	}
}
