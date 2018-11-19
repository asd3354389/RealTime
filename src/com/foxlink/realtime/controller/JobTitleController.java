package com.foxlink.realtime.controller;

import java.util.List;

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

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.JobInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.service.JobTitleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/JobTitle")
public class JobTitleController {
	private static Logger logger=Logger.getLogger(JobTitleController.class);
	private JobTitleService jobTitleService;
	
	@RequestMapping(value="/ShowAllJobTitle",method=RequestMethod.GET)
	public String ShowAllJobTitle(){
		return "ShowJobTitle";
	}
	
	@RequestMapping(value="/AllJobTitle.show",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableJobTitleInfos(HttpSession session,@RequestParam("curPage")String curPage,@RequestParam("queryCritirea")String queryCritirea,@RequestParam("queryParam")String queryParam){
		String DisableResult=null;
		try{
			int currentPage=1;
			if(curPage=="" || curPage==null)
			     currentPage=1;
			else
				currentPage=Integer.parseInt(curPage);;
			    if(queryParam=="" || queryParam==null)
			    	queryCritirea="";
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			String updateUser=(String) session.getAttribute("username");
			String userDataCostId=(String) session.getAttribute("userDataCostId");
			jobTitleService = (JobTitleService) context.getBean("jobTitleService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page page = jobTitleService.getJobTitlePage(currentPage,queryCritirea, queryParam,updateUser,userDataCostId);
			page.setList(jobTitleService.FindQueryRecord(updateUser, currentPage, queryCritirea,queryParam,userDataCostId));
			DisableResult = gson.toJson(page);
		}catch(Exception ex){
			logger.error(ex);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時工時職位和職務列表列表失敗，原因："+ex.toString());
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/UpdateJobTitle.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String UpdateJobTitle(HttpSession session,@RequestBody Emp emp){
		JsonObject UpdateResult=new JsonObject();
		System.out.println(emp.toString());
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			jobTitleService = (JobTitleService) context.getBean("jobTitleService");
			if(jobTitleService.UpdateRecord(emp)){
				UpdateResult.addProperty("StatusCode", "200");
				UpdateResult.addProperty("Message", "更新員工職位成功");
			}else{
				UpdateResult.addProperty("StatusCode", "500");
				UpdateResult.addProperty("Message", "更新員工職位失敗");
			}
		}catch(Exception e){
			logger.error("Updating the JobTitle info is failed, due to: ",e);
			UpdateResult.addProperty("StatusCode", "500");
			UpdateResult.addProperty("Message", "更新員工職位發生錯誤，原因："+e.toString());
		}
		
		return UpdateResult.toString();
	}
	
	@RequestMapping(value="/JobType.show",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String GetJobInfo(HttpSession session,@RequestParam("JobType")String jobType){
		String JobInfoResult=null;
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			jobTitleService = (JobTitleService) context.getBean("jobTitleService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<JobInfo> jobInfo = jobTitleService.FindAllJobInfoByJobType(jobType);
			JobInfoResult = gson.toJson(jobInfo);
		}catch(Exception e){
			logger.error("Get the JobTitle info is failed, due to: ",e);
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得實時工時職位和職務關係列表列表失敗，原因："+e.toString());
			JobInfoResult=exception.toString();
		}
		
		return JobInfoResult;
	}
	
}
