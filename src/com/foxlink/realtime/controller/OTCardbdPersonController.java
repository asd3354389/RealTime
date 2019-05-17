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

import com.foxlink.realtime.model.Employee;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.FLinePersonMtService;
import com.foxlink.realtime.service.OTCardbdPersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@Controller
@RequestMapping("/OTCardPerson")
public class OTCardbdPersonController {
	private static Logger logger =Logger.getLogger(OTCardbdPersonController.class);
	private OTCardbdPersonService oTCardbdPersonService;
	
	@RequestMapping(value = "/ShowOTCardbdPerson", method = RequestMethod.GET)
	public String ShowAllAccountPage() {
		return "OTCardPersonbinding";
	}
	
	@RequestMapping(value="/ShowPersonList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String personList(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam) {
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
			String userDataCostId="ALL";
			oTCardbdPersonService = (OTCardbdPersonService)context.getBean("oTCardbdPersonService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = oTCardbdPersonService.getPersonPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(oTCardbdPersonService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			System.out.println(gson.toJson(page));
			JsonResult = gson.toJson(page);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得離崗卡綁定費用代碼資料列表列表失敗，原因："+e.toString());
			JsonResult=exception.toString();
		}
		return JsonResult;
	}
	
	/*@RequestMapping(value="/bdOutCard",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String OTCardbdPerson(@RequestBody OTCardBD[] otCardbd) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
		System.err.println("123");
		System.out.println(Emp.length);
		for(int i = 0 ;i<otCardbd.length;i++){
			System.out.println(otCardbd[i].getD_CardID());
		}		
		return oTCardbdPersonService.BDotCardPerson(otCardbd) 1;
	}*/
	//判斷csr_empoylee表是否存在此員工信息
//	@RequestMapping(value="/checkCostIdExistence.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
//	@ResponseBody 
//	public String checkEmpIdExistence(HttpSession session,@RequestParam("CostId")String CostId){
//		JsonObject checkResult=new JsonObject();	
//		
//		try{
//			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
//			oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
//			if(oTCardbdPersonService.checkUserNameDuplicate(CostId)){
//				checkResult.addProperty("StatusCode", "500");
//				checkResult.addProperty("Message", "沒有此費用代碼，請重新輸入!");
//			}
//			else{
//				checkResult.addProperty("StatusCode", "200");
//				checkResult.addProperty("Message", "此費用代碼存在！");
//			}
//		}
//		catch(Exception ex){
//			logger.error("Check new Account info is failed, due to: ",ex);
//			checkResult.addProperty("StatusCode", "500");
//			checkResult.addProperty("Message", "檢查工號是否綁定發生錯誤，原因："+ex.toString());
//		}
//		System.out.println(checkResult.toString());
//		return checkResult.toString();
//	}
	
	@RequestMapping(value="/checkCostIDCard.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String checkUserNameDuplicate(HttpSession session,@RequestParam("CostId")String CostId,@RequestParam("D_CardId")String D_CardId){
		JsonObject checkResult=new JsonObject();	
		String userDataCostId=(String) session.getAttribute("userDataCostId");
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
			System.out.println(CostId);
			System.out.println(D_CardId);
			if(oTCardbdPersonService.checkUserNameDuplicate(CostId)) {
				if(oTCardbdPersonService.checkDcardDuplicate(CostId,D_CardId)){
					checkResult.addProperty("StatusCode", "200");
					checkResult.addProperty("Message", "此費用代碼未綁定此離崗卡號，可以綁定此離崗卡號!");
				}else{
					checkResult.addProperty("StatusCode", "500");
					checkResult.addProperty("Message", "此費用代碼已經綁定此離崗卡號，請重新輸入離崗卡號！");
				}	
			}else {
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "沒有此費用代碼！");
			}
			
		}
		catch(Exception ex){
			logger.error("Check new Account info is failed, due to: ",ex);
			checkResult.addProperty("StatusCode", "500");
			checkResult.addProperty("Message", "檢查費用代碼是否綁定發生錯誤，原因："+ex.toString());
		}
		/*System.out.println(checkResult.toString());*/
		return checkResult.toString();
	}
	
	@RequestMapping(value="/AddOTCardBdPerson.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String OTCardbdPerson(HttpSession session,@RequestBody OTCardBD otCardbd){
		JsonObject AddResult=new JsonObject();		
		try{
			String updateUser=(String) session.getAttribute("username");
			otCardbd.setUpdate_UserId(updateUser);
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
			if(oTCardbdPersonService.OTCardbd(otCardbd)){
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "離崗卡與費用代碼綁定成功");
			}
			else{
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "離崗卡與費用代碼綁定失敗");
			}
		}
		catch(Exception ex){
			logger.error("Adding the new Account info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "綁定離崗卡發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}

	@RequestMapping(value="/UpdateBdOTCard",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String UpdateBdOTCard(HttpSession session,@RequestBody OTCardBD otCardbd) {
		JsonObject UpdateResult=new JsonObject();		
	try	{
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		String updateUser = (String)session.getAttribute("username");
		oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
		if(oTCardbdPersonService.UpdateBdOTCard(otCardbd,updateUser)){
			UpdateResult.addProperty("StatusCode", "200");
			UpdateResult.addProperty("Message", "更改離崗卡成功");
		}
		else{
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更改離崗卡失敗");
		}
	}
	catch(Exception ex){
		logger.error("Updating the BdOTCard info is failed, due to: ",ex);
		UpdateResult.addProperty("StatusCode", "500");
		UpdateResult.addProperty("Message", "更改離崗卡發生錯誤，原因："+ex.toString());
	}
	return UpdateResult.toString();
	}
	
	@RequestMapping(value="/RelieveOTCard",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String RelieveOTCard(HttpSession session,@RequestBody OTCardBD otCardbd) {
		JsonObject DisableResult=new JsonObject();
		System.out.println(otCardbd.getDeptid());
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser = (String)session.getAttribute("username");
			oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
			if(oTCardbdPersonService.RelieveOTCard(otCardbd, updateUser)){
				if(oTCardbdPersonService.OTCardNbdPerson(otCardbd, updateUser)) {
					DisableResult.addProperty("StatusCode", "200");
					DisableResult.addProperty("Message", "離崗卡綁定已失效");
				}else{
					DisableResult.addProperty("StatusCode", "500");
					DisableResult.addProperty("Message", "離崗卡綁定發生錯誤");
				}
			}else {
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "離崗卡綁定發生錯誤");
			}
			
	}
	catch(Exception ex){
		logger.error("Disable the OTCard info is failed, due to:",ex);
		DisableResult.addProperty("StatusCode", "500");
		DisableResult.addProperty("Message", "離崗卡綁定已發生錯誤，原因:"+ex.toString());
	}		
	return DisableResult.toString();
	}
	
	@RequestMapping(value="/ShowCostNo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowCostNo(HttpSession session) {
		String JsonResult = null;
		String userDataCostId=(String) session.getAttribute("userDataCostId");
		return userDataCostId;
	}
	
	@RequestMapping(value = "/ShowDeptNo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String ShowDeptNo(@RequestParam("CostId") String CostId) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		oTCardbdPersonService = (OTCardbdPersonService) context.getBean("oTCardbdPersonService");
        return oTCardbdPersonService.ShowDeptNo(CostId);
    }
}

