package com.iceze.push_notification_service.model;

public abstract class Push {
	protected PushType type;
	protected String body;
	
	public PushType getType() {
		return this.type;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public abstract void validate();
	
	public abstract String toString();
}
