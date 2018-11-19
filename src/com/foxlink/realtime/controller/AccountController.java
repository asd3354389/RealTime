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
import com.foxlink.realtime.model.User;
import com.foxlink.realtime.service.AccountService;
import com.foxlink.realtime.service.AssistantService;
import com.foxlink.realtime.service.LmtDeptService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/Account")
public class AccountController {
	
	private static Logger logger=Logger.getLogger(AccountController.class);
	private AccountService accountService;
	private AssistantService assistantService;
	private LmtDeptService lmtDeptService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}

	@RequestMapping(value="/ShowAllAccount",method=RequestMethod.GET)
	public String ShowAllAccountPage(){
		return "AccountManage";
	}
	
	@RequestMapping(value = "/AllAccount.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String ShowAllUsers(@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
				accountService = (AccountService) context.getBean("accountService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				Page page=accountService.getAccountPage(currentPage,queryCritirea, queryParam);
				page.setList(accountService.FindAllRecords(currentPage, queryCritirea, queryParam));
				jsonResults=gson.toJson(page);
				
			//	jsonResults=gson.toJson(accountService.FindAllRecords(currentPage, queryCritirea, queryParam));
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得實時工時助理列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value = "/AllAssistant.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String getAssistantIds(){
		String jsonResults="";
		try{						
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				assistantService = (AssistantService) context.getBean("assistantService");
				Gson gson = new GsonBuilder().serializeNulls().create();	
				jsonResults=gson.toJson(assistantService.FindAllRecords());
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得實時工時賬號列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value = "/AllCostId.show", method = RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String getAllCostId(){
		String jsonResults="";
		try{						
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				lmtDeptService = (LmtDeptService) context.getBean("lmtDeptService");
				Gson gson = new GsonBuilder().serializeNulls().create();	
				jsonResults=gson.toJson(lmtDeptService.FindCostIds());
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得實時工時賬號列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value="/AddAccount.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String SaveAccountInfos(HttpSession session,@RequestBody User user){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
			user.setUPDATE_USER(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			accountService = (AccountService) context.getBean("accountService");
			if(accountService.AddNewRecord(user)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "建立賬號基本資料成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "建立賬號基本資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new Account info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "建立賬號基本資料發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
	@RequestMapping(value="/checkUserName.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkUserNameDuplicate(HttpSession session,@RequestParam("userName")String userName){
		JsonObject checkResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			accountService = (AccountService) context.getBean("accountService");
			if(accountService.checkUserNameDuplicate(userName)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "賬號不存在，可以新增此賬號!");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "賬號已存在，請更改賬號！");
			}
		}
		catch(Exception ex){
			logger.error("Check new Account info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查賬號是否存在發生錯誤，原因："+ex.toString());
		}
		return checkResult.toString();
	}
	
	@RequestMapping(value="/UpdateAccount.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public  String UpdateAccountInfos(HttpSession session,@RequestBody User user){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			accountService = (AccountService) context.getBean("accountService");
			String updateUser=(String) session.getAttribute("username");
			user.setUPDATE_USER(updateUser);
			if(accountService.UpdateRecord(user)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新賬號資料成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新賬號資料失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新賬號資料發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/DisableAccount.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableAccountInfos(HttpSession session,@RequestParam("userName")String userName){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			accountService = (AccountService) context.getBean("accountService");
			String updateUser=(String) session.getAttribute("username");
			if(accountService.DeleteRecord(userName, updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "賬號已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "失效賬號發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the Account info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "失效賬號發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
}
