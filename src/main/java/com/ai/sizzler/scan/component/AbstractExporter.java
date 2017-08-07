package com.ai.sizzler.scan.component;

import java.sql.Date;
import java.util.Map;

public abstract class AbstractExporter implements Exporter{
	private long id;
	private String name;
	private String desc;
	private String props;
	private Date ctime;
	private long dsId;
	
	@Override
	public void load(Map map) {
		this.setId((Long)map.get("id"));
		this.setName((String)map.get("expName"));
		this.setDesc((String)map.get("description"));		
		this.setProps((String)map.get("props"));
		this.setCtime((Date)map.get("ctime"));
		this.setDsId((Long)map.get("dsId"));
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
	public long getDsId() {
		return dsId;
	}
	public void setDsId(long dsId) {
		this.dsId = dsId;
	}

}
