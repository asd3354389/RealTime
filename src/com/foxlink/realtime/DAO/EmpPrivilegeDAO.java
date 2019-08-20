package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.EmpPrivilege;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.foxlink.realtime.model.objectMapper.EmpPrivilegeMapper;
import com.foxlink.realtime.model.objectMapper.QueryWSStatus;

public class EmpPrivilegeDAO extends DAO<EmpPrivilege>{
	private static Logger logger=Logger.getLogger(EmpPrivilegeDAO.class);

	@Override
	public boolean AddNewRecord(EmpPrivilege newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpPrivilege updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpPrivilege> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpPrivilege> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		List<EmpPrivilege> AllWSStatus = null;
		String sSQL = "select * from (select c.*,rownum rn from (select t.id,t.privilege_level from EMP_PRIVILEGE_LEVEL t where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
    		if(queryCritirea.equals("id")){
				sSQL+=" and id = ? ";  
				queryList.add(queryParam);
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.id)c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		   /* where rn>"+page.getStartIndex()+" and rn<="+endIndex+"*/
		  
		  AllWSStatus = jdbcTemplate.query(sSQL, queryList.toArray(),new EmpPrivilegeMapper());	
		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShopStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllWSStatus;
	}

	@Override
	public List<EmpPrivilege> FindRecord(String userDataCostId, int currentPage, int totalRecord, EmpPrivilege t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpPrivilege> FindRecords(EmpPrivilege t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from EMP_PRIVILEGE_LEVEL t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("id")){
				sSQL+=" and id = ?";  
				queryList.add(queryParam);
			}
			/*else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}*/
			else{
				sSQL+="";
			}

		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShopStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	@Override
	public int getTotalRecords(String userDataCostId, EmpPrivilege t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int checkEmpExist(String id) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from csr_employee t where t.id = ? and t.isonwork = '0' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
		    queryList.add(id);

		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShopStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	public int addEmpPrivilege(List<String> existIdList, String privilege, String updateUser){
		// TODO Auto-generated method stub
		int createRow=0;
		String DSQL = "update EMP_PRIVILEGE_LEVEL t set t.enabled = 'N',t.Update_Time=sysdate,t.Update_Userid=? where t.id = ? and t.enabled = 'Y' ";
		String ISQL = "insert into EMP_PRIVILEGE_LEVEL(id,Privilege_Level,Update_Time,Update_Userid,Enabled) values(?,?,sysdate,?,'Y') ";
		try{
			txDef = new DefaultTransactionDefinition();
			txStatus = transactionManager.getTransaction(txDef);
			jdbcTemplate.batchUpdate(DSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1,updateUser);
					ps.setString(2,existIdList.get(i));
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return existIdList.size();
				}
			});
			
			jdbcTemplate.batchUpdate(ISQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1,existIdList.get(i));
					ps.setString(2,privilege);
					ps.setString(3,updateUser);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return existIdList.size();
				}
			});
			
			transactionManager.commit(txStatus);
		}catch(Exception ex) {
			ex.printStackTrace();
				logger.error(ex);
				transactionManager.rollback(txStatus);
				createRow=1;
		}
		return createRow;
		
	}

	public boolean RlEmpPrivilege(EmpPrivilege[] empPrivilege, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL = "update EMP_PRIVILEGE_LEVEL t set t.enabled = 'N',t.Update_Time=sysdate,t.Update_Userid=? where t.id = ? and t.enabled = 'Y' ";
		int disableRow=0;
		try {
			  if (empPrivilege!=null) {
				  jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1,updateUser);
							ps.setString(2, empPrivilege[i].getId());
						}
						
						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return empPrivilege.length;
						}
					});
				  transactionManager.commit(txStatus);
			}	
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("Disable WorkShopStatus is failed",ex);
			transactionManager.rollback(txStatus);
			disableRow=1;
		}
		
		 if(disableRow == 0) 
			   return true; 
		 else
			 return false;
	}

}
