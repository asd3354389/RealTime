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
import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.service.QueryByIdService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/QueryById")
public class QueryByIdController {
	private static Logger logger = Logger.getLogger(ExceptionCostController.class);
	private QueryByIdService queryByIdService;
	@RequestMapping(value = "/ShowQueryById", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "QueryById";
	}
	
	/**
	 * //查詢異常信息
	 * @param session
	 * @param prodPerson
	 * @return
	 */
	@RequestMapping(value="/QueryByIdList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String DeletePerson(HttpSession session,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("UserId")String UserId) {
			String JsonResult = null;
			//RECORDID recordid
			System.out.println("後台返回數據"+startDate);
			try {
			        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			        queryByIdService = (QueryByIdService)context.getBean("QueryByIdService");
			        Gson gson = new GsonBuilder().serializeNulls().create();
			      //查詢是否有刷卡時間  SelectProdList
					List<QueryByIdList> AllChange = queryByIdService.selectById(startDate,endDate,UserId);
					
		            JsonObject statusJson=new JsonObject();	   
                   if (AllChange ==null || AllChange.isEmpty()) {
		            	
		            	statusJson.addProperty("StatusCode", "500");
		            	statusJson.addProperty("Message", "查詢異常列表失敗");
		            	JsonResult = statusJson.toString();
					} else {
						JsonResult=gson.toJson(AllChange);
					}
		            
			} catch (Exception e) {
				// TODO: handle exception
					logger.error(e);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Message", "機種排配刪除失敗，原因："+e.toString());
					JsonResult=exception.toString();
			}
			System.out.println("返回查詢依工號數據++++++++++++++++++>>>"+JsonResult);
		return JsonResult;
	}
}
