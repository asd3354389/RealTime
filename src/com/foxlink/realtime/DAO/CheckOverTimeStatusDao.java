package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.objectMapper.QueryOTMapper;
import com.foxlink.realtime.model.objectMapper.UserMapper;

@Repository
public class CheckOverTimeStatusDao extends DAO<QueryStatus> {

	public CheckOverTimeStatusDao() {
		super();
	}

	// 获取总记录数
	@Override
	public int getTotalRecords(String userDataCostId,QueryStatus queryStatus) {
		int totalRecord = -1;
		String sSql = "select count(*) from SWIPE.notes_overtime_state where 1=1";

		try {
			// 把前台传过来的id档拆分为字符串
			String str = queryStatus.getId();
			String strA[] = str.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strA.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strA[i]).append("'");
			}
			List<Object> queryList = new ArrayList<Object>();
			if (!queryStatus.getId().equals("")) {
				sSql += " and id in (" + idsStr + ") ";
				// queryList.add(idsStr);
			}
			// 把前台传过来的costid档拆分为字符串
			String strcostid = queryStatus.getCostId();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (queryStatus.getCostId() != null && queryStatus.getCostId() != "") {
				sSql += " and costid in(" + costidsStr + ") ";
				// queryList.add(queryStatus.getCostId());
			}
			if (queryStatus.getOVERTIMEDATE() != "" && queryStatus.getOVERTIMEDATEEnd() != "") {
				sSql += " and to_date(OVERTIMEDATE,'yyyy/mm/dd') >= to_date(?,'yyyy/mm/dd') and to_date(OVERTIMEDATE,'yyyy/mm/dd') <=to_date(?,'yyyy/mm/dd')";
				queryList.add(queryStatus.getOVERTIMEDATE());
				queryList.add(queryStatus.getOVERTIMEDATEEnd());
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

	// 分页查询
	@Override
	public List<QueryStatus> FindRecord(String userDataCostId,int currentPage, int totalRecord, QueryStatus queryEmpStatus) {
		// String sql = "select id,costid,name from notes_overtime_state where
		// id=?";
		// String sql = "SELECT id,name,costid,depid,direct from
		// notes_overtime_state where id=?";
		String sql = "SELECT id,NAME,Depid,costID,Direct,overtimedate,Shift,WorkContent,overtimeHours,overtimeType,overtimeInterval,application_person,application_id, NOTESSTATES,Reason,BackTime,Workshopno,BONUS from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from (SELECT id,NAME,Depid,costID,Direct,overtimedate,Shift,WorkContent,overtimeHours,overtimeType,overtimeInterval,application_person,application_id, NOTESSTATES,Reason,BackTime,Workshopno,BONUS FROM SWIPE.notes_overtime_state WHERE 1=1 ";
		List<QueryStatus> queryStatus = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			String strId = queryEmpStatus.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (queryEmpStatus.getId() != "") {
				sql += " and ID in (" + idsStr + ")";
				// queryList.add(idsStr);
			}
			String strcostid = queryEmpStatus.getCostId();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (queryEmpStatus.getCostId() != "" && queryEmpStatus.getCostId() != null) {
				sql += " and costID in (" + costidsStr + ") ";
				// queryList.add(queryEmpStatus.getCostId());
			}
			if (queryEmpStatus.getOVERTIMEDATE() != "" && queryEmpStatus.getOVERTIMEDATEEnd() != "") {
				sql += "  and to_date(OVERTIMEDATE,'yyyy/mm/dd') >= to_date(?,'yyyy/mm/dd') and to_date(OVERTIMEDATE,'yyyy/mm/dd') <=to_date(?,'yyyy/mm/dd') ";
				queryList.add(queryEmpStatus.getOVERTIMEDATE());
				queryList.add(queryEmpStatus.getOVERTIMEDATEEnd());
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
			sql += " order by Depid,overtimedate ,id) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			// sql += " order by overtimedate ) A ) where rnum > "+0+" and rnum
			// <= "+ 2 ;
			queryStatus = jdbcTemplate.query(sql, queryList.toArray(), new QueryOTMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return queryStatus;
	}

	// 查询总记录
	public List<QueryStatus> FindRecords(String userDataCostId,QueryStatus queryEmpStatus) {
		// String sql = "select id,costid,name from notes_overtime_state where
		// id=?";
		// String sql = "SELECT id,name,costid,depid,direct from
		// notes_overtime_state where id=?";
		//String sql = "SELECT id,NAME,Depid,costID,Direct,overtimedate,Shift,WorkContent,overtimeHours,overtimeType,overtimeInterval,application_person,application_id, NOTESSTATES,Reason,BackTime,Workshopno from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from (SELECT id,NAME,Depid,costID,Direct,overTimeDate,Shift,WorkContent,overtimeHours,overtimeType,overtimeInterval,application_person,application_id, NOTESSTATES,Reason,BackTime,Workshopno FROM notes_overtime_state WHERE 1=1 ";
		String sql = "SELECT id,NAME,Depid,costID,Direct,overtimedate,Shift,WorkContent,overtimeHours,overtimeType,overtimeInterval,application_person,application_id, NOTESSTATES ,Reason,BackTime,Workshopno,BONUS  FROM SWIPE.notes_overtime_state WHERE 1=1 ";
		List<QueryStatus> queryStatus = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			String strId = queryEmpStatus.getId();
			String strIdArray[] = strId.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < strIdArray.length; i++) {
				if (i > 0) {
					idsStr.append(",");
				}
				idsStr.append("'").append(strIdArray[i]).append("'");
			}
			if (queryEmpStatus.getId() != "") {
				sql += " and ID in (" + idsStr + ")";
				// queryList.add(idsStr);
			}
			String strcostid = queryEmpStatus.getCostId();
			String strCostidArray[] = strcostid.split(",");
			StringBuffer costidsStr = new StringBuffer();
			for (int i = 0; i < strCostidArray.length; i++) {
				if (i > 0) {
					costidsStr.append(",");
				}
				costidsStr.append("'").append(strCostidArray[i]).append("'");
			}
			if (queryEmpStatus.getCostId() != "" && queryEmpStatus.getCostId() != null) {
				sql += " and costID in (" + costidsStr + ") ";
				// queryList.add(queryEmpStatus.getCostId());
			}
			if (queryEmpStatus.getOVERTIMEDATE() != "" && queryEmpStatus.getOVERTIMEDATEEnd() != "") {
				sql += "  and to_date(OVERTIMEDATE,'yyyy/mm/dd') >= to_date(?,'yyyy/mm/dd') and to_date(OVERTIMEDATE,'yyyy/mm/dd') <=to_date(?,'yyyy/mm/dd') ";
				queryList.add(queryEmpStatus.getOVERTIMEDATE());
				queryList.add(queryEmpStatus.getOVERTIMEDATEEnd());
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

			sql += " order by Depid,overtimedate ,id ";
			// sql += " order by overtimedate ) A ) where rnum > "+0+" and rnum
			// <= "+ 2 ;
			queryStatus = jdbcTemplate.query(sql, queryList.toArray(), new QueryOTMapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return queryStatus;
	}

	@Override
	public boolean AddNewRecord(QueryStatus newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryStatus updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryStatus> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 获取总记录数
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<QueryStatus> FindRecords(QueryStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
