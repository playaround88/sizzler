package com.ai.sizzler.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IScanTaskDao;
import com.ai.sizzler.scan.Task;
import com.ai.sizzler.service.IScanTaskService;

@Service("scanTaskService")
public class ScanTaskService implements IScanTaskService{
	private static Logger LOG=LoggerFactory.getLogger(ScanTaskService.class);
	private IScanTaskDao dao;
	
	@Autowired
	public void setDao(IScanTaskDao dao) {
		this.dao = dao;
	}

	@Override
	public int insert(Task task) {
		return dao.insert(task);
	}

	@Override
	public int del(long id) {
		return dao.del(id);
	}

	@Override
	public int update(Task task) {
		return dao.update(task);
	}

	@Override
	public Task selectById(long id) {
		return dao.selectById(id);
	}

	@Override
	public PagedList<Map> selectPagedList(HashMap params) {
		return dao.selectPagedList(params);
	}
}
