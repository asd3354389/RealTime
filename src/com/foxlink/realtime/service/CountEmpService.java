package com.foxlink.realtime.service;

import java.util.List;

import javax.xml.crypto.Data;

import org.apache.log4j.Logger;


import com.foxlink.realtime.DAO.CountEmpDAO;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.ManPowerStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



public class CountEmpService extends Service<ManPowerStatus> {
	private static Logger logger = Logger.getLogger(CountEmpService.class);
	private CountEmpDAO countEmpDAO;
	
	public void setCountEmpDAO(CountEmpDAO countEmpDAO) {
		this.countEmpDAO = countEmpDAO;
	}

	public  List<String> FindDepidRecords(String userDataCostId) {
		// TODO Auto-generated method stub
		List<String> allStatus=null;
		try {
			String time = countEmpDAO.searchDate().substring(0,10);
			allStatus=countEmpDAO.FindDepidRecords(userDataCostId,time);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}

	@Override
	public boolean AddNewRecord(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ManPowerStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindRecord(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindQueryRecords(String userDataCostId, ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindQueryRecord(String userDataCostId, int currentPage, ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String ShowCountEmpList(String depid, String sDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		int sun = countEmpDAO.searchSunCount(depid,sDate);
		int night = countEmpDAO.searchNightCount(depid,sDate);
		List<ManPowerStatus>countEmpList = countEmpDAO.searchCountEmpList(depid,sDate);
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(countEmpList.size() == 0 || countEmpList == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("Sun", sun);
			result.addProperty("Night", night);
			result.addProperty("countEmpList", gson.toJson(countEmpList));
		}
		
		//System.out.println(result.toString());
		return result.toString();
	}

	public boolean UpdateStatus(String userNo, String depid, String sDate, String type_status, String class_no) {
		// TODO Auto-generated method stub
		return countEmpDAO.UpdateStatus(userNo,depid,sDate,type_status,class_no);
	}

	public List<String> FindAssistantDepid(String username) {
		// TODO Auto-generated method stub
		List<String> allStatus=null;
		try {
			allStatus=countEmpDAO.FindAssistantDepid(username);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allStatus;
	}
	

}
