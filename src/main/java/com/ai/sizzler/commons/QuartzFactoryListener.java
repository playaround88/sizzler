package com.ai.sizzler.commons;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.quartz.QuartzFacade;

public class QuartzFactoryListener implements ServletContextListener{
	private static final Logger LOG=LoggerFactory.getLogger(QuartzFactoryListener.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.debug("init quartz facade"); 
		QuartzFacade.getInstance().init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOG.debug("init quartz scheduler");
		QuartzFacade.getInstance().shutdown();
	}

}
