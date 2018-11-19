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
import com.foxlink.realtime.model.QueryForgetCard;
import com.foxlink.realtime.service.QueryFCService;
import com.foxlink.realtime.service.QueryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class EmpFCController {
	private static Logger logger = Logger.getLogger(EmpFCController.class);
	private QueryFCService queryFCService;

	@RequestMapping(value = "/ShowCheckForgetCard", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "CheckForgetCard";
	}

	@RequestMapping(value = "/CheckFCJson.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryEmp(HttpSession session,@RequestParam("curPage") String curPage,
			@ModelAttribute QueryForgetCard queryForgetCard) {
		// ApplicationContext applicationContext = new
		// ClassPathXmlAppltionContext("Beans.xml");
		// QueryService queryService = (QueryService)
		// applicationContext.getBean("queryService");
		/*
		 * QueryFCService queryFCService=new QueryFCService(); Gson gson = new
		 * GsonBuilder().serializeNulls().create(); String jsonResults =
		 * gson.toJson(queryFCService.FindQueryRecords(queryForgetCard));
		 */
		String jsonResults = "";
		try {
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			int currentPage = 1;
			if (curPage == "" || curPage == null)
				currentPage = 1;
			else
				currentPage = Integer.parseInt(curPage);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			queryFCService = (QueryFCService) context.getBean("queryFCService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = queryFCService.getOTPage(userDataCostId,currentPage, queryForgetCard);
			page.setList(queryFCService.FindQueryRecord(userDataCostId,currentPage, queryForgetCard));
			jsonResults = gson.toJson(page);
			// jsonResults =
			// gson.toJson(queryFCService.FindQueryRecords(queryForgetCard));
		} else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		}
		}
		catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢忘卡失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}

	@RequestMapping(value = "/CheckFCJsonAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryEmps(HttpSession session,@ModelAttribute QueryForgetCard queryForgetCard) {
		String jsonResults = "";
		try {
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			queryFCService = (QueryFCService) context.getBean("queryFCService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(queryFCService.FindQueryRecords(userDataCostId,queryForgetCard));
			// jsonResults =
			// gson.toJson(queryFCService.FindQueryRecords(queryForgetCard));
		}else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		} 
		}catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢忘卡失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
}
