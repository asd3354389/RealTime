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
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.QueryOTCardBdMapper;


public class OTCardbdPersonDAO extends DAO<Emp> {
	private static Logger logger = Logger.getLogger(OTCardbdPersonDAO.class);
	

	
	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPARTURE_CARD_INFO a ,(select t.id,t.name FROM SWIPE.Csr_Employee t where  t.isonwork = 0";
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
				sSQL+=" and Costid in("+idsStr+")";
			}
    		if(queryCritirea.equals("Id")){
				sSQL+=" and Id = ?";  
			}
			else if(queryCritirea.equals("Name")){
				sSQL+=" and Name = ?";  
			}else if(queryCritirea.equals("Depid")){
				sSQL+=" and Depid = ?";  
			}else if(queryCritirea.equals("Costid")){
				sSQL+=" and Costid = ?";  
			}
			else{
				sSQL+="";
			}
    		sSQL += ")b where a.DMP_ID = b.id ";
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
		String sSQL = "select * from(select c.*,rownum rn from (select a.d_cardid, a.dmp_id ,b.name, a.default_workshopno,a.enabled FROM SWIPE.DEPARTURE_CARD_INFO a ,(select t.id,t.name FROM SWIPE.Csr_Employee t where  t.isonwork = 0";
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
				sSQL+=" and Costid in("+idsStr+")";
			}
    		if(queryCritirea.equals("Id")){
				sSQL+=" and Id = ?";  
			}
			else if(queryCritirea.equals("Name")){
				sSQL+=" and Name = ?";  
			}else if(queryCritirea.equals("Depid")){
				sSQL+=" and Depid = ?";  
			}else if(queryCritirea.equals("Costid")){
				sSQL+=" and Costid = ?";  
			}
			else{
				sSQL+="";
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += "order by t.id)b where a.DMP_ID = b.id and a.enabled='Y')c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	    
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

	public boolean checkUserNameDuplicate(String Dmp_id) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.DEPARTURE_CARD_INFO where Dmp_id=? and ENABLED='Y'";
    	try {    	    	
    		totalRecord = jdbcTemplate.queryForObject(sSQL, new Object[] { Dmp_id },Integer.class);	   	
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	/*System.out.println(sSQL);*/
    	 if(totalRecord > 0) 
			   return false; 
		 else
			 return true;
	}

	public boolean OTCardbdPerson(OTCardBD otCardbd) {
		// TODO Auto-generated method stub
		int createRow=-1;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.DEPARTURE_CARD_INFO (D_CardId,Dmp_Id,Default_WorkShopNo,Update_UserId) VALUES(?,?,?,?)";
		try {
			if(otCardbd!=null) {
		      createRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, otCardbd.getD_CardID());
						arg0.setString(2, otCardbd.getDmp_id());
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

	public int UpdateBdOTCard(OTCardBD[] otCardbd, String updateUser) {
		// TODO Auto-generated method stub
		String sSQL = "update SWIPE.DEPARTURE_CARD_INFO set D_CardId =?,Default_WorkShopNo=?,update_time = sysdate,Update_UserId=? where enabled='Y' and Dmp_id =?";
		int result = 0;
		try {
			jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, otCardbd[i].getD_CardID());
					ps.setString(2, otCardbd[i].getDefault_WorkShop());
					ps.setString(3, updateUser);
					ps.setString(4, otCardbd[i].getDmp_id());
					
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return otCardbd.length;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("更改離崗卡與工號綁定狀態失敗，原因："+e);
			e.printStackTrace();
			result=1;
		}
		System.out.println(sSQL);
		return result;
	}

	public int RelieveOTCard(OTCardBD[] otCardbd, String updateUser) {
		// TODO Auto-generated method stub
		String sSQL = "delete from SWIPE.DEPARTURE_CARD_INFO where enabled='Y' and Dmp_id =?";
		int result = 0;
		try {
			jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, otCardbd[i].getDmp_id());				
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return otCardbd.length;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("解除離崗卡與工號綁定狀態失敗，原因："+e);
			e.printStackTrace();
			result=1;
		}
		return result;
	}
	
	public int OTCardNbdPerson(OTCardBD[] otCardbd,String updateUser) {
		// TODO Auto-generated method stub
		int result=0;

		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		
		String sSQL="INSERT INTO SWIPE.DEPARTURE_CARD_INFO_HT (D_CardId,Dmp_Id,Default_WorkShopNo,Update_UserId,enabled) VALUES(?,?,?,?,'N')";
		try {
			
				jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, otCardbd[i].getD_CardID());
					ps.setString(2, otCardbd[i].getDmp_id());
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
			ex.printStackTrace();
			result=1;
			transactionManager.rollback(txStatus);
		}
		return result;
	}

}
