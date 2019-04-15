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


import com.foxlink.realtime.model.Employee;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.FLinePersonMtService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/FlinePerson")
public class FLinePersonMtController {
	private static Logger logger =Logger.getLogger(FLinePersonMtController.class);
	private FLinePersonMtService fLinePersonMtService;
	
	@RequestMapping(value = "/ShowFlinePersonMaintain", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "FLinePersonMaintain";
	}
	
	@RequestMapping(value="/ShowPersonListY",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String personListY(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
			System.out.println(userDataCostId);
			fLinePersonMtService = (FLinePersonMtService)context.getBean("fLinePersonMtService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = fLinePersonMtService.getFindPersonPageY(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(fLinePersonMtService.FindQueryRecordY(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			/*System.out.println(gson.toJson(page));*/
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時工時人員資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
		
	}
	
	@RequestMapping(value="/ShowPersonListN",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String personListN(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
			fLinePersonMtService = (FLinePersonMtService)context.getBean("fLinePersonMtService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = fLinePersonMtService.getFindPersonPageN(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(fLinePersonMtService.FindQueryRecordN(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			/*System.out.println(gson.toJson(page));*/
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時工時人員資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
		
	}
	
	@RequestMapping(value="/ShowPersonList",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String personList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
			fLinePersonMtService = (FLinePersonMtService)context.getBean("fLinePersonMtService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = fLinePersonMtService.getFindPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(fLinePersonMtService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
		/*	System.out.println(gson.toJson(page));*/
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時工時人員資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
		
	}
	
	@RequestMapping(value="/getAllPerson",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateAllPerson(HttpSession session,@RequestParam("status")String status) {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			fLinePersonMtService = (FLinePersonMtService)context.getBean("fLinePersonMtService");
			/*System.out.println(status);*/
			System.out.println(fLinePersonMtService.getAllPerson(updateUser,userDataCostId,status));
			
		
		return fLinePersonMtService.getAllPerson(updateUser,userDataCostId,status);
		
	}
	
	@RequestMapping(value="/getAllPersonCondition",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateAllPersonCondition(HttpSession session,@RequestParam("status")String status,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			fLinePersonMtService = (FLinePersonMtService)context.getBean("fLinePersonMtService");
			/*System.out.println(status);*/
			System.out.println(fLinePersonMtService.getAllPersonCondition(updateUser,userDataCostId,status,queryCritirea,queryParam));
			
		
		return fLinePersonMtService.getAllPersonCondition(updateUser,userDataCostId,status,queryCritirea,queryParam);
		
	}
	
	@RequestMapping(value="/getPersonList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String updatePersonList(@RequestBody Employee[] Emp) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		fLinePersonMtService = (FLinePersonMtService) context.getBean("fLinePersonMtService");
	/*	System.err.println("123");
		System.out.println(Emp.length);*/
		for(int i = 0 ;i<Emp.length;i++){
			System.out.println(Emp[i].getEmpNo());
		}
		/*try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			fLinePersonMtService = (FLinePersonMtService) context.getBean("fLinePersonMtService");
			if(fLinePersonMtService.getToPersonN(emp)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新隨綫狀態成功");
			}else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新隨綫狀態失敗");
			}
		}catch(Exception e){
			logger.error("Updating the PersonList info is failed, due to: ",e);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新隨綫狀態發生錯誤，原因："+e.toString());
		}*/
		
		return fLinePersonMtService.getToPerson(Emp);
	}
}
