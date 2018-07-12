package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;

import com.iceze.push_notification_service.service.InvalidNotificationException;

public class LinkTest {

	@Test
	public void toStringReturns() {
		Link link = Link.builder().withBody("test body")
								  .withTitle("test title")
								  .withUrl("http://www.test.com")
								  .build();
		
		Object result = link.toString();
		
		assertThat(result).isInstanceOf(String.class)
						  .isEqualTo("{\"url\":\"http://www.test.com\",\"title\":\"test title\",\"type\":\"link\",\"body\":\"test body\"}");
	}
	
	@Test
	public void validateBodyNullShould() {
		Link link = Link.builder().withBody(null)
									  .withTitle("test title")
									  .withUrl("http://www.test.com")
									  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodyEmptyStringShould() {
		Link link = Link.builder().withBody("")
				  					  .withTitle("test title")
				  					  .withUrl("http://www.test.com")
				  					  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodySpaceShould() {
		Link link = Link.builder().withBody(" ")
				  .withTitle("test title")
				  .withUrl("http://www.test.com")
				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateTitleNullShould() {
		Link link = Link.builder().withBody("test body")
									  .withTitle(null)
									  .withUrl("http://www.test.com")
									  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's title is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateTitleEmptyStringShould() {
		Link link = Link.builder().withBody("test body")
									  .withTitle("")
									  .withUrl("http://www.test.com")
									  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's title is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateTitleSpaceShould() {
		Link link = Link.builder().withBody("test body")
									  .withTitle(" ")
									  .withUrl("http://www.test.com")
									  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's title is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateUrlNullShould() {
		Link link = Link.builder().withBody("test body")
				  					  .withTitle("test title")
				  					  .withUrl(null)
				  					  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's url is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateUrlEmptyStringShould() {
		Link link = Link.builder().withBody("test body")
				  					  .withTitle("test title")
				  					  .withUrl("")
				  					  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's url is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateUrlSpaceShould() {
		Link link = Link.builder().withBody("test body")
				  					  .withTitle("test title")
				  					  .withUrl(" ")
				  					  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> link.validate())
	    	.withMessage("Notification's url is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateShould() {
		Link link = Link.builder().withBody("test body")
				  					  .withTitle("test title")
				  					  .withUrl("http://www.test.com")
				  					  .build();
		
		assertThatCode(() -> link.validate()).doesNotThrowAnyException();
	}

}
