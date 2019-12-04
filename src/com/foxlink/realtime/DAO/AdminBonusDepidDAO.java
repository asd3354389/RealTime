package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.BonusDeptid;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.QueryBonusDeptid;
import com.foxlink.realtime.model.objectMapper.QueryExceptionCost;
import com.foxlink.realtime.service.AdminBonusDepidService;

public class AdminBonusDepidDAO extends DAO<BonusDeptid>{
	private static Logger logger = Logger.getLogger(AdminBonusDepidDAO.class);

	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.Bonus_Dept t where 1=1";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("Deptid")){
				sSQL+=" and Deptid = ?";  
			}
			else{
				sSQL+="";
			}
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		 System.out.println("总条数======>>"+sSQL);
		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find AdminBonusDepid TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	@Override
	public boolean AddNewRecord(BonusDeptid newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(BonusDeptid updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BonusDeptid> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonusDeptid> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonusDeptid> FindRecord(String userDataCostId, int currentPage, int totalRecord, BonusDeptid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BonusDeptid> FindRecords(BonusDeptid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, BonusDeptid t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<BonusDeptid> FindAllBonus(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<BonusDeptid> AllBonus = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select c.*,rownum rn from (select Deptid,Bonus_Rule,Modify_Allowed from SWIPE.Bonus_Dept t where 1=1";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
    		if(queryCritirea.equals("Deptid")){
				sSQL+=" and Deptid = ?";  
			}
			else{
				sSQL+="";
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.Deptid)c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		   /* where rn>"+page.getStartIndex()+" and rn<="+endIndex+"*/
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllBonus = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryBonusDeptid());	
		  System.out.println(sSQL);
		
    	  } catch (Exception ex) {
    		  logger.error("Find BonusDeptid TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllBonus;
	}

	public boolean RelieveBonusDeptid(BonusDeptid[] bonusDeptid, String updateUser, String accessRole) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL = "Delete From SWIPE.Bonus_Dept where Deptid=? and Bonus_Rule=?";
		int disableRow=0;
		try {
			  if (bonusDeptid!=null) {
				 
				  jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1, bonusDeptid[i].getDeptid());
							ps.setString(2, bonusDeptid[i].getBonus_Rule());
							
						}
						
						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return bonusDeptid.length;
						}
					});
				  transactionManager.commit(txStatus);
			}	
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("Disable BonusDeptid is failed",ex);
			transactionManager.rollback(txStatus);
		}
		
		 if(disableRow == 0) 
			   return true; 
		 else
			 return false;
	}

	public boolean UpdateBonus(BonusDeptid bonusDeptid) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "update SWIPE.Bonus_Dept set Modify_Allowed=? where Deptid=? and Bonus_Rule =? ";
		try {
			if(bonusDeptid!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						
						ps.setString(1, bonusDeptid.getModify_Allowed());
						ps.setString(2, bonusDeptid.getDeptid());
						ps.setString(3, bonusDeptid.getBonus_Rule());						
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update BonusAllowed is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public int addBonusDeptid(String deptid, String bonus_Rule) {
		// TODO Auto-generated method stub
		int createRow=0;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="INSERT INTO SWIPE.Bonus_Dept (Deptid,Bonus_Rule) VALUES(?,?)";
		try {
								
			createRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, deptid);
					ps.setString(2, bonus_Rule);
				}
			});
		      transactionManager.commit(txStatus);
						
		}
		catch(Exception ex) {
			logger.error(ex);
			transactionManager.rollback(txStatus);
		}
		return createRow;
	}

	public boolean checkBonusByDeptid(String deptid) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.Bonus_Dept where Deptid=?";
    	try {    	 
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { deptid},Integer.class);	   	
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
