package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.model.QueryInfoByIdList;
import com.foxlink.realtime.model.QueryRecordByIdList;
import com.foxlink.realtime.model.objectMapper.QueryByIdListMap;
import com.foxlink.realtime.model.objectMapper.QueryInfoByIdListMap;
import com.foxlink.realtime.model.objectMapper.QueryRecordByIdListMap;

public class QueryRecordByIdDAO extends DAO<QueryRecordByIdDAO>{
	private static Logger logger = Logger.getLogger(QueryRecordByIdDAO.class);
	@Override
	public boolean AddNewRecord(QueryRecordByIdDAO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryRecordByIdDAO updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryRecordByIdDAO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdDAO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdDAO> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			QueryRecordByIdDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryRecordByIdDAO> FindRecords(QueryRecordByIdDAO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, QueryRecordByIdDAO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	//查詢實時門禁
		public List<QueryRecordByIdList> selectSwipeCardById(String startDate,String endDate,String UserId) {
			List<QueryRecordByIdList> swipeCardList = null;
			String numSql = "SELECT t.EMP_ID,e.NAME,e.DEPID,t.SWIPE_DATE,t.SWIPECARDTIME,t.SWIPECARDTIME2,t.SHIFT" + 
					" FROM CSR_SWIPECARDTIME t,CSR_EMPLOYEE e" + 
					" WHERE" + 
					" t.SWIPE_DATE>= ? " + 
					" AND t.SWIPE_DATE<= ? " + 
					" AND t.EMP_ID = ?" + 
					" AND t.EMP_ID = e.ID";
			System.out.println("實時門禁數據庫語句======="+numSql);
					try {
			    		List <Object> queryList=new  ArrayList<Object>();
			    		queryList.add(startDate);
			    		queryList.add(endDate);
			    		queryList.add(UserId);
			    		swipeCardList = jdbcTemplate.query(numSql, queryList.toArray(), new QueryRecordByIdListMap());	
					 
			    	} catch (Exception ex) {
			    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
			    		  ex.printStackTrace();
			    	}
					System.out.println("查詢實時門禁的數據==============>"+swipeCardList);
					return swipeCardList;		
			
		}
		
		//查詢大門門禁 selectSwipeInfoListById  swipeInfoList
	    public List<QueryInfoByIdList> selectSwipeInfoListById(String startDate,String endDate,String UserId) {
	    	List<QueryInfoByIdList> swipeInfoList = null;
	    	
			String numSql = "SELECT DEPID,DEPNAME,USERID,USERNAME,SWIPEDATETIME,SWIPEDOOR,INSERT_DATETIME" + 
					" FROM SWIPE_INFO" + 
					" WHERE TO_CHAR(SWIPEDATETIME,'YYYY-MM-DD') >=? AND TO_CHAR(SWIPEDATETIME,'YYYY-MM-DD') <=?" + 
					" AND USERID = ?";
			System.out.println("大門門禁數據庫語句======="+numSql);
					try {
			    		List <Object> queryList=new  ArrayList<Object>();
			    		queryList.add(startDate);
			    		queryList.add(endDate);
			    		queryList.add(UserId);
			    		swipeInfoList = jdbcTemplate.query(numSql, queryList.toArray(), new QueryInfoByIdListMap());	
					 
			    	} catch (Exception ex) {
			    		  logger.error("Find WorkshopNoRestInfo TotalRecord are failed ",ex);
			    		  ex.printStackTrace();
			    	}
					System.out.println("查詢大門門禁的數據==============>"+swipeInfoList);
					return swipeInfoList;		
		}
}
