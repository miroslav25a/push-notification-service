package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class FileTest {

	@Test
	public void toStringReturns() {
		File file = File.builder().withBody("test body")
								  .withType(PushType.FILE)
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

}
