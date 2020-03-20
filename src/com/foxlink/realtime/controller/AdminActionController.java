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

import com.foxlink.realtime.model.AppLogin;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.IpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.AdminActionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/AdminActioin")
public class AdminActionController {
	private static Logger logger=Logger.getLogger(AdminActionController.class);
	private AdminActionService adminActionService;
	
	/**
	 * 員工信息查詢
	 * @return
	 */
	@RequestMapping(value="/ShowEmpInfo",method=RequestMethod.GET)
	public String ShowAllEmpInfo(){
		return "EmpInfo";
	}
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableEmpInfo(HttpSession session,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			if(queryParam==null||"".equals(queryParam)){
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得員工信息列表失敗，原因："+"員工工號為空，請輸入需要查詢的員工工號");
				DisableResult=exception.toString();
			}else{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser=(String) session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<Emp> empList = adminActionService.FindQueryRecord(queryCritirea,queryParam);
			DisableResult = gson.toJson(empList);
			}
			    
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得員工信息列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	/**
	 * 節假日信息查詢
	 * @return
	 */
	@RequestMapping(value="/ShowHolidayInfo",method=RequestMethod.GET)
	public String ShowHolidayInfo(){
		return "HolidayInfo";
	}
	
	@RequestMapping(value="/HolidayYList",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableHolidayYListInfos(){
		String DisableResult=null;
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<String> empList = adminActionService.FindHolidayYList();
			DisableResult = gson.toJson(empList);
			    
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得法定節假日或補休信息列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/Holiday",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableHolidayInfos(@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			if(queryParam==null||"".equals(queryParam)){
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得法定節假日或補休信息列表失敗，原因："+"請選擇查詢的年份");
				DisableResult=exception.toString();
			}else{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<String> holidayLList = adminActionService.FindHoliday(queryParam,"L");
			List<String> holidaySList = adminActionService.FindHoliday(queryParam,"S");
			JsonObject exception=new JsonObject();
			exception.addProperty("L", gson.toJson(holidayLList));
			exception.addProperty("S", gson.toJson(holidaySList));
			DisableResult=exception.toString();
			}
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得法定節假日或補休信息列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/DeleteHoliday",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteHoliday(@RequestParam("delete_date")String delete_date){
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		try{
			if(delete_date==null||"".equals(delete_date)){
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "刪除節假日或補休失敗，原因：無選擇日期");
				DisableResult=exception.toString();
			}else{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(adminActionService.DeleteHoliday(delete_date)){
				exception.addProperty("StatusCode", "200");
				exception.addProperty("ErrorMessage", "刪除節假日或補休成功");
				DisableResult=exception.toString();
			}else{
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "刪除節假日或補休失敗");
				DisableResult=exception.toString();
			}
			}
		}catch(Exception ex){
			logger.error(ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "刪除節假日或補休失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/AddHoliday",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String AddHoliday(@RequestParam("holidayType")String holidayType,@RequestParam("holidayDate")String holidayDate){
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		try{
			if(holidayType==null||"".equals(holidayType)||holidayDate==null||"".equals(holidayDate)){
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "新增節假日或補休失敗，原因：無選擇日期");
				DisableResult=exception.toString();
			}else{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(adminActionService.checkHoliday(holidayDate)){
				if(adminActionService.AddHoliday(holidayType,holidayDate)){
					exception.addProperty("StatusCode", "200");
					if("S".endsWith(holidayType)){
						exception.addProperty("ErrorMessage", "新增補休成功");
					}else{
						exception.addProperty("ErrorMessage", "新增節假日成功");
					}
					DisableResult=exception.toString();
				}else{
					exception.addProperty("StatusCode", "500");
					exception.addProperty("ErrorMessage", "新增節假日或補休失敗");
					DisableResult=exception.toString();
				}
			}else{
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "節假日或補休已存在");
				DisableResult=exception.toString();
			}
			}
		}catch(Exception ex){
			logger.error(ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "新增節假日或補休失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	
	
	/**
	 * 卡機綁定可刷卡費用代碼查詢
	 * @return
	 */
	@RequestMapping(value="/ShowIpBindingCostSCInfo",method=RequestMethod.GET)
	public String ShowIpBindingCostSCInfo(){
		return "IpBindingCostSC";
	}
	
	
	/**
	 * 卡機綁定可刷卡費用代碼
	 * @param session
	 * @param curPage
	 * @param queryCritirea
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/ShowAllIpBindingCostSCInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowAllIpBindingCostSCInfo(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = adminActionService.getIpBindingCostSCPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
			page.setList(adminActionService.FindQueryIpBindingCostSCRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得卡機綁定可刷卡費用代碼列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/AddIpBindingCostSCInfo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddIpBindingCostSCInfo(HttpSession session,@RequestBody IpBinding ipBinding){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			AddResult = adminActionService.setIpBindingCostSCInfo(ipBinding,updateUser);
		}
		catch(Exception ex){
			logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增卡機綁定可刷卡費用代碼發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
	@RequestMapping(value="/deleteIpBindingCostSC.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteIpBindingCostSC(HttpSession session,@RequestParam("ip")String ip,@RequestParam("costid")String costid){
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			String updateUser=(String) session.getAttribute("username");
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(adminActionService.DeleteIpBindingCostSC(ip,costid,updateUser)){
				exception.addProperty("StatusCode", "200");
				exception.addProperty("ErrorMessage", "刪除卡機綁定可刷卡費用代碼成功");
				DisableResult=exception.toString();
			}else{
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "刪除卡機綁定可刷卡費用代碼失敗");
				DisableResult=exception.toString();
			}
		}catch(Exception ex){
			logger.error(ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "刪除卡機綁定可刷卡費用代碼失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	/**
	 * 實時卡機ip管控
	 * @return
	 */
	@RequestMapping(value="/ShowAppLoginInfo",method=RequestMethod.GET)
	public String ShowAppLoginInfo(){
		return "AppLogin";
	}
	
	@RequestMapping(value="/ShowAllAppLoginInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowAllAppLoginInfo(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = adminActionService.getAppLoginPage(currentPage,queryCritirea, queryParam);
			page.setList(adminActionService.FindQueryAppLoginRecord(currentPage, queryCritirea,queryParam));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時卡機ip管控列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/deleteAppLogin.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteAppLogin(HttpSession session,@RequestParam("ip")String ip){
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			String updateUser=(String) session.getAttribute("username");
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(adminActionService.DeleteAppLogin(ip,updateUser)){
				exception.addProperty("StatusCode", "200");
				exception.addProperty("ErrorMessage", "刪除實時卡機ip管控信息成功");
				DisableResult=exception.toString();
			}else{
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "刪除實時卡機ip管控信息失敗");
				DisableResult=exception.toString();
			}
		}catch(Exception ex){
			logger.error(ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "刪除實時卡機ip管控信息失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		return DisableResult;
	}
	
	@RequestMapping(value="/AddAppLoginInfo.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddAppLoginInfo(HttpSession session,@RequestBody AppLogin appLogin){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminActionService = (AdminActionService) context.getBean("AdminActionService");
			AddResult = adminActionService.setAppLoginInfo(appLogin,updateUser);
		}
		catch(Exception ex){
			logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增實時卡機ip管控信息發生錯誤，原因："+ex.toString());
		}
		return AddResult.toString();
	}
	
}
