package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;
import com.foxlink.realtime.DAO.ProdPersonDAO;
import com.foxlink.realtime.model.ProdPerson;
import com.foxlink.realtime.model.SelectProdList;
import com.foxlink.realtime.model.SelectProdPerson;

public class ProdPersonService extends Service<ProdPersonService>{
	private static Logger logger = Logger.getLogger(ExceptionCostService.class);
	private ProdPersonDAO prodPersonDAO;
	
	public void setProdPersonDAO(ProdPersonDAO prodPersonDAO) {
		this.prodPersonDAO = prodPersonDAO;
	}
	@Override
	public boolean AddNewRecord(ProdPersonService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ProdPersonService t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProdPersonService> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonService> FindRecord(ProdPersonService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonService> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonService> FindQueryRecords(String userDataCostId, ProdPersonService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdPersonService> FindQueryRecord(String userDataCostId, int currentPage, ProdPersonService t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//查询費用代碼
		public String CheckCostId(String assistantId) {
			return prodPersonDAO.CheckCostId(assistantId);
		}
	//插入數據
	public int insertProd(ProdPerson prodPerson) {
		return prodPersonDAO.insertProd(prodPerson);
	}
	//刪除排配機種
	public List<SelectProdList> selectProd(SelectProdPerson selectProdPerson) {
		return prodPersonDAO.selectProd(selectProdPerson);
	}
	//deleteProd 刪除排配機種
	public boolean deleteProd(String recordid) {
		return prodPersonDAO.deleteProd(recordid);
	}
	

}
