package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.model.ThreeMergeOne;
import com.foxlink.realtime.model.ThreeMergeOneGetNum;
import com.foxlink.realtime.model.objectMapper.QueryMergeData;
import com.foxlink.realtime.model.objectMapper.QueryMergeDataGetNum;

public class ThreeMergeOneDAO extends DAO<ThreeMergeOne>{
	private static Logger logger = Logger.getLogger(ThreeMergeOneDAO.class);

	public List<ThreeMergeOne> searchData(String startDate, String endDate, String type, String data) {
		// TODO Auto-generated method stub
		List<ThreeMergeOne> result = null;
		String sSQl ="  SELECT tio.emp_id," + 
				"         ce.name," + 
				"         tio.swipecardtime," + 
				"         tio.record_type" + 
				"    FROM csr_employee ce," + 
				"         (SELECT id emp_id, swipecardtime swipecardtime, '實時' record_type" + 
				"            FROM raw_record" ;
				
				if(type.equals("empid")) {
					sSQl+=  "           WHERE id = ?" + 
							"                 AND record_status IN ('0', '3', '4', '5', '6', '7')" + 
							"                 AND swipecardtime >=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"                 AND swipecardtime <=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"          UNION" + 
							"          SELECT userid emp_id," + 
							"                 swipedatetime swipecardtime," + 
							"                 '門禁' record_type" + 
							"            FROM swipe_info" + 
							"           WHERE userid = ?" + 
							"                 AND swipedatetime >=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"                 AND swipedatetime <=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"          UNION" + 
							"          SELECT ygbh emp_id, dates swipecardtime, '臉模' record_type" + 
							"            FROM kq_record" + 
							"           WHERE ygbh = ?" + 
							"                 AND dates >=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"                 AND dates <=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')) tio" + 
							"   WHERE ce.id = tio.emp_id ORDER BY emp_id";
				}else if(type.equals("depid")) {
					sSQl+=  "           WHERE record_status IN ('0', '3', '4', '5', '6', '7')" + 
							"                 AND swipecardtime >=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"                 AND swipecardtime <=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"          UNION" + 
							"          SELECT userid emp_id," + 
							"                 swipedatetime swipecardtime," + 
							"                 '門禁' record_type" + 
							"            FROM swipe_info" + 
							"           WHERE swipedatetime >=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"                 AND swipedatetime <=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"          UNION" + 
							"          SELECT ygbh emp_id, dates swipecardtime, '臉模' record_type" + 
							"            FROM kq_record" + 
							"           WHERE dates >=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')" + 
							"                 AND dates <=" + 
							"                        TO_DATE (?," + 
							"                                 'yyyy-mm-dd hh24:mi:ss')) tio" + 
							"   WHERE ce.id = tio.emp_id and ce.isonwork=0 and ce.depid=? ORDER BY emp_id";
				}else if(type.equals("costid")) {
					sSQl+=  	 "           WHERE record_status IN ('0', '3', '4', '5', '6', '7')" + 
								"                 AND swipecardtime >=" + 
								"                        TO_DATE (?," + 
								"                                 'yyyy-mm-dd hh24:mi:ss')" + 
								"                 AND swipecardtime <=" + 
								"                        TO_DATE (?," + 
								"                                 'yyyy-mm-dd hh24:mi:ss')" + 
								"          UNION" + 
								"          SELECT userid emp_id," + 
								"                 swipedatetime swipecardtime," + 
								"                 '門禁' record_type" + 
								"            FROM swipe_info" + 
								"           WHERE swipedatetime >=" + 
								"                        TO_DATE (?," + 
								"                                 'yyyy-mm-dd hh24:mi:ss')" + 
								"                 AND swipedatetime <=" + 
								"                        TO_DATE (?," + 
								"                                 'yyyy-mm-dd hh24:mi:ss')" + 
								"          UNION" + 
								"          SELECT ygbh emp_id, dates swipecardtime, '臉模' record_type" + 
								"            FROM kq_record" + 
								"           WHERE dates >=" + 
								"                        TO_DATE (?," + 
								"                                 'yyyy-mm-dd hh24:mi:ss')" + 
								"                 AND dates <=" + 
								"                        TO_DATE (?," + 
								"                                 'yyyy-mm-dd hh24:mi:ss')) tio" + 
								"   WHERE ce.id = tio.emp_id and ce.isonwork=0 and ce.costid=? ORDER BY emp_id";
				}
				 
		try {
			result=jdbcTemplate.query(sSQl, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					// TODO Auto-generated method stub
					if(type.equals("empid")) {
						ps.setString(1, data);
						ps.setString(2, startDate);
						ps.setString(3, endDate);
						ps.setString(4, data);
						ps.setString(5, startDate);
						ps.setString(6, endDate);
						ps.setString(7, data);
						ps.setString(8, startDate);
						ps.setString(9, endDate);
					}else if(type.equals("depid")) {
						ps.setString(1, startDate);
						ps.setString(2, endDate);
						ps.setString(3, startDate);
						ps.setString(4, endDate);
						ps.setString(5, startDate);
						ps.setString(6, endDate);
						ps.setString(7, data);
					}else if(type.equals("costid")) {
						ps.setString(1, startDate);
						ps.setString(2, endDate);
						ps.setString(3, startDate);
						ps.setString(4, endDate);
						ps.setString(5, startDate);
						ps.setString(6, endDate);
						ps.setString(7, data);
					}
				}
			},new QueryMergeData());
		} catch (Exception e) {
			// TODO: handle exception
			 logger.error("Find ThreeMergeOneData TotalRecord are failed ",e);
   		  	 e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean AddNewRecord(ThreeMergeOne newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ThreeMergeOne updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ThreeMergeOne> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThreeMergeOne> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThreeMergeOne> FindRecord(String userDataCostId, int currentPage, int totalRecord, ThreeMergeOne t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThreeMergeOne> FindRecords(ThreeMergeOne t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, ThreeMergeOne t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<ThreeMergeOneGetNum> searchNum(String startDate, String endDate, String type, String data) {
		// TODO Auto-generated method stub
		List<ThreeMergeOneGetNum> result = null;
		String sSQL ="  SELECT DISTINCT(emp_id),name  " + 
				"    FROM csr_employee ce," + 
				"         (SELECT id emp_id, swipecardtime swipecardtime, '實時' record_type" + 
				"            FROM raw_record" ;
		if(type.equals("empid")) {
			sSQL+=  "           WHERE id = ?" + 
					"                 AND record_status IN ('0', '3', '4', '5', '6', '7')" + 
					"                 AND swipecardtime >=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"                 AND swipecardtime <=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"          UNION" + 
					"          SELECT userid emp_id," + 
					"                 swipedatetime swipecardtime," + 
					"                 '門禁' record_type" + 
					"            FROM swipe_info" + 
					"           WHERE userid = ?" + 
					"                 AND swipedatetime >=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"                 AND swipedatetime <=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"          UNION" + 
					"          SELECT ygbh emp_id, dates swipecardtime, '臉模' record_type" + 
					"            FROM kq_record" + 
					"           WHERE ygbh = ?" + 
					"                 AND dates >=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"                 AND dates <=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')) tio" + 
					"   WHERE ce.id = tio.emp_id ORDER BY emp_id";
		}else if(type.equals("depid")) {
			sSQL+=  "           WHERE record_status IN ('0', '3', '4', '5', '6', '7')" + 
					"                 AND swipecardtime >=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"                 AND swipecardtime <=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"          UNION" + 
					"          SELECT userid emp_id," + 
					"                 swipedatetime swipecardtime," + 
					"                 '門禁' record_type" + 
					"            FROM swipe_info" + 
					"           WHERE swipedatetime >=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"                 AND swipedatetime <=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"          UNION" + 
					"          SELECT ygbh emp_id, dates swipecardtime, '臉模' record_type" + 
					"            FROM kq_record" + 
					"           WHERE dates >=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')" + 
					"                 AND dates <=" + 
					"                        TO_DATE (?," + 
					"                                 'yyyy-mm-dd hh24:mi:ss')) tio" + 
					"   WHERE ce.id = tio.emp_id and ce.isonwork=0 and ce.depid=? ORDER BY emp_id";
		}else if(type.equals("costid")) {
			sSQL+=  	 "           WHERE record_status IN ('0', '3', '4', '5', '6', '7')" + 
						"                 AND swipecardtime >=" + 
						"                        TO_DATE (?," + 
						"                                 'yyyy-mm-dd hh24:mi:ss')" + 
						"                 AND swipecardtime <=" + 
						"                        TO_DATE (?," + 
						"                                 'yyyy-mm-dd hh24:mi:ss')" + 
						"          UNION" + 
						"          SELECT userid emp_id," + 
						"                 swipedatetime swipecardtime," + 
						"                 '門禁' record_type" + 
						"            FROM swipe_info" + 
						"           WHERE swipedatetime >=" + 
						"                        TO_DATE (?," + 
						"                                 'yyyy-mm-dd hh24:mi:ss')" + 
						"                 AND swipedatetime <=" + 
						"                        TO_DATE (?," + 
						"                                 'yyyy-mm-dd hh24:mi:ss')" + 
						"          UNION" + 
						"          SELECT ygbh emp_id, dates swipecardtime, '臉模' record_type" + 
						"            FROM kq_record" + 
						"           WHERE dates >=" + 
						"                        TO_DATE (?," + 
						"                                 'yyyy-mm-dd hh24:mi:ss')" + 
						"                 AND dates <=" + 
						"                        TO_DATE (?," + 
						"                                 'yyyy-mm-dd hh24:mi:ss')) tio" + 
						"   WHERE ce.id = tio.emp_id and ce.isonwork=0 and ce.costid=? ORDER BY emp_id";
		}
		try {
			result=jdbcTemplate.query(sSQL, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					// TODO Auto-generated method stub
					if(type.equals("empid")) {
						ps.setString(1, data);
						ps.setString(2, startDate);
						ps.setString(3, endDate);
						ps.setString(4, data);
						ps.setString(5, startDate);
						ps.setString(6, endDate);
						ps.setString(7, data);
						ps.setString(8, startDate);
						ps.setString(9, endDate);
					}else if(type.equals("depid")) {
						ps.setString(1, startDate);
						ps.setString(2, endDate);
						ps.setString(3, startDate);
						ps.setString(4, endDate);
						ps.setString(5, startDate);
						ps.setString(6, endDate);
						ps.setString(7, data);
					}else if(type.equals("costid")) {
						ps.setString(1, startDate);
						ps.setString(2, endDate);
						ps.setString(3, startDate);
						ps.setString(4, endDate);
						ps.setString(5, startDate);
						ps.setString(6, endDate);
						ps.setString(7, data);
					}
				}
			},new QueryMergeDataGetNum());
		} catch (Exception e) {
			// TODO: handle exception
			 logger.error("Find ThreeMergeOneGetNum TotalRecord are failed ",e);
   		  	 e.printStackTrace();
		}
		return result;
	}
}
