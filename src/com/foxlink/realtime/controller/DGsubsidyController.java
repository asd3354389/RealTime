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
import org.springframework.web.servlet.ModelAndView;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.DGsubsidyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/DGsubsidy")
public class DGsubsidyController {
	
	private static Logger logger=Logger.getLogger(DGsubsidyController.class);
	private DGsubsidyService dGsubsidyService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView showMainPage(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.addObject("username", session.getAttribute("username"));
		
	    model.setViewName("index");
	    return model;
	}

	@RequestMapping(value="/ShowDGsubsidy",method=RequestMethod.GET)
	public String ShowDGsubsidyPage(){
		return "DGsubsidy";
	}
	
	@RequestMapping(value = "/SearchDGsubsidy.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
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
				dGsubsidyService = (DGsubsidyService) context.getBean("dGsubsidyService");
				Gson gson = new GsonBuilder().serializeNulls().create();

				Page page = dGsubsidyService.getDGsubsidyPage(userDataCostId, currentPage, empId, depId, costId,
						startDate, endDate);
				page.setList(dGsubsidyService.FindSearchDGsubsidys(userDataCostId, currentPage, empId, depId, costId,
						startDate, endDate, isShowAll));
				jsonResults = gson.toJson(page);
			}
			else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢頂崗津貼的權限！");
				jsonResults=costIdJson.toString();
			}
			}catch(Exception ex){
				logger.error(ex);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得頂崗津貼列表失敗，原因："+ex.toString());
				jsonResults=exception.toString();
		}
		return jsonResults;
	}
		
}
