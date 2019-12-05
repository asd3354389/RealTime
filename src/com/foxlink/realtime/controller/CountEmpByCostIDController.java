package com.foxlink.realtime.controller;

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

import com.foxlink.realtime.service.CountEmpByCostIDService;
import com.foxlink.realtime.service.CountEmpService;


@Controller
@RequestMapping("/CountEmpByCostID")
public class CountEmpByCostIDController {
	private static Logger logger=Logger.getLogger(CountEmpByCostIDController.class);
	private CountEmpByCostIDService countEmpByCostIDService;
	
	@RequestMapping(value="/ShowCountEmpByCostID",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "CountEmpByCostid";
	}
	
	@RequestMapping(value = "/ShowCountEmpAll", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowCountEmpAll(HttpSession session,@RequestParam("SDate")String SDate) {
		String costid="6251*6252*9628*9629*9097";
		String accessRole=(String) session.getAttribute("accessRole");
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		countEmpByCostIDService = (CountEmpByCostIDService) context.getBean("countEmpByCostIDService");
	
		return countEmpByCostIDService.ShowCountEmpList(costid,SDate);
	}
	
	@RequestMapping(value = "/ShowCountEmpByCostId", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowCountEmpByCostId(HttpSession session,@RequestParam("costid")String costid,@RequestParam("SDate")String SDate) {

		String accessRole=(String) session.getAttribute("accessRole");
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		countEmpByCostIDService = (CountEmpByCostIDService) context.getBean("countEmpByCostIDService");
	
		return countEmpByCostIDService.ShowCountEmpBCList(costid,SDate);
	}
	
	@RequestMapping(value = "/ShowABEmp", method = RequestMethod.POST, produces = "Application/json;charset=utf-8")
	public @ResponseBody String ShowCountEmpAll(HttpSession session,@RequestParam("costid")String costid,@RequestParam("SDate")String SDate) {
		if(costid.equals("All")) {
				costid="6251*6252*9628*9629*9097";
		}
		String accessRole=(String) session.getAttribute("accessRole");
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		countEmpByCostIDService = (CountEmpByCostIDService) context.getBean("countEmpByCostIDService");
	
		return countEmpByCostIDService.ShowABEmpList(costid,SDate);
	}
}
