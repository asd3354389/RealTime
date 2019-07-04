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

import com.foxlink.realtime.model.ClassNoRestInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.service.ClassNoRestService;
import com.foxlink.realtime.service.ExceptionCostService;
import com.foxlink.realtime.service.OTCardbdPersonService;
import com.foxlink.realtime.service.WorkShopService;
import com.foxlink.realtime.service.WorkshopNoRestService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/ClassNoRest")
public class ClassNoRestController {
	private static Logger logger = Logger.getLogger(ClassNoRestController.class);
	private ClassNoRestService classNoRestService;
	
	@RequestMapping(value="/ShowClassNoRestInfo",method=RequestMethod.GET)
	public String ShowClassNoRestInfo(){
		return "ClassNoRestInfo";
	}
	
	@RequestMapping(value="/ShowAllClassNoRestInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
			classNoRestService = (ClassNoRestService) context.getBean("classNoRestService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = classNoRestService.getclassNoRestPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(classNoRestService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得車間休息時間列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/AddClassNoRestInfo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddClassNoRestInfo(HttpSession session,@RequestBody ClassNoRestInfo[] classNoRestInfo){
		String updateUser=(String) session.getAttribute("username");
//		otCardbd.setUpdate_UserId(updateUser);
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		classNoRestService = (ClassNoRestService) context.getBean("classNoRestService");	
		return classNoRestService.setClassNoRestInfo(classNoRestInfo, updateUser);
	}
	
	@RequestMapping(value="/UpdateClassNoRest.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateClassNoRest(HttpSession session,@RequestBody ClassNoRestInfo classNoRestInfo){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			classNoRestService = (ClassNoRestService) context.getBean("classNoRestService");
			String updateUser=(String) session.getAttribute("username");
			if(classNoRestService.UpdateRecord(classNoRestInfo,updateUser)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新班別加班休息時間段成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新班別加班休息時間段失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新班別加班休息時間段發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/deleteClassNoRest.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteClassNoRest(HttpSession session,@RequestParam("CostId")String CostId,@RequestParam("Class_No")String Class_No){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			classNoRestService = (ClassNoRestService) context.getBean("classNoRestService");
			String updateUser=(String) session.getAttribute("username");
			if(classNoRestService.DeleteClassNoRest(CostId,Class_No,updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "班別加班休息時間段信息已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "刪除班別加班休息時間段信息發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShopNoRest info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "刪除班別加班休息時間段信息發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	//顯示所有班別
	@RequestMapping(value="/ClassNo.show",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowClassNo() {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			classNoRestService=(ClassNoRestService) context.getBean("classNoRestService");
			jsonResults=gson.toJson(classNoRestService.FindClassNo());
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得ClassNo失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}
	
	//顯示班別信息内容
	@RequestMapping(value="/ShowClassNoCotent.show",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowClassNoCotent(@RequestParam("Class_No")String Class_No) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		classNoRestService = (ClassNoRestService) context.getBean("classNoRestService");
        return classNoRestService.FindClassNoCotent(Class_No);
	}
}
