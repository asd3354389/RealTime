package com.foxlink.realtime.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.ThreeMergeOne;
import com.foxlink.realtime.service.ThreeMergeOneService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/ThreeMergeOne")
public class ThreeMergeOneController {
	private static Logger logger=Logger.getLogger(ThreeMergeOneController.class);
	private ThreeMergeOneService threeMergeOneService;
	
	@RequestMapping(value="/ShowThreeMergeOne",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "ThreeMergeOne";
	}
	
	@RequestMapping(value="/outPutExcel",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public void outPutExcel(HttpServletResponse response,@RequestParam("StartDate")String StartDate,@RequestParam("EndDate")String EndDate,@RequestParam("Type")String Type,@RequestParam("Data")String Data) {
//		@ResponseBody String
//		JsonObject Result=new JsonObject();
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		threeMergeOneService = (ThreeMergeOneService) context.getBean("threeMergeOneService");
		threeMergeOneService.export(StartDate,EndDate,Type,Data,response);
//		try {
//			if(threeMergeOneService.export(StartDate,EndDate,Type,Data,response)){
//				System.out.println(123465);
//				Result.addProperty("StatusCode", "200");
//				Result.addProperty("Message", "導出Excel文檔成功");
//			}else{
//				Result.addProperty("StatusCode", "500");
//				Result.addProperty("Message", "導出Excel文檔失敗");
//			}
//		} catch (Exception ex) {
//			// TODO: handle exception
//			logger.error("Disable the export info is failed, due to:",ex);
//			Result.addProperty("StatusCode", "500");
//			Result.addProperty("Message", "十導出Excel文檔發生錯誤，原因:"+ex.toString());
//		}
		
        
//		return Result.toString();
	}
	
	@RequestMapping(value="/judgeDownload",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String judgeDownload(@RequestParam("StartDate")String StartDate,@RequestParam("EndDate")String EndDate,@RequestParam("Type")String Type,@RequestParam("Data")String Data) {

		JsonObject Result=new JsonObject();
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		threeMergeOneService = (ThreeMergeOneService) context.getBean("threeMergeOneService");
		try {
			if(threeMergeOneService.judgeDownload(StartDate,EndDate,Type,Data)){
				Result.addProperty("StatusCode", "200");
				Result.addProperty("Message", "導出Excel文檔成功");
			}else{
				Result.addProperty("StatusCode", "500");
				Result.addProperty("Message", "查詢不到相應數據,導出Excel文檔失敗");
			}
		} catch (Exception ex) {
			// TODO: handle exception
			logger.error("Disable the export info is failed, due to:",ex);
			Result.addProperty("StatusCode", "500");
			Result.addProperty("Message", "導出Excel文檔發生錯誤，原因:"+ex.toString());
		}
		
        
		return Result.toString();
	}
}
