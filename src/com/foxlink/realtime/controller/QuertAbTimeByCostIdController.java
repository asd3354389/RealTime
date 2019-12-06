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

import com.foxlink.realtime.service.CountEmpService;
import com.foxlink.realtime.service.QuertAbTimeByCostIdService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@Controller
@RequestMapping("/QuertAbTimeByCostId")
public class QuertAbTimeByCostIdController {
	private static Logger logger=Logger.getLogger(QuertAbTimeByCostIdController.class);
	private QuertAbTimeByCostIdService quertAbTimeByCostIdService;
	
	@RequestMapping(value="/ShowQuertAbTimeByCostId",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "QueryAbTimeByCostId";
	}
	
	@RequestMapping(value = "/ShowDepid.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowDepid(HttpSession session,@RequestParam("costid")String costid) {
		String jsonResults = null;
		try {
			String accessRole = (String) session.getAttribute("accessRole");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			quertAbTimeByCostIdService = (QuertAbTimeByCostIdService) context.getBean("quertAbTimeByCostIdService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(quertAbTimeByCostIdService.FindDepidRecords(costid));
			
		} catch (Exception ex) {
			logger.error("FindDepid falid", ex);
			JsonObject error = new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取部門代碼失敗，原因:" + ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
	
	@RequestMapping(value="/ShowABTimeByCostid", method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String ShowABTimeByCostid(HttpSession session,@RequestParam("Bu")String Bu,@RequestParam("costid")String costid,@RequestParam("depid")String depid,@RequestParam("SDate")String SDate,@RequestParam("EDate")String EDate){
			String result = "";
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			quertAbTimeByCostIdService = (QuertAbTimeByCostIdService) context.getBean("quertAbTimeByCostIdService");
			String accessRole = (String) session.getAttribute("accessRole");
			if(accessRole.equals("ROLE_VIC_ASSISTANT")) {
				result = quertAbTimeByCostIdService.ShowABTimeByDepid(depid,SDate,EDate);
			}else if(accessRole.equals("ROLE_VIC_ADMIN")||accessRole.equals("ALL")) {
				result = quertAbTimeByCostIdService.ShowABTimeByCostid(Bu,costid,depid,SDate,EDate);
			}
			return result;
	}
	
	
}
