package com.foxlink.realtime.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.EmpPrivilegeDAO;
import com.foxlink.realtime.model.EmpPrivilege;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;

public class EmpPrivilegeService extends Service<EmpPrivilege>{
	private static Logger logger = Logger.getLogger(EmpPrivilegeService.class);
	private EmpPrivilegeDAO empPrivilegeDAO;
	
	public void setEmpPrivilegeDAO(EmpPrivilegeDAO empPrivilegeDAO) {
		this.empPrivilegeDAO = empPrivilegeDAO;
	}

	@Override
	public boolean AddNewRecord(EmpPrivilege t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpPrivilege t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpPrivilege> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpPrivilege> FindRecord(EmpPrivilege t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpPrivilege> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<EmpPrivilege> AllEmpPrivilege = null;
		try{
			int totalRecord = empPrivilegeDAO.getTotalRecord(queryCritirea, queryParam);
			AllEmpPrivilege = empPrivilegeDAO.FindAllRecords(currentPage,totalRecord, queryCritirea, queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find WorkShopStatus Record is failed ",e);
		}
		return AllEmpPrivilege;
	}

	@Override
	public List<EmpPrivilege> FindQueryRecords(String userDataCostId, EmpPrivilege t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpPrivilege> FindQueryRecord(String userDataCostId, int currentPage, EmpPrivilege t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getEmpPrivilegePage(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord = empPrivilegeDAO.getTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		return page;
	}

	public String addEmpPrivilege(String id, String privilege, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		String message = "";
		String[] idArray = id.split(",");
		Set idSet = new HashSet<String>();
		for (int i = 0; i < idArray.length; i++) {
			idSet.add(idArray[i].toUpperCase());
		}
		List<String> idList = new ArrayList<>(idSet);
		List<String> existIdList = new ArrayList<>();
		List<String> noExistIdList = new ArrayList<>();
		for (int i = 0; i < idList.size(); i++) {
			int idCount = empPrivilegeDAO.checkEmpExist(idList.get(i));
			if(idCount==1){
				existIdList.add(idList.get(i));
			}else{
				noExistIdList.add(idList.get(i));
			}
		}
		
		if(existIdList.size()>0){
			int result =	empPrivilegeDAO.addEmpPrivilege(existIdList,privilege,updateUser);
				
				if(result==0){
					message+="添加人員權限成功。";
				}else{
					message+="添加人員權限失敗。";
				}
		}
		
		if(noExistIdList.size()>0){
			for (int i = 0; i < noExistIdList.size(); i++) {
				if(i == noExistIdList.size()-1){
					message+=noExistIdList.get(i);
				}else{
					message+=noExistIdList.get(i)+",";
				}
				message+="以上工號不存在";
			}
		}
		resultJson.addProperty("StatusCode", "200");
		resultJson.addProperty("Message", message);
		
		return resultJson.toString();
	}

	public boolean RlEmpPrivilege(EmpPrivilege[] empPrivilege, String updateUser) {
		// TODO Auto-generated method stub
		return empPrivilegeDAO.RlEmpPrivilege(empPrivilege,updateUser);
	}

}
