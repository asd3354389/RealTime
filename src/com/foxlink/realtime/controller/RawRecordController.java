package com.foxlink.realtime.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.foxlink.realtime.model.Holiday;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.HolidayService;
import com.foxlink.realtime.service.RawRecordService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/RawRecord")
public class RawRecordController {
	
	private static Logger logger=Logger.getLogger(RawRecordController.class);
	private RawRecordService rawRecordService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}

	@RequestMapping(value="/ShowRawRecord",method=RequestMethod.GET)
	public String ShowRawRecordPage(){
		return "RawRecord";
	}
	
	@RequestMapping(value = "/SearchRawRecord.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SearchRawRecords(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("empId")String empId,@RequestParam("depId")String depId,@RequestParam("costId")String costId,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("recordStatus")String recordStatus,@RequestParam("isShowAll")Boolean isShowAll){
		String jsonResults="";
		try{
			String userDataCostId=(String) session.getAttribute("userDataCostId");
	//		String assistantId=(String) session.getAttribute("assistantId");
			if (userDataCostId != null && userDataCostId != "") {
				int currentPage = 1;
				if (curPage == "" || curPage == null)
					currentPage = 1;
				else
					currentPage = Integer.parseInt(curPage);
				
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				rawRecordService = (RawRecordService) context.getBean("rawRecordService");
				Gson gson = new GsonBuilder().serializeNulls().create();

				Page page = rawRecordService.getRawRecordPage(userDataCostId,currentPage, empId, depId, costId, startDate, endDate,
						recordStatus);
				page.setList(rawRecordService.FindSearchRawRecords(userDataCostId,currentPage, empId, depId, costId, startDate,
						endDate, recordStatus, isShowAll));
				
				jsonResults = gson.toJson(page);
			}
			else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢原始刷卡記錄列表的權限！");
				jsonResults=costIdJson.toString();
			}
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得原始刷卡記錄列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
	
	
}
