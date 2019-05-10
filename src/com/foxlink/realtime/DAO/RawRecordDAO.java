package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.RawRecord;
import com.foxlink.realtime.model.SearchRawRecordInfo;
import com.foxlink.realtime.model.objectMapper.SearchRawRecordInfoMapper;

public class RawRecordDAO  extends DAO<RawRecord> {
private static Logger logger=Logger.getLogger(RawRecordDAO.class);	
	
	public RawRecordDAO(){
		super();
	}

	@Override
	public boolean AddNewRecord(RawRecord newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(RawRecord updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RawRecord> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<RawRecord> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getTotalRecord(String userDataCostId,String empId,String depId,String costId,String startDate,String endDate,String recordStatus) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.raw_record r join csr_employee e on r.cardId=e.cardId where e.isOnwork='0'";
    	try {     		
    		 List <Object> queryList=new  ArrayList<Object>();  
    		 if(!empId.equals("")){
 				sSQL+=" and r.id in(";  
 				  String [] empIdArray = empId.split(",");
 		            for(int i=0;i<empIdArray.length;i++){
 		            	sSQL+="'"+empIdArray[i].trim()+"'";
 		                if(empIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			
 			}
 			if(!depId.equals("")){
 				sSQL+=" and e.depId in(";  
 				  String [] depIdArray = depId.split(",");
 		            for(int i=0;i<depIdArray.length;i++){
 		            	sSQL+="'"+depIdArray[i].trim()+"'";
 		                if(depIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			}
 			if(!costId.equals("")){
 				sSQL+=" and e.costId in(";  
 				  String [] costIdArray = costId.split(",");
 		            for(int i=0;i<costIdArray.length;i++){
 		            	sSQL+="'"+costIdArray[i].trim()+"'";
 		                if(costIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			}
 			if(!startDate.equals("") && !endDate.equals("")){
 				sSQL+=" and r.SwipeCardTime >= to_date(?,'yyyy-MM-dd HH24:mi:ss') and r.SwipeCardTime< to_date(?,'yyyy-MM-dd HH24:mi:ss')";  
 				queryList.add(startDate);
 				queryList.add(endDate);
 			}
 	        
 			if(!recordStatus.equals("")){
 				sSQL+=" and r.RECORD_STATUS in(";  
 				  String [] recordStatusArray = recordStatus.split(",");
 		            for(int i=0;i<recordStatusArray.length;i++){
 		            	sSQL+="'"+recordStatusArray[i].trim()+"'";
 		            	 if(recordStatusArray.length-1!=i)
  		                	sSQL+=",";
  		                else
  		                	sSQL+=") ";				
 		               }
 			}
 			
 			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and e.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sSQL += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sSQL += ",";
						else
							sSQL += ") ";
					}
				}
			}else{
				sSQL += " and e.costId in('')";
			}
 			
 		   totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
 		
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}
	
	public List<SearchRawRecordInfo> FindSearchRawRecords(String userDataCostId,int currentPage,int totalRecord,String empId,String depId,String costId,String startDate,String endDate,String recordStatus,Boolean isShowAll) {
		// TODO Auto-generated method stub
		List<SearchRawRecordInfo> searchRawRecord = null;
		String sSQL = "select * from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from "
				+ "(SELECT r.id empId,e.name,e.depid,e.costId,to_char(r.SwipeCardTime,'yyyy-MM-dd HH24:mi:ss') swipeCardTime,r.IP_ADDRESS swipeCardIpAddress"
				+ " FROM SWIPE.raw_record r join SWIPE.csr_employee e on r.id=e.id where e.isOnwork='0'";
		try {	
			 List <Object> queryList=new  ArrayList<Object>();  
			if(!empId.equals("")){
				sSQL+=" and r.id in(";  
				  String [] empIdArray = empId.split(",");
		            for(int i=0;i<empIdArray.length;i++){
		            	sSQL+="'"+empIdArray[i].trim()+"'";
		                if(empIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			
			}
			if(!depId.equals("")){
				sSQL+=" and e.depId in(";  
				  String [] depIdArray = depId.split(",");
		            for(int i=0;i<depIdArray.length;i++){
		            	sSQL+="'"+depIdArray[i].trim()+"'";
		                if(depIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			}
			if(!costId.equals("")){
				sSQL+=" and e.costId in(";  
				  String [] costIdArray = costId.split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sSQL+="'"+costIdArray[i].trim()+"'";
		                if(costIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			}
			if(!startDate.equals("") && !endDate.equals("")){
				sSQL+=" and r.SwipeCardTime >= to_date(?,'yyyy-MM-dd HH24:mi:ss') and r.SwipeCardTime< to_date(?,'yyyy-MM-dd HH24:mi:ss')";  
				queryList.add(startDate);
				queryList.add(endDate);
			}
			
			if(!recordStatus.equals("")){
 				sSQL+=" and r.RECORD_STATUS in(";  
 				  String [] recordStatusArray = recordStatus.split(",");
 		            for(int i=0;i<recordStatusArray.length;i++){
 		            	sSQL+="'"+recordStatusArray[i].trim()+"'";
 		                if(recordStatusArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";		
 		                
 		               }
 			}
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and e.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sSQL += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sSQL += ",";
						else
							sSQL += ") ";
					}
				}
			}else{
				sSQL += " and e.costId in('')";
			}
			
			if(!isShowAll){
				Page page = new Page(currentPage, totalRecord);	  
				int endIndex=page.getStartIndex() + page.getPageSize();
				sSQL += " order by e.costID,e.depid,r.id,r.swipecardtime ) a ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
			}
			else
			{
				sSQL += " order by e.costID,e.depid,r.id,r.swipecardtime ) a ) where 1=1";
			}
		    	    
		    searchRawRecord = jdbcTemplate.query(sSQL,  queryList.toArray(), new SearchRawRecordInfoMapper());			    
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search RawRecords Record is failed",ex);
		}
		return searchRawRecord;
	}

	

	

	@Override
	public List<RawRecord> FindRecords(RawRecord t) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<RawRecord> FindRecord(String userDataCostId, int currentPage, int totalRecord, RawRecord t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, RawRecord t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
