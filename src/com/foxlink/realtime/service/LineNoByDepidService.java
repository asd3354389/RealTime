package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxlink.realtime.DAO.LineNoByDepidDAO;
import com.foxlink.realtime.model.LineNoByDepid;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.google.gson.JsonObject;

@Service
public class LineNoByDepidService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private LineNoByDepidDAO lineNoByDepidDAO;

	public List<String> FindLineNo(String workShopNo) {
		// TODO Auto-generated method stub
		return lineNoByDepidDAO.FindLineNo(workShopNo);
	}

	public boolean checkWorkShopStatud(String lineNo, String workShopNo) {
		// TODO Auto-generated method stub
		return lineNoByDepidDAO.checkWorkShopStatud(lineNo,workShopNo);
	}

	public String addLineNoByDepid(LineNoByDepid[] lineNoByDepid, String updateUser) {
		// TODO Auto-generated method stub
		JsonObject resultJson = new JsonObject();
		int result = lineNoByDepidDAO.addLineNoByDepid(lineNoByDepid, updateUser);
		if (lineNoByDepid!=null) {
			if(result==0){
				resultJson.addProperty("StatusCode", "200");
				resultJson.addProperty("Message", "車間綫體綁定綫組別代碼設定成功");
			}else{
				resultJson.addProperty("StatusCode", "500");
				resultJson.addProperty("Message", "車間綫體綁定綫組別代碼設定失敗");
			}
		} else {
			resultJson.addProperty("StatusCode", "500");
			resultJson.addProperty("Message", "車間綫體綁定綫組別代碼設定錯誤");
		}
		System.out.println(resultJson.toString());
		return resultJson.toString();
	}

	public Page getLineNoByDepidPage(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord = lineNoByDepidDAO.getTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		return page;
	}

	public List<LineNoByDepid> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<LineNoByDepid> AllLNBDepid = null;
		try{
			int totalRecord = lineNoByDepidDAO.getTotalRecord(queryCritirea, queryParam);
			AllLNBDepid = lineNoByDepidDAO.FindAllRecords(currentPage,totalRecord, queryCritirea, queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find LineNoByDepid Record is failed ",e);
		}
		return AllLNBDepid;
	}

	public boolean DeleteLineNoByDepid(String workShopNo, String lineNo, String depid, String updateUser) {
		// TODO Auto-generated method stub
		return lineNoByDepidDAO.DeleteLineNoByDepid(workShopNo,lineNo,depid,updateUser);
	}
}
