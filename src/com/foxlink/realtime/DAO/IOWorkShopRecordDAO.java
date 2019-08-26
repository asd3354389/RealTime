package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.IOWSRecord;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.SearchRawRecordInfo;
import com.foxlink.realtime.model.objectMapper.QueryIOWSRecord;
import com.foxlink.realtime.model.objectMapper.SearchRawRecordInfoMapper;

public class IOWorkShopRecordDAO extends DAO<IOWSRecord>{
	private static Logger logger = Logger.getLogger(IOWorkShopRecordDAO.class);

	@Override
	public boolean AddNewRecord(IOWSRecord newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(IOWSRecord updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IOWSRecord> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWSRecord> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWSRecord> FindRecord(String userDataCostId, int currentPage, int totalRecord, IOWSRecord t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOWSRecord> FindRecords(IOWSRecord t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, IOWSRecord t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String userDataCostId, String empId, String workShopNo,String depId, String costId, String startDate,
			String endDate, String recordStatus, String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_CONTROL_RECORD r join csr_employee e on r.Emp_id=e.id where e.isOnwork='0'";
    	try {     		
    		 List <Object> queryList=new  ArrayList<Object>();  
    		 if(!empId.equals("")){
 				sSQL+=" and r.Emp_id in(";  
 				  String [] empIdArray = empId.split(",");
 		            for(int i=0;i<empIdArray.length;i++){
 		            	sSQL+="'"+empIdArray[i].trim()+"'";
 		                if(empIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			
 			}
    		if(!workShopNo.equals("")){
  				sSQL+=" and r.WorkShopNo in(";  
  				  String [] workShopNoArray = workShopNo.split(",");
  		            for(int i=0;i<workShopNoArray.length;i++){
  		            	sSQL+="'"+workShopNoArray[i].trim()+"'";
  		                if(workShopNoArray.length-1!=i)
  		                	sSQL+=",";
  		                else
  		                	sSQL+=") ";				                
  		               }
  			
  			}
 			if(!depId.equals("")){
 				sSQL+=" and e.deptid in(";  
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
 			
 			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
 			 
 			
 		   totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
 		
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	public List<IOWSRecord> FindSearchRawRecords(String userDataCostId, int currentPage, int totalRecord, String empId,String workShopNo,
			String depId, String costId, String startDate, String endDate, String recordStatus, Boolean isShowAll, String accessRole) {
		// TODO Auto-generated method stub
		List<IOWSRecord> searchRawRecord = null;
		String sSQL = "select * from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from "
				+ "(SELECT r.Emp_id,r.D_Cardid,r.WorkShopNo,e.name,e.depid,e.deptid,e.costId,to_char(r.SwipeCardTime,'yyyy-MM-dd HH24:mi:ss') swipeCardTime,r.Direction"
				+ " FROM SWIPE.RT_ACCESS_CONTROL_RECORD r join SWIPE.csr_employee e on r.Emp_id=e.id where e.isOnwork='0'";
		try {	
			 List <Object> queryList=new  ArrayList<Object>();  
			if(!empId.equals("")){
				sSQL+=" and r.Emp_id in(";  
				  String [] empIdArray = empId.split(",");
		            for(int i=0;i<empIdArray.length;i++){
		            	sSQL+="'"+empIdArray[i].trim()+"'";
		                if(empIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			
			}
			if(!workShopNo.equals("")){
  				sSQL+=" and r.WorkShopNo in(";  
  				  String [] workShopNoArray = workShopNo.split(",");
  		            for(int i=0;i<workShopNoArray.length;i++){
  		            	sSQL+="'"+workShopNoArray[i].trim()+"'";
  		                if(workShopNoArray.length-1!=i)
  		                	sSQL+=",";
  		                else
  		                	sSQL+=") ";				                
  		               }
  			
  			}
			if(!depId.equals("")){
				sSQL+=" and e.deptid in(";  
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
			
			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
			
			
			if(!isShowAll){
				Page page = new Page(currentPage, totalRecord);	  
				int endIndex=page.getStartIndex() + page.getPageSize();
				sSQL += " order by e.costID,e.depid,r.Emp_id,r.SwipeCardTime ) a ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
			}
			else
			{
				sSQL += " order by e.costID,e.depid,r.Emp_id,r.SwipeCardTime ) a ) where 1=1";
			}
		    
		    searchRawRecord = jdbcTemplate.query(sSQL,  queryList.toArray(), new QueryIOWSRecord());			    
		    System.out.println(sSQL);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search IOWSRecord Record is failed",ex);
		}
		return searchRawRecord;
	}

	public int getTotalOtherRecord(String userDataCostId, String workShopNo, String startDate, String endDate,
			String recordStatus, String accessRole) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) FROM SWIPE.RT_ACCESS_CONTROL_RECORD r  where 1=1";
    	try {     		
    		 List <Object> queryList=new  ArrayList<Object>();  
    		if(!workShopNo.equals("")){
  				sSQL+=" and r.WorkShopNo in(";  
  				  String [] workShopNoArray = workShopNo.split(",");
  		            for(int i=0;i<workShopNoArray.length;i++){
  		            	sSQL+="'"+workShopNoArray[i].trim()+"'";
  		                if(workShopNoArray.length-1!=i)
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
 			
 			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
 			 
 			
 		   totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
 		   System.out.println(sSQL);
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}

	public List<IOWSRecord> FindSearchOtherRawRecords(String userDataCostId, int currentPage, int totalRecord,
			String workShopNo, String startDate, String endDate, String recordStatus, Boolean isShowAll, String accessRole) {
		// TODO Auto-generated method stub
		List<IOWSRecord> searchRawRecord = null;
		String sSQL = "select * from (select a.*,rownum as rnum,COUNT (*) OVER () totalPage from "
				+ "(SELECT r.Emp_id,r.D_Cardid,r.WorkShopNo,'' AS NAME,'' as depid'' as costId,to_char(r.SwipeCardTime,'yyyy-MM-dd HH24:mi:ss') swipeCardTime,r.Direction"
				+ " FROM SWIPE.RT_ACCESS_CONTROL_RECORD r  where 1=1";
		try {	
			 List <Object> queryList=new  ArrayList<Object>();  

			if(!workShopNo.equals("")){
  				sSQL+=" and r.WorkShopNo in(";  
  				  String [] workShopNoArray = workShopNo.split(",");
  		            for(int i=0;i<workShopNoArray.length;i++){
  		            	sSQL+="'"+workShopNoArray[i].trim()+"'";
  		                if(workShopNoArray.length-1!=i)
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
			
			if(accessRole!=null&&!accessRole.equals("")){
				if(!accessRole.equals("ALL")){
					sSQL+=" and bu = '"+accessRole+"' "; 
				}
			}
			
			if(!isShowAll){
				Page page = new Page(currentPage, totalRecord);	  
				int endIndex=page.getStartIndex() + page.getPageSize();
				sSQL += " order by r.SwipeCardTime ) a ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
			}
			else
			{
				sSQL += " order by r.SwipeCardTime ) a ) where 1=1";
			}
		    
		    searchRawRecord = jdbcTemplate.query(sSQL,  queryList.toArray(), new QueryIOWSRecord());			    
		    System.out.println(sSQL);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search IOWSRecord Record is failed",ex);
		}
		return searchRawRecord;
	}
}
