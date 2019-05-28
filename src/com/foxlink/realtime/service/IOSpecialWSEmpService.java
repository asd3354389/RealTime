package com.foxlink.realtime.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;

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
	//員工保密車間權限
		public boolean addIOSpecialWSEmp(IOWorkShopPW[] ioWorkShopPW, String updateUser) {
			// TODO Auto-generated method stub

			return iOSpecialWSEmpDAO.addIOSpecialWSEmp(ioWorkShopPW,updateUser);
		}
	//臺干和廠商保密車間權限  addIOSpecialWSEmpOther
		public boolean addIOSpecialWSEmpOther(IOWorkShopPW[] ioWorkShopPW, String updateUser) {
			// TODO Auto-generated method stub

			return iOSpecialWSEmpDAO.addIOSpecialWSEmpOther(ioWorkShopPW,updateUser);
		}
//	public String addIOSpecialWSEmp(IOWorkShopPW[] ioWorkShopPW, String updateUser) {
//		// TODO Auto-generated method stub
//		String[] empArray = ioWorkShopPW.getEmp_id().split(",");
//		String message = "";
//		HashSet<String> exEmpset = new HashSet();
//		HashSet<String> noexEmpset = new HashSet();
//		for (String empID : empArray) {
//			if(iOSpecialWSEmpDAO.checkEmpIdExistence(empID)){
//				exEmpset.add(empID.toUpperCase());
//			}else{
//				noexEmpset.add(empID.toUpperCase());
//			}
//		}
//		List<String> exEmpList = new ArrayList(exEmpset);
//		List<String> noexEmpList = new ArrayList(noexEmpset);
//		
//		
//		if(exEmpList.size()>0){
//			if(iOSpecialWSEmpDAO.addIOSpecialWSEmp(exEmpList,ioWorkShopPW,updateUser)){
//				message +="保密車間工號綁定車間成功\n";
//			}else{
//				message +="保密車間工號綁定車間名稱失敗\n";
//			}
//		}else{
//			message += "無在職員工工號\n";
//		}
//		
//		if(noexEmpList.size()>0){
//			for(int i = 0;i<noexEmpList.size();i++){
//				if(i == noexEmpList.size()-1){
//					message += noexEmpList.get(i)+"以上員工不存在";
//				}else{
//					message += noexEmpList.get(i)+",";
//				}
//			}
//		}
//		
//		return message;
//	}
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
	//String emp_id, String updateUser,String CardID,String WorkShopNo
	public boolean DeleteIOWorkShopPW(String emp_id, String workShopNo, String updateUser,String CardID) {
		// TODO Auto-generated method stub
		return iOSpecialWSEmpDAO.DeleteIOWorkShopPW(emp_id,workShopNo,updateUser,CardID);
	}
	
}
