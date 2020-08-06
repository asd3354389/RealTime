package com.foxlink.task;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.foxlink.realtime.service.EChartssService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
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

@Component
@EnableScheduling
@Lazy(false)
public class EchartssTask {
	
	private static Logger logger=Logger.getLogger(EchartssTask.class);
    private EChartssService echartssService;
    
    @Scheduled(cron = "12 10 09 ? * MON")
    public void cronWeek() {
    	//12 10 09 ? * MON
    	//天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    	Calendar cld = Calendar.getInstance(Locale.CHINA);
    	cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
    	cld.add(Calendar.DATE, -1*7);
    	int iWeek=cld.get(Calendar.WEEK_OF_YEAR);
    	String strWeek="第"+iWeek+"周";
    	System.out.println(iWeek);

    	cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
    	String strStart=df.format(cld.getTime());
    	System.out.println(strStart);

    	cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//周日
    	String strEnd=df.format(cld.getTime());
    	System.out.println(strEnd);
    	
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		echartssService = (EChartssService) context.getBean("echartssService");
		echartssService.EchartTaskToMail(strStart,strEnd,"FQ3Q",strWeek);
		echartssService.EchartTaskToMail(strStart,strEnd,"FD",strWeek);
    }
    
    @Scheduled(cron = "50 10 09 01 * ?")
    public void cronMonth() {
    	//12 11 09 01 * ?
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    	Calendar cld = Calendar.getInstance(Locale.CHINA);
    	cld.add(Calendar.MONTH, -1);
    	cld.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
    	String strStart=df.format(cld.getTime());
    	String strMonth="第"+strStart.substring(5,7)+"月";
    	System.out.println(strStart);

    	cld.set(Calendar.DAY_OF_MONTH, cld.getActualMaximum(Calendar.DAY_OF_MONTH)); 
    	String strEnd=df.format(cld.getTime());
    	System.out.println(strEnd);
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		echartssService = (EChartssService) context.getBean("echartssService");
		echartssService.EchartTaskToMail(strStart,strEnd,"FQ3Q",strMonth);
		echartssService.EchartTaskToMail(strStart,strEnd,"FD",strMonth);
    }   

    
}
