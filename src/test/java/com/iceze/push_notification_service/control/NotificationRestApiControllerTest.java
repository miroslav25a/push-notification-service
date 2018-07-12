package com.iceze.push_notification_service.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.iceze.push_notification_service.model.Notification;
import com.iceze.push_notification_service.model.PushResponse;
import com.iceze.push_notification_service.service.InvalidNotificationException;
import com.iceze.push_notification_service.service.NotificationService;

public class NotificationRestApiControllerTest {
	@InjectMocks
	private NotificationRestApiController controller;
	@Mock
	private NotificationService notificationService;
	@Mock
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void createNotificationShould() {
		PushResponse pr = new PushResponse();
		when(this.notificationService.push(any(Notification.class))).thenReturn(Optional.of(pr));
		
		ResponseEntity<?> result = this.controller.createNotification(Notification.builder().withBody("test body")
														 							.withTitle("test title")
														 							.withType("note")
														 							.withUsername("user")
														 							.build(), uriComponentsBuilder);
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);
		assertThat(result.getBody()).isNotNull().isEqualTo(pr);
		
		verify(this.notificationService).push(any(Notification.class));
		verify(this.notificationService).validate(any(Notification.class));
	}
	
	@Test
	public void createNotificationValidationInvalidShould() {
		doThrow(new InvalidNotificationException("error")).when(this.notificationService).validate(any(Notification.class));
		
		ResponseEntity<?> result = this.controller.createNotification(Notification.builder().withBody("test body")
																							.withTitle("test title")
																							.withType("note")
																							.withUsername("user")
																							.build(), uriComponentsBuilder);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(result.getBody()).isNotNull().isEqualTo("error");
		
		verify(this.notificationService).validate(any(Notification.class));
		verify(this.notificationService, times(0)).push(any(Notification.class));
	}
	
	@Test
	public void createNotificationServerErrorShould() {
		doThrow(new RuntimeException("error")).when(this.notificationService).push(any(Notification.class));
		
		ResponseEntity<?> result = this.controller.createNotification(Notification.builder().withBody("test body")
																							.withTitle("test title")
																							.withType("note")
																							.withUsername("user")
																							.build(), uriComponentsBuilder);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isNotNull().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(result.getBody()).isNotNull().isEqualTo("Internal Server Error");
		
		verify(this.notificationService).validate(any(Notification.class));
		verify(this.notificationService, times(1)).push(any(Notification.class));
	}

}
