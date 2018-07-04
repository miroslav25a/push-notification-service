package com.iceze.push_notification_service.model;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.iceze.push_notification_service.service.InvalidNotificationException;

public class File extends Push {
	@SerializedName("file_name")
	private String fileName;
	@SerializedName("file_type")
	private String fileType;
	@SerializedName("file_url")
	private String fileUrl;
	
	public File() {
	}
	
	private File(final Builder builder) {
		this.type = builder.type;
		this.body = builder.body;
		this.fileName = builder.fileName;
		this.fileType = builder.fileType;
		this.fileUrl = builder.fileUrl;
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

	/**
	 * {@inheritDoc}
	 * TODO: validate file type against a list of all possible file types. 
	 * Also, validate the file name extension against a list of all possible file extensions.
	 */
	@Override
	public void validate() {
		if(this.type == null) {
			throw new InvalidNotificationException("Notification's type is invalid");
		}
		
		if(StringUtils.isBlank(this.fileName)) {
			throw new InvalidNotificationException("Notification's file_name is invalid");
		}
		
		if(StringUtils.isBlank(this.fileType)) {
			throw new InvalidNotificationException("Notification's file_type is invalid");
		}
		
		if(StringUtils.isBlank(this.fileUrl) || !this.fileUrl.contains(this.fileName)) {
			throw new InvalidNotificationException("Notification's file_url is invalid");
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
		private String fileName;
		private String fileType;
		private String fileUrl;
		private String body;
		
		private Builder() {  
		}
		
		public Builder withType(final PushType type) {
            this.type = type;
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
		
		public Builder withBody(final String body) {
            this.body = body;
            return this;
		}
		
		public File build() {
            return new File(this);
		}
	}
}
