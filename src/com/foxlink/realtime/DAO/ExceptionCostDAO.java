package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Employee;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.QueryEmpyloeeMapper;
import com.foxlink.realtime.model.objectMapper.QueryExceptionCost;

public class ExceptionCostDAO extends DAO<ExceptionCost>{
	private static Logger logger = Logger.getLogger(ExceptionCostDAO.class);
	
	@Override
	public boolean AddNewRecord(ExceptionCost newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ExceptionCost updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ExceptionCost> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindRecord(String userDataCostId, int currentPage, int totalRecord, ExceptionCost t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindRecords(ExceptionCost t) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int getTotalRecords(String userDataCostId, ExceptionCost t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int addExceptionCost(ExceptionCost[] exceptionCost, String updateUser) {
		// TODO Auto-generated method stub
		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		deleteExcep(exceptionCost);
		String sSQL="INSERT INTO SWIPE.RT_ACCESS_CONTROL_EXCEPTION (WorkShopNo,CostId,Update_UserId) VALUES(?,?,?)";
		try {
			if(exceptionCost!=null) {
				
				
		       jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1,exceptionCost[i].getWorkShopNo());
					ps.setString(2, exceptionCost[i].getCostId());
					ps.setString(3, updateUser);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return exceptionCost.length;
				}
			});
		      transactionManager.commit(txStatus);
			}	
			
		}
		catch(Exception ex) {
			logger.error(ex);
			transactionManager.rollback(txStatus);
			createRow=1;
		}
		return createRow;
	}

	public void deleteExcep(ExceptionCost[] exceptionCost) {
		// TODO Auto-generated method stub
		
		
		String sSQL = "delete from RT_ACCESS_CONTROL_EXCEPTION where WorkShopNo=? and Costid=?";
		try {
			
				jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, exceptionCost[i].getWorkShopNo());
						ps.setString(2, exceptionCost[i].getCostId());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return exceptionCost.length;
					}
				});
			
			
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error(ex);
		
		}
	}

	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_CONTROL_EXCEPTION t where t.Enabled='Y'";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("CostId")){
				sSQL+=" and CostId = ?";  
			}
			else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}
			else{
				sSQL+="";
			}
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find ExceptionCost TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	public List<ExceptionCost> FindAllExceps(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		List<ExceptionCost> AllExce = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select c.*,rownum rn from (select WorkShopNo,CostId,Enabled from SWIPE.RT_ACCESS_CONTROL_EXCEPTION t where t.Enabled = 'Y'";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
    		if(queryCritirea.equals("CostId")){
				sSQL+=" and CostId = ?";  
			}
			else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}
			else{
				sSQL+="";
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.WorkShopNo)c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		   /* where rn>"+page.getStartIndex()+" and rn<="+endIndex+"*/
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllExce = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryExceptionCost());	
		  System.out.println(sSQL);
		
    	  } catch (Exception ex) {
    		  logger.error("Find ExceptionCost TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllExce;
	}

	public boolean UpdateExceCost(ExceptionCost exceptionCost, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "update SWIPE.RT_ACCESS_CONTROL_EXCEPTION set CostId=?,update_time = sysdate,Update_UserId=? where enabled='Y' and CostId =? and WorkShopNo=?";
		try {
			if(exceptionCost!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						
						ps.setString(1, exceptionCost.getCostId());
						ps.setString(2, updateUser);
						ps.setString(3, exceptionCost.getO_CostId());
						ps.setString(4, exceptionCost.getO_WorkShopNo());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update ExceptionCost is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean RelieveExceCost(ExceptionCost exceptionCost, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "delete from SWIPE.RT_ACCESS_CONTROL_EXCEPTION where enabled='Y' and CostId =? and WorkShopNo=?";
		try {
			if(exceptionCost!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, exceptionCost.getCostId());
						ps.setString(2, exceptionCost.getWorkShopNo());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("delete ExceCost is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean checkExceCost(String CostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPT_RELATION where CostId=? ";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CostId },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}

	public boolean checkWorkShopCost(String CostId, String WorkShopNo) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_CONTROL_EXCEPTION where CostId=? and WorkShopNo=?";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CostId,WorkShopNo },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}

}
