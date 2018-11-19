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
import com.foxlink.realtime.model.QueryShift;

import com.foxlink.realtime.service.QueryService;

import com.foxlink.realtime.service.AccountService;

import com.foxlink.realtime.service.QueryShiftService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class EmpShiftController {

	private static Logger logger = Logger.getLogger(EmpShiftController.class);
	private QueryShiftService queryShiftService;
	//ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@RequestMapping(value = "/ShowCheckShiftStatus", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "CheckShiftStatus";
	}

	@RequestMapping(value = "CheckShiftStatusJson.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String queryShift(HttpSession session,@RequestParam("curPage") String curPage,@ModelAttribute QueryShift queryShift) {
		String jsonResults = "";
		try {
			//取session中的值
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			//String userDataCostid=(String) session.getAttribute("userDataCostid");
			//if(userDataCostid!=null&&userDataCostid!=""){
				int currentPage = 1;
				if (curPage == "" || curPage == null)
					currentPage = 1;
				else
					currentPage = Integer.parseInt(curPage);
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				queryShiftService = (QueryShiftService) context.getBean("queryShiftService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				Page page = queryShiftService.getShiftPage(userDataCostId,currentPage, queryShift);
				page.setList(queryShiftService.FindQueryRecord(userDataCostId,currentPage, queryShift));
				jsonResults = gson.toJson(page);
			/*}else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "此賬號無查詢權限");
				jsonResults=costIdJson.toString();
			}*/
			
			// jsonResults =
			// gson.toJson(queryShiftService.FindQueryRecords(queryShift));
		}else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		} 
			}catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢員工班別失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}

	@RequestMapping(value = "CheckShiftStatusJsonAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String queryShift(HttpSession session,@ModelAttribute QueryShift queryShift) {

		String jsonResults = "";
		try {
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if(userDataCostId!=""&&userDataCostId!=null){
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			queryShiftService = (QueryShiftService) context.getBean("queryShiftService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(queryShiftService.FindQueryRecords(userDataCostId,queryShift));
			// jsonResults =
			// gson.toJson(queryShiftService.FindQueryRecords(queryShift));
		} else{
			JsonObject costIdJson=new JsonObject();
			costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
			jsonResults=costIdJson.toString();
		}}catch (Exception ex) {
			logger.error("FindEmpOTStatus falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMessage", "查詢員工班別失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		System.out.println(jsonResults);
		return jsonResults;
	}

}
