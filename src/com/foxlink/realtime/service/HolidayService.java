package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.HolidayDao;
import com.foxlink.realtime.DAO.LmtDeptDAO;
import com.foxlink.realtime.model.Holiday;

public class HolidayService extends Service<Holiday> {
	private static Logger logger=Logger.getLogger(HolidayService.class);
	private HolidayDao holidayDAO;
	
	public void setHolidayDAO(HolidayDao holidayDAO ) {
	      this.holidayDAO = holidayDAO;
	      }
	
	@Override
	public boolean AddNewRecord(Holiday t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(Holiday t) {
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
	public List<Holiday> FindRecord(Holiday t) {
		// TODO Auto-generated method stub
		return holidayDAO.FindRecords(t);
	}

	@Override
	public List<Holiday> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Holiday> FindQueryRecords(String userDataCostId, Holiday t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Holiday> FindQueryRecord(String userDataCostId, int currentPage, Holiday t) {
		// TODO Auto-generated method stub
		return null;
	}


}
