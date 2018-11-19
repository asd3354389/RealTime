package com.foxlink.realtime.controller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.User;
import com.foxlink.realtime.service.AccountService;
import com.foxlink.realtime.service.AssistantService;
import com.foxlink.realtime.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/ChangePassWord")
public class ChangePassWordController implements ServletContextAware {
	private static Logger logger=Logger.getLogger(ChangePassWordController.class);
	private AccountService accountService;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		
	}
	
	 @RequestMapping(value="/checkUserName.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody 
		public String checkUserNameDuplicate(HttpSession session,@RequestParam("userName")String userName){
			JsonObject checkResult=new JsonObject();		
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				accountService = (AccountService) context.getBean("accountService");
				if(accountService.checkUserNameDuplicate(userName)){
					checkResult.addProperty("StatusCode", "500");
					checkResult.addProperty("Message", "賬號不存在，请确认所更改密码的账号是否正确!");
				}
				else{
					checkResult.addProperty("StatusCode", "200");
					checkResult.addProperty("Message", "賬號已存在，可以更改賬號密码！");
				}
			}
			catch(Exception ex){
				logger.error("Check new Account info is failed, due to: ",ex);
				checkResult.addProperty("StatusCode", "500");
				checkResult.addProperty("Message", "檢查賬號是否存在發生錯誤，原因："+ex.toString());
			}
			return checkResult.toString();
		}
	 
	 
	 @RequestMapping(value = "/FindChangePassWordUserInfo.show", method = RequestMethod.POST,produces="Application/json;charset=utf-8")
		public @ResponseBody String FindChangePassWordUserInfo(@RequestBody User changePasswordUser){
			String jsonResults="";
			try{				    	
					ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
					accountService = (AccountService) context.getBean("accountService");
					Gson gson = new GsonBuilder().serializeNulls().create();					
					jsonResults=gson.toJson(accountService.FindRecord(changePasswordUser));
					
				}catch(Exception ex){
					logger.error(ex);
					JsonObject exception=new JsonObject();
					exception.addProperty("StatusCode", "500");
					exception.addProperty("ErrorMessage", "更改密码的user信息失敗，原因："+ex.toString());
					jsonResults=exception.toString();
			}
			return jsonResults;
		}
	  
	 @RequestMapping(value="/updateAccountPassWord.do",method=RequestMethod.POST,produces="Application/json;charset=utf-8")
		@ResponseBody
		public  String UpdateAccountPassWord(@RequestBody User user){
			JsonObject UpdateResult=new JsonObject();		
			try{
				ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
				accountService = (AccountService) context.getBean("accountService");
				if(accountService.UpdateAccountPassWord(user)){
					UpdateResult.addProperty("StatusCode", "200");
					UpdateResult.addProperty("Message", "更新賬號密码成功，请用新密码进行登录");
				}
				else{
					UpdateResult.addProperty("StatusCode", "500");
					UpdateResult.addProperty("Message", "更新賬號密码失敗");
				}
			}
			catch(Exception ex){
				logger.error("Updating the Account PassWord is failed, due to: ",ex);
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新賬號密码發生錯誤，原因："+ex.toString());
			}
			return UpdateResult.toString();
		}
		
}
