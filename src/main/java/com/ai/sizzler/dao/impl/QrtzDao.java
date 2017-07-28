package com.ai.sizzler.dao.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.commons.pager.IQueryer;
import com.ai.commons.pager.MybatisQueryer;
import com.ai.commons.pager.PagedList;
import com.ai.sizzler.dao.IQrtzDao;

@Repository("qrtzDao")
public class QrtzDao implements IQrtzDao{
	private static final Logger LOG = LoggerFactory.getLogger(QrtzDao.class);
	private static final String NM = "com.ai.sizzler.qrtz";
	private SqlSession sqlSession;

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public PagedList<Map> queryTaskList(Map params) {
		IQueryer<Map> queryer=new MybatisQueryer<Map>(sqlSession, NM+".selectTrigger", params);
		queryer.query();
		return queryer.getPageList();
	}
	
	
	
}
