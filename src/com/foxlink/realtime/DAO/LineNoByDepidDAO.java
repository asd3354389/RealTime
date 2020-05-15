package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.LineNoByDepid;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.foxlink.realtime.model.objectMapper.QueryLineNoByDepid;
import com.foxlink.realtime.model.objectMapper.QueryWSStatus;

@Repository
public class LineNoByDepidDAO extends DAO<LineNoByDepid>{
	private Logger logger = Logger.getLogger(this.getClass());

	public List<String> FindLineNo(String workShopNo) {
		// TODO Auto-generated method stub
		List<String> AllLineNo = null;
		String sSQL = "SELECT distinct(LineNo) FROM SWIPE.LineNo WHERE ENABLED=1 and WorkShopNo='"+workShopNo+"'";
		try {	
			AllLineNo = jdbcTemplate.queryForList(sSQL, String.class);			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Find WorkShops are failed",ex);
		}
		return AllLineNo;
	}

	@Override
	public boolean AddNewRecord(LineNoByDepid newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(LineNoByDepid updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LineNoByDepid> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineNoByDepid> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		List<LineNoByDepid> AllLNBDepid = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select c.*,rownum rn from (select WorkShopNo,LineNo,Depid from SWIPE.LINE_DEPT_BINDING t where t.Enabled = 'Y'";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
    		if(queryCritirea.equals("depid")){
				sSQL+=" and Depid = ?";  
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
		  
		  AllLNBDepid = jdbcTemplate.query(sSQL, queryList.toArray(),new QueryLineNoByDepid());	
		  System.out.println(sSQL);
		
    	  } catch (Exception ex) {
    		  logger.error("Find WorkShopStatus TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllLNBDepid;
	}

	@Override
	public List<LineNoByDepid> FindRecord(String userDataCostId, int currentPage, int totalRecord, LineNoByDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineNoByDepid> FindRecords(LineNoByDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.LINE_DEPT_BINDING t where t.Enabled='Y'";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("depid")){
				sSQL+=" and Depid = ?";  
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
    		  logger.error("Find LineNoByDepid TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	@Override
	public int getTotalRecords(String userDataCostId, LineNoByDepid t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean checkWorkShopStatud(String lineNo, String workShopNo) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.LINE_DEPT_BINDING where WorkShopNo=? and LineNo=? and Enabled='Y'";
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

	public int addLineNoByDepid(LineNoByDepid[] lineNoByDepid, String updateUser) {
		// TODO Auto-generated method stub
		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="INSERT INTO SWIPE.LINE_DEPT_BINDING (WorkShopNo,LineNo,Depid,Update_UserId,Update_Time) VALUES(?,?,?,?,sysdate)";
		try {
			if(lineNoByDepid!=null) {			
				jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1,lineNoByDepid[i].getWorkShopNo());
					ps.setString(2, lineNoByDepid[i].getLineNo());
					ps.setString(3, lineNoByDepid[i].getDepid());
					ps.setString(4, updateUser);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return lineNoByDepid.length;
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

	public boolean DeleteLineNoByDepid(String workShopNo, String lineNo, String depid, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.LINE_DEPT_BINDING t set t.enabled = 'N',t.update_userid = ?,t.update_time = sysdate "
				+ " where t.workShopNo=? and  t.LineNo = ? and t.Depid = ? and t.enabled = 'Y'";
		int disableRow=-1;
		try {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, workShopNo);
						arg0.setString(3, lineNo);
						arg0.setString(4, depid);
					}	
				});
				System.out.print(sSQL);
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Disable LineNoByDepid is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}
	
}
