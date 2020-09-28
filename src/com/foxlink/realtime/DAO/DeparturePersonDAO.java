package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.DeparturePerson;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.QueryDeparturePersonMapper;

public class DeparturePersonDAO extends DAO<DeparturePerson> {

	private static Logger logger=Logger.getLogger(DeparturePersonDAO.class);

	@Override
	public boolean AddNewRecord(DeparturePerson newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(DeparturePerson updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DeparturePerson> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeparturePerson> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeparturePerson> FindRecord(String userDataCostId, int currentPage, int totalRecord, DeparturePerson t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeparturePerson> FindRecords(DeparturePerson t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, DeparturePerson t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPARTURE_EXCEPTION_EMP t where t.enable = 'Y'";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("deviceIP")){
				sSQL+=" and deviceIP = ?";  
			}
			if(queryCritirea.equals("emp_id")){
				sSQL+=" and emp_id = ?";  
			}
			if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<DeparturePerson> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub

		List<DeparturePerson> empIpBinding = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.emp_id,c.name from DEPARTURE_EXCEPTION_EMP t,CSR_EMPLOYEE c"
				+ " where t.enable = 'Y' AND t. EMP_ID = c.id ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("deviceIP")){
				sSQL+=" and deviceIP = ?";  
			}
			if(queryCritirea.equals("emp_id")){
				sSQL+=" and emp_id = ?";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.emp_id)b order by b.emp_id asc) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    empIpBinding = jdbcTemplate.query(sSQL,queryList.toArray(), new QueryDeparturePersonMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return empIpBinding;
	}

	public boolean checkRepeat(DeparturePerson empIpBinding) {
		// TODO Auto-generated method stub
		String sSQL="select count(*) from DEPARTURE_EXCEPTION_EMP t where  t.emp_id = ? and t.enable = 'Y'";
		int updateRow=-1;
		try{
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(empIpBinding.getName());
			queryList.add(empIpBinding.getEmp_id());
			updateRow = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
			System.out.println("updateRow:"+updateRow);
		}catch(Exception ex) {
			logger.error("Check WorkshopNoRestInfo is failed",ex);
		}
		if(updateRow > 0) 
			   return false; 
			else
			   return true;
	}

	public boolean insertEmpIPBinding(DeparturePerson empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		String sSQL="insert into DEPARTURE_EXCEPTION_EMP t values(?,?,sysdate,'Y')";
		int insertRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			if(empIpBinding!=null) {
				insertRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, empIpBinding.getEmp_id());
						arg0.setString(2, updateUser);
					}	
				});
			}	
			transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(insertRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean UpdateRecord(DeparturePerson empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="update DEPARTURE_EXCEPTION_EMP t set t.emp_id=?,t.update_user=? "
				+ " where  and t.emp_id = ? and t.enable = 'Y'";
		try {
			if(empIpBinding!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, empIpBinding.getEmp_id());
						arg0.setString(2, updateUser);
						arg0.setString(2, empIpBinding.getOldEmp_id());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean DeleteEmpIPBinding(DeparturePerson[] empIpBindings, String updateUser) {
		// TODO Auto-generated method stub
		//System.out.println(123);
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update DEPARTURE_EXCEPTION_EMP t set t.enable = 'N',t.update_user=?,update_time=sysdate where  t.emp_id = ? and t.enable = 'Y'";
		int disableRow=0;
		System.out.println("數據庫語句++++++++======"+sSQL);
		try {
			if(empIpBindings!=null) {
				
				for (int i = 0; i < empIpBindings.length; i++) {
					System.err.println("=====工號========"+empIpBindings[i].getEmp_id());
				}
				
					jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, updateUser);
						ps.setString(2, empIpBindings[i].getEmp_id());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return empIpBindings.length;
					}
				});
					//System.out.println(sSQL);
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow == 0) 
			   return true; 
		 else
			 return false;
	}

	public boolean checkEmpID(String empID) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from csr_employee t where t.id = ? and t.isonwork = '0'";
		int checkEmpCount = 0;
		try {
    		List <Object> queryList=new  ArrayList<Object>();
    		queryList.add(empID);
    		checkEmpCount = jdbcTemplate.queryForObject(sql,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
		if(checkEmpCount>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean insertEmpIPBinding(List<String> exEmpList, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		//String sSQL="update DEPARTURE_EXCEPTION_EMP t set t.enable= 'N',t.update_user=?,t.Update_Time=sysdate where  t.emp_id = ? and t.enable = 'Y'";
		String insertSQL="insert into DEPARTURE_EXCEPTION_EMP(Emp_Id,Update_User,Update_Time,Enable) values(?,?,sysdate,'Y')";
		int[] disableRow;
		System.out.println("============DAO"+exEmpList);
		try {
			if(exEmpList.size()>0) {
				/*jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, updateUser);
						ps.setString(2, exEmpList.get(i));
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return exEmpList.size();
					}
				});*/
				jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
					
						ps.setString(1, exEmpList.get(i));
						ps.setString(2, updateUser);
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return exEmpList.size();
					}
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
			return false;
		}
		return true;
	}

}
