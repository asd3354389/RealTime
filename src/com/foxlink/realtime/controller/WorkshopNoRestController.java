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

import com.foxlink.realtime.model.IOCardMachineIP;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.service.IOCardbdIPService;
import com.foxlink.realtime.service.JobTitleService;
import com.foxlink.realtime.service.WorkshopNoRestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/WorkShopNoRest")
public class WorkshopNoRestController {
	private static Logger logger=Logger.getLogger(WorkshopNoRestController.class);
	private WorkshopNoRestService workshopNoRestService;
	
	@RequestMapping(value="/ShowWorkshopNoRestInfo",method=RequestMethod.GET)
	public String ShowWorkShopNoRestInfo(){
		return "WorkshopNoRestInfo";
	}
	
	@RequestMapping(value="/ShowAllWorkShopNoRestInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowAllWorkShopNoRestInfo(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);;
			    if(queryParam=="" || queryParam==null)
			    	queryCritirea="";
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser=(String) session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			String accessRole=(String) session.getAttribute("accessRole");
			workshopNoRestService = (WorkshopNoRestService) context.getBean("workshopNoRestService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = workshopNoRestService.getworkshopNoRestPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
			page.setList(workshopNoRestService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得車間休息時間列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/AddWorkShopNoRestInfo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddWorkShopNoRestInfo(HttpSession session,@RequestBody WorkshopNoRestInfo workshopNoRestInfo){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workshopNoRestService = (WorkshopNoRestService) context.getBean("workshopNoRestService");
			String accessRole=(String) session.getAttribute("accessRole");
			AddResult = workshopNoRestService.setWorkShopNoRestInfo(workshopNoRestInfo,updateUser,accessRole);
		}
		catch(Exception ex){
			logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增車間休息時間段發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	
	@RequestMapping(value="/UpdateWorkShopNoRest.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateIOCardMaIP(HttpSession session,@RequestBody WorkshopNoRestInfo workshopNoRestInfo){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workshopNoRestService = (WorkshopNoRestService) context.getBean("workshopNoRestService");
			String updateUser=(String) session.getAttribute("username");
			String accessRole=(String) session.getAttribute("accessRole");
			if(workshopNoRestService.UpdateRecord(workshopNoRestInfo,updateUser,accessRole)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新車間休息時間段成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新車間休息時間段失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新車間休息時間段發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/ShowWorkShopNoRestList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String WorkShopNoRestList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
		System.out.println("123"+queryParam);
		String JsonResult = null;
		try {
			int currentPage = 1;
			if (curPage==""||curPage==null) {
				currentPage=1;
			} else {
				currentPage = Integer.parseInt(curPage);
				if (queryParam==""||queryParam==null) {
					queryCritirea="";
				}
			}
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			workshopNoRestService = (WorkshopNoRestService)context.getBean("workshopNoRestService");
			String accessRole=(String) session.getAttribute("accessRole");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = workshopNoRestService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
			page.setList(workshopNoRestService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			System.out.println(gson.toJson(page));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得卡機ip進出狀態資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
	}
	
	@RequestMapping(value="/deleteWorkShopNoRest.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteWorkShopNoRest(HttpSession session,@RequestParam("workshopNo")String workshopNo){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workshopNoRestService = (WorkshopNoRestService) context.getBean("workshopNoRestService");
			String updateUser=(String) session.getAttribute("username");
			String accessRole=(String) session.getAttribute("accessRole");
			if(workshopNoRestService.DeleteWorkshopNoRest(workshopNo, updateUser,accessRole)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "車間休息信息已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "刪除車間休息信息發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShopNoRest info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "刪除車間休息信息發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
}
