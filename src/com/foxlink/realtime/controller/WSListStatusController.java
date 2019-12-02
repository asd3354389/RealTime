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

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WSListStatus;
import com.foxlink.realtime.model.WorkShopStatus;
import com.foxlink.realtime.service.ExceptionCostService;
import com.foxlink.realtime.service.WSListStatusService;
import com.foxlink.realtime.service.WorkShopStatusService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/WSListStatus")
public class WSListStatusController {
	private static Logger logger = Logger.getLogger(WSListStatusController.class);
	private WSListStatusService wSListStatusService;
	
	@RequestMapping(value = "/ShowWSListStatus", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "WSListStatus";
	}
	
	@RequestMapping(value="/ShowWSStatusList",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String WSStatusList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
		String JsonResult = null;
		try {
			int currentPage = 1;
			if (curPage==""||curPage==null) {
				currentPage=1;
			} else {
				currentPage = Integer.parseInt(curPage);
				if (queryParam==""||queryParam==null) {
					queryCritirea="";
				}
			}
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
/*			String updateUser = (String)session.getAttribute("username");
			String userDataCostId="ALL";
			System.out.println(userDataCostId);*/
			wSListStatusService = (WSListStatusService)context.getBean("wSListStatusService");
			String accessRole=(String) session.getAttribute("accessRole");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = wSListStatusService.getFindWslsPage(currentPage,queryCritirea, queryParam,accessRole);
			page.setList(wSListStatusService.FindQueryWsls(currentPage, queryCritirea,queryParam,accessRole));
			/*System.out.println(gson.toJson(page));*/
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得產綫維護資料列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
	}
	
	@RequestMapping(value="/RlWSListStatus",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String RlWSListStatus(HttpSession session,@RequestBody WSListStatus[] wSListStatus) {
		JsonObject DisableResult=new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			wSListStatusService = (WSListStatusService) context.getBean("wSListStatusService");
			if(wSListStatusService.RlWSListStatus(wSListStatus, updateUser)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "原因描述已失效");
			}else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "解除原因描述發生錯誤");
			}
			
	}
	catch(Exception ex){
		logger.error("Disable the WSListStatus info is failed, due to:",ex);
		DisableResult.addProperty("StatusCode", "500");
		DisableResult.addProperty("Message", "解除原因描述已發生錯誤，原因:"+ex.toString());
	}		
	return DisableResult.toString();
	}
	
	@RequestMapping(value="/ReasonClass.show",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowReasonClass() {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			wSListStatusService=(WSListStatusService) context.getBean("wSListStatusService");
			jsonResults=gson.toJson(wSListStatusService.FindReasonClass());
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得ReasonClass失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}
	
	@RequestMapping(value="/AddWSListStatus.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddWSListStatus(HttpSession session,@RequestParam("Reason_Class")String Reason_Class,@RequestParam("Reason_Desc")String Reason_Desc){
//		JsonObject AddResult=new JsonObject();		
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			wSListStatusService = (WSListStatusService) context.getBean("wSListStatusService");
			
			return wSListStatusService.addWSListStatus(Reason_Class,Reason_Desc, updateUser);
	}
	
	@RequestMapping(value="/checkWSListStatus.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkWorkShopCost(HttpSession session,@RequestParam("Reason_Class")String Reason_Class,@RequestParam("Reason_Desc")String Reason_Desc){
		JsonObject checkResult=new JsonObject();	
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			wSListStatusService = (WSListStatusService) context.getBean("wSListStatusService");
			String accessRole=(String) session.getAttribute("accessRole");
			if(wSListStatusService.checkWSListStatus(Reason_Class,Reason_Desc)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "此原因描述描述已存在！");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "此原因描述未設置!");
			}
		}
		catch(Exception ex){
			logger.error("Check new Account info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "此原因描述設置發生錯誤，原因："+ex.toString());
		}
		return checkResult.toString();
	}
}
