package com.foxlink.realtime.controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.service.GetDepidService;
import com.foxlink.realtime.service.QueryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class GetDepidController {
	private static Logger logger = Logger.getLogger(GetDepidController.class);
private GetDepidService getDepidService;
	@RequestMapping(value = "/GetDepid.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")

	public @ResponseBody String queryEmp(@ModelAttribute GetDepid getDepid) {
		// ApplicationContext applicationContext = new
		// ClassPathXmlAppltionContext("Beans.xml");
		// QueryService queryService = (QueryService)
		// applicationContext.getBean("queryService");
		String jsonResults = null;
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			getDepidService = (GetDepidService) context.getBean("getDepidService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			jsonResults = gson.toJson(getDepidService.FindQueryRecords(getDepid));
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
