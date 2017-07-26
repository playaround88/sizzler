package com.ai.sizzler.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ai.sizzler.dao.ITestDao;

@Repository("testDao")
public class TestDao implements ITestDao{
	private static final Logger LOG=LoggerFactory.getLogger(TestDao.class);

	@Override
	public String sayHello(String name) {
		LOG.debug("access testDao");
		return "Hello "+name;
	}

}
