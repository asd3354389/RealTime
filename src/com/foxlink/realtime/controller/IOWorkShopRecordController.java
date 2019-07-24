package com.foxlink.realtime.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.IOWorkShopRecordService;
import com.foxlink.realtime.service.RawRecordService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@Controller
@RequestMapping("/IOWorkShop")
public class IOWorkShopRecordController {
	private static Logger logger = Logger.getLogger(IOWorkShopRecordController.class);
	private IOWorkShopRecordService iOWorkShopRecordService;
	
	@RequestMapping(value = "/ShowIOWorkShopRecord", method = RequestMethod.GET)
	public String ShowIOWorkShopPwListPage() {
		return "IOWorkShopPw";
	}
	
	@RequestMapping(value = "/SearchIOWorkShopRecord.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SearchIOWSRecords(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("empId")String empId,@RequestParam("workShopNo")String workShopNo,@RequestParam("depId")String depId,@RequestParam("costId")String costId,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("recordStatus")String recordStatus,@RequestParam("isShowAll")Boolean isShowAll){
		
		String jsonResults="";
		try{
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			String accessRole=(String) session.getAttribute("accessRole");
	//		String assistantId=(String) session.getAttribute("assistantId");
			if (userDataCostId != null && userDataCostId != "") {
				int currentPage = 1;
				if (curPage == "" || curPage == null)
					currentPage = 1;
				else
					currentPage = Integer.parseInt(curPage);
				
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOWorkShopRecordService = (IOWorkShopRecordService) context.getBean("iOWorkShopRecordService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				if(!recordStatus.equals("41")) {
					System.out.println(123);
					Page page = iOWorkShopRecordService.getRawRecordPage(userDataCostId,currentPage, empId,workShopNo, depId, costId, startDate, endDate,
							recordStatus,accessRole);
					page.setList(iOWorkShopRecordService.FindSearchRawRecords(userDataCostId,currentPage, empId,workShopNo, depId, costId, startDate,
							endDate, recordStatus, isShowAll,accessRole));
					jsonResults = gson.toJson(page);
				}else {
					System.out.println(456);
					Page page = iOWorkShopRecordService.getRawRecordOtherPage(userDataCostId,currentPage,workShopNo, startDate, endDate,recordStatus,accessRole);
					page.setList(iOWorkShopRecordService.FindSearchOtherRawRecords(userDataCostId,currentPage,workShopNo, startDate,endDate, recordStatus, isShowAll,accessRole));
					jsonResults = gson.toJson(page);
				}	
			}
			else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢車間門禁刷卡記錄列表的權限！");
				jsonResults=costIdJson.toString();
			}
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得車間門禁刷卡記錄列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
}
