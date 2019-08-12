package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.foxlink.realtime.model.objectMapper.QueryExceptionCost;
import com.foxlink.realtime.model.objectMapper.QueryWSStatus;

public class WorkShopStatusDAO extends DAO<WorkShopStatus> {
	private static Logger logger=Logger.getLogger(WorkShopStatusDAO.class);

	@Override
	public boolean AddNewRecord(WorkShopStatus newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(WorkShopStatus updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WorkShopStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkShopStatus> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		List<WorkShopStatus> AllWSStatus = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select c.*,rownum rn from (select WorkShopNo,LineNo,Status,Enabled from SWIPE.WORKSHOP_LINE_STATUS t where t.Enabled = 'Y'";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
    		if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}
			/*else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}*/
			else{
				sSQL+="";
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.WorkShopNo,t.lineNo)c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		   /* where rn>"+page.getStartIndex()+" and rn<="+endIndex+"*/
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllWSStatus = jdbcTemplate.query(sSQL, queryList.toArray(),new QueryWSStatus());	
		  System.out.println(sSQL);
		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShopStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllWSStatus;
	}

	@Override
	public List<WorkShopStatus> FindRecord(String userDataCostId, int currentPage, int totalRecord, WorkShopStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkShopStatus> FindRecords(WorkShopStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.WORKSHOP_LINE_STATUS t where t.Enabled='Y'";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}
			/*else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}*/
			else{
				sSQL+="";
			}
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShopStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	@Override
	public int getTotalRecords(String userDataCostId, WorkShopStatus t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> FindLineNo(String workShopNo) {
		// TODO Auto-generated method stub
		List<String> AllLineNo = null;
		String sSQL = "SELECT LineNo FROM SWIPE.lineno WHERE WorkshopNo ='"+workShopNo+"' and ENABLED=1 GROUP BY LineNo order by LineNo asc";
		try {	
			AllLineNo = jdbcTemplate.queryForList(sSQL, String.class);			
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find LineNo are failed",ex);
	}
	return AllLineNo;
	}

	public int addWorkShopStatus(WorkShopStatus[] workShopStatus, String updateUser) {
		// TODO Auto-generated method stub
		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="INSERT INTO SWIPE.WORKSHOP_LINE_STATUS (WorkShopNo,LineNo,Status,Setup_Time,Setup_Userid) VALUES(?,?,?,sysdate,?)";
		try {
			if(workShopStatus!=null) {			
		       jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1,workShopStatus[i].getWorkShopNo());
					ps.setString(2, workShopStatus[i].getLineNo());
					ps.setString(3, workShopStatus[i].getStatus());
					ps.setString(4, updateUser);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return workShopStatus.length;
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

	public boolean checkWorkShopStatud(String lineNo, String workShopNo) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.WORKSHOP_LINE_STATUS where WorkShopNo=? and LineNo=? and Enabled='Y'";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { workShopNo,lineNo },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}

	public boolean RlWorkShopStatus(WorkShopStatus[] workShopStatus, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL = "delete from SWIPE.WORKSHOP_LINE_STATUS where WorkShopNo=? and LineNo=? and Enabled='Y'";
		int disableRow=0;
		try {
			  if (workShopStatus!=null) {
				  jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1, workShopStatus[i].getWorkShopNo());
							ps.setString(2, workShopStatus[i].getLineNo());
						}
						
						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return workShopStatus.length;
						}
					});
				  transactionManager.commit(txStatus);
			}	
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("Disable WorkShopStatus is failed",ex);
			transactionManager.rollback(txStatus);
		}
		
		 if(disableRow == 0) 
			   return true; 
		 else
			 return false;
	}
}
