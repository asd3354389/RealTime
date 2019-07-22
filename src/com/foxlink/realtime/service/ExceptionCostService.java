package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.ExceptionCostDAO;
import com.foxlink.realtime.model.Employee;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;

public class ExceptionCostService extends Service<ExceptionCost>{
	private static Logger logger = Logger.getLogger(ExceptionCostService.class);
	private ExceptionCostDAO exceptionCostDAO;
	
	public void setExceptionCostDAO(ExceptionCostDAO exceptionCostDAO) {
		this.exceptionCostDAO = exceptionCostDAO;
	}

	@Override
	public boolean AddNewRecord(ExceptionCost t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ExceptionCost t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ExceptionCost> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindRecord(ExceptionCost t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindQueryRecords(String userDataCostId, ExceptionCost t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExceptionCost> FindQueryRecord(String userDataCostId, int currentPage, ExceptionCost t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addExceptionCost(ExceptionCost[] exceptionCost, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = exceptionCostDAO.addExceptionCost(exceptionCost, updateUser,accessRole);
		if (exceptionCost!=null) {
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "車間列外費用代碼設定成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "車間列外費用代碼設定失敗");
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "車間列外費用代碼設定錯誤");
		}
		System.out.println(resultJson.toString());
		return resultJson.toString();
		
	}

	public Page getFindExcePage(int currentPage, String queryCritirea, String queryParam,String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord = exceptionCostDAO.getTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<ExceptionCost> FindQueryExce(int currentPage, String queryCritirea, String queryParam,String accessRole) {
		// TODO Auto-generated method stub
		List<ExceptionCost> AllExce = null;
		try{
			int totalRecord = exceptionCostDAO.getTotalRecord(queryCritirea, queryParam,accessRole);
			AllExce = exceptionCostDAO.FindAllExceps(currentPage,totalRecord, queryCritirea, queryParam,accessRole);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find ExceptionCost Record is failed ",e);
		}
		return AllExce;
	}

	public boolean UpdateExceCost(ExceptionCost exceptionCost, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return exceptionCostDAO.UpdateExceCost(exceptionCost,updateUser,accessRole);
	}

	public boolean RelieveExceCost(ExceptionCost[] exceptionCost, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return exceptionCostDAO.RelieveExceCost(exceptionCost,updateUser,accessRole);
	}

	public boolean checkExceCost(String CostId,String accessRole) {
		// TODO Auto-generated method stub
		return exceptionCostDAO.checkExceCost(CostId,accessRole);
	}

	public boolean checkWorkShopCost(String CostId, String WorkShopNo,String accessRole) {
		// TODO Auto-generated method stub
		return exceptionCostDAO.checkWorkShopCost(CostId,WorkShopNo,accessRole);
	}

}
