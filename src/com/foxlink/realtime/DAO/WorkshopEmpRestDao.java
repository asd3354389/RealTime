package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopEmpRestInfo;
import com.foxlink.realtime.model.objectMapper.QueryWEmpRestInfoMapper;

public class WorkshopEmpRestDao extends DAO<WorkshopEmpRestInfo>{
	private static Logger logger=Logger.getLogger(WorkshopEmpRestInfo.class);

	@Override
	public boolean AddNewRecord(WorkshopEmpRestInfo newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(WorkshopEmpRestInfo updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WorkshopEmpRestInfo> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopEmpRestInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopEmpRestInfo> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			WorkshopEmpRestInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopEmpRestInfo> FindRecords(WorkshopEmpRestInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, WorkshopEmpRestInfo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId,String accessRole) {
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.WORKSHOP_EMP_OVERTIME15_REST t where t.ENABLE = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("workshopno")){
				sSQL+=" and EMP_ID = ?";  
			}
			/*if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}*/
			if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		 totalRecord = jdbcTemplate.queryForObject(sSQL,queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<WorkshopEmpRestInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam, String updateUser, String userDataCostId,String accessRole) {
		List<WorkshopEmpRestInfo> workshopNoRestInfo = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.EMP_ID,t.CLASS_NO,t.REST_START1,t.REST_END1,t.REST_START2,t.REST_END2 from SWIPE.WORKSHOP_EMP_OVERTIME15_REST t "
				+ " where t.ENABLE = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("workshopno")){
				sSQL+=" and  t.EMP_ID = ?";  
			}
			/*if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}*/
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by  t.EMP_ID)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    workshopNoRestInfo = jdbcTemplate.query(sSQL,queryList.toArray(), new QueryWEmpRestInfoMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopEmpRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return workshopNoRestInfo;
	}

	public boolean UpdateRecord(WorkshopEmpRestInfo workshopNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="UPDATE SWIPE.WORKSHOP_EMP_OVERTIME15_REST t SET t.rest_start1 = ?,t.rest_end1 = ?,t.rest_start2 = ?,t.rest_end2 = ?,t.UPDATE_USER = ?,t.UPDATE_DATE=SYSDATE  WHERE t.EMP_ID = ? AND t.enable = 'Y' AND CLASS_NO = ?";
		System.out.println("更新語句========="+workshopNoRestInfo.getCLASS_NO());
		/*if(accessRole!=null&&!accessRole.equals("")){
			if(!accessRole.equals("ALL")){
				sSQL+=" and bu = '"+accessRole+"' "; 
			}
		}*/
		try {
			if(workshopNoRestInfo!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						
						arg0.setString(1, workshopNoRestInfo.getREST_START1());
						arg0.setString(2, workshopNoRestInfo.getREST_END1());
						arg0.setString(3, workshopNoRestInfo.getREST_START2());
						arg0.setString(4, workshopNoRestInfo.getREST_END2());
						arg0.setString(5, updateUser);
						arg0.setString(6, workshopNoRestInfo.getEMP_ID());
						arg0.setString(7, workshopNoRestInfo.getCLASS_NO());
						
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update WorkshopEmpRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean checkRepeat(WorkshopEmpRestInfo workshopNoRestInfo,String accessRole) {
		// TODO Auto-generated method stub
		String sSQL="select count(*) from SWIPE.WORKSHOP_EMP_OVERTIME15_REST t where t.EMP_ID = ? and t.ENABLE = 'Y' AND class_no = ?";
		int updateRow=-1;
		try{
			
		/*	if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}*/
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(workshopNoRestInfo.getEMP_ID());
			queryList.add(workshopNoRestInfo.getCLASS_NO());
			updateRow = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
			System.out.println("查詢數據+========="+queryList);
			System.out.println("updateRow:"+updateRow);
		}catch(Exception ex) {
			logger.error("Check WorkshopNoRestInfo is failed",ex);
		}
		if(updateRow > 0) 
			   return false; 
			else
			   return true;
	}

	public boolean insertWorkShopNoRestInfo(WorkshopEmpRestInfo workshopNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		String sSQL="insert into SWIPE.WORKSHOP_EMP_OVERTIME15_REST (EMP_ID,CLASS_NO,REST_START1,REST_END1,REST_START2,REST_END2,UPDATE_USER,UPDATE_DATE,ENABLE) "
				+ "values(?,?,?,?,?,?,?，sysdate,'Y')";
		int insertRow=-1;
		System.out.println("數據庫語句=========="+sSQL);
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			if(workshopNoRestInfo!=null) {
				insertRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, workshopNoRestInfo.getEMP_ID());
						arg0.setString(2, workshopNoRestInfo.getCLASS_NO());
						arg0.setString(3, workshopNoRestInfo.getREST_START1());
						arg0.setString(4, workshopNoRestInfo.getREST_END1());
						arg0.setString(5, workshopNoRestInfo.getREST_START2());
						arg0.setString(6, workshopNoRestInfo.getREST_END2());
						arg0.setString(7, updateUser);
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update WorkshopEmpRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(insertRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean DeleteWorkshopNoRest(String empId, String classNo,String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.WORKSHOP_EMP_OVERTIME15_REST t set t.ENABLE = 'N',t.UPDATE_USER = ? where t.EMP_ID = ? and t.ENABLE='Y' AND CLASS_NO = ?";
		int disableRow=-1;
		try {
			if(empId!=null&& classNo!=null) {
				/*if(accessRole!=null&&!accessRole.equals("")){
					if(!accessRole.equals("ALL")){
						sSQL+=" and bu = '"+accessRole+"' "; 
					}
				}*/
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, empId);
						arg0.setString(3, classNo);
						
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}
}
