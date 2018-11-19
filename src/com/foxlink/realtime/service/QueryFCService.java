package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.CheckFCDao;
import com.foxlink.realtime.DAO.CheckOverTimeStatusDao;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryForgetCard;
import com.foxlink.realtime.model.QueryStatus;

public class QueryFCService extends Service<QueryForgetCard>{
	private static Logger logger = Logger.getLogger(QueryFCService.class);
	private CheckFCDao checkFCDao;
	public void setCheckFCDao(CheckFCDao checkFCDao) {
		this.checkFCDao = checkFCDao;
	}

	@Override
	public boolean AddNewRecord(QueryForgetCard t) {
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryForgetCard t) {
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		return false;
	}

	@Override
	public List<QueryForgetCard> FindAllRecords() {
		return null;
	}

	@Override
	public List<QueryForgetCard> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		return null;
	}

	@Override
	public List<QueryForgetCard> FindQueryRecords(String userDataCostId,QueryForgetCard queryForgetCard) {
		List<QueryForgetCard> allStatus=null;
		try {
			//checkFCDao = (CheckFCDao) super.context.getBean("checkFcStatusDao");
			allStatus=checkFCDao.FindRecords(userDataCostId,queryForgetCard);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}
		return allStatus;
		
	}

	@Override
	public List<QueryForgetCard> FindQueryRecord(String userDataCostId,int currentPage, QueryForgetCard queryForgetCard) {
		List<QueryForgetCard> allStatus = null;
		try {
			//checkFCDao = (CheckFCDao) super.context.getBean("checkFcStatusDao");
			int totalRecord = checkFCDao.getTotalRecords(userDataCostId,queryForgetCard); 
			allStatus = checkFCDao.FindRecord(userDataCostId,currentPage,totalRecord,queryForgetCard);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}

	public Page getOTPage(String userDataCostId,int pageNum, QueryForgetCard queryForgetCard) {
		//checkFCDao = (CheckFCDao) super.context.getBean("checkFcStatusDao");
		int totalRecord = checkFCDao.getTotalRecords(userDataCostId,queryForgetCard);
		Page page = new Page(pageNum, totalRecord);
		return page;
	}

	@Override
	public List<QueryForgetCard> FindRecord(QueryForgetCard t) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
