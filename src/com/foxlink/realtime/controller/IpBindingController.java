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
import com.foxlink.realtime.model.IpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.IOCardbdIPService;
import com.foxlink.realtime.service.IpBindingService;
import com.foxlink.realtime.service.OTCardbdPersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@Controller
@RequestMapping("/IpBinding")
public class IpBindingController {
	private static Logger logger =Logger.getLogger(IpBindingController.class);
	private IpBindingService ipBindingService;
	///IpBinding/ShowIpBinding
	@RequestMapping(value = "/ShowIpBinding", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "IpBinding";
	}
	
	//電腦Ip綁定
	@RequestMapping(value="/BindingIp",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowProjectName(HttpSession session,@RequestParam(value="DeviceIp")String DeviceIp,@RequestParam(value="DeptId")String DeptId,@RequestParam(value="Dif")Boolean isDif){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		ipBindingService = (IpBindingService) context.getBean("ipBindingService");
		String updateUser=(String) session.getAttribute("username");
		
		return ipBindingService.BindingIp(DeviceIp,DeptId,updateUser,isDif);
	} 
	
//	//電腦Ip綁定
//		@RequestMapping(value="/ShowAllIpList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
//		@ResponseBody
//		public String ShowAllIpList(){
//			
//			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
//			ipBindingService = (IpBindingService) context.getBean("ipBindingService");
//			return ipBindingService.ShowAllIpList();
//		}
		
		//顯示查詢的Ip信息
		@RequestMapping(value="/ShowSelectIpList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
		@ResponseBody
		public String ShowSelectAllIpList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
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
				ipBindingService = (IpBindingService)context.getBean("ipBindingService");
				Gson gson = new GsonBuilder().serializeNulls().create();
				Page page = ipBindingService.getPersonPage(currentPage,queryCritirea,queryParam,userDataCostId);				
				page.setList(ipBindingService.FindQueryRecord(currentPage, queryCritirea,queryParam,userDataCostId));
				JsonResult = gson.toJson(page);
				System.out.println("分頁顯示工號========="+gson.toJson(page).toString());
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e);
				JsonObject exception=new JsonObject();
				exception.addProperty("StatusCode", "500");
				exception.addProperty("ErrorMessage", "取得電腦Ip信息列表列表失敗，原因："+e.toString());
				JsonResult=exception.toString();
				
			}
			return JsonResult;
		}
		//更新
		//UpdateBindingIP.do
		@RequestMapping(value="/UpdateBindingIP.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
		@ResponseBody
		public String UpdateIOCardMaIP(HttpSession session,@RequestParam("DeviceIp")String DeviceIp,@RequestParam("DeptID")String DeptID,@RequestParam("OldDeptID")String OldDeptID){
			JsonObject UpdateResult=new JsonObject();		
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				ipBindingService = (IpBindingService) context.getBean("ipBindingService");
				String updateUser=(String) session.getAttribute("username");
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				System.out.println("電腦ip=============>"+DeviceIp);
				if(ipBindingService.UpdateRecord(DeviceIp,DeptID,updateUser,OldDeptID,userDataCostId)){
					UpdateResult.addProperty("StatusCode", "200");
					UpdateResult.addProperty("Message", "更新成功");
				}
				else{
					UpdateResult.addProperty("StatusCode", "500");
					UpdateResult.addProperty("Message", "更新失敗,部門代碼已存在或沒有該部門報加班權限");
				}
			}
			catch(Exception ex){
				logger.error("Updating the Account info is failed, due to: ",ex);
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新發生錯誤，原因："+ex.toString());
			}
			
			return UpdateResult.toString();
		}
		//刪除
		@RequestMapping(value="/deleteBindingIP.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
		@ResponseBody
		public String DeleteIOCardMaIP(HttpSession session,@RequestParam("Deviceip")String Deviceip,@RequestParam("DeptId")String DeptId){
			JsonObject DisableResult=new JsonObject();
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				ipBindingService = (IpBindingService) context.getBean("ipBindingService");
				String updateUser=(String) session.getAttribute("username");
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				if(ipBindingService.DeleteIpBinding(Deviceip, updateUser,DeptId)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "電腦IP狀態已失效");
				}
				else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "電腦IP狀態發生錯誤");
				}
			}
			catch(Exception ex){
				logger.error("Disable the IOCardMaIP info is failed, due to:",ex);
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "電腦IP狀態發生錯誤，原因:"+ex.toString());
			}		
			return DisableResult.toString();
		}
		
		//顯示費用代碼
		@RequestMapping(value="/ShowCostNo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
		@ResponseBody
		public String ShowCostNo(HttpSession session) {
			String JsonResult = null;
			String userDataCostId= "ALL";//(String) session.getAttribute("userDataCostId");
			System.out.println("助理報加班的費用代碼=====>>"+userDataCostId);
			return userDataCostId;
		}
		//顯示部門代碼
		@RequestMapping(value = "/ShowDeptNo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	    @ResponseBody
	    public String ShowDeptNo(@RequestParam("CostId") String CostId) {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			ipBindingService = (IpBindingService) context.getBean("ipBindingService");
	        return ipBindingService.ShowDeptNo(CostId);
	    }
}
