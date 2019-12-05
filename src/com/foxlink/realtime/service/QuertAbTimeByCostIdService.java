package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.QuertAbTimeByCostIdDAO;
import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.QueryByIdList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class QuertAbTimeByCostIdService extends Service<QueryByIdList>{
	private static Logger logger = Logger.getLogger(QuertAbTimeByCostIdService.class);
	private QuertAbTimeByCostIdDAO quertAbTimeByCostIdDAO;
	
	public void setQuertAbTimeByCostIdDAO(QuertAbTimeByCostIdDAO quertAbTimeByCostIdDAO) {
		this.quertAbTimeByCostIdDAO = quertAbTimeByCostIdDAO;
	}

	public List<String> FindDepidRecords(String costid) {
		// TODO Auto-generated method stub
		List<String> allDepid=null;
		try {
			allDepid=quertAbTimeByCostIdDAO.FindDepidRecords(costid);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allDepid;
	}

	@Override
	public boolean AddNewRecord(QueryByIdList t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryByIdList t) {
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
	public List<QueryByIdList> FindRecord(QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindQueryRecords(String userDataCostId, QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindQueryRecord(String userDataCostId, int currentPage, QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String ShowABTimeByCostid(String bu, String costid, String depid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<QueryByIdList>ABTimeList = quertAbTimeByCostIdDAO.searchABTimeList(bu,costid,depid,sDate,eDate);
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(ABTimeList.size() == 0 || ABTimeList == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("ABTimeList", gson.toJson(ABTimeList));
		}
		
		//System.out.println(result.toString());
		return result.toString();
	}

	public String ShowABTimeByDepid(String depid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<QueryByIdList>ABTimeList = quertAbTimeByCostIdDAO.searchABTimeDepid(depid,sDate,eDate);
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(ABTimeList.size() == 0 || ABTimeList == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("ABTimeList", gson.toJson(ABTimeList));
		}
		
		//System.out.println(result.toString());
		return result.toString();
	}

	
}
