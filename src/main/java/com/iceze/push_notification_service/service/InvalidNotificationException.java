package com.iceze.push_notification_service.service;

public class InvalidNotificationException extends RuntimeException {
	private static final long serialVersionUID = -217699758249739149L;
	
	public InvalidNotificationException(final String e) {
		super(e);
	}
}
