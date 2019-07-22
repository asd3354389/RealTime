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

import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.IOCardbdIPService;
import com.foxlink.realtime.service.IOWorkShopPowerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@Controller
@RequestMapping("/IOWorkShopPower")
public class IOWorkShopPower {
	private static Logger logger =Logger.getLogger(IOWorkShopPower.class);
	private IOWorkShopPowerService iOWorkShopPowerService;
//	
	@RequestMapping(value = "/ShowIOWorkShopPwList", method = RequestMethod.GET)
	public String ShowIOWorkShopPwListPage() {
		return "IOWorkShopTsPower";
	}
	
	@RequestMapping(value="/ShowIOWorkShopList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String IOWorkShopList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
//		System.out.println("123"+queryParam);
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
			iOWorkShopPowerService = (IOWorkShopPowerService)context.getBean("iOWorkShopPowerService");
			String accessRole=(String) session.getAttribute("accessRole");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = iOWorkShopPowerService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId,accessRole);
			page.setList(iOWorkShopPowerService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			System.out.println(iOWorkShopPowerService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId,accessRole));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得進出車間臨時權限資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
	}
	//判斷同一工號和車間是否有數據
	@RequestMapping(value="/checkUserName.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkUserNameDuplicate(HttpSession session,@RequestParam("Emp_id")String Emp_id,@RequestParam("WorkshopNo")String WorkshopNo){
		JsonObject checkResult=new JsonObject();	
		String userDataCostId=(String) session.getAttribute("userDataCostId");
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			iOWorkShopPowerService = (IOWorkShopPowerService) context.getBean("iOWorkShopPowerService");
			String accessRole=(String) session.getAttribute("accessRole");
			if(iOWorkShopPowerService.checkEmpIdExistence(Emp_id)) {
				if(iOWorkShopPowerService.checkUserNameDuplicate(Emp_id,WorkshopNo,accessRole)){
					checkResult.addProperty("StatusCode", "200");
					checkResult.addProperty("Message", "此工號未設置臨時權限，可以新增此賬號!");
				}else{
					checkResult.addProperty("StatusCode", "500");
					checkResult.addProperty("Message","工號"+Emp_id+ "已設置車間"+WorkshopNo+"的臨時權限，請更改賬號！");
				}	
			}else {
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "無工號為:"+Emp_id+"的信息，請更改賬號！");
			}
			
		}
		catch(Exception ex){
			logger.error("Check new Account info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查工號是否置臨時權限，原因："+ex.toString());
		}
		/*System.out.println(checkResult.toString());*/
		return checkResult.toString();
	}
	//判斷同一卡號和車間是否有數據
		@RequestMapping(value="/checkCardId.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody 
		public String checkCardIdDuplicate(HttpSession session,@RequestParam("CardId")String CardId,@RequestParam("WorkshopNo")String WorkshopNo){
			JsonObject checkResult=new JsonObject();	
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				iOWorkShopPowerService = (IOWorkShopPowerService) context.getBean("iOWorkShopPowerService");
				String accessRole=(String) session.getAttribute("accessRole");
				System.out.println("進入查詢同一卡號=======================>>>>>>>>>>");
					if(iOWorkShopPowerService.checkCardIdDuplicate(CardId,WorkshopNo,accessRole)){
						checkResult.addProperty("StatusCode", "200");
						checkResult.addProperty("Message", "此卡號未設置臨時權限，可以新增此賬號!");
					}else{
						checkResult.addProperty("StatusCode", "500");
						checkResult.addProperty("Message","卡號"+CardId+ "已設置車間"+WorkshopNo+"的臨時權限，請更改賬號！");
					}	
			
				
			}
			catch(Exception ex){
				logger.error("Check new Account info is failed, due to: ",ex);
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "檢查卡號是否置臨時權限，原因："+ex.toString());
			}
			/*System.out.println(checkResult.toString());*/
			return checkResult.toString();
		}
	//創建員工車間臨時權限
	@RequestMapping(value="/AddIOWorkShopPW.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String IOWorkShopPW(HttpSession session,@RequestBody IOWorkShopPW[] ioWorkShopPW ){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			System.out.println("數據信息===========>>>>>>"+ioWorkShopPW);
			iOWorkShopPowerService = (IOWorkShopPowerService) context.getBean("iOWorkShopPowerService");
			String accessRole=(String) session.getAttribute("accessRole");
			if(iOWorkShopPowerService.addIOWorkShopPW(ioWorkShopPW,updateUser,accessRole)){
				for (int i = 0; i < ioWorkShopPW.length; i++) {
					AddResult.addProperty("StatusCode", "200");
					AddResult.addProperty("Message", "工號"+ioWorkShopPW[i].getEmp_id()+"車間為"+ioWorkShopPW[i].getWorkShopNo()+"進出臨時權限設置成功");
				}
				
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "車間進出臨時權限設置失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new IOWsPw info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "車間進出臨時權限設置發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	//創建廠商和臺干車間臨時權限
	@RequestMapping(value="/AddIOWorkShopPWOther.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String IOWorkShopPWOther(HttpSession session,@RequestBody IOWorkShopPW[] ioWorkShopPW){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
//			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			iOWorkShopPowerService = (IOWorkShopPowerService) context.getBean("iOWorkShopPowerService");
			String accessRole=(String) session.getAttribute("accessRole");
			if(iOWorkShopPowerService.addIOWorkShopPWOther(ioWorkShopPW,updateUser,accessRole)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "車間進出臨時權限設置成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "車間進出臨時權限設置失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new IOWsPw info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "車間進出臨時權限設置發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	@RequestMapping(value="/UpdateIOWorkShopPW.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String UpdateIOWorkShopPW(HttpSession session,@RequestBody IOWorkShopPW ioWorkShopPW){
		JsonObject UpdateResult=new JsonObject();		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			iOWorkShopPowerService = (IOWorkShopPowerService) context.getBean("iOWorkShopPowerService");
			String updateUser=(String) session.getAttribute("username");
			String accessRole=(String) session.getAttribute("accessRole");
			System.out.println("数据信息============="+ioWorkShopPW);
			if(iOWorkShopPowerService.UpdateRecord(ioWorkShopPW,updateUser,accessRole)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新臨時權限成功");
			}
			else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新臨時權限失敗");
			}
		}
		catch(Exception ex){
			logger.error("Updating the Account info is failed, due to: ",ex);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新臨時權限發生錯誤，原因："+ex.toString());
		}
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/deleteIOWorkShopPW.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeleteIOWorkShopPW(HttpSession session,@RequestParam("Emp_id")String Emp_id,@RequestParam("CardID")String CardID,@RequestParam("WorkShopNo")String WorkShopNo){
		JsonObject DisableResult=new JsonObject();
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			iOWorkShopPowerService = (IOWorkShopPowerService) context.getBean("iOWorkShopPowerService");
			String updateUser=(String) session.getAttribute("username");
			String accessRole=(String) session.getAttribute("accessRole");
			if(iOWorkShopPowerService.DeleteIOWorkShopPW(Emp_id, updateUser,CardID,WorkShopNo,accessRole)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "車間進出臨時權限已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "車間進出臨時權限發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the IOCardMaIP info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "車間進出臨時權限發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
}	
