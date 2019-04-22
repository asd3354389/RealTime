package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.IOCardbdIPDAO;
import com.foxlink.realtime.model.IOCardMachineIP;
import com.foxlink.realtime.model.OTCardBD;
import com.foxlink.realtime.model.Page;


public class IOCardbdIPService extends Service<IOCardMachineIP>{
	private static Logger logger = Logger.getLogger(IOCardbdIPService.class);
	private IOCardbdIPDAO iOCardbdIPDAO;
	
	public void setIOCardbdIPDAO(IOCardbdIPDAO iOCardbdIPDAO) {
		this.iOCardbdIPDAO = iOCardbdIPDAO;
	}

	@Override
	public boolean AddNewRecord(IOCardMachineIP t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(IOCardMachineIP t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IOCardMachineIP> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOCardMachineIP> FindRecord(IOCardMachineIP t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOCardMachineIP> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOCardMachineIP> FindQueryRecords(String userDataCostId, IOCardMachineIP t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOCardMachineIP> FindQueryRecord(String userDataCostId, int currentPage, IOCardMachineIP t) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getPersonPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = iOCardbdIPDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List<IOCardMachineIP> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<IOCardMachineIP> AllDeip = null;
		try{
			int totalRecord = iOCardbdIPDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			/*System.out.println(totalRecord);*/
			AllDeip = iOCardbdIPDAO.FindAllRecord(currentPage,totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find IOCardIPList Record is failed ",e);
		}
		return AllDeip;
	}

	public boolean checkDeviceipDuplicate(String Deviceip) {
		// TODO Auto-generated method stub
		return iOCardbdIPDAO.checkDeviceipDuplicate(Deviceip);
	}

	public boolean setIOCardIP(IOCardMachineIP ioCardMachineIP, String updateUser) {
		// TODO Auto-generated method stub
		return iOCardbdIPDAO.setIOCardIP(ioCardMachineIP,updateUser);
	}

	public boolean checkMachineIPExistence(String Deviceip) {
		// TODO Auto-generated method stub
		return iOCardbdIPDAO.checkMachineIPExistence(Deviceip);
	}

	public boolean UpdateRecord(IOCardMachineIP ioCardMachineIP, String updateUser) {
		// TODO Auto-generated method stub
		return iOCardbdIPDAO.UpdateRecord(ioCardMachineIP,updateUser);
	}

	public boolean DeleteIOCardMaIP(String Deviceip, String updateUser) {
		// TODO Auto-generated method stub
		return iOCardbdIPDAO.DeleteIOCardMaIP(Deviceip,updateUser);
	}
}
