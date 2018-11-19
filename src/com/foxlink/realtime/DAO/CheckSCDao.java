package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QuerySwipeCard;
import com.foxlink.realtime.model.objectMapper.QuerySCMapper;

public class CheckSCDao extends DAO<QuerySwipeCard> {

	public CheckSCDao() {
		super();
	}
	
	@Override
	public boolean AddNewRecord(QuerySwipeCard newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QuerySwipeCard updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QuerySwipeCard> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuerySwipeCard> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	// 分頁查詢
	@Override
	public List<QuerySwipeCard> FindRecord(String userDataCostId,int currentPage, int totalRecord, QuerySwipeCard querySwipeCard) {
		//String sql = " SELECT ID,NAME,depid ,costID ,SwipeCardTime ,SwipeCardTime2 ,WorkshopNo FROM (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from (SELECT emp.ID,emp.NAME,emp.depid ,emp.costID ,cardtime.SwipeCardTime ,cardtime.SwipeCardTime2 ,cardtime.WorkshopNo from swipe.csr_employee emp, swipe.csr_swipecardtime cardtime WHERE emp.id = cardtime.emp_id ";
		String sql = " SELECT ID,NAME,depid ,costID ,to_char(SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') SwipeCardTime ,to_char(SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') SwipeCardTime2,WorkshopNo,swipe_date FROM (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from (SELECT emp.ID,emp.NAME,emp.depid ,emp.costID ,cardtime.SwipeCardTime ,cardtime.SwipeCardTime2 ,cardtime.WorkshopNo,cardtime.swipe_date from swipe.csr_employee emp, swipe.csr_swipecardtime cardtime WHERE emp.id = cardtime.emp_id ";
		List<QuerySwipeCard> querySwipeCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			String strId = querySwipeCard.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (querySwipeCard.getId() != ""&&querySwipeCard.getId() != null) {
				sql += " and emp.id in (" + idsStr + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			String strDepid = querySwipeCard.getDepid();
			String strDepidArray[] = strDepid.split(",");
			StringBuffer depidsStr = new StringBuffer();
			for (int i = 0; i < strDepidArray.length; i++) {
				if (i > 0) {
					depidsStr.append(",");
				}
				depidsStr.append("'").append(strDepidArray[i]).append("'");
			}
			if (querySwipeCard.getDepid() != "") {
				sql += " and emp.depid in (" + depidsStr + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			String strcostid = querySwipeCard.getCostid();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (querySwipeCard.getCostid() != "") {
				sql += " and emp.costID in (" + costidsStr + ") ";
			}
			if (querySwipeCard.getTimeStart() != "" && querySwipeCard.getTimeEnd() != "") {
				sql += " and cardtime.swipe_date >= ? and cardtime.swipe_date <= ?";
				queryList.add(querySwipeCard.getTimeStart());
				queryList.add(querySwipeCard.getTimeEnd());
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
			}else{
				sql += " and costId in('')";
			}
			Page page = new Page(currentPage, totalRecord);
			int endIndex = page.getStartIndex() + page.getPageSize();
			sql += " order by emp.depid,emp.id,swipe_date) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			querySwipeCards = jdbcTemplate.query(sql, queryList.toArray(), new QuerySCMapper());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return querySwipeCards;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 查詢總記錄數
	@Override
	public int getTotalRecords(String userDataCostId,QuerySwipeCard querySwipeCard) {
		int totalRecord = -1;
		String sSql = " SELECT count(*) FROM swipe.csr_employee emp, swipe.csr_swipecardtime cardtime WHERE emp.id = cardtime.emp_id ";

		try {
			List<Object> queryList = new ArrayList<Object>();
			String strId = querySwipeCard.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (querySwipeCard.getId() != ""&&querySwipeCard.getId() != null) {
				sSql += " and emp.id in (" + idsStr + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			String strDepid = querySwipeCard.getDepid();
			String strDepidArray[] = strDepid.split(",");
			StringBuffer depidsStr = new StringBuffer();
			for (int i = 0; i < strDepidArray.length; i++) {
				if (i > 0) {
					depidsStr.append(",");
				}
				depidsStr.append("'").append(strDepidArray[i]).append("'");
			}
			if (querySwipeCard.getDepid() != "") {
				sSql += " and emp.depid in (" + depidsStr + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			String strcostid = querySwipeCard.getCostid();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (querySwipeCard.getCostid() != "") {
				sSql += " and emp.costID in (" + costidsStr + ") ";
			}
			if (querySwipeCard.getTimeStart() != "" && querySwipeCard.getTimeEnd() != "") {
				sSql += " and cardtime.swipe_date >= ? and cardtime.swipe_date <= ?";
				queryList.add(querySwipeCard.getTimeStart());
				queryList.add(querySwipeCard.getTimeEnd());
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
			}else{
				sSql += " and costId in('')";
			}
			totalRecord = jdbcTemplate.queryForObject(sSql, queryList.toArray(), Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return totalRecord;
	}

	// 查詢總記錄
	public List<QuerySwipeCard> FindRecords(String userDataCostId,QuerySwipeCard querySwipeCard) {
		//String sql = "SELECT emp.ID,emp.NAME,emp.depid ,emp.costID ,cardtime.SwipeCardTime ,cardtime.SwipeCardTime2 ,cardtime.WorkshopNo from swipe.csr_employee emp, swipe.csr_swipecardtime cardtime WHERE emp.id = cardtime.emp_id ";
		String sql = "SELECT emp.ID,emp.NAME,emp.depid ,emp.costID ,to_char(cardtime.SwipeCardTime, 'yyyy-mm-dd hh24:mi:ss') SwipeCardTime ,to_char(cardtime.SwipeCardTime2, 'yyyy-mm-dd hh24:mi:ss') SwipeCardTime2   ,cardtime.WorkshopNo,cardtime.swipe_date from swipe.csr_employee emp, swipe.csr_swipecardtime cardtime WHERE emp.id = cardtime.emp_id ";
		List<QuerySwipeCard> querySwipeCards = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			String strId = querySwipeCard.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (querySwipeCard.getId() != ""&&querySwipeCard.getId() != null) {
				sql += " and emp.id in (" + idsStr + ") ";
				// queryList.add(querySwipeCard.getId());
			}
			String strDepid = querySwipeCard.getDepid();
			String strDepidArray[] = strDepid.split(",");
			StringBuffer depidsStr = new StringBuffer();
			for (int i = 0; i < strDepidArray.length; i++) {
				if (i > 0) {
					depidsStr.append(",");
				}
				depidsStr.append("'").append(strDepidArray[i]).append("'");
			}
			if (querySwipeCard.getDepid() != "") {
				sql += " and emp.depid in (" + depidsStr + ") ";
				//queryList.add(querySwipeCard.getDepid());
			}
			String strcostid = querySwipeCard.getCostid();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (querySwipeCard.getCostid() != "") {
				sql += " and emp.costID in (" + costidsStr + ") ";
				//queryList.add(querySwipeCard.getCostid());
			}
			if (querySwipeCard.getTimeStart() != "" && querySwipeCard.getTimeEnd() != "") {
				sql += " and cardtime.swipe_date >= ? and cardtime.swipe_date <= ? ";
				queryList.add(querySwipeCard.getTimeStart());
				queryList.add(querySwipeCard.getTimeEnd());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sql += " and emp.costId in(";
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
				sql += " and emp.costId in('')";
			}
			sql+=" order by emp.depid, emp.id,cardtime.swipe_date";
			querySwipeCards = jdbcTemplate.query(sql, queryList.toArray(), new QuerySCMapper());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return querySwipeCards;
	}

	@Override
	public List<QuerySwipeCard> FindRecords(QuerySwipeCard t) {
		// TODO Auto-generated method stub
		return null;
	}

}
