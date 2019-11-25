package com.foxlink.realtime.DAO;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.model.objectMapper.QueryABTimeList;
import com.foxlink.realtime.model.objectMapper.QueryCountEmp;

public class QuertAbTimeByCostIdDAO extends DAO<QueryByIdList>{
	private static Logger logger = Logger.getLogger(QuertAbTimeByCostIdDAO.class);

	public List<String> FindDepidRecords(String costid) {
		// TODO Auto-generated method stub
		List<String> getDepids=null;
		String sSQL="select distinct(depid) from swipe.MANPOWER_STATUS where depid like '%XR%' and costid='"+costid+"' order by depid";
		try { 			
				getDepids=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error("Search DepidRecords Record is failed",ex);
		}
		return getDepids;
	}

	@Override
	public boolean AddNewRecord(QueryByIdList newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryByIdList updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryByIdList> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindRecord(String userDataCostId, int currentPage, int totalRecord, QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindRecords(QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, QueryByIdList t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<QueryByIdList> searchABTimeList(String bu, String costid, String depid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		List<QueryByIdList> ABTimeList = null;
		String sSQl = "SELECT count(*)as count,userid,username,depid,sum(EXCEPTION_TIME)as sumtime FROM SWIPE.SWIPE_TIMEOUT_CACHE where Exception_Date >= to_date('"+sDate+"','yyyy-MM-dd HH24:mi:ss') and Exception_Date< to_date('"+eDate+"','yyyy-MM-dd HH24:mi:ss')";
		try {
			if(bu.equals("AllBU")) {
				sSQl+="";
			}else {
				if(costid.equals("AllCost")){
					sSQl += " and bu ='"+bu+"'";
				}else{
					if(depid.equals("AllDepid")){
						sSQl += " and bu ='"+bu+"' and costid ='"+costid+"'";
					}else{
						sSQl += " and bu ='"+bu+"' and costid ='"+costid+"' and depid='"+depid+"'";
					}
				}
			}
			sSQl+="group by userid,username,depid ORDER BY depid";
			System.out.println(sSQl);
			ABTimeList = jdbcTemplate.query(sSQl,new QueryABTimeList());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ABTimeList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return ABTimeList;
	}

	public List<QueryByIdList> searchABTimeDepid(String depid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		List<QueryByIdList> ABTimeList = null;
		String sSQl = "SELECT count(*)as count,userid,username,depid,sum(EXCEPTION_TIME)as sumtime FROM SWIPE.SWIPE_TIMEOUT_CACHE where Exception_Date >= to_date('"+sDate+"','yyyy-MM-dd HH24:mi:ss') and Exception_Date< to_date('"+eDate+"','yyyy-MM-dd HH24:mi:ss') and depid='"+depid+"'";
		try {
			sSQl+="group by userid,username,depid ORDER BY depid";
			System.out.println(sSQl);
			ABTimeList = jdbcTemplate.query(sSQl,new QueryABTimeList());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ABTimeList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return ABTimeList;
	}
}
