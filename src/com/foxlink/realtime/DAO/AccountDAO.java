package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.User;
import com.foxlink.realtime.model.objectMapper.UserMapper;

public class AccountDAO extends DAO<User> {
	private static Logger logger=Logger.getLogger(AccountDAO.class);	
	
	public AccountDAO(){
		super();
	}
	/***
     * 獲取賬號列表
     * 
     * @Author Yaru Song 
     * @return
    */

	@Override
	public List<User> FindAllRecords(int currentPage, int totalRecord,String queryCritirea,String queryParam) {
		// TODO Auto-generated method stub
		List<User> AllUsers = null;
		String sSQL = "select * from ("
				+ "select c.*,rownum as rnum,COUNT (*) OVER () totalPage from ("
				+ "select  a.USERNAME,a.PASSWORD,"
				+ "a.CHINESENAME,a.ASSISTANT_ID,a.DEPARTMENTCODE,a.COSTID,a.PHONE_TEL,a.CREATE_DATE,"
				+ "a.UPDATE_USER,a.ENABLED,a.CHPASS_TIME,"
				+ "a.QUERY_COSTID,b.role from SWIPE.USER_DATA a left join SWIPE.user_roles b on a.username=b.username  where ENABLED=1";
		try {	
			if(queryCritirea.equals("UserName")){
				sSQL+=" and a.username = ?";  
			}
			else if(queryCritirea.equals("ChineseName")){
				sSQL+=" and a.ChineseName = ?";  
			}
			else{
				sSQL+="";
			}
			
			Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by a.username,b.role ) c ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
		    List <Object> queryList=new  ArrayList<Object>();  
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    AllUsers = jdbcTemplate.query(sSQL, queryList.toArray(), new UserMapper());			    
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Find Account are failed",ex);
		}
		return AllUsers;
	}

    /* 
     * 获得总记录数
     */
	@Override
    public int getTotalRecord(String queryCritirea,String queryParam) {
    	int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.USER_DATA where ENABLED=1";
    	try { 
    		if(queryCritirea.equals("UserName")){
				sSQL+=" and username = ?";  
			}
			else if(queryCritirea.equals("ChineseName")){
				sSQL+=" and ChineseName = ?";  
			}
			else{
				sSQL+="";
			}
   		  List <Object> queryList=new  ArrayList<Object>();
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		 totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		 
    	  } catch (Exception ex) {
    		  logger.error("Find Account TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
    }

   /*  
     * 获取当前页数据
     
    @SuppressWarnings("unchecked")
    public Page getPage(int pageNum, int totalRecord) {
    	String sSQL = "select * from (select a.*,rownum as rnum from (select * from USER_DATA where ENABLED=1 order by username) a)";
        Page page = new Page(pageNum, totalRecord);
        int endIndex=page.getStartIndex() + page.getPageSize();
        sSQL = sSQL + " where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;  
        List list=jdbcTemplate.query(sSQL,   new UserMapper());    	      
        page.setList(list);
        return page;
    }
*/
	@Override
	public boolean AddNewRecord(User newRecord) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.USER_DATA (UserName,PassWord,ChineseName,Assistant_Id,DepartmentCode,CostId,Phone_Tel,Update_User) VALUES(?,?,?,?,?,?,?,?)";
		try {
			if(newRecord!=null && AddAccountRoles(newRecord)) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, newRecord.getUSERNAME());
						arg0.setString(2, newRecord.getUSERNAME());
						arg0.setString(3, newRecord.getCHINESENAME());
						arg0.setString(4, newRecord.getASSISTANT_ID());
						arg0.setString(5, newRecord.getDEPARTMENTCODE());
						arg0.setString(6, newRecord.getCOSTID());
						arg0.setString(7, newRecord.getPHONE_TEL());
						arg0.setString(8, newRecord.getUPDATE_USER());
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
	
	public boolean AddAccountRoles(User newRecord) {
		// TODO Auto-generated method stub
		int createRow=-1;
		
		String sSQL="INSERT INTO SWIPE.USER_ROLES (UserName,Role,Update_User) VALUES(?,?,?)";
		try {
			if(newRecord!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, newRecord.getUSERNAME());
						arg0.setString(2, newRecord.getROLE());
						arg0.setString(3, newRecord.getUPDATE_USER());						
					}	
				});
			}			
		}
		catch(Exception ex) {
			logger.error(ex);
		}
		
		 if(createRow > 0) 
			   return true; 
		 else
			 return false;
	}

	@Override
	public boolean UpdateRecord(User updateRecord) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL="UPDATE SWIPE.USER_DATA SET Assistant_Id=?,CostId=?,Phone_Tel=?,Update_User=?,Departmentcode=? WHERE UserName=?";
		try {
			if(updateRecord!=null) {
				String sSQLrole="UPDATE USER_Roles SET ROLE=?,Update_User=? WHERE UserName=?";
				updateRole=jdbcTemplate.update(sSQLrole,new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement arg0) throws SQLException {
							// TODO Auto-generated method stub
							arg0.setString(1, updateRecord.getROLE());
							arg0.setString(2, updateRecord.getUPDATE_USER());
							arg0.setString(3, updateRecord.getUSERNAME());
						}	
					});
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateRecord.getASSISTANT_ID());
						arg0.setString(2, updateRecord.getCOSTID());
						arg0.setString(3, updateRecord.getPHONE_TEL());
						arg0.setString(4, updateRecord.getUPDATE_USER());
						arg0.setString(5, updateRecord.getDEPARTMENTCODE());
						arg0.setString(6, updateRecord.getUSERNAME());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update Account is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0 && updateRole >0) 
				   return true; 
				else
				   return false;
	}

	@Override
	public boolean DeleteRecord(String recordID,String updateUser) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update SWIPE.USER_DATA set ENABLED=0,UPDATE_USER=? WHERE USERNAME=?";
		int disableRow=-1;
		try {
			if(recordID!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, recordID);
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable user_data is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}

	

	public boolean checkUserNameDuplicate(String userName){
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.USER_DATA where userName=? and ENABLED=1";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { userName },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}
	

	
	public Map<String, Object> GetLoginInfo(String userName) {
		Map<String, Object>  selectUsers = null;
		String sSQL = " select username,chinesename,assistant_id,departmentcode,costid from SWIPE.user_data where username=? and ENABLED=1";
		try {		
			selectUsers= jdbcTemplate.queryForMap(sSQL, new Object[]{userName}); 
			/***
			 * spring jdbc 的queryForMap() 或者queryForObject().
			 * 这两个函数查询数据库的时候只能查询一条数据 而且 必须在数据库中有一条符合条件的数据。
			 * 如果没有符合查询条件的数据或者查询出多条数据都会报 Incorrect result size 错误.
			 */
		} catch (Exception ex) {
			//ex.printStackTrace();
			//logger.error("Search User Infos is failed",ex);
			return null;
		}
		return selectUsers;
	}
	
	@Override
	public List<User> FindRecords(User changePasswordUser) {
		// TODO Auto-generated method stub
		List<User> changePasswordUsers = null;
		String sSQL = "select  a.USERNAME,a.PASSWORD,"
				+ "a.CHINESENAME,a.ASSISTANT_ID,a.DEPARTMENTCODE,a.COSTID,a.PHONE_TEL,a.CREATE_DATE,"
				+ "a.UPDATE_USER,a.ENABLED,a.CHPASS_TIME,"
				+ "a.QUERY_COSTID,b.role from SWIPE.USER_DATA a left join SWIPE.user_roles b on a.username=b.username"
				+ " where a.userName=? and a.ENABLED=1";
		try {					
			changePasswordUsers = jdbcTemplate.query(sSQL, new Object[] { changePasswordUser.getUSERNAME() }, new UserMapper());			    
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Find changePasswordUser are failed",ex);
		}
		return changePasswordUsers;
	}
	
	public boolean UpdateAccountPassWord(User updateRecord) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="UPDATE SWIPE.USER_DATA SET password=?,CHPASS_TIME=sysdate WHERE UserName=? and ChineseName=? "
				+ "and DEPARTMENTCODE=? and ASSISTANT_ID=? and ENABLED=1";
		try {
			if(updateRecord!=null) {
				updateRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateRecord.getPASSWORD());
						arg0.setString(2, updateRecord.getUSERNAME());
						arg0.setString(3, updateRecord.getCHINESENAME());
						arg0.setString(4, updateRecord.getDEPARTMENTCODE());
						arg0.setString(5, updateRecord.getASSISTANT_ID());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update Account Password is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(updateRow > 0) 
			   return true; 
		 else
			 return false;

	}
	
	@Override
	public List<User> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<User> FindRecord(String userDataCostId, int currentPage, int totalRecord, User t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getTotalRecords(String userDataCostId, User t) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
