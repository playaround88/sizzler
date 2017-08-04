package com.ai.sizzler.domain;

public class ImporterForm {
	private long id;
	private String impName;
	private String desc;
	private String props;
	private long dsId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImpName() {
		return impName;
	}
	public void setImpName(String impName) {
		this.impName = impName;
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
