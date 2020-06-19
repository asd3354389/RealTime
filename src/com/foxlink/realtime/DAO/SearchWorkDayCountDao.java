package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryWorkDayCount;
import com.foxlink.realtime.model.objectMapper.QuerySCMapper;
import com.foxlink.realtime.model.objectMapper.QueryWorkDayCountMapper;

public class SearchWorkDayCountDao extends DAO<QueryWorkDayCount> {

	public int getTotalRecords(String userDataCostId, String type, String data) {
		// TODO Auto-generated method stub
		int totalRecord = -1;
//		System.out.println("查詢模型==============>>"+querySwipeCard.getDeptid());
		String sSql = "select count(*) from CSR_EMPLOYEE a,EMP_CONTINUOUS_WORKING_DAYS b where a.id = b.EMP_ID ";

		try {
			if(type.equals("empid")) {
				sSql += " and a.id = '"+data+"'";
			}else if(type.equals("depid")) {
				sSql += " and a.depid = '"+data+"' ";
			}else if(type.equals("costid")) {
				if (!data.equals("ALL")) {
					sSql += " and a.costid = '"+data+"' ";
				}
			}
			sSql += " and b.CONTINUOUS_DAYS >= 5";
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSql += " and costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sSql += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sSql += ",";
						else
							sSql += ") ";
					}
				}
			}else{
				sSql += " and costId in('')";
			}
			
			System.out.println("查詢總記錄數==============>>"+sSql);
			totalRecord = jdbcTemplate.queryForObject(sSql, Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return totalRecord;
	}

	@Override
	public boolean AddNewRecord(QueryWorkDayCount newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryWorkDayCount updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryWorkDayCount> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryWorkDayCount> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryWorkDayCount> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryWorkDayCount> FindRecords(QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, QueryWorkDayCount t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<QueryWorkDayCount> FindRecord(String userDataCostId, int currentPage, int totalRecord,String type, String data) {
//		COUNT (*) OVER () totalPage
		// TODO Auto-generated method stub
		String sql = " SELECT id,name,depid,costid,START_DATE,END_DATE,CONTINUOUS_DAYS FROM (select a.*,rownum as rnum from (SELECT id,name,depid,costid,START_DATE,END_DATE,CONTINUOUS_DAYS from swipe.csr_employee a, swipe.EMP_CONTINUOUS_WORKING_DAYS b WHERE a.id = b.emp_id ";
		List<QueryWorkDayCount> querySwipeCards = null;
		try {
			if(type.equals("empid")) {
				sql += " and a.id = ? ";
			}else if(type.equals("depid")) {
				sql += " and a.depid = ? ";
			}else if(type.equals("costid")) {
				if (!data.equals("ALL")) {
					sql += " and a.costid = ? ";
				}
			}
			sql += " and b.CONTINUOUS_DAYS >= 5 ";
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and costId in(";
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
				sql += " and costId in('')";
			}
			
			Page page = new Page(currentPage, totalRecord);
			int endIndex = page.getStartIndex() + page.getPageSize();
//			sql += " order by emp.depid,emp.id,swipe_date) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			sql += " order by a.deptid,a.id,START_DATE) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			System.out.println("分頁查詢=============>>"+sql);
			querySwipeCards = jdbcTemplate.query(sql, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					// TODO Auto-generated method stub
//					ps.setString(1, data);
//					ps.setString(2, startDate);
//					ps.setString(3, endDate);
					if (type.equals("costid")) {
						if (userDataCostId.equals("ALL")) {
							
						}else {
							ps.setString(1, data);
							
						}
					}else {
						ps.setString(1, data);
						
					}
				}
			}, new QueryWorkDayCountMapper());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return querySwipeCards;
	}

	public List<QueryWorkDayCount> FindRecords(String userDataCostId, String type,
			String data) {
		// TODO Auto-generated method stub
		String sql = " SELECT id,name,depid,costid,START_DATE,END_DATE,CONTINUOUS_DAYS FROM (select a.* from (SELECT id,name,depid,costid,START_DATE,END_DATE,CONTINUOUS_DAYS from swipe.csr_employee a, swipe.EMP_CONTINUOUS_WORKING_DAYS b WHERE a.id = b.emp_id ";
		List<QueryWorkDayCount> querySwipeCards = null;
		try {
			if(type.equals("empid")) {
				sql += " and a.id = ? ";
			}else if(type.equals("depid")) {
				sql += " and a.depid = ? ";
			}else if(type.equals("costid")) {
				if (!data.equals("ALL")) {
					sql += " and a.costid = ? ";
				}
			}
			sql += " and b.CONTINUOUS_DAYS >= 5 ";
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and costId in(";
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
				sql += " and costId in('')";
			}
//			sql += " order by emp.depid,emp.id,swipe_date) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			sql += " order by a.deptid,a.id,START_DATE) A )";
			System.out.println("分頁查詢=============>>"+sql);
			querySwipeCards = jdbcTemplate.query(sql, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					// TODO Auto-generated method stub
//					ps.setString(1, data);
//					ps.setString(2, startDate);
//					ps.setString(3, endDate);
					if (type.equals("costid")) {
						if (userDataCostId.equals("ALL")) {
							
						}else {
							ps.setString(1, data);
							
						}
					}else {
						ps.setString(1, data);
						
					}
				}
			}, new QueryWorkDayCountMapper());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return querySwipeCards;
	}

}
