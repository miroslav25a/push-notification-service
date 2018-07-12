package com.iceze.push_notification_service.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User {
	private String username;
	private String accessToken;
	private String creationTime;
	private Integer numOfNotificationsPushed;
	
	public User() {
	}
	
	private User(final Builder builder) {
		this.username = builder.username;
		this.accessToken = builder.accessToken;
		this.creationTime = builder.creationTime;
		this.numOfNotificationsPushed = builder.numOfNotificationsPushed;
	}
	
	public String getUsername() {
		return username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public Integer getNumOfNotificationsPushed() {
		return numOfNotificationsPushed;
	}
	
	public static Builder builder() {
	        return new Builder();
	}

	
	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.username);
        return builder.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
        if (!(obj instanceof User)) {
            return false;
        }
        
        User user = (User) obj;
        
        return new EqualsBuilder()
                .append(username, user.username)
                .isEquals();
	}



	public static final class Builder {
		private String username;
		private String accessToken;
		private String creationTime;
		private Integer numOfNotificationsPushed;
		
		private Builder() {  
		}
		
		public Builder withUsername(final String username) {
            this.username = username;
            return this;
		}
		
		public Builder withAccessToken(final String accessToken) {
            this.accessToken = accessToken;
            return this;
		}
		
		public Builder withCreationTime(final String creationTime) {
            this.creationTime = creationTime;
            return this;
		}
		
		public Builder withNumOfNotificationsPushed(final Integer numOfNotificationsPushed) {
            this.numOfNotificationsPushed = numOfNotificationsPushed;
            return this;
		}
		
		public User build() {
            return new User(this);
		}
	}
}
