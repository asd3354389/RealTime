package com.foxlink.realtime.DAO;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.FileCopyUtils;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.SignOverTime;
import com.foxlink.realtime.model.UserDate;
import com.foxlink.realtime.model.objectMapper.QueryEMPMapper;
import com.foxlink.realtime.model.objectMapper.QueryOTMapper;
import com.foxlink.realtime.model.objectMapper.SignOverTimeMapper;
import com.foxlink.realtime.model.objectMapper.UserDateMapper;

public class SignOverTimeDAO extends DAO<UserDate> {
	private static Logger logger=Logger.getLogger(SignOverTimeDAO.class);

	@Override
	public boolean AddNewRecord(UserDate newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(UserDate updateRecord) {
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
	public List<UserDate> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDate> FindRecord(String userDataCostId, int currentPage, int totalRecord, UserDate t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDate> FindRecords(UserDate t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, UserDate t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<UserDate> login(String userName, String password) {
		List<UserDate> userDate = null;
		String sSQL = "select a.username,a.password,b.role from user_data a join user_roles b on a.username = b.username where "
				+ "a.username = ? and a.password = ? ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(userName);
			queryList.add(password);
			
			userDate=jdbcTemplate.query(sSQL, queryList.toArray(),new UserDateMapper());
			
    	} catch (Exception ex) {
    		  ex.printStackTrace();
    	}
		return userDate;
	}
	
	public List<UserDate> checkRole(String username, String password) {
		List<UserDate> userDate = null;
		String sSQL = "select a.username,a.password,b.role from user_data a join user_roles b on a.username = b.username where "
				+ "a.username = ? and a.password = ? and b.role = 'ROLE_LineLeader' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(username);
			queryList.add(password);
			
			userDate=jdbcTemplate.query(sSQL, queryList.toArray(),new UserDateMapper());
			
    	} catch (Exception ex) {
    		  ex.printStackTrace();
    	}
		return userDate;
	}

	public List<Emp> login() {
		// TODO Auto-generated method stub
		List<Emp> emp = null;
		String sSQL = "Select * from csr_employee t where t.costid = '8146'";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			
			emp=jdbcTemplate.query(sSQL,new QueryEMPMapper());
			
    	} catch (Exception ex) {
    		  ex.printStackTrace();
    	}
		return emp;
	}

	public List<SignOverTime> FindAllRecords(String username, String shift) {
		// TODO Auto-generated method stub
		String sql = "select a.id,a.name,a.depid,b.class_no,to_char(b.emp_date,'yyyy-mm-dd') overtimeDate,'0' status from csr_employee a,emp_class b,classno c where a.depid in (select d.departmentcode from user_data d  where d.username = ?) and "
				+ "a.id = b.id and b.class_no = c.class_no and c.shift = ? and a.isonwork = '0' ";
		List<SignOverTime> list= null;
		try{
			System.out.println(shift);
			if(shift.equals("D")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate,'yyyymmdd') "
						+ "and a.id not in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate,'yyyy-mm-dd')) ";
			}else if(shift.equals("N")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate-1,'yyyymmdd') "
						+ "and a.id not in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate-1,'yyyy-mm-dd')) ";
			}
			
			
			sql+= "union all select a.id,a.name,a.depid,b.class_no,to_char(b.emp_date,'yyyy-mm-dd') overtimeDate,'1' status from csr_employee a,emp_class b,classno c  "
					+ "where a.depid in (select d.departmentcode from user_data d  where d.username = ?) and a.id = b.id and b.class_no = c.class_no and c.shift = ? and a.isonwork = '0' ";
			if(shift.equals("D")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate,'yyyymmdd') "
						+ "and a.id in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate,'yyyy-mm-dd')) ";
			}else if(shift.equals("N")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate-1,'yyyymmdd') "
						+ "and a.id in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate-1,'yyyy-mm-dd')) ";
			}
			sql += "order by 1 asc";
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(username);
			queryList.add(shift);
			queryList.add(username);
			queryList.add(shift);
			list=jdbcTemplate.query(sql, queryList.toArray(),new SignOverTimeMapper());
			System.out.println(sql);
			System.out.println(list);
		}catch(Exception ex){
			logger.error("Select AllQueryStatus is failed",ex);
			ex.printStackTrace();
		}
		return list;
	}

	public List<SignOverTime> FindOneRecord(String id, String shift) {
		// TODO Auto-generated method stub
		String sql = "select a.id,a.name,a.depid,b.class_no,to_char(b.emp_date,'yyyy-mm-dd') as overtimeDate,'0' status from csr_employee a,emp_class b,classno c where a.id = ? "
				+ "and a.id = b.id and b.class_no = c.class_no and c.shift = ? and a.isonwork = '0' ";
		List<SignOverTime> list = null;
		try{
			System.out.println(shift);
			if(shift.equals("D")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate,'yyyymmdd') "
						+ "and a.id not in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate,'yyyy-mm-dd')) ";
			}else if(shift.equals("N")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate-1,'yyyymmdd') "
						+ "and a.id not in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate-1,'yyyy-mm-dd')) ";
			}
			sql += "union all select a.id,a.name,a.depid,b.class_no,to_char(b.emp_date,'yyyy-mm-dd') as overtimeDate,'1' status from csr_employee a,emp_class b,classno c where a.id = ? "
					+ "and a.id = b.id and b.class_no = c.class_no and c.shift = ? and a.isonwork = '0' ";
			if(shift.equals("D")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate,'yyyymmdd') "
						+ "and a.id in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate,'yyyy-mm-dd')) ";
			}else if(shift.equals("N")){
				sql += "and to_char(b.emp_date,'yyyymmdd') = to_char(sysdate-1,'yyyymmdd') "
						+ "and a.id in (select t.id from OVERTIME_CONFIRM t where t.overtimedate = to_char(sysdate-1,'yyyy-mm-dd')) ";
			}
			System.out.println(id+"!------!"+shift);
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(id);
			queryList.add(shift);
			queryList.add(id);
			queryList.add(shift);
			list=jdbcTemplate.query(sql, queryList.toArray(),new SignOverTimeMapper());
		}catch(Exception ex){
			logger.error("Select OneQueryStatus is failed",ex);
			ex.printStackTrace();
		}
		return list;
	}

	public boolean SaveSign(String id, long l) {
		// TODO Auto-generated method stub
		int updateRow = -1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sql = "insert into OVERTIME_CONFIRM(ID,OVERTIMEDATE,SIGNATURE_PICTURE) values(?,'2018-01-01',?)";
		try{
			if(id!=null&&id!=""){
				updateRow=jdbcTemplate.update(sql,new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, id);
					}
					
				});
				transactionManager.commit(txStatus);
			}
		}catch(Exception e){
			logger.error("Save Sign is failed",e);
			transactionManager.rollback(txStatus);
		}
		
		if(updateRow > 0) 
			   return true; 
			else
			   return false;
	}

	public boolean upload(String id, String confirm_time, String login_user, String overtimedate, float overtimehours,
			InputStream fin, int contentlength) {
		// TODO Auto-generated method stub
		int updateRow = -1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		LobHandler lobHandler = new DefaultLobHandler();
		String sql = "insert into OVERTIME_CONFIRM(ID,OVERTIMEDATE,SIGNATURE_PICTURE,overtimehours,confirm_time,login_user,import_time) values(?,?,?,?,?,?,sysdate)";
		try{
			if(id!=null&&id!=""){
				updateRow=jdbcTemplate.execute(sql,
					       new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					         protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					           ps.setString(1, id);
					           ps.setString(2, overtimedate);
					           lobCreator.setBlobAsBinaryStream(ps, 3, fin, contentlength);
					           ps.setFloat(4, overtimehours);
					           ps.setString(5, confirm_time);
					           ps.setString(6, login_user);
					        }
					      }
					  );
				transactionManager.commit(txStatus);
			}
			
		}catch(Exception e){
			logger.error("Upload Sign is failed",e);
			transactionManager.rollback(txStatus);
		}finally {
			try {
				if(fin != null){
					fin.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(updateRow > 0) 
			   return true; 
			else
			   return false;
	}

	public void getSignPicture(String id, String overtimedate, ByteArrayOutputStream byteOutputStream) throws IOException{
		// TODO Auto-generated method stub
		String sql = "select t.signature_picture from OVERTIME_CONFIRM t where t.id = ? and t.overtimedate = ? ";
		LobHandler lobHandler = new DefaultLobHandler();
		List <Object> queryList=new  ArrayList<Object>();
		queryList.add(id);
		queryList.add(overtimedate);
		jdbcTemplate.query(sql,queryList.toArray(),new AbstractLobStreamingResultSetExtractor(){
		protected void streamData(ResultSet rs) throws SQLException,IOException,DataAccessException{
			FileCopyUtils.copy(lobHandler.getBlobAsBinaryStream(rs,"signature_picture"),byteOutputStream);
		}
		});
	}

	public boolean update(String id, String confirm_time, String login_user, String overtimedate, float overtimehours,
			InputStream fin, int contentlength) {
		// TODO Auto-generated method stub
		System.out.println(id+login_user);
		int updateRow = -1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		LobHandler lobHandler = new DefaultLobHandler();
		String sql = "update OVERTIME_CONFIRM t set t.overtimehours = ?,t.signature_picture = ?,t.confirm_time= ?,t.login_user = ? where t.id = ? and t.overtimedate = ? ";
		try{
			if(id!=null&&id!=""){
				updateRow=jdbcTemplate.execute(sql,
					       new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					         protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					           ps.setFloat(1, overtimehours);
					           lobCreator.setBlobAsBinaryStream(ps, 2, fin, contentlength);
					           ps.setString(3, confirm_time);
					           ps.setString(4, login_user);
					           ps.setString(5, id);
					           ps.setString(6, overtimedate);
					        }
					      }
					  );
				transactionManager.commit(txStatus);
			}
			
		}catch(Exception e){
			logger.error("Update Sign is failed",e);
			transactionManager.rollback(txStatus);
		}finally {
			try {
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(updateRow > 0) 
			   return true; 
			else
			   return false;
	}

	

}
