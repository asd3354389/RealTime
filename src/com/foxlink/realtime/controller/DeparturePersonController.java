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

import com.foxlink.realtime.model.DeparturePerson;
import com.foxlink.realtime.model.EmpIpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.DeparturePersonService;
import com.foxlink.realtime.service.EmpIPBindingService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/DeparturePerson")
public class DeparturePersonController {
	private static Logger logger=Logger.getLogger(DeparturePersonController.class);
	private DeparturePersonService departurePersonService;
	
	@RequestMapping(value="/ShowDeparturePerson",method=RequestMethod.GET)
	public String ShowWorkShopNoRestInfo(){
		return "DeparturePerson";
	}
	
	
	//顯示列表數據
	@RequestMapping(value="/ShowAllDeparturePerson",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
			departurePersonService = (DeparturePersonService) context.getBean("departurePersonService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = departurePersonService.getDeparturePersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(departurePersonService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得例外員工列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/AdddeparturePerson.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddDeparturePerson(HttpSession session,@RequestBody DeparturePerson empIpBinding){
		JsonObject AddResult=new JsonObject();	
		
		try{
			String updateUser=(String) session.getAttribute("username");
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			departurePersonService = (DeparturePersonService) context.getBean("departurePersonService");
			//AddResult = departurePersonService.setdeparturePerson(empIpBinding,updateUser);
			System.out.println("===========================員工信息控制器"+empIpBinding.getEmp_id());
			AddResult = departurePersonService.setdeparturePerson(empIpBinding, updateUser);
			
		}
		catch(Exception ex){
			
			logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "新增例外員工發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	//刪除例外員工
	@RequestMapping(value="/deleteDeparturePerson.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteWorkShopNoRest(HttpSession session,@RequestBody DeparturePerson[] empIpBindings){
		JsonObject DisableResult=new JsonObject();
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			departurePersonService = (DeparturePersonService) context.getBean("departurePersonService");
			String updateUser=(String) session.getAttribute("username");		
			/*for(int i = 0;i<empIpBindings.length;i++) {
				System.out.println(empIpBindings[i].getDeviceIP());
			}*/
			
			System.out.println("=============刪除的員工信息==="+empIpBindings);
			if(departurePersonService.DeleteDeparturePerson(empIpBindings, updateUser)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "例外員工綁定已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "刪除例外員工發生錯誤發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the WorkShopNoRest info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "刪除例外員工發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	
	//
	@RequestMapping(value="/ShowDeparturePersonList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
			departurePersonService = (DeparturePersonService)context.getBean("departurePersonService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = departurePersonService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(departurePersonService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			System.out.println(gson.toJson(page));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得例外員工列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
	}
}
