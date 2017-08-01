package com.ai.sizzler.scan.component;

import java.sql.Date;
import java.util.Map;

public abstract class AbstractDataSource implements DataSource{
	private long id;
	private String name;
	private String type;
	private String desc;
	private String props;
	private Date ctime;
	
	@Override
	public void load(Map map) {
		this.setId((Long)map.get("id"));
		this.setName((String)map.get("name"));
		this.setType((String)map.get("type"));
		this.setDesc((String)map.get("desc"));		
		this.setProps((String)map.get("prop"));
		this.setCtime((Date)map.get("ctime"));
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getProps() {
		return props;
	}
	public void setProps(String props) {
		this.props = props;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	
	
}
