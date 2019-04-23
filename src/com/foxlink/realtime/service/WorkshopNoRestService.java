package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.WorkshopNoRestDao;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.google.gson.JsonObject;

public class WorkshopNoRestService {
	private static Logger logger=Logger.getLogger(WorkshopNoRestService.class);
	private WorkshopNoRestDao workshopNoRestDao;
	public void setWorkshopNoRestDao(WorkshopNoRestDao workshopNoRestDao) {
		this.workshopNoRestDao = workshopNoRestDao;
	}
	public Page getworkshopNoRestPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		int totalRecord = workshopNoRestDao.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public List<WorkshopNoRestInfo> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		List<WorkshopNoRestInfo> AllworkshopNoRestInfo = null;
		try{
			int totalRecord = workshopNoRestDao.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllworkshopNoRestInfo = workshopNoRestDao.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllworkshopNoRestInfo;
	}
	public boolean UpdateRecord(WorkshopNoRestInfo workshopNoRestInfo, String updateUser) {
		// TODO Auto-generated method stub
		return workshopNoRestDao.UpdateRecord(workshopNoRestInfo,updateUser);
	}
	public JsonObject setWorkShopNoRestInfo(WorkshopNoRestInfo workshopNoRestInfo, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject AddResult=new JsonObject();
		if(workshopNoRestDao.checkRepeat(workshopNoRestInfo)){
			if(workshopNoRestDao.insertWorkShopNoRestInfo(workshopNoRestInfo,updateUser)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "新增車間休息時間段成功");
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "新增車間休息時間段失敗");
			}
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "該車間已設置休息時間段");
		}
		return AddResult;
	}
	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = workshopNoRestDao.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public boolean DeleteWorkshopNoRest(String workshopNo, String updateUser) {
		// TODO Auto-generated method stub
		return workshopNoRestDao.DeleteWorkshopNoRest(workshopNo,updateUser);
	}
}
