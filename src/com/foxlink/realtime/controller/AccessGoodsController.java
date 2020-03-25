package com.foxlink.realtime.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.AccessGoods;
import com.foxlink.realtime.model.FourteenROP;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.AccessGoodsService;
import com.foxlink.realtime.service.WorkShopService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/AccessGoods")
public class AccessGoodsController {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccessGoodsService accessGoodsService;
	
	@RequestMapping(value="/ShowAccessGoods",method=RequestMethod.GET)
	public String ShowAccessGoodsInfo(){
		return "AccessGoods";
	}
	
	@RequestMapping(value="/WorkshopNo.show",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowWorkshopNO() {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			accessGoodsService=(AccessGoodsService) context.getBean("accessGoodsService");
			jsonResults=gson.toJson(accessGoodsService.FindWorkShopNo());
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得WorkshopNo失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}
	
	@RequestMapping(value="/AddAccessGoods.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	@ResponseBody 
	public String ADDAccessGoods(HttpSession session,@RequestBody AccessGoods[] accessGoods){
		JsonObject AddResult=new JsonObject();	
	/*	System.out.println(accessGoods[0].getUserId());
		System.out.println(accessGoods[0].getCardId());
		System.out.println(accessGoods[0].getWorkShopNo());
		System.out.println(accessGoods[0].getUdisk());
		System.out.println(accessGoods[0].getComputer());
		System.out.println(accessGoods[0].getMobilePhone());
		System.out.println(accessGoods[0].getStart_date());
		System.out.println(accessGoods[0].getEnd_date());*/
		try{
			String updateUser=(String) session.getAttribute("username");
			String accessRole = (String) session.getAttribute("accessRole");
			if (accessGoodsService.addAccessGoods(accessGoods,updateUser,accessRole)) {
				AddResult.addProperty("StatusCode", "200");
				AddResult.addProperty("Message", "員工進入車間携帶物品權限成功");
			} else {
				AddResult.addProperty("StatusCode", "500");
				AddResult.addProperty("Message", "員工進入車間携帶物品權限失敗");
			}

		}
		catch(Exception ex){
			logger.error("Adding the new AccessGoods info is failed, due to: ",ex);
			AddResult.addProperty("StatusCode", "500");
			AddResult.addProperty("Message", "設置員工進入車間携帶物品權限發生錯誤，原因："+ex.toString());
		}
		System.out.println(AddResult.toString());
		return AddResult.toString();
	}
	
	@RequestMapping(value="/ShowAllAccessGoods",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String ShowAllAccessGoods(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);;
			    if(queryParam=="" || queryParam==null)
			    	queryCritirea="";
			
			String updateUser=(String) session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			//fourteenROPService = (FourteenROPService) context.getBean("fourteenROPService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = accessGoodsService.getFourteenROPPage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(accessGoodsService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得員工進入車間携帶物品列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/deleteAccessGoods.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DeletedeleteAccessGoods(HttpSession session,@RequestParam("id")String id
			,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,
			@RequestParam("workShopNo")String workShopNo,@RequestParam("cardId")String cardId){
		JsonObject DisableResult=new JsonObject();
		try{
			String updateUser=(String) session.getAttribute("username");
			/*System.out.println("費用代碼========="+id);
			System.out.println(id);
			System.out.println(cardId);
			System.out.println(workShopNo);
			System.out.println(startDate);
			System.out.println(endDate);*/
			
			if(accessGoodsService.DeleteAccessGoods(id,cardId,workShopNo,updateUser,startDate,endDate)){
				DisableResult.addProperty("StatusCode", "200");
				DisableResult.addProperty("Message", "員工進入車間携帶物品權限已失效");
			}
			else{
				DisableResult.addProperty("StatusCode", "500");
				DisableResult.addProperty("Message", "取消員工進入車間携帶物品權限發生錯誤");
			}
		}
		catch(Exception ex){
			logger.error("Disable the AccessGoods info is failed, due to:",ex);
			DisableResult.addProperty("StatusCode", "500");
			DisableResult.addProperty("Message", "取消員工進入車間携帶物品權限發生錯誤，原因:"+ex.toString());
		}		
		return DisableResult.toString();
	}
}
