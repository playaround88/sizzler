package com.ai.sizzler.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IImporterDao;
import com.ai.sizzler.domain.ImporterForm;
import com.ai.sizzler.service.IImporterService;

public class ImporterService implements IImporterService{
	private static Logger LOG=LoggerFactory.getLogger(ImporterService.class);
	private IImporterDao dao;
	
	@Autowired
	public void setDao(IImporterDao dao) {
		this.dao = dao;
	}

	@Override
	public int insert(ImporterForm imp) {
		return dao.insert(imp);
	}

	@Override
	public int del(long id) {
		return dao.del(id);
	}

	@Override
	public int update(ImporterForm imp) {
		return dao.update(imp);
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
