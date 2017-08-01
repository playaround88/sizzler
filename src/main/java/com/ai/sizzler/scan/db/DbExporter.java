package com.ai.sizzler.scan.db;

import java.util.HashMap;
import java.util.Map;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractExporter;

public class DbExporter extends AbstractExporter{
	private DbDataSource ds;
	private String tabName;
	
	@Override
	public void load(Map map) {
		super.load(map);
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setTabName((String)prop.get("tab_name"));
		
		this.ds=new DbDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deal(Object record) {
		
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

}
