package com.foxlink.realtime.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.FourteenRO;
import com.foxlink.realtime.model.FourteenROP;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.FourteenROPService;
import com.foxlink.realtime.service.FourteenROService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/FourteenROP")
public class FourteenROPController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private FourteenROPService fourteenROPService;
	
	@RequestMapping(value="/ShowFourteenROP",method=RequestMethod.GET)
	public String ShowWorkShopNoRestInfo(){
		return "FourteenROP";
	}

	@RequestMapping(value="/ShowAllFourteenROP",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowAllIOSpecialWSEmp(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);;
			    if(queryParam=="" || queryParam==null)
			    	queryCritirea="";
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser=(String) session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			//fourteenROPService = (FourteenROPService) context.getBean("fourteenROPService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = fourteenROPService.getFourteenROPPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(fourteenROPService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得十四休一列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/deleteFourteenROP.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteIOSpecialWSEmp(HttpSession session,@RequestParam("id")String id
			,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate){
		JsonObject DisableResult=new JsonObject();
		try{
			String updateUser=(String) session.getAttribute("username");
			System.out.println("費用代碼========="+id);
			if(fourteenROPService.DeleteFourteenROP(id, updateUser,startDate,endDate)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "十四休一設置已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "十四休一設置發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the IOCardMaIP info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "十四休一設置發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	@RequestMapping(value="/AddFourteenROP.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String ADDIOSpecialWSEmp(HttpSession session,@RequestBody FourteenROP[] fourteenROP){
		JsonObject AddResult=new JsonObject();	

		try{
			String updateUser=(String) session.getAttribute("username");

			if (fourteenROPService.addfourteenROP(fourteenROP,updateUser)) {
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "十四休一設置成功");
			} else {
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "十四休一設置失敗");
			}

		}
		catch(Exception ex){
			logger.error("Adding the new IOWsPw info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "十四休一設置發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	
}
