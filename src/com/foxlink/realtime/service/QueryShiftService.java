package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.CheckShiftDao;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryShift;

public class QueryShiftService extends Service<QueryShift> {
	private static Logger logger = Logger.getLogger(QueryShiftService.class);
	private CheckShiftDao checkShiftDao;


	public void setCheckShiftDao(CheckShiftDao checkShiftDao) {
		this.checkShiftDao = checkShiftDao;
	}


	
	@Override
	public boolean AddNewRecord(QueryShift t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryShift t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryShift> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryShift> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryShift> FindQueryRecords(String userDataCostId,QueryShift queryShift) {

		List<QueryShift> allStatus = null;
		try {
			//checkShiftDao = (CheckShiftDao) super.context.getBean("checkShiftStatusDao");
			allStatus = checkShiftDao.FindRecords(userDataCostId,queryShift);
			//allStatus = checkShiftDao.FindRecord(queryShift);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}
		return allStatus;

	}

	@Override
	public List<QueryShift> FindQueryRecord(String userDataCostId,int currentPage, QueryShift queryShift) {
		List<QueryShift> allStatus = null;
		try {
			//checkShiftDao = (CheckShiftDao) super.context.getBean("checkShiftStatusDao");
			int totalRecord = checkShiftDao.getTotalRecords(userDataCostId,queryShift);
			allStatus = checkShiftDao.FindRecord(userDataCostId,currentPage, totalRecord, queryShift);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}

	public Page getShiftPage(String userDataCostId,int pageNum, QueryShift queryShift) {
		//checkShiftDao = (CheckShiftDao) super.context.getBean("checkShiftStatusDao");
		int totalRecord = checkShiftDao.getTotalRecords(userDataCostId,queryShift);
		Page page = new Page(pageNum, totalRecord);
		return page;
	}

	@Override
	public List<QueryShift> FindRecord(QueryShift t) {
		// TODO Auto-generated method stub
		return null;
	}






}
