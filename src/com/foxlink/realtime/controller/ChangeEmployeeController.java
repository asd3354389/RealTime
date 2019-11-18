package com.foxlink.realtime.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.ChangeEmployee;
import com.foxlink.realtime.model.EmpChange;
import com.foxlink.realtime.service.ChangeEmployeeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.sf.json.JSON;


@Controller
@RequestMapping("/ChangeEmployee")
public class ChangeEmployeeController {
	private static Logger logger = Logger.getLogger(ExceptionCostController.class);
	private ChangeEmployeeService changeEmployeeService;
	@RequestMapping(value = "/ShowChangeEmployee", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "ChangeEmployee";
	}
	
	//查詢是否有刷卡
	@RequestMapping(value="/ShowEmployeeList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String EmployeeList(HttpSession session,@RequestParam("startdate")String startdate,@RequestParam("empId")String empId) {
		String JsonResult = null;
		System.out.println(startdate+empId);
try {
		//QueryChangeEmployee
	        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	        changeEmployeeService = (ChangeEmployeeService)context.getBean("ChangeEmployeeService");
            String assistantId=(String) session.getAttribute("assistantId");
			
			//獲取登錄賬號的費用代碼
		System.out.println("登錄的工號"+assistantId);
			Gson gson = new GsonBuilder().serializeNulls().create();
			
			//查詢是否有刷卡時間
			List<ChangeEmployee> AllChange = changeEmployeeService.CheckSwipeCard(startdate,empId);
		if (AllChange ==null || AllChange.isEmpty()) {
			List<EmpChange> empList = changeEmployeeService.CheckEmp(empId);
			List<EmpChange> checkList = new ArrayList<>();
			EmpChange empChange = new EmpChange();
			System.out.println("數據條數"+empList.size());
			for (int i = 0; i < empList.size(); i++) {
				empChange = empList.get(i);
				}
			empChange.setNoCard("未刷卡");
			empChange.setStatus("500");
			checkList.add(empChange);
			JsonResult=gson.toJson(checkList);
			System.out.println("未刷卡"+JsonResult);
		   
		}else {
			List<ChangeEmployee> changeList = new ArrayList<>();
			ChangeEmployee changeEmployee = new ChangeEmployee();
			
			for (int i = 0; i < AllChange.size(); i++) {
				changeEmployee = AllChange.get(i);
				}
			changeEmployee.setStatus("200");
			changeList.add(changeEmployee);
			 JsonResult=gson.toJson(changeList);
			System.out.println("刷卡"+JsonResult);
			
		}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "更新狀態失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
		
	}
	
	//更新狀態
	@RequestMapping(value="/UpdateStatus",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String UpdateStatus(HttpSession session,@RequestParam("selcetValue")String selcetValue,@RequestParam("StatusValue")String StatusValue,@RequestParam("startdate")String startdate,@RequestParam("empId")String empId) {
		JsonObject UpdateResult=new JsonObject();
		try {
			//QueryChangeEmployee
		        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		        changeEmployeeService = (ChangeEmployeeService)context.getBean("ChangeEmployeeService");
				String accessRole=(String) session.getAttribute("accessRole");
				Gson gson = new GsonBuilder().serializeNulls().create();
				if (changeEmployeeService.updateStatus(selcetValue,StatusValue,startdate,empId)) {
					UpdateResult.addProperty("StatusCode", "200");
					UpdateResult.addProperty("Message", "更改員工狀態成功");
				} else {
					UpdateResult.addProperty("StatusCode", "500");
					UpdateResult.addProperty("Message", "更改員工狀態失敗");
				}
			
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("Updating the ExceptionCost info is failed, due to: ",e);
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更改員工狀態發生錯誤，原因："+e.toString());
			}
		return UpdateResult.toString();
	}
}
