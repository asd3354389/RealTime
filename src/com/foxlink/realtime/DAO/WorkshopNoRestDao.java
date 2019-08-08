package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.model.objectMapper.QueryEMPMapper;
import com.foxlink.realtime.model.objectMapper.QueryWRestInfoMapper;
import com.foxlink.realtime.service.WorkshopNoRestService;

public class WorkshopNoRestDao extends DAO<WorkshopNoRestInfo>{
	private static Logger logger=Logger.getLogger(WorkshopNoRestDao.class);

	@Override
	public boolean AddNewRecord(WorkshopNoRestInfo newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(WorkshopNoRestInfo updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WorkshopNoRestInfo> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopNoRestInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopNoRestInfo> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			WorkshopNoRestInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopNoRestInfo> FindRecords(WorkshopNoRestInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, WorkshopNoRestInfo t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId,String accessRole) {
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.WORKSHOPNO_REST_INFO t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("workshopno")){
				sSQL+=" and workshopno = ?";  
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
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	public List<WorkshopNoRestInfo> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam, String updateUser, String userDataCostId,String accessRole) {
		List<WorkshopNoRestInfo> workshopNoRestInfo = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.workshopno,t.rest_start1,t.rest_end1,t.rest_start2,t.rest_end2,t.rest_start3,t.rest_end3,t.rest_start4,t.rest_end4 from WORKSHOPNO_REST_INFO t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("workshopno")){
				sSQL+=" and workshopno = ?";  
			}
			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.workshopno)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    workshopNoRestInfo = jdbcTemplate.query(sSQL,queryList.toArray(), new QueryWRestInfoMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return workshopNoRestInfo;
	}

	public boolean UpdateRecord(WorkshopNoRestInfo workshopNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="update WORKSHOPNO_REST_INFO t set t.rest_start1 = ?,t.rest_end1 = ?,t.rest_start2 = ?,t.rest_end2 = ?,t.rest_start3 = ?,t.rest_end3 = ?,"
				+ "t.rest_start4 = ?,t.rest_end4 = ?,t.update_userid = ?,t.update_time=sysdate "
				+ "where t.workshopno = ? and t.enabled = 'Y'";
		if(accessRole!=null&&!accessRole.equals("")){
			if(!accessRole.equals("ALL")){
				sSQL+=" and bu = '"+accessRole+"' "; 
			}
		}
		try {
			if(workshopNoRestInfo!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, workshopNoRestInfo.getRest_start1());
						arg0.setString(2, workshopNoRestInfo.getRest_end1());
						arg0.setString(3, workshopNoRestInfo.getRest_start2());
						arg0.setString(4, workshopNoRestInfo.getRest_end2());
						arg0.setString(5, workshopNoRestInfo.getRest_start3());
						arg0.setString(6, workshopNoRestInfo.getRest_end3());
						arg0.setString(7, workshopNoRestInfo.getRest_start4());
						arg0.setString(8, workshopNoRestInfo.getRest_end4());
						arg0.setString(9, updateUser);
						arg0.setString(10, workshopNoRestInfo.getWorkshopno());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean checkRepeat(WorkshopNoRestInfo workshopNoRestInfo,String accessRole) {
		// TODO Auto-generated method stub
		String sSQL="select count(*) from WORKSHOPNO_REST_INFO t where t.workshopno = ? and t.enabled = 'Y'";
		int updateRow=-1;
		try{
			
			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(workshopNoRestInfo.getWorkshopno());
			updateRow = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
			System.out.println("updateRow:"+updateRow);
		}catch(Exception ex) {
			logger.error("Check WorkshopNoRestInfo is failed",ex);
		}
		if(updateRow > 0) 
			   return false; 
			else
			   return true;
	}

	public boolean insertWorkShopNoRestInfo(WorkshopNoRestInfo workshopNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		String sSQL="insert into WORKSHOPNO_REST_INFO(WORKSHOPNO,REST_START1,REST_END1,REST_START2,REST_END2,REST_START3,REST_END3,REST_START4,REST_END4,UPDATE_USERID,UPDATE_TIME,Enabled,BU) "
				+ "values(?,?,?,?,?,?,?,?,?,?,sysdate,'Y',?)";
		int insertRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			if(workshopNoRestInfo!=null) {
				insertRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, workshopNoRestInfo.getWorkshopno());
						arg0.setString(2, workshopNoRestInfo.getRest_start1());
						arg0.setString(3, workshopNoRestInfo.getRest_end1());
						arg0.setString(4, workshopNoRestInfo.getRest_start2());
						arg0.setString(5, workshopNoRestInfo.getRest_end2());
						arg0.setString(6, workshopNoRestInfo.getRest_start3());
						arg0.setString(7, workshopNoRestInfo.getRest_end3());
						arg0.setString(8, workshopNoRestInfo.getRest_start4());
						arg0.setString(9, workshopNoRestInfo.getRest_end4());
						arg0.setString(10, updateUser);
						arg0.setString(11, accessRole);
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update WorkshopNoRestInfo is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(insertRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean DeleteWorkshopNoRest(String workshopNo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update WORKSHOPNO_REST_INFO t set t.enabled = 'N',t.update_userid = ? where t.workshopno = ? and t.enabled='Y'";
		int disableRow=-1;
		try {
			if(workshopNo!=null) {
				if(accessRole!=null&&!accessRole.equals("")){
					if(!accessRole.equals("ALL")){
						sSQL+=" and bu = '"+accessRole+"' "; 
					}
				}
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, workshopNo);
						
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
