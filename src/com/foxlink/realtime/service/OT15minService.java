package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.OT15minDAO;
import com.foxlink.realtime.model.EmpOT15min;

public class OT15minService extends Service<EmpOT15min>{
	private static Logger logger=Logger.getLogger(OT15minService.class);
	private OT15minDAO OT15minDAO;
	

	public void setOT15minDAO(OT15minDAO oT15minDAO) {
		OT15minDAO = oT15minDAO;
	}

	@Override
	public boolean AddNewRecord(EmpOT15min t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpOT15min t) {
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
	public List<EmpOT15min> FindRecord(EmpOT15min t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpOT15min> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpOT15min> FindQueryRecords(String userDataCostId, EmpOT15min t) {
		// TODO Auto-generated method stub
		List<EmpOT15min> list = null;
		try{
			list = OT15minDAO.FindRecords(userDataCostId,t);
		}catch (Exception ex) {
			logger.error("query error" + ex);
		}
		return list;
	}

	@Override
	public List<EmpOT15min> FindQueryRecord(String userDataCostId, int currentPage, EmpOT15min t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EmpOT15min> FindQueryRecordsHistory(String userDataCostId, EmpOT15min queryOT15min) {
		// TODO Auto-generated method stub
		List<EmpOT15min> list = null;
		try{
			list = OT15minDAO.FindRecordsHistory(userDataCostId,queryOT15min);
		}catch (Exception ex) {
			logger.error("query error" + ex);
		}
		return list;
	}

}
