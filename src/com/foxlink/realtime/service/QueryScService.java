package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.CheckOverTimeStatusDao;
import com.foxlink.realtime.DAO.CheckSCDao;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.QuerySwipeCard;

public class QueryScService extends Service<QuerySwipeCard>{
	private static Logger logger = Logger.getLogger(QueryScService.class);
	private CheckSCDao checkSCDao;


	public void setCheckSCDao(CheckSCDao checkSCDao) {
		this.checkSCDao = checkSCDao;
	}

	@Override
	public boolean AddNewRecord(QuerySwipeCard t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QuerySwipeCard t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QuerySwipeCard> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuerySwipeCard> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuerySwipeCard> FindQueryRecords(String userDataCostId,QuerySwipeCard querySwipeCard) {
		List<QuerySwipeCard> allStatus=null;
		try {

			//checkSCDao = (CheckSCDao) super.context.getBean("checkScStatusDao");
			allStatus=checkSCDao.FindRecords(userDataCostId,querySwipeCard);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
		
	}

	@Override
	public List<QuerySwipeCard> FindQueryRecord(String userDataCostId,int currentPage, QuerySwipeCard querySwipeCard) {
		List<QuerySwipeCard> allStatus = null;
		try {
			//checkSCDao = (CheckSCDao) super.context.getBean("checkScStatusDao");
			int totalRecord = checkSCDao.getTotalRecords(userDataCostId,querySwipeCard); 
			allStatus = checkSCDao.FindRecord(userDataCostId,currentPage,totalRecord,querySwipeCard);

			//allStatus=checkSCDao.FindRecord(querySwipeCard);

		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}
	public Page getSCPage(String userDataCostId,int pageNum, QuerySwipeCard querySwipeCard) {
		//checkSCDao = (CheckSCDao) super.context.getBean("checkScStatusDao");
		int totalRecord = checkSCDao.getTotalRecords(userDataCostId,querySwipeCard);
		Page page = new Page(pageNum, totalRecord);
		return page;
	}

	@Override
	public List<QuerySwipeCard> FindRecord(QuerySwipeCard t) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
