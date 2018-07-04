package com.iceze.push_notification_service.model;

public class Notification {
	private String username;
	private String type;
	private String title;
	private String body;
	private String url;
	private String fileName;
	private String fileType;
	private String fileUrl;

	public Notification() {
	}
	
	private Notification(final Builder builder) {
		this.username = builder.username;
		this.type = builder.type;
		this.title = builder.title;
		this.body = builder.body;
		this.url = builder.url;
		this.fileName = builder.fileName;
		this.fileType = builder.fileType;
		this.fileUrl = builder.fileUrl;
	}
	
	public String getUsername() {
		return username;
	}

	public String getType() {
		return type;
	}
	
	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getUrl() {
		return url;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public static Builder builder() {
	        return new Builder();
	}
	
	public static final class Builder {
		private String username;
		private String type;
		private String title;
		private String body;
		private String url;
		private String fileName;
		private String fileType;
		private String fileUrl;
		
		private Builder() {  
		}
		
		public Builder withUsername(final String username) {
            this.username = username;
            return this;
		}
		
		public Builder withType(final String type) {
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
		
		public Builder withUrl(final String url) {
            this.url = url;
            return this;
		}
		
		public Builder withFileName(final String fileName) {
            this.fileName = fileName;
            return this;
		}
		
		public Builder withFileType(final String fileType) {
            this.fileType = fileType;
            return this;
		}
		
		public Builder withFileUrl(final String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
		}
		
		public Notification build() {
            return new Notification(this);
		}
	}
}
