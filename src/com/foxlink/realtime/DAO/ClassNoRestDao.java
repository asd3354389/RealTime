package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.ClassNO;
import com.foxlink.realtime.model.ClassNoRestInfo;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.ClassInfoMapper;
import com.foxlink.realtime.model.objectMapper.ClassNoMapper;
import com.foxlink.realtime.model.objectMapper.QueryCRestInfoMapper;
import com.foxlink.realtime.model.objectMapper.QueryWRestInfoMapper;

public class ClassNoRestDao extends DAO<ClassNoRestInfo> {
	private static Logger logger = Logger.getLogger(ClassNoRestDao.class);

	@Override
	public boolean AddNewRecord(ClassNoRestInfo newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ClassNoRestInfo updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ClassNoRestInfo> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassNoRestInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassNoRestInfo> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			ClassNoRestInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassNoRestInfo> FindRecords(ClassNoRestInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, ClassNoRestInfo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.COSTID_CLASS_SUB_RESTTIME t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new ArrayList<Object>();
			if(queryCritirea.equals("classNo")){
				sSQL+=" and Class_No = ?";  
			}
			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
			if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find ClassNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<ClassNoRestInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam, String updateUser, String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		List<ClassNoRestInfo> classNoRestInfo = null;
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.COSTID,t.CLASS_NO,t.SUB_REST_START1,t.SUB_REST_END1,t.SUB_REST_START2,t.SUB_REST_END2 from COSTID_CLASS_SUB_RESTTIME t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new ArrayList<Object>();
			if(queryCritirea.equals("classNo")){
				sSQL+=" and CLASS_NO = ?";  
			}
			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.CLASS_NO)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    classNoRestInfo = jdbcTemplate.query(sSQL,queryList.toArray(), new QueryCRestInfoMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find ClassNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		System.out.println(sSQL);
		return classNoRestInfo;
	}

	public boolean UpdateRecord(ClassNoRestInfo classNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="update COSTID_CLASS_SUB_RESTTIME t set t.SUB_REST_START2 = ?,t.SUB_REST_END2 = ?,t.update_userid = ?,t.update_time=sysdate "
				+ "where t.COSTID=? and t.CLASS_NO=? and t.enabled = 'Y' and bu =?";
		try {
			if(classNoRestInfo!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, classNoRestInfo.getRest_Start_S());
						arg0.setString(2, classNoRestInfo.getRest_End_S());
						arg0.setString(3, updateUser);
						arg0.setString(4, classNoRestInfo.getCostId());
						arg0.setString(5, classNoRestInfo.getClass_No());
						arg0.setString(6, accessRole);
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update classNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean DeleteClassNoRest(String costId, String class_No, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update COSTID_CLASS_SUB_RESTTIME t set t.enabled = 'N',t.update_userid = ? where t.COSTID = ? and t.CLASS_NO = ? and t.enabled='Y' and bu=?";
		int disableRow=-1;
		try {
			if(class_No!=null&&class_No!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, costId);
						arg0.setString(3, class_No);
						arg0.setString(4, accessRole);
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable classNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}

	public List<String> FindClassNo() {
		// TODO Auto-generated method stub
		List<String> AllClassNos = null;
		String sSQL = "SELECT Class_No FROM SWIPE.CLASSNO WHERE Class_No is not null";
		try {	
			AllClassNos = jdbcTemplate.queryForList(sSQL, String.class);			
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find AllClassNos are failed",ex);
	}
	return AllClassNos;
	}

	public List<ClassNO> FindClassNoCotent(String class_No) {
		// TODO Auto-generated method stub
		List<ClassNO> AllDept = null;
		String sSQl = "SELECT Rest_Start1,Rest_End1,Rest_Start2,Rest_End2 FROM SWIPE.CLASSNO WHERE Class_No ='"+class_No+"'";
		try {
			AllDept = jdbcTemplate.query(sSQl,new ClassNoMapper());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ClassNo TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return AllDept;
	}

	public boolean checkRepeat(ClassNoRestInfo classNoRestInfo) {
		// TODO Auto-generated method stub
		String sSQL="select count(*) from COSTID_CLASS_SUB_RESTTIME t where t.CostId = ? and t.Class_No=? and t.enabled = 'Y'";
		int updateRow=-1;
		try{
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(classNoRestInfo.getCostId());
			queryList.add(classNoRestInfo.getClass_No());
			updateRow = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
			System.out.println("updateRow:"+updateRow);
		}catch(Exception ex) {
			logger.error("Check ClassNoRestInfo is failed",ex);
		}
		if(updateRow > 0) 
			   return false; 
			else
			   return true;
	}

/*	public boolean insertWorkShopNoRestInfo(ClassNoRestInfo classNoRestInfo, String updateUser) {
		// TODO Auto-generated method stub
		String sSQL="insert into COSTID_CLASS_SUB_RESTTIME(COSTID,CLASS_NO,SUB_REST_START2,SUB_REST_END2,UPDATE_TIME,UPDATE_USERID,Enabled) "
				+ "values(?,?,?,?,sysdate,?,'Y')";
		int insertRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			if(classNoRestInfo!=null) {
				insertRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, classNoRestInfo.getCostId());
						arg0.setString(2, classNoRestInfo.getClass_No());
						arg0.setString(3, classNoRestInfo.getRest_Start_S());
						arg0.setString(4, classNoRestInfo.getRest_End_S());
						arg0.setString(5, updateUser);
						
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update ClassNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(insertRow > 0) 
				   return true; 
				else
				   return false;
	}*/
	
	
	public void deleteExcep(ClassNoRestInfo[] classNoRestInfo,String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		
		
		String sSQL = "update SWIPE.COSTID_CLASS_SUB_RESTTIME SET Enabled='N',Update_Time=sysdate,Update_UserId=? where COSTID = ? and CLASS_NO = ? and enabled='Y' and bu =?";
		try {
			
				jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, updateUser);
						ps.setString(2, classNoRestInfo[i].getCostId());
						ps.setString(3, classNoRestInfo[i].getClass_No());
						ps.setString(4, accessRole);
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return classNoRestInfo.length;
					}
				});
			
			
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error(ex);
		
		}
	}

	public int insertClassNoRestInfo(ClassNoRestInfo[] classNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		int createRow=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		deleteExcep(classNoRestInfo,updateUser,accessRole);
		String sSQL="insert into COSTID_CLASS_SUB_RESTTIME(COSTID,CLASS_NO,SUB_REST_START2,SUB_REST_END2,UPDATE_TIME,UPDATE_USERID,Enabled,BU) "
				+ "values(?,?,?,?,sysdate,?,'Y',?)";;
		try {
			if(classNoRestInfo!=null) {
				
				
		       jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, classNoRestInfo[i].getCostId());
					ps.setString(2, classNoRestInfo[i].getClass_No());
					ps.setString(3, classNoRestInfo[i].getRest_Start_S());
					ps.setString(4, classNoRestInfo[i].getRest_End_S());
					ps.setString(5, updateUser);
					ps.setString(6, accessRole);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return classNoRestInfo.length;
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
	
}
