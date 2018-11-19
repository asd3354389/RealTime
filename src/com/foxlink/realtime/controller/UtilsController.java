package com.foxlink.realtime.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.service.ClassNoService;
import com.foxlink.realtime.service.WorkShopService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/Utils")
public class UtilsController {
	
	private WorkShopService workshopService;
	private ClassNoService classNoService;
	@RequestMapping(value="/ClassInfo.show",method=RequestMethod.GET)
	public @ResponseBody String ShowClassInfo(@RequestParam(value="currentShift")String currentShift) {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			classNoService=(ClassNoService) context.getBean("classNOService");
			jsonResults=gson.toJson(classNoService.FindRecord(currentShift));
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得ClassInfo列表失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}

	
	@RequestMapping(value="/WorkshopNo.show",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowWorkshopNO() {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			 workshopService=(WorkShopService) context.getBean("workShopService");
			jsonResults=gson.toJson(workshopService.FindWorkShopNo());
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得WorkshopNo失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}
}
