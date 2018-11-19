package com.foxlink.realtime.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foxlink.realtime.model.QueryStatus;

public abstract class Service<T>{
/*	public ApplicationContext context=null;
	/*public ApplicationContext context=null;
	public Service() {
		this.context=new ClassPathXmlApplicationContext("Beans.xml");
	}*/
	
	public abstract boolean AddNewRecord(T t);
	public abstract boolean UpdateRecord(T t);
	public abstract boolean DeleteRecord(String recordID,String updateUser);
	public abstract List<T> FindAllRecords();
	public abstract List<T> FindRecord(T t);
	public abstract List<T> FindAllRecords(int currentPage,String queryCritirea,String queryParam);
	public abstract List<T> FindQueryRecords(String userDataCostId,T t);
	public abstract List<T> FindQueryRecord(String userDataCostId,int currentPage,T t);
}