package com.foxlink.realtime.DAO;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.foxlink.realtime.model.ClassNO;
import com.foxlink.realtime.model.objectMapper.ClassInfoMapper;

import oracle.jdbc.OracleTypes;

public class ClassNoDAO extends DAO<ClassNO> {
	private static Logger logger=Logger.getLogger(ClassNoDAO.class);
	
	public ClassNoDAO() {
		super();
	}

	@Override
	public List<ClassNO> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ClassNO> FindAllRecords(String CurrentShift){
		SimpleJdbcCall call=null;
		Map<String, Object> executeResult=null;
		List<ClassNO> ClassNOInfos=null;
		try {
			call=new SimpleJdbcCall(jdbcTemplate).
					withProcedureName("SHOW_CLASS_INFO").
					declareParameters(new SqlParameter("VCURRENT_SHIFT",Types.VARCHAR),
							new SqlOutParameter("CURRENT_CLASS_INFO",OracleTypes.CURSOR,new ClassInfoMapper()));
			executeResult=call.execute(new MapSqlParameterSource("VCURRENT_SHIFT",CurrentShift));
			ClassNOInfos=(List<ClassNO>) executeResult.get("CURRENT_CLASS_INFO");
		}
		catch(Exception ex) {
			logger.error("FindAllRecords(SHOW_CLASS_INFO) is failed");
		}
		return ClassNOInfos;
	}

	@Override
	public boolean AddNewRecord(ClassNO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ClassNO updateRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ClassNO> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}


	


	
	@Override
	public int getTotalRecords(String userDataCostId, ClassNO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ClassNO> FindRecord(String userDataCostId, int currentPage, int totalRecord, ClassNO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassNO> FindRecords(ClassNO t) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
