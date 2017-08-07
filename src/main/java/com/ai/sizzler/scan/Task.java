package com.ai.sizzler.scan;

import java.sql.Date;
import java.util.Map;

import com.ai.sizzler.scan.component.Exporter;
import com.ai.sizzler.scan.component.Importer;
import com.ai.sizzler.scan.db.DbExporter;
import com.ai.sizzler.scan.db.DbImporter;
import com.ai.sizzler.scan.redis.RedisExporter;
import com.ai.sizzler.scan.redis.RedisImporter;
import com.ai.sizzler.scan.url.UrlExporter;
import com.ai.sizzler.scan.url.UrlImporter;

public class Task {
	private long id;
	//名称
	private String name;
	//分组
	private String group;
	//fixed:固定线程任务
	//dynmic:自增长线程任务
	private String type;
	//描述
	private String description;
	//每次取数据，获取的数量
	private int fetchSize;
	//当监听器判断当前没有要处理的数据时，休眠的时间，单位为秒
	private long sleepTime;
	//当type=fixed时有效，线程池数量
	private int poolSize;
	//资源导入编码
	private long impId;
	//资源导出编码
	private long expId;
	//创建时间
	private Date cTime;
	//PAUSED RUNNING
	private String state;
	
	private Map impMap;
	private Map expMap;
	//
	private Importer importer;
	private Exporter exporter;
	private Listener listener;
	
	public void init() throws ScanTaskException{
		//绑定importer
		String impType=(String)impMap.get("TYPE");
		if("db".equalsIgnoreCase(impType)){
			this.importer=new DbImporter();
			
		}else if("redis".equalsIgnoreCase(impType)){
			this.importer=new RedisImporter();
		}else if("url".equalsIgnoreCase(impType)){
			this.importer=new UrlImporter();
		}else{
			throw new ScanTaskException("初始化导入importer异常，未知的类型");
		}
		//初始化
		this.importer.load(this.impMap);
		this.importer.init();
		
		//绑定exporter
		String expType=(String)expMap.get("TYPE");
		if("db".equalsIgnoreCase(expType)){
			this.exporter=new DbExporter();
		}else if("redis".equalsIgnoreCase(expType)){
			this.exporter=new RedisExporter();
		}else if("url".equalsIgnoreCase(expType)){
			this.exporter=new UrlExporter();
		}else{
			throw new ScanTaskException("初始化导出exporter异常，未知的类型");
		}
		//初始化
		this.exporter.load(this.expMap);
		this.exporter.init();
	}
	
	public void start(){
		listener.start();
	}
	
	public void shutdown(){
		listener.shutdown();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFetchSize() {
		return fetchSize;
	}
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}
	public long getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public long getImpId() {
		return impId;
	}
	public void setImpId(long impId) {
		this.impId = impId;
	}
	public long getExpId() {
		return expId;
	}
	public void setExpId(long expId) {
		this.expId = expId;
	}
	public Date getcTime() {
		return cTime;
	}
	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}
	public Importer getImporter() {
		return importer;
	}
	public void setImporter(Importer importer) {
		this.importer = importer;
	}
	public Exporter getExporter() {
		return exporter;
	}
	public void setExporter(Exporter exporter) {
		this.exporter = exporter;
	}
	public Listener getListener() {
		return listener;
	}
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
