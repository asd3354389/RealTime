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

import com.foxlink.realtime.model.FourteenROP;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.FourteenROPMapper;

@Repository
public class FourteenROPDAO extends DAO<FourteenROP>{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean AddNewRecord(FourteenROP newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(FourteenROP updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FourteenROP> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FourteenROP> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FourteenROP> FindRecord(String userDataCostId, int currentPage, int totalRecord, FourteenROP t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FourteenROP> FindRecords(FourteenROP t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, FourteenROP t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		int totalRecord=-1;
    	String sSQL = "select count(*) from SWIPE.CSR_14R1_PERSONAL t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("id")){
				sSQL+=" and id = ? ";  
			}
				
			
			if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find FourteenROP TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<FourteenROP> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {

		List<FourteenROP> FourteenRO = null;
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.id,t.start_date,t.end_date from SWIPE.CSR_14R1_PERSONAL t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("id")){
				sSQL+=" and id = ?";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.id)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    FourteenRO = jdbcTemplate.query(sSQL,queryList.toArray(), new FourteenROPMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find FourteenROP TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return FourteenRO;
	}

	public boolean DeleteFourteenROP(String id, String updateUser, String startDate, String endDate) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update CSR_14R1_PERSONAL t set t.enabled = 'N',t.update_userid = ?,t.update_time = sysdate "
				+ " where t.id = ? and t.start_date = ? and t.end_date = ? and t.enabled = 'Y'";
		int disableRow=-1;
		try {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, id);
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

	public boolean addfourteenROP(FourteenROP[] fourteenROP, String updateUser) {
		// TODO Auto-generated method stub

		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		//String DSQL = "update CSR_14R1_COSTID t set t.enabled = 'N' where t.costid = ? and t.enabled = 'Y'";
		String ISQL="insert into CSR_14R1_PERSONAL values(UPPER(trim(?)),?,?,?,sysdate,'Y')";
		try {
			if(fourteenROP!=null) {
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

						ps.setString(1, fourteenROP[i].getId());
						ps.setString(2, fourteenROP[i].getStart_date());
						ps.setString(3, fourteenROP[i].getEnd_date());
						ps.setString(4, updateUser);
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return fourteenROP.length;
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

}
