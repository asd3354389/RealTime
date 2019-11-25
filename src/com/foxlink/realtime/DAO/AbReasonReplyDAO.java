package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.foxlink.realtime.model.QueryByIdList;
import com.foxlink.realtime.model.objectMapper.QueryABReason;
import com.foxlink.realtime.model.objectMapper.QueryABTimeList;

public class AbReasonReplyDAO extends DAO<QueryByIdList>{
	private static Logger logger = Logger.getLogger(AbReasonReplyDAO.class);

	public List<QueryByIdList> searchABReasonList(String bu, String costid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		List<QueryByIdList> ABReasonList = null;
		String sSQl = "SELECT recordid,costid,userid,username,depid,exception_date,shift,exception_interval,exception_time,exception_reason FROM SWIPE.SWIPE_TIMEOUT_CACHE where Exception_Date >= to_date('"+sDate+"','yyyy-MM-dd HH24:mi:ss') and Exception_Date< to_date('"+eDate+"','yyyy-MM-dd HH24:mi:ss')";
		try {
			if(bu.equals("AllBU")) {
				sSQl+="";
			}else {
				if(costid.equals("AllCost")){
					sSQl += " and bu ='"+bu+"'";
				}else{
					sSQl += " and bu ='"+bu+"' and costid ='"+costid+"'";
				}
			}
			sSQl+=" ORDER BY depid";
			//System.out.println(sSQl);
			ABReasonList = jdbcTemplate.query(sSQl,new QueryABReason());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ABReasonList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return ABReasonList;
	}

	@Override
	public boolean AddNewRecord(QueryByIdList newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(QueryByIdList updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<QueryByIdList> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindRecord(String userDataCostId, int currentPage, int totalRecord, QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QueryByIdList> FindRecords(QueryByIdList t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, QueryByIdList t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int replyReason(QueryByIdList[] emp) {
		// TODO Auto-generated method stub
		String sSQL="update SWIPE.SWIPE_TIMEOUT_CACHE set exception_reason = ? where recordid=?";
		int result = 0;
		try {
			jdbcTemplate.batchUpdate(sSQL, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					// TODO Auto-generated method stub
					ps.setString(1, emp[i].getEXCEPTION_REASON());
					ps.setString(2, emp[i].getRECORDID());
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return emp.length;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("異常原因回復失敗，原因："+e);
			e.printStackTrace();
			result=1;
		}
		return result;
	}

	public List<QueryByIdList> searchABReasonByDepid(String depid, String sDate, String eDate) {
		// TODO Auto-generated method stub
		List<QueryByIdList> ABReasonList = null;
		String sSQl = "SELECT recordid,costid,userid,username,depid,exception_date,shift,exception_interval,exception_time,exception_reason FROM SWIPE.SWIPE_TIMEOUT_CACHE where Exception_Date >= to_date('"+sDate+"','yyyy-MM-dd HH24:mi:ss') and Exception_Date< to_date('"+eDate+"','yyyy-MM-dd HH24:mi:ss')";
		try {
			sSQl+=" and depid='"+depid+"' ORDER BY depid";
			//System.out.println(sSQl);
			ABReasonList = jdbcTemplate.query(sSQl,new QueryABReason());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ABReasonByDepid TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return ABReasonList;
	}
}
