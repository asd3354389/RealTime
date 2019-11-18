package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.foxlink.realtime.model.ChangeEmployee;
import com.foxlink.realtime.model.EmpChange;
import com.foxlink.realtime.model.objectMapper.QueryChangeEmployee;
import com.foxlink.realtime.model.objectMapper.QueryEmployee;




public class ChangeEmployeeDAO extends DAO<ChangeEmployeeDAO>{
	private static Logger logger = Logger.getLogger(ExceptionCostDAO.class);

	@Override
	public boolean AddNewRecord(ChangeEmployeeDAO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ChangeEmployeeDAO updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ChangeEmployeeDAO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeDAO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeDAO> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			ChangeEmployeeDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeDAO> FindRecords(ChangeEmployeeDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, ChangeEmployeeDAO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//查询刷卡时间
	public List<ChangeEmployee> CheckSwipeCard(String startdate, String empId) {
	
		List<ChangeEmployee> AllChange = null;
		String numSql = "SELECT  e.NAME ,c.EMP_ID,c.SWIPE_DATE FROM CSR_SWIPECARDTIME  c,CSR_EMPLOYEE e WHERE  SWIPE_DATE = ? AND EMP_ID = ? AND e.ID = c.EMP_ID";
		System.out.println("數據庫語句======="+numSql);
			
				try {
		    		List <Object> queryList=new  ArrayList<Object>();
		    		queryList.add(startdate);
		    		queryList.add(empId);
		    		AllChange = jdbcTemplate.query(numSql, queryList.toArray(), new QueryChangeEmployee());	
				 
		    	} catch (Exception ex) {
		    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
		    		  ex.printStackTrace();
		    	}
				
				return AllChange;		
	}
	
	//查询人员  CheckEmp
	public List<EmpChange> CheckEmp(String empId) {
		List<EmpChange> empList = null;
		String empSql = "SELECT NAME,ID FROM CSR_EMPLOYEE WHERE ID = ?";
		System.out.println("查詢人員數據庫語句======="+empSql);
		try {
    		List <Object> queryList=new  ArrayList<Object>();
    		queryList.add(empId);
    		
    		empList = jdbcTemplate.query(empSql, queryList.toArray(), new QueryEmployee());	
    		System.out.println("查询的人员资料"+empList);
    	} catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
	return empList;
	}
	
	//更新狀態
	public boolean updateStatus(String status,String startdate,String empId) {
		// TODO Auto-generated method stub
		//UPDATE MANPOWER_STATUS SET STATUS = '1_0' WHERE ID = '133566' AND TO_CHAR(WORKDATE,'yyyy-mm-dd') ='2019-10-31'
		
		System.out.println("傳入的數據==================>"+status+startdate+empId);
				int updateRow=-1;
				txDef = new DefaultTransactionDefinition();
				txStatus = transactionManager.getTransaction(txDef);		
				String sSQL = "UPDATE MANPOWER_STATUS SET STATUS =? WHERE ID =? AND TO_CHAR(WORKDATE,'yyyy-mm-dd') =?";
				
				try {
						updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								// TODO Auto-generated method stub
								ps.setString(1, status);
								ps.setString(2, empId);
								ps.setString(3, startdate);
							}	
						});
						transactionManager.commit(txStatus);	
				}
				catch(Exception ex) {
					logger.error("Update ExceptionCost is failed",ex);
					transactionManager.rollback(txStatus);
				}			
				System.out.println("數據庫語句==================>"+sSQL);
				System.out.println("更新返回數字==================>"+updateRow);
					if(updateRow > 0) 
						   return true; 
						else
						   return false;
		
		
	}
}
