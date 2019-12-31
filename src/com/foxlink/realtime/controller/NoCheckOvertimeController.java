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
import com.foxlink.realtime.service.DGsubsidyService;
import com.foxlink.realtime.service.NoCheckOvertimeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/NoCheckOvertime")
public class NoCheckOvertimeController {
	
	private static Logger logger=Logger.getLogger(NoCheckOvertimeController.class);
	private NoCheckOvertimeService noCheckOvertimeService;
	
	@RequestMapping(value="/ShowNoCheckOvertime",method=RequestMethod.GET)
	public String ShowDGsubsidyPage(){
		return "NoCheckOverTime";
	}
	
	@RequestMapping(value = "/SearchNoCheckOvertime.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SearchDGsubsidys(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("empId")String empId,@RequestParam("depId")String depId,@RequestParam("costId")String costId,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("isShowAll")Boolean isShowAll){
		String jsonResults="";
		try{
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			if (userDataCostId != null && userDataCostId != "") {
				int currentPage = 1;
				if (curPage == "" || curPage == null)
					currentPage = 1;
				else
					currentPage = Integer.parseInt(curPage);

				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				noCheckOvertimeService = (NoCheckOvertimeService) context.getBean("noCheckOvertimeService");
				Gson gson = new GsonBuilder().serializeNulls().create();

				Page page = noCheckOvertimeService.getDGsubsidyPage(userDataCostId, currentPage, empId, depId, costId,
						startDate, endDate);
				page.setList(noCheckOvertimeService.FindSearchDGsubsidys(userDataCostId, currentPage, empId, depId, costId,
						startDate, endDate, isShowAll ,page.getTotalPage()));
				jsonResults = gson.toJson(page);
			}
			else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有未提報加班人員查詢的權限！");
				jsonResults=costIdJson.toString();
			}
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得未提報加班人員列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}

}
