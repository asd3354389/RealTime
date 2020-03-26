package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxlink.realtime.DAO.AccessGoodsDAO;
import com.foxlink.realtime.DAO.FourteenROPDAO;
import com.foxlink.realtime.model.AccessGoods;
import com.foxlink.realtime.model.FourteenROP;
import com.foxlink.realtime.model.Page;
import com.google.gson.JsonElement;

@Service
public class AccessGoodsService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccessGoodsDAO accessGoodsDAO;

	public List<String> FindWorkShopNo() {
		// TODO Auto-generated method stub
		return accessGoodsDAO.FindWorkShopNo();
	}

	public boolean addAccessGoods(AccessGoods[] accessGoods, String updateUser, String accessRole) {
		// TODO Auto-generated method stub
		//System.out.print(accessGoods.length);
		int i =0;
		for(int j=0;j<accessGoods.length;j++) {
			i=i+accessGoodsDAO.Delete(accessGoods[j].getUserId(), accessGoods[j].getCardId(), updateUser, accessRole,accessGoods[j].getWorkShopNo());
		}
		return accessGoodsDAO.addAccessGoods(accessGoods,updateUser,accessRole);
	}

	public Page getFourteenROPPage(int currentPage, String queryCritirea, String queryParam, String updateUser,
			String userDataCostId) {
		// TODO Auto-generated method stub
		int totalRecord = accessGoodsDAO.getTotalRecord(queryCritirea, queryParam, updateUser, userDataCostId);
		Page page = new Page(currentPage, totalRecord);
		// Page page = accountDAO.getPage(pageNum, User.class, totalRecord);
		return page;
	}

	public List FindQueryRecord(String updateUser, int currentPage, String queryCritirea, String queryParam,
			String userDataCostId) {
		// TODO Auto-generated method stub
		List<AccessGoods> AllAccessGoods = null;
		try{
			int totalRecord = accessGoodsDAO.getTotalRecord(queryCritirea, queryParam,updateUser, userDataCostId);
			AllAccessGoods = accessGoodsDAO.FindAllRecords(currentPage, totalRecord, queryCritirea, queryParam,updateUser, userDataCostId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Find accessGoods Record is failed ",e);
		}
		return AllAccessGoods;
	}

	public boolean DeleteAccessGoods(String id, String cardId, String workShopNo, String updateUser, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return accessGoodsDAO.DeleteAccessGoods(id,cardId,workShopNo,updateUser,startDate,endDate);
	}

	/*public boolean checkAccessGoods(AccessGoods[] accessGoods, String accessRole) {
		// TODO Auto-generated method stub
		return accessGoodsDAO.checkAccessGoods(accessGoods,accessRole);
	}*/

}
