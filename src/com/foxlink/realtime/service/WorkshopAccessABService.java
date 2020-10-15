package com.foxlink.realtime.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.foxlink.realtime.model.AccessAB;
import com.foxlink.realtime.model.objectMapper.AccessABMapper;
import com.foxlink.realtime.util.WorkshopABExcelUtils;

@Service
public class WorkshopAccessABService {
	private static Logger logger=Logger.getLogger(WorkshopAccessABService.class);
	
	@Autowired
	public JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void sendWorkshopNoAccessAB(String strDate) {
		String sql = "select b.id,b.name,b.costid,b.deptid,b.depid,b.depname,a.exception_interval,a.exception_time from RT_ACCESS_AB a,csr_employee b where a.emp_id = b.id and a.emp_id not in (select t.emp_id from DEPARTURE_EXCEPTION_EMP t where t.enable = 'Y') and b.line_personnel = 'Y' and a.swipe_date = '"+strDate+"' and b.isonwork = '0' order by b.costid,b.deptid,b.depid,b.id";
		
		List<AccessAB> abList = jdbcTemplate.query(sql, new AccessABMapper());
		
		Boolean isAnyAB = false;
		Boolean isEx = false;
		
		WorkshopABExcelUtils workshopABExcelUtils = new WorkshopABExcelUtils();
		
		InputStream is = null;
		
		if(abList!=null&&abList.size()>0) {
			isAnyAB = true;
			try {
				is = workshopABExcelUtils.creatExcel(abList,strDate+"上班時間出車間超15分鐘");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
				e.printStackTrace();
				isEx = true;
			}
		}else{
			isAnyAB = false;
		}
		
		       
	//郵箱服務器地址
	 String smtpServer="dp-notes.foxlink.com.tw";
	//發件人
	 String emailID="MIS_SFC@foxlink.com.tw";
	//發件人暱稱
	 String userName="sfc_mis";
	//發件人密碼
	 String password ="21250Cw";

	 Properties props=System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.auth", "true");
		// 设置session,和邮件服务器进行通讯。
		Session session=Session.getInstance(props,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				if ((userName != null) && (userName.length() > 0) && (password != null) 
				    && (password.length() > 0)) {
				       return new PasswordAuthentication(userName, password);
				}
				return null;
	        }
		});					
		MimeMessage msg=new MimeMessage(session);
		try{
			
			File file=new File("C:\\config\\WorkshopABMailID.txt");
			String mailIds = "";
			if(file.isFile() && file.exists()){ //判断文件是否存在
	            InputStreamReader read = new InputStreamReader(new FileInputStream(file),"utf-8");//考虑到编码格式
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String lineTxt = null;
	            while((lineTxt = bufferedReader.readLine()) != null){
	                System.out.println(lineTxt);
	                mailIds = mailIds + lineTxt;
	            }
	            
	            read.close();
			}else{
				System.out.println("找不到指定的文件");
			}
			mailIds.trim();
			System.out.println("mailIds:"+mailIds);
			// 设置多个收件人地址  
	        String StrTO = "";
	        if(mailIds!=null&&!"".equals(mailIds)) {
	        	String[] mailIDArray = mailIds.split(",");
	        	for (String id : mailIDArray) {
	        		StrTO=StrTO+id+"@foxlink.com.tw,";
				}
	        }
	        if(!StrTO.isEmpty()) StrTO=StrTO.substring(0, StrTO.length()-1);
	        System.out.println(StrTO);
	        
	        
	        File fileCC=new File("C:\\config\\WorkshopABMailCCID.txt");
			String mailCCIds = "";
			if(fileCC.isFile() && fileCC.exists()){ //判断文件是否存在
	            InputStreamReader read = new InputStreamReader(new FileInputStream(fileCC),"utf-8");//考虑到编码格式
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String lineTxt = null;
	            while((lineTxt = bufferedReader.readLine()) != null){
	                System.out.println(lineTxt);
	                mailCCIds = mailCCIds + lineTxt;
	            }
	            
	            read.close();
			}else{
				System.out.println("找不到指定的文件");
			}
			mailCCIds.trim();
	        // 设置多个抄送地址  
			String StrCC = "";
			if(mailCCIds!=null&&!"".equals(mailCCIds)) {
	        	String[] mailIDArray = mailCCIds.split(",");
	        	for (String id : mailIDArray) {
	        		StrCC=StrCC+id+"@foxlink.com.tw,";
				}
	        }

			// 设置邮件发送者的地址
			 InternetAddress fromAddress = new InternetAddress(emailID,userName, "utf-8");
		        msg.setFrom(fromAddress);
		     // 设置邮件接收方的地址
		        // 设置多个收件人地址  
		        if(!StrTO.isEmpty()) {
		           InternetAddress[] ToAddress = InternetAddress.parse(StrTO);
		           // 设置邮件接收方
		           msg.setRecipients(RecipientType.TO, ToAddress);
		        }
			
			  if(!StrCC.isEmpty()) { // 设置多个抄送地址 
				  InternetAddress[] CCAddress = InternetAddress.parse(StrCC); 
				  msg.setRecipients(RecipientType.CC, CCAddress);
			  }
			 
		        
		        
		        // 设置邮件标题
	        	String strject=strDate+"上班時間出車間超15分鐘";
		        msg.setSubject(strject, "utf-8");
		        
		       
		        // 设置显示的发件时间
		        msg.setSentDate(new Date());
		        // 保存设置
		       
		      //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
		        MimeMultipart multipart = new MimeMultipart();
		        if(isAnyAB) {
		        	//创建文本节点
			        MimeBodyPart text = new MimeBodyPart();
			        String strContent="各位長官好：<BR>附件爲"+strDate+"上班時間出車間超15分鐘報表"+"，<BR>請查閲，謝謝。";
			        text.setContent(strContent,"text/html;charset=UTF-8");
			      //将文本和图片添加到multipart
			        /*添加附件*/
	                MimeBodyPart fileBody = new MimeBodyPart();
	                DataSource source = new ByteArrayDataSource(is, "application/msexcel;charset=UTF-8");
	                fileBody.setDataHandler(new DataHandler(source));
	                String fileName  = strDate+"上班時間出車間超15分鐘"+".xls";
	                // 中文乱码问题
	                fileBody.setFileName(MimeUtility.encodeWord(fileName,"UTF-8",null));
			        multipart.addBodyPart(text);
			        multipart.addBodyPart(fileBody);
		        }else {
		        	//创建文本节点
			        MimeBodyPart text = new MimeBodyPart();
			        String strContent="各位長官好：<BR>"+strDate+"無上班時間出車間超15分鐘報人員"+"，<BR>謝謝。";
			        text.setContent(strContent,"text/html;charset=UTF-8");
			        multipart.addBodyPart(text);
		        }
		      
		        multipart.setSubType("mixed");//混合关系
		        
		        msg.setContent(multipart);
		       // msg.setContent("Test","text/html;charset=UTF-8");
		        msg.saveChanges();
		        Transport.send(msg);
		        is.close();
		        System.out.println("發送成功");
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.info(ex);
			
		}
		
	}
}
