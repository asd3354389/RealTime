package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.AssistantDAO;
import com.foxlink.realtime.model.AssistantInfo;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.QueryStatus;

public class AssistantService extends Service<AssistantInfo>{
	/***
     * 獲取助理列表
     * 
     * @Author Yaru Song 
     * @return
    */
    private static Logger logger=Logger.getLogger(AssistantService.class);  
		
	public AssistantService() {
		super();
			// TODO Auto-generated constructor stub
	}

	private AssistantDAO assistantDAO;
	public void setAssistantDAO(AssistantDAO assistantDAO ) {
	      this.assistantDAO = assistantDAO;
	      }
	
	@Override
	public List<AssistantInfo> FindAllRecords() {
		// TODO Auto-generated method stub
		List<AssistantInfo> AllAssistant=null;
		try {				     
			AllAssistant = assistantDAO.FindAllRecords();					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Find Assistant Record is failed ",e);
		}
		return AllAssistant;
	}
	
	 public Page getAssistantPage(int pageNum, String queryCritirea,String queryParam) {
	      
	        int totalRecord = assistantDAO.getTotalRecord(queryCritirea, queryParam);	      
	        Page page = new Page(pageNum, totalRecord);
	        return page;
	    }
	 
	 public boolean checkAssistantIdDuplicate(String assistantId) {
			// TODO Auto-generated method stub
			return assistantDAO.checkAssistantIdDuplicate(assistantId);
		}	

	 public boolean checkAssistantEmailDuplicate(String assistantEmail) {
			// TODO Auto-generated method stub
			return assistantDAO.checkAssistantEmailDuplicate(assistantEmail);
		}	
	 
	@Override
	public List<AssistantInfo> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<AssistantInfo> AllAssistants=null;
		try {
			int totalRecord = assistantDAO.getTotalRecord(queryCritirea,queryParam);	     
			AllAssistants = assistantDAO.FindAllRecords(currentPage,totalRecord, queryCritirea, queryParam);					  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Find AssistantId Record is failed ",e);
		}
		return AllAssistants;
	}

	@Override
	public boolean AddNewRecord(AssistantInfo newRecord) {
		// TODO Auto-generated method stub
		return assistantDAO.AddNewRecord(newRecord);
	}

	@Override
	public boolean UpdateRecord(AssistantInfo updateRecord) {
		// TODO Auto-generated method stub
		return assistantDAO.UpdateRecord(updateRecord);
	}

	@Override
	public boolean DeleteRecord(String recordID,String updateUser) {
		// TODO Auto-generated method stub
		return assistantDAO.DeleteRecord(recordID,updateUser);
	}

	@Override
	public List<AssistantInfo> FindRecord(AssistantInfo t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AssistantInfo> FindQueryRecord(String userDataCostId, int currentPage, AssistantInfo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssistantInfo> FindQueryRecords(String userDataCostId, AssistantInfo t) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
