package com.ai.sizzler.scan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.sizzler.commons.JsonUtil;
import com.ai.sizzler.scan.component.AbstractImporter;

public class DbImporter extends AbstractImporter{
	private static Logger LOG= LoggerFactory.getLogger(DbImporter.class);
	private DbDataSource ds;
	private String tabName;
	private String idField;
	private String stateField;
	private String loadState;
	private String lockState;
	//mysql oracle
	private String dsType;
	//
	private Connection conn;
	private PreparedStatement loadStmt;
	private PreparedStatement lockStmt;
	//
	private String loadSql;
	private String lockSql;
	
	@Override
	public void load(Map map) {
		super.load(map);
		
		Map prop=JsonUtil.fromJson(getProps(), HashMap.class);
		setTabName((String)prop.get("tabName"));
		setIdField((String)prop.get("idField"));
		setStateField((String)prop.get("stateField"));
		setLoadState((String)prop.get("loadState"));
		setLockState((String)prop.get("lockState"));
		
		this.ds=new DbDataSource();
		this.ds.load((Map)map.get("dataSource"));
	}

	@Override
	public void init() {
	    try {
	    	//判断类型，目前只支持两种
	    	dsType=ds.getDriver().contains("mysql")?"mysql":"oracle";
	    	lockSql="update "+this.tabName+" set "+this.stateField+"='"+this.lockState
	    			+"' where "+this.idField+"=? and "+stateField+"='"+this.loadState+"'";
	    	//拼接分页查询的sql
	    	if("mysql".equals(dsType)){
	    		loadSql="select * from "+this.tabName+" where "+this.stateField+"='"+this.loadState+"' limit 0,?";
	    	}else if("oracle".equals(dsType)){
	    		loadSql="select a.*,rownum from "+this.tabName+"a where "+this.stateField+"='"+this.loadState+"' and rownum<?";
	    	}
	    	//初始化连接
	        Class.forName(ds.getDriver()); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(ds.getDbUrl(), ds.getUser(), ds.getPassword());
	        
	        loadStmt=conn.prepareStatement(loadSql);
	        lockStmt=conn.prepareStatement(lockSql);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void destroy() {
		try{
			if(loadStmt!=null){
				loadStmt.close();
				loadStmt=null;
			}
			if(lockStmt!=null){
				lockStmt.close();
				lockStmt=null;
			}
			if(conn!=null){
				conn.close();
				conn=null;
			}
		}catch (Exception e) {
			LOG.error("关闭资源链接异常:",e);
		}
	}

	@Override
	public List<Object> scan(int size) {
		//待返回结果集
		List<Object> result=new ArrayList<Object>();
		try {
			loadStmt.setInt(1, size);
			ResultSet rs=loadStmt.executeQuery();
			//遍历组装
			ResultSetMetaData meta=rs.getMetaData();
			int count = meta.getColumnCount();
			while(rs.next()){
				//单条记录
				HashMap record=new HashMap();
				for(int i=1;i<=count;i++){
					record.put(meta.getColumnName(i),rs.getObject(i));
				}
				result.add(record);
			}
			
			rs.close();
		} catch (SQLException e) {
			LOG.error("加载数据异常:",e);
		}
		return result;
	}

	@Override
	public int updateState(Object record) {
		int result=0;
		HashMap tmp=(HashMap)record;
		Object idValue=tmp.get(this.idField);

		try {
			lockStmt.setObject(1, idValue);
			result=lockStmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error("锁定数据异常:",e);
		}
		return result;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getStateField() {
		return stateField;
	}

	public void setStateField(String stateField) {
		this.stateField = stateField;
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
