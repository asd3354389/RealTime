package com.foxlink.realtime.DAO;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.LmtDept;


public class LmtDeptDAO extends DAO<LmtDept> {
	private static Logger logger=Logger.getLogger(LmtDeptDAO.class);
	
	@Override
	public List<LmtDept> FindAllRecords() {	
		// TODO Auto-generated method stub
				return null;
	}

	public List<String> FindCostIds() {
		// TODO Auto-generated method stub
		List<String> AllCostIds = null;
		String sSQL = " SELECT DISTINCT COSTID FROM SWIPE.csr_employee order by costID ";
		try {	
			AllCostIds = jdbcTemplate.queryForList(sSQL, String.class);			
			} catch (Exception ex) {
		ex.printStackTrace();
		logger.error("Find AllCostIds are failed",ex);
	}
	return AllCostIds;
	}

	@Override
	public boolean AddNewRecord(LmtDept newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(LmtDept updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LmtDept> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public List<LmtDept> FindRecords(LmtDept t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LmtDept> FindRecord(String userDataCostId, int currentPage, int totalRecord, LmtDept t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, LmtDept t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
