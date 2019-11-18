package com.foxlink.realtime.controller;


import java.util.ArrayList;
import java.util.List;
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

import com.foxlink.realtime.model.ChangeEmployee;
import com.foxlink.realtime.model.ProdPerson;
import com.foxlink.realtime.model.SelectProdList;
import com.foxlink.realtime.model.SelectProdPerson;
import com.foxlink.realtime.service.ProdPersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



@Controller
@RequestMapping("/ProdPerson")
public class ProdPersonController {
	private static Logger logger = Logger.getLogger(ExceptionCostController.class);
	private ProdPersonService prodPersonService;
	@RequestMapping(value = "/ShowProdPerson", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "ProdPerson";
	}
	
	
	/**
	 * 新增機種排配
	 * @param session
	 * @param prodPerson
	 * @return
	 */
	@RequestMapping(value="/InsertProdPersonList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String InsertProdPerson(HttpSession session,@RequestBody ProdPerson prodPerson) {
		String JsonResult = null;
		System.out.println("後台返回數據"+prodPerson);
		try {
			//QueryChangeEmployee
		        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		        prodPersonService = (ProdPersonService)context.getBean("ProdPersonService");
	            int isInsert = prodPersonService.insertProd(prodPerson);
	            JsonObject statusJson=new JsonObject();
	            if (isInsert==0) {
	            	statusJson.addProperty("StatusCode", "200");
	            	statusJson.addProperty("Message", "幾種排配新增成功");
				} else {
					statusJson.addProperty("StatusCode", "500");
	            	statusJson.addProperty("Message", "機種排配新增失敗");
				}
	            JsonResult = statusJson.toString();
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("Message", "幾種排配新增失敗，原因："+e.toString());
				JsonResult=exception.toString();
			}
		return JsonResult;
	}
	
	
	/**
	 * 查詢費用代碼
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/ShowCostId",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String CostidStr(HttpSession session) {
		String JsonResult = null;
			
		try {
				//QueryChangeEmployee
			        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			        prodPersonService = (ProdPersonService)context.getBean("ProdPersonService");
		            String assistantId=(String)session.getAttribute("assistantId");
		            JsonResult = prodPersonService.CheckCostId(assistantId);
					//獲取登錄賬號的費用代碼
				    System.out.println("登錄的工號"+assistantId);
					Gson gson = new GsonBuilder().serializeNulls().create();
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("ErrorMessage", "取得車間例外費用代碼資料列表列表失敗，原因："+e.toString());
					JsonResult=exception.toString();
			}
		return JsonResult;
	}
		
	
	/**
	 * //查詢要刪除的機種排配
	 * @param session
	 * @param prodPerson
	 * @return
	 */
	@RequestMapping(value="/SelectProdPersonList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String SelectPerson(HttpSession session,@RequestBody SelectProdPerson selectProdPerson) {
			String JsonResult = null;
			System.out.println("後台返回數據"+selectProdPerson);
			try {
			        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			        prodPersonService = (ProdPersonService)context.getBean("ProdPersonService");
			        Gson gson = new GsonBuilder().serializeNulls().create();
			      //查詢是否有刷卡時間  SelectProdList
					List<SelectProdList> AllChange = prodPersonService.selectProd(selectProdPerson);
		            JsonObject statusJson=new JsonObject();
		            if (AllChange ==null || AllChange.isEmpty()) {
		            	
		            	statusJson.addProperty("StatusCode", "500");
		            	statusJson.addProperty("Message", "機種排配查詢失敗");
		            	JsonResult = statusJson.toString();
					} else {
						JsonResult=gson.toJson(AllChange);
					}
			} catch (Exception e) {
				// TODO: handle exception
					logger.error(e);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Message", "機種排配查詢失敗，原因："+e.toString());
					JsonResult=exception.toString();
			}
			//System.out.println("返回前端的數據++++++++++++++++++>>>"+JsonResult);
		return JsonResult;
	}
	
	/**
	 * //查詢要刪除的機種排配
	 * @param session
	 * @param prodPerson
	 * @return
	 */
	@RequestMapping(value="/DeleteProdPersonList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String DeletePerson(HttpSession session,@RequestParam("RECORDID")String recordid) {
			String JsonResult = null;
			//RECORDID recordid
			System.out.println("後台返回數據"+recordid);
			try {
			        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			        prodPersonService = (ProdPersonService)context.getBean("ProdPersonService");
			        Gson gson = new GsonBuilder().serializeNulls().create();
			        JsonObject statusJson=new JsonObject();			   
					if (prodPersonService.deleteProd(recordid)) {
						statusJson.addProperty("StatusCode", "200");
		            	statusJson.addProperty("Message", "機種排配刪除成功");
		            	JsonResult=statusJson.toString();
					} else {
						statusJson.addProperty("StatusCode", "500");
		            	statusJson.addProperty("Message", "機種排配刪除失敗");
		            	JsonResult=statusJson.toString();
					}
		            
			} catch (Exception e) {
				// TODO: handle exception
					logger.error(e);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Message", "機種排配刪除失敗，原因："+e.toString());
					JsonResult=exception.toString();
			}
			System.out.println("返回刪除的前端的數據++++++++++++++++++>>>"+JsonResult);
		return JsonResult;
	}
}
