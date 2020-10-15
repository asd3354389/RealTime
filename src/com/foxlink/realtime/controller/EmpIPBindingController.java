package com.foxlink.realtime.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.EmpIpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;
import com.foxlink.realtime.service.EmpIPBindingService;
import com.foxlink.realtime.service.WorkshopNoRestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/EmpIPBinding")
public class EmpIPBindingController {
	private static Logger logger=Logger.getLogger(EmpIPBindingController.class);
	private EmpIPBindingService empIPBindingService;
	
	@RequestMapping(value="/ShowEmpIPBinding",method=RequestMethod.GET)
	public String ShowWorkShopNoRestInfo(){
		return "EmpIPBinding";
	}
	
	@RequestMapping(value="/ShowAllEmpIPBinding",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowAllEmpIPBinding(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);
			    if(queryParam=="" || queryParam==null)
			    	queryCritirea="";
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser=(String) session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			empIPBindingService = (EmpIPBindingService) context.getBean("empIPBindingService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = empIPBindingService.getEmpIPBindingPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(empIPBindingService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得員工綁定卡機ip列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/AddEmpIPBinding.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddEmpIPBinding(HttpSession session,@RequestBody EmpIpBinding empIpBinding){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			empIPBindingService = (EmpIPBindingService) context.getBean("empIPBindingService");
			AddResult = empIPBindingService.setEmpIPBinding(empIpBinding,updateUser);
		}
		catch(Exception ex){
			logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增員工綁定卡機ip發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	
	@RequestMapping(value="/UpdateEmpIPBinding.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateEmpIPBinding(HttpSession session,@RequestBody EmpIpBinding empIpBinding){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			empIPBindingService = (EmpIPBindingService) context.getBean("empIPBindingService");
			String updateUser=(String) session.getAttribute("username");
			if(empIPBindingService.UpdateRecord(empIpBinding,updateUser)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新員工綁定卡機ip成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新員工綁定卡機ip失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新員工綁定卡機ip發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/deleteEmpIPBinding.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteWorkShopNoRest(HttpSession session,@RequestBody EmpIpBinding[] empIpBindings){
		JsonObject DisableResult=new JsonObject();
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			empIPBindingService = (EmpIPBindingService) context.getBean("empIPBindingService");
			String updateUser=(String) session.getAttribute("username");		
			for(int i = 0;i<empIpBindings.length;i++) {
				System.out.println(empIpBindings[i].getDeviceIP());
			}
			if(empIPBindingService.DeleteEmpIPBinding(empIpBindings, updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "員工綁定卡機ip已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "刪除員工綁定卡機ip發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShopNoRest info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "刪除員工綁定卡機ip發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	@RequestMapping(value="/ShowEmpIPBindingList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String EmpIPBindingList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
			empIPBindingService = (EmpIPBindingService)context.getBean("empIPBindingService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = empIPBindingService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(empIPBindingService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
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
	
	@RequestMapping(value="/DeleteIpList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteIpList(HttpSession session,@RequestParam("deleteParam") String deleteParam){
		JsonObject DisableResult=new JsonObject();
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			empIPBindingService = (EmpIPBindingService) context.getBean("empIPBindingService");
			String updateUser=(String) session.getAttribute("username");		
			if(empIPBindingService.DeleteIpList(deleteParam, updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "員工綁定卡機ip已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "刪除員工綁定卡機ip發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShopNoRest info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "刪除員工綁定卡機ip發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
}
