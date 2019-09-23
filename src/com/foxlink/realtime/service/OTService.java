package com.foxlink.realtime.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.OTDAO;
import com.foxlink.realtime.model.EmpInOTPendingSheet;
import com.foxlink.realtime.model.OTHourConfirm;
import com.foxlink.realtime.model.OverTimePending;
import com.foxlink.realtime.model.OverTimeSheet;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.util.CommonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OTService extends Service<OverTimeSheet> {
	private static Logger logger=Logger.getLogger(OTService.class);
	private OTDAO otDAO;
	
	public OTService() {
		super();
	}
	
	public void setOTDAO(OTDAO otDAO ) {
	      this.otDAO = otDAO;
	      }
	
	public List<OverTimeSheet> FindAllOTSheets(int[] checkState,String RCNO,String WorkshopNO,String LineNo,
			Date StartDate,Date EndDate,String AssistantAccount,boolean isAbnormal){
		List<OverTimeSheet> OTSheets=null;
		try {
			OTSheets=otDAO.FindAllRecords(checkState, RCNO, WorkshopNO, LineNo,StartDate, EndDate, AssistantAccount,isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllOTSheets is failed",ex);
		}
		return OTSheets;
	}
	
	/*加班單審核（更新加班時數）*/
	public boolean ConfirmEmpsOTHours(int isAbNormal,OTHourConfirm otHourConfirm) {
		boolean EmpsOTHoursConfirmed=false;
		try {
			if(otHourConfirm!=null)
				EmpsOTHoursConfirmed=otDAO.ConfirmEmpOTInfos(otHourConfirm,isAbNormal);
		}
		catch(Exception ex) {
			logger.error("ConfirmEmpsOTHours is failed",ex);
		}
		return EmpsOTHoursConfirmed;
	}
	
	/*加班單審核（更新加班時數）*/
	public boolean ConfirmEmpsOTHoursByDepid(int isAbNormal,OTHourConfirm otHourConfirm) {
		boolean EmpsOTHoursConfirmed=false;
		try {
			if(otHourConfirm!=null)
				EmpsOTHoursConfirmed=otDAO.ConfirmEmpOTInfosByDepid(otHourConfirm,isAbNormal);
		}
		catch(Exception ex) {
			logger.error("ConfirmEmpsOTHours is failed",ex);
		}
		return EmpsOTHoursConfirmed;
	}


	@Override
	public boolean AddNewRecord(OverTimeSheet t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean UpdateRecord(OverTimeSheet t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<OverTimeSheet> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OverTimeSheet> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OverTimeSheet> FindRecord(OverTimeSheet t) {
		return null;
	}

	
	@Override
	public List<OverTimeSheet> FindQueryRecord(String userDataCostId, int currentPage, OverTimeSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OverTimeSheet> FindQueryRecords(String userDataCostId, OverTimeSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OverTimeSheet> FindAllOTSheetsByDepid(int[] checkState,String RCNO,String WorkshopNO,String LineNo,
			Date StartDate,Date EndDate,String AssistantAccount,boolean isAbnormal) {
		// TODO Auto-generated method stub
		List<OverTimeSheet> OTSheets=null;
		try {
			OTSheets=otDAO.FindAllRecordsByDepid(checkState, RCNO, WorkshopNO, LineNo,StartDate, EndDate, AssistantAccount,isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllOTSheets is failed",ex);
		}
		return OTSheets;
	}


	public List<String> checkDeptIdExistence() {
		// TODO Auto-generated method stub
		return otDAO.FindAllRecordsByDepid();
	}

	public String updateBonus(String updateUser, OverTimePending[] overTimePending) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = otDAO.updateBonus(updateUser,overTimePending);
		if(result==0){
			resultJson.addProperty("StatusCode", "200");
			resultJson.addProperty("Message", "頂崗津貼時數修改成功");
		}else{
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "頂崗津貼時數修改失敗");
		}
		//System.out.println(resultJson.toString());
		return resultJson.toString();
	}

	public List<String> checkModifyEmpA(String[] empList) {
		// TODO Auto-generated method stub
		return otDAO.checkModifyEmpA(empList);
	}

	public List<String> checkModifyEmpB(String[] empList) {
		// TODO Auto-generated method stub
		return otDAO.checkModifyEmpB(empList);
	}

	public List<String> checkModifyEmpBonusA(String[] empList) {
		// TODO Auto-generated method stub
		return otDAO.checkModifyEmpBonusA(empList);
	}
	
	public List<String> checkModifyEmpBonusB(String[] empList) {
		// TODO Auto-generated method stub
		return otDAO.checkModifyEmpBonusB(empList);
	}
	
}
