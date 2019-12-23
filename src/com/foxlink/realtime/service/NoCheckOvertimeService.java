package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.NoCheckOvertimeDAO;
import com.foxlink.realtime.model.DGsubsidy;
import com.foxlink.realtime.model.Page;

public class NoCheckOvertimeService {
	private static Logger logger=Logger.getLogger(NoCheckOvertimeService.class);  
	private NoCheckOvertimeDAO noCheckOvertimeDAO;
	public void setNoCheckOvertimeDAO(NoCheckOvertimeDAO noCheckOvertimeDAO) {
		this.noCheckOvertimeDAO = noCheckOvertimeDAO;
	}
	 public Page getDGsubsidyPage(String userDataCostId,int pageNum,String empId,String depId,String costId,String startDate,String endDate) {
	      
	        int totalRecord = noCheckOvertimeDAO.getTotalRecord(userDataCostId,empId, depId, costId, startDate, endDate);	      
	        Page page = new Page(pageNum, totalRecord);
	        return page;
	    }
	 public List<DGsubsidy> FindSearchDGsubsidys(String userDataCostId,int currentPage,String empId,String depId,String costId,String startDate,String endDate,Boolean isShowAll, int totalRecord) {
			// TODO Auto-generated method stub
			List<DGsubsidy> searchDGsubsidy=null;
			try {
				searchDGsubsidy = noCheckOvertimeDAO.FindDGsubsidys(userDataCostId,currentPage,totalRecord, empId,depId,costId,startDate,endDate,isShowAll);					  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Search NoCheckOvertimeService Record is failed ",e);
			}
			return searchDGsubsidy;
		}
	

}
