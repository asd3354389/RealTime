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
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopEmpRestInfo;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.service.WorkshopEmpRestService;
import com.foxlink.realtime.service.WorkshopNoRestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
@Controller
@RequestMapping("/WorkShopEmpRest")
public class WorkShopEmpRestController {
	private static Logger logger=Logger.getLogger(WorkShopEmpRestController.class);
	private WorkshopEmpRestService workshopEmpRestService;
	
	@RequestMapping(value="/ShowWorkshopEmpRestInfo",method=RequestMethod.GET)
	public String ShowWorkShopNoRestInfo(){
		return "WorkshopEmpRestInfo";
	}
	
	@RequestMapping(value="/ShowAllWorkShopEmpRestInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
			workshopEmpRestService = (WorkshopEmpRestService) context.getBean("workshopEmpRestService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = workshopEmpRestService.getworkshopNoRestPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
			page.setList(workshopEmpRestService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得員工休息時間列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/AddWorkShopEmpRestInfo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddWorkShopNoRestInfo(HttpSession session,@RequestBody WorkshopEmpRestInfo workshopNoRestInfo){
		JsonObject AddResult=new JsonObject();		
		System.out.println("前台傳入數據====="+workshopNoRestInfo);
		try{
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workshopEmpRestService = (WorkshopEmpRestService) context.getBean("workshopEmpRestService");
			String accessRole=(String) session.getAttribute("accessRole");
			AddResult = workshopEmpRestService.setWorkShopNoRestInfo(workshopNoRestInfo,updateUser,accessRole);
		}
		catch(Exception ex){
			logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增員工休息時間段發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	
	@RequestMapping(value="/UpdateWorkShopEmpRest.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateIOCardMaIP(HttpSession session,@RequestBody WorkshopEmpRestInfo workshopNoRestInfo){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workshopEmpRestService = (WorkshopEmpRestService) context.getBean("workshopEmpRestService");
			String updateUser=(String) session.getAttribute("username");
			String accessRole=(String) session.getAttribute("accessRole");
			if(workshopEmpRestService.UpdateRecord(workshopNoRestInfo,updateUser,accessRole)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新員工休息時間段成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新員工休息時間段失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新車間休息時間段發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/ShowWorkShopEmpRestList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
			workshopEmpRestService = (WorkshopEmpRestService)context.getBean("workshopEmpRestService");
			String accessRole=(String) session.getAttribute("accessRole");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = workshopEmpRestService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
			page.setList(workshopEmpRestService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			System.out.println(gson.toJson(page));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得員工休息段列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
	}
	
	@RequestMapping(value="/deleteWorkShopEmpRest.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteWorkShopNoRest(HttpSession session,@RequestParam("empId")String empId,@RequestParam("classNo")String classNo){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workshopEmpRestService = (WorkshopEmpRestService) context.getBean("workshopEmpRestService");
			String updateUser=(String) session.getAttribute("username");
			String accessRole=(String) session.getAttribute("accessRole");
			if(workshopEmpRestService.DeleteWorkshopNoRest(empId,classNo,updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "員工休息信息已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "刪除員工休息信息發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShopNoRest info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "刪除員工休息信息發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}

}
