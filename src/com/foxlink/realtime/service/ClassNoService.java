package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.ClassNoDAO;
import com.foxlink.realtime.model.ClassNO;
import com.foxlink.realtime.model.QueryStatus;

public class ClassNoService extends Service<ClassNO> {
	private static Logger logger=Logger.getLogger(ClassNoService.class);
	private ClassNoDAO classNoDAO;
	public void setClassNoDAO(ClassNoDAO classNoDAO ) {
	      this.classNoDAO = classNoDAO;
	      }

	@Override
	public List<ClassNO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ClassNO FindRecord(String CurrentShift){
		ClassNO classNoInfos=null;
		try {
			classNoInfos=classNoDAO.FindAllRecords(CurrentShift).get(0);
		}
		catch(Exception ex) {
			logger.error("",ex);
		}
		return classNoInfos;
	}


	@Override
	public boolean AddNewRecord(ClassNO t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean UpdateRecord(ClassNO t) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<ClassNO> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassNO> FindRecord(ClassNO t) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<ClassNO> FindQueryRecord(String userDataCostId, int currentPage, ClassNO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassNO> FindQueryRecords(String userDataCostId, ClassNO t) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
