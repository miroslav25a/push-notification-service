package com.iceze.push_notification_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.iceze.push_notification_service.model.User;

public class BasicUserServiceTest {
	private BasicUserService service;
	
	@Before
	public void setup() {
		this.service = new BasicUserService();
	}

	@Test
	public void findAllUsersShould() {
		User user1 = User.builder().withAccessToken("token")
				   				   .withUsername("user1")
				   				   .build();
		
		service.saveUser(user1);
		
		Optional<Set<User>> result = service.findAllUsers();
		
		assertThat(result).isNotNull();
		assertThat(result.get()).isNotEmpty().contains(user1);
	}
	
	@Test
	public void saveUserShould() {
		Optional<User> result = this.service.saveUser(User.builder().withAccessToken("token")
																	.withUsername("user1")
																	.build());
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getAccessToken()).isNotNull().isEqualTo("token");
		assertThat(result.get().getUsername()).isNotNull().isEqualTo("user1");
		assertThat(result.get().getCreationTime()).isNotBlank();
		assertThat(result.get().getNumOfNotificationsPushed()).isNotNull().isEqualTo(0);
	}
	
	@Test
	public void saveUserExistingUserShould() {
		User user = User.builder().withAccessToken("token")
								  .withUsername("user1")
								  .build();
		
		this.service.saveUser(user);
		
		assertThatExceptionOfType(ExistingUserException.class)
		    	.isThrownBy(() -> this.service.saveUser(user))
		    	.withMessage("User with username user1 already exists")
		    	.withStackTraceContaining("ExistingUserException")
		    	.withNoCause();
	}
	
	@Test
	public void findByUsernameShould() {
		User user = User.builder().withAccessToken("token")
				  .withUsername("user1")
				  .build();

		this.service.saveUser(user);
		
		Optional<User> result = this.service.findByUsername("user1");
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getUsername()).isNotNull().isEqualTo("user1");
	}
	
	@Test
	public void findByUsernameUserDoesNotExistShould() {
		User user = User.builder().withAccessToken("token")
				  .withUsername("user1")
				  .build();

		this.service.saveUser(user);
		
		Optional<User> result = this.service.findByUsername("user2");
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isFalse();
	}
	
	@Test
	public void updateUserShould() {
		User user = User.builder().withAccessToken("token")
				  				  .withUsername("user1")
				  				  .build();
		
		this.service.saveUser(user);
		
		User updateUser = User.builder().withAccessToken("update token")
				  						.withUsername("user1")
				  						.withCreationTime("update time")
				  						.withNumOfNotificationsPushed(1)
				  						.build();
		
		Optional<User> result = this.service.updateUser(updateUser);
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getUsername()).isNotNull().isEqualTo("user1");
		assertThat(result.get().getAccessToken()).isNotNull().isEqualTo("update token");
		assertThat(result.get().getCreationTime()).isNotNull().isEqualTo("update time");
		assertThat(result.get().getNumOfNotificationsPushed()).isNotNull().isEqualTo(1);
	}
	
	@Test
	public void updateUserDoesNotExistShould() {
		User user = User.builder().withAccessToken("token")
								  .withUsername("user1")
								  .build();
		
		this.service.saveUser(user);
		
		User updateUser = User.builder().withAccessToken("update token")
										.withUsername("user2")
										.withCreationTime("update time")
										.withNumOfNotificationsPushed(1)
										.build();
		
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.updateUser(updateUser))
		    	.withMessage("User not found")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserNullShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(null))
		    	.withMessage("User is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserUsernameNullShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(User.builder().withAccessToken("test token")
		    														  .withUsername(null)
		    														  .build()))
		    	.withMessage("User's username is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserUsernameEmptyStringShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(User.builder().withAccessToken("test token")
		    														  .withUsername("")
		    														  .build()))
		    	.withMessage("User's username is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserUsernameSpaceShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(User.builder().withAccessToken("test token")
		    														  .withUsername(" ")
		    														  .build()))
		    	.withMessage("User's username is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserAccessTokenNullShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(User.builder().withAccessToken(null)
		    														  .withUsername("user")
		    														  .build()))
		    	.withMessage("User's access token is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserAccessTokenEmptyStringShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(User.builder().withAccessToken("")
		    														  .withUsername("user")
		    														  .build()))
		    	.withMessage("User's access token is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateUserAccessTokenSpaceShould() {
		assertThatExceptionOfType(InvalidUserException.class)
		    	.isThrownBy(() -> this.service.validate(User.builder().withAccessToken(" ")
		    														  .withUsername("user")
		    														  .build()))
		    	.withMessage("User's access token is invalid")
		    	.withStackTraceContaining("InvalidUserException")
		    	.withNoCause();
	}
	
	@Test
	public void validateExistingUserShould() {
		User user = User.builder().withAccessToken("token")
								  .withUsername("user1")
								  .build();
		
		this.service.saveUser(user);
		
		assertThatExceptionOfType(ExistingUserException.class)
		    	.isThrownBy(() -> this.service.validate(user))
		    	.withMessage("User with username user1 already exists")
		    	.withStackTraceContaining("ExistingUserException")
		    	.withNoCause();
	}
}
