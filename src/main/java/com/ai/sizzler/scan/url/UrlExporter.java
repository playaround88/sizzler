package com.ai.sizzler.scan.url;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractExporter;

public class UrlExporter extends AbstractExporter{
	private static Logger LOG = LoggerFactory.getLogger(UrlExporter.class);
	private String dealUri;
	private UrlDataSource ds;

	@Override
	public void load(Map map) {
		super.load(map);
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setDealUri((String)prop.get("deal_uri"));
		
		this.ds=new UrlDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}
	
	@Override
	public void init() {
		LOG.debug("init UrlExporter");
	}

	@Override
	public void deal(Object record) {
		String jsonStr=JsonUtil.toJson(record);
		LOG.debug("urlExport deal record:"+jsonStr);
		
	}

	public String getDealUri() {
		return dealUri;
	}

	public void setDealUri(String dealUri) {
		this.dealUri = dealUri;
	}
	
}
