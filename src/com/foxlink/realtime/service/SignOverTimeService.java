package com.foxlink.realtime.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import com.foxlink.realtime.DAO.SignOverTimeDAO;
import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.SignOverTime;
import com.foxlink.realtime.model.UserDate;

public class SignOverTimeService extends Service<UserDate> {
	private SignOverTimeDAO signOverTimeDAO;
	

	public void setSignOverTimeDAO(SignOverTimeDAO signOverTimeDAO) {
		this.signOverTimeDAO = signOverTimeDAO;
	}

	@Override
	public boolean AddNewRecord(UserDate t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(UserDate t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserDate> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDate> FindRecord(UserDate t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDate> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDate> FindQueryRecords(String userDataCostId, UserDate t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDate> FindQueryRecord(String userDataCostId, int currentPage, UserDate t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDate> login(String userName, String password) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.login(userName,password);
	}

	public List<Emp> login() {
		// TODO Auto-generated method stub
		return signOverTimeDAO.login();
	}

	public List<SignOverTime> FindAllRecords(String username, String shift) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.FindAllRecords(username,shift);
	}

	public List<SignOverTime> FindOneRecord(String id, String shift) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.FindOneRecord(id,shift);
	}

	public boolean SaveSign(String id, long l) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.SaveSign(id,l);
	}

	public boolean upload(String id, String confirm_time, String login_user, String overtimedate, float overtimehours,
			InputStream fin, int contentlength) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.upload(id,confirm_time,login_user,overtimedate,overtimehours,fin,contentlength);
	}

	public void getSignPicture(String id, String overtimedate, ByteArrayOutputStream byteOutputStream) throws IOException{
		// TODO Auto-generated method stub
		signOverTimeDAO.getSignPicture(id,overtimedate,byteOutputStream);
	}

	public List<UserDate> checkRole(String username, String password) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.checkRole(username,password);
	}

	public boolean update(String id, String confirm_time, String login_user, String overtimedate, float overtimehours,
			InputStream fin, int contentlength) {
		// TODO Auto-generated method stub
		return signOverTimeDAO.update(id,confirm_time,login_user,overtimedate,overtimehours,fin,contentlength);
	}

}
