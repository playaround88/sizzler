package com.ai.sizzler.scan.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractDataSource;

public class RedisDataSource extends AbstractDataSource{
	private static Logger LOG=LoggerFactory.getLogger(RedisDataSource.class);
	private String host;
	private int port;
	private String password;

	@Override
	public void load(Map map) {
		LOG.debug("load RedisDataSource props");
		super.load(map);
		
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setHost((String)prop.get("host"));
		setPort(Integer.parseInt((String)prop.get("port")));
		setPassword((String)prop.get("password"));
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
