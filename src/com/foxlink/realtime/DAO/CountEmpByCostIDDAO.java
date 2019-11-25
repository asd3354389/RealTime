package com.foxlink.realtime.DAO;

import java.util.List;

import org.apache.log4j.Logger;

import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.Prod;
import com.foxlink.realtime.model.objectMapper.QueryCountEmp;
import com.foxlink.realtime.model.objectMapper.QueryManPowerPord;
import com.foxlink.realtime.model.objectMapper.QueryProd;
import com.foxlink.realtime.model.objectMapper.QueryProdDepid;
import com.foxlink.realtime.model.objectMapper.QueryProdDepidDetail;


public class CountEmpByCostIDDAO extends DAO<ManPowerStatus> {
	private static Logger logger=Logger.getLogger(CountEmpByCostIDDAO.class);
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

	public List<ManPowerStatus> searchCountEmpList(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> CEmpList = null;
		String sSQl = "select costid,depid,class_no,id,name,status from Swipe.manpower_status where depid like '%XR%' and  to_char(workdate,'yyyy-mm-dd')= ? ";
				if(!costid.equals("")){
					sSQl+=" and costId in(";  
	 				  String [] costIdArray = costid.split("\\*");
	 		            for(int i=0;i<costIdArray.length;i++){
	 		            	sSQl+="'"+costIdArray[i].trim()+"'";
	 		                if(costIdArray.length-1!=i)
	 		                	sSQl+=",";
	 		                else
	 		                	sSQl+=") ";				                
	 		               }
	 			}
				sSQl+="order by costid,depid,class_no DESC";
		try {
			CEmpList = jdbcTemplate.query(sSQl, new Object[] {sDate},new QueryCountEmp());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find CountEmpList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return CEmpList;
	}
	
	public List<ManPowerStatus> searchReasonEmpList(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> REmpList = null;
		String sSQl = "select costid,shift,status,sum(number_of_people) as count from Swipe.manpower_pro where to_char(plan_date,'yyyy-mm-dd')=? group by costid,shift,status";

		try {
			REmpList = jdbcTemplate.query(sSQl, new Object[] {sDate},new QueryManPowerPord());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ReasonEmpList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return REmpList;
	}

	public List<ManPowerStatus> searchOffLineList(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> OLineist = null;
		String sSQl = "SELECT costid, shift, status, SUM(number_of_people) AS count	FROM Swipe.manpower_pro	WHERE to_char(plan_date,'yyyy-mm-dd')=?	AND (prod_name like '%線外%' or prod_name like '%樣品%')	GROUP BY costid, shift, status";

		try {
			OLineist = jdbcTemplate.query(sSQl, new Object[] {sDate},new QueryManPowerPord());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find OffLineList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return OLineist;
	}

	public List<ManPowerStatus> searchCountEmpBCList(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<ManPowerStatus> CEmpBCList = null;
		String sSQl = "select costid,depid,class_no,id,name,status from Swipe.manpower_status where costid = ? and  depid like '%XR%' and to_char(workdate,'yyyy-mm-dd')= ? order by depid,class_no  DESC";
				
		try {
			CEmpBCList = jdbcTemplate.query(sSQl, new Object[] {costid,sDate},new QueryCountEmp());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find CountEmpBCList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return CEmpBCList;
	}

	public List<String> FindDepidRecords(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<String> getDepids=null;
		String sSQL="select distinct(depid) from swipe.MANPOWER_STATUS where to_char(WorkDate,'yyyy-mm-dd')='"+sDate+"' and depid like '%XR%' and costid='"+costid+"'order by depid";
		try { 
							
				getDepids=jdbcTemplate.queryForList(sSQL,String.class);
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			logger.error("Search DepidRecords Record is failed",ex);
		}
		return getDepids;
	}

	public List<Prod> searchpEmpList(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<Prod> PEmpList = null;
		String sSQl = "select depid,shift,sum(number_of_people) as number_of_people from swipe.manpower_pro where to_char(plan_date,'yyyy-mm-dd')=? AND costid =? group by depid,shift ORDER by DEPID,shift desc";
				
		try {
			PEmpList = jdbcTemplate.query(sSQl, new Object[] {sDate,costid},new QueryProdDepid());
			//System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find pEmpList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return PEmpList;
	}

	public List<Prod> searchABEmpList(String costid, String sDate) {
		// TODO Auto-generated method stub
		List<Prod> ABEmpList = null;
		String sSQl = "select recordid,costid,depid,shift,prod_name,prod_code,depid_go,reason,number_of_people,plan_date from swipe.manpower_pro where status = 1 and to_char(plan_date,'yyyy-mm-dd')= ? ";
				if(costid.indexOf("*")!=-1){
					sSQl+=" and costId in(";  
	 				  String [] costIdArray = costid.split("\\*");
	 		            for(int i=0;i<costIdArray.length;i++){
	 		            	sSQl+="'"+costIdArray[i].trim()+"'";
	 		                if(costIdArray.length-1!=i)
	 		                	sSQl+=",";
	 		                else
	 		                	sSQl+=") ";				                
	 		               }
	 			}else {
	 				sSQl+=" and costId='"+costid+"'" ; 
	 			}
				
		try {
			ABEmpList = jdbcTemplate.query(sSQl,new Object[] {sDate},new QueryProd());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find ABEmpList TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return ABEmpList;
	}

}
