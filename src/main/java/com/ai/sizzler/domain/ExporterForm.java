package com.ai.sizzler.domain;

public class ExporterForm {
	private long id;
	private String expName;
	private String desc;
	private String props;
	private long dsId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
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
	public long getDsId() {
		return dsId;
	}
	public void setDsId(long dsId) {
		this.dsId = dsId;
	}
	
	
}
