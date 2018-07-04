package com.iceze.push_notification_service.service;

public class ExistingUserException extends RuntimeException {
	private static final long serialVersionUID = -4721071289849696547L;
	
	public ExistingUserException(final String e) {
		super(e);
	}
}
