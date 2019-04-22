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
import com.foxlink.realtime.model.IpBinding;








public class IpBindingDAO extends DAO<IpBinding>{
	
	private static Logger logger = Logger.getLogger(IpBindingDAO.class);
	//Ip地址
	private String DEVICEIP;
	//是否有ip
	boolean isIp;
	//部門Id
	private String Dept_Id;
	//工號
	private String User_Id;
	//存放異常list
	List<String> reList;
	
	
	
	
	//insert數據
	public boolean insertIPBinding(String DeviceIp,String DeptId,String ID) {
		Dept_Id = DeptId;
		User_Id = ID;
		//返回的ip地址
		String Ipreturn = SelectAppIp(DeviceIp);
		
		//返回的費用代碼
		String CostIdReturn = SelectDeptId();
		//device表返回的ip信息		
	    String DeviceIpRe = SelectDeviceIp(DeviceIp);
	    //返回的助理Id
	    String IdReturn = SelectId(ID);
	    System.out.println("返回的工號地址==============================================================="+IdReturn);
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
	 		reList.add("ip地址不存在,請重新輸入");
			System.out.println("ip地址不存在,請重新輸入");
		}else if (CostIdReturn == "") {
			reList.add("部門代碼不存在,請重新輸入!!");
			createRow=-1;
			System.out.println("部門代碼不存在,請重新輸入");
		}else if (IdReturn == "") {
			createRow=-1;
			reList.add("助理不存在,請重新輸入!!");
			System.out.println("助理不存在,請重新輸入");
		}else if (DeviceIpRe != "") {
			createRow=-1;
			reList.add("資料庫中已有此Ip信息,請重新輸入");
			System.out.println("資料庫中已有此Ip信息,請重新輸入!!");
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
	public String SelectDeptId() {
		// TODO Auto-generated method stub

		String cost_id = "";
		String DeptStr = "SELECT DEPID,COSTID FROM DEPT_RELATION WHERE COSTID ='"+Dept_Id+"'";
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
					 cost_id = ipBinding.getCOSTID();
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
		public String SelectDeviceIp(String DeviceIp) {
			//String com_ip = SelectAppIp(DeviceIp);
			String DeviceIpStr = "SELECT DISTINCT DEVICEIP,DEPTID,UPDATE_USERID,ENABLED   from SWIPE.DEVICE_DEPT_BINDING WHERE DEVICEIP = '"+DeviceIp+"'";
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
					 System.out.println("Ip地址==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+ipBinding.getDEVICEIP());
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
			String DeptStr = "SELECT USERNAME FROM USER_DATA WHERE USERNAME ='"+ID+"'";
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
						 System.out.println("查詢的費用代碼==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+ipBinding.getUSERNAME());
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
			
			String strSQL = "SELECT DISTINCT DEVICEIP,DEPTID,UPDATE_USERID,ENABLED FROM SWIPE.DEVICE_DEPT_BINDING";
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
		public List<String> ListRe() {
			// TODO Auto-generated method stub
			List<String> ListRe = reList;

			
			return ListRe;
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


	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getTotalRecords(String userDataCostId, IpBinding t) {
		// TODO Auto-generated method stub
		return 0;
	}
}
