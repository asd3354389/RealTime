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
import com.foxlink.realtime.model.Employee;
import com.foxlink.realtime.model.Page;
/*import com.foxlink.realtime.model.Page;*/
import com.foxlink.realtime.model.objectMapper.QueryEMPMapper;
import com.foxlink.realtime.model.objectMapper.QueryEmpyloeeMapper;


public class FLinePersonMtDAO extends DAO<Employee> {
	private static Logger logger=Logger.getLogger(FLinePersonMtDAO.class);
	/*private FLinePersonMtDAO fLinePersonMtDAO;

	public void setFLinePersonMtDAO(FLinePersonMtDAO fLinePersonMtDAO) {
		this.fLinePersonMtDAO = fLinePersonMtDAO;
	}
*/
	public int getTotalRecordY(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.Csr_Employee t where t.isonwork = 0 and t.line_personnel='Y' ";
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

	public List<Employee> FindAllRecordsY(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		System.out.println(queryCritirea);
		System.out.println(queryParam);
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.id,t.name,t.depid,t.costid,t.job_title,t.job_name,t.line_personnel FROM SWIPE.Csr_Employee t "
				+ "where t.isonwork = 0 and t.line_personnel = 'Y'";
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
		    sSQL += "order by t.id,t.costid)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+" " ;
		   /* where rn>"+page.getStartIndex()+" and rn<="+endIndex+"*/
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllEmp = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryEmpyloeeMapper());	
		  System.out.println(sSQL);
		
    	  } catch (Exception ex) {
    		  logger.error("Find FLinePersonMtDAOY TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllEmp;
	}



	public int getTotalRecordN(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.Csr_Employee t where t.isonwork = 0 and t.line_personnel='N' ";
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

	public List<Employee> FindAllRecordsN(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.id,t.name,t.depid,t.costid,t.job_title,t.job_name,t.line_personnel FROM SWIPE.Csr_Employee t "
				+ "where t.isonwork = 0 and t.line_personnel = 'N'";
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
		    sSQL += "order by t.id,t.costid)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	    
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllEmp = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryEmpyloeeMapper());	
		
    	  } catch (Exception ex) {
    		  logger.error("Find FLinePersonMtDAOY TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllEmp;
	}



	public int getToPerson(Employee[] emp) {
		// TODO Auto-generated method stub
		/*String sSQL = "Update SWIPE.Csr_Employee SET Line_Personnel = ? where id = ?";*/
		String sSQL = "Update (select * from CSR_EMPLOYEE where id = ?) SET Line_Personnel = ?,updatedate = sysdate ";
		int result = 0;
		try {
			jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, emp[i].getEmpNo());
					ps.setString(2, emp[i].getLine_Personnel());
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return emp.length;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("更改隨綫狀態失敗，原因："+e);
			e.printStackTrace();
			result=1;
		}
		return result;
	}

	public int getTotalRecord(String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.Csr_Employee t where t.isonwork = 0 and t.line_personnel='Y' ";
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

	public List<Employee> FindAllRecord(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.id,t.name,t.depid,t.costid,t.job_title,t.job_name,t.line_personnel FROM SWIPE.Csr_Employee t "
				+ "where t.isonwork = 0 ";
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
		    sSQL += "order by t.id,t.costid)b) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	    
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllEmp = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryEmpyloeeMapper());	
		
    	  } catch (Exception ex) {
    		  logger.error("Find FLinePersonMtDAOY TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllEmp;
	}

	public int getToPerson( String updateUser, String userDataCostId, String status) {
		// TODO Auto-generated method stub
		int result = -1;
		// TODO Auto-generated method stub
		/*String sSQL = "update (select b.*,rownum rn from "
				+ "(select t.id,t.name,t.depid,t.costid,t.job_title,t.job_name,t.line_personnel FROM SWIPE.Csr_Employee t "
				+ "where t.isonwork = 0 ";*/
		String sSQL="";
		if(status.equals("Y") ) {
			 sSQL+= "update SWIPE.Csr_Employee t set t.line_personnel = ? , t.updatedate = sysdate where t.isonwork= 0 and t.line_personnel = 'N'";
		}else if(status.equals("N") ){
			 sSQL+= "update SWIPE.Csr_Employee t set t.line_personnel = ? , t.updatedate = sysdate where t.isonwork= 0 and t.line_personnel = 'Y' ";
		}
		
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
				System.out.println(idsStr);
				sSQL+=" and Costid in("+idsStr+")";
			}
    		
			else{
				sSQL+="";
			}
		  
		  result = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, status);
			}
		});
		
    	  } catch (Exception ex) {
    		  logger.error("Update FLinePersonMtDAO TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return result;
	}

	public int getToPersonCondition(String updateUser, String userDataCostId, String status, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		int result = -1;
		// TODO Auto-generated method stub
		/*String sSQL = "update (select b.*,rownum rn from "
				+ "(select t.id,t.name,t.depid,t.costid,t.job_title,t.job_name,t.line_personnel FROM SWIPE.Csr_Employee t "
				+ "where t.isonwork = 0 ";*/
		System.out.println(status);
		String sSQL="";
		if(status.equals("Y") ) {
			 sSQL+= "update SWIPE.Csr_Employee t set t.line_personnel = ? , t.updatedate = sysdate where t.isonwork= 0 and t.line_personnel = 'N'";
		}else if(status.equals("N") ){
			 sSQL+= "update SWIPE.Csr_Employee t set t.line_personnel = ? , t.updatedate = sysdate where t.isonwork= 0 and t.line_personnel = 'Y' ";
		}
		
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
				System.out.println(idsStr);
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
		  
		  result = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, status);
				ps.setString(2, queryParam);
			}
		});
		
    	  } catch (Exception ex) {
    		  logger.error("Update FLinePersonMtDAO TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		System.out.println(result);
		return result;
	}

	@Override
	public boolean AddNewRecord(Employee newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Employee updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Employee> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> FindRecord(String userDataCostId, int currentPage, int totalRecord, Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> FindRecords(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, Employee t) {
		// TODO Auto-generated method stub
		return 0;
	}



	


}
