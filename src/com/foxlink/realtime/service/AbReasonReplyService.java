package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.AbReasonReplyDAO;
import com.foxlink.realtime.DAO.CountEmpDAO;
import com.foxlink.realtime.model.QueryByIdList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import oracle.net.aso.n;

public class AbReasonReplyService extends Service<QueryByIdList> {
	private static Logger logger = Logger.getLogger(AbReasonReplyService.class);
	private AbReasonReplyDAO abReasonReplyDAO;
	
	public void setAbReasonReplyDAO(AbReasonReplyDAO abReasonReplyDAO) {
		this.abReasonReplyDAO = abReasonReplyDAO;
	}

	public String ShowABReasonReply(String bu, String costid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<QueryByIdList>ABReason = abReasonReplyDAO.searchABReasonList(bu,costid,sDate,eDate);
		//System.out.println(ABReason);
		Gson gson = new GsonBuilder().serializeNulls().create();		
		if (ABReason.size()==0|| ABReason==null) {
			result.addProperty("StatusCode", "500");
			result.addProperty("message", "查無數據");
		} else {
			result.addProperty("StatusCode", "200");
			result.addProperty("ABReason", gson.toJson(ABReason));		
		}
		return result.toString();
	}

	@Override
	public boolean AddNewRecord(QueryByIdList t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryByIdList t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryByIdList> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindRecord(QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindQueryRecords(String userDataCostId, QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindQueryRecord(String userDataCostId, int currentPage, QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String replyReason(QueryByIdList[] emp) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = abReasonReplyDAO.replyReason(emp);
		if (emp!=null) {
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "異常原因回復成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "異常原因回復失敗");
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "異常原因回復發生錯誤");
		}
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}

	public String ShowABReplyByDepid(String depid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<QueryByIdList>ABReason = abReasonReplyDAO.searchABReasonByDepid(depid,sDate,eDate);
		//System.out.println(ABReason);
		Gson gson = new GsonBuilder().serializeNulls().create();		
		if (ABReason.size()==0|| ABReason==null) {
			result.addProperty("StatusCode", "500");
			result.addProperty("message", "查無數據");
		} else {
			result.addProperty("StatusCode", "200");
			result.addProperty("ABReason", gson.toJson(ABReason));		
		}
		return result.toString();
	}
}
