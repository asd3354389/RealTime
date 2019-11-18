package com.foxlink.realtime.DAO;

import java.util.List;

import org.apache.log4j.Logger;

public class QuertAbTimeByCostIdDAO {
	private static Logger logger = Logger.getLogger(QuertAbTimeByCostIdDAO.class);

	public List<String> FindDepidRecords(String costid) {
		// TODO Auto-generated method stub
		List<String> getDepids=null;
		String sSQL="select distinct(depid) from swipe.MANPOWER_STATUS where depid like '%XR%' and costid='"+costid+"' order by depid";
		try { 			
//				getDepids=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error("Search DepidRecords Record is failed",ex);
		}
		return getDepids;
	}
}
