package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.OTCardbdPersonDAO;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;

public class OTCardbdPersonService extends Service<Emp>{
	private static Logger logger = Logger.getLogger(OTCardbdPersonService.class);
	private OTCardbdPersonDAO oTCardbdPersonDAO;
	
	public void setOTCardbdPersonDAO(OTCardbdPersonDAO oTCardbdPersonDAO) {
		this.oTCardbdPersonDAO = oTCardbdPersonDAO;
	}
	
	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = oTCardbdPersonDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<OTCardBD> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<OTCardBD> AllEmp = null;
		try{
			int totalRecord = oTCardbdPersonDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			/*System.out.println(totalRecord);*/
			AllEmp = oTCardbdPersonDAO.FindAllRecord(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find PersonList Record is failed ",e);
		}
		return AllEmp;
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

	/*public String BDotCardPerson(OTCardBD[] otCardbd) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		if (oTCardbdPersonDAO) {
			
		} else {

		}
		int result = oTCardbdPersonDAO.getToPerson(otCardbd);
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
	}*/

	public boolean checkUserNameDuplicate(String Dmp_id) {
		// TODO Auto-generated method stub
		/*System.out.println(oTCardbdPersonDAO.checkUserNameDuplicate(Dmp_id));*/
		return oTCardbdPersonDAO.checkUserNameDuplicate(Dmp_id);
	}

	public boolean OTCardbd(OTCardBD otCardbd) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.OTCardbdPerson(otCardbd);
	}

	@Override
	public boolean AddNewRecord(Emp t) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkEmpIdExistence(String EmpId, String userDataCostId) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.checkEmpIdExistence(EmpId,userDataCostId);
	}

	public String UpdateBdOTCard(OTCardBD[] otCardbd, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = oTCardbdPersonDAO.UpdateBdOTCard(otCardbd,updateUser);
		if (otCardbd!=null) {
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "離崗卡和車間信息修改成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "離崗卡和車間信息修改失敗");
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "離崗卡和車間信息修改錯誤");
		}
		return resultJson.toString();
	}

	public String RelieveOTCard(OTCardBD[] otCardbd, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = oTCardbdPersonDAO.RelieveOTCard(otCardbd,updateUser);
		int insertHT = oTCardbdPersonDAO.OTCardNbdPerson(otCardbd, updateUser);
		if (otCardbd!=null) {
			if(result==0) {
				if(insertHT==0){
					resultJson.addProperty("StatusCode", "200");
					resultJson.addProperty("Message", "離崗卡和员工信息解绑成功");
				}else{
					resultJson.addProperty("StatusCode", "500");
					resultJson.addProperty("Message", "離崗卡和员工信息解绑失敗");
				}
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "離崗卡和员工信息解绑錯誤");
		}
		return resultJson.toString();
	}


}
