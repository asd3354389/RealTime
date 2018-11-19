package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.LineMappingDAO;
import com.foxlink.realtime.model.LineMapping;
import com.foxlink.realtime.model.Page;

public class LineMappingService extends Service<LineMapping> {
	private static Logger logger = Logger.getLogger(LineMappingService.class);
	private LineMappingDAO lineMappingDAO;
	
	public void setLineMappingDAO(LineMappingDAO lineMappingDAO) {
		this.lineMappingDAO = lineMappingDAO;
	}

	@Override
	public boolean AddNewRecord(LineMapping t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(LineMapping t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LineMapping> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineMapping> FindRecord(LineMapping t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineMapping> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineMapping> FindQueryRecords(String userDataCostId, LineMapping t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineMapping> FindQueryRecord(String userDataCostId, int currentPage, LineMapping t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LineMapping> FindQueryRecord(int currentPage, String queryCritirea,String queryParam) {
		// TODO Auto-generated method stub
		List<LineMapping> lineMapping = null;
		try{
			int totalRecord = lineMappingDAO.getTotalRecord(queryCritirea, queryParam);
			lineMapping = lineMappingDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find LineMapping Record is failed ",e);
		}
		return lineMapping;
	}

	public Page getLineMappingPage(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord = lineMappingDAO.getTotalRecord(queryCritirea, queryParam);
		Page page = new Page(currentPage, totalRecord);
		return page;
	}

	public boolean UpdateRecord(LineMapping lineMapping, String updateUser) {
		// TODO Auto-generated method stub
		return lineMappingDAO.UpdateRecord(lineMapping,updateUser);
	}

	public List<String> getRTLine() {
		// TODO Auto-generated method stub
		return lineMappingDAO.getRTLine();
	}

	public boolean checkRTLine(String rt_LINENO) {
		// TODO Auto-generated method stub
		return lineMappingDAO.checkRTLine(rt_LINENO);
	}

	public boolean AddNewRecord(LineMapping lineMapping, String insertUser) {
		// TODO Auto-generated method stub
		return lineMappingDAO.AddNewRecord(lineMapping,insertUser);
	}

}
