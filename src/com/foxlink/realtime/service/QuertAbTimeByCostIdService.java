package com.foxlink.realtime.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.QuertAbTimeByCostIdDAO;
import com.google.gson.JsonElement;


public class QuertAbTimeByCostIdService {
	private static Logger logger = Logger.getLogger(QuertAbTimeByCostIdService.class);
	private QuertAbTimeByCostIdDAO qertAbTimeByCostIdDAO;
	
	public void setQuertAbTimeByCostIdDAO(QuertAbTimeByCostIdDAO qertAbTimeByCostIdDAO) {
		this.qertAbTimeByCostIdDAO = qertAbTimeByCostIdDAO;
	}

	public List<String> FindDepidRecords(String costid) {
		// TODO Auto-generated method stub
		List<String> allDepid=null;
		try {
			allDepid=qertAbTimeByCostIdDAO.FindDepidRecords(costid);
		} catch (Exception ex) {
			logger.error("query error" + ex);
		}

		return allDepid;
	}
}
