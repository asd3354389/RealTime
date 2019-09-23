package com.foxlink.realtime.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.EmpPrivilege;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.foxlink.realtime.service.EmpPrivilegeService;
import com.foxlink.realtime.service.WorkShopStatusService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/EmpPrivilege")
public class EmpPrivilegeController {
	private static Logger logger = Logger.getLogger(EmpPrivilegeController.class);
	private WorkShopStatusService workShopStatusService;
	private EmpPrivilegeService empPrivilegeService;
	
	@RequestMapping(value="/ShowEmpPrivilege",method=RequestMethod.GET)
	public String ShowWorkShopStatusPage(){
		return "EmpPrivilege";
	}
	
	@RequestMapping(value = "/EmpPrivilegeList.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String EmpPrivilegeList(@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String jsonResults="";
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);
			 if(queryParam=="" || queryParam==null)
			    	queryCritirea="";	
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				empPrivilegeService = (EmpPrivilegeService) context.getBean("empPrivilegeService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				
				Page page=empPrivilegeService.getEmpPrivilegePage(currentPage,queryCritirea, queryParam);
				page.setList(empPrivilegeService.FindAllRecords(currentPage,queryCritirea, queryParam));
				jsonResults=gson.toJson(page);
				//System.out.println(jsonResults);
				//jsonResults=gson.toJson(workShopService.FindAllRecords());
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得車間員工權限列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		
		return jsonResults;
	}
	
	
	@RequestMapping(value="/AddEmpPrivilege.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddExceptionCost(HttpSession session,@RequestParam("id")String id,@RequestParam("privilege")String privilege){
//		JsonObject AddResult=new JsonObject();		
			System.out.println(id+privilege);
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			empPrivilegeService = (EmpPrivilegeService) context.getBean("empPrivilegeService");
			
			return empPrivilegeService.addEmpPrivilege(id, privilege,updateUser);
	}
	
	@RequestMapping(value="/checkWorkShopStatud.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkWorkShopCost(HttpSession session,@RequestParam("LineNo")String LineNo,@RequestParam("WorkShopNo")String WorkShopNo){
		JsonObject checkResult=new JsonObject();	
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopStatusService = (WorkShopStatusService) context.getBean("workShopStatusService");
			if(workShopStatusService.checkWorkShopStatud(LineNo,WorkShopNo)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "此車間綫體狀態已存在！");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "可以設置此車間綫體狀態!");
			}
		}
		catch(Exception ex){
			logger.error("Check new WorkShopStatud info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查此車間綫體狀態是否存在發生錯誤，原因："+ex.toString());
		}
		return checkResult.toString();
	}
	
	@RequestMapping(value="/RlEmpPrivilege",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String RelieveExceCost(HttpSession session,@RequestBody EmpPrivilege[] empPrivilege) {
		JsonObject DisableResult=new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			empPrivilegeService = (EmpPrivilegeService) context.getBean("empPrivilegeService");
			if(empPrivilegeService.RlEmpPrivilege(empPrivilege, updateUser)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "車間員工權限已失效");
			}else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "解除車間員工權限發生錯誤");
			}
			
	}
	catch(Exception ex){
		logger.error("Disable the ExceptionCost info is failed, due to:",ex);
		DisableResult.addProperty("StatusCode", "500");
		DisableResult.addProperty("Message", "解除車間員工權限發生錯誤，原因:"+ex.toString());
	}		
	return DisableResult.toString();
	}


}
