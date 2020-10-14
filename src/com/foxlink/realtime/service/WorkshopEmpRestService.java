package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.WorkshopEmpRestDao;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopEmpRestInfo;
import com.google.gson.JsonObject;

public class WorkshopEmpRestService {
	private static Logger logger=Logger.getLogger(WorkshopEmpRestService.class);
	private WorkshopEmpRestDao workshopEmpRestDao;
	public void setWorkshopEmpRestDao(WorkshopEmpRestDao workshopNoRestDao) {
		this.workshopEmpRestDao = workshopNoRestDao;
	}
	public Page getworkshopNoRestPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId,String accessRole) {
		int totalRecord = workshopEmpRestDao.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId,accessRole);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public List<WorkshopEmpRestInfo> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId,String accessRole) {
		List<WorkshopEmpRestInfo> AllworkshopNoRestInfo = null;
		try{
			int totalRecord = workshopEmpRestDao.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
			AllworkshopNoRestInfo = workshopEmpRestDao.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllworkshopNoRestInfo;
	}
	public boolean UpdateRecord(WorkshopEmpRestInfo workshopNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return workshopEmpRestDao.UpdateRecord(workshopNoRestInfo,updateUser,accessRole);
	}
	public JsonObject setWorkShopNoRestInfo(WorkshopEmpRestInfo workshopNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		JsonObject AddResult=new JsonObject();
		if(workshopEmpRestDao.checkRepeat(workshopNoRestInfo,accessRole)){
			if(workshopEmpRestDao.insertWorkShopNoRestInfo(workshopNoRestInfo,updateUser,accessRole)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "新增員工休息時間段成功");
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "新增員工休息時間段失敗");
			}
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "員工已設置該班別休息時間段");
		}
		return AddResult;
	}
	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord = workshopEmpRestDao.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId,accessRole);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public boolean DeleteWorkshopNoRest(String empId, String classNo,String updateUser) {
		// TODO Auto-generated method stub
		return workshopEmpRestDao.DeleteWorkshopNoRest(empId,classNo,updateUser);
	}
}
