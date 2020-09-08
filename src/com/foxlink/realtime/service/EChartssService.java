package com.foxlink.realtime.service;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.persistence.criteria.From;

import org.apache.log4j.Logger;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

import com.foxlink.realtime.model.QueryWorkDayCount;
import com.foxlink.realtime.model.SwipecardAB;
import com.foxlink.realtime.model.objectMapper.QueryEmpIPBindingMapper;
import com.foxlink.realtime.model.objectMapper.QueryWorkDayCountMapper;
import com.foxlink.realtime.model.objectMapper.SwipecardABMapper;
import com.foxlink.realtime.util.SwipecardABExcelUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

public class EChartssService {

	 private static Logger logger=Logger.getLogger(AccountService.class);
		private JdbcTemplate jdbcTemplate;

		@Autowired
		public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
	 
	 
	 public JsonObject getChartSwipeCardAB(String varStartTime,String varEndTime){
		 List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> listCostID= new ArrayList<Map<String, Object>>();
		 JsonObject jsonb =new JsonObject();
		    /*String strSQL1="  SELECT DISTINCT CE.COSTID,CE.DEPTID,CE.DEPNAME \r\n" + 
		    		"    FROM swipe.ALERT_SWIPECARD_AB_WECHAT asaw, SWIPE.CSR_EMPLOYEE ce\r\n" + 
		    		"   WHERE     asaw.id = CE.ID \r\n" + 
		    		"         AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
		    		"         AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
		    		"         AND  CE.DEPTID IN (SELECT DISTINCT DEPTID FROM swipe.WECHAT_USER WHERE ENABLED=1)\r\n" +
		    		"ORDER BY ce.costid,ce.DEPTID";*/
		    String strSQL1="  SELECT DISTINCT dr.COSTID, dr.DEPID DEPTID, dr.DEPT_NAME DEPNAME\r\n" + 
		    		"    FROM swipe.WECHAT_USER wu, SWIPE.DEPT_RELATION dr\r\n" + 
		    		"   WHERE WU.DEPTID = dr.DEPID AND WU.ENABLED = 1 \r\n" + 
		    		"ORDER BY dr.costid, dr.DEPID";
			String strSQL = "  SELECT ASAW.SWIPE_DATE,\r\n" + 
					"         ASAW.STATUS,\r\n" + 
					"         CE.COSTID,\r\n" + 
					"         CE.DEPTID,\r\n" + 
					"         COUNT (ASAW.ID) SUM \r\n" + 
					"    FROM swipe.ALERT_SWIPECARD_AB_WECHAT asaw, SWIPE.CSR_EMPLOYEE ce\r\n" + 
					"   WHERE     asaw.id = CE.ID \r\n" + 
					"         AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
					"         AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
					"         AND  CE.DEPTID IN (SELECT DISTINCT DEPTID FROM swipe.WECHAT_USER WHERE ENABLED=1)\r\n" +
					"GROUP BY ASAW.SWIPE_DATE, ASAW.STATUS, CE.COSTID,CE.DEPTID\r\n" + 
					"ORDER BY ASAW.SWIPE_DATE, ASAW.STATUS, CE.COSTID,CE.DEPTID";		
			System.out.println( strSQL );
			try {
				listCostID=jdbcTemplate.queryForList(strSQL1);
				//System.out.println(listCostID);
				listMap = jdbcTemplate.queryForList(strSQL);
				//JSONObject jsonb = new JSONObject(); 
				
				//jsonb.addProperty("sucess", "true");
				List<String> listSD = new ArrayList<String>();
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date dStartTime = df.parse(varStartTime); 
				Date dEndTime = df.parse(varEndTime);
				long li = ChronoUnit.DAYS.between(dStartTime.toInstant(),dEndTime.toInstant());//两个时间相差多少天。
				System.out.println( li );
				
				
			List<Integer> lOne=new ArrayList<>();
/*			List<Integer> lTwo=new ArrayList<>();
			List<Integer> lThree=new ArrayList<>();
			List<Integer> lFour=new ArrayList<>();*/
			List<String> lCostID=new ArrayList<>();
			List<String> lDate=new ArrayList<>();
			
			for (int j = 0; j <= li; j++) {
				Calendar c = Calendar.getInstance();
				c.setTime(dStartTime);
				c.add(Calendar.DATE, j);
				boolean b=true;
				String str = df.format(c.getTime());// dStartTime的日期上面加上j天
				lDate.add("'"+str+"'");
			}
			// 循环costid
			for (Map<String, Object> LCID : listCostID) {
				String strLCIDCostID = LCID.get("DEPTID") == null ? "0000" : LCID.get("DEPTID").toString();
				String strLCIDCostID2 = LCID.get("COSTID") == null ? "0000" : LCID.get("COSTID").toString();
				String strLCIDDeptName = LCID.get("DEPNAME") == null ? "0000" : LCID.get("DEPNAME").toString();
				JsonArray arrayPlayer = new JsonArray();
				lCostID.add(strLCIDCostID);
				JsonObject jbplayer = new JsonObject();
				// 循环4个异常状态。
				for (int i = 1; i <= 4; i++) {
					lOne.clear();
					
					//jbplayer.addProperty("status", i);
					// 循环天
					for (int j = 0; j <= li; j++) {
						Calendar c = Calendar.getInstance();
						c.setTime(dStartTime);
						c.add(Calendar.DATE, j);
						boolean b=true;
						String str = df.format(c.getTime());// dStartTime的日期上面加上j天
				
						for (Map<String, Object> LM : listMap) {
							String strSwipe_date = LM.get("SWIPE_DATE").toString();
							String strLMCOSTID = LM.get("DEPTID") == null ? "0000" : LM.get("DEPTID").toString();
							String strSTATUS = LM.get("STATUS").toString();
							int iSUM = Integer.parseInt(LM.get("SUM").toString());
							// 如果listmap中存在日期；费用代码；异常状态，值保存到数组中。
							if (strSwipe_date.equals(str) && strLMCOSTID.equals(strLCIDCostID)
									&& strSTATUS.equals(String.valueOf(i))) {
/*								switch (i) {
								case 1:
									lOne.add(iSUM);
									break;
								case 2:
									lTwo.add(iSUM);
									break;
								case 3:
									lThree.add(iSUM);
									break;
								case 4:
									lFour.add(iSUM);
									break;
								}*/
								lOne.add(iSUM);
								b=false;
								break;
							}
						}
						// 如果listmap中匹配不到日期；费用代码；异常状态，运行到此处
/*						switch (i) {
						case 1:
							lOne.add(0);
							break;
						case 2:
							lTwo.add(0);
							break;
						case 3:
							lThree.add(0);
							break;
						case 4:
							lFour.add(0);
							break;
						}*/
						if(b) lOne.add(0);
					} // 天
					jbplayer.addProperty(String.valueOf(i), lOne.toString());
					//jbplayer.addProperty("COSTID",listCostID.toString() );
					

				} // 4
				jbplayer.addProperty("xdate", lDate.toString());
				jbplayer.addProperty("deptName", LCID.get("DEPNAME").toString());
				arrayPlayer.add(jbplayer);
				jsonb.add(strLCIDCostID2+"-"+strLCIDCostID, arrayPlayer);
				
			}
			System.out.println(jsonb);
				
				/*for( Map<String, Object> LM : listMap ) {
		            for( String key : LM.keySet() ) {
		            	String sKeyValue=LM.get(key).toString();
		            	System.out.println( key + "-->" + LM.get(key) );
		            	if(key.equals(key)&&!listSD.contains(sKeyValue)) {
		            		listSD.add(sKeyValue);
		            	}
		            }
		        }*/
				
			} catch (Exception e) {
				logger.info(e);
			}
			return jsonb ;
	 }
	 
	 public List<Map<String, Object>> getCSCABByID(String varStartTime,String varEndTime){
		 List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		 String strSQL = "SELECT CE.ID,\r\n" + 
		 		"         CE.NAME,\r\n" + 
		 		"         CE.DEPID,\r\n" + 
		 		"         CE.DEPTID,\r\n" +
		 		"         CE.COSTID,\r\n" + 
		 		"         ASAW.STATUS,\r\n" + 
		 		"         COUNT (ASAW.ID) SUM\r\n" + 
		 		"    FROM SWIPE.CSR_EMPLOYEE CE, SWIPE.ALERT_SWIPECARD_AB_WECHAT ASAW\r\n" + 
		 		"   WHERE     CE.ID = ASAW.ID\r\n" + 
		 		"         AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
		 		"         AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
		 		"         AND  CE.DEPTID IN (SELECT DISTINCT DEPTID FROM swipe.WECHAT_USER WHERE ENABLED=1)\r\n" + 
		 		"GROUP BY CE.ID,\r\n" + 
		 		"         CE.NAME,\r\n" + 
		 		"         CE.DEPID,\r\n" +
		 		"         CE.DEPTID,\r\n" +
		 		"         CE.COSTID,\r\n" + 
		 		"         ASAW.STATUS\r\n" + 
		 		"ORDER BY CE.COSTID, CE.DEPID, SUM DESC";
		 strSQL="SELECT t.*, (S1 + S2 + S3 + S4) SSUM\r\n" + 
		 		"    FROM (  SELECT ID,\r\n" + 
		 		"                   NAME,\r\n" + 
		 		"                   COSTID,\r\n" + 
		 		"                   DEPTID,\r\n" + 
		 		"                   DEPID,\r\n" + 
		 		"                   DEPNAME,\r\n" + 
		 		"                   SUM (CASE WHEN STATUS = 1 THEN SS ELSE 0 END) AS S1,\r\n" + 
		 		"                   SUM (CASE WHEN STATUS = 2 THEN SS ELSE 0 END) AS S2,\r\n" + 
		 		"                   SUM (CASE WHEN STATUS = 3 THEN SS ELSE 0 END) AS S3,\r\n" + 
		 		"                   SUM (CASE WHEN STATUS = 4 THEN SS ELSE 0 END) AS S4\r\n" + 
		 		"              FROM (  SELECT CE.ID,\r\n" + 
		 		"                             CE.NAME,\r\n" + 
		 		"                             CE.COSTID,\r\n" + 
		 		"                             CE.DEPTID,\r\n" + 
		 		"                             CE.DEPID,\r\n" + 
		 		"                             CE.DEPNAME,\r\n" + 
		 		"                             ASAW.STATUS,\r\n" + 
		 		"                             COUNT (ASAW.ID) SS\r\n" + 
		 		"                        FROM SWIPE.CSR_EMPLOYEE CE,\r\n" + 
		 		"                             SWIPE.ALERT_SWIPECARD_AB_WECHAT ASAW\r\n" + 
		 		"                       WHERE     CE.ID = ASAW.ID\r\n" + 
		 		"                             AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
		 		"                             AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
		 		"                             AND CE.DEPTID IN (SELECT DISTINCT DEPTID\r\n" + 
		 		"                                                 FROM swipe.WECHAT_USER\r\n" + 
		 		"                                                WHERE ENABLED = 1)\r\n" + 
		 		"                    GROUP BY CE.ID,\r\n" + 
		 		"                             CE.NAME,\r\n" + 
		 		"                             CE.COSTID,\r\n" + 
		 		"                             CE.DEPTID,\r\n" + 
		 		"                             CE.DEPID,\r\n" + 
		 		"                             CE.DEPNAME,\r\n" + 
		 		"                             ASAW.STATUS)\r\n" + 
		 		"          GROUP BY ID,\r\n" + 
		 		"                   NAME,\r\n" + 
		 		"                   COSTID,\r\n" + 
		 		"                   DEPTID,\r\n" + 
		 		"                   DEPID,\r\n" + 
		 		"                   DEPNAME) t\r\n" + 
		 		"ORDER BY COSTID,\r\n" + 
		 		"         DEPTID,\r\n" + 
		 		"         DEPID,\r\n" + 
		 		"         SSUM DESC";
		try {
			logger.info(strSQL);
			listMap = (List<Map<String, Object>>) jdbcTemplate.queryForList(strSQL);

		} catch (Exception e) {
			logger.info(e);
		}
		return listMap;
		 
	 }

	 
	 
	 public void EchartTaskToMail(String varStartTime,String varEndTime,String strFactory,String strWeekorMonth){
		 Date date=new Date(); 
		 SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd hh:mm:ss"); 
		 logger.info("-----------------------------EchartFQ3QToMail-Start"+sdf.format(date));
		 SwipecardABExcelUtils swipeABUtil = new SwipecardABExcelUtils();
		 List<SwipecardAB> swipecardABList = new ArrayList<SwipecardAB>();
		 String sheetName1 = strFactory+varStartTime.replace("-", "")+"-"+varEndTime.replace("-", "")+"員工異常刷卡次數報表";
		 List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> listCostID= new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> listMapTo = new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> listMapCC= new ArrayList<Map<String, Object>>();
		 
/*		    String strSQL1="  SELECT DISTINCT CE.COSTID,CE.DEPTID,CE.DEPNAME \r\n" + 
		    		"    FROM swipe.ALERT_SWIPECARD_AB_WECHAT asaw, SWIPE.CSR_EMPLOYEE ce\r\n" + 
		    		"   WHERE     asaw.id = CE.ID \r\n" + 
		    		"         AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
		    		"         AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
		    		"         AND  CE.DEPTID IN (SELECT DISTINCT DEPTID FROM swipe.WECHAT_USER WHERE ENABLED=1 AND FACTORY='"+strFactory+"')\r\n" +
		    		"ORDER BY ce.costid,ce.DEPTID";*/
		    String strSQL1="  SELECT DISTINCT dr.COSTID, dr.DEPID DEPTID, dr.DEPT_NAME DEPNAME\r\n" + 
		    		"    FROM swipe.WECHAT_USER wu, SWIPE.DEPT_RELATION dr\r\n" + 
		    		"   WHERE WU.DEPTID = dr.DEPID AND WU.ENABLED = 1 AND WU.FACTORY = '"+strFactory+"'\r\n" + 
		    		"ORDER BY dr.costid, dr.DEPID";
			String strSQL = "  SELECT ASAW.SWIPE_DATE,\r\n" + 
					"         ASAW.STATUS,\r\n" + 
					"         CE.COSTID,\r\n" + 
					"         CE.DEPTID,\r\n" + 
					"         COUNT (ASAW.ID) SUM \r\n" + 
					"    FROM swipe.ALERT_SWIPECARD_AB_WECHAT asaw, SWIPE.CSR_EMPLOYEE ce\r\n" + 
					"   WHERE     asaw.id = CE.ID \r\n" + 
					"         AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
					"         AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
					"         AND  CE.DEPTID IN (SELECT DISTINCT DEPTID FROM swipe.WECHAT_USER WHERE ENABLED=1 AND FACTORY='"+strFactory+"' )\r\n" +
					"GROUP BY ASAW.SWIPE_DATE, ASAW.STATUS, CE.COSTID,CE.DEPTID\r\n" + 
					"ORDER BY ASAW.SWIPE_DATE, ASAW.STATUS, CE.COSTID,CE.DEPTID";	
			    String strSQLTo="SELECT DISTINCT WECHAT_ID FROM swipe.WECHAT_USER WHERE ENABLED=1 AND FACTORY='"+strFactory+"'";
				String strSQLCC = "SELECT DISTINCT WECHAT_ID FROM swipe.WECHAT_USER WHERE ENABLED=1 AND deptid in ('ALL','"+strFactory+"')";	
			System.out.println( strSQL );
			
			String sstrSQL = "select b.id,b.name,b.costid,b.deptid,b.depid,b.depname,sum(case when a.status = 2 then 1 else 0 end) OVERTIME15,sum(case when a.status = 3 then 1 else 0 end) MORETHEN7,sum(case when a.status = 4 then 1 else 0 end) OUTWORK15,count(*) count "
					+ " from ALERT_SWIPECARD_AB_WECHAT a,csr_employee b where a.id = b.id and "
					+ " a.swipe_date >= '"+varStartTime+"' and a.swipe_date <= '"+varEndTime+"' and a.status != 1 and b.deptid in "
							+ " (select distinct(deptid) from wechat_user where enabled = '1' and factory = '"+strFactory+"') group by b.id,b.name,b.costid,b.depid,b.deptid,b.depname order by b.costid,b.depid,b.deptid,b.id";
			
			
		try {
			swipecardABList = jdbcTemplate.query(sstrSQL, new SwipecardABMapper());
			InputStream swipeABExcelIS = swipeABUtil.creatExcel(swipecardABList, sheetName1);
			
			listCostID = jdbcTemplate.queryForList(strSQL1);
			// System.out.println(listCostID);
			listMap = jdbcTemplate.queryForList(strSQL);
			// JSONObject jsonb = new JSONObject();
			listMapTo=jdbcTemplate.queryForList(strSQLTo);
			System.out.println(listMapTo);
			listMapCC = jdbcTemplate.queryForList(strSQLCC);
			System.out.println(listMapCC);

			// jsonb.addProperty("sucess", "true");
			List<String> listSD = new ArrayList<String>();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dStartTime = df.parse(varStartTime);
			Date dEndTime = df.parse(varEndTime);
			long li = ChronoUnit.DAYS.between(dStartTime.toInstant(), dEndTime.toInstant());// 两个时间相差多少天。
			System.out.println(li);

			List<Integer> lOne = new ArrayList<>();
			List<String> lCostID = new ArrayList<>();
			List<String> lDate = new ArrayList<>();

			for (int j = 0; j <= li; j++) {
				Calendar c = Calendar.getInstance();
				c.setTime(dStartTime);
				c.add(Calendar.DATE, j);
				boolean b = true;
				String str = df.format(c.getTime());// dStartTime的日期上面加上j天
				lDate.add("'" + str + "'");
			}

			XSSFWorkbook wb = new XSSFWorkbook();
			String sheetName = "Sheet1";
			FileOutputStream fileOut = null;
			XSSFSheet sheet = wb.createSheet(sheetName);
			int irowLength = 0;
			int idrawDataStart = 1;
			int idrawDataEnd = 5;
			int idrawLengthEnd = 26;
			int ii = 0;

			// 循环costid
			for (Map<String, Object> LCID : listCostID) {

				String strLCIDCostID = LCID.get("DEPTID") == null ? "0000" : LCID.get("DEPTID").toString();
				String strLCIDCostID2 = LCID.get("COSTID") == null ? "0000" : LCID.get("COSTID").toString();
				String strLCIDDeptName = LCID.get("DEPNAME") == null ? "0000" : LCID.get("DEPNAME").toString();
				JsonArray arrayPlayer = new JsonArray();
				lCostID.add(strLCIDCostID);
				JsonObject jbplayer = new JsonObject();
				// 第0行第0列写入日期
				Row rowData = sheet.createRow(irowLength);
				Cell cell0 = rowData.createCell(0);
				cell0.setCellValue("日期" + strLCIDCostID2 + "-" + strLCIDCostID);
				// 循环4个异常状态。
				for (int i = 1; i <= 4; i++) {
					// 第1行第0列写入状态资料
					Row row1234 = sheet.createRow(irowLength + i);
					cell0 = row1234.createCell(0);
					switch (i) {
					case 1:
						cell0.setCellValue("上班超時15分鐘刷卡");
						break;
					case 2:
						cell0.setCellValue("下班超時15分鐘刷卡");
						break;
					case 3:
						cell0.setCellValue("超7休1");
						break;
					case 4:
						cell0.setCellValue("下班超15分鐘未刷卡");
						break;
					}
					lOne.clear();

					// jbplayer.addProperty("status", i);
					// 循环天
					for (int j = 0; j <= li; j++) {
						Calendar c = Calendar.getInstance();
						c.setTime(dStartTime);
						c.add(Calendar.DATE, j);
						boolean b = true;
						String str = df.format(c.getTime());// dStartTime的日期上面加上j天
						// 第0行第1列写入具体日期，2020-07-01
						Cell cellj = rowData.createCell(j + 1);
						if (li < 10) {
							cellj.setCellValue(str);
						} else {
							cellj.setCellValue(str.substring(5));
						}

						for (Map<String, Object> LM : listMap) {
							String strSwipe_date = LM.get("SWIPE_DATE").toString();
							String strLMCOSTID = LM.get("DEPTID") == null ? "0000" : LM.get("DEPTID").toString();
							String strSTATUS = LM.get("STATUS").toString();
							int iSUM = Integer.parseInt(LM.get("SUM").toString());
							// 如果listmap中存在日期；费用代码；异常状态，值保存到数组中。
							if (strSwipe_date.equals(str) && strLMCOSTID.equals(strLCIDCostID)
									&& strSTATUS.equals(String.valueOf(i))) {
								// 第1行第1列写入数据
								cellj = row1234.createCell(j + 1);
								cellj.setCellValue(iSUM);
								lOne.add(iSUM);
								b = false;
								break;
							}
						}

						if (b) {
							cellj = row1234.createCell(j + 1);
							cellj.setCellValue(0);
						}

					} // 天
						// jbplayer.addProperty("COSTID",listCostID.toString() );

				} // 4
					// if(!strLCIDCostID.equals("8146")) {
					// 创建一个画布
				XSSFDrawing drawing = sheet.createDrawingPatriarch();
				// 前四个默认0，[0,5]：从0列5行开始;[7,26]:宽度7个单元格，26向下扩展到26行
				// 默认宽度(14-8)*12
				XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 1, idrawDataEnd, (int) li + 2,
						idrawLengthEnd);
				// 创建一个chart对象
				XSSFChart chart = drawing.createChart(anchor);
				// 标题
				chart.setTitleText(strLCIDCostID2 + "-" + strLCIDCostID + "-" + strLCIDDeptName);
				// 标题覆盖
				chart.setTitleOverlay(false);

				// 图例位置
				XDDFChartLegend legend = chart.getOrAddLegend();
				legend.setPosition(LegendPosition.TOP);

				// 分类轴标(X轴),标题位置
				XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
				bottomAxis.setTitle("日期");
				// 值(Y轴)轴,标题位置
				XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
				leftAxis.setTitle("異常數量");
				leftAxis.setMinimum(0);

				// CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
				// 分类轴标(X轴)数据，单元格范围位置[0, 0]到[0, 6]
				XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet,
						new CellRangeAddress(irowLength, irowLength, 1, (int) li + 1));
				// XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(new
				// String[] {"俄罗斯","加拿大","美国","中国","巴西","澳大利亚","印度"});
				// 数据1，单元格范围位置[1, 0]到[1, 6]
				XDDFNumericalDataSource<Double> area = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
						new CellRangeAddress(irowLength + 1, irowLength + 1, 1, (int) li + 1));
				// XDDFNumericalDataSource<Integer> area = XDDFDataSourcesFactory.fromArray(new
				// Integer[] {17098242,9984670,9826675,9596961,8514877,7741220,3287263});

				// 数据1，单元格范围位置[2, 0]到[2, 6]
				XDDFNumericalDataSource<Double> population = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
						new CellRangeAddress(irowLength + 2, irowLength + 2, 1, (int) li + 1));

				// LINE：折线图，
				XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

				// 图表加载数据，折线1
				XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(countries, area);
				// 折线图例标题
				series1.setTitle("上班超時15分鐘刷卡", null);
				// 直线
				series1.setSmooth(true);
				// 设置标记大小
				series1.setMarkerSize((short) 5);
				// 设置标记样式，星星
				series1.setMarkerStyle(MarkerStyle.STAR);

				// 图表加载数据，折线2
				XDDFLineChartData.Series series2 = (XDDFLineChartData.Series) data.addSeries(countries, population);
				// 折线图例标题
				series2.setTitle("下班超時15分鐘刷卡", null);
				// 曲线
				series2.setSmooth(true);
				// 设置标记大小
				series2.setMarkerSize((short) 5);
				// 设置标记样式，正方形
				series2.setMarkerStyle(MarkerStyle.SQUARE);

				// 图表加载数据，平均线3
				// 数据1，单元格范围位置[2, 0]到[2, 6]
				XDDFNumericalDataSource<Double> population3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
						new CellRangeAddress(irowLength + 3, irowLength + 3, 1, (int) li + 1));
				XDDFLineChartData.Series series3 = (XDDFLineChartData.Series) data.addSeries(countries, population3);
				// 折线图例标题
				series3.setTitle("超7休1", null);
				// 直线
				series3.setSmooth(true);
				// 设置标记大小
				series3.setMarkerSize((short) 5);
				// 设置标记样式，正方形
				series3.setMarkerStyle(MarkerStyle.CIRCLE);
				// 折线图LineChart
				// XDDFSolidFillProperties fill = new
				// XDDFSolidFillProperties(XDDFColor.from(PresetColor.CHARTREUSE));
				// XDDFLineProperties line = new XDDFLineProperties();
				// line.setFillProperties(fill);
				// line.setLineCap(LineCap.ROUND);
				// line.setPresetDash(new XDDFPresetLineDash(PresetLineDash.DOT));//虚线

				// XDDFShapeProperties shapeProperties = new XDDFShapeProperties();
				// shapeProperties.setLineProperties(line);
				// series3.setShapeProperties(shapeProperties);
				// series3.setLineProperties(line);

				// 图表加载数据，平均线3
				// 数据1，单元格范围位置[2, 0]到[2, 6]
				XDDFNumericalDataSource<Double> population4 = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
						new CellRangeAddress(irowLength + 4, irowLength + 4, 1, (int) li + 1));
				XDDFLineChartData.Series series4 = (XDDFLineChartData.Series) data.addSeries(countries, population4);
				// 折线图例标题
				series4.setTitle("下班超15分鐘未刷卡", null);
				// 直线
				series4.setSmooth(true);
				// 设置标记大小
				series4.setMarkerSize((short) 5);
				// 设置标记样式，正方形
				series4.setMarkerStyle(MarkerStyle.PLUS);
				// XDDFLineProperties line4 = new XDDFLineProperties();
				// line4.setPresetDash(new XDDFPresetLineDash(PresetLineDash.DOT));//虚线
				// series4.setLineProperties(line);

				// 绘制
				chart.plot(data);

				String string = "上班超時15分鐘刷卡";
				int iLength = string.getBytes().length;
				sheet.setColumnWidth((short) 0, iLength * 256);// 第一列是中文所以手动设置列宽。

				for (int b = 1; b <= li + 1; b++) {
					sheet.autoSizeColumn((short) b); // 调整第二列宽度
				}

				irowLength = irowLength + 28;
				idrawDataStart = idrawDataStart + 28;
				idrawDataEnd = idrawDataEnd + 28;
				idrawLengthEnd = idrawLengthEnd + 28;
				ii += ii + 10000 * 10;

			}

			// 将输出写入excel文件
			String sST = varStartTime.replace("-", "");
			String sSE = varEndTime.replace("-", "");
			String sName = strFactory  + sST + "-" + sSE;
			File file =new File("C:\\ExcelBak");
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       			    
			    file .mkdir();  //不存在就创建  
			}

			String filename = "C:\\ExcelBak\\" + sName + ".xlsx";

			fileOut = new FileOutputStream(filename);
			wb.write(fileOut);



					 // 设置多个收件人地址  
			        String StrTO = "";
			        for (Map<String, Object> ToID : listMapTo) {
			        	StrTO=StrTO+ToID.get("WECHAT_ID").toString()+"@foxlink.com.tw,";
			        }
			        if(!StrTO.isEmpty()) StrTO=StrTO.substring(0, StrTO.length()-1);
			        System.out.println(StrTO);
			     // 设置多个抄送地址  
			        String StrCC = "";
			        for (Map<String, Object> CCID : listMapCC) {
			        	StrCC=StrCC+CCID.get("WECHAT_ID").toString()+"@foxlink.com.tw,";
			        }
			        //StrCC=StrCC+"DONGPING_LIN"+"@foxlink.com.tw,";
			        if(!StrCC.isEmpty()) StrCC=StrCC.substring(0, StrCC.length()-1);
			        System.out.println(StrCC);
			        if(StrCC.isEmpty()&&StrTO.isEmpty()) return;			       
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
					        if(!StrCC.isEmpty()) {
					           // 设置多个抄送地址  		
				        	   InternetAddress[] CCAddress = InternetAddress.parse(StrCC);
				        	   msg.setRecipients(RecipientType.CC, CCAddress);
					        }
					        
					        
					        // 设置邮件标题
				        	String strject=strWeekorMonth+strFactory+"員工異常刷卡趨勢圖和次數報表";
					        msg.setSubject(strject, "utf-8");
					        
					       
					        // 设置显示的发件时间
					        msg.setSentDate(new Date());
					        // 保存设置
					       
					      //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
					        MimeMultipart multipart = new MimeMultipart();
					      //创建文本节点
					        MimeBodyPart text = new MimeBodyPart();
					        String strContent="各位長官好：<BR>附件爲"+strWeekorMonth+strFactory+"員工"+sST + "-" + sSE+"異常刷卡趨勢圖和次數報表，<BR>請查閲，謝謝。";
					        text.setContent(strContent,"text/html;charset=UTF-8");
					        //创建xsl附件节点
					        MimeBodyPart file2 = new MimeBodyPart();					        
					        DataHandler dataHandler3 = new DataHandler(new FileDataSource(filename));
					        file2.setDataHandler(dataHandler3);
					        file2.setFileName(MimeUtility.encodeWord(dataHandler3.getName()));
					      //将文本和图片添加到multipart
					        /*添加附件*/
			                MimeBodyPart fileBody = new MimeBodyPart();
			                DataSource source = new ByteArrayDataSource(swipeABExcelIS, "application/msexcel;charset=UTF-8");
			                fileBody.setDataHandler(new DataHandler(source));
			                String fileName  = sheetName1+".xlsx";
			                // 中文乱码问题
			                fileBody.setFileName(MimeUtility.encodeWord(fileName,"UTF-8",null));
					        multipart.addBodyPart(text);
					        multipart.addBodyPart(file2);
					        multipart.addBodyPart(fileBody);
					        multipart.setSubType("mixed");//混合关系
					        
					        msg.setContent(multipart);
					       // msg.setContent("Test","text/html;charset=UTF-8");
					        msg.saveChanges();
					        Transport.send(msg);
					        logger.info("------------------------------------SendMailOK "+sdf.format(date));
						
					}
					catch(Exception ex){
						logger.info(ex);
						
					}finally {
						swipeABExcelIS.close();
					}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e);
		}	
		
			
			//return jsonb ;
	 }

	    
}
