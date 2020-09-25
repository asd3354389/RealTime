package com.foxlink.realtime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.DeparturePersonDAO;
import com.foxlink.realtime.model.DeparturePerson;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;

public class DeparturePersonService {
	private static Logger logger=Logger.getLogger(DeparturePersonService.class);  
	private DeparturePersonDAO departurePersonDAO;
	public void setDeparturePersonDAO(DeparturePersonDAO departurePersonDAO) {
		this.departurePersonDAO = departurePersonDAO;
	}
	public Page getDeparturePersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		int totalRecord = departurePersonDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<DeparturePerson> AllempIpBinding = null;
		try{
			int totalRecord = departurePersonDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllempIpBinding = departurePersonDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllempIpBinding;
	}
//setdeparturePerson
	public JsonObject setdeparturePerson(DeparturePerson empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		JsonObject AddResult=new JsonObject();
		//String[] ipArray = empIpBinding.getName().split(",");
		String message = "";
		String[] empArray = empIpBinding.getEmp_id().split(",");
		List<String> empList = Arrays.asList(empArray);
		HashSet<String> exEmpset = new HashSet();
		HashSet<String> noexEmpset = new HashSet();
		HashSet<String> ableexEmpset = new HashSet();
		HashSet<String> unableexEmpset = new HashSet();
		System.out.println("===========================員工信息service"+empList.size());
		System.out.println("===========================員工信息service"+empIpBinding.getEmp_id());
		List<String> ableexEmpList = new ArrayList();
		List<String> unableexEmpList = new ArrayList();
		//判斷員工是否存在
		for (String empID : empArray) {
			if(departurePersonDAO.checkEmpID(empID.toUpperCase())){
				exEmpset.add(empID.toUpperCase());
			}else{
				noexEmpset.add(empID.toUpperCase());
			}
		}
		List<String> exEmpList = new ArrayList(exEmpset);
		List<String> noexEmpList = new ArrayList(noexEmpset);
		if(empList.size()>0 && exEmpList.size()>0){
			//for (String ip : ipArray) {
				//Matcher mat = pat.matcher(ip);
				//System.out.println("ip:"+ip+"-----"+mat.matches());*/
				//if(mat.matches()){
		boolean isinset = departurePersonDAO.insertEmpIPBinding(empList,updateUser);
		message += "員工創建成功\n";
		AddResult.addProperty("StatusCode", "200");
		System.out.println("==========================建立員工信息状态"+isinset);
					//if(departurePersonDAO.insertEmpIPBinding(ip,empList,updateUser)){
						//ableexEmpList.add(ip);
					/*}else{
						unableexEmpList.add(ip);
					}*/
				/*}else{
					unableexEmpList.add(ip);
				}*/
			//}
		}else{
			message += "無在職員工工號\n";
		}
		
		/*if(ableexEmpList.size()>0&&unableexEmpList.size()==0){
			message += "員工創建成功\n";
			AddResult.addProperty("StatusCode", "200");
		}else if(ableexEmpList.size()>0&&unableexEmpList.size()>0){
			message += "員工創建成功\n";
			AddResult.addProperty("StatusCode", "200");
		}else{
			AddResult.addProperty("StatusCode", "500");
		}*/
		
		/*if(unableexEmpList.size()>0){
			for(int i = 0;i<unableexEmpList.size();i++){
				if(i==unableexEmpList.size()-1){
					message += unableexEmpList.get(i)+"員工綁定失敗,請重新輸入\n";
				}else{
					message += unableexEmpList.get(i)+",";
				}
			}
		}*/
		
		/*if(noexEmpList.size()>0){
			for(int i = 0;i<noexEmpList.size();i++){
				if(i==noexEmpList.size()-1){
					message += noexEmpList.get(i)+"員工綁定失敗,請重新輸入\n";
				}else{
					message += noexEmpList.get(i)+",";
				}
			}
		}*/
		
		
		/*if(empIPBindingDAO.checkRepeat(empIpBinding)){
			if(empIPBindingDAO.insertEmpIPBinding(empIpBinding,updateUser)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "新增員工綁定車間ip成功");
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "新增員工綁定車間ip失敗");
			}
		}else{
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "該卡機ip與該員工已綁定，無法新增");
		}*/
		
		AddResult.addProperty("Message", message);
		return AddResult;
	}

	public boolean UpdateRecord(DeparturePerson empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		if(departurePersonDAO.checkRepeat(empIpBinding)){
			return departurePersonDAO.UpdateRecord(empIpBinding,updateUser);
		}else{
			return false;
		}
	}

	public boolean DeleteDeparturePerson(DeparturePerson[] empIpBindings,String updateUser) {
		// TODO Auto-generated method stub
		return departurePersonDAO.DeleteEmpIPBinding(empIpBindings, updateUser);
	}

	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = departurePersonDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
}
