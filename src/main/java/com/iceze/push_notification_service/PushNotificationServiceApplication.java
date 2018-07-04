package com.iceze.push_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.iceze.push_notification_service"})
public class PushNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushNotificationServiceApplication.class, args);
	}
}
