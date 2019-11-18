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

import com.foxlink.realtime.model.QueryInfoByIdList;
import com.foxlink.realtime.model.QueryRecordByIdList;
import com.foxlink.realtime.service.QueryRecordByIdService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
@Controller
@RequestMapping("/QueryRecordById")
public class QueryRecordByIdController {
	private static Logger logger = Logger.getLogger(ExceptionCostController.class);
	private QueryRecordByIdService queryRecordByIdService;
	@RequestMapping(value = "/ShowQueryRecordById", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "QueryRecordById";
	}
	
	/**
	 * //查詢實時門禁信息
	 * @param session
	 * @param prodPerson
	 * @return
	 */
	@RequestMapping(value="/QueryRecordByIdList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String QueryRecordByIdList(HttpSession session,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("UserId")String UserId) {
			String JsonResult = null;
			//RECORDID recordid
			System.out.println("後台返回數據"+startDate);
			try {
			        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			        queryRecordByIdService = (QueryRecordByIdService)context.getBean("QueryRecordByIdService");
			        Gson gson = new GsonBuilder().serializeNulls().create();
			      //查詢實時門禁
					List<QueryRecordByIdList> swipeCardList = queryRecordByIdService.selectSwipeCardById(startDate,endDate,UserId);
		            JsonObject statusJson=new JsonObject();	   
                   if (swipeCardList ==null || swipeCardList.isEmpty()) {
		            	
		            	statusJson.addProperty("StatusCode", "500");
		            	statusJson.addProperty("Message", "查詢實時門禁列表失敗");
		            	JsonResult = statusJson.toString();
					} else {
						JsonResult=gson.toJson(swipeCardList);
					}
		            
			} catch (Exception e) {
				// TODO: handle exception
					logger.error(e);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Message", "查詢實時門禁列表失敗，原因："+e.toString());
					JsonResult=exception.toString();
			}
			System.out.println("返回查詢依工號實時門禁數據++++++++++++++++++>>>"+JsonResult);
		return JsonResult;
	}
	/**
	 * //查詢大門門禁信息
	 * @param session
	 * @param prodPerson
	 * @return
	 */
	@RequestMapping(value="/QueryInfoByIdList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String QueryInfoByIdList(HttpSession session,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("UserId")String UserId) {
			String JsonResult = null;
			//RECORDID recordid
			System.out.println("後台返回數據"+startDate);
			try {
			        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			        queryRecordByIdService = (QueryRecordByIdService)context.getBean("QueryRecordByIdService");
			        Gson gson = new GsonBuilder().serializeNulls().create();
					//查詢大門門禁
					List<QueryInfoByIdList>  swipeInfoList = queryRecordByIdService.selectSwipeInfoListById(startDate,endDate,UserId);
					
		            JsonObject statusJson=new JsonObject();	   
                   if (swipeInfoList ==null || swipeInfoList.isEmpty()) {
		            	
		            	statusJson.addProperty("StatusCode", "500");
		            	statusJson.addProperty("Message", "查詢大門門禁列表失敗");
		            	JsonResult = statusJson.toString();
					} else {
						JsonResult=gson.toJson(swipeInfoList);
					}
		            
			} catch (Exception e) {
				// TODO: handle exception
					logger.error(e);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Message", "查詢大門門禁列表失敗，原因："+e.toString());
					JsonResult=exception.toString();
			}
			System.out.println("返回查詢依工號大門門禁數據++++++++++++++++++>>>"+JsonResult);
		return JsonResult;
	}
}
