package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.JobTitleDAO;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.JobInfo;
import com.foxlink.realtime.model.Page;

public class JobTitleService extends Service<Emp> {
	private static Logger logger = Logger.getLogger(JobTitleService.class);
	private JobTitleDAO jobTitleDAO;
	
	

	public void setJobTitleDAO(JobTitleDAO jobTitleDAO) {
		this.jobTitleDAO = jobTitleDAO;
	}

	@Override
	public boolean AddNewRecord(Emp t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Emp t) {
		// TODO Auto-generated method stub
		return jobTitleDAO.UpdateRecord(t);
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
	public List<Emp> FindRecord(Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindQueryRecords(String userDataCostId, Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindQueryRecord(String userDataCostId, int currentPage, Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getJobTitlePage(int currentPage, String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = jobTitleDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<Emp> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam, String userDataCostId) {
		// TODO Auto-generated method stub
		List<Emp> AllEmp = null;
		try{
			int totalRecord = jobTitleDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllEmp = jobTitleDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllEmp;
	}

	public List<JobInfo> FindAllJobInfoByJobType(String jobType) {
		// TODO Auto-generated method stub
		return jobTitleDAO.FindAllJobInfoByJobType(jobType);
	}

}
