package com.foxlink.realtime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.EmpIPBindingDAO;
import com.foxlink.realtime.model.EmpIpBinding;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonObject;

public class EmpIPBindingService {
	private static Logger logger=Logger.getLogger(EmpIPBindingService.class);  
	private EmpIPBindingDAO empIPBindingDAO;
	public void setEmpIPBindingDAO(EmpIPBindingDAO empIPBindingDAO) {
		this.empIPBindingDAO = empIPBindingDAO;
	}
	
	public Page getEmpIPBindingPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		int totalRecord = empIPBindingDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<EmpIpBinding> AllempIpBinding = null;
		try{
			int totalRecord = empIPBindingDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllempIpBinding = empIPBindingDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllempIpBinding;
	}

	public JsonObject setEmpIPBinding(EmpIpBinding empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		JsonObject AddResult=new JsonObject();
		String[] ipArray = empIpBinding.getDeviceIP().split(",");
		String message = "";
		String[] empArray = empIpBinding.getEmp_id().split(",");
		List<String> empList = Arrays.asList(empArray);
		HashSet<String> exEmpset = new HashSet();
		HashSet<String> noexEmpset = new HashSet();
		HashSet<String> ableexEmpset = new HashSet();
		HashSet<String> unableexEmpset = new HashSet();
		
		List<String> ableexEmpList = new ArrayList();
		List<String> unableexEmpList = new ArrayList();
		//判斷員工是否存在
		for (String empID : empArray) {
			if(empIPBindingDAO.checkEmpID(empID.toUpperCase())){
				exEmpset.add(empID.toUpperCase());
			}else{
				noexEmpset.add(empID.toUpperCase());
			}
		}
		List<String> exEmpList = new ArrayList(exEmpset);
		List<String> noexEmpList = new ArrayList(noexEmpset);
		if(exEmpList.size()>0){
			for (String ip : ipArray) {
				Matcher mat = pat.matcher(ip);
				System.out.println("ip:"+ip+"-----"+mat.matches());
				if(mat.matches()){
					if(empIPBindingDAO.insertEmpIPBinding(ip,exEmpList,updateUser)){
						ableexEmpList.add(ip);
					}else{
						unableexEmpList.add(ip);
					}
				}else{
					unableexEmpList.add(ip);
				}
			}
		}else{
			message += "無在職員工工號\n";
		}
		
		if(ableexEmpList.size()>0&&unableexEmpList.size()==0){
			message += "ip與員工綁定成功\n";
			AddResult.addProperty("StatusCode", "200");
		}else if(ableexEmpList.size()>0&&unableexEmpList.size()>0){
			message += "部分ip與員工綁定成功\n";
			AddResult.addProperty("StatusCode", "200");
		}else{
			AddResult.addProperty("StatusCode", "500");
		}
		
		if(unableexEmpList.size()>0){
			for(int i = 0;i<unableexEmpList.size();i++){
				if(i==unableexEmpList.size()-1){
					message += unableexEmpList.get(i)+"以上ip添加失敗，或許是非正規ip地址,或者輸入法為中文輸入法，請用英文輸入法\n";
				}else{
					message += unableexEmpList.get(i)+",";
				}
			}
		}
		
		if(noexEmpList.size()>0){
			for(int i = 0;i<noexEmpList.size();i++){
				if(i==noexEmpList.size()-1){
					message += noexEmpList.get(i)+"以上員工不存在,或者輸入法為中文輸入法，請用英文輸入法\n";
				}else{
					message += noexEmpList.get(i)+",";
				}
			}
		}
		
		
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

	public boolean UpdateRecord(EmpIpBinding empIpBinding, String updateUser) {
		// TODO Auto-generated method stub
		if(empIPBindingDAO.checkRepeat(empIpBinding)){
			return empIPBindingDAO.UpdateRecord(empIpBinding,updateUser);
		}else{
			return false;
		}
	}

	public boolean DeleteEmpIPBinding(EmpIpBinding[] empIpBindings,String updateUser) {
		// TODO Auto-generated method stub
		return empIPBindingDAO.DeleteEmpIPBinding(empIpBindings, updateUser);
	}

	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = empIPBindingDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	
	
}
