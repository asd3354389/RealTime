package com.foxlink.realtime.controller;

import java.util.List;

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

import com.foxlink.realtime.model.LineMapping;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.LineMappingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/LineMapping")
public class LineMappingController {
	private static Logger logger=Logger.getLogger(LineMappingController.class);
	private LineMappingService lineMappingService;

	@RequestMapping(value="/ShowAllLineMapping",method=RequestMethod.GET)
	public String ShowAllAccountPage(){
		return "ShowLineMapping";
	}
	
	@RequestMapping(value="/AllLineMapping.show",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableJobTitleInfos(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
			lineMappingService = (LineMappingService) context.getBean("lineMappingService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = lineMappingService.getLineMappingPage(currentPage,queryCritirea, queryParam);
			page.setList(lineMappingService.FindQueryRecord(currentPage, queryCritirea ,queryParam));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error("Get the LineMapping info is failed, due to: ",ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得線別對應關係列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/GetAllRTLine.show",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public List<String> GetRTLine(HttpSession session){
		String DisableResult=null;
		List<String> list = null;
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			lineMappingService = (LineMappingService) context.getBean("lineMappingService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			list = lineMappingService.getRTLine();
			DisableResult = gson.toJson(list);
		}catch(Exception ex){
			logger.error("Get the RTLine info is failed, due to: ",ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時系統線別列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return list;
	}
	
	@RequestMapping(value="/UpdateLineMapping.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String UpdateLineMapping(HttpSession session,@RequestBody LineMapping lineMapping){
		JsonObject UpdateResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			lineMappingService = (LineMappingService) context.getBean("lineMappingService");
			String updateUser=(String) session.getAttribute("username");
			if(lineMappingService.UpdateRecord(lineMapping,updateUser)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新線別對應關係成功");
			}else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新線別對應關係失敗");
			}
		}catch(Exception e){
			logger.error("Updating the LineMapping info is failed, due to: ",e);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新線別對應關係發生錯誤，原因："+e.toString());
		}
		
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/AddLineMapping.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody 
	public String SaveLineMappingInfos(HttpSession session,@RequestBody LineMapping lineMapping){
		JsonObject AddResult=new JsonObject();	
		System.out.println("------------------");
		System.out.println(lineMapping+"1");
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			lineMappingService = (LineMappingService) context.getBean("lineMappingService");
			String insertUser=(String) session.getAttribute("username");
			if(!lineMappingService.checkRTLine(lineMapping.getRT_LINENO())){
				if(lineMappingService.AddNewRecord(lineMapping,insertUser)){
					AddResult.addProperty("StatusCode", "200");
					AddResult.addProperty("Message", "建立線別對應關係成功");
				}else{
					AddResult.addProperty("StatusCode", "500");
					AddResult.addProperty("Message", "建立線別對應關係失敗");
				}
			}else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "線別對應關係已存在，請不要重複添加");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new Account info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "建立線別對應關係發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
}
