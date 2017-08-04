package com.ai.sizzler.commons;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlMigrateListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(SqlMigrateListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties prop = new Properties();
		try {
			LOG.info("加载配置文件(load config.properties)");
			prop.load(SqlMigrateListener.class.getResourceAsStream("/config.properties"));

			String dbUrl = prop.getProperty("db_url");
			String dbUser = prop.getProperty("db_user");
			String dbPassword = prop.getProperty("db_password");

			LOG.info("执行sql migration...");
			Flyway flyway = new Flyway();
			flyway.setDataSource(dbUrl, dbUser, dbPassword);
			//flyway.baseline();
			flyway.migrate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			prop.clear();
			prop = null;
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// pass
	}

}
