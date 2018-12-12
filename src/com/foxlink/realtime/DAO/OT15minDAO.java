package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.EmpOT15min;
import com.foxlink.realtime.model.objectMapper.EmpOT15minMapper;

public class OT15minDAO extends DAO<EmpOT15min>{
	private static Logger logger=Logger.getLogger(OT15minDAO.class);

	@Override
	public boolean AddNewRecord(EmpOT15min newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpOT15min updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpOT15min> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpOT15min> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpOT15min> FindRecord(String userDataCostId, int currentPage, int totalRecord, EmpOT15min t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpOT15min> FindRecords(EmpOT15min t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, EmpOT15min t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<EmpOT15min> FindRecords(String userDataCostId, EmpOT15min t) {
		// TODO Auto-generated method stub
		List<EmpOT15min> empList = null;
		String sql ="SELECT ce.id, ce.Name, ce.costid, ce.depid, ce.depname, cs.class_no, to_char(cs.SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') AS SwipeCardTimeg"
				+ " , to_char(cs.SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') AS SwipeCardTimeo, c.class_start, CASE "
				+ " WHEN (TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600 < 0 THEN NULL"
				+ " ELSE (TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600"
				+ " END AS goWorkAdvance, c.overtime_start, CASE WHEN (cs.swipecardtime2 - TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 < 0 THEN NULL"
				+ " ELSE (cs.swipecardtime2 - TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600"
				+ " END AS outWorkOvertime FROM swipe.csr_swipecardtime cs, swipe.csr_employee ce, swipe.classno c"
				+ " WHERE ce.id = cs.emp_id AND cs.class_no = c.class_no AND cs.swipe_date >= ? AND cs.swipe_date <= ?"
				+ " AND ((TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600 >= 1"
				+ " OR (cs.swipecardtime2 - TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 >= 1) AND cs.shift = 'D'"
				+ "";
		
		try{
			List<Object> queryList = new ArrayList<Object>();
			String strId = t.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (t.getId() != ""&&t.getId() != null) {
				sql += " and ce.id in (" + idsStr + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			String strDepid = t.getDepid();
			String strDepidArray[] = strDepid.split(",");
			StringBuffer depidsStr = new StringBuffer();
			for (int i = 0; i < strDepidArray.length; i++) {
				if (i > 0) {
					depidsStr.append(",");
				}
				depidsStr.append("'").append(strDepidArray[i]).append("'");
			}
			if (t.getDepid() != "") {
				sql += " and ce.depid in (" + depidsStr + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			String strcostid = t.getCostid();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (t.getCostid() != "") {
				sql += " and ce.costid in (" + costidsStr + ") ";
				//queryList.add(querySwipeCard.getCostid());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and ce.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			}else{
				sql += " and ce.costId in('')";
			}
			
			queryList.add(t.getTimeStart());
			queryList.add(t.getTimeEnd());
			
			sql+=" union SELECT ce.id, ce.Name, ce.costid, ce.depid, ce.depname, cs.class_no, to_char(cs.SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') AS SwipeCardTimeg"
					+ " , to_char(cs.SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') AS SwipeCardTimeo, c.class_start, CASE "
					+ " WHEN (TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600 < 0 THEN NULL"
					+ " ELSE (TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600"
					+ " END AS goWorkAdvance, c.overtime_start, CASE "
					+ " WHEN (cs.swipecardtime2 - TO_DATE(TO_CHAR(TO_DATE(cs.swipe_date, 'yyyy-mm-dd') + 1, 'yyyy-mm-dd') || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 < 0 THEN NULL"
					+ " ELSE (cs.swipecardtime2 - TO_DATE(TO_CHAR(TO_DATE(cs.swipe_date, 'yyyy-mm-dd') + 1, 'yyyy-mm-dd') || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600"
					+ " END AS outWorkOvertime FROM swipe.csr_swipecardtime cs, swipe.csr_employee ce, swipe.classno c WHERE ce.id = cs.emp_id AND cs.class_no = c.class_no"
					+ " AND cs.swipe_date >= ? AND cs.swipe_date <= ?"
					+ " AND ((TO_DATE(cs.swipe_date || ' ' || SUBSTR(c.class_start, 1, 2) || ':' || SUBSTR(c.class_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 1 / 96 - cs.swipecardtime) * 24 * 3600 >= 1"
					+ " OR (cs.swipecardtime2 - TO_DATE(TO_CHAR(TO_DATE(cs.swipe_date, 'yyyy-mm-dd') + 1, 'yyyy-mm-dd') || ' ' || SUBSTR(c.overtime_start, 1, 2) || ':' || SUBSTR(c.overtime_start, 3, 2) || ':00', 'yyyy-mm-dd hh24:mi:ss') - 9 / 96) * 24 * 3600 >= 1) AND cs.shift = 'N'";
			
			String strId1 = t.getId();
			String strIdArray1[] = strId1.split(",");
			StringBuffer idsStr1 = new StringBuffer();
			for (int i = 0; i < strIdArray1.length; i++) {
				if (i > 0) {
					idsStr1.append(",");
				}
				idsStr1.append("'").append(strIdArray1[i]).append("'");
			}
			if (t.getId() != ""&&t.getId() != null) {
				sql += " and ce.id in (" + idsStr1 + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			
			String strDepid1 = t.getDepid();
			String strDepidArray1[] = strDepid1.split(",");
			StringBuffer depidsStr1 = new StringBuffer();
			for (int i = 0; i < strDepidArray1.length; i++) {
				if (i > 0) {
					depidsStr1.append(",");
				}
				depidsStr1.append("'").append(strDepidArray1[i]).append("'");
			}
			if (t.getDepid() != "") {
				sql += " and ce.depid in (" + depidsStr1 + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			
			String strcostid1 = t.getCostid();
			String strCostidArray1[] = strcostid1.split(",");
			StringBuffer costidsStr1 = new StringBuffer();
			for (int i = 0; i < strCostidArray1.length; i++) {
				if (i > 0) {
					costidsStr1.append(",");
				}
				costidsStr1.append("'").append(strCostidArray1[i]).append("'");
			}
			if (t.getCostid() != "") {
				sql += " and ce.costid in (" + costidsStr1 + ") ";
				//queryList.add(querySwipeCard.getCostid());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and ce.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			}else{
				sql += " and ce.costId in('')";
			}
			
			queryList.add(t.getTimeStart());
			queryList.add(t.getTimeEnd());
			
			sql+=" ORDER BY 1";
			System.out.println(sql);
			empList = jdbcTemplate.query(sql, queryList.toArray(),new EmpOT15minMapper());
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("查詢上下班超15分鐘"+ex);
		}
		
		return empList;
	}

	public List<EmpOT15min> FindRecordsHistory(String userDataCostId, EmpOT15min t) {
		// TODO Auto-generated method stub
		List<EmpOT15min> empList = null;
		String sql = "SELECT EMP.ID,EMP.Name,EMP.costID,EMP.Depid,Emp.Depname,CLO.Class_No,to_char(EMT.SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') swipecardtimeg,to_char(EMT.SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') swipecardtimeo,CLO.Class_Start,TO_CHAR((TO_NUMBER ((SUBSTR (CLO.class_start, 1, 2))) * 3600 "
				+ "+ TO_NUMBER ((SUBSTR (CLO.class_start, 3, 2))) * 60 - 900) - (    TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime, 'hh24:mi:ss'),1,2)))* 3600+ TO_NUMBER  "
				+ "((SUBSTR (TO_CHAR (EMT.SwipeCardTime, 'hh24:mi:ss'), 4,2)))* 60+ TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime, 'hh24:mi:ss'), 7, 2))))) goWorkAdvance,CLO.Class_End as overtime_start, "
				+ "'' AS outWorkOvertime, TO_CHAR (EMT.SwipeCardTime, 'yyyy-mm-dd') AS 班別日期 FROM CSR_SWIPECARDTIME_MS EMT, CSR_EMPLOYEE EMP,EMP_CLASS_MS EMCLO,classno CLO "
				+ "WHERE 1 = 1 AND EMT.EMP_ID = EMP.ID AND EMP.ID = EMCLO.ID AND TO_CHAR (EMT.SwipeCardTime, 'yyyy-mm-dd') =? AND EMCLO.emp_date = TO_DATE (?, 'yyyy-mm-dd') AND EMCLO.class_no = CLO.class_no "
				+ "AND TO_NUMBER((SUBSTR (TO_CHAR (EMT.SwipeCardTime, 'hh24:mi:ss'), 1, 2))) * 3600 + TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime, 'hh24:mi:ss'), 4, 2))) * 60 "
				+ "+ TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime, 'hh24:mi:ss'), 7, 2))) < TO_NUMBER ((SUBSTR (CLO.class_start, 1, 2))) * 3600+ TO_NUMBER ((SUBSTR (CLO.class_start, 3, 2))) * 60 - 900 ";
		
		try{
			List<Object> queryList = new ArrayList<Object>();
			String strId = t.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (t.getId() != ""&&t.getId() != null) {
				sql += " and EMP.id in (" + idsStr + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			String strDepid = t.getDepid();
			String strDepidArray[] = strDepid.split(",");
			StringBuffer depidsStr = new StringBuffer();
			for (int i = 0; i < strDepidArray.length; i++) {
				if (i > 0) {
					depidsStr.append(",");
				}
				depidsStr.append("'").append(strDepidArray[i]).append("'");
			}
			if (t.getDepid() != "") {
				sql += " and EMP.depid in (" + depidsStr + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			String strcostid = t.getCostid();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (t.getCostid() != "") {
				sql += " and EMP.costid in (" + costidsStr + ") ";
				//queryList.add(querySwipeCard.getCostid());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and EMP.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			}else{
				sql += " and EMP.costId in('') ";
			}
			for(int i = 0;i<2;i++){
				queryList.add(t.getTimeStart());
			}
			
			sql += "UNION ALL SELECT EMP.ID,EMP.Name,EMP.costID,EMP.Depid,Emp.Depname,CLO.Class_No,to_char(EMT.SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') swipecardtimeg,to_char(EMT.SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') swipecardtimeo,CLO.Class_Start,'' AS goWorkAdvance,CLO.Class_End as overtime_start, TO_CHAR((TO_NUMBER((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'),1,2))) * 3600 + TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'),4,2))) * 60 + TO_NUMBER((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'),7,2)))) - (TO_NUMBER ((SUBSTR (CLO.overtime_start, 1, 2))) * 3600+ TO_NUMBER ((SUBSTR (CLO.overtime_start, 3, 2))) * 60+ 8100)) outWorkOvertime,TO_CHAR (EMT.SwipeCardTime2, 'yyyy-mm-dd') 班別日期 FROM CSR_SWIPECARDTIME_MS  EMT,CSR_employee EMP,EMP_CLASS_MS EMCLO,classno CLO WHERE 1 = 1 AND EMT.EMP_ID = EMP.ID AND EMP.ID = EMCLO.ID AND EMT.Shift = 'D' AND TO_CHAR (EMT.SwipeCardTime2, 'yyyy-mm-dd') =? AND EMCLO.emp_date = TO_DATE (?, 'yyyy-mm-dd') AND EMCLO.class_no = CLO.class_no AND TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 1, 2))) * 3600 + TO_NUMBER ( (SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 4, 2))) * 60 + TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 7, 2))) >TO_NUMBER ((SUBSTR (CLO.overtime_start, 1, 2))) * 3600 + TO_NUMBER ((SUBSTR (CLO.overtime_start, 3, 2))) * 60 + 8100";
			String strId1 = t.getId();
			String strIdArray1[] = strId1.split(",");
			StringBuffer idsStr1 = new StringBuffer();
			for (int i = 0; i < strIdArray1.length; i++) {
				if (i > 0) {
					idsStr1.append(",");
				}
				idsStr1.append("'").append(strIdArray1[i]).append("'");
			}
			if (t.getId() != ""&&t.getId() != null) {
				sql += " and EMP.id in (" + idsStr1 + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			
			String strDepid1 = t.getDepid();
			String strDepidArray1[] = strDepid1.split(",");
			StringBuffer depidsStr1 = new StringBuffer();
			for (int i = 0; i < strDepidArray1.length; i++) {
				if (i > 0) {
					depidsStr1.append(",");
				}
				depidsStr1.append("'").append(strDepidArray1[i]).append("'");
			}
			if (t.getDepid() != "") {
				sql += " and EMP.depid in (" + depidsStr1 + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			
			String strcostid1 = t.getCostid();
			String strCostidArray1[] = strcostid1.split(",");
			StringBuffer costidsStr1 = new StringBuffer();
			for (int i = 0; i < strCostidArray1.length; i++) {
				if (i > 0) {
					costidsStr1.append(",");
				}
				costidsStr1.append("'").append(strCostidArray1[i]).append("'");
			}
			if (t.getCostid() != "") {
				sql += " and EMP.costid in (" + costidsStr1 + ") ";
				//queryList.add(querySwipeCard.getCostid());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and EMP.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			}else{
				sql += " and EMP.costId in('')";
			}
			for(int i = 0;i<2;i++){
				queryList.add(t.getTimeStart());
			}
			
			sql += "UNION ALL SELECT EMP.ID,EMP.Name,EMP.costID,EMP.Depid,Emp.Depname,CLO.Class_No,to_char(EMT.SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') swipecardtimeg,to_char(EMT.SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') swipecardtimeo,CLO.Class_Start,''AS goWorkAdvance,CLO.Class_End as overtime_start, "
					+ "TO_CHAR((TO_NUMBER((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 1,2)))* 3600+TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 4,2)))* 60 "
					+ "+ TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'),7,2))))- (  TO_NUMBER ((SUBSTR (CLO.overtime_start, 1, 2))) * 3600+ TO_NUMBER ((SUBSTR (CLO.overtime_start, 3, 2))) * 60 "
					+ "+ 8100))outWorkOvertime,TO_CHAR (EMT.SwipeCardTime2 - 1, 'yyyy-mm-dd') 班別日期 FROM CSR_SWIPECARDTIME_MS EMT,CSR_employee EMP,EMP_CLASS_MS EMCLO,classno CLO "
					+ "WHERE 1 = 1 AND EMT.EMP_ID = EMP.ID AND EMP.ID = EMCLO.ID AND EMT.Shift = 'N'AND TO_CHAR (EMT.SwipeCardTime2, 'yyyy-mm-dd') =to_char(to_date(?,'yyyy-mm-dd')+1,'yyyy-mm-dd') "
					+ "AND EMCLO.emp_date = TO_DATE (?, 'yyyy-mm-dd') AND EMCLO.class_no = CLO.class_no AND TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 1, 2))) "
					+ " * 3600 +TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 4, 2)))* 60+ TO_NUMBER ((SUBSTR (TO_CHAR (EMT.SwipeCardTime2, 'hh24:mi:ss'), 7, 2))) >TO_NUMBER ((SUBSTR (CLO.overtime_start, 1, 2))) * 3600  "
					+ " + TO_NUMBER ((SUBSTR (CLO.overtime_start, 3, 2))) * 60 + 8100 ";
			String strId2 = t.getId();
			String strIdArray2[] = strId2.split(",");
			StringBuffer idsStr2 = new StringBuffer();
			for (int i = 0; i < strIdArray2.length; i++) {
				if (i > 0) {
					idsStr2.append(",");
				}
				idsStr2.append("'").append(strIdArray2[i]).append("'");
			}
			if (t.getId() != ""&&t.getId() != null) {
				sql += " and EMP.id in (" + idsStr2 + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			
			String strDepid2 = t.getDepid();
			String strDepidArray2[] = strDepid2.split(",");
			StringBuffer depidsStr2 = new StringBuffer();
			for (int i = 0; i < strDepidArray2.length; i++) {
				if (i > 0) {
					depidsStr2.append(",");
				}
				depidsStr2.append("'").append(strDepidArray2[i]).append("'");
			}
			if (t.getDepid() != "") {
				sql += " and EMP.depid in (" + depidsStr2 + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			
			String strcostid2 = t.getCostid();
			String strCostidArray2[] = strcostid2.split(",");
			StringBuffer costidsStr2 = new StringBuffer();
			for (int i = 0; i < strCostidArray2.length; i++) {
				if (i > 0) {
					costidsStr2.append(",");
				}
				costidsStr2.append("'").append(strCostidArray2[i]).append("'");
			}
			if (!t.getCostid().equals("")) {
				sql += " and EMP.costid in (" + costidsStr2 + ") ";
				//queryList.add(querySwipeCard.getCostid());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and EMP.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sql += ",";
						else
							sql += ") ";
					}
				}
			}else{
				sql += " and EMP.costId in('')";
			}
			for(int i = 0;i<2;i++){
				queryList.add(t.getTimeStart());
			}
			
			sql+=" ORDER BY 3,1";
			System.out.println(sql);
			empList = jdbcTemplate.query(sql, queryList.toArray(),new EmpOT15minMapper());
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("查詢上下班超15分鐘"+ex);
		}
		
		return empList;
	}

}
