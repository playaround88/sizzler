package com.ai.sizzler.domain;

public class SimpleJobForm extends JobForm{
	private static final long serialVersionUID = -6246955968046621842L;
	
	private int intervalInSeconds;
	private boolean repeat;
	
	public int getIntervalInSeconds() {
		return intervalInSeconds;
	}
	public void setIntervalInSeconds(int intervalInSeconds) {
		this.intervalInSeconds = intervalInSeconds;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
}
