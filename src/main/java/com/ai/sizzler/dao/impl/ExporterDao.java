package com.ai.sizzler.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.commons.pager.IQueryer;
import com.ai.commons.pager.MybatisQueryer;
import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IExporterDao;
import com.ai.sizzler.domain.ExporterForm;

@Repository("exporterDao")
public class ExporterDao implements IExporterDao{
	private static final String NM="com.ai.sizzler.scan.exporter.";
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int insert(ExporterForm exp) {
		return sqlSession.insert(NM+"insert", exp);
	}

	@Override
	public int del(long id) {
		return sqlSession.delete(NM+"deleteById", id);
	}

	@Override
	public int update(ExporterForm exp) {
		return sqlSession.update(NM+"update", exp);
	}

	@Override
	public Map selectById(long id) {
		return sqlSession.selectOne(NM+"selectById", id);
	}

	@Override
	public PagedList<Map> selectPagedList(HashMap params) {
		IQueryer<Map> queryer=new MybatisQueryer<Map>(sqlSession, NM+"selectList", params);
		queryer.query();
		return queryer.getPageList();
	}
}
