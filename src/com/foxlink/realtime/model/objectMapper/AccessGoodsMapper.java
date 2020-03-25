package com.foxlink.realtime.model.objectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.foxlink.realtime.model.AccessGoods;

public class AccessGoodsMapper implements RowMapper<AccessGoods> {

	@Override
	public AccessGoods mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		AccessGoods accessGoods = new AccessGoods();
		accessGoods.setUserId(rs.getString("id"));
		accessGoods.setWorkShopNo(rs.getString("WorkShopNo"));
		accessGoods.setUdisk(rs.getString("Udisk"));
		accessGoods.setComputer(rs.getString("Computer"));
		accessGoods.setMobilePhone(rs.getString("MobilePhone"));
		accessGoods.setStart_date(rs.getString("start_date"));
		accessGoods.setEnd_date(rs.getString("end_date"));
		accessGoods.setCardId(rs.getString("CardId"));
		return accessGoods;
	}

}
