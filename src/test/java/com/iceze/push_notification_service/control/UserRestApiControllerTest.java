package com.iceze.push_notification_service.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Sets;
import com.iceze.push_notification_service.model.User;
import com.iceze.push_notification_service.service.ExistingUserException;
import com.iceze.push_notification_service.service.InvalidUserException;
import com.iceze.push_notification_service.service.UserService;

public class UserRestApiControllerTest {
	@InjectMocks
	private UserRestApiController controller;
	@Mock
	private UserService userService;
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Before
	public void setup() {
		initMocks(this);
		this.uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme("http").host("www.test.com");
	}
	
	@Test
	public void createUserShould() {
		when(this.userService.saveUser(any(User.class))).thenReturn(Optional.of(User.builder().withAccessToken("token")
																							  .withCreationTime("time")
																							  .withNumOfNotificationsPushed(0)
																							  .withUsername("user")
																							  .build()));
		
		ResponseEntity<?> result = this.controller.createUser(User.builder().withAccessToken("token")
												 							.withUsername("user")
												 							.build(), uriComponentsBuilder);
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		User resultBody = (User) result.getBody();
		
		assertThat(resultBody.getAccessToken()).isNotNull().isEqualTo("token");
		assertThat(resultBody.getCreationTime()).isNotNull().isEqualTo("time");
		assertThat(resultBody.getNumOfNotificationsPushed()).isNotNull().isEqualTo(0);
		assertThat(resultBody.getUsername()).isNotNull().isEqualTo("user");
		
		assertThat(result.getHeaders()).isNotEmpty();
		assertThat(result.getHeaders().getLocation()).isNotNull().isEqualTo(URI.create("http://www.test.com/api/user/user"));
	}
	
	@Test
	public void createUserValidationInvalidShould() {
		doThrow(new InvalidUserException("error")).when(this.userService).validate(any(User.class));
		
		ResponseEntity<?> result = this.controller.createUser(User.builder().withAccessToken("token")
																			.withUsername("user")
																			.build(), uriComponentsBuilder);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isNotNull().isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(result.getBody()).isNotNull().isEqualTo("error");
		
		verify(this.userService).validate(any(User.class));
		verify(this.userService, times(0)).saveUser(any(User.class));
	}
	
	@Test
	public void createUserValidationExistingUserShould() {
		doThrow(new ExistingUserException("error")).when(this.userService).validate(any(User.class));
		
		ResponseEntity<?> result = this.controller.createUser(User.builder().withAccessToken("token")
																			.withUsername("user")
																			.build(), uriComponentsBuilder);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CONFLICT);
		assertThat(result.getHeaders().getLocation()).isNotNull().isEqualTo(URI.create("http://www.test.com/api/user/user"));
		
		verify(this.userService).validate(any(User.class));
		verify(this.userService, times(0)).saveUser(any(User.class));
	}
	
	@Test
	public void listAllUsersShould() {
		User user = User.builder().withAccessToken("token")
				   				  .withCreationTime("time")
				   				  .withNumOfNotificationsPushed(0)
				   				  .withUsername("user")
				   				  .build();
		
		when(this.userService.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		ResponseEntity<Set<User>> result = this.controller.listAllUsers();
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isNotEmpty().contains(user);
		
		verify(this.userService).findAllUsers();
	}
	
	@Test
	public void listAllUsersOptionalEmptyShould() {
		when(this.userService.findAllUsers()).thenReturn(Optional.empty());
		ResponseEntity<Set<User>> result = this.controller.listAllUsers();
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		verify(this.userService).findAllUsers();
	}
	
	@Test
	public void listAllUsersSetEmptyShould() {
		when(this.userService.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet()));
		ResponseEntity<Set<User>> result = this.controller.listAllUsers();
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		verify(this.userService).findAllUsers();
	}
	
	@Test
	public void retrieveUserShould() {
		User user = User.builder().withAccessToken("token")
 				  				  .withCreationTime("time")
 				  				  .withNumOfNotificationsPushed(0)
 				  				  .withUsername("user")
 				  				  .build();
		
		when(this.userService.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		ResponseEntity<?> result = this.controller.retrieveUser("user");
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		User resultUser = (User) result.getBody();
		
		assertThat(resultUser.getAccessToken()).isNotNull().isEqualTo("token");
		assertThat(resultUser.getCreationTime()).isNotNull().isEqualTo("time");
		assertThat(resultUser.getNumOfNotificationsPushed()).isNotNull().isEqualTo(0);
		assertThat(resultUser.getUsername()).isNotNull().isEqualTo("user");
		
		verify(this.userService).findByUsername(any(String.class));
	}
	
	@Test
	public void retrieveUserOptionalEmptyShould() {
		when(this.userService.findByUsername(any(String.class))).thenReturn(Optional.empty());
		ResponseEntity<?> result = this.controller.retrieveUser("user");
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		verify(this.userService).findByUsername(any(String.class));
	}
}
