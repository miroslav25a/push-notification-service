package com.iceze.push_notification_service.service;

import static com.iceze.push_notification_service.util.Constants.PUSH_TYPE_NAME_NOTE;
import static com.iceze.push_notification_service.util.Constants.PUSH_TYPE_NAME_LINK;
import static com.iceze.push_notification_service.util.Constants.PUSH_TYPE_NAME_FILE;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.iceze.push_notification_service.model.File;
import com.iceze.push_notification_service.model.Link;
import com.iceze.push_notification_service.model.Note;
import com.iceze.push_notification_service.model.Notification;
import com.iceze.push_notification_service.model.PushResponse;
import com.iceze.push_notification_service.model.PushType;
import com.iceze.push_notification_service.model.User;

@Service("notificationService")
public class BasicNotificationService implements NotificationService {
	public static final Logger LOG = LoggerFactory.getLogger(BasicNotificationService.class);
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Override
	public Optional<PushResponse> push(final Notification notification) {
		Optional<User> optionalUser = userService.findByUsername(notification.getUsername());
		User user = null;
		if(!optionalUser.isPresent()) {
			throw new InvalidNotificationException("Notification's username is invalid");
		}
		user = optionalUser.get();
		
		String requestBody = this.getPushRequestBody(notification);
		HttpHeaders headers = this.getHeaders(user.getAccessToken());
		HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
		RestTemplate restTemplate = this.createClient();
		
		ResponseEntity<PushResponse> response = restTemplate.exchange("https://api.pushbullet.com/v2/pushes", HttpMethod.POST, entity, PushResponse.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			User toUpdateUser = User.builder().withAccessToken(user.getAccessToken())
											  .withCreationTime(user.getCreationTime())
											  .withNumOfNotificationsPushed(new Integer(user.getNumOfNotificationsPushed() + 1))
											  .withUsername(user.getUsername())
											  .build();
			
			this.userService.updateUser(toUpdateUser);
			
			return Optional.ofNullable(response.getBody());
		} 
		
		return Optional.empty();
	}

	@Override
	public void validate(final Notification notification) {
		if(StringUtils.isBlank(notification.getUsername())) {
			throw new InvalidNotificationException("Notification's username is invalid");
		} else {
			Optional<User> optionalUser = this.userService.findByUsername(notification.getUsername());
			
			if(!optionalUser.isPresent()) {
				throw new InvalidNotificationException("Notification's username is invalid");
			}
		}
		
		switch (notification.getType()) {
			case PUSH_TYPE_NAME_NOTE:	Note note = Note.builder().withType(PushType.NOTE)
																  .withTitle(notification.getTitle())
																  .withBody(notification.getBody())
																  .build();
										note.validate();
										break;
			case PUSH_TYPE_NAME_LINK: 	Link link = Link.linkBuilder().withType(PushType.LINK)
																	  .withTitle(notification.getTitle())
																	  .withBody(notification.getBody())
																	  .withUrl(notification.getUrl())
																	  .build();
										link.validate();
										break;
			case PUSH_TYPE_NAME_FILE: 	File file = File.builder().withType(PushType.FILE)
																  .withBody(notification.getBody())
																  .withFileName(notification.getFileName())
																  .withFileType(notification.getFileType())
																  .withFileUrl(notification.getFileUrl())
																  .build();
										file.validate();
										break;
			default:					throw new InvalidNotificationException("Notification's type is invalid");
		}
	}
	
	private HttpHeaders getHeaders(final String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Access-Token", accessToken);
		
		return headers;
	}
	
	private String getPushRequestBody(final Notification notification) {
		String body = "";
		
		switch (notification.getType()) {
			case PUSH_TYPE_NAME_NOTE:	Note note = Note.builder().withType(PushType.NOTE)
																  .withTitle(notification.getTitle())
																  .withBody(notification.getBody())
																  .build();
										body = note.toString();
										break;
			case PUSH_TYPE_NAME_LINK: 	Link link = Link.linkBuilder().withType(PushType.LINK)
																	  .withTitle(notification.getTitle())
																	  .withBody(notification.getBody())
																	  .withUrl(notification.getUrl())
																	  .build();
										body = link.toString();
										break;
			case PUSH_TYPE_NAME_FILE: 	File file = File.builder().withType(PushType.FILE)
																  .withBody(notification.getBody())
																  .withFileName(notification.getFileName())
																  .withFileType(notification.getFileType())
																  .withFileUrl(notification.getFileUrl())
																  .build();
										body = file.toString();
										break;
			default:					body = null;
		}
		
		return body;
	}
	
	private RestTemplate createClient() {
		return new RestTemplate();
	}
}
