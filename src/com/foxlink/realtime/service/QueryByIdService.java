package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import com.foxlink.realtime.DAO.QueryByIdDAO;
import com.foxlink.realtime.model.QueryByIdList;

public class QueryByIdService extends Service<QueryByIdService>{
	private static Logger logger = Logger.getLogger(ExceptionCostService.class);
	private QueryByIdDAO queryByIdDAO;
	
	public void setQueryByIdDAO(QueryByIdDAO queryByIdDAO) {
		this.queryByIdDAO = queryByIdDAO;
	}
	@Override
	public boolean AddNewRecord(QueryByIdService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryByIdService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryByIdService> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdService> FindRecord(QueryByIdService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdService> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdService> FindQueryRecords(String userDataCostId, QueryByIdService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdService> FindQueryRecord(String userDataCostId, int currentPage, QueryByIdService t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//selectById(startDate,endDate,UserId)
	//查詢異常列表
	public List<QueryByIdList> selectById(String startDate,String endDate,String UserId) {
		
		return queryByIdDAO.selectById(startDate,endDate,UserId);
	}

}
