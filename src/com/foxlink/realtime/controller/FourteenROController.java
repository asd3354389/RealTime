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

import com.foxlink.realtime.model.FourteenRO;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.FourteenROService;
import com.foxlink.realtime.service.IOSpecialWSEmpService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/FourteenRO")
public class FourteenROController {
	private static Logger logger = Logger.getLogger(FourteenROController.class);
	private FourteenROService fourteenROService;

	@RequestMapping(value="/ShowFourteenRO",method=RequestMethod.GET)
	public String ShowWorkShopNoRestInfo(){
		return "FourteenRO";
	}
	
	@RequestMapping(value="/ShowAllFourteenRO",method=RequestMethod.POST,produces="application/json;charset=utf-8")
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
			fourteenROService = (FourteenROService) context.getBean("fourteenROService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = fourteenROService.getFourteenROPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(fourteenROService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
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
	
	@RequestMapping(value="/AddFourteenRO.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String ADDIOSpecialWSEmp(HttpSession session,@RequestBody FourteenRO[] fourteenRO){
		JsonObject AddResult=new JsonObject();	

		try{
			String updateUser=(String) session.getAttribute("username");

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			fourteenROService = (FourteenROService) context.getBean("fourteenROService");
			if (fourteenROService.addfourteenRO(fourteenRO,updateUser)) {
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
	
	@RequestMapping(value="/UpdateFourteenRO.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateIOSpecialWSEmp(HttpSession session,@RequestBody FourteenRO fourteenRO){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			fourteenROService = (FourteenROService) context.getBean("fourteenROService");
			String updateUser=(String) session.getAttribute("username");
			if(fourteenROService.UpdateRecord(fourteenRO,updateUser)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新保密車間臨時權限成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新保密車間臨時權限失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新保密車間臨時權限發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/deleteFourteenRO.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteIOSpecialWSEmp(HttpSession session,@RequestParam("Costid")String Costid
			,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			fourteenROService = (FourteenROService) context.getBean("fourteenROService");
			String updateUser=(String) session.getAttribute("username");
			System.out.println("費用代碼========="+Costid);
			if(fourteenROService.DeleteFourteenRO(Costid, updateUser,startDate,endDate)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "保密車間進出臨時權限已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "保密車間進出臨時權限發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the IOCardMaIP info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "保密車間進出臨時權限發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
}
