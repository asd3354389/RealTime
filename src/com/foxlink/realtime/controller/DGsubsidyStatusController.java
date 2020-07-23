package com.foxlink.realtime.controller;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.EmpIpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryDGsubsidyStatus;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.service.DGsubsidyStatusService;
import com.foxlink.realtime.service.EmpIPBindingService;
import com.foxlink.realtime.service.QueryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
//@RequestMapping("/DGsubsidyStatus")
public class DGsubsidyStatusController {
	private static Logger logger = Logger.getLogger(DGsubsidyStatusController.class);
	private DGsubsidyStatusService dgsubsidyStatusService;
	@RequestMapping(value = "/ShowDGsubsidyStatus", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "DGsubsidyStatus";
	}


	@RequestMapping(value = "/DGsubsidyStatusJson.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String queryEmp(HttpSession session,@RequestParam("curPage") String curPage,
			@ModelAttribute QueryStatus queryStatus) {
System.out.println("=================进入方法11");
		String jsonResults = "";
		try {
			System.out.println("=================进入方法22================");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			int currentPage = 1;
			if (curPage == "" || curPage == null)
				currentPage = 1;
			else
				currentPage = Integer.parseInt(curPage);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			DGsubsidyStatusService queryService = (DGsubsidyStatusService) context.getBean("dGsubsidyStatusService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = queryService.getOTPage(userDataCostId,currentPage, queryStatus);
			page.setList(queryService.FindQueryRecord(userDataCostId,currentPage, queryStatus));
			jsonResults = gson.toJson(page);
		} 
		else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		}
		}catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢人员加班昨状态失败，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		System.out.println("查询的数据"+jsonResults);
		return jsonResults;
	}
	//CheckOverTimeStatusJsonAll.show
	@RequestMapping(value = "/DGsubsidyStatusJsonAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryEmpAll(HttpSession session,@ModelAttribute QueryStatus queryStatus) {
		String jsonResults = "";
		try {
			
			System.out.println("=============进入方法33=====");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Beans.xml");
			dgsubsidyStatusService = (DGsubsidyStatusService) applicationContext.getBean("dGsubsidyStatusService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(dgsubsidyStatusService.FindQueryRecords(userDataCostId,queryStatus));
			}else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
				jsonResults=costIdJson.toString();
			}
		} catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢人员加班昨状态失败，原因:" + ex.toString());
			jsonResults = error.toString();
			}
		System.out.println("=============进入方法44=====");
		System.out.println("查询的数据"+jsonResults);
		return jsonResults;
		}


}
