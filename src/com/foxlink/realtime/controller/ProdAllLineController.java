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

import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.ProdAllLine;
import com.foxlink.realtime.service.AbReasonReplyService;
import com.foxlink.realtime.service.IOWorkShopRecordService;
import com.foxlink.realtime.service.OTCardbdPersonService;
import com.foxlink.realtime.service.ProdAllLineService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/ProdAllLine")
public class ProdAllLineController {
	private static Logger logger=Logger.getLogger(ProdAllLineController.class);
	private ProdAllLineService prodAllLineService;
	
	@RequestMapping(value="/ShowProdAllLine",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "ProdAllLine";
	}
	
	@RequestMapping(value = "/SupportMachineNum", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SupportMachineNum(HttpSession session,@RequestParam("costId")String costId,@RequestParam("SDate")String SDate){
		
		
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				String accessRole=(String) session.getAttribute("accessRole");
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				prodAllLineService = (ProdAllLineService) context.getBean("prodAllLineService");
			
				return prodAllLineService.SupportMachineNum(costId,SDate);
	}
	
	@RequestMapping(value = "/SupportMachineNumDetail", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SupportMachineNumDetail(HttpSession session,@RequestParam("costId")String costId,@RequestParam("SDate")String SDate){
		
		
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				String accessRole=(String) session.getAttribute("accessRole");
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				prodAllLineService = (ProdAllLineService) context.getBean("prodAllLineService");
			
				return prodAllLineService.SupportMachineNumDetail(costId,SDate);
	}
	
	@RequestMapping(value = "/SupportMachine.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String SupportMachine(HttpSession session,@RequestParam("costId")String costId,@RequestParam("SDate")String SDate){
		
		
				String userDataCostId=(String) session.getAttribute("userDataCostId");
				String accessRole=(String) session.getAttribute("accessRole");
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				prodAllLineService = (ProdAllLineService) context.getBean("prodAllLineService");
			
				return prodAllLineService.SupportMachine(costId,SDate);
	}
	
	@RequestMapping(value="/DelSupportMachine",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DelSupportMachine(HttpSession session,@RequestParam("recordid")String recordid) {
		JsonObject DisableResult=new JsonObject();
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
//			String updateUser = (String)session.getAttribute("username");
			prodAllLineService = (ProdAllLineService) context.getBean("prodAllLineService");
			if(prodAllLineService.DelSupportMachine(recordid)){
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "異動人數刪除成功");	
			}else {
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "異動人數刪除發生錯誤");
			}
			
		}
		catch(Exception ex){
		logger.error("Disable the SupportMachine info is failed, due to:",ex);
		DisableResult.addProperty("StatusCode", "500");
		DisableResult.addProperty("Message", "異動人數刪除已發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
	
	@RequestMapping(value="/AddSupportMachine",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody
	public String AddSupportMachine(HttpSession session,@RequestBody ProdAllLine prodAllLine) {

		
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
//			String updateUser = (String)session.getAttribute("username");
			prodAllLineService = (ProdAllLineService) context.getBean("prodAllLineService");
				
			return prodAllLineService.AddSupportMachine(prodAllLine);
	}
}
