package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NotificationTest {

	@Test
	public void builderShould() {
		Object result = Notification.builder().withBody("test body")
											  .withFileName("test file name")
											  .withFileType("test file type")
											  .withFileUrl("test file url")
											  .withTitle("test title")
											  .withType("test type")
											  .withUrl("test url")
											  .withUsername("test username")
											  .build();
		
		assertThat(result).isNotNull().isInstanceOf(Notification.class);
		
		Notification n = (Notification) result;
		
		assertThat(n.getBody()).isEqualTo("test body");
		assertThat(n.getFileName()).isEqualTo("test file name");
		assertThat(n.getFileType()).isEqualTo("test file type");
		assertThat(n.getFileUrl()).isEqualTo("test file url");
		assertThat(n.getTitle()).isEqualTo("test title");
		assertThat(n.getType()).isEqualTo("test type");
		assertThat(n.getUrl()).isEqualTo("test url");
		assertThat(n.getUsername()).isEqualTo("test username");
	}
}
