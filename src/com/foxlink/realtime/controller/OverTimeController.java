package com.foxlink.realtime.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.EmpInOTIdentifiedSheet;
import com.foxlink.realtime.model.EmpInOTPendingSheet;
import com.foxlink.realtime.model.Holiday;
import com.foxlink.realtime.model.OTHourConfirm;
import com.foxlink.realtime.model.OverTimeSheet;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.HolidayService;
import com.foxlink.realtime.service.OTIdentifiedService;
import com.foxlink.realtime.service.OTPendingService;
import com.foxlink.realtime.service.OTService;
import com.foxlink.realtime.util.CommonUtils;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
//@Scope("session")
@RequestMapping("/Overtime")
public class OverTimeController {
	private static Logger logger = Logger.getLogger(OverTimeController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	/*顯示加班審核頁面*/
	@RequestMapping(value="/OT.show",method=RequestMethod.GET)
	public String ShowOverTime() {
		return "OverTime";
	}
	
	/*顯示已審核的名單列表*/
	@RequestMapping(value="/Identified.show",method=RequestMethod.GET)
	public String ShowOverTimeIdentified() {
		return "OverTimeIdentified";
	}
	
	/*顯示未審核的名單列表*/
	@RequestMapping(value="/Pending.show",method=RequestMethod.GET)
	public String ShowOverTimePending() {
		return "OverTimePending";
	}
	
	/*顯示未審核的名單列表*/
	@RequestMapping(value="/PendingByDepid.show",method=RequestMethod.GET)
	public String ShowOverTimePendingByDepid() {
		return "OverTimePendingByDepid";
	}
	
	/*顯示已審核的名單列表*/
	@RequestMapping(value="/IdentifiedByDepid.show",method=RequestMethod.GET)
	public String ShowOverTimeIdentifiedByDepid() {
		return "OverTimeIdentifiedByDepid";
	}
	
	/*顯示未審核的名單列表*/
	@RequestMapping(value="/OTByLL.show",method=RequestMethod.GET)
	public String ShowOverTimeByLL() {
		return "OverTimeByDepid";
	}
	
	/*列出該線長可審批的加班單*/
	@RequestMapping(value="/AllOTSheetsByDepid.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindAllOTSheetsByDepid(HttpSession session,@RequestParam(value="RCNO")String RC_NO,
			@RequestParam(value="CheckState")String CheckState,
			@RequestParam(value="WorkshopNO")String WorkShopNo,
			@RequestParam(value="LineNo")String LineNo,
			@RequestParam(value="StartDate")String StartDate,
			@RequestParam(value="EndDate")String EndDate,
			@RequestParam(value="IsAbnormal")int isAbnormal) {
		String AllOTSheets="";
		OTService otService=null;
		boolean IsAbnormal=false;
		try {
			if (isAbnormal==1)
				IsAbnormal=true;
			else
				IsAbnormal=false;
			
			String AssistantAccount=(String) session.getAttribute("username");
			
			otService=(OTService)context.getBean("OTService");		
			int[] CheckStateArray = Arrays.stream(CheckState.split(":")).mapToInt(Integer::parseInt).toArray();
			List<OverTimeSheet> OTSheetLists=otService.FindAllOTSheetsByDepid(CheckStateArray, RC_NO, WorkShopNo,LineNo, 
					new CommonUtils().ConvertString2SQLDate(StartDate), 
					new CommonUtils().ConvertString2SQLDate(EndDate), AssistantAccount,IsAbnormal);
			if(OTSheetLists!=null){
				if(OTSheetLists.size()>0) {
					Gson gson = new GsonBuilder().serializeNulls().create();
					AllOTSheets=gson.toJson(OTSheetLists);
				}
				else {
					AllOTSheets=new CommonUtils().ProduceNoDataFoundJsonString("加班單");
				}
			}else{
				AllOTSheets=new CommonUtils().ProduceNoDataFoundJsonString("加班單");
			}
		}
		catch(Exception ex) {
			logger.error("FindAllOTSheets is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取得人員加班列表失敗，原因:"+ex.toString());
			AllOTSheets=error.toString();
		}
		return AllOTSheets;
	}
	
	/*列出該人員可審批的加班單*/
	@RequestMapping(value="/AllOTSheets.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindAllOTSheets(HttpSession session,@RequestParam(value="RCNO")String RC_NO,
			@RequestParam(value="CheckState")String CheckState,
			@RequestParam(value="WorkshopNO")String WorkShopNo,
			@RequestParam(value="LineNo")String LineNo,
			@RequestParam(value="StartDate")String StartDate,
			@RequestParam(value="EndDate")String EndDate,
			@RequestParam(value="IsAbnormal")int isAbnormal) {
		String AllOTSheets="";
		OTService otService=null;
		boolean IsAbnormal=false;
		try {
			if (isAbnormal==1)
				IsAbnormal=true;
			else
				IsAbnormal=false;
			
			String AssistantAccount=(String) session.getAttribute("username");
			
			otService=(OTService)context.getBean("OTService");		
			int[] CheckStateArray = Arrays.stream(CheckState.split(":")).mapToInt(Integer::parseInt).toArray();
			List<OverTimeSheet> OTSheetLists=otService.FindAllOTSheets(CheckStateArray, RC_NO, WorkShopNo,LineNo, 
					new CommonUtils().ConvertString2SQLDate(StartDate), 
					new CommonUtils().ConvertString2SQLDate(EndDate), AssistantAccount,IsAbnormal);
			if(OTSheetLists.size()>0) {
				Gson gson = new GsonBuilder().serializeNulls().create();
				AllOTSheets=gson.toJson(OTSheetLists);
			}
			else {
				AllOTSheets=new CommonUtils().ProduceNoDataFoundJsonString("加班單");
			}
		}
		catch(Exception ex) {
			logger.error("FindAllOTSheets is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取得人員加班列表失敗，原因:"+ex.toString());
			AllOTSheets=error.toString();
		}
		return AllOTSheets;
	}
	
	/*列出加班員工詳細資訊（上、下刷時間、班別）*/
	@RequestMapping(value="/EmpsInOTSheet.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindEmpsInOTSheet(HttpSession session,@RequestParam(value="WorkshopNo")String WorkshopNo,
			@RequestParam(value="LineNo")String LineNo,
			@RequestParam(value="RCNO")String RCNO,
			@RequestParam(value="ClassNo")String ClassNo,
			@RequestParam(value="OverTimeDate")String OTDate,
			@RequestParam(value="IsIdentified")int IsIdentified,
			@RequestParam(value="IsAbnormal")int isAbnormal) {
		String EmpsInOTSheet="";
		boolean IsAbnormal=false;
		try {
			if(RCNO.isEmpty())
				RCNO=null;
			if (isAbnormal==1)
				IsAbnormal=true;
			else
				IsAbnormal=false;
			
			String AssistantAccount=(String) session.getAttribute("username");
						
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(IsIdentified==1) {
				/*顯示已審核的人員列表*/
				OTIdentifiedService OTidentificedService=(OTIdentifiedService) context.getBean("EmpOTIdentifiedService");
				List<EmpInOTIdentifiedSheet> empInIdentifiedOTSheet=OTidentificedService.
						FindAllRecords(WorkshopNo,LineNo, RCNO, AssistantAccount, ClassNo, OTDate,IsAbnormal);
				EmpsInOTSheet=gson.toJson(empInIdentifiedOTSheet);
			}
			else {
				/*顯示未審核的人員列表*/
				OTPendingService OTpendingService=(OTPendingService)context.getBean("EmpOTPendingService");
				List<EmpInOTPendingSheet> empPendingOTSheet=OTpendingService.FindAllRecords(WorkshopNo,LineNo, RCNO, AssistantAccount, ClassNo, OTDate,IsAbnormal);
				EmpsInOTSheet=gson.toJson(empPendingOTSheet);
			}
		}
		catch(Exception ex) {
			logger.error("FindEmpsInOTSheet is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取得人員加班資訊失敗，原因:"+ex.toString());
			EmpsInOTSheet=error.toString();
		}
		return EmpsInOTSheet;
	}
	
	/*列出加班員工詳細資訊（上、下刷時間、班別）*/
	@RequestMapping(value="/EmpsInOTSheetByDepid.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String FindEmpsInOTSheetByDepid(HttpSession session,@RequestParam(value="WorkshopNo")String WorkshopNo,
			@RequestParam(value="LineNo")String LineNo,
			@RequestParam(value="RCNO")String RCNO,
			@RequestParam(value="ClassNo")String ClassNo,
			@RequestParam(value="OverTimeDate")String OTDate,
			@RequestParam(value="IsIdentified")int IsIdentified,
			@RequestParam(value="IsAbnormal")int isAbnormal) {
		String EmpsInOTSheet="";
		boolean IsAbnormal=false;
		try {
			if(RCNO.isEmpty())
				RCNO=null;
			if (isAbnormal==1)
				IsAbnormal=true;
			else
				IsAbnormal=false;
			
			String AssistantAccount=(String) session.getAttribute("username");
						
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(IsIdentified==1) {
				/*顯示已審核的人員列表*/
				OTIdentifiedService OTidentificedService=(OTIdentifiedService) context.getBean("EmpOTIdentifiedService");
				List<EmpInOTIdentifiedSheet> empInIdentifiedOTSheet=OTidentificedService.
						FindAllRecordsByDepid(WorkshopNo,LineNo, RCNO, AssistantAccount, ClassNo, OTDate,IsAbnormal);
				EmpsInOTSheet=gson.toJson(empInIdentifiedOTSheet);
			}
			else {
				/*顯示未審核的人員列表*/
				OTPendingService OTpendingService=(OTPendingService)context.getBean("EmpOTPendingService");
				List<EmpInOTPendingSheet> empPendingOTSheet=OTpendingService.FindAllRecordsByDepid(WorkshopNo,LineNo, RCNO, AssistantAccount, ClassNo, OTDate,IsAbnormal);
				EmpsInOTSheet=gson.toJson(empPendingOTSheet);
			}
		}
		catch(Exception ex) {
			logger.error("FindEmpsInOTSheetByDepid is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "取得人員加班資訊失敗，原因:"+ex.toString());
			EmpsInOTSheet=error.toString();
		}
		return EmpsInOTSheet;
	}
	
	
	/*列出加班員工詳細時間和加班計算起始時間*/
	@RequestMapping(value="/GetCalOverTime.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String  GetCalOverTime(HttpSession session,
			@RequestParam(value="WorkshopNo")String WorkshopNo,
			@RequestParam(value="LineNo")String LineNo,
			@RequestParam(value="RCNO")String RCNO,
			@RequestParam(value="ClassNo")String ClassNo,
			@RequestParam(value="CheckState")int CheckState,
			@RequestParam(value="OverTimeDate")String OverTimeDate,
			@RequestParam(value="OverTimeType")int OverTimeType,			
			@RequestParam(value="ItemNumber")String ItemNumber,
			@RequestParam(value="IsAbnormal")int IsAbnormal) {
		String EmpsInOTSheet="";
		try {
			if(RCNO.isEmpty())
				RCNO=null;
			
			String AssistantAccount=(String) session.getAttribute("username");//取得登錄賬號
			String AssistantId=(String) session.getAttribute("assistantId");//取得登錄賬號
			
			Gson gson = new GsonBuilder().serializeNulls().create();			
				/*计算未審核的人員列表的加班时间*/
				OTPendingService OTpendingService=(OTPendingService)context.getBean("EmpOTPendingService");
				List<EmpInOTPendingSheet> empPendingOTSheet=OTpendingService.GetCalOverTime(WorkshopNo,LineNo, RCNO, AssistantAccount,AssistantId, ClassNo, CheckState,OverTimeDate,OverTimeType,ItemNumber,IsAbnormal);
				EmpsInOTSheet=gson.toJson(empPendingOTSheet);
		}
		catch(Exception ex) {
			logger.error("计算未審核的人員列表的加班时间 is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "计算未審核的人員列表的加班时间失敗，原因:"+ex.toString());
			EmpsInOTSheet=error.toString();
		}
		return EmpsInOTSheet;
	}
	
	/*列出加班員工詳細時間和加班計算起始時間*/
	@RequestMapping(value="/GetCalOverTimeByDepid.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String  GetCalOverTimeByDepid(HttpSession session,
			@RequestParam(value="WorkshopNo")String WorkshopNo,
			@RequestParam(value="LineNo")String LineNo,
			@RequestParam(value="RCNO")String RCNO,
			@RequestParam(value="ClassNo")String ClassNo,
			@RequestParam(value="CheckState")int CheckState,
			@RequestParam(value="OverTimeDate")String OverTimeDate,
			@RequestParam(value="OverTimeType")int OverTimeType,			
			@RequestParam(value="ItemNumber")String ItemNumber,
			@RequestParam(value="IsAbnormal")int IsAbnormal) {
		String EmpsInOTSheet="";
		try {
			if(RCNO.isEmpty())
				RCNO=null;
			
			String AssistantAccount=(String) session.getAttribute("username");//取得登錄賬號
			String AssistantId=(String) session.getAttribute("assistantId");//取得登錄賬號
			
			Gson gson = new GsonBuilder().serializeNulls().create();			
				/*计算未審核的人員列表的加班时间*/
				OTPendingService OTpendingService=(OTPendingService)context.getBean("EmpOTPendingService");
				List<EmpInOTPendingSheet> empPendingOTSheet=OTpendingService.GetCalOverTimeByDepid(WorkshopNo,LineNo, RCNO, AssistantAccount,AssistantId, ClassNo, CheckState,OverTimeDate,OverTimeType,ItemNumber,IsAbnormal);
				EmpsInOTSheet=gson.toJson(empPendingOTSheet);
		}
		catch(Exception ex) {
			logger.error("计算未審核的人員列表的加班时间 is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "计算未審核的人員列表的加班时间失敗，原因:"+ex.toString());
			EmpsInOTSheet=error.toString();
		}
		return EmpsInOTSheet;
	}
	
	
	/*送出加班審核單*/
	@RequestMapping(value="/PendingOT.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SubmitPendingOT(HttpSession session,@RequestParam(value="IsAbnormal")int IsAbnormal,
			@RequestParam(value="OT_Hour_Confirm")String otHourConfirm) {
		JsonObject executeResult=new JsonObject();
		OTService otService=null;
		try {
			String AssistantAccount=(String) session.getAttribute("username");//取得登錄賬號
			String AssistantId=(String) session.getAttribute("assistantId");//取得登錄賬號
			
			otService=(OTService)context.getBean("OTService");
			//Call Store Procedure 處理加班單審核
			Gson g=new Gson();
			OTHourConfirm otHourConfirmObj=g.fromJson(otHourConfirm, OTHourConfirm.class);
			otHourConfirmObj.setAssistantID(AssistantId);
			otHourConfirmObj.setAssistantAccount(AssistantAccount);
			if(otService.ConfirmEmpsOTHours(IsAbnormal, otHourConfirmObj)) {
				executeResult.addProperty("ErrorCode", 200);
				executeResult.addProperty("ErrorMsg", "加班時數提交成功");
			}
			else {
				executeResult.addProperty("ErrorCode", 500);
				executeResult.addProperty("ErrorMsg", "加班時數提交失敗");
			}
		}
		catch(Exception ex) {
			logger.error("SubmitPendingOT is failed",ex);
			executeResult.addProperty("ErrorCode", 500);
			executeResult.addProperty("ErrorMsg", "加班時數提交失敗，發生以下錯誤： "+ex.toString());
		}
		return executeResult.toString();
	}
	
	/*送出加班審核單*/
	@RequestMapping(value="/PendingOTByDepid.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SubmitPendingOTByDepid(HttpSession session,@RequestParam(value="IsAbnormal")int IsAbnormal,
			@RequestParam(value="OT_Hour_Confirm")String otHourConfirm) {
		JsonObject executeResult=new JsonObject();
		OTService otService=null;
		try {
			String AssistantAccount=(String) session.getAttribute("username");//取得登錄賬號
			String AssistantId=(String) session.getAttribute("assistantId");//取得登錄賬號
			
			otService=(OTService)context.getBean("OTService");
			//Call Store Procedure 處理加班單審核
			Gson g=new Gson();
			OTHourConfirm otHourConfirmObj=g.fromJson(otHourConfirm, OTHourConfirm.class);
			otHourConfirmObj.setAssistantID(AssistantId);
			otHourConfirmObj.setAssistantAccount(AssistantAccount);
			if(otService.ConfirmEmpsOTHoursByDepid(IsAbnormal, otHourConfirmObj)) {
				executeResult.addProperty("ErrorCode", 200);
				executeResult.addProperty("ErrorMsg", "加班時數提交成功");
			}
			else {
				executeResult.addProperty("ErrorCode", 500);
				executeResult.addProperty("ErrorMsg", "加班時數提交失敗");
			}
		}
		catch(Exception ex) {
			logger.error("SubmitPendingOT is failed",ex);
			executeResult.addProperty("ErrorCode", 500);
			executeResult.addProperty("ErrorMsg", "加班時數提交失敗，發生以下錯誤： "+ex.toString());
		}
		return executeResult.toString();
	}
	
	/*獲取節假日信息，節假日需要自己維護*/
	@RequestMapping(value="/checkHoliday.show",method=RequestMethod.GET,produces="Application/json;charset=utf-8")
	public @ResponseBody String checkHoliday(@RequestParam(value="OverTimeDate")String OverTimeDate){
		String jsonResults="";		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			HolidayService holidayService = (HolidayService) context.getBean("holidayService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<Holiday> holiday = holidayService.FindRecord(new Holiday(OverTimeDate));
			jsonResults = gson.toJson(holiday);
		}
		catch(Exception ex){
			logger.error("checkHoliday is failed",ex);
			JsonObject error=new JsonObject();
			error.addProperty("ErrorCode", 500);
			error.addProperty("ErrorMsg", "檢測報加班日期是否為假日日期發生錯誤，原因:"+ex.toString());
			jsonResults = error.toString();
		}
		return jsonResults;
	}
	
}