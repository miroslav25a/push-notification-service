package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PushTypeTest {

	@Test
	public void findByDisplayNameReturns() {
		Object result = PushType.findByDisplayName("note");
		
		assertThat(result).isNotNull()
						  .isInstanceOf(PushType.class)
						  .isEqualTo(PushType.NOTE);
	}
	
	@Test
	public void findByDisplayNameWrongReturns() {
		Object result = PushType.findByDisplayName("notee");
		
		assertThat(result).isNull();
	}
	
	@Test
	public void displayNameReturns() {
		Object result = PushType.NOTE.displayName();
		
		assertThat(result).isNotNull()
						  .isInstanceOf(String.class)
						  .isEqualTo("note");
	}
}
