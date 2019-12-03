package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WSListStatus;
import com.foxlink.realtime.model.objectMapper.QueryExceptionCost;
import com.foxlink.realtime.model.objectMapper.QueryWSListStatus;
import com.google.gson.JsonElement;



public class WSListStatusDAO extends DAO<WSListStatus> {
	private static Logger logger = Logger.getLogger(WSListStatusDAO.class);

	@Override
	public boolean AddNewRecord(WSListStatus newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(WSListStatus updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WSListStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindRecord(String userDataCostId, int currentPage, int totalRecord, WSListStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindRecords(WSListStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, WSListStatus t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.PDLINE_MAINTENANCE_REASONS t where t.Enabled='Y'";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("Reason_Class")){
				sSQL+=" and Reason_Class = ?";  
			}
			/*else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}*/
			else{
				sSQL+="";
			}
    		/*if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}*/
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		// System.out.println("总条数======>>"+sSQL);
		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find WSListStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	public List<WSListStatus> FindAllWslss(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String accessRole) {
		// TODO Auto-generated method stub
		List<WSListStatus> AllWsls = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select c.*,rownum rn from (select Reason_No,Reason_Class,Reason_Desc from SWIPE.PDLINE_MAINTENANCE_REASONS t where t.Enabled = 'Y'";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
    		if(queryCritirea.equals("Reason_Class")){
				sSQL+=" and Reason_Class = ?";  
			}
			/*else if(queryCritirea.equals("WorkShopNo")){
				sSQL+=" and WorkShopNo = ?";  
			}*/
			else{
				sSQL+="";
			}
    		/*if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}*/
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL +=" order by t.Reason_Class)c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		   /* where rn>"+page.getStartIndex()+" and rn<="+endIndex+"*/
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllWsls = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryWSListStatus());	
		
    	  } catch (Exception ex) {
    		  logger.error("Find WSListStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllWsls;
	}

	public boolean RlWSListStatus(WSListStatus[] wSListStatus, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL = "update SWIPE.PDLINE_MAINTENANCE_REASONS set Enabled='N',update_userid=? where Reason_Class=? and Reason_Desc=? and Enabled='Y'";
		int upableRow=0;
		try {
			  if (wSListStatus!=null) {
				  jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// TODO Auto-generated method stub
						
							ps.setString(1, updateUser);
							ps.setString(2, wSListStatus[i].getReason_Class());
							ps.setString(3, wSListStatus[i].getReason_Desc());
						}
						
						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return wSListStatus.length;
						}
					});
				  transactionManager.commit(txStatus);
			}	
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("Disable WSListStatus is failed",ex);
			transactionManager.rollback(txStatus);
		}
		
		 if(upableRow == 0) 
			   return true; 
		 else
			 return false;
	}

	public  List<String> FindReasonClass() {
		// TODO Auto-generated method stub
		List<String> AllReasonClass = null;
		String sSQL = "SELECT distinct(Reason_Class) FROM SWIPE.PDLINE_MAINTENANCE_REASONS  where enabled='Y' order by Reason_Class";
		try {	
			AllReasonClass = jdbcTemplate.queryForList(sSQL, String.class);			
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find ReasonClass are failed",ex);
	}
	return AllReasonClass;
	}

	public int addWSListStatus(String reason_Class, String reason_Desc, String updateUser) {
		// TODO Auto-generated method stub
		
		String reason_No = Num();
		System.out.print(reason_No);
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		int insertRow = -1;
		String sql = "insert into SWIPE.PDLINE_MAINTENANCE_REASONS(Reason_No,Reason_Class,Reason_Desc,Update_UserId,Update_Time)VALUES(?,?,?,?,sysdate)";
		try{
				insertRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, reason_No);
						ps.setString(2, reason_Class);
						ps.setString(3, reason_Desc);
						ps.setString(4, updateUser);
					}
				});
				transactionManager.commit(txStatus);
		}catch(Exception ex){
			logger.error("Find InsertLineMapping TotalRecord are failed ",ex);
			transactionManager.rollback(txStatus);
		}	
		return insertRow;
	}
	
	public String Num() {
		int totalRecord=0;
		String sSQL = "SELECT COUNT(*) FROM SWIPE.PDLINE_MAINTENANCE_REASONS";
		totalRecord = jdbcTemplate.queryForObject(sSQL, Integer.class);	
		totalRecord += 100001;
		String Reason_No=String.valueOf(totalRecord);
		return Reason_No;
	}

	public boolean checkWSListStatus(String reason_Class, String reason_Desc) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.PDLINE_MAINTENANCE_REASONS where Reason_Class=? and Reason_Desc=? and Enabled='Y'";
    	try {    	 
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { reason_Class,reason_Desc },Integer.class);	   	
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
