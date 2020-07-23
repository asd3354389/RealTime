package com.foxlink.realtime.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.From;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxlink.realtime.model.QueryWorkDayCount;
import com.foxlink.realtime.model.objectMapper.QueryEmpIPBindingMapper;
import com.foxlink.realtime.model.objectMapper.QueryWorkDayCountMapper;
import com.google.gson.JsonArray;
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
		    String strSQL1="  SELECT DISTINCT CE.COSTID,CE.DEPTID,CE.DEPNAME \r\n" + 
		    		"    FROM swipe.ALERT_SWIPECARD_AB_WECHAT asaw, SWIPE.CSR_EMPLOYEE ce\r\n" + 
		    		"   WHERE     asaw.id = CE.ID \r\n" + 
		    		"         AND ASAW.SWIPE_DATE >= '"+varStartTime+"'\r\n" + 
		    		"         AND ASAW.SWIPE_DATE <= '"+varEndTime+"'\r\n" + 
		    		"         AND  CE.DEPTID IN (SELECT DISTINCT DEPTID FROM swipe.WECHAT_USER WHERE ENABLED=1)\r\n" +
		    		"ORDER BY ce.costid,ce.DEPTID";
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
		try {
			logger.info(strSQL);
			listMap = (List<Map<String, Object>>) jdbcTemplate.queryForList(strSQL);

		} catch (Exception e) {
			logger.info(e);
		}
		return listMap;
		 
	 }
}
