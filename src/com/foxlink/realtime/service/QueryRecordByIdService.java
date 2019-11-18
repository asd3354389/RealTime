package com.foxlink.realtime.service;

import java.util.List;
import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.QueryByIdDAO;
import com.foxlink.realtime.DAO.QueryRecordByIdDAO;
import com.foxlink.realtime.model.QueryInfoByIdList;
import com.foxlink.realtime.model.QueryRecordByIdList;

public class QueryRecordByIdService extends Service<QueryRecordByIdService> {
	private static Logger logger = Logger.getLogger(QueryRecordByIdService.class);
	private QueryRecordByIdDAO queryRecordByIdDAO;
	public void setQueryRecordByIdDAO(QueryRecordByIdDAO queryRecordByIdDAO) {
		this.queryRecordByIdDAO = queryRecordByIdDAO;
	}
	@Override
	public boolean AddNewRecord(QueryRecordByIdService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryRecordByIdService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryRecordByIdService> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdService> FindRecord(QueryRecordByIdService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdService> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdService> FindQueryRecords(String userDataCostId, QueryRecordByIdService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdService> FindQueryRecord(String userDataCostId, int currentPage,
			QueryRecordByIdService t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//selectSwipeCardById(startDate,endDate,UserId);
	
	//查詢實時門禁
	public List<QueryRecordByIdList> selectSwipeCardById(String startDate,String endDate,String UserId) {
		
		return queryRecordByIdDAO.selectSwipeCardById(startDate,endDate,UserId);
	}
	//查詢大門門禁 selectSwipeInfoListById
    public List<QueryInfoByIdList> selectSwipeInfoListById(String startDate,String endDate,String UserId) {
		
		return queryRecordByIdDAO.selectSwipeInfoListById(startDate,endDate,UserId);
	}
}
