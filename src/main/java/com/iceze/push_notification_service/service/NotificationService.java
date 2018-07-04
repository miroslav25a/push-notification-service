package com.iceze.push_notification_service.service;

import java.util.Optional;

import com.iceze.push_notification_service.model.Notification;
import com.iceze.push_notification_service.model.PushResponse;

public interface NotificationService {
	Optional<PushResponse> push(final Notification notification);
	void validate(final Notification notification);
}
