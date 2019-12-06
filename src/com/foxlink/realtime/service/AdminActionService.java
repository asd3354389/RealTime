package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.AdminActionDao;
import com.foxlink.realtime.model.AppLogin;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.IpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.google.gson.JsonObject;

public class AdminActionService extends Service<Emp>{
	private static Logger logger = Logger.getLogger(AdminActionService.class);
	private AdminActionDao adminActionDao;
	public void setAdminActionDao(AdminActionDao adminActionDao) {
		this.adminActionDao = adminActionDao;
	}

	@Override
	public boolean AddNewRecord(Emp t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Emp t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Emp> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindRecord(Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindQueryRecords(String userDataCostId, Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Emp> FindQueryRecord(String userDataCostId, int currentPage, Emp t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Emp> FindQueryRecord(String queryCritirea, String queryParam) throws Exception {
		return adminActionDao.FindAllRecords(queryCritirea, queryParam);
		// TODO Auto-generated method stub
		
	}

	public List<String> FindHolidayYList() {
		// TODO Auto-generated method stub
		return adminActionDao.FindHolidayYList();
	}

	public List<String> FindHoliday(String queryParam, String holidayType) {
		// TODO Auto-generated method stub
		return adminActionDao.FindHoliday(queryParam,holidayType);
	}

	public Boolean DeleteHoliday(String delete_date) {
		// TODO Auto-generated method stub
		return adminActionDao.DeleteHoliday(delete_date);
	}

	public boolean AddHoliday(String holidayType, String holidayDate) {
		// TODO Auto-generated method stub
		return adminActionDao.AddHoliday(holidayType,holidayDate);
	}

	public boolean checkHoliday(String holidayDate) {
		// TODO Auto-generated method stub
		return adminActionDao.checkHoliday(holidayDate);
	}

	public Page getIpBindingCostSCPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId, String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord = adminActionDao.getIpBindingCostSCTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId,accessRole);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryIpBindingCostSCRecord(String updateUser, int currentPage, String queryCritirea,
			String queryParam, String userDataCostId, String accessRole) {
		// TODO Auto-generated method stub
		List<IpBinding> AllIpBindingCostSCInfo = null;
		try{
			int totalRecord = adminActionDao.getIpBindingCostSCTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
			AllIpBindingCostSCInfo = adminActionDao.FindQueryIpBindingCostSCRecord(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllIpBindingCostSCInfo;
	}

	public JsonObject setIpBindingCostSCInfo(IpBinding ipBinding, String updateUser) {
		// TODO Auto-generated method stub
		System.out.println(ipBinding.getDEVICEIP()+ipBinding.getCOSTID());
		String ip = ipBinding.getDEVICEIP();
		String[] costid = ipBinding.getCOSTID().split("\\*");
		JsonObject AddResult=new JsonObject();
		if(adminActionDao.insertIpBindingCostSCInfo(ip,costid,updateUser)){
			AddResult.addProperty("StatusCode", "200");
			AddResult.addProperty("Message", "新增卡機綁定可刷卡費用代碼成功");
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增卡機綁定可刷卡費用代碼失敗");
		}
		return AddResult;
	}

	public boolean DeleteIpBindingCostSC(String ip, String costid, String updateUser) {
		// TODO Auto-generated method stub
		return adminActionDao.DeleteIpBindingCostSC(ip,costid,updateUser);
	}

	public Page getAppLoginPage(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord = adminActionDao.getAppLoginTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryAppLoginRecord(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<AppLogin> AllAppLoginInfo = null;
		try{
			int totalRecord = adminActionDao.getAppLoginTotalRecord(queryCritirea, queryParam);
			AllAppLoginInfo = adminActionDao.FindQueryAppLoginRecord(currentPage, totalRecord, queryCritirea, queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllAppLoginInfo;
	}

	public boolean DeleteAppLogin(String ip, String updateUser) {
		// TODO Auto-generated method stub
		return adminActionDao.DeleteAppLogin(ip,updateUser);
	}

	public JsonObject setAppLoginInfo(AppLogin appLogin, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject AddResult=new JsonObject();
		if(adminActionDao.insertAppLoginInfo(appLogin,updateUser)){
			AddResult.addProperty("StatusCode", "200");
			AddResult.addProperty("Message", "新增實時卡機ip管控信息成功");
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增實時卡機ip管控信息失敗");
		}
		return AddResult;
	}

}
