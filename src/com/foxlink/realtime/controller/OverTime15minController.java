package com.foxlink.realtime.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.EmpOT15min;
import com.foxlink.realtime.service.OT15minService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class OverTime15minController {
	private static Logger logger = Logger.getLogger(OverTime15minController.class);
	private OT15minService ot15minService;
	
	@RequestMapping(value = "/ShowCheckOverTime15min", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "CheckOverTime15min";
	}
	
	//查詢總記錄
		@RequestMapping(value = "/CheckOT15minJsonAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
		public @ResponseBody String queryEmps15minJsonAll(HttpSession session,@ModelAttribute EmpOT15min queryOT15min) {
			// ApplicationContext applicationContext = new
			// ClassPathXmlAppltionContext("Beans.xml");
			// QueryService queryService = (QueryService)
			// applicationContext.getBean("queryService");
			String jsonResults = "";
			try {
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				if(userDataCostId!=""&&userDataCostId!=null){
				System.out.println(queryOT15min);
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				ot15minService = (OT15minService) context.getBean("OT15minService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				List<EmpOT15min> list=ot15minService.FindQueryRecords(userDataCostId,queryOT15min);
				jsonResults = gson.toJson(list);
			}else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢上下班刷卡超15分鐘的權限！");
				jsonResults=costIdJson.toString();
			}} catch (Exception ex) {
				logger.error("FindEmpOTStatus falid", ex);
				JsonObject error = new JsonObject();
				error.addProperty("ErrorCode", 500);
				error.addProperty("ErrorMessage", "查詢上下班刷卡超15分鐘失敗，原因:" + ex.toString());
				jsonResults = error.toString();
			}
			return jsonResults;
		}
		/*mysql的歷史上下班刷卡記錄*/
		@RequestMapping(value = "/CheckOT15minHistoryJsonAll.show", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
		public @ResponseBody String queryEmps15minJsonAllHistory(HttpSession session,@ModelAttribute EmpOT15min queryOT15min) {
			// ApplicationContext applicationContext = new
			// ClassPathXmlAppltionContext("Beans.xml");
			// QueryService queryService = (QueryService)
			// applicationContext.getBean("queryService");
			String jsonResults = "";
			String timeStart = queryOT15min.getTimeStart();
			String timeEnd = queryOT15min.getTimeEnd();
			SimpleDateFormat sdf =new SimpleDateFormat("yyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			List<EmpOT15min> allList = new ArrayList<EmpOT15min>();
			try {
				Date ds = sdf.parse(timeStart);
				Date de = sdf.parse(timeEnd);
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				if(userDataCostId!=""&&userDataCostId!=null){
					System.out.println(queryOT15min);
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				ot15minService = (OT15minService) context.getBean("OT15minService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				while(de.compareTo(ds)>=0){
					System.out.println(1);
					System.out.println(queryOT15min.getTimeStart());
					queryOT15min.setTimeStart(sdf.format(ds));
					cal.setTime(ds);
					cal.add(Calendar.DAY_OF_YEAR, 1);
					ds= cal.getTime();
					List<EmpOT15min> list=ot15minService.FindQueryRecordsHistory(userDataCostId,queryOT15min);
					if(list!=null){
						allList.addAll(list);
					}
				}
				jsonResults = gson.toJson(allList);
			}else{
				JsonObject costIdJson=new JsonObject();
				costIdJson.addProperty("ErrorCode", "500");
				costIdJson.addProperty("ErrorMessage", "该賬號沒有查詢上下班刷卡超15分鐘的權限！");
				jsonResults=costIdJson.toString();
			}} catch (Exception ex) {
				logger.error("FindEmpOTStatus falid", ex);
				JsonObject error = new JsonObject();
				error.addProperty("ErrorCode", 500);
				error.addProperty("ErrorMessage", "查詢上下班刷卡超15分鐘失敗，原因:" + ex.toString());
				jsonResults = error.toString();
			}
			return jsonResults;
		}
}
