package com.iceze.push_notification_service.control;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.iceze.push_notification_service.model.User;
import com.iceze.push_notification_service.service.ExistingUserException;
import com.iceze.push_notification_service.service.InvalidUserException;
import com.iceze.push_notification_service.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UserRestApiController {
	public static final Logger LOG = LoggerFactory.getLogger(UserRestApiController.class);
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@ApiOperation(value = "Create user", notes = "Creating a new user", response = User.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Success", response = User.class),
        @ApiResponse(code = 400, message = "Bad Request", response = String.class),
        @ApiResponse(code = 409, message = "Conflict", response = String.class)
    })
	@RequestMapping(value = "/api/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody final User user, final UriComponentsBuilder uriComponentsBuilder) {
		LOG.info("Creating User: {}", user);
		
		try {
			this.userService.validate(user);
		} catch(InvalidUserException e) {
			// HTTP 400 in RFC 7231 means "the server cannot or will not process the request 
			// due to something that is perceived to be a client error
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(ExistingUserException e) {
			//HTTP 409 Conflict
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriComponentsBuilder.path("/api/user/{username}").buildAndExpand(user.getUsername()).toUri());
			return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
		}
		
		Optional<User> optionalSavedUser = this.userService.saveUser(user);
		User savedUser = optionalSavedUser.get();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/api/user/{username}").buildAndExpand(savedUser.getUsername()).toUri());
		
		return new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Find all users", notes = "Retrieving the collection of user", response = Set.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = List.class),
        @ApiResponse(code = 404, message = "Not found")
    })
	@RequestMapping(value = "/api/user/", method = RequestMethod.GET)
	public ResponseEntity<Set<User>> listAllUsers() {
		Optional<Set<User>> optionalUsers = this.userService.findAllUsers();
		
		if(!optionalUsers.isPresent() || optionalUsers.get().isEmpty()) {
			return new ResponseEntity<Set<User>>(HttpStatus.NOT_FOUND);
		}
		
		Set<User> users = optionalUsers.get();
		return new ResponseEntity<Set<User>>(users, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find a user", notes = "Retrieving a single user", response = List.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = User.class),
        @ApiResponse(code = 404, message = "Not found", response = String.class)
    })
	@RequestMapping(value ="/api/user/{username}", method = RequestMethod.GET) 
	public ResponseEntity<?> retrieveUser(@PathVariable("username") final String username) {
		LOG.info("Retrieving User, username: {}", username);
		
		Optional<User> optionalUser = this.userService.findByUsername(username);
		if(!optionalUser.isPresent()) {
			return new ResponseEntity<>("User username: " + username, HttpStatus.NOT_FOUND);
		}
		
		User user = optionalUser.get();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
