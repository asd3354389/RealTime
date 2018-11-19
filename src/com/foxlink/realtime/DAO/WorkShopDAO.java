package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.AssistantInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShop;
import com.foxlink.realtime.model.objectMapper.AssistantInfoMapper;
import com.foxlink.realtime.model.objectMapper.WorkShopMapper;

public class WorkShopDAO extends DAO<WorkShop> {
	private static Logger logger=Logger.getLogger(WorkShopDAO.class);
	
	@Override
	public List<WorkShop> FindAllRecords() {	
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public boolean AddNewRecord(WorkShop newRecord) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="INSERT INTO SWIPE.lineno (WORKSHOPNO,Update_User) VALUES(?,?)";
		try {
			if(newRecord!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, newRecord.getWORKSHOPNO());
						arg0.setString(2, newRecord.getUPDATE_USER());
					
					}	
				});
				transactionManager.commit(txStatus);
			}			
		}
		catch(Exception ex) {
			logger.error("Update WorkShop is failed,原因"+ex);
			transactionManager.rollback(txStatus);
		}
		
		 if(createRow > 0) 
			   return true; 
		 else
			 return false;
	}

	@Override
	public boolean UpdateRecord(WorkShop updateRecord) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="UPDATE SWIPE.lineno SET LINENO=?,UPDATE_USER=? WHERE ROWID=? and WORKSHOPNO=?";
		try {
			if(updateRecord!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateRecord.getLINENO());
						arg0.setString(2, updateRecord.getUPDATE_USER());
						arg0.setString(3, updateRecord.getID());
						arg0.setString(4, updateRecord.getWORKSHOPNO());						
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update WorkShop is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(updateRow > 0) 
			   return true; 
		 else
			 return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean DisableWorkShopInfos(WorkShop workShop) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.lineno set ENABLED=0,UPDATE_USER=? WHERE ROWID=? and WORKSHOPNO=?";
		int disableRow=-1;
		try {
			if(workShop!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, workShop.getUPDATE_USER());
						arg0.setString(2, workShop.getID());
						arg0.setString(3, workShop.getWORKSHOPNO());
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable WORKSHOP is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}

	@Override
	public List<WorkShop> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<WorkShop> workShops=null;
		String sSQL="select * from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from "
				+ "(SELECT ROWID ID,WORKSHOPNO,LINENO,LINESIZE,CREATE_DATE,UPDATE_USER,ENABLED FROM SWIPE.lineno WHERE "
				+ "enabled=1 and workshopno is not null";
		try {
			if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and workshopno = ?";  
			}	
			else if(queryCritirea.equals("LineNo")){
				sSQL+=" and lineno = ?";  
			}
			
			Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by WORKSHOPNO,LINENO ) A ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
    		 List <Object> queryList=new  ArrayList<Object>();
    		  if (!queryCritirea.equals("")){
  		    	queryList.add(queryParam);
  		    }
    		  
			workShops=jdbcTemplate.query(sSQL, queryList.toArray(), new WorkShopMapper());
		}
		catch(Exception ex) {
			logger.error("Find All WorkShop Records are failed ",ex);
		}
		return workShops;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.lineno WHERE enabled=1 and workshopno is not null";
    	try { 
    		if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and workshopno = ?";  
			}			

    		 List <Object> queryList=new  ArrayList<Object>();
    		  if (!queryCritirea.equals("")){
  		    	queryList.add(queryParam);
  		    }

    		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
    		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShop TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}
	
	public boolean checkWorkShopNoDuplicate(String workShopNo){
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.lineno where workshopno=? and ENABLED=1";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { workShopNo },Integer.class);	   	
    	  } 
    	   catch (Exception ex) {
    		   ex.printStackTrace();
    		  }
    	
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}
	
	public boolean checkLineNoDuplicate(WorkShop workShop){
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.lineno where lineno=? and workshopno=? and ENABLED=1";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] {workShop.getLINENO(), workShop.getWORKSHOPNO() },Integer.class);	   	
    	  } 
    	   catch (Exception ex) {
    		   ex.printStackTrace();
    		  }
    	
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}


	

	
	public List<String> FindWorkShopNo() {
		// TODO Auto-generated method stub
		List<String> AllWorkShops = null;
		String sSQL = "SELECT WORKSHOPNO FROM SWIPE.lineno WHERE WorkshopNo is not null  and ENABLED=1 GROUP BY WorkshopNo order by WorkshopNo asc";
		try {	
			AllWorkShops = jdbcTemplate.queryForList(sSQL, String.class);			
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find WorkShops are failed",ex);
	}
	return AllWorkShops;
	}
	
	public List<String> GetLineNoByWorkShop(String workShopNo) {
		// TODO Auto-generated method stub
		List<String> AllLineNosByWorkShop = null;
		String sSQL = "select lineno from SWIPE.lineno where workshopno=? and lineno is not null GROUP BY lineno";
		try {	
			AllLineNosByWorkShop = jdbcTemplate.queryForList(sSQL, new Object[] { workShopNo }, String.class);		
	  
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find WorkShops are failed",ex);
	}
	return AllLineNosByWorkShop;
	}
	
	public boolean AddLineNo(WorkShop newRecord) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="INSERT INTO SWIPE.lineno (WORKSHOPNO,lineno,Update_User) VALUES(?,?,?)";
		try {
			if(newRecord!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, newRecord.getWORKSHOPNO());
						arg0.setString(2, newRecord.getLINENO());
						arg0.setString(3, newRecord.getUPDATE_USER());
					
					}	
				});
				transactionManager.commit(txStatus);
			}			
		}
		catch(Exception ex) {
			logger.error("Update LineNo is failed,原因"+ex);
			transactionManager.rollback(txStatus);
		}
		
		 if(createRow > 0) 
			   return true; 
		 else
			 return false;

	}

	@Override
	public List<WorkShop> FindRecords(WorkShop t) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<WorkShop> FindRecord(String userDataCostId, int currentPage, int totalRecord, WorkShop t) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int getTotalRecords(String userDataCostId, WorkShop t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
