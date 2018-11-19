package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.JobInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.JobInfoMapper;
import com.foxlink.realtime.model.objectMapper.QueryEMPMapper;

public class JobTitleDAO extends DAO<Emp> {
	private static Logger logger=Logger.getLogger(JobTitleDAO.class);
	
	@Override
	public boolean AddNewRecord(Emp newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Emp updateRecord) {
		// TODO Auto-generated method stub
		int updateRow = -1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sql = "update SWIPE.Csr_Employee t set t.job_title = ?,t.job_name = ? where t.id = ? and t.name = ? and t.depid = ? and t.costid = ? and t.isonwork = '0'";
		try{
			if(updateRecord!=null){
				updateRow=jdbcTemplate.update(sql,new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, updateRecord.getJob_Title());
						ps.setString(2, updateRecord.getJob_Name());
						ps.setString(3, updateRecord.getEmpNo());
						ps.setString(4, updateRecord.getEmpName());
						ps.setString(5, updateRecord.getDeptNo());
						ps.setString(6, updateRecord.getCostID());
					}
					
				});
				transactionManager.commit(txStatus);
			}
		}catch(Exception e){
			logger.error("Update JobTitle is failed",e);
			transactionManager.rollback(txStatus);
		}
		
		if(updateRow > 0) 
			   return true; 
			else
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

	public int getTotalRecord(String queryCritirea, String queryParam,String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.Csr_Employee t where t.isonwork = 0 ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(!userDataCostId.equals("ALL")){
				sSQL+="and t.costid in (SELECT trim(regexp_substr(costid, '[^*]+', 1, LEVEL)) "
						+ "FROM (SELECT costid  FROM   SWIPE.user_data  WHERE  USERNAME= ?)  CONNECT BY instr(costid, ''*'', 1, LEVEL - 1) > 0)";
				queryList.add(updateUser);
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

	@Override
	public int getTotalRecords(String userDataCostId, Emp t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Emp> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam,
			String updateUser, String userDataCostId) {
		List<Emp> AllEmp = null;
		// TODO Auto-generated method stub
		String sSQL = "select * from (select b.*,rownum rn from "
				+ "(select t.id,t.name,t.depid,t.costid,t.job_title,t.job_name FROM SWIPE.Csr_Employee t "
				+ "where t.isonwork = 0 ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(!userDataCostId.equals("ALL")){
				sSQL+="and t.costid in (SELECT trim(regexp_substr(costid, '[^*]+', 1, LEVEL)) "
						+ "FROM (SELECT costid  FROM   SWIPE.user_data  WHERE  USERNAME= ?)  CONNECT BY instr(costid, ''*'', 1, LEVEL - 1) > 0)";
				queryList.add(updateUser);
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
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }

		  AllEmp = jdbcTemplate.query(sSQL, queryList.toArray(), new QueryEMPMapper());	
		 
    	  } catch (Exception ex) {
    		  logger.error("Find JobTitle TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllEmp;
	}

	public List<JobInfo> FindAllJobInfoByJobType(String jobType) {
		// TODO Auto-generated method stub
		List<JobInfo> jobInfo = null;
		String sql = "select trim(t.job_code) job_code,trim(t.job_desc) job_desc from foxlink_job_info t where t.job_code like ? and t.job_code != ?";
		try{
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(jobType+"%");
			queryList.add(jobType);
			jobInfo = jdbcTemplate.query(sql, queryList.toArray(), new JobInfoMapper());
		}catch(Exception ex){
			logger.error("Find JobInfo are failed ",ex);
	  		ex.printStackTrace();
		}
		return jobInfo;
	}
	
	
}
