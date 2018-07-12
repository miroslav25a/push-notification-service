package com.iceze.push_notification_service.model;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.iceze.push_notification_service.service.InvalidNotificationException;

public class Note extends Push {
	protected String title;
	
	public Note() {
	}
	
	private Note(final Builder builder) {
		this.type = PushType.NOTE;
		this.title = builder.title;
		this.body = builder.body;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public void validate() {		
		if(StringUtils.isBlank(this.title)) {
			throw new InvalidNotificationException("Notification's title is invalid");
		}
		
		if(StringUtils.isBlank(this.body)) {
			throw new InvalidNotificationException("Notification's body is invalid");
		}
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		
		return gson.toJson(this);
	}
	
	public static Builder builder() {
        return new Builder();
	}
	
	public static class Builder {
		protected String title;
		protected String body;
		
		protected Builder() {  
		}
		
		public Builder withTitle(final String title) {
            this.title = title;
            return this;
		}
		
		public Builder withBody(final String body) {
            this.body = body;
            return this;
		}
		
		public Note build() {
            return new Note(this);
		}
	}
}
