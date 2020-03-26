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

import com.foxlink.realtime.model.AccessGoods;
import com.foxlink.realtime.model.FourteenROP;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.AccessGoodsMapper;
import com.foxlink.realtime.model.objectMapper.FourteenROPMapper;

import oracle.net.aso.i;

@Repository
public class AccessGoodsDAO extends DAO<AccessGoods> {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<String> FindWorkShopNo() {
		// TODO Auto-generated method stub
		List<String> AllWorkShops = null;
		String sSQL = "SELECT distinct(WORKSHOPNO) FROM SWIPE.RT_DEVICE_INFO WHERE WorkshopNo is not null  and ENABLED='Y' and Is_Special='Y' GROUP BY WorkshopNo order by WorkshopNo asc";
		try {	
			AllWorkShops = jdbcTemplate.queryForList(sSQL, String.class);			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Find WorkShops are failed",ex);
		}
		return AllWorkShops;
	}

	@Override
	public boolean AddNewRecord(AccessGoods newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(AccessGoods updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AccessGoods> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccessGoods> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccessGoods> FindRecord(String userDataCostId, int currentPage, int totalRecord, AccessGoods t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccessGoods> FindRecords(AccessGoods t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, AccessGoods t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean addAccessGoods(AccessGoods[] accessGoods, String updateUser, String accessRole) {
		// TODO Auto-generated method stub
		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		//String DSQL = "update CSR_14R1_COSTID t set t.enabled = 'N' where t.costid = ? and t.enabled = 'Y'";
		String ISQL="insert into SWIPE.RT_ACCESS_GOODS values(?,?,?,?,?,?,?,?,sysdate,'Y',?,?)";
		try {
			if(accessGoods!=null) {
				jdbcTemplate.batchUpdate(ISQL, new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						
						ps.setString(1, accessGoods[i].getUserId()==""?"":accessGoods[i].getUserId());
						ps.setString(2, accessGoods[i].getWorkShopNo());
						ps.setString(3, accessGoods[i].getUdisk());
						ps.setString(4, accessGoods[i].getComputer());
						ps.setString(5, accessGoods[i].getMobilePhone());
						ps.setString(6, accessGoods[i].getStart_date());
						ps.setString(7, accessGoods[i].getEnd_date());
						ps.setString(8, updateUser);
						ps.setString(9, accessRole);
						ps.setString(10, accessGoods[i].getCardId()==""?"":accessGoods[i].getCardId());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return accessGoods.length;
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

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from SWIPE.RT_ACCESS_GOODS t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("userId")){
				sSQL+=" and id = ? ";  
			}else if(queryCritirea.equals("cardId")){
				sSQL+=" and cardid = ? ";  
			}
				
			
			if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find AccessGoods TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<AccessGoods> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		List<AccessGoods> AccessGood = null;
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.id,t.WorkShopNo,t.Udisk,t.Computer,t.MobilePhone,t.start_date,t.end_date,t.CardId from SWIPE.RT_ACCESS_GOODS t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("userId")){
				sSQL+=" and id = ?";  
			}else if(queryCritirea.equals("cardId")){
				sSQL+=" and cardid = ? ";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.id)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    AccessGood = jdbcTemplate.query(sSQL,queryList.toArray(), new AccessGoodsMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find AccessGoods TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AccessGood;
	}

	public boolean DeleteAccessGoods(String id, String cardId, String workShopNo, String updateUser, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.RT_ACCESS_GOODS t set t.enabled = 'N',t.update_userid = ?,t.update_time = sysdate "
				+ " where t.workShopNo=? and  t.start_date = ? and t.end_date = ? and t.enabled = 'Y'";
		if(id.equals("")) {
			sSQL+= " and t.cardId = ?";
		}else if(cardId.equals("")) {
			sSQL+= " and t.id = ?";
		}
		int disableRow=-1;
		try {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, workShopNo);
						arg0.setString(3, startDate);
						arg0.setString(4, endDate);
						if(id.equals("")) {
							arg0.setString(5, cardId);
						}else if(cardId.equals("")) {
							arg0.setString(5, id);
						}
					}	
				});
				System.out.print(sSQL);
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Disable AccessGoods is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}

//	public boolean checkAccessGoods(AccessGoods[] accessGoods, String accessRole) {
//		// TODO Auto-generated method stub
//		int totalRecord=-1;
//    	String sSQL = "select count(*) FROM SWIPE.DEPT_RELATION where CostId=? ";
//    	try {    	    
//    		
//    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CostId },Integer.class);	   	
//    	  } catch (Exception ex) {
//    		  ex.printStackTrace();
//    		  }
//    	/*System.out.println(sSQL);*/
//    	 if(totalRecord > 0) 
//			   return true; 
//		 else
//			 return false;
//	}
	
	public int Delete(String id, String cardId, String updateUser, String accessRole, String workShopNo) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.RT_ACCESS_GOODS t set t.enabled = 'N',t.update_userid = ?,t.update_time = sysdate "
				+ " where t.enabled = 'Y' and t.Bu = ?";
		if(id.equals("")) {
			sSQL+= " and t.cardId = ?";
		}else if(cardId.equals("")) {
			sSQL+= " and t.id = ?";
		}
		
		if(workShopNo.equals("ALL")) {
			sSQL+= "";
		}else {
			sSQL+=" and t.WorkShopNo=?";
		}
		int num = 0;
		System.out.println(sSQL);
		try {
			num=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, accessRole);
						if(id.equals("")) {
							arg0.setString(3, cardId);
						}else if(cardId.equals("")) {
							arg0.setString(3, id);
						}
						if(workShopNo.equals("ALL")) {
							
						}else {
							arg0.setString(4, workShopNo);
						}
						
					}	
				});
			transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Disable AccessGoods is failed",ex);
			transactionManager.rollback(txStatus);
		}
		return num;
	}
}
