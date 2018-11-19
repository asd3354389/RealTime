package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.JobInfo;


public class JobInfoMapper implements RowMapper<JobInfo>{

	@Override
	public JobInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJob_Code(rs.getString("Job_Code"));
		jobInfo.setJob_Desc(rs.getString("Job_Desc"));
		return jobInfo;
	}

}
