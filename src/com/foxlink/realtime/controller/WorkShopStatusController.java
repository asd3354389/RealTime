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

import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShopStatus;
import com.foxlink.realtime.service.ExceptionCostService;
import com.foxlink.realtime.service.WorkShopService;
import com.foxlink.realtime.service.WorkShopStatusService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



@Controller
@RequestMapping("/WorkShopStatus")
public class WorkShopStatusController {
	private static Logger logger = Logger.getLogger(WorkShopController.class);
	private WorkShopStatusService workShopStatusService;
	
	@RequestMapping(value="/ShowWorkShopStatus",method=RequestMethod.GET)
	public String ShowWorkShopStatusPage(){
		return "WorkShopStatus";
	}
	
	@RequestMapping(value = "/WorkShopStatusList.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String WorkShopStatusList(@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
				workShopStatusService = (WorkShopStatusService) context.getBean("workShopStatusService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				
				Page page=workShopStatusService.getWorkShopStatusPage(currentPage,queryCritirea, queryParam);
				page.setList(workShopStatusService.FindAllRecords(currentPage,queryCritirea, queryParam));
				jsonResults=gson.toJson(page);
				System.out.println(queryCritirea+"-----"+queryParam);
				//System.out.println(jsonResults);
				//jsonResults=gson.toJson(workShopService.FindAllRecords());
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得車間綫號狀態列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		
		return jsonResults;
	}
	
	@RequestMapping(value="/LineNo.show",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowLineNo(@RequestParam("WorkShopNo")String workShopNo) {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopStatusService=(WorkShopStatusService) context.getBean("workShopStatusService");
			jsonResults=gson.toJson(workShopStatusService.FindLineNo(workShopNo));
			System.out.print(jsonResults);
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得LineNo失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}
	
	@RequestMapping(value="/AddWorkShopStatus.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddExceptionCost(HttpSession session,@RequestBody WorkShopStatus[] workShopStatus){
//		JsonObject AddResult=new JsonObject();		

			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopStatusService = (WorkShopStatusService) context.getBean("workShopStatusService");
			
			return workShopStatusService.addWorkShopStatus(workShopStatus, updateUser);
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
	
	@RequestMapping(value="/RlWorkShopStatus",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String RelieveExceCost(HttpSession session,@RequestBody WorkShopStatus[] workShopStatus) {
		JsonObject DisableResult=new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			workShopStatusService = (WorkShopStatusService) context.getBean("workShopStatusService");
			if(workShopStatusService.RlWorkShopStatus(workShopStatus, updateUser)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "車間綫體狀態已失效");
			}else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "解除車間綫體狀態發生錯誤");
			}
			
	}
	catch(Exception ex){
		logger.error("Disable the ExceptionCost info is failed, due to:",ex);
		DisableResult.addProperty("StatusCode", "500");
		DisableResult.addProperty("Message", "解除車間綫體狀態已發生錯誤，原因:"+ex.toString());
	}		
	return DisableResult.toString();
	}
}
