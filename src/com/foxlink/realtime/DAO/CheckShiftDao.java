package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryShift;
import com.foxlink.realtime.model.objectMapper.QueryShiftMapper;

public class CheckShiftDao extends DAO<QueryShift> {
	
	public CheckShiftDao() {
		super();
	}

	@Override
	public boolean AddNewRecord(QueryShift newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryShift updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryShift> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryShift> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	// 分頁查詢
	@Override
	public List<QueryShift> FindRecord(String userDataCostId,int currentPage, int totalRecord, QueryShift queryShift) {
		String sql = "SELECT id ,name ,depid ,costid ,emp_date ,class_no ,update_time ,class_start ,class_end FROM "
				+ "(select a.*,rownum as rnum,COUNT (*) OVER () totalPage from "
				+ "(SELECT emp.id ,emp.name ,emp.depid ,emp.costid ,emp_class.emp_date ,emp_class.class_no ,"
				+ "emp_class.update_time ,class_no.class_start ,class_no.class_end from swipe.csr_employee emp,"
				+ "swipe.classno class_no,swipe.emp_class emp_class "
				+ "WHERE emp.ID = emp_class.ID AND emp_class.class_no = class_no.class_no and emp.isonwork='0'";
		List<QueryShift> queryShifts = null;
		try {
			List<Object> queryList = new ArrayList<Object>();
			if (queryShift.getId() != "" && queryShift.getId() != null) {
				String strId = queryShift.getId();
				String strIdArray[] = strId.split(",");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sql += " and emp.ID in (" + idsStr + ") ";
				// queryList.add(queryShift.getId());
			}
			if (queryShift.getCostid() != "" && queryShift.getCostid() != null) {
				String strcostid = queryShift.getCostid();
				String strCostidArray[] = strcostid.split(",");
				StringBuffer costidsStr = new StringBuffer();
				for (int i = 0; i < strCostidArray.length; i++) {
					if (i > 0) {
						costidsStr.append(",");
					}
					costidsStr.append("'").append(strCostidArray[i]).append("'");
				}
				sql += " and emp.costid in (" + costidsStr + ") ";
				// queryList.add(queryShift.getCostid());
			}
			if (queryShift.getEmp_date() != "" && queryShift.getEmpDateEnd() != "") {
				sql += " and emp_class.emp_date >= TO_DATE (?, 'yyyy/mm/dd') and emp_class.emp_date<= TO_DATE (?, 'yyyy/mm/dd')";
				queryList.add(queryShift.getEmp_date());
				queryList.add(queryShift.getEmpDateEnd());
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
			sql += " order by costId,depid,emp_date ,id) A ) where rnum > " + page.getStartIndex() + " and rnum <= " + endIndex;
			queryShifts = jdbcTemplate.query(sql, queryList.toArray(), new QueryShiftMapper());
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}

		return queryShifts;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 查询总個数
	@Override
	public int getTotalRecords(String userDataCostId,QueryShift queryShift) {
		int totalRecord = -1;
		String sSql = "SELECT count(*) FROM swipe.csr_employee emp,swipe.classno class_no,swipe.emp_class emp_class WHERE emp.ID = emp_class.ID AND emp_class.class_no = class_no.class_no and emp.isonwork='0'";

		try {
			List<Object> queryList = new ArrayList<Object>();
			if (queryShift.getId() != "" && queryShift.getId() != null) {
				String strId = queryShift.getId();
				String strIdArray[] = strId.split(",");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sSql += " and emp.ID in (" + idsStr + ") ";
				// queryList.add(queryShift.getId());
			}
			if (queryShift.getCostid() != "" && queryShift.getCostid() != null) {
				String strcostid = queryShift.getCostid();
				String strCostidArray[] = strcostid.split(",");
				StringBuffer costidsStr = new StringBuffer();
				for (int i = 0; i < strCostidArray.length; i++) {
					if (i > 0) {
						costidsStr.append(",");
					}
					costidsStr.append("'").append(strCostidArray[i]).append("'");
				}
				sSql += " and emp.costid in (" + costidsStr + ") ";
			}
			if (queryShift.getEmp_date() != "" && queryShift.getEmpDateEnd() != "") {
				sSql += " and emp_class.emp_date >= TO_DATE (?, 'yyyy/mm/dd') and emp_class.emp_date<= TO_DATE (?, 'yyyy/mm/dd')";
				queryList.add(queryShift.getEmp_date());
				queryList.add(queryShift.getEmpDateEnd());
			}
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSql += " and emp.costId in(";
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
				sSql += " and emp.costId in('')";
			}
			totalRecord = jdbcTemplate.queryForObject(sSql, queryList.toArray(), Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return totalRecord;
	}

	// 查詢所有記錄
	public List<QueryShift> FindRecords(String userDataCostId,QueryShift queryShift) {
		String sql = "SELECT emp.id ,emp.name ,emp.depid ,emp.costid ,emp_class.emp_date ,emp_class.class_no ,emp_class.update_time ,class_no.class_start ,class_no.class_end from swipe.csr_employee emp,swipe.classno class_no,swipe.emp_class emp_class WHERE emp.ID = emp_class.ID AND emp_class.class_no = class_no.class_no ";
		List<QueryShift> queryShifts = null;
			try {
			List<Object> queryList = new ArrayList<Object>();
			if (queryShift.getId() != "" && queryShift.getId() != null) {
				String strId = queryShift.getId();
				String strIdArray[] = strId.split(",");
				StringBuffer idsStr = new StringBuffer();
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
				sql += " and emp.ID in (" + idsStr + ") ";
				// queryList.add(queryShift.getId());
			}
			if (queryShift.getCostid() != "" && queryShift.getCostid() != null) {
				String strcostid = queryShift.getCostid();
				String strCostidArray[] = strcostid.split(",");
				StringBuffer costidsStr = new StringBuffer();
				for (int i = 0; i < strCostidArray.length; i++) {
					if (i > 0) {
						costidsStr.append(",");
					}
					costidsStr.append("'").append(strCostidArray[i]).append("'");
				}
				sql += " and emp.costid in (" + costidsStr + ") ";
			}
			if (queryShift.getEmp_date() != "" && queryShift.getEmpDateEnd() != "") {
				sql += " and emp_class.emp_date >= TO_DATE (?, 'yyyy/mm/dd') and emp_class.emp_date<= TO_DATE (?, 'yyyy/mm/dd')";
				queryList.add(queryShift.getEmp_date());
				System.out.println(queryShift.getEmp_date()+"---------------"+queryShift.getEmpDateEnd());
				queryList.add(queryShift.getEmpDateEnd());
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
			sql+=" order by emp.costId,emp.depid,emp_class.emp_date ,emp.id";
			queryShifts = jdbcTemplate.query(sql, queryList.toArray(), new QueryShiftMapper());
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}

		return queryShifts;
	}

	@Override
	public List<QueryShift> FindRecords(QueryShift t) {
		// TODO Auto-generated method stub
		return null;
	}

}
