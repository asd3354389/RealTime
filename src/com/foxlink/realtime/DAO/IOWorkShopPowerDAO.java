package com.foxlink.realtime.DAO;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId,String accessRole) {
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
    		if(queryCritirea.equals("CardId")){
				sSQL+=" and CardId = ?";  
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
    		if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
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
			String updateUser, String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		List<IOWorkShopPW> AllPW = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from(select c.*,ROWNUM rn from (SELECT Emp_id,WorkShopNo,Start_Date,End_Date,Enabled,Cardid,Remark from SWIPE.RT_ACCESS_USER_TEMP where Enabled='Y'";
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
    		//CardId
    		if(queryCritirea.equals("CardId")){
				sSQL+=" and CardId = ?";  
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
    		if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += ")c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	  
		    System.out.println("列表顯示數據+================================>>>>>"+sSQL);
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
    	String sSQL = "select count(*) from SWIPE.CSR_EMPLOYEE where id =upper(?) and isonwork = 0";
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

	//判斷同一卡號和車間是否有數據
	public boolean checkCardIdDuplicate(String CardId, String workshopNo,String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_USER_TEMP where CardId=? and workshopno = ? and ENABLED='Y'";
    	System.out.println(" 查詢語句"+sSQL+"卡號"+CardId+"車間號"+workshopNo);
    	try {    	    	
    		if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CardId,workshopNo },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}
	//判斷同一工號和車間是否有數據
		public boolean checkUserNameDuplicate(String Emp_id, String workshopNo,String accessRole) {
			// TODO Auto-generated method stub
			int totalRecord=-1;
	    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_USER_TEMP where Emp_id=upper(?)and workshopno = ? and ENABLED='Y'";
	    	try {    
	    		if(accessRole!=null&&!accessRole.equals("")){
					if(!accessRole.equals("ALL")){
						sSQL+=" and bu = '"+accessRole+"' "; 
					}
				}
	    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { Emp_id,workshopNo },Integer.class);	   	
	    	  } catch (Exception ex) {
	    		  ex.printStackTrace();
	    		  }
	    	/*System.out.println(sSQL);*/
	    	 if(totalRecord > 0) 
				   return false; 
			 else
				 return true;
		}
	//員工進出車間權限
	public boolean addIOWorkShopPW(IOWorkShopPW[] ioWorkShopPW, String updateUser,String accessRole) {
		// TODO Auto-generated method stub

		int createRow=0;

		//先刪除之前的
		DeleteSettingMessage(ioWorkShopPW,accessRole);

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		//upper(Emp_id)
		String sSQL="INSERT INTO SWIPE.RT_ACCESS_USER_TEMP (Emp_id,WorkShopNo,Start_Date,End_Date,Update_UserId,CardId,Remark,BU) VALUES(upper(?),?,?,?,?,?,?,?)";
		try {
			if(ioWorkShopPW!=null) {

				jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub

						ps.setString(1, ioWorkShopPW[i].getEmp_id());
						ps.setString(2, ioWorkShopPW[i].getWorkShopNo());
						ps.setString(3, ioWorkShopPW[i].getStart_Date());
						ps.setString(4, ioWorkShopPW[i].getEnd_Date());
						ps.setString(5, updateUser);
						ps.setString(6, ioWorkShopPW[i].getCardId());
						ps.setString(7, ioWorkShopPW[i].getRemark());
						ps.setString(8, accessRole);
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return ioWorkShopPW.length;
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
//addIOWorkShopPWOther
	
	public boolean addIOWorkShopPWOther(IOWorkShopPW[] ioWorkShopPW, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		int createRow=0;
		 System.out.println("進入方法----------=======================>");
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		//先刪除之前的
		DeleteSettingMessageCard(ioWorkShopPW,accessRole);
		String sSQL="INSERT INTO SWIPE.RT_ACCESS_USER_TEMP (Emp_id,WorkShopNo,Start_Date,End_Date,Update_UserId,CardId,Remark,BU) VALUES(upper(?),?,?,?,?,?,?,?)";
		try {
			if(ioWorkShopPW!=null) {

				jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, ioWorkShopPW[i].getEmp_id());
						ps.setString(2, ioWorkShopPW[i].getWorkShopNo());
						ps.setString(3, ioWorkShopPW[i].getStart_Date());
						ps.setString(4, ioWorkShopPW[i].getEnd_Date());
						ps.setString(5, updateUser);
						ps.setString(6, ioWorkShopPW[i].getCardId());
						ps.setString(7, ioWorkShopPW[i].getRemark());
						ps.setString(8, accessRole);
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return ioWorkShopPW.length;
					}
				});
				 transactionManager.commit(txStatus);
			}			
		}
		catch(Exception ex) {
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
	public boolean UpdateRecord(IOWorkShopPW ioWorkShopPW, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		//String sSQL="UPDATE SWIPE.RT_ACCESS_USER_TEMP SET WorkShopNo=?,Start_Date=?,End_Date=?,Update_Userid=?,Remark=? WHERE Emp_id=? and Enabled='Y'";
		//String sSQL="UPDATE SWIPE.RT_ACCESS_USER_TEMP SET WorkShopNo=?,Start_Date=?,End_Date=?,Update_Userid=?,Remark=?";
		String sSQL="UPDATE SWIPE.RT_ACCESS_USER_TEMP SET Start_Date=?,End_Date=?,Update_Userid=?,update_time=sysdate,Remark=?";
		System.out.println("更新信息================="+ioWorkShopPW.getCardId());
		try {
			if (ioWorkShopPW.getEmp_id() == null||ioWorkShopPW.getEmp_id() == "" ||ioWorkShopPW.getEmp_id().equals("null")) {
				System.out.println("=====================>>>>>>進入方法");
				sSQL += "WHERE CardId=? and Enabled='Y' and WorkShopNo=? and bu=?";
				//if(ioWorkShopPW!=null) {
					updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement arg0) throws SQLException {
							// TODO Auto-generated method stub
							arg0.setString(1, ioWorkShopPW.getStart_Date());
							arg0.setString(2, ioWorkShopPW.getEnd_Date());
							arg0.setString(3, updateUser);
							arg0.setString(4, ioWorkShopPW.getRemark());
							arg0.setString(5, ioWorkShopPW.getCardId());
							arg0.setString(6, ioWorkShopPW.getWorkShopNo());
							arg0.setString(7, accessRole);
						}	
					});
					System.out.print(sSQL);
					transactionManager.commit(txStatus);
				
				//}	
			}else {
				sSQL += "WHERE Emp_id=? and Enabled='Y' and WorkShopNo=? and bu=?";
				//if(ioWorkShopPW!=null) {
					updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement arg0) throws SQLException {
							// TODO Auto-generated method stub
							arg0.setString(1, ioWorkShopPW.getStart_Date());
							arg0.setString(2, ioWorkShopPW.getEnd_Date());
							arg0.setString(3, updateUser);
							arg0.setString(4, ioWorkShopPW.getRemark());
							arg0.setString(5, ioWorkShopPW.getEmp_id());
							arg0.setString(6, ioWorkShopPW.getWorkShopNo());
							arg0.setString(7, accessRole);
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

	public boolean DeleteIOWorkShopPW(String emp_id, String updateUser,String CardID,String WorkShopNo,String accessRole) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="update RT_ACCESS_USER_TEMP set ENABLED='N',Update_Userid=? ";
		int disableRow=-1;
		System.out.println("工号========="+emp_id+"卡号==========="+CardID);
		try {
			if(emp_id.equals("null")||emp_id.equals("")||emp_id==null) {
				sSQL += "WHERE CardId=? AND Enabled='Y' AND WORKSHOPNO = ? and bu=?";
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, CardID);
						arg0.setString(3, WorkShopNo);
						arg0.setString(4, accessRole);
					}	
				});
				System.out.print(sSQL);
				transactionManager.commit(txStatus);
				
			}else {
				sSQL += "WHERE Emp_id=? AND Enabled='Y' AND WORKSHOPNO = ? and bu=?";
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, emp_id);
						arg0.setString(3, WorkShopNo);
						arg0.setString(4, accessRole);
					}	
				});
				System.out.print(sSQL);
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
	//刪除員工信息
	private void DeleteSettingMessage(IOWorkShopPW[] ioWorkShopPW,String accessRole) {

		// TODO Auto-generated method stub	
				int updateRow=-1;		
				//String sSQL = "DELETE FROM SWIPE.RT_ACCESS_USER_TEMP WHERE EMP_ID = ? AND WORKSHOPNO = ?";
				String sSQL = "DELETE FROM SWIPE.RT_ACCESS_USER_TEMP WHERE EMP_ID = ? AND WORKSHOPNO = ? and bu=?";
				System.out.println("批量删除==========>>>>>>>>>>"+sSQL);
				//IOWorkShopPW ioWorkShopPW2 = null;
				try {
//					
							System.out.println("刪除員工信息=========="+sSQL);
							jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
								
								@Override
								public void setValues(PreparedStatement ps,int i ) throws SQLException {
									// TODO Auto-generated method stub
									ps.setString(1, ioWorkShopPW[i].getEmp_id());
									ps.setString(2, ioWorkShopPW[i].getWorkShopNo());	
									ps.setString(3, accessRole);	
								}
								
								@Override
								public int getBatchSize() {
									// TODO Auto-generated method stub
									return ioWorkShopPW.length;
								}
							});
							

				} catch (Exception e) {
					// TODO: handle exception
					logger.error("删除失敗，原因："+e);
					e.printStackTrace();
					
				}
	}
	//刪除卡號信息
	private void DeleteSettingMessageCard(IOWorkShopPW[] ioWorkShopPW,String accessRole) {

		// TODO Auto-generated method stub	
				int updateRow=-1;		
				
				String sSQL = "DELETE FROM SWIPE.RT_ACCESS_USER_TEMP WHERE CARDID = ? AND WORKSHOPNO = ? and bu=?";
				System.out.println("批量删除==========>>>>>>>>>>"+sSQL);
				
				try {

							System.out.println("刪除員工信息=========="+sSQL);
						
							
							jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
								
								@Override
								public void setValues(PreparedStatement ps,int i ) throws SQLException {
									// TODO Auto-generated method stub
									ps.setString(1, ioWorkShopPW[i].getCardId());
									ps.setString(2, ioWorkShopPW[i].getWorkShopNo());	
									ps.setString(3, accessRole);
								}
								
								@Override
								public int getBatchSize() {
									// TODO Auto-generated method stub
									return ioWorkShopPW.length;
								}
							});

					
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("删除失敗，原因："+e);
					e.printStackTrace();
					
				}
	}

}
