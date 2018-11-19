package com.foxlink.realtime.service;

import java.util.List;
import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.LmtDeptDAO;

public class LmtDeptService  extends Service<LmtDeptDAO> {
	private static Logger logger=Logger.getLogger(LmtDeptService.class);  
	private LmtDeptDAO lmtDeptDAO;
	public void setLmtDeptDAO(LmtDeptDAO lmtDeptDAO ) {
	      this.lmtDeptDAO = lmtDeptDAO;
	      }

	
	public List<String> FindCostIds() {
		return lmtDeptDAO.FindCostIds();
	}


	@Override
	public boolean AddNewRecord(LmtDeptDAO t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean UpdateRecord(LmtDeptDAO t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<LmtDeptDAO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<LmtDeptDAO> FindRecord(LmtDeptDAO t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<LmtDeptDAO> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<LmtDeptDAO> FindQueryRecord(String userDataCostId, int currentPage, LmtDeptDAO t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<LmtDeptDAO> FindQueryRecords(String userDataCostId, LmtDeptDAO t) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
