package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.EmpIpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.model.objectMapper.QueryEmpIPBindingMapper;
import com.foxlink.realtime.model.objectMapper.QueryWRestInfoMapper;

public class EmpIPBindingDAO extends DAO<EmpIpBinding>{
	private static Logger logger=Logger.getLogger(EmpIPBindingDAO.class);

	@Override
	public boolean AddNewRecord(EmpIpBinding newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpIpBinding updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpIpBinding> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpIpBinding> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpIpBinding> FindRecord(String userDataCostId, int currentPage, int totalRecord, EmpIpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpIpBinding> FindRecords(EmpIpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, EmpIpBinding t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.EMP_DEPT_BINDING t where t.enabled = 'Y' ";
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

	public List<EmpIpBinding> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub

		List<EmpIpBinding> empIpBinding = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.deviceip,t.emp_id  from EMP_DEPT_BINDING t "
				+ " where t.enabled = 'Y' ";
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
		    sSQL += "order by t.emp_id)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    empIpBinding = jdbcTemplate.query(sSQL,queryList.toArray(), new QueryEmpIPBindingMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return empIpBinding;
	}

	public boolean checkRepeat(EmpIpBinding empIpBinding) {
		// TODO Auto-generated method stub
		String sSQL="select count(*) from EMP_DEPT_BINDING t where t.deviceip = ? and t.emp_id = ? and t.enabled = 'Y'";
		int updateRow=-1;
		try{
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(empIpBinding.getDeviceIP());
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

	public boolean insertEmpIPBinding(EmpIpBinding empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		String sSQL="insert into EMP_DEPT_BINDING t values(?,?,?,sysdate,'Y')";
		int insertRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			if(empIpBinding!=null) {
				insertRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, empIpBinding.getDeviceIP());
						arg0.setString(2, empIpBinding.getEmp_id());
						arg0.setString(3, updateUser);
					}	
				});
				transactionManager.commit(txStatus);
			}	
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

	public boolean UpdateRecord(EmpIpBinding empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="update EMP_DEPT_BINDING t set t.emp_id=?,t.update_userid=? "
				+ " where t.deviceip = ? and t.emp_id = ? and t.enabled = 'Y'";
		try {
			if(empIpBinding!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, empIpBinding.getEmp_id());
						arg0.setString(2, updateUser);
						arg0.setString(3, empIpBinding.getDeviceIP());
						arg0.setString(4, empIpBinding.getOldEmp_id());
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

	public boolean DeleteEmpIPBinding(String deviceIP,String emp_id, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update EMP_DEPT_BINDING t set t.enabled = 'N',t.update_userid=? where t.deviceip = ? and t.emp_id = ? and t.enabled = 'Y'";
		int disableRow=-1;
		try {
			if(deviceIP!=null && emp_id!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, deviceIP);
						arg0.setString(3, emp_id);
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}

}
