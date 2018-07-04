package com.iceze.push_notification_service.control;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.iceze.push_notification_service.model.Notification;
import com.iceze.push_notification_service.model.PushResponse;
import com.iceze.push_notification_service.service.InvalidNotificationException;
import com.iceze.push_notification_service.service.NotificationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class NotificationRestApiController {
	public static final Logger LOG = LoggerFactory.getLogger(NotificationRestApiController.class);
	
	@Autowired
	@Qualifier("notificationService")
	private NotificationService notificationService;
	
	@ApiOperation(value = "Create notification", notes = "Creating a new notification", response = Notification.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Success", response = Notification.class),
        @ApiResponse(code = 400, message = "Bad Request", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
    })
	@RequestMapping(value = "/api/notification/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody final Notification notification, final UriComponentsBuilder uriComponentsBuilder) {
		LOG.info("Creating Notification: {}", notification);

		try {
			this.notificationService.validate(notification);
		} catch(InvalidNotificationException e) {
			// HTTP 400 in RFC 7231 means "the server cannot or will not process the request 
			// due to something that is perceived to be a client error
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		try {
			Optional<PushResponse> optionalPushResponse = this.notificationService.push(notification);
			if(optionalPushResponse.isPresent()) {
				PushResponse pushResponse = optionalPushResponse.get();
				
				return new ResponseEntity<PushResponse>(pushResponse, HttpStatus.CREATED);
			}
		} catch(Exception e) {
			LOG.error("Error pushing notification with exception message: {}", e.getMessage());
		}
		
		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
