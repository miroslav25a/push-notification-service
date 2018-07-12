package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class UserTest {

	@Test
	public void builderShould() {
		User result = User.builder().withAccessToken("test token")
								  	.withCreationTime("2018-08-01T23:11:11")
								  	.withNumOfNotificationsPushed(1)
								  	.withUsername("test username")
								  	.build();
		
		assertThat(result).isNotNull();
		assertThat(result.getAccessToken()).isEqualTo("test token");
		assertThat(result.getCreationTime()).isEqualTo("2018-08-01T23:11:11");
		assertThat(result.getNumOfNotificationsPushed()).isEqualTo(1);
		assertThat(result.getUsername()).isEqualTo("test username");
	}
	
	@Test
	public void equalsTrueShould() {
		User u1 = User.builder().withAccessToken("test token 1")
				  				.withCreationTime("2018-08-01T23:11:11")
				  				.withNumOfNotificationsPushed(1)
				  				.withUsername("user")
				  				.build();
		
		User u2 = User.builder().withAccessToken("test token 2")
				  				.withCreationTime("2018-08-01T23:11:12")
				  				.withNumOfNotificationsPushed(2)
				  				.withUsername("user")
				  				.build();
		
		boolean result = u1.equals(u2);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void equalsFalseShould() {
		User u1 = User.builder().withAccessToken("test token 1")
				  				.withCreationTime("2018-08-01T23:11:11")
				  				.withNumOfNotificationsPushed(1)
				  				.withUsername("user")
				  				.build();
		
		User u2 = User.builder().withAccessToken("test token 1")
				  				.withCreationTime("2018-08-01T23:11:11")
				  				.withNumOfNotificationsPushed(1)
				  				.withUsername("user2")
				  				.build();
		
		boolean result = u1.equals(u2);
		
		assertThat(result).isFalse();
	}
}
