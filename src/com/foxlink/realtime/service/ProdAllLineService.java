package com.foxlink.realtime.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.CountEmpDAO;
import com.foxlink.realtime.DAO.ProdAllLineDAO;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.Prod;
import com.foxlink.realtime.model.ProdAllLine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ProdAllLineService extends Service<ProdAllLine> {
	private static Logger logger = Logger.getLogger(ProdAllLineService.class);
	private ProdAllLineDAO prodAllLineDAO;
	
	public void setProdAllLineDAO(ProdAllLineDAO prodAllLineDAO) {
		this.prodAllLineDAO = prodAllLineDAO;
	}

	public String SupportMachine(String costId, String sDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<ProdAllLine> SMachine = prodAllLineDAO.SupportMachine(costId,sDate);
		//List<String> ListRe = ipBindingDAO.ListRe();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (SMachine.size() == 0 || SMachine == null) {
            result.addProperty("StatusCode", "500");
            result.addProperty("message", "查無數據");
        } else {
            result.addProperty("StatusCode", "200");
            result.addProperty("message", gson.toJson(SMachine));
        }
		System.out.println(result.toString());
		return result.toString();
	}

	@Override
	public boolean AddNewRecord(ProdAllLine t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ProdAllLine t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProdAllLine> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindRecord(ProdAllLine t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindQueryRecords(String userDataCostId, ProdAllLine t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdAllLine> FindQueryRecord(String userDataCostId, int currentPage, ProdAllLine t) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean DelSupportMachine(String recordid) {
		// TODO Auto-generated method stub
		return prodAllLineDAO.DelSupportMachine(recordid);
	}

	public String AddSupportMachine(ProdAllLine prodAllLine) {
		// TODO Auto-generated method stub
		JsonObject AddResult=new JsonObject();
		try {
			if(prodAllLineDAO.AddSupportMachine(prodAllLine)>0){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "添加異動人數成功");	
			}else {
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "添加異動人數發生錯誤");
			}
		} catch (Exception ex) {
			// TODO: handle exception
				logger.error("Add the SupportMachine info is failed, due to:",ex);
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "添加異動人數已發生錯誤，原因:"+ex.toString());
		}
		
		return AddResult.toString();
	}

	private Date parse(String insert_Date) {
		// TODO Auto-generated method stub
		return null;
	}

	public String SupportMachineNum(String costId, String sDate) {
		// TODO Auto-generated method stub
		//String a ="123";
		JsonObject result = new JsonObject();
		List<ManPowerStatus>depidNumList = prodAllLineDAO.searchDepidNumList(costId,sDate);
		List<ManPowerStatus>countNumeList = prodAllLineDAO.searchCountNumList(costId,sDate);
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(depidNumList.size() == 0 || depidNumList == null||countNumeList.size() == 0 || countNumeList == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("countNumeList", gson.toJson(countNumeList));
			result.addProperty("depidNumList", gson.toJson(depidNumList));
		}
		
		//System.out.println(result.toString());
		return result.toString();
	}

	public String SupportMachineNumDetail(String costId, String sDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<Prod>depidNumDetail= prodAllLineDAO.searchDepidNumDetail(costId,sDate);
		List<Prod>countNumDetail = prodAllLineDAO.searchCountNumDetail(costId,sDate);
		Gson gson = new GsonBuilder().serializeNulls().create();
		
		System.out.println(depidNumDetail);
		System.out.println(countNumDetail);
		if(depidNumDetail.size() == 0 || depidNumDetail == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
	        result.addProperty("depidNumDetail", "null");
	        result.addProperty("countNumDetail", "null");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("depidNumDetail", gson.toJson(depidNumDetail));
			result.addProperty("countNumDetail", gson.toJson(countNumDetail));
		}
		
		//System.out.println(result.toString());
		return result.toString();
	}
}
