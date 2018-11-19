package com.foxlink.realtime.service;

import java.util.List;
import org.apache.log4j.Logger;
import com.foxlink.realtime.DAO.EmpOTIdentifiedDAO;
import com.foxlink.realtime.model.EmpInOTIdentifiedSheet;
import com.foxlink.realtime.util.CommonUtils;

public class OTIdentifiedService extends Service<EmpInOTIdentifiedSheet> {
	private static Logger logger=Logger.getLogger(OTIdentifiedService.class);
	private EmpOTIdentifiedDAO empOTIdentifiedDAO;
	
	public OTIdentifiedService() {
		super();
	}
	
	public void setEmpOTIdentifiedDAO(EmpOTIdentifiedDAO dao) {
		this.empOTIdentifiedDAO=dao;
	}
	
	@Override
	public boolean AddNewRecord(EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<EmpInOTIdentifiedSheet> FindAllRecords(String WorkshopNo,String LineNo,String RCNo,String CostID,String ClassNo,String SwipeCradDate,boolean isAbnormal){
		List<EmpInOTIdentifiedSheet> empOTIdentifiedList=null;
		try {
			empOTIdentifiedList=empOTIdentifiedDAO.FindAllRecords(WorkshopNo,LineNo, RCNo, CostID, ClassNo, 
					new CommonUtils().ConvertString2SQLDate(SwipeCradDate),isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed",ex);
		}
		return empOTIdentifiedList;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindRecord(EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<EmpInOTIdentifiedSheet> FindQueryRecord(String userDataCostId, int currentPage,
			EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpInOTIdentifiedSheet> FindQueryRecords(String userDataCostId, EmpInOTIdentifiedSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EmpInOTIdentifiedSheet> FindAllRecordsByDepid(String WorkshopNo,String LineNo,String RCNo,String CostID,String ClassNo,String SwipeCradDate,boolean isAbnormal) {
		List<EmpInOTIdentifiedSheet> empOTIdentifiedList=null;
		try {
			empOTIdentifiedList=empOTIdentifiedDAO.FindAllRecordsByDepid(WorkshopNo,LineNo, RCNo, CostID, ClassNo, 
					new CommonUtils().ConvertString2SQLDate(SwipeCradDate),isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllRecordsByDepid is failed",ex);
		}
		return empOTIdentifiedList;
	}
	
	
}
