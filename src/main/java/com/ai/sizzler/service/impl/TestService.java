package com.ai.sizzler.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.sizzler.dao.ITestDao;
import com.ai.sizzler.service.ITestService;

@Service("testService")
public class TestService implements ITestService{
	private static final Logger LOG=LoggerFactory.getLogger(TestService.class);
	private ITestDao dao=null;
	@Autowired
	public void setDao(ITestDao dao) {
		this.dao = dao;
	}

	@Override
	public String sayHello(String name) {
		LOG.debug("access testservie");
		return dao.sayHello(name);
	}
	
}
