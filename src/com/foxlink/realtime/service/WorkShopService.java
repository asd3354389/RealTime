package com.foxlink.realtime.service;

import java.util.List;
import org.apache.log4j.Logger;
import com.foxlink.realtime.DAO.WorkShopDAO;
import com.foxlink.realtime.model.AssistantInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.SearchRawRecordInfo;
import com.foxlink.realtime.model.WorkShop;

public class WorkShopService  extends Service<WorkShop> {
	private static Logger logger=Logger.getLogger(WorkShopService.class);  
	private WorkShopDAO workShopDAO;
	public void setWorkShopDAO(WorkShopDAO workShopDAO ) {
	      this.workShopDAO = workShopDAO;
	      }
	
	@Override
	public boolean AddNewRecord(WorkShop newRecord) {
		// TODO Auto-generated method stub
		return workShopDAO.AddNewRecord(newRecord);
	}
	
	@Override
	public boolean UpdateRecord(WorkShop updateRecord) {
		// TODO Auto-generated method stub
		return workShopDAO.UpdateRecord(updateRecord);
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean DisableWorkShopInfos(WorkShop workShop) {
		return workShopDAO.DisableWorkShopInfos(workShop);
	}
	
	@Override
	public List<WorkShop> FindAllRecords() {
		// TODO Auto-generated method stub
		List<WorkShop> workShopList=null;
		try {
			//int totalRecord = rawRecordDAO.getTotalRecord(empId,depId,costId,startDate,endDate,recordStatus);	     
			workShopList = workShopDAO.FindAllRecords();					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Find workShopList is failed ",e);
		}
		return workShopList;
	}

	@Override
	public List<WorkShop> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<WorkShop> AllWorkShops=null;
		try {
			int totalRecord = workShopDAO.getTotalRecord(queryCritirea,queryParam);	     
			AllWorkShops = workShopDAO.FindAllRecords(currentPage,totalRecord, queryCritirea, queryParam);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Find AllWorkShops Record is failed ",e);
		}
		return AllWorkShops;
	}
	
	 public Page getWorkShopPage(int pageNum,String queryCritirea,String queryParam) {
	      
	        int totalRecord = workShopDAO.getTotalRecord(queryCritirea, queryParam);	      
	        Page page = new Page(pageNum, totalRecord);
	        return page;
	    }
	 
	 public boolean checkWorkShopNoDuplicate(String workShopNo) {
			// TODO Auto-generated method stub
			return workShopDAO.checkWorkShopNoDuplicate(workShopNo);
		}
	 
	 public boolean checkLineNoDuplicate(WorkShop workShop) {
			// TODO Auto-generated method stub
			return workShopDAO.checkLineNoDuplicate(workShop);
		}

	@Override
	public List<WorkShop> FindRecord(WorkShop workShop) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<String> FindWorkShopNo() {
		return workShopDAO.FindWorkShopNo();
	}
	
	public List<String> GetLineNoByWorkShop(String workShopNo) {
		return workShopDAO.GetLineNoByWorkShop(workShopNo);
	}
	
	public boolean AddLineNo(WorkShop newRecord) {
		// TODO Auto-generated method stub
		return workShopDAO.AddLineNo(newRecord);
	}


	@Override
	public List<WorkShop> FindQueryRecord(String userDataCostId, int currentPage, WorkShop t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkShop> FindQueryRecords(String userDataCostId, WorkShop t) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
