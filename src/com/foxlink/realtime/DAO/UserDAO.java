package com.foxlink.realtime.DAO;

import java.util.List;

import javax.sql.DataSource;

import com.foxlink.realtime.model.User;
import com.foxlink.realtime.model.objectMapper.UserMapper;

public class UserDAO extends DAO<User> {

	@Override
	public boolean AddNewRecord(User newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(User updateRecord) {
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
	public List<User> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<User> FindRecords(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> FindRecord(String userDataCostId, int currentPage, int totalRecord, User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, User t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
