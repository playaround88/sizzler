package com.ai.sizzler.domain;

public class CronJobForm extends JobForm{
	private static final long serialVersionUID = 8314785589754274445L;
	private String cronExpression;
	
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
