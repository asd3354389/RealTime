package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.AccountDAO;
import com.foxlink.realtime.DAO.CheckOverTimeStatusDao;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryStatus;

public class QueryService extends Service<QueryStatus> {
	private static Logger logger = Logger.getLogger(QueryService.class);
	private CheckOverTimeStatusDao checkOverTimeStatusDao;


	public void setCheckOverTimeStatusDao(CheckOverTimeStatusDao checkOverTimeStatusDao) {
		this.checkOverTimeStatusDao = checkOverTimeStatusDao;
	}

public QueryService() {
		super();
	}

	@Override
	public List<QueryStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public List<QueryStatus> QueryOverTimeStatus(QueryStatus queryEmpStatus)
	// {
	// List<QueryStatus> allStatus=null;
	// try {
	// checkOverTimeStatusDao = (CheckOverTimeStatusDao)
	// super.context.getBean("checkOverTimeStatusDao");
	// allStatus=checkOverTimeStatusDao.FindRecord(queryEmpStatus);
	// } catch (Exception ex) {
	// logger.error("query error" + ex);
	// }
	//
	// return allStatus;
	// }

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryStatus> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean AddNewRecord(QueryStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryStatus> FindQueryRecord(String userDataCostId,int currentPage,QueryStatus queryStatus) {
		List<QueryStatus> allStatus = null;
		try {
			//checkOverTimeStatusDao = (CheckOverTimeStatusDao) super.context.getBean("checkOverTimeStatusDao");
			int totalRecord = checkOverTimeStatusDao.getTotalRecords(userDataCostId,queryStatus); 
			allStatus = checkOverTimeStatusDao.FindRecord(userDataCostId,currentPage,totalRecord,queryStatus);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}

	public Page getOTPage(String userDataCostId,int pageNum, QueryStatus queryStatus) {
		//checkOverTimeStatusDao = (CheckOverTimeStatusDao) super.context.getBean("checkOverTimeStatusDao");
		int totalRecord = checkOverTimeStatusDao.getTotalRecords(userDataCostId,queryStatus);
		Page page = new Page(pageNum, totalRecord);
		return page;
	}

	//@Override
	public List<QueryStatus> FindQueryRecords(String userDataCostId,QueryStatus queryStatus) {
		List<QueryStatus> allStatus = null;
		try {

			//checkOverTimeStatusDao = (CheckOverTimeStatusDao) super.context.getBean("checkOverTimeStatusDao");
			
			allStatus = checkOverTimeStatusDao.FindRecords(userDataCostId,queryStatus);

			//allStatus=checkOverTimeStatusDao.FindRecord(queryEmpStatus);

		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}



	@Override
	public List<QueryStatus> FindRecord(QueryStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

}
