package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.ProdAllLine;
import com.foxlink.realtime.model.objectMapper.GetDepidMapper;
import com.foxlink.realtime.model.objectMapper.QueryCountEmp;
import com.foxlink.realtime.model.objectMapper.QueryProdAllLine;

public class CountEmpDAO extends DAO<ManPowerStatus> {
	private static Logger logger = Logger.getLogger(CountEmpDAO.class);

	public List<String> FindDepidRecords(String userDataCostId, String time) {
		// TODO Auto-generated method stub
		List<String> getDepids=null;
		String sSQL="select distinct(depid) from swipe.MANPOWER_STATUS where to_char(WorkDate,'yyyy-mm-dd')='"+time+"' and depid like '%XR%'";
		try { 
				if(!userDataCostId.equals("")){
	 				sSQL+=" and costId in(";  
	 				  String [] costIdArray = userDataCostId.split("\\*");
	 		            for(int i=0;i<costIdArray.length;i++){
	 		            	sSQL+="'"+costIdArray[i].trim()+"'";
	 		                if(costIdArray.length-1!=i)
	 		                	sSQL+=",";
	 		                else
	 		                	sSQL+=") ";				                
	 		               }
	 			}
				sSQL += "order by depid";				
				getDepids=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error("Search DepidRecords Record is failed",ex);
		}
		return getDepids;
	}

	@Override
	public boolean AddNewRecord(ManPowerStatus newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ManPowerStatus updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ManPowerStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindAllRecords(int currentPage, int totalRecord, String queryCritirea,
			String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindRecord(String userDataCostId, int currentPage, int totalRecord, ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindRecords(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, ManPowerStatus t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String searchDate() {
		// TODO Auto-generated method stub
		String sql="select Max(workdate) from manpower_status";
		String c= jdbcTemplate.queryForObject(sql,String.class);
		return c;
	}

	public List<ManPowerStatus> searchCountEmpList(String depid, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> CEmpList = null;
		String sSQl = "select id,name,depid,costid,status,class_no from manpower_status where depid=? and to_char(WorkDate,'yyyy-mm-dd')=? order by class_no  DESC";
		try {
			CEmpList = jdbcTemplate.query(sSQl, new Object[] {depid,sDate},new QueryCountEmp());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find CountEmpList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return CEmpList;
	}

	public int searchSunCount(String depid, String sDate) {
		// TODO Auto-generated method stub
		int sun =0;
		String sSQl = "select count(0) from manpower_status where depid=? and to_char(WorkDate,'yyyy-mm-dd')=? and class_no!='11'";
		try {
			sun = jdbcTemplate.queryForObject(sSQl, new Object[] {depid,sDate},Integer.class);
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find SunCount TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return sun;
	}

	public int searchNightCount(String depid, String sDate) {
		// TODO Auto-generated method stub
		int nigth =0;
		String sSQl = "select count(0) from manpower_status where depid=? and to_char(WorkDate,'yyyy-mm-dd')=? and class_no='11'";
		try {
			nigth = jdbcTemplate.queryForObject(sSQl, new Object[] {depid,sDate},Integer.class);
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find NightCount TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return nigth;
	}

	public boolean UpdateStatus(String userNo, String depid, String sDate, String type_status, String class_no) {
		// TODO Auto-generated method stub
		int updateRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL="";
		if(!class_no.equals("11")) {
			sSQL+="update manpower_status set status =? where id=? and to_char(workdate,'yyyy-mm-dd') = ?  and class_no != '11'" ;
		}else {
		    sSQL+="update manpower_status set status =? where id=? and to_char(workdate,'yyyy-mm-dd') =?  and class_no = '11' ";
		}
		
		try {
				updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, type_status);
						arg0.setString(2, userNo);
						arg0.setString(3, sDate);						
					}	
				});
				transactionManager.commit(txStatus);
				System.out.println(sSQL);
		}
		
		catch(Exception ex) {
			logger.error("Update Status is failed",ex);
			transactionManager.rollback(txStatus);
		}			
			if(updateRow > 0) 
				   return true; 
				else
				   return false;
	}

	public List<String> FindAssistantDepid(String username) {
		// TODO Auto-generated method stub
		List<String> getDepids=null;
		String sSQL="select DEPARTMENTCODE from swipe.user_data where enabled='1' and username='"+username+"'";
		try { 
				sSQL += "order by DEPARTMENTCODE";				
				getDepids=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error("Search AssistantDepid Record is failed",ex);
		}
		return getDepids;
	}
	
}
