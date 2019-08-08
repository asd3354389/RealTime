package com.foxlink.realtime.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.foxlink.realtime.service.ClassNoService;
import com.foxlink.realtime.service.SignOverTimeService;
import com.foxlink.realtime.service.WorkShopService;
import com.foxlink.realtime.util.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/Utils")
public class UtilsController {
	
	private WorkShopService workshopService;
	private ClassNoService classNoService;
	@RequestMapping(value="/ClassInfo.show",method=RequestMethod.GET)
	public @ResponseBody String ShowClassInfo(@RequestParam(value="currentShift")String currentShift) {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			classNoService=(ClassNoService) context.getBean("classNOService");
			jsonResults=gson.toJson(classNoService.FindRecord(currentShift));
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得ClassInfo列表失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}

	
	@RequestMapping(value="/WorkshopNo.show",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public @ResponseBody String ShowWorkshopNO() {
		String jsonResults="";
		try{
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			 workshopService=(WorkShopService) context.getBean("workShopService");
			jsonResults=gson.toJson(workshopService.FindWorkShopNo());
		}catch(Exception ex){
			JsonObject exception=new JsonObject();
			exception.addProperty("StatusCode", "500");
			exception.addProperty("ErrorMessage", "取得WorkshopNo失敗，原因："+ex.toString());
			jsonResults=exception.toString();
	}
		return jsonResults;
	}
	
	@RequestMapping(value="/DownLoadChrome.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	public @ResponseBody ResponseEntity<byte[]> ShowDownLoadChrome(@RequestParam(value="filename")String filename,
			HttpServletRequest request,HttpServletResponse response) {
		//下载文件路径
				String path ="";
				if(filename==null||filename.equals("")){
					filename = "32";
				}
				path = "C:/chrome";
				if(filename.equals("64")){
					filename = "chromebeta_x64-v68.0.3440.33.exe";
				}else{
					filename = "64.0.3282.140_chrome32_stable_windows_installer.exe";
				}
		        File file = new File(path + File.separator + filename);
		        HttpHeaders headers = new HttpHeaders(); 
		        //下载显示的文件名，解决中文名称乱码问题 
		        String downloadFielName = null;
		        byte[] readFileToByteArray = null;
				try {
					downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
					readFileToByteArray = FileUtils.readFileToByteArray(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //通知浏览器以attachment（下载方式）打开图片
		        headers.setContentDispositionFormData("attachment", downloadFielName);
		        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
		        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		        return new ResponseEntity<byte[]>(readFileToByteArray,headers, HttpStatus.CREATED);
	}
}
