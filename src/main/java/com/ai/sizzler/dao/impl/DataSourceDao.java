package com.ai.sizzler.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.commons.pager.IQueryer;
import com.ai.commons.pager.MybatisQueryer;
import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IDataSourceDao;
import com.ai.sizzler.domain.DataSourceForm;

@Repository("dataSourceDao")
public class DataSourceDao implements IDataSourceDao{
	private static final String NM="com.ai.sizzler.scan.ds.";
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int insert(DataSourceForm ds) {
		return sqlSession.insert(NM+"insert", ds);
	}

	@Override
	public int del(long id) {
		return sqlSession.delete(NM+"deleteById", id);
	}

	@Override
	public int update(DataSourceForm ds) {
		return sqlSession.update(NM+"update", ds);
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

	@Override
	public List<Map> selectList(HashMap params) {
		return sqlSession.selectList(NM+"selectList", params);
	}

}
