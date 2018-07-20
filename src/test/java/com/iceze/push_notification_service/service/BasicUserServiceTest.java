package com.iceze.push_notification_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.google.common.collect.Sets;
import com.iceze.push_notification_service.dao.UserDao;
import com.iceze.push_notification_service.model.User;

public class BasicUserServiceTest {
	@InjectMocks
	private BasicUserService service;
	@Mock
	private UserDao userDao;
	
	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void findAllUsersShould() {
		User user1 = User.builder().withAccessToken("token")
				   				   .withUsername("user1")
				   				   .build();
		
		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user1)));
		
		Optional<Set<User>> result = service.findAllUsers();
		
		assertThat(result).isNotNull();
		assertThat(result.get()).isNotEmpty().contains(user1);
		
		verify(this.userDao).findAllUsers();
	}
	
	@Test
	public void saveUserShould() {
		User user = User.builder().withAccessToken("token")
								  .withUsername("user1")
								  .build();
		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet()));
		when(this.userDao.saveUser(any(User.class))).thenReturn(Optional.of(user));
		
		Optional<User> result = this.service.saveUser(user);
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getAccessToken()).isNotNull().isEqualTo("token");
		assertThat(result.get().getUsername()).isNotNull().isEqualTo("user1");
		assertThat(result.get().getCreationTime()).isNotBlank();
		assertThat(result.get().getNumOfNotificationsPushed()).isNotNull().isEqualTo(0);
		
		verify(this.userDao).findAllUsers();
		verify(this.userDao).saveUser(any(User.class));
	}
	
	@Test
	public void saveUserExistingUserShould() {
		User user = User.builder().withAccessToken("token")
								  .withUsername("user1")
								  .build();
		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		
		assertThatExceptionOfType(ExistingUserException.class)
		    	.isThrownBy(() -> this.service.saveUser(user))
		    	.withMessage("User with username user1 already exists")
		    	.withStackTraceContaining("ExistingUserException")
		    	.withNoCause();
		
		verify(this.userDao).findAllUsers();
		verify(this.userDao, times(0)).saveUser(any(User.class));
	}
	
	@Test
	public void findByUsernameShould() {
		User user = User.builder().withAccessToken("token")
				  .withUsername("user1")
				  .build();

		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		
		Optional<User> result = this.service.findByUsername("user1");
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getUsername()).isNotNull().isEqualTo("user1");
		
		verify(this.userDao).findAllUsers();
	}
	
	@Test
	public void findByUsernameUserDoesNotExistShould() {
		User user = User.builder().withAccessToken("token")
				  .withUsername("user1")
				  .build();

		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		
		Optional<User> result = this.service.findByUsername("user2");
		
		assertThat(result).isNotNull();
		assertThat(result.isPresent()).isFalse();
		
		verify(this.userDao).findAllUsers();
	}
	
	@Test
	public void updateUserShould() {
		User user = User.builder().withAccessToken("token")
				  				  .withUsername("user1")
				  				  .build();
		
		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		
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
		
		verify(this.userDao, times(2)).findAllUsers();
		verify(this.userDao).saveUser(any(User.class));
	}
	
	@Test
	public void updateUserDoesNotExistShould() {
		User user = User.builder().withAccessToken("token")
								  .withUsername("user1")
								  .build();
		
		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		
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
		
		verify(this.userDao).findAllUsers();
		verify(this.userDao, times(0)).saveUser(any(User.class));
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
		
		when(this.userDao.findAllUsers()).thenReturn(Optional.of(Sets.newHashSet(user)));
		
		assertThatExceptionOfType(ExistingUserException.class)
		    	.isThrownBy(() -> this.service.validate(user))
		    	.withMessage("User with username user1 already exists")
		    	.withStackTraceContaining("ExistingUserException")
		    	.withNoCause();
		
		verify(this.userDao).findAllUsers();
	}
}
