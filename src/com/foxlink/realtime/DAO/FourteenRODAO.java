package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.FourteenRO;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.FourteenROMapper;
import com.foxlink.realtime.model.objectMapper.QueryIOWorkShopPW;

public class FourteenRODAO extends DAO<FourteenRO>{
	private static Logger logger = Logger.getLogger(FourteenRODAO.class);

	@Override
	public boolean AddNewRecord(FourteenRO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(FourteenRO updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FourteenRO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FourteenRO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FourteenRO> FindRecord(String userDataCostId, int currentPage, int totalRecord, FourteenRO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FourteenRO> FindRecords(FourteenRO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, FourteenRO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		int totalRecord=-1;
    	String sSQL = "select count(*) from CSR_14R1_COSTID t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("costid")){
				sSQL+=" and costid = ? ";  
			}
				
			
			if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find FourteenRO TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<FourteenRO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {

		// TODO Auto-generated method stub
		List<FourteenRO> FourteenRO = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.costid,t.start_date,t.end_date from CSR_14R1_COSTID t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("costid")){
				sSQL+=" and costid = ?";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.costid)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    FourteenRO = jdbcTemplate.query(sSQL,queryList.toArray(), new FourteenROMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find FourteenRO TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return FourteenRO;
	}

	public boolean addfourteenRO(FourteenRO[] fourteenRO, String updateUser) {
		// TODO Auto-generated method stub

		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		//String DSQL = "update CSR_14R1_COSTID t set t.enabled = 'N' where t.costid = ? and t.enabled = 'Y'";
		String ISQL="insert into CSR_14R1_COSTID values(?,?,?,?,sysdate,'Y')";
		try {
			if(fourteenRO!=null) {
				/*
				 * jdbcTemplate.batchUpdate(DSQL, new BatchPreparedStatementSetter() {
				 * 
				 * @Override public void setValues(PreparedStatement ps,int i ) throws
				 * SQLException { // TODO Auto-generated method stub ps.setString(1,
				 * fourteenRO[i].getCostid()); }
				 * 
				 * @Override public int getBatchSize() { // TODO Auto-generated method stub
				 * return fourteenRO.length; } });
				 */

				jdbcTemplate.batchUpdate(ISQL, new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub

						ps.setString(1, fourteenRO[i].getCostid());
						ps.setString(2, fourteenRO[i].getStart_date());
						ps.setString(3, fourteenRO[i].getEnd_date());
						ps.setString(4, updateUser);
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return fourteenRO.length;
					}
				});
				 transactionManager.commit(txStatus);
			}
		}catch(Exception ex) {
			logger.error(ex);
			transactionManager.rollback(txStatus);
			createRow = 1;
		}
		
		if(createRow == 0) {
			 return true; 
		} else {
			 return false;
	}
	}

	public boolean UpdateRecord(FourteenRO fourteenRO, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		
		//String sSQL="UPDATE SWIPE.RT_SPECIAL_AREA_CONTROL SET WorkShopNo=?,Start_Date=?,End_Date=?,Update_Userid=?,Remark=?";
		//String sSQL="UPDATE SWIPE.RT_SPECIAL_AREA_CONTROL SET Start_Date=?,End_Date=?,Update_Userid=?,update_time=sysdate,Remark=? WHERE Emp_id=? and WorkShopNo=? and Enabled='Y'
		String sSQL="update CSR_14R1_COSTID t set t.start_date = ?,t.end_date = ?,t.update_userid = ?,t.update_time = sysdate where t.costid = ?";
		
		try {
				//if(ioWorkShopPW!=null) {
					updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement arg0) throws SQLException {
							// TODO Auto-generated method stub
							arg0.setString(1, fourteenRO.getStart_date());
							arg0.setString(2, fourteenRO.getEnd_date());
							arg0.setString(3, updateUser);
							arg0.setString(4, fourteenRO.getCostid());
						}	
					});
					System.out.print(sSQL);
					transactionManager.commit(txStatus);
			
			
			
		}
		catch(Exception ex) {
			logger.error("Update FourteenRO is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean DeleteFourteenRO(String costid, String updateUser, String startDate, String endDate) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update CSR_14R1_COSTID t set t.enabled = 'N',t.update_userid = ?,t.update_time = sysdate "
				+ " where t.costid = ? and t.start_date = ? and t.end_date = ? and t.enabled = 'Y'";
		int disableRow=-1;
		try {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, costid);
						arg0.setString(3, startDate);
						arg0.setString(4, endDate);
					}	
				});
				System.out.print(sSQL);
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Disable IOWorkShopPW is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}

}
