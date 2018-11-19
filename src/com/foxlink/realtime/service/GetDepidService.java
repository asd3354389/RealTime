package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.GetDepidDao;
import com.foxlink.realtime.model.GetDepid;

public class GetDepidService extends Service<GetDepid> {
	private static Logger logger = Logger.getLogger(GetDepidService.class);
	private GetDepidDao getDepidDao;


	public void setGetDepidDao(GetDepidDao getDepidDao) {
		this.getDepidDao = getDepidDao;
	}

	@Override
	public boolean AddNewRecord(GetDepid t) {
		return false;
	}

	@Override
	public boolean UpdateRecord(GetDepid t) {
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		return false;
	}

	@Override
	public List<GetDepid> FindAllRecords() {
		return null;
	}

	@Override
	public List<GetDepid> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		return null;
	}

	
	public List<GetDepid> FindQueryRecords(GetDepid getDepid) {
		
		List<GetDepid> allStatus=null;
		try {
			//getDepidDao = (GetDepidDao) super.context.getBean("getDepidDao");
			allStatus=getDepidDao.FindRecord(getDepid);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
		//return null;
	}

	

	@Override
	public List<GetDepid> FindRecord(GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GetDepid> FindQueryRecord(String userDataCostId, int currentPage, GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GetDepid> FindQueryRecords(String userDataCostId, GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

}
