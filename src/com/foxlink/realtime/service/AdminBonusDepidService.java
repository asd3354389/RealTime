package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.AdminBonusDepidDAO;
import com.foxlink.realtime.model.BonusDeptid;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;


public class AdminBonusDepidService extends Service<BonusDeptid>{
private AdminBonusDepidDAO adminBonusDepidDAO;
private static Logger logger = Logger.getLogger(AdminBonusDepidService.class);
	public void setAdminBonusDepidDAO(AdminBonusDepidDAO adminBonusDepidDAO) {
		this.adminBonusDepidDAO = adminBonusDepidDAO;
	}
	public Page getFindBonusPage(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord = adminBonusDepidDAO.getTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	@Override
	public boolean AddNewRecord(BonusDeptid t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean UpdateRecord(BonusDeptid t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<BonusDeptid> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BonusDeptid> FindRecord(BonusDeptid t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BonusDeptid> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BonusDeptid> FindQueryRecords(String userDataCostId, BonusDeptid t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BonusDeptid> FindQueryRecord(String userDataCostId, int currentPage, BonusDeptid t) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<BonusDeptid> FindQueryBonus(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<BonusDeptid> AllBonus = null;
		try{
			int totalRecord = adminBonusDepidDAO.getTotalRecord(queryCritirea, queryParam);
			AllBonus = adminBonusDepidDAO.FindAllBonus(currentPage,totalRecord, queryCritirea, queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find BonusDeptid Record is failed ",e);
		}
		return AllBonus;
	}
	public boolean RelieveBonusDeptid(BonusDeptid[] bonusDeptid, String updateUser, String accessRole) {
		// TODO Auto-generated method stub
		return adminBonusDepidDAO.RelieveBonusDeptid(bonusDeptid,updateUser,accessRole);
	}
	public boolean UpdateBonus(BonusDeptid bonusDeptid) {
		// TODO Auto-generated method stub
		return adminBonusDepidDAO.UpdateBonus(bonusDeptid);
	}
	public String addBonusDeptid(String deptid, String bonus_Rule) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = adminBonusDepidDAO.addBonusDeptid(deptid, bonus_Rule);
		System.out.print(result);
			if(result>0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "頂崗津貼信息設定成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "頂崗津貼信息設定失敗");
			}
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}
	public boolean checkBonusByDeptid(String deptid) {
		// TODO Auto-generated method stub
		return adminBonusDepidDAO.checkBonusByDeptid(deptid);
	}
}
