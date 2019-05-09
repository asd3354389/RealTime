package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.EmpIPBindingDAO;
import com.foxlink.realtime.model.EmpIpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.google.gson.JsonObject;

public class EmpIPBindingService {
	private static Logger logger=Logger.getLogger(EmpIPBindingService.class);  
	private EmpIPBindingDAO empIPBindingDAO;
	public void setEmpIPBindingDAO(EmpIPBindingDAO empIPBindingDAO) {
		this.empIPBindingDAO = empIPBindingDAO;
	}
	
	public Page getEmpIPBindingPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		int totalRecord = empIPBindingDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<EmpIpBinding> AllempIpBinding = null;
		try{
			int totalRecord = empIPBindingDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllempIpBinding = empIPBindingDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllempIpBinding;
	}

	public JsonObject setEmpIPBinding(EmpIpBinding empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject AddResult=new JsonObject();
		if(empIPBindingDAO.checkRepeat(empIpBinding)){
			if(empIPBindingDAO.insertEmpIPBinding(empIpBinding,updateUser)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "新增員工綁定車間ip成功");
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "新增員工綁定車間ip失敗");
			}
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "該卡機ip與該員工已綁定，無法新增");
		}
		return AddResult;
	}

	public boolean UpdateRecord(EmpIpBinding empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		if(empIPBindingDAO.checkRepeat(empIpBinding)){
			return empIPBindingDAO.UpdateRecord(empIpBinding,updateUser);
		}else{
			return false;
		}
	}

	public boolean DeleteEmpIPBinding(String deviceIP,String emp_id, String updateUser) {
		// TODO Auto-generated method stub
		return empIPBindingDAO.DeleteEmpIPBinding(deviceIP,emp_id, updateUser);
	}

	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = empIPBindingDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	
	
}
