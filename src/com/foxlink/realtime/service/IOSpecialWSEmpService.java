package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.IOSpecialWSEmpDAO;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.WorkshopNoRestInfo;


public class IOSpecialWSEmpService {
	private static Logger logger = Logger.getLogger(IOSpecialWSEmpService.class);
	private IOSpecialWSEmpDAO iOSpecialWSEmpDAO;
	public void setiOSpecialWSEmpDAO(IOSpecialWSEmpDAO iOSpecialWSEmpDAO) {
		this.iOSpecialWSEmpDAO = iOSpecialWSEmpDAO;
	}
	public Page getworkshopNoRestPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = iOSpecialWSEmpDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	public List<IOWorkShopPW> FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		List<IOWorkShopPW> AllIOSpecialWSEmp = null;
		try{
			int totalRecord = iOSpecialWSEmpDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllIOSpecialWSEmp = iOSpecialWSEmpDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
			System.out.println(AllIOSpecialWSEmp);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find JobTitle Record is failed ",e);
		}
		return AllIOSpecialWSEmp;
	}
	public boolean addIOSpecialWSEmp(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		return iOSpecialWSEmpDAO.addIOSpecialWSEmp(ioWorkShopPW,updateUser);
	}
	public boolean checkEmpIdExistence(String Emp_id) {
		// TODO Auto-generated method stub
		return iOSpecialWSEmpDAO.checkEmpIdExistence(Emp_id);
	}
	public boolean checkUserNameDuplicate(String Emp_id, String workShopNo) {
		// TODO Auto-generated method stub
		return iOSpecialWSEmpDAO.checkUserNameDuplicate(Emp_id,workShopNo);
	}
	public boolean UpdateRecord(IOWorkShopPW ioWorkShopPW, String updateUser) {
		// TODO Auto-generated method stub
		return iOSpecialWSEmpDAO.UpdateRecord(ioWorkShopPW,updateUser);
	}
	public boolean DeleteIOWorkShopPW(String emp_id, String workShopNo, String updateUser) {
		// TODO Auto-generated method stub
		return iOSpecialWSEmpDAO.DeleteIOWorkShopPW(emp_id,workShopNo,updateUser);
	}
	
}
