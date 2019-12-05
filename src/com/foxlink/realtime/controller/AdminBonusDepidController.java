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

import com.foxlink.realtime.model.BonusDeptid;
import com.foxlink.realtime.model.ExceptionCost;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.AdminBonusDepidService;
import com.foxlink.realtime.service.ExceptionCostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



@Controller
@RequestMapping("/AdminBonusDepid")
public class AdminBonusDepidController {
	private static Logger logger=Logger.getLogger(AdminBonusDepidController.class);
	private AdminBonusDepidService adminBonusDepidService;
	
	@RequestMapping(value="/ShowAdminBonusDepid",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "AdminBonusDeptid";
	}
	
	@RequestMapping(value="/ShowBonusDepidList",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String BonusDepidList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
			adminBonusDepidService = (AdminBonusDepidService)context.getBean("adminBonusDepidService");
			String accessRole=(String) session.getAttribute("accessRole");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = adminBonusDepidService.getFindBonusPage(currentPage,queryCritirea, queryParam);
			page.setList(adminBonusDepidService.FindQueryBonus(currentPage, queryCritirea,queryParam));
			/*System.out.println(gson.toJson(page));*/
			System.out.println(gson.toJson(page));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得頂崗津貼信息資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
		
	}
	
	@RequestMapping(value="/RelieveBonusDeptid",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String RelieveBonusDeptid(HttpSession session,@RequestBody BonusDeptid[] bonusDeptid) {
		JsonObject DisableResult=new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			adminBonusDepidService = (AdminBonusDepidService) context.getBean("adminBonusDepidService");
			String accessRole=(String) session.getAttribute("accessRole");
			if(adminBonusDepidService.RelieveBonusDeptid(bonusDeptid, updateUser,accessRole)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "此組別代碼的頂崗津貼已失效");
			}else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "解除此組別代碼的頂崗津貼發生錯誤");
			}
			
		}
		catch(Exception ex){
			logger.error("Disable the BonusDeptid info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "解除此組別代碼的頂崗津貼已發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	@RequestMapping(value="/UpdateBonusAllowed",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String UpdateBonusAllowed(HttpSession session,@RequestBody BonusDeptid bonusDeptid) {
		JsonObject UpdateResult=new JsonObject();		
	try	{
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		String updateUser = (String)session.getAttribute("username");
		adminBonusDepidService = (AdminBonusDepidService) context.getBean("adminBonusDepidService");
		String accessRole=(String) session.getAttribute("accessRole");
		if(adminBonusDepidService.UpdateBonus(bonusDeptid)){
			UpdateResult.addProperty("StatusCode", "200");
			UpdateResult.addProperty("Message", "開通修改頂崗津貼權限成功");
		}
		else{
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "開通修改頂崗津貼權限失敗");
		}
	}
	catch(Exception ex){
		logger.error("Updating the BonusAllowed info is failed, due to: ",ex);
		UpdateResult.addProperty("StatusCode", "500");
		UpdateResult.addProperty("Message", "開通修改頂崗津貼權限發生錯誤，原因："+ex.toString());
	}
	return UpdateResult.toString();
	}
	
	@RequestMapping(value="/AddBonusDeptid.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String AddBonusDeptid(HttpSession session,@RequestParam("Deptid")String Deptid,@RequestParam("Bonus_Rule")String Bonus_Rule){	

			String updateUser=(String) session.getAttribute("username");
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminBonusDepidService = (AdminBonusDepidService) context.getBean("adminBonusDepidService");
			String accessRole=(String) session.getAttribute("accessRole");
			

			return adminBonusDepidService.addBonusDeptid(Deptid,Bonus_Rule);
	}
	@RequestMapping(value="/checkBonusByDeptid.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkBonusByDeptid(HttpSession session,@RequestParam("Deptid")String Deptid){
		JsonObject checkResult=new JsonObject();	
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			adminBonusDepidService = (AdminBonusDepidService) context.getBean("adminBonusDepidService");
			String accessRole=(String) session.getAttribute("accessRole");
			if(adminBonusDepidService.checkBonusByDeptid(Deptid)){
				checkResult.addProperty("StatusCode", "200");
				checkResult.addProperty("Message", "此部門頂崗津貼信息已存在！");
			}
			else{
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "未綁定此部門頂崗津貼信息!");
			}
		}
		catch(Exception ex){
			logger.error("Check BonusByDeptid info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "查詢此部門是否綁定頂崗津貼信息發生錯誤，原因："+ex.toString());
		}
		return checkResult.toString();
	}
}
