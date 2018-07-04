package com.iceze.push_notification_service.model;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.iceze.push_notification_service.service.InvalidNotificationException;

public class Note extends Push {
	protected String title;
	
	public Note() {
	}
	
	private Note(final Builder builder) {
		this.type = builder.type;
		this.title = builder.title;
		this.body = builder.body;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public void validate() {
		if(this.type == null) {
			throw new InvalidNotificationException("Notification's type is invalid");
		}
		
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
	
	public static final class Builder {
		private PushType type;
		private String title;
		private String body;
		
		private Builder() {  
		}
		
		public Builder withType(final PushType type) {
            this.type = type;
            return this;
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
