package com.foxlink.realtime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.QueryWorkDayCount;
import com.foxlink.realtime.service.EChartssService;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/EChartss")
public class EChartssController {
	private static Logger logger=Logger.getLogger(EChartssController.class);
	private EChartssService echartssService;
	
	@RequestMapping(value = "/ShowChartSwipeCardABPage", method = RequestMethod.GET)
	public String ShowChartSwipeCardABPage() {
		return "ChartSwipeCardAB";
	}

	@RequestMapping("/getChartSwipeCardAB")
	@ResponseBody
	public JsonObject getChartSwipeCardAB(@RequestParam(value = "varStartTime") String varStartTime,
			@RequestParam(value = "varEndTime") String varEndTime) {
		//List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		JsonObject jsonb =new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			 echartssService = (EChartssService) context.getBean("echartssService");
			 jsonb=echartssService.getChartSwipeCardAB(varStartTime, varEndTime);						
		} catch (Exception e) {
			logger.info(e);
		}
		return jsonb;
	}
	
	

}
