package com.ai.sizzler.scan.url;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractDataSource;

public class UrlDataSource extends AbstractDataSource{
	private static Logger LOG = LoggerFactory.getLogger(UrlDataSource.class);
	private String baseUrl;

	@Override
	public void load(Map map) {
		super.load(map);
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setBaseUrl((String)prop.get("BASE_URL"));
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
