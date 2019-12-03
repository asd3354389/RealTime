package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.WSListStatusDAO;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WSListStatus;
import com.foxlink.realtime.model.WorkShopStatus;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



public class WSListStatusService extends Service<WSListStatus> {
	private static Logger logger = Logger.getLogger(WSListStatusService.class);
	private WSListStatusDAO wSListStatusDAO;
	
	public void setWSListStatusDAO(WSListStatusDAO wSListStatusDAO) {
		this.wSListStatusDAO = wSListStatusDAO;
	}

	@Override
	public boolean AddNewRecord(WSListStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(WSListStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WSListStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindRecord(WSListStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindQueryRecords(String userDataCostId, WSListStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WSListStatus> FindQueryRecord(String userDataCostId, int currentPage, WSListStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getFindWslsPage(int currentPage, String queryCritirea, String queryParam, String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord = wSListStatusDAO.getTotalRecord(queryCritirea, queryParam,accessRole);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<WSListStatus> FindQueryWsls(int currentPage, String queryCritirea, String queryParam, String accessRole) {
		// TODO Auto-generated method stub
		List<WSListStatus> AllWsls = null;
		try{
			int totalRecord = wSListStatusDAO.getTotalRecord(queryCritirea, queryParam,accessRole);
			AllWsls = wSListStatusDAO.FindAllWslss(currentPage,totalRecord, queryCritirea, queryParam,accessRole);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find WSListStatus Record is failed ",e);
		}
		return AllWsls;
	}

	public boolean RlWSListStatus(WSListStatus[] wSListStatus, String updateUser) {
		// TODO Auto-generated method stub
		return wSListStatusDAO.RlWSListStatus(wSListStatus,updateUser);
	}

	public  List<String> FindReasonClass() {
		// TODO Auto-generated method stub
		return wSListStatusDAO.FindReasonClass();
	}

	public String addWSListStatus(String reason_Class, String reason_Desc, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = wSListStatusDAO.addWSListStatus(reason_Class,reason_Desc, updateUser);
			if(result>0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "原因描述設定成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "原因描述設定失敗");
			}
		return resultJson.toString();
	}

	public boolean checkWSListStatus(String Reason_Class, String Reason_Desc) {
		// TODO Auto-generated method stub
		return wSListStatusDAO.checkWSListStatus(Reason_Class,Reason_Desc);
	}
}
