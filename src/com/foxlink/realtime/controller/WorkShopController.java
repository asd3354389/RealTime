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
import org.springframework.web.servlet.ModelAndView;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkShop;
import com.foxlink.realtime.service.AssistantService;
import com.foxlink.realtime.service.WorkShopService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/WorkShop")
public class WorkShopController {
	private static Logger logger=Logger.getLogger(WorkShopController.class);
	private WorkShopService workShopService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}
	
	@RequestMapping(value="/ShowAllWorkShop",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "WorkShopInfo";
	}
	
	@RequestMapping(value = "/AllWorkShops.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String AllWorkShops(@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
				workShopService = (WorkShopService) context.getBean("workShopService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				
				Page page=workShopService.getWorkShopPage(currentPage,queryCritirea, queryParam);
				page.setList(workShopService.FindAllRecords(currentPage,queryCritirea, queryParam));
				jsonResults=gson.toJson(page);
				System.out.println(queryCritirea+"-----"+queryParam);
				System.out.println(page);
				//jsonResults=gson.toJson(workShopService.FindAllRecords());
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得車間列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		
		return jsonResults;
	}
	
	@RequestMapping(value="/AddWorkShop.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String SaveWorkShopInfos(HttpSession session,@RequestBody WorkShop workShop){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
			workShop.setUPDATE_USER(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopService = (WorkShopService) context.getBean("workShopService");
			if(workShopService.AddNewRecord(workShop)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "建立車間基本資料成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "建立車間基本資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new WorkShop info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "建立車間基本資料發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
	@RequestMapping(value="/checkWorkShopNo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkWorkShop(HttpSession session,@RequestParam("workShopNo")String workShopNo){
		JsonObject checkResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopService = (WorkShopService) context.getBean("workShopService");
			if(workShopService.checkWorkShopNoDuplicate(workShopNo)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "車間信息不存在，可以新增此車間信息!");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "車間已存在，請更改車間！");
			}
		}
		catch(Exception ex){
			logger.error("Check new WorkShop info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查車間是否存在發生錯誤，原因："+ex.toString());
		}
		return checkResult.toString();
	}
	
	@RequestMapping(value="/UpdateWorkShop.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public  String UpdateWorkShopInfos(HttpSession session,@RequestBody WorkShop workShop){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopService = (WorkShopService) context.getBean("workShopService");
			String updateUser=(String) session.getAttribute("username");
			workShop.setUPDATE_USER(updateUser);
			if(workShopService.UpdateRecord(workShop)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新車間資料成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新車間資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating WorkShop info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "200");
			UpdateResult.addProperty("Message", "更新車間資料發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/DisableWorkShop.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableWorkShopInfos(HttpSession session,@RequestBody WorkShop workShop){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopService = (WorkShopService) context.getBean("workShopService");
			String updateUser=(String) session.getAttribute("username");
			workShop.setUPDATE_USER(updateUser);
			if(workShopService.DisableWorkShopInfos(workShop)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "車間已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "失效車間發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShop info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "失效車間發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	@RequestMapping(value = "/GetWorkShops.do", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String getWorkShops(){
		String jsonResults="";
		try{						
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				workShopService = (WorkShopService) context.getBean("workShopService");
				Gson gson = new GsonBuilder().serializeNulls().create();	
				jsonResults=gson.toJson(workShopService.FindWorkShopNo());
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得實時工時車間信息失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value = "/GetLineNoByWorkShop.do", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String getLineNoByWorkShop(@RequestParam("workShopNo")String workShopNo){
		String jsonResults="";
		try{						
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				workShopService = (WorkShopService) context.getBean("workShopService");
				Gson gson = new GsonBuilder().serializeNulls().create();	
				jsonResults=gson.toJson(workShopService.GetLineNoByWorkShop(workShopNo));
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "根據車間號取得實時工時線體信息失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value="/checkLineNo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkLineNo(HttpSession session,@RequestBody WorkShop workShop){
		JsonObject checkResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopService = (WorkShopService) context.getBean("workShopService");
			if(workShopService.checkLineNoDuplicate(workShop)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", workShop.getWORKSHOPNO()+"車間不存在新增線體信息，可以新增此線體!");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message",workShop.getWORKSHOPNO() + "車間已存在該線體，請更改線體名稱！");
			}
		}
		catch(Exception ex){
			logger.error("Check new WorkShop info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查線體是否存在發生錯誤，原因："+ex.toString());
		}
		return checkResult.toString();
	}
	
	@RequestMapping(value="/AddLineNo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddLineNoInfos(HttpSession session,@RequestBody WorkShop workShop){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
			workShop.setUPDATE_USER(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			workShopService = (WorkShopService) context.getBean("workShopService");
			if(workShopService.AddLineNo(workShop)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "建立線體基本資料成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "建立線體基本資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new WorkShop info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "建立車間基本資料發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
}
