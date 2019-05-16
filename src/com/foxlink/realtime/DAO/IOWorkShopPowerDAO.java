package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.IOCardMachineIP;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.QueryIOCardMaIPMapper;
import com.foxlink.realtime.model.objectMapper.QueryIOWorkShopPW;

public class IOWorkShopPowerDAO extends DAO<IOWorkShopPW>{
	private static Logger logger = Logger.getLogger(IOWorkShopPowerDAO.class);
	
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

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		int totalRecord=-1;
		// TODO Auto-generated method stub
		String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_USER_TEMP where Enabled='Y'";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    	
    		/*if(!userDataCostId.equals("ALL")){
    			String strIdArray[] = userDataCostId.split("\\*");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sSQL+=" and Costid in("+idsStr+")";
			}*/
    		if(queryCritirea.equals("ID")){
				sSQL+=" and Emp_id = ?";  
			}
			/*else if(queryCritirea.equals("Name")){
				sSQL+=" and Name = ?";  
			}else if(queryCritirea.equals("Depid")){
				sSQL+=" and Depid = ?";  
			}else if(queryCritirea.equals("Costid")){
				sSQL+=" and Costid = ?";  
			}*/
			else{
				sSQL+="";
			}
 
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find IOWorkShopPW TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	public List<IOWorkShopPW> FindAllRecord(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		List<IOWorkShopPW> AllPW = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from(select c.*,ROWNUM rn from (SELECT Emp_id,WorkShopNo,Start_Date,End_Date,Enabled from SWIPE.RT_ACCESS_USER_TEMP where Enabled='Y'";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			/*if(!userDataCostId.equals("ALL")){
				String strIdArray[] = userDataCostId.split("\\*");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sSQL+=" and Costid in("+idsStr+")";
			}*/
    		if(queryCritirea.equals("ID")){
				sSQL+=" and Emp_id = ?";  
			}
			/*else if(queryCritirea.equals("Name")){
				sSQL+=" and Name = ?";  
			}else if(queryCritirea.equals("Depid")){
				sSQL+=" and Depid = ?";  
			}else if(queryCritirea.equals("Costid")){
				sSQL+=" and Costid = ?";  
			}*/
			else{
				sSQL+="";
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += ")c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	    
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllPW = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryIOWorkShopPW());	
		  System.out.println(sSQL);
    	  } catch (Exception ex) {
    		  logger.error("Find IOWorkShopPW TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllPW;
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

	public boolean checkUserNameDuplicate(String Emp_id) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_USER_TEMP where Emp_id=? and ENABLED='Y'";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { Emp_id },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

	public boolean addIOWorkShopPW(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.RT_ACCESS_USER_TEMP (Emp_id,WorkShopNo,Start_Date,End_Date,Update_UserId) VALUES(?,?,?,?,?)";
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

	public boolean UpdateRecord(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="UPDATE SWIPE.RT_ACCESS_USER_TEMP SET WorkShopNo=?,Start_Date=?,End_Date=?,Update_Userid=? WHERE Emp_id=? and Enabled='Y'";
		try {
			if(ioWorkShopPW!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, ioWorkShopPW.getWorkShopNo());
						arg0.setString(2, ioWorkShopPW.getStart_Date());
						arg0.setString(3, ioWorkShopPW.getEnd_Date());
						arg0.setString(4, updateUser);
						arg0.setString(5, ioWorkShopPW.getEmp_id());
					}	
				});
				System.out.print(sSQL);
				transactionManager.commit(txStatus);
			}	
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

	public boolean DeleteIOWorkShopPW(String emp_id, String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update RT_ACCESS_USER_TEMP set ENABLED='N',Update_Userid=? WHERE Emp_id=? AND Enabled='Y'";
		int disableRow=-1;
		try {
			if(emp_id!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, emp_id);
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
