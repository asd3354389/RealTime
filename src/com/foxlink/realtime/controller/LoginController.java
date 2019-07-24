package com.foxlink.realtime.controller;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.foxlink.realtime.service.AccountService;
import com.foxlink.realtime.service.UserService;
import com.google.gson.Gson;

@Controller
//@RequestMapping("/login")
public class LoginController implements ServletContextAware {
	private static Logger logger=Logger.getLogger(LoginController.class);
	private AccountService accountService;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		
	}
	
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This is default page!");
		model.addObject("user", GetPrincipal());  
		model.setViewName("hello");
		return model;
	}
	
	@RequestMapping(value = "/AllUsers.show", method = RequestMethod.GET)
	public @ResponseBody String ShowAllUsers(){
		UserService userService=new UserService();
		String jsonResults=new Gson().toJson(userService.FindAllRecords());
		return jsonResults;
	}
	
	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
		
	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }
	  
	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
		model.setViewName("Login");
    
	  return model;
	}
	
	   @RequestMapping(value = "/index", method = RequestMethod.GET)  
	    public String homePage(HttpSession session) { 
		   session.setAttribute("username", GetPrincipal());
		   if(GetLoginInfo()!=null){
			   session.setAttribute("userDataCostId", GetLoginInfo().get("COSTID"));
			   session.setAttribute("assistantId", GetLoginInfo().get("ASSISTANT_ID"));
			   session.setAttribute("accessRole", GetLoginInfo().get("ROLE"));
		   }
	        return "index";  
	    } 
	
	@RequestMapping(value="/403",method=RequestMethod.GET)
	public ModelAndView AccessDenied(){
		ModelAndView model = new ModelAndView();
		//check if user is login
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addObject("username", userDetail.getUsername());
		  }
			
		  model.setViewName("403");
		  return model;
	}	
	
	 private String GetPrincipal(){
	        String userName = null;
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 
	        if (principal instanceof UserDetails) {
	            userName = ((UserDetails)principal).getUsername();
	        } else {
	            userName = principal.toString();
	        }     
	        return userName;
	    }
	 
	 private Map<String, Object> GetLoginInfo(){
		 Map<String, Object>  selectUsers = null;
		 ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		 accountService = (AccountService) context.getBean("accountService"); 
		 selectUsers = accountService.GetLoginInfo(GetPrincipal());
		 if(selectUsers!=null){
			 if(accountService.GetLoginRole(GetPrincipal())!=null){
				 selectUsers.put("ROLE", accountService.GetLoginRole(GetPrincipal()));
			 }else{
				 selectUsers.put("ROLE", "NOACCESS");
			 }
		 }
		 return selectUsers;
	 }
		
}
