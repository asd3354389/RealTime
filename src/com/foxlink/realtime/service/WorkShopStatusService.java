package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.WorkShopStatusDAO;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WorkShopStatusService extends Service<WorkShopStatus> {
	private static Logger logger = Logger.getLogger(WorkShopStatusService.class);
	private WorkShopStatusDAO workShopStatusDAO;
	
	public void setWorkShopStatusDAO(WorkShopStatusDAO workShopStatusDAO) {
		this.workShopStatusDAO=workShopStatusDAO;
	}

	@Override
	public boolean AddNewRecord(WorkShopStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(WorkShopStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WorkShopStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkShopStatus> FindRecord(WorkShopStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkShopStatus> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<WorkShopStatus> AllWSStatus = null;
		try{
			int totalRecord = workShopStatusDAO.getTotalRecord(queryCritirea, queryParam);
			AllWSStatus = workShopStatusDAO.FindAllRecords(currentPage,totalRecord, queryCritirea, queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find WorkShopStatus Record is failed ",e);
		}
		return AllWSStatus;
	}

	@Override
	public List<WorkShopStatus> FindQueryRecords(String userDataCostId, WorkShopStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkShopStatus> FindQueryRecord(String userDataCostId, int currentPage, WorkShopStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getWorkShopStatusPage(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord = workShopStatusDAO.getTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		return page;
	}

	public List<String> FindLineNo(String workShopNo) {
		// TODO Auto-generated method stub
		return workShopStatusDAO.FindLineNo(workShopNo);
	}

	public String addWorkShopStatus(WorkShopStatus[] workShopStatus, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = workShopStatusDAO.addWorkShopStatus(workShopStatus, updateUser);
		if (workShopStatus!=null) {
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "車間綫號狀態設定成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "車間綫號狀態設定失敗");
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "車間綫號狀態設定錯誤");
		}
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}

	public boolean checkWorkShopStatud(String lineNo, String workShopNo) {
		// TODO Auto-generated method stub
		return workShopStatusDAO.checkWorkShopStatud(lineNo,workShopNo);
	}

	public boolean RlWorkShopStatus(WorkShopStatus[] workShopStatus, String updateUser) {
		// TODO Auto-generated method stub
		return workShopStatusDAO.RlWorkShopStatus(workShopStatus,updateUser);
	}
}
