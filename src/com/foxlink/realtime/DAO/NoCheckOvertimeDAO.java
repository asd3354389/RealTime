package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.DGsubsidy;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.NoCheckeOvertimeMapper;

public class NoCheckOvertimeDAO extends DAO<DGsubsidy>{

	private static Logger logger=Logger.getLogger(NoCheckOvertimeDAO.class);

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
	public List<DGsubsidy> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGsubsidy> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGsubsidy> FindRecord(String userDataCostId, int currentPage, int totalRecord, DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGsubsidy> FindRecords(DGsubsidy t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRecords(String userDataCostId, DGsubsidy t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTotalRecord(String userDataCostId,String empId,String depId,String costId,String startDate,String endDate) {
		// TODO Auto-generated method stub
		int totalRecord=-1;
    	String sSQL = "select count(*) "
				+ "from csr_swipecardtime a,csr_employee b where a.emp_id = b.id and b.isonwork = '0' ";
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
 				sSQL+=" and b.deptId in(";  
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
			
			sSQL += " and (a.emp_id,a.swipe_date)  not in (select t.id,t.overtimedate from notes_overtime_state t,csr_employee e where t.id = e.id and e.isonwork = '0'";
			
			if(!empId.equals("")){
				sSQL+=" and e.id in(";  
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
				sSQL+=" and e.deptid in(";  
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
				sSQL+=" and e.costid in(";  
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
				sSQL+=" and t.overtimedate >= ? and t.overtimedate <= ?";  
				queryList.add(startDate);
				queryList.add(endDate);
			}
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and e.costid in(";
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
				sSQL += " and e.costid in('')";
			}
			
			sSQL += ")";
 			
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
				+ "(select b.id,b.name,b.deptid,b.costid,a.swipe_date,nvl(a.swipecardtime,a.swipecardtime2) swipecardtime,nvl(a.swipecardtime2,a.swipecardtime) swipecardtime2 from csr_swipecardtime a,csr_employee b where a.emp_id = b.id and b.isonwork = '0' ";
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
				sSQL+=" and b.deptId in(";  
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
			
			sSQL += " and (a.emp_id,a.swipe_date)  not in (select t.id,t.overtimedate from notes_overtime_state t,csr_employee e where t.id = e.id and e.isonwork = '0' ";
			
			if(!empId.equals("")){
				sSQL+=" and e.id in(";  
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
				sSQL+=" and e.deptid in(";  
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
				sSQL+=" and e.costid in(";  
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
				sSQL+=" and t.overtimedate >= ? and t.overtimedate <= ?";  
				queryList.add(startDate);
				queryList.add(endDate);
			}
			
			if (userDataCostId != null && userDataCostId != "") {
				if (!userDataCostId.equals("ALL")) {
					sSQL += " and e.costid in(";
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
				sSQL += " and e.costid in('')";
			}
			
			sSQL += ")";
			
			if(!isShowAll){
				Page page = new Page(currentPage, totalRecord);	  
				int endIndex=page.getStartIndex() + page.getPageSize();
				sSQL += " order by a.swipe_date,b.costID,b.deptid,a.emp_id,a.swipecardtime ) e ) where rnum > "+page.getStartIndex()+" and rnum <= "+ endIndex ;
			}
		    else
			{
				sSQL += " order by a.swipe_date,b.costID,b.deptid,a.emp_id,a.swipecardtime ) e ) where 1=1";
			}    
		    searchDGsubsidy = jdbcTemplate.query(sSQL,  queryList.toArray(), new NoCheckeOvertimeMapper());			    
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Search DGsubsidys Record is failed",ex);
		}
		return searchDGsubsidy;
	}
}
