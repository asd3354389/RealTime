package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.IOCardbdIPDAO;
import com.foxlink.realtime.DAO.IOWorkShopPowerDAO;
import com.foxlink.realtime.model.IOCardMachineIP;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;

public class IOWorkShopPowerService extends Service<IOWorkShopPW>{

	private static Logger logger = Logger.getLogger(IOWorkShopPowerService.class);
	private IOWorkShopPowerDAO iOWorkShopPowerDAO;
	
	public void setIOWorkShopPowerDAO(IOWorkShopPowerDAO iOWorkShopPowerDAO) {
		this.iOWorkShopPowerDAO = iOWorkShopPowerDAO;
	}
	@Override
	public boolean AddNewRecord(IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IOWorkShopPW> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindRecord(IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindQueryRecords(String userDataCostId, IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWorkShopPW> FindQueryRecord(String userDataCostId, int currentPage, IOWorkShopPW t) {
		// TODO Auto-generated method stub
		return null;
	}
	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = iOWorkShopPowerDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public List<IOWorkShopPW> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<IOWorkShopPW> AllPW = null;
		try{
			int totalRecord = iOWorkShopPowerDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			/*System.out.println(totalRecord);*/
			AllPW = iOWorkShopPowerDAO.FindAllRecord(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find IOWorkShopPW Record is failed ",e);
		}
		return AllPW;
	}
	public boolean checkEmpIdExistence(String Emp_id) {
		// TODO Auto-generated method stub
		return iOWorkShopPowerDAO.checkEmpIdExistence(Emp_id);
	}
	public boolean checkUserNameDuplicate(String Emp_id, String workshopNo) {
		// TODO Auto-generated method stub
		return iOWorkShopPowerDAO.checkUserNameDuplicate(Emp_id,workshopNo);
	}
	public boolean addIOWorkShopPW(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		return iOWorkShopPowerDAO.addIOWorkShopPW(ioWorkShopPW,updateUser);
	}
	public boolean UpdateRecord(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		return iOWorkShopPowerDAO.UpdateRecord(ioWorkShopPW,updateUser);
	}
	public boolean DeleteIOWorkShopPW(String emp_id, String updateUser) {
		// TODO Auto-generated method stub
		return iOWorkShopPowerDAO.DeleteIOWorkShopPW(emp_id,updateUser);
	}

}
