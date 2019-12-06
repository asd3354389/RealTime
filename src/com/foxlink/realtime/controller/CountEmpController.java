package com.foxlink.realtime.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.IOCardMachineIP;
import com.foxlink.realtime.service.CountEmpService;
import com.foxlink.realtime.service.GetDepidService;
import com.foxlink.realtime.service.IOCardbdIPService;
import com.foxlink.realtime.service.ProdAllLineService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/CountEmp")
public class CountEmpController {
	private static Logger logger=Logger.getLogger(CountEmpController.class);
	private CountEmpService countEmpService;
	
	@RequestMapping(value="/ShowCountEmp",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "CountEmp";
	}
	
	@RequestMapping(value = "/ShowDepid.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowDepid(HttpSession session) {
		String jsonResults = null;
		String userDataCostId=(String) session.getAttribute("userDataCostId");
		String username=(String) session.getAttribute("username");
		//String accessRole = (String) session.getAttribute("accessRole");

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			countEmpService = (CountEmpService) context.getBean("countEmpService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(countEmpService.FindDepidRecords(userDataCostId));
		} catch (Exception ex) {
			logger.error("FindDepid falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取部門代碼失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value = "/ShowCountEmpList", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowCountEmpList(HttpSession session,@RequestParam("depid")String depid,@RequestParam("SDate")String SDate) {

		String accessRole=(String) session.getAttribute("accessRole");
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		countEmpService = (CountEmpService) context.getBean("countEmpService");
	
		return countEmpService.ShowCountEmpList(depid,SDate);
	}
	
	@RequestMapping(value="/UpdateStatus.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateStatus(HttpSession session,@RequestParam("UserNo")String userNo,@RequestParam("depid")String depid,@RequestParam("SDate")String SDate,@RequestParam("type_status")String type_status,@RequestParam("class_no")String class_no){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			countEmpService = (CountEmpService) context.getBean("countEmpService");
			if(countEmpService.UpdateStatus(userNo,depid,SDate,type_status,class_no)){
				
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新狀態成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新狀態失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Status info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新狀態發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value = "/ShowRole", method = RequestMethod.POST,produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowRole(HttpSession session) {
		String jsonResults = null;
		String accessRole=(String) session.getAttribute("accessRole");	
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			countEmpService = (CountEmpService) context.getBean("countEmpService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(accessRole);
			//System.out.println(accessRole);
		} catch (Exception ex) {
			logger.error("FindRole falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取权限失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value = "/ShowRoleText", method = RequestMethod.POST,produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowRoleText(HttpSession session) {
			String accessRole=(String) session.getAttribute("accessRole");	
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			countEmpService = (CountEmpService) context.getBean("countEmpService");

		return accessRole;
	}
	
	@RequestMapping(value = "/ShowAssistantDepid.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowAssistantDepid(HttpSession session) {
		String jsonResults = null;
		String username=(String) session.getAttribute("username");

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			countEmpService = (CountEmpService) context.getBean("countEmpService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(countEmpService.FindAssistantDepid(username));
		} catch (Exception ex) {
			logger.error("FindDepid falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取部門代碼失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
}
