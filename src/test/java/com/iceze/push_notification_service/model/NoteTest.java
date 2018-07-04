package com.iceze.push_notification_service.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NoteTest {

	@Test
	public void toStringReturns() {
		Note note = Note.builder().withBody("test body")
								  .withTitle("test title")
								  .withType(PushType.NOTE)
								  .build();
		
		Object result = note.toString();
		
		assertThat(result).isInstanceOf(String.class)
						  .isEqualTo("{\"title\":\"test title\",\"type\":\"note\",\"body\":\"test body\"}");
	}

}
