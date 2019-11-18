package com.foxlink.realtime.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foxlink.realtime.service.AbReasonReplyService;



@Controller
@RequestMapping("/AbReasonReply")
public class AbReasonReplyController {
	private static Logger logger=Logger.getLogger(AbReasonReplyController.class);
	private AbReasonReplyService abReasonReplyService;
	
	@RequestMapping(value="/ShowAbReasonReply",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "AbReasonReply";
	}
}
