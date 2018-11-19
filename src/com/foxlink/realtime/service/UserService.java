package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.User;

public class UserService extends Service<User> {
	private static Logger logger=Logger.getLogger(UserService.class);
	
	public UserService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean AddNewRecord(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> FindRecord(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	


	@Override
	public List<User> FindQueryRecord(String userDataCostId, int currentPage, User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> FindQueryRecords(String userDataCostId, User t) {
		// TODO Auto-generated method stub
		return null;
	}

}
