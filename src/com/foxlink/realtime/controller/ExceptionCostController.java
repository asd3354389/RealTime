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
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.ExceptionCostService;
import com.foxlink.realtime.service.FLinePersonMtService;
import com.foxlink.realtime.service.OTCardbdPersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/ExceptionCost")
public class ExceptionCostController {
	private static Logger logger = Logger.getLogger(ExceptionCostController.class);
	private ExceptionCostService exceptionCostService;
	
	@RequestMapping(value = "/ShowExceptionCost", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "ExceptionCost";
	}
	
	@RequestMapping(value="/AddExceptionCost.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddExceptionCost(HttpSession session,@RequestBody ExceptionCost[] exceptionCost){
//		JsonObject AddResult=new JsonObject();		

			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			exceptionCostService = (ExceptionCostService) context.getBean("exceptionCostService");

			
			return exceptionCostService.addExceptionCost(exceptionCost, updateUser);
	}
	
	@RequestMapping(value="/ShowExceptionList",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ExceptionList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
/*			String updateUser = (String)session.getAttribute("username");
			String userDataCostId="ALL";
			System.out.println(userDataCostId);*/
			exceptionCostService = (ExceptionCostService)context.getBean("exceptionCostService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = exceptionCostService.getFindExcePage(currentPage,queryCritirea, queryParam);
			page.setList(exceptionCostService.FindQueryExce(currentPage, queryCritirea,queryParam));
			/*System.out.println(gson.toJson(page));*/
			System.out.println(gson.toJson(page));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得車間例外費用代碼資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
		
	}
	
	@RequestMapping(value="/UpdateExceCost",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String UpdateExceCost(HttpSession session,@RequestBody ExceptionCost exceptionCost) {
		JsonObject UpdateResult=new JsonObject();		
	try	{
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		String updateUser = (String)session.getAttribute("username");
		exceptionCostService = (ExceptionCostService) context.getBean("exceptionCostService");
		if(exceptionCostService.UpdateExceCost(exceptionCost,updateUser)){
			UpdateResult.addProperty("StatusCode", "200");
			UpdateResult.addProperty("Message", "更改車間例外費用代碼成功");
		}
		else{
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更改車間例外費用代碼失敗");
		}
	}
	catch(Exception ex){
		logger.error("Updating the ExceptionCost info is failed, due to: ",ex);
		UpdateResult.addProperty("StatusCode", "500");
		UpdateResult.addProperty("Message", "更改車間例外費用代碼發生錯誤，原因："+ex.toString());
	}
	return UpdateResult.toString();
	}
	
	@RequestMapping(value="/RelieveExceCost",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String RelieveExceCost(HttpSession session,@RequestBody ExceptionCost[] exceptionCost) {
		JsonObject DisableResult=new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			exceptionCostService = (ExceptionCostService) context.getBean("exceptionCostService");
			if(exceptionCostService.RelieveExceCost(exceptionCost, updateUser)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "例外費用代碼已失效");
			}else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "解除例外費用代碼發生錯誤");
			}
			
	}
	catch(Exception ex){
		logger.error("Disable the ExceptionCost info is failed, due to:",ex);
		DisableResult.addProperty("StatusCode", "500");
		DisableResult.addProperty("Message", "解除例外費用代碼已發生錯誤，原因:"+ex.toString());
	}		
	return DisableResult.toString();
	}
	
	@RequestMapping(value="/checkExceCost.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkExceCost(HttpSession session,@RequestParam("CostId")String CostId){
		JsonObject checkResult=new JsonObject();	
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			exceptionCostService = (ExceptionCostService) context.getBean("exceptionCostService");
			if(exceptionCostService.checkExceCost(CostId)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "此費用代碼存在！");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "沒有此費用代碼，請重新輸入!");
			}
		}
		catch(Exception ex){
			logger.error("Check new Account info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查費用代碼是否存在發生錯誤，原因："+ex.toString());
		}
		System.out.println(checkResult.toString());
		return checkResult.toString();
	}
	
	@RequestMapping(value="/checkWorkShopCost.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkWorkShopCost(HttpSession session,@RequestParam("CostId")String CostId,@RequestParam("WorkShopNo")String WorkShopNo){
		JsonObject checkResult=new JsonObject();	
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			exceptionCostService = (ExceptionCostService) context.getBean("exceptionCostService");
			if(exceptionCostService.checkWorkShopCost(CostId,WorkShopNo)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "此費用代碼綁定例外車間已存在！");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "此費用代碼未綁定例外車間!");
			}
		}
		catch(Exception ex){
			logger.error("Check new Account info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "此費用代碼綁定例外車間發生錯誤，原因："+ex.toString());
		}
		System.out.println(checkResult.toString());
		return checkResult.toString();
	}
}
