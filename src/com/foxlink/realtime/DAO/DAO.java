package com.foxlink.realtime.DAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.foxlink.realtime.model.QueryStatus;
import com.foxlink.realtime.model.User;

public abstract class DAO<T> {

	protected DataSourceTransactionManager  transactionManager;
	protected JdbcTemplate jdbcTemplate;
	protected TransactionDefinition txDef;
	TransactionStatus txStatus;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	/*protected DataSourceTransactionManager  transactionManager;

/*	
	protected DataSourceTransactionManager  transactionManager;
	public DataSource dataSource;
	public JdbcTemplate jdbcTemplate;
	protected TransactionDefinition txDef;
	TransactionStatus txStatus;
	
	public void setDataSource(DataSource ds) {
		// TODO Auto-generated method stub
		this.dataSource=ds;
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		transactionManager = new DataSourceTransactionManager(ds); 

	}*/
/*Abstract Method*/
	public abstract boolean AddNewRecord(T newRecord);
	public abstract boolean UpdateRecord(T updateRecord);
	public abstract boolean DeleteRecord(String recordID,String updateUser);
	public abstract List<T> FindAllRecords();
	public abstract List<T> FindAllRecords(int currentPage,int totalRecord,String queryCritirea,String queryParam);
	public abstract List<T> FindRecord(String userDataCostId,int currentPage,int totalRecord,T t); 
	public abstract List<T> FindRecords(T t); 
	public abstract int getTotalRecord(String queryCritirea,String queryParam);
	public abstract int getTotalRecords(String userDataCostId,T t);

	
	
}
