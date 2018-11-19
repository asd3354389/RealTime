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
import com.foxlink.realtime.model.objectMapper.AssistantInfoMapper;

public class AssistantDAO  extends DAO<AssistantInfo>{
	private static Logger logger=Logger.getLogger(AccountDAO.class);
	
	@Override
	public List<AssistantInfo> FindAllRecords() {
		// TODO Auto-generated method stub
		List<AssistantInfo> AllAssistant = null;
		String sSQL = "select ASSISTANTCOSTID,ASSISTANTNAME,ASSISTANTID,ASSISTANTDEP,ASSISTANTTEL,ASSISTANTEMAIL,CREATE_DATE,UPDATE_USER,ENABLED from SWIPE.ASSISTANTINFO where ENABLED=1 ORDER BY ASSISTANTID";
		try {	
			AllAssistant = jdbcTemplate.query(sSQL, new AssistantInfoMapper());			
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find AssistantInfo are failed",ex);
	}
	return AllAssistant;
	}
	
	@Override
	public List<AssistantInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,String queryParam) {
		// TODO Auto-generated method stub
		List<AssistantInfo> AllAssistants = null;
		String sSQL = "select * from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from (select ASSISTANTCOSTID,"
				+ "ASSISTANTNAME,ASSISTANTID,ASSISTANTDEP,ASSISTANTTEL,ASSISTANTEMAIL,CREATE_DATE,UPDATE_USER,ENABLED "
				+ "from SWIPE.ASSISTANTINFO where ENABLED=1";
		try {				
			 List <Object> queryList=new  ArrayList<Object>();  		      
			if(queryCritirea.equals("assistantId")){
				sSQL+=" and ASSISTANTID = ?"; 				
			}
			else if(queryCritirea.equals("assistantName")){
				sSQL+=" and ASSISTANTNAME = ?";  
			}
			
			Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by ASSISTANTID ) A ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		    AllAssistants = jdbcTemplate.query(sSQL, queryList.toArray(),new AssistantInfoMapper());	
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Find Assistants are failed",ex);
		}
		return AllAssistants;
	}
	
	 /* 
     * 获得总记录数
     */
	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.ASSISTANTINFO where ENABLED=1";
    	try { 
    		if(queryCritirea.equals("assistantId")){
				sSQL+=" and ASSISTANTID = ?";  
			}
			else if(queryCritirea.equals("assistantName")){
				sSQL+=" and ASSISTANTNAME = ?";  
			}
			else{
				sSQL+="";
			}
    		 List <Object> queryList=new  ArrayList<Object>();
    		  if (!queryCritirea.equals("")){
  		    	queryList.add(queryParam);
  		    }

    		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
    		
    	  } catch (Exception ex) {
    		  logger.error("Find Assistants TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	@Override
	public boolean AddNewRecord(AssistantInfo newRecord) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="INSERT INTO SWIPE.ASSISTANTINFO (ASSISTANTID,ASSISTANTNAME,ASSISTANTCOSTID,ASSISTANTDEP,ASSISTANTTEL,ASSISTANTEMAIL,Update_User) VALUES(?,?,?,?,?,?,?)";
		try {
			if(newRecord!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, newRecord.getASSISTANTID());
						arg0.setString(2, newRecord.getASSISTANTNAME());
						arg0.setString(3, newRecord.getASSISTANTCOSTID());
						arg0.setString(4, newRecord.getASSISTANTDEP());
						arg0.setString(5, newRecord.getASSISTANTTEL());
						arg0.setString(6, newRecord.getASSISTANTEMAIL());
						arg0.setString(7, newRecord.getUPDATE_USER());
					}	
				});
				transactionManager.commit(txStatus);
			}			
		}
		catch(Exception ex) {
			logger.error(ex);
			transactionManager.rollback(txStatus);
		}
		
		 if(createRow > 0) 
			   return true; 
		 else
			 return false;
	}

	@Override
	public boolean UpdateRecord(AssistantInfo updateRecord) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="UPDATE SWIPE.ASSISTANTINFO SET ASSISTANTNAME=?,ASSISTANTCOSTID=?,ASSISTANTDEP=?,ASSISTANTTEL=?,ASSISTANTEMAIL=?,UPDATE_USER=? WHERE ASSISTANTID=?";
		try {
			if(updateRecord!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateRecord.getASSISTANTNAME());
						arg0.setString(2, updateRecord.getASSISTANTCOSTID());
						arg0.setString(3, updateRecord.getASSISTANTDEP());
						arg0.setString(4, updateRecord.getASSISTANTTEL());
						arg0.setString(5, updateRecord.getASSISTANTEMAIL());
						arg0.setString(6, updateRecord.getUPDATE_USER());
						arg0.setString(7, updateRecord.getASSISTANTID());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update Assistant is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(updateRow > 0) 
			   return true; 
		 else
			 return false;
	}

	@Override
	public boolean DeleteRecord(String recordID,String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.ASSISTANTINFO set ENABLED=0,UPDATE_USER=? WHERE ASSISTANTID=?";
		int disableRow=-1;
		try {
			if(recordID!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, recordID);
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable Assistant is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}
	
	public boolean checkAssistantIdDuplicate(String assistantId){
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.ASSISTANTINFO where assistantId=? and ENABLED=1";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { assistantId },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}
	
	public boolean checkAssistantEmailDuplicate(String assistantEmail){
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.ASSISTANTINFO where assistantEmail=? and ENABLED=1";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { assistantEmail },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

	

	
	@Override
	public List<AssistantInfo> FindRecords(AssistantInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssistantInfo> FindRecord(String userDataCostId, int currentPage, int totalRecord, AssistantInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, AssistantInfo t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
