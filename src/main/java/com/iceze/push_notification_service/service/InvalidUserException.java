package com.iceze.push_notification_service.service;

public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = -217699758249739149L;
	
	public InvalidUserException(final String e) {
		super(e);
	}
}
