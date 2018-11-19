package com.foxlink.realtime.controller;


import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QuerySwipeCard;
import com.foxlink.realtime.service.QueryScService;
import com.foxlink.realtime.service.QueryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class EmpSCController {
	private static Logger logger = Logger.getLogger(EmpSCController.class);
private QueryScService queryScService;
	@RequestMapping(value = "/ShowCheckSwipeCardRecords", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "CheckSwipeCardRecords";
	}


	@RequestMapping(value = "/CheckSCJson.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryEmp(HttpSession session,@RequestParam("curPage")String curPage,@ModelAttribute QuerySwipeCard querySwipeCard) {
		// ApplicationContext applicationContext = new
		// ClassPathXmlAppltionContext("Beans.xml");
		// QueryService queryService = (QueryService)
		// applicationContext.getBean("queryService");
		String jsonResults = "";
		try {
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			int currentPage = 1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			 queryScService = (QueryScService) context.getBean("queryScService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = queryScService.getSCPage(userDataCostId,currentPage, querySwipeCard);
			page.setList(queryScService.FindQueryRecord(userDataCostId,currentPage,querySwipeCard));
			jsonResults = gson.toJson(page);
			//jsonResults = gson.toJson(queryScService.FindQueryRecords(querySwipeCard));
		}else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		}} catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢員工加班狀態失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}

	//查詢總記錄
	@RequestMapping(value = "/CheckSCJsonAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryEmps(HttpSession session,@ModelAttribute QuerySwipeCard querySwipeCard) {
		// ApplicationContext applicationContext = new
		// ClassPathXmlAppltionContext("Beans.xml");
		// QueryService queryService = (QueryService)
		// applicationContext.getBean("queryService");
		String jsonResults = "";
		try {
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			 queryScService = (QueryScService) context.getBean("queryScService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(queryScService.FindQueryRecords(userDataCostId,querySwipeCard));
			//jsonResults = gson.toJson(queryScService.FindQueryRecords(querySwipeCard));
		}else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		}} catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢員工刷卡記錄失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
}
