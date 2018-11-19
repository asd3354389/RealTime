package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.RawRecordDAO;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.RawRecord;
import com.foxlink.realtime.model.SearchRawRecordInfo;

public class RawRecordService  extends Service<RawRecord> {
	private static Logger logger=Logger.getLogger(RawRecordService.class);  
	private RawRecordDAO rawRecordDAO;
	public void setRawRecordDAO(RawRecordDAO rawRecordDAO ) {
	      this.rawRecordDAO = rawRecordDAO;
	      }
	@Override
	public boolean AddNewRecord(RawRecord t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(RawRecord t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RawRecord> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RawRecord> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<SearchRawRecordInfo> FindSearchRawRecords(String userDataCostId,int currentPage,String empId,String depId,String costId,String startDate,String endDate,String recordStatus,Boolean isShowAll) {
		// TODO Auto-generated method stub
		List<SearchRawRecordInfo> searchRawRecord=null;
		try {
			int totalRecord = rawRecordDAO.getTotalRecord(userDataCostId,empId,depId,costId,startDate,endDate,recordStatus);	     
			searchRawRecord = rawRecordDAO.FindSearchRawRecords(userDataCostId,currentPage,totalRecord, empId,depId,costId,startDate,endDate,recordStatus,isShowAll);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Search RawRecords Record is failed ",e);
		}
		return searchRawRecord;
	}
	
	 public Page getRawRecordPage(String userDataCostId,int pageNum,String empId,String depId,String costId,String startDate,String endDate,String recordStatus) {
	      
	        int totalRecord = rawRecordDAO.getTotalRecord(userDataCostId,empId, depId, costId, startDate, endDate, recordStatus);	      
	        Page page = new Page(pageNum, totalRecord);
	        return page;
	    }
	@Override
	public List<RawRecord> FindRecord(RawRecord t) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	@Override
	public List<RawRecord> FindQueryRecord(String userDataCostId, int currentPage, RawRecord t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<RawRecord> FindQueryRecords(String userDataCostId, RawRecord t) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
