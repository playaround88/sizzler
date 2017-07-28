package com.ai.sizzler.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IQrtzDao;
import com.ai.sizzler.service.IQrtzService;

@Service("qrtzService")
public class QrtzService implements IQrtzService{
	private static Logger LOG = LoggerFactory.getLogger(QrtzService.class);
	private IQrtzDao dao;
	
	@Autowired
	public void setDao(IQrtzDao dao) {
		this.dao = dao;
	}

	@Override
	public PagedList<Map> queryTaskList(Map params) {
		return dao.queryTaskList(params);
	}

}
