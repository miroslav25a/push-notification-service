package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;

import com.iceze.push_notification_service.service.InvalidNotificationException;

public class NoteTest {

	@Test
	public void toStringReturns() {
		Note note = Note.builder().withBody("test body")
								  .withTitle("test title")
								  .build();
		
		Object result = note.toString();
		
		assertThat(result).isInstanceOf(String.class)
						  .isEqualTo("{\"title\":\"test title\",\"type\":\"note\",\"body\":\"test body\"}");
	}
	
	@Test
	public void validateBodyNullShould() {
		Note note = Note.builder().withBody(null)
								  .withTitle("test title")
								  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> note.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodyEmptyStringShould() {
		Note note = Note.builder().withBody("")
				  				  .withTitle("test title")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> note.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodySpaceShould() {
		Note note = Note.builder().withBody(" ")
				  				  .withTitle("test title")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> note.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateTitleNullShould() {
		Note note = Note.builder().withBody("test body")
								  .withTitle(null)
								  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> note.validate())
	    	.withMessage("Notification's title is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateTitleEmptyStringShould() {
		Note note = Note.builder().withBody("test body")
								  .withTitle("")
								  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> note.validate())
	    	.withMessage("Notification's title is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateTitleSpaceShould() {
		Note note = Note.builder().withBody("test body")
								  .withTitle(" ")
								  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> note.validate())
	    	.withMessage("Notification's title is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateShould() {
		Note note = Note.builder().withBody("test body")
				  				  .withTitle("test title")
				  				  .build();
		
		assertThatCode(() -> note.validate()).doesNotThrowAnyException();
	}
}
