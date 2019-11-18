package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.model.objectMapper.QueryByIdListMap;


public class QueryByIdDAO extends DAO<QueryByIdDAO>{
	private static Logger logger = Logger.getLogger(QueryByIdDAO.class);
	@Override
	public boolean AddNewRecord(QueryByIdDAO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryByIdDAO updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryByIdDAO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdDAO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdDAO> FindRecord(String userDataCostId, int currentPage, int totalRecord, QueryByIdDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdDAO> FindRecords(QueryByIdDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, QueryByIdDAO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//查詢異常列表
		public List<QueryByIdList> selectById(String startDate,String endDate,String UserId) {
			List<QueryByIdList> AllChange = null;
			String numSql = "SELECT EXCEPTION_DATE,COSTID,DEPID,SHIFT,USERID,USERNAME,EXCEPTION_INTERVAL,EXCEPTION_REASON" + 
					" FROM SWIPE_TIMEOUT_CACHE " + 
					" WHERE	TO_CHAR(EXCEPTION_DATE,'YYYY-MM-DD') >=?" + 
					" AND TO_CHAR(EXCEPTION_DATE,'YYYY-MM-DD') >=?" + 
					" AND USERID = ?";
			System.out.println("數據庫語句======="+numSql);
					try {
			    		List <Object> queryList=new  ArrayList<Object>();
			    		queryList.add(startDate);
			    		queryList.add(endDate);
			    		queryList.add(UserId);
			    		AllChange = jdbcTemplate.query(numSql, queryList.toArray(), new QueryByIdListMap());	
					 
			    	} catch (Exception ex) {
			    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
			    		  ex.printStackTrace();
			    	}
					System.out.println("查詢的數據==============>"+AllChange);
					return AllChange;		
		}

}
