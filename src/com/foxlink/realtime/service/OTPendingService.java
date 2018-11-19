package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.EmpOTPendingDAO;
import com.foxlink.realtime.model.EmpInOTPendingSheet;
import com.foxlink.realtime.util.CommonUtils;

public class OTPendingService extends Service<EmpInOTPendingSheet> {
	private static Logger logger=Logger.getLogger(OTPendingService.class);
	private EmpOTPendingDAO empOTPendingDAO=null;
	
	public OTPendingService() {
		super();
	}
	
	public void setEmpOTPendingDAO(EmpOTPendingDAO dao) {
		this.empOTPendingDAO=dao;
	}
	
	@Override
	public boolean AddNewRecord(EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpInOTPendingSheet> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<EmpInOTPendingSheet> FindAllRecords(String WorkshopNo,String LineNo,String RCNo,String CostID,String ClassNo,
			String SwipeCradDate,boolean isAbnormal){
		List<EmpInOTPendingSheet> empOTPendingList=null;
		try {
			empOTPendingList=empOTPendingDAO.FindAllRecords(WorkshopNo,LineNo, RCNo, CostID, ClassNo,
					new CommonUtils().ConvertString2SQLDate(SwipeCradDate),isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed ",ex);
		}
		return empOTPendingList;
	}
	
	public List<EmpInOTPendingSheet> GetCalOverTime(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String AssistantId,String ClassNo,int CheckState,
			String OverTimeDate,int OverTimeType,String ItemNumber,int isAbnormal){
		List<EmpInOTPendingSheet> empOTPendingList=null;
		try {
			empOTPendingList=empOTPendingDAO.GetCalOverTime(WorkshopNo,LineNo, RCNo, AssistantAccount, AssistantId,ClassNo,CheckState,
					new CommonUtils().ConvertString2SQLDate(OverTimeDate),OverTimeType,ItemNumber,isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllRecords is failed ",ex);
		}
		return empOTPendingList;
	}

	@Override
	public List<EmpInOTPendingSheet> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpInOTPendingSheet> FindRecord(EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<EmpInOTPendingSheet> FindQueryRecord(String userDataCostId, int currentPage, EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpInOTPendingSheet> FindQueryRecords(String userDataCostId, EmpInOTPendingSheet t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EmpInOTPendingSheet> FindAllRecordsByDepid(String WorkshopNo, String LineNo, String RCNo,
			String CostID, String ClassNo, String SwipeCradDate, boolean isAbnormal) {
		List<EmpInOTPendingSheet> empOTPendingList=null;
		try {
			empOTPendingList=empOTPendingDAO.FindAllRecordsByDepid(WorkshopNo,LineNo, RCNo, CostID, ClassNo,
					new CommonUtils().ConvertString2SQLDate(SwipeCradDate),isAbnormal);
		}
		catch(Exception ex) {
			logger.error("FindAllRecordsByDepid is failed ",ex);
		}
		return empOTPendingList;
	}

	public List<EmpInOTPendingSheet> GetCalOverTimeByDepid(String WorkshopNo,String LineNo,String RCNo,String AssistantAccount,String AssistantId,String ClassNo,int CheckState,
			String OverTimeDate,int OverTimeType,String ItemNumber,int isAbnormal) {
		List<EmpInOTPendingSheet> empOTPendingList=null;
		try {
			empOTPendingList=empOTPendingDAO.GetCalOverTimeByDepid(WorkshopNo,LineNo, RCNo, AssistantAccount, AssistantId,ClassNo,CheckState,
					new CommonUtils().ConvertString2SQLDate(OverTimeDate),OverTimeType,ItemNumber,isAbnormal);
		}
		catch(Exception ex) {
			logger.error("GetCalOverTimeByDepid is failed ",ex);
		}
		return empOTPendingList;
	}

}
