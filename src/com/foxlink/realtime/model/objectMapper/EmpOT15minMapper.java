package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.EmpOT15min;

public class EmpOT15minMapper implements RowMapper<EmpOT15min>{
	/*ce.id 工號,
    ce.Name 姓名,
    ce.costid 費用代碼,
    ce.depid 部門代碼,
    ce.depname 部門名稱,
    cs.class_no 班別,
    cs.swipecardtime 上刷,
    cs.swipecardtime2 下刷,
    c.class_start 標準上班起時,*/
	@Override
	public EmpOT15min mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		EmpOT15min ot15min = new EmpOT15min();
		SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ot15min.setId(rs.getString("ID"));
		ot15min.setName(rs.getString("Name"));
		ot15min.setCostid(rs.getString("costid"));
		ot15min.setDepid(rs.getString("depid"));
		ot15min.setDepname(rs.getString("depname"));
		ot15min.setClass_no(rs.getString("class_no"));
		ot15min.setSwipecardtimeg(rs.getString("swipecardtimeg"));
		ot15min.setSwipecardtimeo(rs.getString("swipecardtimeo"));
		ot15min.setClass_start(rs.getString("class_start"));
		ot15min.setGoWorkAdvance(rs.getInt("goWorkAdvance"));
		ot15min.setOvertime_start(rs.getString("overtime_start"));
		ot15min.setOutWorkOvertime(rs.getInt("outWorkOvertime"));
		return ot15min;
	}

}
