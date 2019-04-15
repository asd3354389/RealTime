package com.foxlink.realtime.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.SignOverTime;
import com.foxlink.realtime.model.UserDate;
import com.foxlink.realtime.service.LineMappingService;
import com.foxlink.realtime.service.SignOverTimeService;
import com.foxlink.realtime.util.ImageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/SignOverTime")
public class SignOverTimeController {
	private static Logger logger=Logger.getLogger(LineMappingController.class);
	private SignOverTimeService signOverTimeService;
	
	/*測試上傳文件頁面*/
	@RequestMapping(value="/uploadtest.show",method=RequestMethod.GET)
	public String ShowOverTime() {
		return "upload";
	}
	
	@RequestMapping(value="/test.show",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String DisableJobTitleInfos(HttpSession session,@RequestParam("username")String username,@RequestParam("password")String password,
			@RequestParam("shift")String shift){
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		Map<String,Object> jsonArray = new HashMap<>();
		System.out.println(username+"------"+password);
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			signOverTimeService = (SignOverTimeService) context.getBean("signOverTimeService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<UserDate> userDate = signOverTimeService.login(username,password);
			List<UserDate> userDateR = signOverTimeService.checkRole(username,password);
			if(userDate.size()>0){
				if(userDateR.size()>0){
					if(shift.equals("D")||shift.equals("N")){
						List<SignOverTime> query = signOverTimeService.FindAllRecords(username,shift);
						if(query!=null){
							if(query.size()>0){
								jsonArray.put("StatusCode", "200");
								jsonArray.put("Message", query);
								DisableResult = gson.toJson(jsonArray);
							}else{
								exception.addProperty("StatusCode", "500");
								exception.addProperty("Messages", "查無數據,無人員資料");
								DisableResult=exception.toString();
							}
						}else{
							exception.addProperty("StatusCode", "500");
							exception.addProperty("Messages", "查無數據");
							DisableResult=exception.toString();
						}
					}else{
						exception.addProperty("StatusCode", "500");
						exception.addProperty("Messages", "班別錯誤錯誤");
						DisableResult=exception.toString();
					}
				}else{
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Messages", "非線長權限");
					DisableResult=exception.toString();
				}
			}else{
				exception.addProperty("StatusCode", "500");
				exception.addProperty("Messages", "賬戶或密碼錯誤");
				DisableResult=exception.toString();
			}
			
		}catch(Exception ex){
			logger.error("Login in app info is failed, due to: ",ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("Messages", "登陸異常，請聯繫管理員");
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value="/FindOneRecord.show",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String FindOneRecord(HttpSession session,@RequestParam("id")String id,@RequestParam("shift")String shift){
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		Map<String,Object> jsonArray = new HashMap<>();
		List<SignOverTime> query = null;
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			signOverTimeService = (SignOverTimeService) context.getBean("signOverTimeService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			if(shift.equals("D")||shift.equals("N")){
				query = signOverTimeService.FindOneRecord(id,shift);
			}
			if(query != null){
				if(query.size()>0){
					jsonArray.put("StatusCode", "200");
					jsonArray.put("Message", query);
					DisableResult = gson.toJson(jsonArray);
				}else{ 
					exception.addProperty("StatusCode", "500");
					exception.addProperty("Messages", "找不到該人員加班記錄");
					DisableResult=exception.toString();
				}
			}else{
				exception.addProperty("StatusCode", "500");
				exception.addProperty("Messages", "找不到該人員加班記錄");
				DisableResult=exception.toString();
			}
		}catch(Exception ex){
			logger.error("Login in app info is failed, due to: ",ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("Messages", "查詢人員加班信息異常");
			DisableResult=exception.toString();
		}		
		System.out.println(DisableResult);
		return DisableResult;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpSession session,@RequestParam("id")String id,@RequestParam("confirm_time")String confirm_time,
    		@RequestParam("login_user")String login_user,
    		@RequestParam("overtimedate")String overtimedate,@RequestParam("overtimehours")float overtimehours,
    		@RequestParam("file")MultipartFile file)
            throws IOException, FileUploadException {
		System.out.println("进入方法了"+"updload");
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		int contentlength = 0 ;
		InputStream fin = file.getInputStream();
		contentlength = (int) file.getSize();
		
		/*String id = "";
		String OVERTIMEDATE = "";
		String confirm_time = "";
		String overtimedate = "";
		float overtimehours = 0;
		String login_user = "";
		int contentlength = 0 ;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 判断是否是表单文件类型
        System.out.println(isMultipart);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        InputStream fin = null;
        List<FileItem> items = sfu.parseRequest(request);// 从request得到所有上传域的列表
        System.out.println(items.size());
        System.out.println("还没进入方法");
        for (Iterator iter = items.iterator(); iter.hasNext();) {
        	System.out.println("是否进入方法");
            FileItem fileitem = (FileItem) iter.next();
            if (!fileitem.isFormField() && fileitem != null) {// 判读不是普通表单域即是file
                                                                // 操作fileitem文件步骤，可以获取大小、路径
            	System.out.println("是文件");
                // 定义图片输出路径
                String imgPath = "C:\\Users\\129548\\Desktop\\2\\" + System.currentTimeMillis() + ".jpg";
                // 定义图片流
                System.out.println(fileitem.getFieldName());
                fin = fileitem.getInputStream();
                System.out.println(fileitem.getSize());
                contentlength = (int) fileitem.getSize();
                FileOutputStream outputStream = new FileOutputStream(imgPath);

                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len=fin.read(bytes)) != -1) {
                    outputStream.write(bytes,0,len);
                }
                outputStream.close();
                
            }else{
            	System.out.println("非");
            	System.out.println(fileitem.getFieldName());
            	System.out.println(fileitem.getString());
            	if(fileitem.getFieldName().equals("id")){
            		id=fileitem.getString();
            	}else if(fileitem.getFieldName().equals("confirm_time")){
            		confirm_time = fileitem.getString();
            	}else if(fileitem.getFieldName().equals("login_user")){
            		login_user = fileitem.getString();
            	}else if(fileitem.getFieldName().equals("overtimedate")){
            		overtimedate = fileitem.getString();
            	}else if(fileitem.getFieldName().equals("overtimehours")){
            		overtimehours = Float.parseFloat(fileitem.getString());
            	}
            }
        }*/
        try{
        	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    		signOverTimeService = (SignOverTimeService) context.getBean("signOverTimeService");
    		if(signOverTimeService.upload(id,confirm_time,login_user,overtimedate,overtimehours,fin,contentlength)){
    			exception.addProperty("StatusCode", "200");
    			exception.addProperty("Messages", "簽名檔上傳成功");
    			DisableResult=exception.toString();
    		}else{
    			exception.addProperty("StatusCode", "500");
    			exception.addProperty("Messages", "簽名檔上傳失敗或今天已簽名");
    			DisableResult=exception.toString();
    		}
        }catch(Exception ex){
			logger.error("upload sign is failed, due to: ",ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("Messages", "查簽名檔上傳異常");
			DisableResult=exception.toString();
		}	
        System.out.println(DisableResult);
        return DisableResult;
    }
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(HttpSession session,@RequestParam("id")String id,@RequestParam("confirm_time")String confirm_time,
    		@RequestParam("login_user")String login_user,
    		@RequestParam("overtimedate")String overtimedate,@RequestParam("overtimehours")float overtimehours,
    		@RequestParam("file")MultipartFile file)
            throws IOException, FileUploadException {
		System.out.println("进入方法了"+"update");
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		int contentlength = 0 ;
		InputStream fin = file.getInputStream();
		contentlength = (int) file.getSize();
        try{
        	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    		signOverTimeService = (SignOverTimeService) context.getBean("signOverTimeService");
    		if(signOverTimeService.update(id,confirm_time,login_user,overtimedate,overtimehours,fin,contentlength)){
    			exception.addProperty("StatusCode", "200");
    			exception.addProperty("Messages", "簽名檔更新成功");
    			DisableResult=exception.toString();
    		}else{
    			exception.addProperty("StatusCode", "500");
    			exception.addProperty("Messages", "簽名檔更新失敗或今天未簽名");
    			DisableResult=exception.toString();
    		}
        }catch(Exception ex){
			logger.error("update sign is failed, due to: ",ex);
			exception.addProperty("StatusCode", "500");
			exception.addProperty("Messages", "簽名檔更新異常");
			DisableResult=exception.toString();
		}	
        System.out.println(DisableResult);
        return DisableResult;
    }
	
	@RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    @ResponseBody
	public ModelAndView getSignPicture(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
        String overtimedate=request.getParameter("overtimedate");
        System.out.println(id+"--------"+overtimedate);
        // 设置Resposne
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+id+overtimedate+".png");
        response.setContentType("text/x-plain");
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("StatusCode", "500");
        mv.addObject("查簽名檔上傳異常", "查簽名檔上傳異常");
        
		String DisableResult=null;
		JsonObject exception=new JsonObject();
		
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			signOverTimeService = (SignOverTimeService) context.getBean("signOverTimeService");
			Gson gson = new GsonBuilder().serializeNulls().create();
			ServletOutputStream out = response.getOutputStream();
			
			// 从数据库拷贝输出流
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(4096);
            signOverTimeService.getSignPicture(id,overtimedate,byteOutputStream);
            logger.info(" call PosService.copyDocumentOutputStream successfully.");
            
            // 转化
            byte[] bt = null;
            bt = byteOutputStream.toByteArray(); 
            
            // 向客户端写输出
            out.write(bt);
            out.flush();
            out.close();
            
		}catch(Exception e){
			e.printStackTrace();
			logger.info(" call PosService.copyDocumentOutputStream false.");
		}
		
		return null;
		
	}

}
