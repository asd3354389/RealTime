package com.foxlink.realtime.service;

import org.apache.log4j.Logger;

import com.foxlink.realtime.DAO.AbReasonReplyDAO;
import com.foxlink.realtime.DAO.CountEmpDAO;

public class AbReasonReplyService {
	private static Logger logger = Logger.getLogger(AbReasonReplyService.class);
	private AbReasonReplyDAO abReasonReplyDAO;
	
	public void setAbReasonReplyDAODAO(AbReasonReplyDAO abReasonReplyDAO) {
		this.abReasonReplyDAO = abReasonReplyDAO;
	}
}
