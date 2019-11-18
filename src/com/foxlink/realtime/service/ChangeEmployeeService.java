package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.ChangeEmployeeDAO;
import com.foxlink.realtime.DAO.ExceptionCostDAO;
import com.foxlink.realtime.model.ChangeEmployee;
import com.foxlink.realtime.model.EmpChange;
import com.foxlink.realtime.model.IpBinding;


public class ChangeEmployeeService extends Service<ChangeEmployeeService>{
	private static Logger logger = Logger.getLogger(ExceptionCostService.class);
	private ChangeEmployeeDAO changeEmployeeDAO;
	
	public void setChangeEmployeeDAO(ChangeEmployeeDAO changeEmployeeDAO) {
		this.changeEmployeeDAO = changeEmployeeDAO;
	}

	@Override
	public boolean AddNewRecord(ChangeEmployeeService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ChangeEmployeeService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ChangeEmployeeService> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeService> FindRecord(ChangeEmployeeService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeService> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeService> FindQueryRecords(String userDataCostId, ChangeEmployeeService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChangeEmployeeService> FindQueryRecord(String userDataCostId, int currentPage,
			ChangeEmployeeService t) {
		// TODO Auto-generated method stub
		return null;
	}

	//查询是否刷卡
	public List<ChangeEmployee> CheckSwipeCard(String startdate, String empId) {
		// TODO Auto-generated method stub
		return changeEmployeeDAO.CheckSwipeCard(startdate,empId);
	}
	//查询人员
		public List<EmpChange> CheckEmp(String empId) {
			// TODO Auto-generated method stub
			return changeEmployeeDAO.CheckEmp(empId);
		}
	//更新狀態 
		public boolean updateStatus(String selcetValue,String StatusValue,String startdate,String empId) {
			
			String status = selcetValue + "_" +StatusValue;
			return changeEmployeeDAO.updateStatus(status,startdate,empId);
		}
}
