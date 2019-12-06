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

import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.service.AbReasonReplyService;
import com.foxlink.realtime.service.FLinePersonMtService;




@Controller
@RequestMapping("/AbReasonReply")
public class AbReasonReplyController {
	private static Logger logger=Logger.getLogger(AbReasonReplyController.class);
	private AbReasonReplyService abReasonReplyService;
	
	@RequestMapping(value="/ShowAbReasonReply",method=RequestMethod.GET)
	public String ShowWorkShopPage(){
		return "AbReasonReply";
	}
	@RequestMapping(value="/ShowABReasonReplyList", method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String ShowABReasonReplyList(HttpSession session,@RequestParam("Bu")String Bu,@RequestParam("costid")String costid,@RequestParam("depid")String depid,@RequestParam("SDate")String SDate,@RequestParam("EDate")String EDate){
			String result = "";
			String accessRole = (String) session.getAttribute("accessRole");
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			abReasonReplyService = (AbReasonReplyService) context.getBean("abReasonReplyService");
			if(accessRole.equals("ROLE_VIC_ASSISTANT")) {
				result = abReasonReplyService.ShowABReplyByDepid(depid,SDate,EDate);
			}else if(accessRole.equals("ROLE_VIC_ADMIN")||accessRole.equals("ALL")) {
				result = abReasonReplyService.ShowABReasonReply(Bu,costid,SDate,EDate);
			}
		return result;
	}
	
	@RequestMapping(value="/ReplyReason", method=RequestMethod.POST,produces="Application/json;charset=utf-8")
	public @ResponseBody String replyReason(@RequestBody QueryByIdList[] Emp) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		abReasonReplyService = (AbReasonReplyService) context.getBean("abReasonReplyService");
		return abReasonReplyService.replyReason(Emp);		
	}
}
