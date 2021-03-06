package com.ai.sizzler.dao.impl;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.commons.pager.IQueryer;
import com.ai.commons.pager.MybatisQueryer;
import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.ITaskDao;
import com.ai.sizzler.scan.Task;

@Repository("taskDao")
public class TaskDao implements ITaskDao{
	private static final String NM="com.ai.sizzler.scan.task.";
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int insert(Task task) {
		return sqlSession.insert(NM+"insert",task);
	}

	@Override
	public int del(long id) {
		return sqlSession.delete(NM+"deleteById",id);
	}

	@Override
	public int update(Task task) {
		return sqlSession.update(NM+"update", task);
	}

	@Override
	public Task selectById(long id) {
		return sqlSession.selectOne(NM+"selectById",id);
	}

	@Override
	public PagedList<Task> selectPagedList(HashMap params) {
		IQueryer<Task> queryer=new MybatisQueryer<Task>(sqlSession, NM+"selectList", params);
		queryer.query();
		return queryer.getPageList();
	}
	
}
