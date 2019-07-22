package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.ClassNoRestDao;
import com.foxlink.realtime.model.ClassNO;
import com.foxlink.realtime.model.ClassNoRestInfo;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ClassNoRestService {
	private static Logger logger = Logger.getLogger(ClassNoRestService.class);
	private ClassNoRestDao classNoRestDao;
	public void setClassNoRestDao(ClassNoRestDao classNoRestDao) {
		this.classNoRestDao = classNoRestDao;
	}
	public Page getclassNoRestPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord = classNoRestDao.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId,accessRole);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public List<ClassNoRestInfo> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		List<ClassNoRestInfo> AllworkshopNoRestInfo = null;
		try{
			int totalRecord = classNoRestDao.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
			AllworkshopNoRestInfo = classNoRestDao.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllworkshopNoRestInfo;
	}
	public boolean UpdateRecord(ClassNoRestInfo classNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return classNoRestDao.UpdateRecord(classNoRestInfo,updateUser,accessRole);
	}
	public boolean DeleteClassNoRest(String costId, String class_No, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return classNoRestDao.DeleteClassNoRest(costId,class_No,updateUser,accessRole);
	}
	public List<String> FindClassNo() {
		// TODO Auto-generated method stub
		return classNoRestDao.FindClassNo();
	}
	public String FindClassNoCotent(String class_No) {
		// TODO Auto-generated method stub
		 JsonObject result = new JsonObject();
	        List<ClassNO> list_dcResult = classNoRestDao.FindClassNoCotent(class_No);
	        Gson gson = new GsonBuilder().serializeNulls().create();
	        if (list_dcResult.size() == 0 || list_dcResult == null) {
	            result.addProperty("StatusCode", "500");
	            result.addProperty("message", "查無數據");
	        } else {
	            result.addProperty("StatusCode", "200");
	            result.addProperty("message", gson.toJson(list_dcResult));
	        }
	        System.out.println(result.toString());
	        return result.toString();
	}
	public String setClassNoRestInfo(ClassNoRestInfo[] classNoRestInfo, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		JsonObject AddResult=new JsonObject();
		/*if(classNoRestDao.checkRepeat(classNoRestInfo)){
			if(classNoRestDao.insertWorkShopNoRestInfo(classNoRestInfo,updateUser)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "新增班別加班替換休息時間段成功");
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "新增班別加班替換休息時間段失敗");
			}
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "該班別加班替換休息時間段已設置");
		}
		return AddResult;*/
		
		int result = classNoRestDao.insertClassNoRestInfo(classNoRestInfo, updateUser,accessRole);
		if (classNoRestInfo!=null) {
			if(result==0){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "車間列外費用代碼設定成功");
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "車間列外費用代碼設定失敗");
			}
		} else {
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "車間列外費用代碼設定錯誤");
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
}
