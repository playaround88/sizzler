package com.ai.sizzler.scan.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractImporter;

public class DbImporter extends AbstractImporter{
	private DbDataSource ds;
	private String tabName;
	private String loadState;
	private String lockState;
	
	@Override
	public void load(Map map) {
		super.load(map);
		
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setTabName((String)prop.get("tab_name"));
		setLoadState((String)prop.get("loadState"));
		setLockState((String)prop.get("lockState"));
		
		this.ds=new DbDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}

	@Override
	public void init() {
		//TODO 初始化数据库连接
	}

	@Override
	public List<Object> scan(int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateState(Object record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getLoadState() {
		return loadState;
	}

	public void setLoadState(String loadState) {
		this.loadState = loadState;
	}

	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}

	
	
}
