package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.Prod;
import com.foxlink.realtime.model.ProdAllLine;
import com.foxlink.realtime.model.objectMapper.GetDepidMapper;
import com.foxlink.realtime.model.objectMapper.QueryManPowerStatus;
import com.foxlink.realtime.model.objectMapper.QueryProd;
import com.foxlink.realtime.model.objectMapper.QueryProdAllLine;
import com.foxlink.realtime.model.objectMapper.QueryProdCountDetail;
import com.foxlink.realtime.model.objectMapper.QueryProdDepidDetail;

public class ProdAllLineDAO extends DAO<ProdAllLine> {
	private static Logger logger = Logger.getLogger(ProdAllLineDAO.class);

	public List<ProdAllLine> SupportMachine(String costId, String sDate) {
		// TODO Auto-generated method stub
		List<ProdAllLine> SMachine = null;
		String sSQl = "Select recordid,costid,shift,transition_reason,prod_out_name,prod_out_code,transition_out_number,type_in,prod_in_name,prod_in_code,transition_in_number,insert_date FROM Swipe.MANPOWER_TRANSITION where CostId=? and to_char(Insert_Date,'yyyy-mm-dd')=?";
		try {
			SMachine = jdbcTemplate.query(sSQl, new Object[] {costId,sDate},new QueryProdAllLine());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find SupportMachine TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return SMachine;
	}

	@Override
	public boolean AddNewRecord(ProdAllLine newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ProdAllLine updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProdAllLine> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindRecord(String userDataCostId, int currentPage, int totalRecord, ProdAllLine t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindRecords(ProdAllLine t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, ProdAllLine t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean DelSupportMachine(String recordid) {
		// TODO Auto-generated method stub
		int delRow=0;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "DELETE FROM Swipe.MANPOWER_TRANSITION WHERE recordid = ?";
		try {
			if(recordid!=null) {
				delRow=jdbcTemplate.update(sSQL, new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, recordid);
						
					}
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("delete SupportMachine is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(delRow > 0) 
				   return true; 
				else
				   return false;
	}

	public int AddSupportMachine(ProdAllLine prodAllLine) {
		// TODO Auto-generated method stub
		int AddRow=0;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "INSERT INTO Swipe.MANPOWER_TRANSITION(recordid,costid,shift,transition_reason,prod_out_name,prod_out_code,transition_out_number,type_in,prod_in_name,prod_in_code,transition_in_number,insert_date)"
				+ "Values(?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		try {
			if(prodAllLine!=null) {
				AddRow=jdbcTemplate.update(sSQL, new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, prodAllLine.getRecordId());
						ps.setString(2, prodAllLine.getCostId());
						ps.setString(3, prodAllLine.getShift());
						ps.setString(4, prodAllLine.getTransition_Reason());
						ps.setString(5, prodAllLine.getProd_Out_Name());
						ps.setString(6, prodAllLine.getProd_Out_Code());
						ps.setString(7, prodAllLine.getTransition_Out_Number());
						ps.setString(8, prodAllLine.getType_In());
						ps.setString(9, prodAllLine.getProd_In_Name());
						ps.setString(10, prodAllLine.getProd_In_Code());
						ps.setString(11, prodAllLine.getTransition_In_Number());
					
					}
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Add SupportMachine is failed",ex);
			transactionManager.rollback(txStatus);
		}			
		
		return AddRow;
	}

	public List<ManPowerStatus> searchDepidNumList(String costId, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> depidNumList = null;
		String sSQl = "select count(0) as count,depid,class_no from Swipe.manpower_status where costID ='"+costId+"' and  depid like '%XR%'  and to_char(workdate,'yyyy-mm-dd') = '"+sDate+"' and status in ('0_0','0_1','8_0','9_0') group by depid,class_no order by depid,class_no desc";
		try {
			depidNumList = jdbcTemplate.query(sSQl, new QueryManPowerStatus());
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find depidNumList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return depidNumList;
	}

	public List<String> searchPeopleList(String costId, String sDate) {
		// TODO Auto-generated method stub
		List<String> peopleList = null;
		String sSQl = "select count(*) from Swipe.manpower_status where costID ='"+costId+"' and  depid like '%XR%' and to_char(workdate,'yyyy-mm-dd') ='"+sDate+"' order by depid";
		try {
			peopleList = jdbcTemplate.queryForList(sSQl, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find peopleList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return peopleList;
	}

	public List<ManPowerStatus> searchCountNumList(String costId, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> countNumList = null;
		String sSQl = "select count(0) as count,depid,class_no from Swipe.manpower_status where costID ='"+costId+"' and  depid like '%XR%'  and to_char(workdate,'yyyy-mm-dd') = '"+sDate+"' group by depid,class_no ";
		try {
			countNumList = jdbcTemplate.query(sSQl, new QueryManPowerStatus());
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find countNumList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return countNumList;
	}

	public List<Prod> searchDepidNumDetail(String costId, String sDate) {
		// TODO Auto-generated method stub
		List<Prod> depidNumDetail = null;
		String sSQl = "SELECT depid, shift, prod_name, number_of_people FROM Swipe.manpower_pro WHERE costid = '"+costId+"' AND to_char(plan_date,'yyyy-mm-dd') = '"+sDate+"' AND status = '0' order by depid,shift desc";
		try {
			depidNumDetail = jdbcTemplate.query(sSQl, new QueryProdDepidDetail());
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find depidNumDetail TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return depidNumDetail;
	}

	public List<Prod> searchCountNumDetail(String costId, String sDate) {
		// TODO Auto-generated method stub
		List<Prod> countNumDetail = null;
		String sSQl = "SELECT shift,sum(number_of_people) as sum_of_people FROM Swipe.manpower_pro WHERE costid ='"+costId+"' AND to_char(plan_date,'yyyy-mm-dd') = '"+sDate+"' AND status = '0' group by shift order by shift desc";
		try {
			countNumDetail = jdbcTemplate.query(sSQl, new QueryProdCountDetail());
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find countNumDetail TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return countNumDetail;
	}
}
