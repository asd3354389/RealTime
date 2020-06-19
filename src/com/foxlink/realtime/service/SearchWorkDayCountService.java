package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.SearchWorkDayCountDao;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QuerySwipeCard;
import com.foxlink.realtime.model.QueryWorkDayCount;
import com.google.gson.JsonElement;


public class SearchWorkDayCountService extends Service<QueryWorkDayCount> {
	private static Logger logger = Logger.getLogger(SearchWorkDayCountService.class);
	private SearchWorkDayCountDao searchWorkDayCountDao;


	public void setSearchWorkDayCountDao(SearchWorkDayCountDao searchWorkDayCountDao) {
		this.searchWorkDayCountDao = searchWorkDayCountDao;
	}


	public Page getSCPage(String userDataCostId, int currentPage, String type, String data) {
		// TODO Auto-generated method stub
		int totalRecord = searchWorkDayCountDao.getTotalRecords(userDataCostId,type,data);
		Page page = new Page(currentPage, totalRecord);
		return page;
	}


	public List FindQueryRecord(String userDataCostId, int currentPage, String type,String data) {
		// TODO Auto-generated method stub
		List<QueryWorkDayCount> allStatus = null;
		try {
			//checkSCDao = (CheckSCDao) super.context.getBean("checkScStatusDao");
			int totalRecord = searchWorkDayCountDao.getTotalRecords(userDataCostId,type,data); 
			allStatus = searchWorkDayCountDao.FindRecord(userDataCostId,currentPage,totalRecord,type,data);

			//allStatus=checkSCDao.FindRecord(querySwipeCard);

		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}


	@Override
	public boolean AddNewRecord(QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean UpdateRecord(QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<QueryWorkDayCount> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<QueryWorkDayCount> FindRecord(QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<QueryWorkDayCount> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<QueryWorkDayCount> FindQueryRecords(String userDataCostId, QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<QueryWorkDayCount> FindQueryRecord(String userDataCostId, int currentPage, QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<QueryWorkDayCount> FindQueryRecords(String userDataCostId,
			String type, String data) {
		// TODO Auto-generated method stub
		List<QueryWorkDayCount> allStatus=null;
		try {

			//checkSCDao = (CheckSCDao) super.context.getBean("checkScStatusDao");
			allStatus=searchWorkDayCountDao.FindRecords(userDataCostId,type,data);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}

	
}
