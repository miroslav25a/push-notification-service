package com.iceze.push_notification_service.model;

import org.apache.commons.lang3.StringUtils;

import com.iceze.push_notification_service.service.InvalidNotificationException;

public class Link extends Note {
	private String url;
	
	public Link() {
	}
	
	private Link(final Builder builder) {
		this.type = PushType.LINK;
		this.title = builder.title;
		this.body = builder.body;
		this.url = builder.url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	@Override
	public void validate() {
		super.validate();
		
		if(StringUtils.isBlank(this.url)) {
			throw new InvalidNotificationException("Notification's url is invalid");
		}
	}
	
	public static Builder builder() {
        return new Builder();
	}
	
	public static final class Builder extends Note.Builder {
		private String url;
		
		private Builder() {  
			super();
		}
		
		public Builder withTitle(final String title) {
            this.title = title;
            return this;
		}
		
		public Builder withBody(final String body) {
            this.body = body;
            return this;
		}
		
		public Builder withUrl(final String url) {
			this.url = url;
			return this;
		}
		
		public Link build() {
            return new Link(this);
		}
	}
}
