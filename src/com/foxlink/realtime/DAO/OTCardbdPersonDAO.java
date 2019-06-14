package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.GetDepidMapper;
import com.foxlink.realtime.model.objectMapper.QueryOTCardBdMapper;


public class OTCardbdPersonDAO extends DAO<Emp> {
	private static Logger logger = Logger.getLogger(OTCardbdPersonDAO.class);
	

	
	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPARTURE_CARD_INFO a ,(select DISTINCT(t.costid) from DEPT_RELATION t";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(!userDataCostId.equals("ALL")){
    			String strIdArray[] = userDataCostId.split("\\*");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sSQL+=" where t.Costid in("+idsStr+")";
				if(queryCritirea.equals("Depid")){
					sSQL+=" and t.Depid = ?";  
				}else if(queryCritirea.equals("Costid")){
					sSQL+=" and t.Costid = ?";  
				}
				else{
					sSQL+="";
				}
			}else {
				if(queryCritirea.equals("Depid")){
					sSQL+=" where t.Depid = ?";  
				}else if(queryCritirea.equals("Costid")){
					sSQL+=" where t.Costid = ?";  
				}
				else{
					sSQL+="";
				}
			}
    		sSQL += ")b where a.CostId = b.costid and a.enabled='Y' ";
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

	public List<OTCardBD> FindAllRecord(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		List<OTCardBD> AllEmp = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from(select c.*,rownum rn from (select a.d_cardid, a.CostId,b.costid CostNo,a.default_workshopno,a.enabled FROM SWIPE.DEPARTURE_CARD_INFO a ,(select DISTINCT(t.costid) from DEPT_RELATION t";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(!userDataCostId.equals("ALL")){
				String strIdArray[] = userDataCostId.split("\\*");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sSQL+=" where Costid in("+idsStr+")";
				 if(queryCritirea.equals("Depid")){
						sSQL+=" and Depid = ?";  
					}else if(queryCritirea.equals("Costid")){
						sSQL+=" and Costid = ?";  
					}
					else{
						sSQL+="";
					}
			}else {
				 if(queryCritirea.equals("Depid")){
						sSQL+=" where Depid = ?";  
					}else if(queryCritirea.equals("Costid")){
						sSQL+=" where Costid = ?";  
					}
					else{
						sSQL+="";
					}
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by t.costid)b where a.CostId = b.costid and a.enabled='Y')c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	    
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllEmp = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryOTCardBdMapper());	
		  System.out.println(sSQL);
    	  } catch (Exception ex) {
    		  logger.error("Find PersonList TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllEmp;
	}

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

	public boolean checkUserNameDuplicate(String CostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPT_RELATION where CostId=? ";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CostId },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}
	
	/*public boolean checkCostIdDuplicate(String CostId,String D_CarId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPT_RELATION where CostId=? and D_CarId=?";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CostId,D_CarId },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	System.out.println(sSQL);
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}*/

	public boolean OTCardbdPerson(OTCardBD otCardbd) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.DEPARTURE_CARD_INFO (D_CardId,CostId,Default_WorkShopNo,Update_UserId) VALUES(?,?,?,?)";
		try {
			if(otCardbd!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, otCardbd.getD_CardID());
						arg0.setString(2, otCardbd.getCostId());
						arg0.setString(3, otCardbd.getDefault_WorkShop());
						arg0.setString(4,otCardbd.getUpdate_UserId());
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

	public boolean checkEmpIdExistence(String EmpId, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from SWIPE.CSR_EMPLOYEE where id =? and isonwork = 0";
    	try {   
    		if(!userDataCostId.equals("ALL")){
				String strIdArray[] = userDataCostId.split("\\*");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sSQL+=" and Costid in("+idsStr+")";
			}
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { EmpId },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	 if(totalRecord > 0) 
			   return true; 
		 else
			 return false;
	}

	public boolean UpdateBdOTCard(OTCardBD otCardbd, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "update SWIPE.DEPARTURE_CARD_INFO set D_CardId =?,Default_WorkShopNo=?,update_time = sysdate,Update_UserId=? where enabled='Y' and CostId =? and D_CardId=?";
		try {
			if(otCardbd!=null) {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, otCardbd.getD_CardID());
						ps.setString(2, otCardbd.getDefault_WorkShop());
						ps.setString(3, updateUser);
						ps.setString(4, otCardbd.getCostId());
						ps.setString(5, otCardbd.getO_CardID());
					}	
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("Update BdOTCard is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}
	
	public boolean RelieveOTCard(OTCardBD[] otCardbd, String updateUser) {
		// TODO Auto-generated method stub	
		int updateRow=0;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);		
		String sSQL = "delete from SWIPE.DEPARTURE_CARD_INFO where enabled='Y' and CostId =? and D_CardId=?";
		try {
			if(otCardbd!=null) {
				jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, otCardbd[i].getCostId());
						ps.setString(2, otCardbd[i].getD_CardID());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return otCardbd.length;
					}
				});
				transactionManager.commit(txStatus);
			}	
		}
		catch(Exception ex) {
			logger.error("delete BdOTCard is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow == 0) 
				   return true; 
				else
				   return false;
	}
	
	public boolean OTCardNbdPerson(OTCardBD[] otCardbd,String updateUser) {
		// TODO Auto-generated method stub
		int result=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.DEPARTURE_CARD_INFO_HT (D_CardId,CostId,Default_WorkShopNo,Update_UserId,enabled) VALUES(?,?,?,?,'N')";
		try {
			
			jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, otCardbd[i].getD_CardID());
					ps.setString(2, otCardbd[i].getCostId());
					ps.setString(3, otCardbd[i].getDefault_WorkShop());
					ps.setString(4, updateUser);
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return otCardbd.length;
				}
			});
				transactionManager.commit(txStatus);
			}			
		catch(Exception ex) {
			logger.error("HT表插入狀態為N數據失敗，原因："+ex);
			transactionManager.rollback(txStatus);
		}
		if(result == 0) 
			   return true; 
			else
			   return false;
	}

	public List<GetDepid> ShowDeptNo(String CostId) {
		// TODO Auto-generated method stub
		List<GetDepid> AllDept = null;
		String sSQl = "select t.depid from DEPT_RELATION t ";
		try {
			if(!CostId.equals("ALL")) {
				sSQl+= " where t.CostId = '"+CostId+"'";
			}
			AllDept = jdbcTemplate.query(sSQl,new GetDepidMapper());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find Dept TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return AllDept;
	}

	public boolean checkDcardDuplicate(String CostId, String D_CardId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPARTURE_CARD_INFO where CostId=? and D_CardId=?";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { CostId,D_CardId},Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

	public boolean checkData(OTCardBD otCardbd) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPARTURE_CARD_INFO where CostId=? and D_CardId=? and Default_WorkShopNo=? and Enabled = 'Y'";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { otCardbd.getCostId(),otCardbd.getD_CardID(),otCardbd.getDefault_WorkShop()},Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	System.out.println(totalRecord);
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

}
