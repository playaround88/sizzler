package com.ai.sizzler.scan.db;

import java.util.HashMap;
import java.util.Map;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractDataSource;

public class DbDataSource extends AbstractDataSource{
	private String driver;
	private String dbUrl;
	private String user;
	private String password;
	
	@Override
	public void load(Map map) {
		super.load(map);
		
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setDriver((String)prop.get("driver"));
		setDbUrl((String)prop.get("dbUrl"));
		setUser((String)prop.get("user"));
		setPassword((String)prop.get("password"));
	}
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
