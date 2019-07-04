package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryForgetCard;
import com.foxlink.realtime.model.objectMapper.QueryFCMapper;

public class CheckFCDao extends DAO<QueryForgetCard> {

	@Override
	public boolean AddNewRecord(QueryForgetCard newRecord) {
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryForgetCard updateRecord) {
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		return false;
	}

	@Override
	public List<QueryForgetCard> FindAllRecords() {
		return null;
	}

	@Override
	public List<QueryForgetCard> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		return null;
	}

	@Override
	public List<QueryForgetCard> FindRecord(String userDataCostId, int currentPage, int totalRecord,
			QueryForgetCard queryForgetCard) {
		String sdate = queryForgetCard.getStartDate();
		String sql = "select id ,name,deptid,costid,  isOnWork, fcDate  from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from (select id ,name,deptid,costid,  isOnWork,  '"
				+ sdate + "' fcDate  from swipe.csr_employee where 1=1";
		List<QueryForgetCard> forgetCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			// List<String> result =
			// Arrays.asList(queryForgetCard.getDepid().split(","));
			// 下拉框多选放入sql语句中
			
			// List<String> result=Arrays.asList(StringUtils.split(str,","));
			if ( queryForgetCard.getDepid() != null) {
				String str = queryForgetCard.getDepid();
				String strA[] = str.split(",");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strA.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strA[i]).append("'");
				}
				sql += " AND costid = ? and deptid in (" + idsStr
						+ ") AND isOnWork = 0 AND id NOT IN (SELECT emp_id FROM swipe.csr_swipecardtime WHERE emp_id IN (SELECT id FROM  swipe.csr_employee WHERE 1 = 1 AND (costid = ?) and deptid in ("
						+ idsStr
						+ ") ) AND  to_char(to_date(swipe.csr_swipecardtime.swipe_date,'yyyy-mm-dd'),'yyyy/mm/dd') = ?) ";
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getStartDate());
			}else 
			{
				sql += " AND costid = ? AND isOnWork = 0 AND id NOT IN (SELECT emp_id FROM swipe.csr_swipecardtime WHERE emp_id IN (SELECT id FROM  swipe.csr_employee WHERE 1 = 1 AND (costid = ?)  ) AND  to_char(to_date(swipe.csr_swipecardtime.swipe_date,'yyyy-mm-dd'),'yyyy/mm/dd') = ?) ";
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getStartDate());
			}
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
			} else {
				sql += " and costId in('')";
			}
			Page page = new Page(currentPage, totalRecord);
			int endIndex = page.getStartIndex() + page.getPageSize();
			sql += " order by isOnWork,deptid,ID) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			forgetCards = super.jdbcTemplate.query(sql, queryList.toArray(), new QueryFCMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return forgetCards;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		return 0;
	}

	// 获取总记录数
	@Override
	public int getTotalRecords(String userDataCostId, QueryForgetCard queryForgetCard) {
		int totalRecord = -1;
		String sSql = "select count(*) from swipe.csr_employee where 1=1";
		// List<QueryForgetCard> forgetCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			// List<String> result =
			// Arrays.asList(queryForgetCard.getDepid().split(","));
			// 下拉框多选放入sql语句中
			
			// List<String> result=Arrays.asList(StringUtils.split(str,","));
			//queryForgetCard.getCostid() != "" && queryForgetCard.getStartDate() != ""
			
			if ( queryForgetCard.getDepid() != null) {
				String str = queryForgetCard.getDepid();
				String strA[] = str.split(",");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strA.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strA[i]).append("'");
				}
				sSql += " AND costid = ? and deptid in (" + idsStr
						+ ") AND isOnWork = 0 AND id NOT IN (SELECT emp_id FROM swipe.csr_swipecardtime WHERE emp_id IN (SELECT id FROM  swipe.csr_employee WHERE 1 = 1 AND (costid = ?) and deptid in ("
						+ idsStr
						+ ") ) AND  to_char(to_date(swipe.csr_swipecardtime.swipe_date,'yyyy-mm-dd'),'yyyy/mm/dd') = ?) ";
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getStartDate());
			} else 
			{
				sSql += " AND costid = ? AND isOnWork = 0 AND id NOT IN (SELECT emp_id FROM swipe.csr_swipecardtime WHERE emp_id IN (SELECT id FROM  swipe.csr_employee WHERE 1 = 1 AND (costid = ?)  ) AND  to_char(to_date(swipe.csr_swipecardtime.swipe_date,'yyyy-mm-dd'),'yyyy/mm/dd') = ?) ";
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getStartDate());
			}
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
			} else {
				sSql += " and costId in('')";
			}
			sSql += " ORDER BY isOnWork,deptid,ID";
			totalRecord = jdbcTemplate.queryForObject(sSql, queryList.toArray(), Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return totalRecord;
	}

	// 查詢所有記錄
	public List<QueryForgetCard> FindRecords(String userDataCostId, QueryForgetCard queryForgetCard) {
		String sdate = queryForgetCard.getStartDate();
		String sql = "select id ,name,deptid,costid,  isOnWork,  '" + sdate
				+ "' fcDate  from swipe.csr_employee where 1=1";
		List<QueryForgetCard> forgetCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			StringBuffer idsStr = new StringBuffer();
			// List<String> result =
			// Arrays.asList(queryForgetCard.getDepid().split(","));
			// 下拉框多选放入sql语句中
			String str = queryForgetCard.getDepid();
			if(str != null && str != ""){
			String strA[] = str.split(",");
				for (int i = 0; i < strA.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strA[i]).append("'");
				}
			}
			// List<String> result=Arrays.asList(StringUtils.split(str,","));
			if (queryForgetCard.getDepid() != null && queryForgetCard.getDepid() != "") {
				sql += " AND costid = ? and deptid in (" + idsStr
						+ ") AND isOnWork = 0 AND id NOT IN (SELECT emp_id FROM swipe.csr_swipecardtime WHERE emp_id IN (SELECT id FROM  swipe.csr_employee WHERE 1 = 1 AND (costid = ?) and deptid in ("
						+ idsStr
						+ ") ) AND  to_char(to_date(swipe.csr_swipecardtime.swipe_date,'yyyy-mm-dd'),'yyyy/mm/dd') = ?) ";
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getCostid());
				// queryList.add(queryForgetCard.getDepid());
				// queryList.add(result);
				queryList.add(queryForgetCard.getStartDate());
			}else{
				sql += " AND costid = ? AND isOnWork = 0 AND id NOT IN (SELECT emp_id FROM swipe.csr_swipecardtime WHERE emp_id IN (SELECT id FROM  swipe.csr_employee WHERE 1 = 1 AND (costid = ?)  ) AND  to_char(to_date(swipe.csr_swipecardtime.swipe_date,'yyyy-mm-dd'),'yyyy/mm/dd') = ?) ";
				queryList.add(queryForgetCard.getCostid());
				queryList.add(queryForgetCard.getCostid());
				queryList.add(queryForgetCard.getStartDate());

			}
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
			} else {
				sql += " and costId in('')";
			}
			sql += " order by isOnWork,deptid,ID";
			forgetCards = jdbcTemplate.query(sql, queryList.toArray(), new QueryFCMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return forgetCards;
	}

	@Override
	public List<QueryForgetCard> FindRecords(QueryForgetCard t) {
		// TODO Auto-generated method stub
		return null;
	}
}
