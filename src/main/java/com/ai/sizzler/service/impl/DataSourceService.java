package com.ai.sizzler.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IDataSourceDao;
import com.ai.sizzler.domain.DataSourceForm;
import com.ai.sizzler.service.IDataSourceService;

@Service("dataSourceService")
public class DataSourceService implements IDataSourceService{
	private static Logger LOG=LoggerFactory.getLogger(DataSourceService.class);
	private IDataSourceDao dao;
	
	@Autowired
	public void setDao(IDataSourceDao dao) {
		this.dao = dao;
	}

	@Override
	public int insert(DataSourceForm ds) {
		return dao.insert(ds);
	}

	@Override
	public int del(long id) {
		return dao.del(id);
	}

	@Override
	public int update(DataSourceForm ds) {
		return dao.update(ds);
	}

	@Override
	public Map selectById(long id) {
		return dao.selectById(id);
	}

	@Override
	public PagedList<Map> selectPagedList(HashMap params) {
		return dao.selectPagedList(params);
	}

	@Override
	public List<Map> selectList(HashMap params) {
		return dao.selectList(params);
	}

}
