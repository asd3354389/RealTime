package com.foxlink.realtime.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.LineMapping;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.LineMappingMapper;

public class LineMappingDAO extends DAO<LineMapping> {
	private static Logger logger = Logger.getLogger(LineMappingDAO.class);
	
	@Override
	public boolean AddNewRecord(LineMapping newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(LineMapping updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LineMapping> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineMapping> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		List<LineMapping> lineMapping = null;
		// TODO Auto-generated method stub
		String sSQL = "select t.org_name,t.mes_pdline_id,t.mes_pdline_name,t.mes_pdline_desc,t.rt_lineno,t.std_man_power FROM SWIPE.MES_RT_LINE_MAPPING t "
				+ "where t.enabled = '1' ";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			if(queryCritirea.equals("RTLINE")){
    			sSQL += " and t.rt_lineno = ? ";
    		}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
		    sSQL += " order by t.rt_lineno " ;
		    if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		    lineMapping = jdbcTemplate.query(sSQL,queryList.toArray() ,new LineMappingMapper());	
    	} catch (Exception ex) {
    		  logger.error("Find LineMapping TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return lineMapping;
	}

	@Override
	public List<LineMapping> FindRecord(String userDataCostId, int currentPage, int totalRecord, LineMapping t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LineMapping> FindRecords(LineMapping t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) from MES_RT_LINE_MAPPING t where t.enabled = '1' ";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		if(queryCritirea.equals("RTLINE")){
    			sSQL += " and t.rt_lineno = ? ";
    		}
    		if(!queryCritirea.equals("")){
    			queryList.add(queryParam);
    		}
    		
    		totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);	
		 
    	} catch (Exception ex) {
    		  logger.error("Find LineMapping TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
    	return totalRecord;
	}

	@Override
	public int getTotalRecords(String userDataCostId, LineMapping t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean UpdateRecord(LineMapping lineMapping, String updateUser) {
		// TODO Auto-generated method stub
		int updateRow = -1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sql = "update MES_RT_LINE_MAPPING t set t.org_name = ?,t.mes_pdline_id = ?,t.mes_pdline_name = ?,t.mes_pdline_desc = ?,t.std_man_power= ?"
				+ ",t.update_time = sysdate,t.update_user = ? where t.enabled = '1' and t.rt_lineno = ?";
		try{
			if(lineMapping!=null){
				System.out.println(updateRow+"updateRow");
				updateRow=jdbcTemplate.update(sql,new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, lineMapping.getORG_NAME());
						ps.setInt(2, lineMapping.getMES_PDLINE_ID());
						ps.setString(3, lineMapping.getMES_PDLINE_NAME());
						ps.setString(4, lineMapping.getMES_PDLINE_DESC());
						ps.setInt(5, lineMapping.getSTD_MAN_POWER());
						ps.setString(6, updateUser);
						ps.setString(7, lineMapping.getRT_LINENO());
					}
					
				});
				transactionManager.commit(txStatus);
			}
		}catch(Exception e){
			logger.error("Update LineMapping is failed",e);
			transactionManager.rollback(txStatus);
		}
		System.out.println(updateRow+"updateRow");
		if(updateRow > 0) 
			   return true; 
			else
			   return false;
	}

	public List<String> getRTLine() {
		// TODO Auto-generated method stub
		List<String> list = null;
		String sSQL = "select t.Lineno from LINENO t where t.lineno is not null and t.lineno not in(select a.rt_lineno from MES_RT_LINE_MAPPING a) order by t.lineno asc";
    	try {
    		
    		list = jdbcTemplate.query(sSQL, new RowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					return rs.getString(1);
				}
    			
    		});
    	} catch (Exception ex) {
    		  logger.error("Find LineMapping TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
		return list;
	}

	public boolean checkRTLine(String rt_LINENO) {
		// TODO Auto-generated method stub
		List<LineMapping> lineMapping = null;
		boolean checkReault = false;
		// TODO Auto-generated method stub
		String sSQL = "select t.org_name,t.mes_pdline_id,t.mes_pdline_name,t.mes_pdline_desc,t.rt_lineno,t.std_man_power "
				+ "FROM SWIPE.MES_RT_LINE_MAPPING t where t.enabled = '1' and t.rt_lineno = ?";
		try {
			List <Object> queryList=new  ArrayList<Object>();
			queryList.add(rt_LINENO);
		    lineMapping = jdbcTemplate.query(sSQL,queryList.toArray() ,new LineMappingMapper());	
    	} catch (Exception ex) {
    		  logger.error("Find LineMapping TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    	}
		if(lineMapping.size()>0){
			checkReault = true;
		}
		return checkReault;
	}

	public boolean AddNewRecord(LineMapping lineMapping, String insertUser) {
		// TODO Auto-generated method stub
		boolean insertReault = false;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		int insertRow = -1;
		String sql = "insert into SWIPE.MES_RT_LINE_MAPPING(ORG_NAME,MES_PDLINE_ID,MES_PDLINE_NAME,MES_PDLINE_DESC,RT_LINENO,STD_MAN_POWER,UPDATE_TIME,UPDATE_USER,ENABLED) "
				+ "VALUES(?,?,?,?,?,?,sysdate,?,?)";
		try{
			if(lineMapping!=null){
				insertRow = jdbcTemplate.update(sql, new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setString(1, lineMapping.getORG_NAME());
						ps.setInt(2, lineMapping.getMES_PDLINE_ID());
						ps.setString(3, lineMapping.getMES_PDLINE_NAME());
						ps.setString(4, lineMapping.getMES_PDLINE_DESC());
						ps.setString(5, lineMapping.getRT_LINENO());
						ps.setInt(6, lineMapping.getSTD_MAN_POWER());
						ps.setString(7, insertUser);
						ps.setInt(8, 1);
					}
				});
				transactionManager.commit(txStatus);
			}
		}catch(Exception ex){
			logger.error("Find InsertLineMapping TotalRecord are failed ",ex);
			transactionManager.rollback(txStatus);
		}
		if(insertRow>0){
			insertReault = true;
		}else{
			insertReault = false;
		}
		
		return insertReault;
	}

}
