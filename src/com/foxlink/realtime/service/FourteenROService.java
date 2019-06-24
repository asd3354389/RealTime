package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.FourteenRODAO;
import com.foxlink.realtime.model.FourteenRO;
import com.foxlink.realtime.model.IOWorkShopPW;
import com.foxlink.realtime.model.Page;

public class FourteenROService {
	private static Logger logger = Logger.getLogger(FourteenROService.class);
	private FourteenRODAO fourteenRODAO;
	public void setFourteenRODAO(FourteenRODAO fourteenRODAO) {
		this.fourteenRODAO = fourteenRODAO;
	}
	
	public Page getFourteenROPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		int totalRecord = fourteenRODAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	
	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		List<FourteenRO> AllFourteenRO = null;
			try{
				int totalRecord = fourteenRODAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
				AllFourteenRO = fourteenRODAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
				System.out.println(AllFourteenRO);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Find JobTitle Record is failed ",e);
			}
			return AllFourteenRO;
	}

	public boolean addfourteenRO(FourteenRO[] fourteenRO, String updateUser) {
		// TODO Auto-generated method stub
		return fourteenRODAO.addfourteenRO(fourteenRO,updateUser);
	}

	public boolean UpdateRecord(FourteenRO fourteenRO, String updateUser) {
		// TODO Auto-generated method stub
		return fourteenRODAO.UpdateRecord(fourteenRO,updateUser);
	}

	public boolean DeleteFourteenRO(String costid, String updateUser) {
		// TODO Auto-generated method stub
		return fourteenRODAO.DeleteFourteenRO(costid,updateUser);
	}
	
}
