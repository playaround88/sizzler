package com.ai.sizzler.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IExporterDao;
import com.ai.sizzler.domain.ExporterForm;
import com.ai.sizzler.service.IExporterService;

public class ExporterService implements IExporterService{
	private static Logger LOG=LoggerFactory.getLogger(ExporterService.class);
	private IExporterDao dao;
	
	@Autowired
	public void setDao(IExporterDao dao) {
		this.dao = dao;
	}

	@Override
	public int insert(ExporterForm exp) {
		return dao.insert(exp);
	}

	@Override
	public int del(long id) {
		return dao.del(id);
	}

	@Override
	public int update(ExporterForm exp) {
		return dao.update(exp);
	}

	@Override
	public Map selectById(long id) {
		return dao.selectById(id);
	}

	@Override
	public PagedList<Map> selectPagedList(HashMap params) {
		return dao.selectPagedList(params);
	}
}
