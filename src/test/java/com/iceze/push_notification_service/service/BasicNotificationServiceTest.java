package com.iceze.push_notification_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

import com.iceze.push_notification_service.model.Notification;
import com.iceze.push_notification_service.model.PushResponse;
import com.iceze.push_notification_service.model.User;

@RunWith(MockitoJUnitRunner.class)
public class BasicNotificationServiceTest {
	@Spy
	@InjectMocks
	private BasicNotificationService service;
	@Mock
	private UserService userService;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ResponseEntity<PushResponse> responseEntity;
	
	@Before
	public void setup() {
		initMocks(this);
		doReturn(this.restTemplate).when(this.service).createClient();
	}

	@Test
	public void pushShould() {
		when(this.userService.findByUsername(anyString())).thenReturn(Optional.of(User.builder().withAccessToken("token")
																								.withNumOfNotificationsPushed(0)
																								.build()));
		when(this.restTemplate.exchange(
				anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(PushResponse.class)))
					.thenReturn(this.responseEntity);
		when(this.responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(this.responseEntity.getBody()).thenReturn(new PushResponse());
		
		Optional<PushResponse> result = this.service.push(Notification.builder().withBody("test body")
																				.withTitle("test title")
																				.withType("note")
																				.withUsername("username")
																				.build());
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		
		verify(this.userService).findByUsername(anyString());
		verify(this.restTemplate).exchange(
				anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(PushResponse.class));
		verify(this.responseEntity).getStatusCode();
		verify(this.responseEntity).getBody();
		verify(this.userService).updateUser(any(User.class));
	}
	
	@Test
	public void pushInvalidUsernameShould() {
		when(this.userService.findByUsername(anyString())).thenReturn(Optional.empty());
		
		assertThatExceptionOfType(InvalidNotificationException.class)
		    	.isThrownBy(() -> this.service.push(Notification.builder().withBody("test body")
																		  .withTitle("test title")
																		  .withType("note")
																		  .withUsername("username")
																		  .build()))
		    	.withMessage("Notification's username is invalid")
		    	.withStackTraceContaining("InvalidNotificationException")
		    	.withNoCause();
		
		verify(this.userService).findByUsername(anyString());
		verify(this.restTemplate, times(0)).exchange(
				anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(PushResponse.class));
		verify(this.responseEntity, times(0)).getStatusCode();
		verify(this.responseEntity, times(0)).getBody();
		verify(this.userService, times(0)).updateUser(any(User.class));
	}
	
	@Test
	public void pushStatusCodeNotOkShould() {
		when(this.userService.findByUsername(anyString())).thenReturn(Optional.of(User.builder().withAccessToken("token")
																								.withNumOfNotificationsPushed(0)
																								.build()));
		when(this.restTemplate.exchange(
				anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(PushResponse.class)))
					.thenReturn(this.responseEntity);
		when(this.responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
		
		Optional<PushResponse> result = this.service.push(Notification.builder().withBody("test body")
																				.withTitle("test title")
																				.withType("note")
																				.withUsername("username")
																				.build());
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isFalse();
		
		verify(this.userService).findByUsername(anyString());
		verify(this.restTemplate).exchange(
				anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(PushResponse.class));
		verify(this.responseEntity).getStatusCode();
		verify(this.responseEntity, times(0)).getBody();
		verify(this.userService, times(0)).updateUser(any(User.class));
	}
}
