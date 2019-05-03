package com.foxlink.realtime.DAO;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor.TURQUOISE;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.foxlink.realtime.model.Emp;
import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.IOCardMachineIP;
import com.foxlink.realtime.model.IpBinding;
import com.foxlink.realtime.model.Page;
import com.foxlink.realtime.model.objectMapper.GetDepidMapper;
import com.foxlink.realtime.model.objectMapper.QueryIOCardMaIPMapper;
import com.foxlink.realtime.model.objectMapper.QueryIpBindingMapper;
import com.google.gson.JsonObject;








public class IpBindingDAO extends DAO<IpBinding>{
	
	private static Logger logger = Logger.getLogger(IpBindingDAO.class);
	//Ip地址
	private String DEVICEIP;
	//是否有ip
	boolean isIp;
	//部門Id
	private String Dept_Id;
	private String Dept_IdRe;
	//工號
	private String User_Id;
	//存放異常list
	List<String> reList;
	//有查詢全的costid
	private StringBuffer userData_CostId;
	JsonObject UpdateResult;
	private String Ipreturn;
	
	
	
	//insert數據
	public boolean insertIPBinding(String DeviceIp,String DeptId,String ID) {
		Dept_Id = DeptId;
		User_Id = ID;
		
	    UpdateResult=new JsonObject();	
	    
	  //處理輸入的IP地址
	  		String[] Destring = DeviceIp.split("\\."); 
	  		String one = Destring[0] + "." +Destring[1] + "." +Destring[2];
		//返回的ip地址  
	  		if (one.equals("10.64.82")  ||one.equals("10.64.83")  ||one.equals("10.64.117") ||one.equals("10.64.119")) {
				//String Ipreturn = SelectAppIp(DeviceIp);
	  			Ipreturn = one;
	  			 System.out.println("返回的IP地址截取的數組==============================================================="+Ipreturn);
			}else {
				
				 Ipreturn = SelectAppIp(DeviceIp);
				 System.out.println("返回的IP地址截取的數組ELSE==============================================================="+Ipreturn);
			}
		
		
		//返回的費用代碼
		String CostIdReturn = SelectDeptId(DeptId);
		
		//device表返回的ip信息		
		String DeviceIpRe = SelectDeviceIp(DeviceIp,DeptId);
	
	    //返回的助理Id
	    String IdReturn = SelectId(ID);
	    System.out.println("返回的IP地址==============================================================="+DeviceIp);
	    //System.out.println("返回的IP地址截取的數組==============================================================="+one);
		reList = new ArrayList<>();
		int createRow=-1;
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
	 	//String strSQL = "INSERT INTO SWIPE.DEVICE_DEPT_BINDING (DEVICEIP,DEPTID,UPDATE_USERID) VALUES ('"+DeviceIp+"','"+DeptId+"','"+ID+"')";
		String strSQL = "INSERT INTO SWIPE.DEVICE_DEPT_BINDING (DEVICEIP,DEPTID,UPDATE_USERID) VALUES (?,?,?)";
	    System.out.println("createRow====>>"+createRow);
	 	System.out.println("調用insert方法"+strSQL);
	 	//if (isIp == false) {
	 	if (Ipreturn == "") {
	 		createRow=-1;
	 		UpdateResult.addProperty("Message", "ip地址不存在,請重新輸入");
	 		reList.add("ip地址不存在,請重新輸入");
			System.out.println("ip地址不存在,請重新輸入");
		}else if (CostIdReturn == "") {
			UpdateResult.addProperty("Message", "部門代碼不存在,請重新輸入");
			reList.add("部門代碼不存在,請重新輸入!!");
			createRow=-1;
			System.out.println("部門代碼不存在,請重新輸入");
		}else if (IdReturn == "") {
			createRow=-1;
			UpdateResult.addProperty("Message", "助理不存在,請重新輸入");
			reList.add("助理不存在,請重新輸入!!");
			System.out.println("助理不存在,請重新輸入");
		}else if (DeviceIpRe != "" ) {
			createRow=-1;
			UpdateResult.addProperty("Message", "部門代碼已經綁定過IP,請重新輸入");
			reList.add("資料庫中已有此Ip信息,請重新輸入");
			System.out.println("部門代碼已經綁定過IP,請重新輸入!!");
		}else {
			try {
				if(DeviceIp!=null && DeptId!=null && ID!=null && IdReturn!=null) {
			      createRow = jdbcTemplate.update(strSQL,new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement arg0) throws SQLException {
							// TODO Auto-generated method stub
							arg0.setString(1, DeviceIp);
							arg0.setString(2, DeptId);
							arg0.setString(3, ID);
							
						}	
					});
					transactionManager.commit(txStatus);
				}			
			}
			catch(Exception ex) {
				logger.error(ex);
				transactionManager.rollback(txStatus);
			}
		}
	
			if(createRow > 0) {
				   return true; 
			} else {
				 return false;
		}
		
	}
	//查詢是否是例外的
	public String SelectExceptIp(String DeviceIp) {
		String Except_Ip = "";
		//SELECT COM_IP,CONTROL_EXCEPT FROM APP_LOGIN_CONTROL WHERE COM_IP LIKE '10.64.119%'
		String AppStr = "SELECT COM_IP,DUTY_PERSON FROM APP_LOGIN_CONTROL WHERE COM_IP LIKE '"+DeviceIp+"' ";
		return Except_Ip;
	}
	//查询app_login_control表的ip地址
	public String SelectAppIp(String DeviceIp) {
		String com_ip = "";
		String AppStr = "SELECT COM_IP,DUTY_PERSON FROM APP_LOGIN_CONTROL WHERE COM_IP = '"+DeviceIp+"' ";
		System.out.println(AppStr);
		List<IpBinding> SpecList = new ArrayList<>();
		 try {
			 RowMapper<IpBinding> rowMapper=new BeanPropertyRowMapper<>(IpBinding.class);
			 SpecList = jdbcTemplate.query(AppStr, rowMapper);	
			 System.out.println("查询的app表中的ip地址==============================================================="+SpecList);
			 if (SpecList == null) {
				 com_ip = "";
			} else {
				for(int i = 0; i < SpecList.size();i++){
					 IpBinding ipBinding = SpecList.get(i); 
					 com_ip = ipBinding.getCOM_IP();
					 System.out.println("APP_LOGIN_CONTROL查詢的Ip地址==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+com_ip);

				 }
			}
			 
			 
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("SELECT_AppIp_Error", e);
		}
		return com_ip;
	}
	//查询是否有该部门
	public String SelectDeptId(String Dept_Id) {
		// TODO Auto-generated method stub

		String cost_id = "";
		String DeptStr = "SELECT DEPID,COSTID FROM DEPT_RELATION WHERE DEPID ='"+Dept_Id+"'";
		System.out.println(DeptStr);
		List<IpBinding> SpecList = new ArrayList<>();
		try {
			 RowMapper<IpBinding> rowMapper=new BeanPropertyRowMapper<>(IpBinding.class);
			 SpecList = jdbcTemplate.query(DeptStr, rowMapper);	
			 if (SpecList == null) {
				 cost_id = "";
			} else {
				 for(int i = 0; i < SpecList.size();i++){
					 IpBinding ipBinding = SpecList.get(i); 
					 cost_id = ipBinding.getDEPTID();
					 System.out.println("查詢的費用代碼==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+ipBinding.getCOSTID());
				 }
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("SELECT_DeptId_Error", e);
		}
		return cost_id;
	}

	//查詢 device_dept_binding表數據庫中的ip地址數據	
		public String SelectDeviceIp(String DeviceIp, String DeptId) {
			//String com_ip = SelectAppIp(DeviceIp);
			String DeviceIpStr = "SELECT DISTINCT DEVICEIP,DEPTID,UPDATE_USERID,ENABLED  from SWIPE.DEVICE_DEPT_BINDING WHERE ENABLED = 'Y' AND DEVICEIP = '"+DeviceIp+"' AND DEPTID = '"+DeptId+"'";
			//String DeviceIpStr = "SELECT DISTINCT d.DEVICEIP,d.DEPTID,d.UPDATE_USERID,d.ENABLED FROM SWIPE.DEVICE_DEPT_BINDING d,SWIPE.APP_LOGIN_CONTROL a WHERE '"+com_ip+"' = '"+DeviceIp+"'";
			List<IpBinding> SpecList = new ArrayList<>();
			
			String Device_ip = "";
			
			System.out.println(DeviceIpStr);
			 try {
				 RowMapper<IpBinding> rowMapper=new BeanPropertyRowMapper<>(IpBinding.class);
				 SpecList = jdbcTemplate.query(DeviceIpStr, rowMapper);	
				 for(int i = 0; i < SpecList.size();i++){
					 IpBinding ipBinding = SpecList.get(i); 
					 Device_ip = ipBinding.getDEVICEIP();
					 Dept_IdRe = ipBinding.getDEPTID();
					 System.out.println("Ip地址==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+Device_ip);
					 System.out.println("输入的Ip地址==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+DeviceIp);

				 }
				 
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("SELECT_Email_Error", e);
			}
				return Device_ip;
			}
		
		//查詢是否有該助理信息
		public String SelectId(String ID) {
			String user_id = "";
			String cost_id = "";
			String DeptStr = "SELECT USERNAME,COSTID FROM USER_DATA WHERE USERNAME  ='"+ID+"'";
			System.out.println(DeptStr);
			List<IpBinding> SpecList = new ArrayList<>();
			try {
				 RowMapper<IpBinding> rowMapper=new BeanPropertyRowMapper<>(IpBinding.class);
				 SpecList = jdbcTemplate.query(DeptStr, rowMapper);	
				 if (SpecList == null) {
					 user_id = "";
				} else {
					
					 for(int i = 0; i < SpecList.size();i++){
						 IpBinding ipBinding = SpecList.get(i); 
						 user_id = ipBinding.getUSERNAME();
						
						
					 }
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("SELECT_ID_Error", e);
			}
			return user_id;
			
		}
		//顯示數據庫的數據
		
		public List<IpBinding> ShowAllIpList() {
			
			String strSQL = "SELECT DISTINCT DEVICEIP,DEPTID,UPDATE_USERID,ENABLED FROM SWIPE.DEVICE_DEPT_BINDING WHERE ENABLED = 'Y' ORDER BY DEVICEIP";
			List<IpBinding> SpecList = new ArrayList<>();
			try {
				RowMapper<IpBinding> mapper = new BeanPropertyRowMapper<>(IpBinding.class); 
				SpecList = jdbcTemplate.query(strSQL, mapper);
			} catch (Exception e) { 
				// TODO: handle exception
				e.printStackTrace();
				logger.error("SpecList查找所有CTP規格書時發生錯誤："+e);
			}

			return SpecList;
		}
		
		//返回異常信息
		public JsonObject ListRe() {
			// TODO Auto-generated method stub
			//List<String> ListRe = reList;
			JsonObject Update_Result = UpdateResult;
			
			return Update_Result;
		}
	@Override
	public boolean AddNewRecord(IpBinding newRecord) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean UpdateRecord(IpBinding updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<IpBinding> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<IpBinding> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<IpBinding> FindRecord(String userDataCostId, int currentPage, int totalRecord, IpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<IpBinding> FindRecords(IpBinding t) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTotalRecord(String queryCritirea, String queryParam,String userDataCostId) {
		// TODO Auto-generated method stub
		//只顯示自己部門的信息
				String strIdArray[] = userDataCostId.split("\\*");
				StringBuffer idsStr = new StringBuffer();
				Dept_Id="";
				for (int i = 0; i < strIdArray.length; i++) {
					if (i > 0) {
						idsStr.append(",");
					}
					idsStr.append("'").append(strIdArray[i]).append("'");
				}
		int totalRecord=-1;
    	String sSQL = "SELECT COUNT(*) FROM SWIPE.DEVICE_DEPT_BINDING WHERE ENABLED = 'Y' AND DEPTID  IN (SELECT DEPID FROM DEPT_RELATION  WHERE COSTID IN ("+idsStr+"))";
    	try {
    		List <Object> queryList=new  ArrayList<Object>();
    		
//    		if(queryCritirea.equals("UPDATE_USERID")){
//				sSQL+=" and UPDATE_USERID = '"+queryParam+"'";  
//			}
    		 if (queryCritirea.equals("DEPTID")) {
				sSQL+=" and DEPTID = '"+queryParam+"'";  
			}else if (queryCritirea.equals("DEVICEIP")) {
				sSQL+=" and DEVICEIP = '"+queryParam+"'"; 
			}
			else{
				sSQL+="";
			}
 
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
//totalRecord":-1,"pageSize":20,"totalPage":1,"startIndex":0,"currentPage":1,"list":null
//		  RowMapper<IpBinding> rowMapper=new BeanPropertyRowMapper<>(IpBinding.class);
//			 SpecList = jdbcTemplate.query(DeptStr, rowMapper);	
		 totalRecord = jdbcTemplate.queryForObject(sSQL,Integer.class);	
		
    	  } catch (Exception ex) {
    		  logger.error("Find Deviceip TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
    	return totalRecord;
	}
	
	public List<IpBinding> FindAllRecord(int currentPage, int totalRecord, String queryCritirea,
			String queryParam,String userDataCostId ) {
		// TODO Auto-generated method stub
		List<IpBinding> AllEmp = null;
		//只顯示自己部門的信息
		String strIdArray[] = userDataCostId.split("\\*");
		StringBuffer idsStr = new StringBuffer();
		Dept_Id="";
		for (int i = 0; i < strIdArray.length; i++) {
			if (i > 0) {
				idsStr.append(",");
			}
			idsStr.append("'").append(strIdArray[i]).append("'");
		}
		// TODO Auto-generated method stub
		String sSQL = "SELECT * FROM ((SELECT  DEVICEIP,DEPTID,UPDATE_USERID,ENABLED,ROWNUM RN  from SWIPE.DEVICE_DEPT_BINDING WHERE ENABLED = 'Y' AND DEPTID  IN (SELECT DEPID FROM DEPT_RELATION )";
		try {
			List <Object> queryList=new  ArrayList<Object>();
//			if(!userDataCostId.equals("ALL")){
//				String strIdArray[] = userDataCostId.split("\\*");
//				StringBuffer idsStr = new StringBuffer();
//				for (int i = 0; i < strIdArray.length; i++) {
//					if (i > 0) {
//						idsStr.append(",");
//					}
//					idsStr.append("'").append(strIdArray[i]).append("'");
//				}
//				sSQL+=" and Costid in("+idsStr+")";
//			}
//    		if(queryCritirea.equals("UPDATE_USERID")){
//				sSQL+=" and UPDATE_USERID = '"+queryParam+"'ORDER BY DEVICEIP";  
//			}
    		 if (queryCritirea.equals("DEPTID")) {
				sSQL+=" and DEPTID = '"+queryParam+"'ORDER BY DEVICEIP";
			}else if (queryCritirea.equals("DEVICEIP")) {
				sSQL+=" and DEVICEIP = '"+queryParam+"'ORDER BY DEVICEIP";
			}else{
				sSQL+="";
			}
    		Page page = new Page(currentPage, totalRecord);	  
			int endIndex=page.getStartIndex() + page.getPageSize();
			 sSQL += ")c) where rn>"+page.getStartIndex()+" and rn<="+endIndex+"" ;	     
		  if (!queryCritirea.equals("")){
		    	queryList.add(queryParam);
		    }
		  
		  AllEmp = jdbcTemplate.query(sSQL, new QueryIpBindingMapper());	
		  System.out.println(sSQL);
    	  } catch (Exception ex) {
    		  logger.error("Find IOCardMaIP TotalRecord are failed ",ex);
    		  ex.printStackTrace();
    		  }
		return AllEmp;
	}
	@Override
	public int getTotalRecords(String userDataCostId, IpBinding t) {
		// TODO Auto-generated method stub
		return 0;
	}
	//更新資料
	public boolean UpdateRecord(String DeviceIp,String DeptID, String updateUser,String OldDeptID,String userDataCostId) {
		// TODO Auto-generated method stub
		int updateRow=-1,updateRole=-1;
		String CostIdReturn = SelectDeptId(DeptID);
		//查詢輸入的部門代碼是否在該助理報加班的權限內
		List<IpBinding> SpecList = SelectDeptIdRe(userDataCostId);
		//數據庫是否有該部門的信息
		String IsDeviceIP = SelectDeviceIp(DeviceIp, DeptID);
//		System.out.println("舊的部門 代碼"+ OldDeptID);
//		System.out.println("新的部門 代碼"+ DeptID);
		  List<String> list=new ArrayList<String>();
		 for(int i = 0; i < SpecList.size();i++){
			 IpBinding ipBinding = SpecList.get(i); 
			 //Dept_Id = ipBinding.getDEPID();
			 list.add(ipBinding.getDEPID());
			 //System.out.println("查詢的 代碼"+ Dept_Id);
		 }
//		 if (SpecList == null) {
//		 Dept_Id = "";
//	} else {
//		
//		 for(int i = 0; i < SpecList.size();i++){
//			 IpBinding ipBinding = SpecList.get(i); 
//			 Dept_Id = ipBinding.getDEPID();
//			 System.out.println("查詢的 代碼"+ Dept_Id);
//			
//		 }
//	}
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);	
		//UPDATE SWIPE.DEVICE_DEPT_BINDING SET DEPTID = '8145',UPDATE_USERID = '129548' WHERE DEVICEIP = '127.0.0.1' AND ENABLED = 'Y'
		//String sSQL="UPDATE SWIPE.RT_DEVICE_INFO SET WorkShopNo=?,WorkShop_Desc=?,Direction=?,Update_Userid=? WHERE Deviceip=? and Enabled='Y'";
		String sSQL="UPDATE SWIPE.DEVICE_DEPT_BINDING SET DEPTID = ?,UPDATE_USERID = ? WHERE DEVICEIP = ? AND ENABLED = 'Y' AND DEPTID = ? ";
		 System.out.println("輸入的部門 代碼"+ IsDeviceIP);
		if (list.contains(DeptID)&& IsDeviceIP == "") {
//			for(int i = 0; i < SpecList.size();i++){
//				 IpBinding ipBinding = SpecList.get(i); 
//				 Dept_Id = ipBinding.getDEPID();
				 
			
				// if (DeptID.equals(ipBinding.getDEPID())) {
					 System.out.println("輸入的部門 代碼"+ DeptID);
					 System.out.println("查詢的 代碼"+ Dept_Id);
					 try {
							if(DeptID!=null) {
								updateRow=jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
									@Override
									public void setValues(PreparedStatement arg0) throws SQLException {
										// TODO Auto-generated method stub
										arg0.setString(1, DeptID);
										arg0.setString(2, updateUser);
										arg0.setString(3, DeviceIp);
										arg0.setString(4, OldDeptID);
									}	
								});
							
								transactionManager.commit(txStatus);
							}	
						}
						catch(Exception ex) {
							logger.error("Update BindingIP is failed",ex);
							transactionManager.rollback(txStatus);
						
				}
				
//				 }else {
//					 updateRow=-1;
//				 }
			//}		
		}else {
			updateRow=-1;
		}
		
		
			if(updateRow > 0) {
				return true; 
				}else{
				   return false;
				   }
	}
	
	public List<IpBinding> SelectDeptIdRe(String userDataCostId) {
		String strIdArray[] = userDataCostId.split("\\*");
		StringBuffer idsStr = new StringBuffer();
		Dept_Id="";
		for (int i = 0; i < strIdArray.length; i++) {
			if (i > 0) {
				idsStr.append(",");
			}
			idsStr.append("'").append(strIdArray[i]).append("'");
		}
		//sSQL+=" and Costid in("+idsStr+")";
		String sSQL= "SELECT DEPID FROM DEPT_RELATION";
		System.out.println(sSQL);
		List<IpBinding> SpecList = new ArrayList<>();
		try {
			 RowMapper<IpBinding> rowMapper=new BeanPropertyRowMapper<>(IpBinding.class);
			 SpecList = jdbcTemplate.query(sSQL, rowMapper);	
//			 if (SpecList == null) {
//				 Dept_Id = "";
//			} else {
//				
//				 for(int i = 0; i < SpecList.size();i++){
//					 IpBinding ipBinding = SpecList.get(i); 
//					 Dept_Id = ipBinding.getDEPID();
//					 
//					
//				 }
			//}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("SELECT_Dept_id_Error", e);
		}
		return SpecList;	
		}
	//刪除資料
	public boolean DeleteIpBinding(String Deviceip, String updateUser,String DeptId) {
		// TODO Auto-generated method stub
		txDef = new DefaultTransactionDefinition();
		txStatus = transactionManager.getTransaction(txDef);
		String sSQL= "UPDATE SWIPE.DEVICE_DEPT_BINDING  SET ENABLED = 'N' ,UPDATE_USERID = ? WHERE DEVICEIP = ? AND ENABLED = 'Y' AND DEPTID = ?";
		
		System.out.println("刪除信息=============>>"+sSQL);
		int disableRow=-1;
		try {
			if(Deviceip!=null) {
				disableRow = jdbcTemplate.update(sSQL,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement arg0) throws SQLException {
						// TODO Auto-generated method stub
						arg0.setString(1, updateUser);
						arg0.setString(2, Deviceip);
						arg0.setString(3, DeptId);
					}	
				});
				transactionManager.commit(txStatus);
			}
		}
		catch(Exception ex) {
			logger.error("Disable IOCardMaIP is failed",ex);
			transactionManager.rollback(txStatus);
		}
		 if(disableRow > 0) 
			   return true; 
		 else
			 return false;
	}
	
	//顯示部門代碼
	public List<GetDepid> ShowDeptNo(String CostId) {
		// TODO Auto-generated method stub
		List<GetDepid> AllDept = null;
		String sSQl = "select t.depid from DEPT_RELATION t order by t.depid asc";
		try {
			/*if(!CostId.equals("ALL")) {
				sSQl+= " where t.CostId = '"+CostId+"'";
			}*/
			AllDept = jdbcTemplate.query(sSQl,new GetDepidMapper());
			System.out.println(sSQl);
		} catch (Exception e) {
			// TODO: handle exception
			  logger.error("Find Dept TotalRecord are failed ",e);
    		  e.printStackTrace();
		}
		return AllDept;
	}
	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}
}
