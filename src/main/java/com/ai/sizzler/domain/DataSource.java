package com.ai.sizzler.domain;

public abstract class DataSource {
	private long id;
	private String name;
	private String description;
	private String props;
	
	protected abstract void parseProps();
	protected abstract void jsonProps();
	//
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProps() {
		return props;
	}
	public void setProps(String props) {
		this.props = props;
	}
}
