package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.foxlink.realtime.DAO.FLinePersonMtDAO;
import com.foxlink.realtime.DAO.IpBindingDAO;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.IpBinding;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




public class IpBindingService extends Service<IpBinding> {
	private static Logger logger = Logger.getLogger(IpBindingService.class);
	private IpBindingDAO ipBindingDAO;
	
	
	public void setIpBindingDAO(IpBindingDAO ipBindingDAO) {
		this.ipBindingDAO = ipBindingDAO;
	}
	
	//綁定ip(insert數據)
	public String BindingIp(String DeviceIp,String DeptId,String ID) {
		JsonObject result = new JsonObject();
		
		//返回的異常信息
		
		//System.out.println("卡機Ip=======>>"+DeviceIp);
		//String com_ip = ipBindingDAO.SelectAppIp(DeviceIp);
		boolean isSuccessful = ipBindingDAO.insertIPBinding(DeviceIp,DeptId,ID);
		List<String> ListRe = ipBindingDAO.ListRe();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (isSuccessful ) {
			result.addProperty("StatusCode", "200");
			result.addProperty("message", "綁定成功");
		} else {
			result.addProperty("StatusCode", "500");
			//result.addProperty("message", "綁定失敗,資料庫中已經錄入此Ip或資料錄入錯誤!!");
			result.addProperty("message", gson.toJson(ListRe));
		}
		System.out.println(result.toString());
		return result.toString();
	}

	//顯示綁定的ip地址
	public String ShowAllIpList() {
		// TODO Auto-generated method stub
				JsonObject result = new JsonObject();
				List<IpBinding>SpecList = ipBindingDAO.ShowAllIpList();
				//System.out.println("專案號"+strProNumber2V);
				Gson gson = new GsonBuilder().serializeNulls().create();
				if(SpecList.size()==0||SpecList==null) {
					result.addProperty("StatusCode", "500");
					result.addProperty("message", "查無數據");
				}else {
					result.addProperty("StatusCode", "200");
					result.addProperty("message", gson.toJson(SpecList));
				}
				System.out.println(result.toString());
				return result.toString();
		
	}
	@Override
	public boolean AddNewRecord(IpBinding t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(IpBinding t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IpBinding> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpBinding> FindRecord(IpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpBinding> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpBinding> FindQueryRecords(String userDataCostId, IpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IpBinding> FindQueryRecord(String userDataCostId, int currentPage, IpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
