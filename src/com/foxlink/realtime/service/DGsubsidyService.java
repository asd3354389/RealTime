package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.DAO.DGsubsidyDAO;
import com.foxlink.realtime.model.DGsubsidy;

public class DGsubsidyService  extends Service<DGsubsidy> {
	private static Logger logger=Logger.getLogger(DGsubsidyService.class);  
	private DGsubsidyDAO dgsubsidyDAO;
	public void setDGsubsidyDAO(DGsubsidyDAO dgsubsidyDAO ) {
	      this.dgsubsidyDAO = dgsubsidyDAO;
	      }
	@Override
	public boolean AddNewRecord(DGsubsidy t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(DGsubsidy t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DGsubsidy> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGsubsidy> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<DGsubsidy> FindSearchDGsubsidys(String userDataCostId,int currentPage,String empId,String depId,String costId,String startDate,String endDate,Boolean isShowAll) {
		// TODO Auto-generated method stub
		List<DGsubsidy> searchDGsubsidy=null;
		try {
			int totalRecord = dgsubsidyDAO.getTotalRecord(userDataCostId,empId,depId,costId,startDate,endDate);	     
			searchDGsubsidy = dgsubsidyDAO.FindDGsubsidys(userDataCostId,currentPage,totalRecord, empId,depId,costId,startDate,endDate,isShowAll);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Search DGsubsidys Record is failed ",e);
		}
		return searchDGsubsidy;
	}
	
	 public Page getDGsubsidyPage(String userDataCostId,int pageNum,String empId,String depId,String costId,String startDate,String endDate) {
	      
	        int totalRecord = dgsubsidyDAO.getTotalRecord(userDataCostId,empId, depId, costId, startDate, endDate);	      
	        Page page = new Page(pageNum, totalRecord);
	        return page;
	    }
	@Override
	public List<DGsubsidy> FindRecord(DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<DGsubsidy> FindQueryRecord(String userDataCostId, int currentPage, DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<DGsubsidy> FindQueryRecords(String userDataCostId, DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}


}
