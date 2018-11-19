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

import com.foxlink.realtime.model.AssistantInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.AssistantService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/Assistant")
public class AssistantController {
	private static Logger logger=Logger.getLogger(AssistantController.class);
	private AssistantService assistantService;
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}

	@RequestMapping(value="/ShowAllAssistant",method=RequestMethod.GET)
	public String ShowAllAssistantPage(){
		return "AssistantInfo";
	}
	
	@RequestMapping(value = "/AllAssistants.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String ShowAllAssistants(@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String jsonResults="";
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);;
			    if(queryParam=="" || queryParam==null)
			    	queryCritirea="";
			    	
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				assistantService = (AssistantService) context.getBean("assistantService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				Page page=assistantService.getAssistantPage(currentPage,queryCritirea, queryParam);
				page.setList(assistantService.FindAllRecords(currentPage, queryCritirea, queryParam));
				jsonResults=gson.toJson(page);
				
			//	jsonResults=gson.toJson(accountService.FindAllRecords(currentPage, queryCritirea, queryParam));
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得助理列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value="/AddAssistant.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String SaveAssistantInfos(HttpSession session,@RequestBody AssistantInfo assistantInfo){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
			assistantInfo.setUPDATE_USER(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			assistantService = (AssistantService) context.getBean("assistantService");
			if(assistantService.AddNewRecord(assistantInfo)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "建立助理基本資料成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "建立助理基本資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new Assistant info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "建立助理基本資料發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
	@RequestMapping(value="/checkAssistantId.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkAssistantIdDuplicate(HttpSession session,@RequestParam("assistantId")String assistantId){
		JsonObject AddResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			assistantService = (AssistantService) context.getBean("assistantService");
			if(assistantService.checkAssistantIdDuplicate(assistantId)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "助理ID不存在，可以新增此助理信息!");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "助理ID已存在，請更改助理信息！");
			}
		}
		catch(Exception ex){
			logger.error("Check new Assistant info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "檢查助理ID是否存在發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
		
	@RequestMapping(value="/checkAssistantEmail.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkAssistantEmail(HttpSession session,@RequestParam("assistantEmail")String assistantEmail){
		JsonObject AddResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			assistantService = (AssistantService) context.getBean("assistantService");
			if(assistantService.checkAssistantEmailDuplicate(assistantEmail)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "助理郵箱不存在，可以新增此助理信息!");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "助理郵箱已存在，請更改助理郵箱！");
			}
		}
		catch(Exception ex){
			logger.error("Check new Assistant info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "檢查助理郵箱是否存在發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
	@RequestMapping(value="/UpdateAssistant.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public  String UpdateAssistantInfos(HttpSession session,@RequestBody AssistantInfo assistantInfo){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			assistantService = (AssistantService) context.getBean("assistantService");
			String updateUser=(String) session.getAttribute("username");
			assistantInfo.setUPDATE_USER(updateUser);
			if(assistantService.UpdateRecord(assistantInfo)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新助理資料成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新助理資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Assistant info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新助理資料發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/DisableAssistant.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableAssistantInfos(HttpSession session,@RequestParam("assistantId")String assistantId){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			assistantService = (AssistantService) context.getBean("assistantService");
			String updateUser=(String) session.getAttribute("username");
			if(assistantService.DeleteRecord(assistantId, updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "助理已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "失效助理發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the AssistantId info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "失效助理發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
}
