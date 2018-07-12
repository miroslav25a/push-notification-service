package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;

import com.iceze.push_notification_service.service.InvalidNotificationException;

public class FileTest {

	@Test
	public void toStringReturns() {
		File file = File.builder().withBody("test body")
								  .withFileName("john.jpg")
								  .withFileType("image/jpeg")
								  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
								  .build();
		
		Object result = file.toString();
		
		assertThat(result).isInstanceOf(String.class)
						  .isEqualTo("{\"file_name\":\"john.jpg\","
						  		+ "\"file_type\":\"image/jpeg\","
						  		+ "\"file_url\":\"https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg\","
						  		+ "\"type\":\"file\","
						  		+ "\"body\":\"test body\"}");
	}
	
	@Test
	public void validateFileNameNullShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName(null)
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_name is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileNameEmptyStringShould() {
		File file = File.builder().withBody("test body")
				  .withFileName("")
				  .withFileType("image/jpeg")
				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_name is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileNameSpaceShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName(" ")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_name is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileTypeNullShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType(null)
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_type is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileTypeEmptyStringShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType("")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_type is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileTypeSpaceShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType(" ")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_type is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileUrlNullShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl(null)
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_url is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileUrlEmptyStringShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_url is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateFileUrlSpaceShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl(" ")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's file_url is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodyNullShould() {
		File file = File.builder().withBody(null)
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodyEmptyStringShould() {
		File file = File.builder().withBody("")
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateBodySpaceShould() {
		File file = File.builder().withBody(" ")
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatExceptionOfType(InvalidNotificationException.class)
	    	.isThrownBy(() -> file.validate())
	    	.withMessage("Notification's body is invalid")
	    	.withStackTraceContaining("InvalidNotificationException")
	    	.withNoCause();
	}
	
	@Test
	public void validateShould() {
		File file = File.builder().withBody("test body")
				  				  .withFileName("john.jpg")
				  				  .withFileType("image/jpeg")
				  				  .withFileUrl("https://dl.pushbulletusercontent.com/foGfub1jtC6yYcOMACk1AbHwTrTKvrDc/john.jpg")
				  				  .build();
		
		assertThatCode(() -> file.validate()).doesNotThrowAnyException();
	}
}
