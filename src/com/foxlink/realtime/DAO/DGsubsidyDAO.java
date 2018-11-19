package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.DGsubsidy;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.DGsubsidyMapper;

public class DGsubsidyDAO  extends DAO<DGsubsidy> {
private static Logger logger=Logger.getLogger(DGsubsidyDAO.class);	
	
	public DGsubsidyDAO(){
		super();
	}

	@Override
	public boolean AddNewRecord(DGsubsidy newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(DGsubsidy updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DGsubsidy> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<DGsubsidy> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getTotalRecord(String userDataCostId,String empId,String depId,String costId,String startDate,String endDate) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) "
				+ "FROM SWIPE.csr_swipecardtime a join csr_employee b on a.emp_id=b.ID "
				+ "join SWIPE.emp_class c on b.ID=c.ID "
				+ "join SWIPE.classno d on c.class_no=d.class_no "
				+ "where "
				+ "to_char(c.emp_date,'yyyy-MM-dd') =to_char(a.SwipeCardTime,'yyyy-MM-dd') "
				+ "and a.SwipeCardTime is not null and a.SwipeCardTime2 is not null "
				+ "and b.isOnWork=0";
    	try {     		
    		 List <Object> queryList=new  ArrayList<Object>();  
    		 if(!empId.equals("")){
 				sSQL+=" and a.emp_id in(";  
 				  String [] empIdArray = empId.split(",");
 		            for(int i=0;i<empIdArray.length;i++){
 		            	sSQL+="'"+empIdArray[i]+"'";
 		                if(empIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			
 			}
 			if(!depId.equals("")){
 				sSQL+=" and b.depId in(";  
 				  String [] depIdArray = depId.split(",");
 		            for(int i=0;i<depIdArray.length;i++){
 		            	sSQL+="'"+depIdArray[i]+"'";
 		                if(depIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			}
 			if(!costId.equals("")){
 				sSQL+=" and b.costId in(";  
 				  String [] costIdArray = costId.split(",");
 		            for(int i=0;i<costIdArray.length;i++){
 		            	sSQL+="'"+costIdArray[i]+"'";
 		                if(costIdArray.length-1!=i)
 		                	sSQL+=",";
 		                else
 		                	sSQL+=") ";				                
 		               }
 			}
 			if(!startDate.equals("") && !endDate.equals("")){
 				sSQL+=" and a.swipe_date >= ? and a.swipe_date <= ?";  
 				queryList.add(startDate);
 				queryList.add(endDate);
 			}
 			
			if (userDataCostId != null && userDataCostId != "") {
				if(!userDataCostId.equals("ALL")){
					sSQL += " and b.costId in(";
					String[] userDataCostArray = userDataCostId.split("\\*");
					for (int i = 0; i < userDataCostArray.length; i++) {
						sSQL += "'" + userDataCostArray[i] + "'";
						if (userDataCostArray.length - 1 != i)
							sSQL += ",";
						else
							sSQL += ") ";
					}
				}
			}
			else{
				sSQL += " and b.costId in('')";
			}
 			
 		   totalRecord = jdbcTemplate.queryForObject(sSQL, queryList.toArray(), Integer.class);
 		
    	  } catch (Exception ex) {
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}	
	
	public List<DGsubsidy> FindDGsubsidys(String userDataCostId,int currentPage,int totalRecord,String empId,String depId,String costId,String startDate,String endDate,Boolean isShowAll) {
		// TODO Auto-generated method stub
		List<DGsubsidy> searchDGsubsidy = null;
		String sSQL = "select * from (select e.*,rownum as rnum,COUNT (*) OVER () totalPage from "
				+ "(SELECT b.ID, b.Name, b.depid,b.costID,a.SwipeCardTime,a.SwipeCardTime2,d.class_start "
				+ "FROM SWIPE.csr_swipecardtime a join csr_employee b on a.emp_id=b.ID "
				+ "join SWIPE.emp_class c on b.ID=c.ID "
				+ "join SWIPE.classno d on c.class_no=d.class_no "
				+ "where "
				+ "to_char(c.emp_date,'yyyy-MM-dd') =to_char(a.SwipeCardTime,'yyyy-MM-dd') "
				+ "and a.SwipeCardTime is not null and a.SwipeCardTime2 is not null "
				+ "and b.isOnWork=0";
		try {	
			 List <Object> queryList=new  ArrayList<Object>();  
			if(!empId.equals("")){
				sSQL+=" and a.emp_id in(";  
				  String [] empIdArray = empId.split(",");
		            for(int i=0;i<empIdArray.length;i++){
		            	sSQL+="'"+empIdArray[i]+"'";
		                if(empIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			
			}
			if(!depId.equals("")){
				sSQL+=" and b.depId in(";  
				  String [] depIdArray = depId.split(",");
		            for(int i=0;i<depIdArray.length;i++){
		            	sSQL+="'"+depIdArray[i]+"'";
		                if(depIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			}
			if(!costId.equals("")){
				sSQL+=" and b.costId in(";  
				  String [] costIdArray = costId.split(",");
		            for(int i=0;i<costIdArray.length;i++){
		            	sSQL+="'"+costIdArray[i]+"'";
		                if(costIdArray.length-1!=i)
		                	sSQL+=",";
		                else
		                	sSQL+=") ";				                
		               }
			}
			if(!startDate.equals("") && !endDate.equals("")){
				sSQL+=" and a.swipe_date >= ? and a.swipe_date <= ?";  
				queryList.add(startDate);
				queryList.add(endDate);
			}
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and b.costId in(";
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
				sSQL += " and b.costId in('')";
			}
			
			if(!isShowAll){
				Page page = new Page(currentPage, totalRecord);	  
				int endIndex=page.getStartIndex() + page.getPageSize();
				sSQL += " order by b.depid,b.costID,a.emp_id,a.swipecardtime ) e ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
			}
		    else
			{
				sSQL += " order by b.depid,b.costID,a.emp_id,a.swipecardtime ) e ) where 1=1";
			}    
		    searchDGsubsidy = jdbcTemplate.query(sSQL,  queryList.toArray(), new DGsubsidyMapper());			    
		
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search DGsubsidys Record is failed",ex);
		}
		return searchDGsubsidy;
	}

	

	@Override
	public List<DGsubsidy> FindRecords(DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGsubsidy> FindRecord(String userDataCostId, int currentPage, int totalRecord, DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, DGsubsidy t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
