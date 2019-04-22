package com.foxlink.realtime.controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.service.IpBindingService;


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
	public String ShowProjectName(@RequestParam(value="DeviceIp")String DeviceIp,@RequestParam(value="DeptId")String DeptId,@RequestParam(value="ID")String ID ){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		ipBindingService = (IpBindingService) context.getBean("ipBindingService");
		return ipBindingService.BindingIp(DeviceIp,DeptId,ID);
	} 
	
	//電腦Ip綁定
		@RequestMapping(value="/ShowAllIpList",method=RequestMethod.POST,produces="application/json;charset=utf-8")
		@ResponseBody
		public String ShowAllIpList(){
			
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			ipBindingService = (IpBindingService) context.getBean("ipBindingService");
			return ipBindingService.ShowAllIpList();
		}
}
