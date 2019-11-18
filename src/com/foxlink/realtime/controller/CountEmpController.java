package com.foxlink.realtime.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/CountEmp")
public class CountEmpController {
	private static Logger logger=Logger.getLogger(CountEmpController.class);
	
	@RequestMapping(value="/ShowCountEmp",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "CountEmp";
	}
}
