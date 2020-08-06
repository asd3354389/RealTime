package com.foxlink.realtime.test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.PresetLineDash;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFPresetLineDash;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.foxlink.realtime.service.EChartssService;

//@Component
//@EnableScheduling
//@Lazy(false)
public class myTest {
	
	 private EChartssService echartssService;
	@Test
	 public void getChartSwipeCardAB2(){
/*		
		 List<Map<String, Object>> listTO= new ArrayList<Map<String, Object>>();
		  List<Map<String, Object>> listCC= new ArrayList<Map<String, Object>>();
		 String strSQL1="select DISTINCT T.WECHAT_ID from swipe.WECHAT_USER t where T.FACTORY='FQ3Q' AND T.ENABLED!=0";
		 String strSQL2="select DISTINCT WECHAT_ID from SWIPE.WECHAT_USER where deptid in ('ALL','FD') AND ENABLED!=0";
		
		 listTO=jdbcTemplate.queryForList(strSQL1);
		 listCC=jdbcTemplate.queryForList(strSQL2);
		 System.out.println(listTO);
		 System.out.println(listCC);*/
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		echartssService = (EChartssService) context.getBean("echartssService");
		//echartssService.ToMail();
			
		
	 }
	
	@Test
	//@Scheduled(cron = "0/3 * * * * ?")
    public void depositDiscount(){
 
        //System.out.println(1);
    }

}
