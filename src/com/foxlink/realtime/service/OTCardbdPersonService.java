package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.OTCardbdPersonDAO;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OTCardbdPersonService extends Service<Emp>{
	private static Logger logger = Logger.getLogger(OTCardbdPersonService.class);
	private OTCardbdPersonDAO oTCardbdPersonDAO;
	
	public void setOTCardbdPersonDAO(OTCardbdPersonDAO oTCardbdPersonDAO) {
		this.oTCardbdPersonDAO = oTCardbdPersonDAO;
	}
	
	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord = oTCardbdPersonDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId,accessRole);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<OTCardBD> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId,String accessRole) {
		// TODO Auto-generated method stub
		List<OTCardBD> AllEmp = null;
		try{
			int totalRecord = oTCardbdPersonDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
			/*System.out.println(totalRecord);*/
			AllEmp = oTCardbdPersonDAO.FindAllRecord(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId,accessRole);
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

	public boolean checkUserNameDuplicate(String CostId) {
		// TODO Auto-generated method stub
		/*System.out.println(oTCardbdPersonDAO.checkUserNameDuplicate(Dmp_id));*/
		return oTCardbdPersonDAO.checkUserNameDuplicate(CostId);
	}

	public boolean OTCardbd(OTCardBD otCardbd,String accessRole) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.OTCardbdPerson(otCardbd,accessRole);
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

	public boolean UpdateBdOTCard(OTCardBD otCardbd, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.UpdateBdOTCard(otCardbd,updateUser,accessRole);
	}

	public boolean RelieveOTCard(OTCardBD[] otCardbd, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.RelieveOTCard(otCardbd,updateUser,accessRole);
	}

	public String ShowDeptNo(String CostId) {
		// TODO Auto-generated method stub
		    JsonObject result = new JsonObject();
	        List<GetDepid> list_dcResult = oTCardbdPersonDAO.ShowDeptNo(CostId);
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

	public boolean OTCardNbdPerson(OTCardBD[] otCardbd, String updateUser,String accessRole) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.OTCardNbdPerson(otCardbd,updateUser,accessRole);
	}

	public boolean checkDcardDuplicate(String CostId, String D_CardId,String accessRole) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.checkDcardDuplicate(CostId,D_CardId,accessRole);
	}

	public boolean checkData(OTCardBD otCardbd,String accessRole) {
		// TODO Auto-generated method stub
		return oTCardbdPersonDAO.checkData(otCardbd,accessRole);
	}


}
