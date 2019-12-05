package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.AppLogin;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.IpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.AppLoginMapper;
import com.foxlink.realtime.model.objectMapper.EmpInfoMapper;
import com.foxlink.realtime.model.objectMapper.IpBindingCostSCMapper;

public class AdminActionDao extends DAO<Emp> {
	private static Logger logger=Logger.getLogger(AdminActionDao.class);

	@Override
	public boolean AddNewRecord(Emp newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Emp updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Emp> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindRecord(String userDataCostId, int currentPage, int totalRecord, Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindRecords(Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, Emp t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Emp> FindAllRecords(String queryCritirea, String queryParam) throws Exception{
		// TODO Auto-generated method stub
		List<Emp> empList = null;
		String sql = "select id,name,costid,deptid,depid,cardid,to_char(t.updatedate,'YYYY-MM-DD') updatedate from csr_employee t where ";
			sql+=" t.id in(";  
				  String [] depIdArray = queryParam.split(",");
		            for(int i=0;i<depIdArray.length;i++){
		            	sql+="'"+depIdArray[i].trim()+"'";
		                if(depIdArray.length-1!=i)
		                	sql+=",";
		                else
		                	sql+=") ";				                
		               }
		List <Object> queryList=new  ArrayList<Object>();
		empList = jdbcTemplate.query(sql, queryList.toArray(), new EmpInfoMapper());
		return empList;
	}

	public List<String> FindHolidayYList() {
		// TODO Auto-generated method stub
		List<String> HolidayYList = null;
		String sql = "select to_char(to_date(t.holiday_date,'yyyy-mm-dd'),'yyyy') year from FOXLINK_LEGAL_HOLIDAYS t "
				+ " group by to_char(to_date(t.holiday_date,'yyyy-mm-dd'),'yyyy') order by year desc ";
		HolidayYList = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				 return rs.getString(1);
			}
		});
		return HolidayYList;
	}

	public List<String> FindHoliday(String queryParam, String holidayType) {
		// TODO Auto-generated method stub
		List<String> HolidatList = null;
		String sql = "select t.holiday_date from FOXLINK_LEGAL_HOLIDAYS t "
				+ " where to_char(to_date(t.holiday_date,'yyyy-mm-dd'),'yyyy') = ? and t.holiday_type = ?  order by holiday_date asc ";
		List <Object> queryList=new  ArrayList<Object>();
		queryList.add(queryParam);
		queryList.add(holidayType);
		
		HolidatList = jdbcTemplate.query(sql,new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				 return rs.getString(1);
			}
		},queryList.toArray());
		return HolidatList;
	}

	public Boolean DeleteHoliday(String delete_date) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="delete from FOXLINK_LEGAL_HOLIDAYS t where t.holiday_date = ?";
		try {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, delete_date);
					}	
				});
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update Account is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean AddHoliday(String holidayType, String holidayDate) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="insert into FOXLINK_LEGAL_HOLIDAYS(holiday_date,holiday_type) values(?,?)";
		try {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, holidayDate);
						arg0.setString(2, holidayType);
					}	
				});
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update Account is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean checkHoliday(String holidayDate) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from FOXLINK_LEGAL_HOLIDAYS t where t.holiday_date = ? ";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { holidayDate },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

	public int getIpBindingCostSCTotalRecord(String queryCritirea, String queryParam, String updateUser,
			String userDataCostId, String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEVICE_DEPT_BINDING_ZJ t where t.enabled = 'Y' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("ip")){
				sSQL+=" and deviceip = ?";  
			}else if("costid".equals(queryCritirea)){
				sSQL+=" and costid = ?";  
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

	public List<IpBinding> FindQueryIpBindingCostSCRecord(int currentPage, int totalRecord,
			String queryCritirea, String queryParam, String updateUser, String userDataCostId, String accessRole) {
		// TODO Auto-generated method stub
		List<IpBinding> IpBindingCostSCInfo = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.deviceip,t.costid from DEVICE_DEPT_BINDING_ZJ t "
				+ " where t.enabled = 'Y' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("ip")){
				sSQL+=" and deviceip = ? ";  
			}else if("costid".equals(queryCritirea)){
				sSQL+=" and costid = ? ";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by t.deviceip,t.costid)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    IpBindingCostSCInfo = jdbcTemplate.query(sSQL,queryList.toArray(), new IpBindingCostSCMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return IpBindingCostSCInfo;
	}


	public boolean insertIpBindingCostSCInfo(String ip, String[] costid, String updateUser) {
		// TODO Auto-generated method stub
		String DSQL = "update DEVICE_DEPT_BINDING_ZJ t set t.enabled = 'N',t.update_userid=?,t.update_time=sysdate where t.deviceip = ? and t.costid = ?";
		String sSQL="insert into DEVICE_DEPT_BINDING_ZJ(DEVICEIP,COSTID,UPDATE_USERID,UPDATE_TIME,ENABLED) values(?,?,?,sysdate,'Y')";
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			jdbcTemplate.batchUpdate(DSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, updateUser);
					ps.setString(2, ip);
					ps.setString(3, costid[i]);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return costid.length;
				}
			});
			
			jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, ip);
					ps.setString(2, costid[i]);
					ps.setString(3, updateUser);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return costid.length;
				}
			});
			transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update WorkshopNoRestInfo is failed",ex);
			ex.printStackTrace();
			transactionManager.rollback(txStatus);
			return false;
		}			
		
		return true; 
		
	}

	public boolean DeleteIpBindingCostSC(String ip, String costid, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="update DEVICE_DEPT_BINDING_ZJ t set t.enabled = 'N',t.update_userid=?,t.update_time=sysdate where t.deviceip = ? and t.costid = ?";
		try {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, ip);
						arg0.setString(3, costid);
					}	
				});
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update Account is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public int getAppLoginTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from APP_LOGIN_CONTROL where 1 = 1 ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("ip")){
				sSQL+=" and com_ip = ? ";  
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

	public List<AppLogin> FindQueryAppLoginRecord(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		List<AppLogin> AppLoginInfo = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select * from APP_LOGIN_CONTROL t "
				+ " where 1 = 1 ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("ip")){
				sSQL+=" and com_ip = ? ";  
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by t.control_except,t.com_ip)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		    
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    AppLoginInfo = jdbcTemplate.query(sSQL,queryList.toArray(), new AppLoginMapper());	
    	  } catch (Exception ex) {
    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AppLoginInfo;
	}

	public boolean DeleteAppLogin(String ip, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="delete from APP_LOGIN_CONTROL t where t.com_ip = ?";
		try {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, ip);
					}	
				});
				transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update Account is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public boolean insertAppLoginInfo(AppLogin appLogin, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		String DSQL = "delete from APP_LOGIN_CONTROL t where t.com_ip = ?";
		String sSQL="insert into APP_LOGIN_CONTROL(com_name,COM_IP,DUTY_COSTID,DUTY_PERSON,DUTY_TEL,CONTROL_EXCEPT) values(?,?,?,?,?,?)";
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		try {
			jdbcTemplate.update(DSQL,new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement arg0) throws SQLException {
					// TODO Auto-generated method stub
					arg0.setString(1, appLogin.getIp());
				}	
			});
			
			updateRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement arg0) throws SQLException {
					// TODO Auto-generated method stub
					arg0.setString(1, appLogin.getCom_name());
					arg0.setString(2, appLogin.getIp());
					arg0.setString(3, appLogin.getCostid());
					arg0.setString(4, appLogin.getId());
					arg0.setString(5, appLogin.getTel());
					arg0.setString(6, appLogin.getControl_type());
				}	
			});
			transactionManager.commit(txStatus);
		}
		catch(Exception ex) {
			logger.error("Update WorkshopNoRestInfo is failed",ex);
			ex.printStackTrace();
			transactionManager.rollback(txStatus);
			return false;
		}			
		
		if(updateRow > 0) 
			   return true; 
			else
			   return false;
		
	}

}
