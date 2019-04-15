package com.foxlink.realtime.service;

import java.util.List;
import org.apache.log4j.Logger;
import com.foxlink.realtime.DAO.FLinePersonMtDAO;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.Employee;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;



public class FLinePersonMtService extends Service<Employee> {
	private static Logger logger = Logger.getLogger(FLinePersonMtService.class);
	private FLinePersonMtDAO fLinePersonMtDAO;
	
	

	public void setFLinePersonMtDAO(FLinePersonMtDAO fLinePersonMtDAO) {
		this.fLinePersonMtDAO = fLinePersonMtDAO;
	}
	


	public List<Employee> FindQueryRecordY(String updateUser, int currentPage, String queryCritirea, String queryParam,String userDataCostId) {
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		try{
			int totalRecord = fLinePersonMtDAO.getTotalRecordY(queryCritirea, queryParam,updateUser, userDataCostId);
			AllEmp = fLinePersonMtDAO.FindAllRecordsY(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find FLinePersonMtDAOY Record is failed ",e);
		}
		return AllEmp;
	}

	public Page getFindPersonPageY(int currentPage, String queryCritirea, String queryParam, String updateUser, String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = fLinePersonMtDAO.getTotalRecordY(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public Page getFindPersonPageN(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = fLinePersonMtDAO.getTotalRecordN(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<Employee> FindQueryRecordN(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		try{
			int totalRecord = fLinePersonMtDAO.getTotalRecordN(queryCritirea, queryParam,updateUser, userDataCostId);
			AllEmp = fLinePersonMtDAO.FindAllRecordsN(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find FLinePersonMtDAOY Record is failed ",e);
		}
		return AllEmp;
	}

	public String getToPerson(Employee[] emp) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = fLinePersonMtDAO.getToPerson(emp);
		if (emp!=null) {
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "隨綫狀態修改成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "隨綫狀態修改失敗");
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "隨綫狀態修改錯誤");
		}
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}

	public Page getFindPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = fLinePersonMtDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		try{
			int totalRecord = fLinePersonMtDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			System.out.println(totalRecord);
			if (totalRecord>0) {
				AllEmp = fLinePersonMtDAO.FindAllRecordsY(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
			} else {
				System.out.println(123);
				AllEmp = fLinePersonMtDAO.FindAllRecordsN(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find FLinePersonMtDAOY Record is failed ",e);
		}
		return AllEmp;
	}

	public List FindQueryRecords(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<Employee> AllEmp = null;
		try{
			int totalRecord = fLinePersonMtDAO.getTotalRecordN(queryCritirea, queryParam,updateUser, userDataCostId);
			AllEmp = fLinePersonMtDAO.FindAllRecord(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find FLinePersonMtDAOY Record is failed ",e);
		}
		return AllEmp;
	}

	public String getAllPerson(String updateUser, String userDataCostId, String status) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = fLinePersonMtDAO.getToPerson( updateUser, userDataCostId,status);
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "隨綫狀態修改成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "隨綫狀態修改失敗");
			}		
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}

	public String getAllPersonCondition(String updateUser, String userDataCostId, String status, String queryCritirea,
			String queryParam) {
		JsonObject resultJson = new JsonObject();
		int result = fLinePersonMtDAO.getToPersonCondition( updateUser, userDataCostId,status,queryCritirea,queryParam);
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "隨綫狀態修改成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "隨綫狀態修改失敗");
			}		
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}



	@Override
	public boolean AddNewRecord(Employee t) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean UpdateRecord(Employee t) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public List<Employee> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Employee> FindRecord(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Employee> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Employee> FindQueryRecords(String userDataCostId, Employee t) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Employee> FindQueryRecord(String userDataCostId, int currentPage, Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
