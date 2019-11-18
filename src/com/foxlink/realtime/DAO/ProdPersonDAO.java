package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.foxlink.realtime.model.ProdPerson;
import com.foxlink.realtime.model.ProdPersonCostId;
import com.foxlink.realtime.model.SelectProdList;
import com.foxlink.realtime.model.SelectProdPerson;
import com.foxlink.realtime.model.objectMapper.QueryProdPersonCostId;
import com.foxlink.realtime.model.objectMapper.QuerySelectProdList;


public class ProdPersonDAO extends DAO<ProdPersonDAO>{
	private static Logger logger = Logger.getLogger(ProdPersonDAO.class);
	private String CostIdstr;
	@Override
	public boolean AddNewRecord(ProdPersonDAO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ProdPersonDAO updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProdPersonDAO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonDAO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonDAO> FindRecord(String userDataCostId, int currentPage, int totalRecord, ProdPersonDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonDAO> FindRecords(ProdPersonDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, ProdPersonDAO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/**
	 * 查询人员  CheckEmp
	 * @param assistantId
	 * @return
	 */
	public String CheckCostId(String assistantId) {
			List<ProdPersonCostId> empList = null;
			CostIdstr = null;
			String empSql = "SELECT COSTID FROM CSR_EMPLOYEE WHERE ID = ?";
			System.out.println("查詢人員數據庫語句======="+empSql);
			try {
	    		List <Object> queryList=new  ArrayList<Object>();
	    		queryList.add(assistantId);
	    		
	    		empList = jdbcTemplate.query(empSql, queryList.toArray(), new QueryProdPersonCostId());	
	    		System.out.println("查询的人员资料"+empList);
	    	} catch (Exception ex) {
	    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
	    		  ex.printStackTrace();
	    	}
			
			for(int i=0;i<empList.size();i++){
				CostIdstr = empList.get(i).getCOSTID();//xx表示list里面装的是什么比如String,等
				}
		return CostIdstr;
		}
		
		/**
		 * 插入數據
		 * @param prodPerson
		 * @return
		 */
		public int insertProd(ProdPerson prodPerson) {
			// TODO Auto-generated method stub
			int createRow=0;
			txDef = new DefaultTransactionDefinition();
			txStatus = transactionManager.getTransaction(txDef);
			//设置随机RECORDID值
			Date day=new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd"); 
			String rodm = df.format(day)+getRandomPassword(5);
			
			System.out.println("传入的costid==+++++++"+prodPerson.getCOSTID());
			for (int i = 0; i < prodPerson.getDataList().length; i++) {
				System.out.println("插入的数据=============="+rodm+prodPerson.getCOSTID()+prodPerson.getDEPID()+prodPerson.getDataList()[i]+prodPerson.getPLAN_DATE_WEEK()+prodPerson.getPLAN_DATE_YEAR()+prodPerson.getSHIFT()+prodPerson.getPROD_NAME()+prodPerson.getPROD_CODE()+prodPerson.getNUMBER_OF_PEOPLE());   
			}
			
			String sSQL="INSERT INTO SWIPE.MANPOWER_PRO (RECORDID,COSTID,DEPID,PLAN_DATE,PLAN_DATE_WEEK,PLAN_DATE_YEAR,SHIFT,PROD_NAME,PROD_CODE,STATUS,NUMBER_OF_PEOPLE) VALUES(?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,?,TO_NUMBER(?))";
			try {
				if(prodPerson!=null) {
					
					
			       jdbcTemplate.batchUpdate(sSQL,new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1,rodm);
						ps.setString(2,prodPerson.getCOSTID());
						ps.setString(3, prodPerson.getDEPID());
						ps.setString(4, prodPerson.getDataList()[i]);
						ps.setString(5, prodPerson.getPLAN_DATE_WEEK());
						ps.setString(6, prodPerson.getPLAN_DATE_YEAR());
						ps.setString(7, prodPerson.getSHIFT());
						ps.setString(8, prodPerson.getPROD_NAME());
						ps.setString(9, prodPerson.getPROD_CODE());
						ps.setString(10, "0");
						ps.setString(11, prodPerson.getNUMBER_OF_PEOPLE());
					}
					
					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return prodPerson.getDataList().length;
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
			
			System.out.println("插入的狀態=============="+createRow);
			return createRow;
			
		}
		
		/**
		 * 刪除排配機種
		 * @param prodPerson
		 * @return
		 */
		public List<SelectProdList> selectProd(SelectProdPerson selectProdPerson) {
			
			List<SelectProdList> AllChange = null;
			String numSql = "SELECT RECORDID,DEPID,PLAN_DATE_YEAR,PLAN_DATE_WEEK,SHIFT,	PROD_NAME,PROD_CODE,NUMBER_OF_PEOPLE" + 
					" FROM" + 
					" SWIPE.MANPOWER_PRO" + 
					" WHERE PLAN_DATE_YEAR = ?  AND PLAN_DATE_WEEK = ?  AND COSTID = ?  AND DEPID = ?" + 
					" GROUP BY RECORDID,DEPID,PLAN_DATE_YEAR,PLAN_DATE_WEEK,SHIFT,PROD_NAME,PROD_CODE,NUMBER_OF_PEOPLE" + 
					" ORDER BY SHIFT,PROD_NAME";
			System.out.println("數據庫語句======="+numSql);
				
					try {
			    		List <Object> queryList=new  ArrayList<Object>();
			    		queryList.add(selectProdPerson.getPLAN_DATE_YEAR());
			    		queryList.add(selectProdPerson.getPLAN_DATE_WEEK());
			    		queryList.add(selectProdPerson.getCOSTID());
			    		queryList.add(selectProdPerson.getDEPID());
			    		AllChange = jdbcTemplate.query(numSql, queryList.toArray(), new QuerySelectProdList());	
					 
			    	} catch (Exception ex) {
			    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
			    		  ex.printStackTrace();
			    	}
					System.out.println("查詢的數據==============>"+AllChange);
					
					return AllChange;		
			
		}
		/**
	     * 生成随机数当作getItemID
	     * n ： 需要的长度
	     * @return
	     */
		 private static String getRandomPassword( int n )
		    {
		        String val = "";
		        Random random = new Random();
		        for ( int i = 0; i < n; i++ )
		        {
		            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
		            if ( "char".equalsIgnoreCase( str ) )
		            { // 产生字母
		                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
		                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
		                val += (char) ( nextInt + random.nextInt( 26 ) );
		            }
		            else if ( "num".equalsIgnoreCase( str ) )
		            { // 产生数字
		                val += String.valueOf( random.nextInt( 10 ) );
		            }
		        }
		        return val;
		    }
		//deleteProd 刪除排配機種
			public boolean deleteProd(String recordid) {
				
				int delRow=0;
				  txDef = new DefaultTransactionDefinition();
				  txStatus = transactionManager.getTransaction(txDef);  
				  String sSQL = "DELETE FROM SWIPE.MANPOWER_PRO WHERE RECORDID = ?";
				  try {
				   if(recordid!=null) {
				    delRow=jdbcTemplate.update(sSQL, new PreparedStatementSetter() {
				     
				     @Override
				     public void setValues(PreparedStatement ps) throws SQLException {
				      // TODO Auto-generated method stub
				      ps.setString(1, recordid);
				      
				     }
				    });
				    transactionManager.commit(txStatus);
				   } 
				  }
				  catch(Exception ex) {
				   logger.error("delete SupportMachine is failed",ex);
				   transactionManager.rollback(txStatus);
				  }   
				   if(delRow > 0) 
				       return true; 
				    else
				       return false;
			}
		
}
