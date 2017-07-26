package com.ai.sizzler.domain;

import java.io.Serializable;

public class JobForm implements Serializable{
	private static final long serialVersionUID = -7594864144801684198L;
	private String jobId;
	private String group;
	private String description;
	private String notify;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	
}
