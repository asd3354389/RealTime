package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxlink.realtime.DAO.FourteenROPDAO;
import com.foxlink.realtime.model.FourteenROP;
import com.foxlink.realtime.model.Page;

@Service
public class FourteenROPService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private FourteenROPDAO fourteenROPDAO;

	public Page getFourteenROPPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		int totalRecord = fourteenROPDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}
	
	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<FourteenROP> AllFourteenRO = null;
		try{
			int totalRecord = fourteenROPDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllFourteenRO = fourteenROPDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find fourteenROP Record is failed ",e);
		}
		return AllFourteenRO;
	}

	public boolean DeleteFourteenROP(String id, String updateUser, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return fourteenROPDAO.DeleteFourteenROP(id,updateUser,startDate,endDate);
	}

	public boolean addfourteenROP(FourteenROP[] fourteenROP, String updateUser) {
		// TODO Auto-generated method stub
		return fourteenROPDAO.addfourteenROP(fourteenROP,updateUser);
	}



}
