package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LinkTest {

	@Test
	public void toStringReturns() {
		Link link = Link.linkBuilder().withBody("test body")
								  .withTitle("test title")
								  .withType(PushType.LINK)
								  .withUrl("http://www.test.com")
								  .build();
		
		Object result = link.toString();
		
		assertThat(result).isInstanceOf(String.class)
						  .isEqualTo("{\"url\":\"http://www.test.com\",\"title\":\"test title\",\"type\":\"link\",\"body\":\"test body\"}");
	}

}
