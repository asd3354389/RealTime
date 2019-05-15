package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.model.objectMapper.QueryIOWorkShopPW;
import com.foxlink.realtime.model.objectMapper.QueryWRestInfoMapper;

public class IOSpecialWSEmpDAO extends DAO<IOWorkShopPW>{
	private static Logger logger = Logger.getLogger(IOSpecialWSEmpDAO.class);

	@Override
	public boolean AddNewRecord(IOWorkShopPW newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(IOWorkShopPW updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IOWorkShopPW> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindRecord(String userDataCostId, int currentPage, int totalRecord, IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindRecords(IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<IOWorkShopPW> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		List<IOWorkShopPW> IOWorkShopPW = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.emp_id,t.workshopno,t.start_date,t.end_date,t.enabled from RT_SPECIAL_AREA_CONTROL t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("workshopno")){
				sSQL+=" and workshopno = ?";  
			}else if(queryCritirea.equals("emp_id")){
				sSQL+=" and Emp_id = ? ";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.workshopno,t.emp_id)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    IOWorkShopPW = jdbcTemplate.query(sSQL,queryList.toArray(), new QueryIOWorkShopPW());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return IOWorkShopPW;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from RT_SPECIAL_AREA_CONTROL t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("workshopno")){
				sSQL+=" and workshopno = ? ";  
			}else if(queryCritirea.equals("emp_id")){
				sSQL+=" and Emp_id = ? ";  
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

	public boolean addIOSpecialWSEmp(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.RT_SPECIAL_AREA_CONTROL (Emp_id,WorkShopNo,Start_Date,End_Date,Update_UserId) VALUES(?,?,?,?,?)";
		try {
			if(ioWorkShopPW!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, ioWorkShopPW.getEmp_id());
						arg0.setString(2, ioWorkShopPW.getWorkShopNo());
						arg0.setString(3, ioWorkShopPW.getStart_Date());
						arg0.setString(4, ioWorkShopPW.getEnd_Date());
						arg0.setString(5, updateUser);
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

	public boolean checkEmpIdExistence(String Emp_id) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from SWIPE.CSR_EMPLOYEE where id =? and isonwork = 0";
    	try {      	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { Emp_id },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}

	public boolean checkUserNameDuplicate(String Emp_id, String workShopNo) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_SPECIAL_AREA_CONTROL where Emp_id=? and workShopNo = ? and ENABLED='Y'";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { Emp_id,workShopNo },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

	public boolean UpdateRecord(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="UPDATE SWIPE.RT_SPECIAL_AREA_CONTROL SET Start_Date=?,End_Date=?,Update_Userid=?,update_time=sysdate WHERE Emp_id=? and WorkShopNo=? and Enabled='Y'";
		try {
			if(ioWorkShopPW!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, ioWorkShopPW.getStart_Date());
						arg0.setString(2, ioWorkShopPW.getEnd_Date());
						arg0.setString(3, updateUser);
						arg0.setString(4, ioWorkShopPW.getEmp_id());
						arg0.setString(5, ioWorkShopPW.getWorkShopNo());
					}	
				});
				System.out.print(sSQL);
			}	
			transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update IOWorkShopPW is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean DeleteIOWorkShopPW(String emp_id, String workShopNo, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update RT_SPECIAL_AREA_CONTROL set ENABLED='N',Update_Userid=?,update_time=sysdate WHERE Emp_id=? and WorkShopNo=? AND Enabled='Y'";
		int disableRow=-1;
		try {
			if(emp_id!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, emp_id);
						arg0.setString(3, workShopNo);
					}	
				});
				transactionManager.commit(txStatus);
			}
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
