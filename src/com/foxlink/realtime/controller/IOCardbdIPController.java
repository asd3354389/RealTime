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
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.User;
import com.foxlink.realtime.service.AccountService;
import com.foxlink.realtime.service.IOCardbdIPService;
import com.foxlink.realtime.service.OTCardbdPersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/IOCardBdIP")
public class IOCardbdIPController {
		private static Logger logger =Logger.getLogger(IOCardbdIPController.class);
		private IOCardbdIPService iOCardbdIPService;
		
		@RequestMapping(value = "/ShowIOCardbdIP", method = RequestMethod.GET)
		public String ShowAllIOCardbdIP() {
			return "IOCardMachineBd";
		}
		
		@RequestMapping(value="/ShowIOCardMachineList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
		@ResponseBody
		public String ioCardMachineList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
			System.out.println("123"+queryParam);
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
				String updateUser = (String)session.getAttribute("username");
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				iOCardbdIPService = (IOCardbdIPService)context.getBean("iOCardbdIPService");
				String accessRole=(String) session.getAttribute("accessRole");
				Gson gson = new GsonBuilder().serializeNulls().create();
				Page page = iOCardbdIPService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
				page.setList(iOCardbdIPService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
				System.out.println(gson.toJson(page));
				JsonResult = gson.toJson(page);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得卡機ip進出狀態資料列表列表失敗，原因："+e.toString());
				JsonResult=exception.toString();
			}
			return JsonResult;
		}
		
		@RequestMapping(value="/checkDeviceip.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody 
		public String checkDeviceipDuplicate(HttpSession session,@RequestParam("Deviceip")String Deviceip,@RequestParam("WorkShopNo")String WorkShopNo){
			JsonObject checkResult=new JsonObject();	
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOCardbdIPService = (IOCardbdIPService) context.getBean("iOCardbdIPService");
				String accessRole=(String) session.getAttribute("accessRole");
					if(iOCardbdIPService.checkDeviceipDuplicate(Deviceip,WorkShopNo,accessRole)){
						checkResult.addProperty("StatusCode", "200");
						checkResult.addProperty("Message", "此卡機IP未綁定車間設置狀態，可以設置此卡機IP!");
					}else{
						checkResult.addProperty("StatusCode", "500");
						checkResult.addProperty("Message", "此卡機IP已綁定車間設置狀態，請更改卡機IP！");
					}	
			}	
			catch(Exception ex){
				logger.error("Check new Deviceip info is failed, due to: ",ex);
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "檢查卡機IP是否綁定車間設置了狀態發生錯誤，原因："+ex.toString());
			}
			/*System.out.println(checkResult.toString());*/
			return checkResult.toString();
		}
		
		@RequestMapping(value="/AddIOCardMaIP.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody 
		public String AddIOCardMaIP(HttpSession session,@RequestBody IOCardMachineIP ioCardMachineIP){
			JsonObject AddResult=new JsonObject();		
			try{
				String updateUser=(String) session.getAttribute("username");
//				otCardbd.setUpdate_UserId(updateUser);
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOCardbdIPService = (IOCardbdIPService) context.getBean("iOCardbdIPService");
				String accessRole=(String) session.getAttribute("accessRole");
				if(iOCardbdIPService.setIOCardIP(ioCardMachineIP,updateUser,accessRole)){
					AddResult.addProperty("StatusCode", "200");
					AddResult.addProperty("Message", "卡機IP狀態設置成功");
				}
				else{
					AddResult.addProperty("StatusCode", "500");
					AddResult.addProperty("Message", "卡機IP狀態設置失敗");
				}
			}
			catch(Exception ex){
				logger.error("Adding the new IOCardMaIP info is failed, due to: ",ex);
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "卡機IP狀態設置發生錯誤，原因："+ex.toString());
			}
			System.out.println(AddResult.toString());
			return AddResult.toString();
		}
		
		@RequestMapping(value="/UpdateIOCardMaIP.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody
		public String UpdateIOCardMaIP(HttpSession session,@RequestBody IOCardMachineIP ioCardMachineIP){
			JsonObject UpdateResult=new JsonObject();		
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOCardbdIPService = (IOCardbdIPService) context.getBean("iOCardbdIPService");
				String updateUser=(String) session.getAttribute("username");
				String accessRole=(String) session.getAttribute("accessRole");
				if(iOCardbdIPService.UpdateRecord(ioCardMachineIP,updateUser,accessRole)){
					UpdateResult.addProperty("StatusCode", "200");
					UpdateResult.addProperty("Message", "更新卡機IP狀態成功");
				}
				else{
					UpdateResult.addProperty("StatusCode", "500");
					UpdateResult.addProperty("Message", "更新卡機IP狀態失敗");
				}
			}
			catch(Exception ex){
				logger.error("Updating the Account info is failed, due to: ",ex);
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新卡機IP狀態發生錯誤，原因："+ex.toString());
			}
			return UpdateResult.toString();
		}
		
		@RequestMapping(value="/setSecrecyWS.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody
		public String SetSecrecyWS(HttpSession session,@RequestParam("SecrecyWS")String SecrecyWS,@RequestParam("Status")String Status){
			JsonObject SetResult=new JsonObject();		
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOCardbdIPService = (IOCardbdIPService) context.getBean("iOCardbdIPService");
				String updateUser=(String) session.getAttribute("username");
				String accessRole=(String) session.getAttribute("accessRole");
				if(iOCardbdIPService.setWorkShop(SecrecyWS,Status,updateUser,accessRole)){
					SetResult.addProperty("StatusCode", "200");
					SetResult.addProperty("Message", "設置保密車閒成功");
				}
				else{
					SetResult.addProperty("StatusCode", "500");
					SetResult.addProperty("Message", "設置保密車閒失敗");
				}
			}
			catch(Exception ex){
				logger.error("Updating the Account info is failed, due to: ",ex);
				SetResult.addProperty("StatusCode", "500");
				SetResult.addProperty("Message", "設置保密車閒發生錯誤，原因："+ex.toString());
			}
			return SetResult.toString();
		}
		
		@RequestMapping(value="/deleteIOCardMaIP.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
		@ResponseBody
		public String DeleteIOCardMaIP(HttpSession session,@RequestBody IOCardMachineIP[] ioCardMachineIP){
			JsonObject DisableResult=new JsonObject();
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOCardbdIPService = (IOCardbdIPService) context.getBean("iOCardbdIPService");
				String updateUser=(String) session.getAttribute("username");
				String accessRole=(String) session.getAttribute("accessRole");
				if(iOCardbdIPService.DeleteIOCardMaIP(ioCardMachineIP, updateUser,accessRole)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "卡機IP狀態已失效");
				}
				else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "卡機IP狀態發生錯誤");
				}
			}
			catch(Exception ex){
				logger.error("Disable the IOCardMaIP info is failed, due to:",ex);
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "卡機IP狀態發生錯誤，原因:"+ex.toString());
			}		
			return DisableResult.toString();
		}
}
