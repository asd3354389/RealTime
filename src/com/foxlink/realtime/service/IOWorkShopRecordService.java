package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.IOWorkShopRecordDAO;
import com.foxlink.realtime.model.IOWSRecord;
import com.foxlink.realtime.model.Page;

public class IOWorkShopRecordService extends Service<IOWSRecord>{
	private static Logger logger = Logger.getLogger(IOWorkShopRecordService.class);
	private IOWorkShopRecordDAO iOWorkShopRecordDAO;
	public void setIOWorkShopRecordDAO(IOWorkShopRecordDAO iOWorkShopRecordDAO) {
		this.iOWorkShopRecordDAO=iOWorkShopRecordDAO;
	}
	
	@Override
	public boolean AddNewRecord(IOWSRecord t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean UpdateRecord(IOWSRecord t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<IOWSRecord> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<IOWSRecord> FindRecord(IOWSRecord t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<IOWSRecord> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<IOWSRecord> FindQueryRecords(String userDataCostId, IOWSRecord t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<IOWSRecord> FindQueryRecord(String userDataCostId, int currentPage, IOWSRecord t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getRawRecordPage(String userDataCostId, int currentPage, String empId,String workShopNo, String depId, String costId,
			String startDate, String endDate, String recordStatus) {
		// TODO Auto-generated method stub
		  int totalRecord = iOWorkShopRecordDAO.getTotalRecord(userDataCostId,empId,workShopNo, depId, costId, startDate, endDate, recordStatus);	      
	        Page page = new Page(currentPage, totalRecord);
	        return page;
	}

	public List<IOWSRecord> FindSearchRawRecords(String userDataCostId, int currentPage, String empId,String workShopNo,String depId, String costId,
			String startDate, String endDate, String recordStatus, Boolean isShowAll) {
		// TODO Auto-generated method stub
		List<IOWSRecord>searchRawRecord=null;
		try {
			int totalRecord = iOWorkShopRecordDAO.getTotalRecord(userDataCostId,empId,workShopNo,depId,costId,startDate,endDate,recordStatus);	     
			searchRawRecord = iOWorkShopRecordDAO.FindSearchRawRecords(userDataCostId,currentPage,totalRecord, empId,workShopNo,depId,costId,startDate,endDate,recordStatus,isShowAll);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Search IOWSRecords Record is failed ",e);
		}
		return searchRawRecord;
	}
}
