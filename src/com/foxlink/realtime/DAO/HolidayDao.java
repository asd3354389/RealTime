package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.Holiday;
import com.foxlink.realtime.model.objectMapper.HolidaysMapper;
import com.foxlink.realtime.model.objectMapper.QuerySCMapper;

public class HolidayDao extends DAO<Holiday> {
	private static Logger logger=Logger.getLogger(HolidayDao.class);	

	@Override
	public boolean AddNewRecord(Holiday newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Holiday updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Holiday> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Holiday> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<Holiday> FindRecord(String userDataCostId, int currentPage, int totalRecord, Holiday t) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<Holiday> FindRecords(Holiday t) {
		// TODO Auto-generated method stub
		List<Holiday> holiday = null;
		String sql = "select holiday_date,holiday_type from FOXLINK_LEGAL_HOLIDAYS  where holiday_date = to_char(to_date(?,'yyyy/mm/dd'),'yyyy-mm-dd')";
		List<Object> queryList = new ArrayList<Object>();
		try{
			queryList.add(t.getHolidayDate());
			holiday = jdbcTemplate.query(sql,new HolidaysMapper(),t.getHolidayDate());
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Find Holiday are failed",ex);
		}
		return holiday;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, Holiday t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
