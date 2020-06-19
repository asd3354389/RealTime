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
import com.foxlink.realtime.service.SearchWorkDayCountService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@Controller
@RequestMapping("/SearchWorkDayCount")
public class SearchWorkDayCountController {

	private static Logger logger=Logger.getLogger(SearchWorkDayCountController.class);
	private SearchWorkDayCountService searchWorkDayCountService;
	
	@RequestMapping(value="/ShowSearchWorkDayCount",method=RequestMethod.GET)
	public String ShowSearchWorkDayCount(){
		return "SearchWorkDayCount";
	}
	
	@RequestMapping(value = "/SearchWorkDayCount.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryWorkDayCount(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("Type")String type,@RequestParam("Data")String data) {
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
			searchWorkDayCountService = (SearchWorkDayCountService) context.getBean("searchWorkDayCountService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = searchWorkDayCountService.getSCPage(userDataCostId,currentPage,type,data);
			page.setList(searchWorkDayCountService.FindQueryRecord(userDataCostId,currentPage,type,data));
			jsonResults = gson.toJson(page);
			//jsonResults = gson.toJson(queryScService.FindQueryRecords(querySwipeCard));
		}else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢上班記錄列表的權限！");
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
	
	@RequestMapping(value = "/SearchAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryAll(HttpSession session,@RequestParam("Type")String type,@RequestParam("Data")String data) {
		String jsonResults = "";
		try {
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			int currentPage = 1;
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				searchWorkDayCountService = (SearchWorkDayCountService) context.getBean("searchWorkDayCountService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				jsonResults = gson.toJson(searchWorkDayCountService.FindQueryRecords(userDataCostId,type,data));
				//jsonResults = gson.toJson(queryScService.FindQueryRecords(querySwipeCard));
		}else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢上班記錄列表的權限！");
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
}
