package com.foxlink.realtime.DAO;

import java.util.ArrayList;
import java.util.List;

import com.foxlink.realtime.model.GetDepid;
import com.foxlink.realtime.model.objectMapper.GetDepidMapper;

public class GetDepidDao extends DAO<GetDepid>{

	@Override
	public boolean AddNewRecord(GetDepid newRecord) {
		return false;
	}

	@Override
	public boolean UpdateRecord(GetDepid updateRecord) {
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		return false;
	}

	@Override
	public List<GetDepid> FindAllRecords() {
		return null;
	}

	@Override
	public List<GetDepid> FindAllRecords(int currentPage, int totalRecord, String queryCritirea, String queryParam) {
		return null;
	}

	
	public List<GetDepid> FindRecord(GetDepid getDepid) {
		String sql="select distinct(depid) from swipe.csr_employee where costid= ? order by depid";
		List<GetDepid> getDepids=null;
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(getDepid.getCostid());
		getDepids=jdbcTemplate.query(sql, queryList.toArray(),new GetDepidMapper());
		return getDepids;
	}

	@Override
	public int getTotalRecord(String queryCritirea, String queryParam) {
		return 0;
	}

	


	@Override
	public List<GetDepid> FindRecords(GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GetDepid> FindRecord(String userDataCostId, int currentPage, int totalRecord, GetDepid t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecords(String userDataCostId, GetDepid t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
