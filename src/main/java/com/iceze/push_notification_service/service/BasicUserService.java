package com.iceze.push_notification_service.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.iceze.push_notification_service.model.User;

@Service("userService")
public class BasicUserService implements UserService {
	private Set<User> users;
	
	public BasicUserService() {
		if(users == null) {
			users = Sets.newHashSet();
		}
	}
	
	@Override
	public Optional<Set<User>> findAllUsers() {
		return Optional.ofNullable(this.users);
	}

	@Override
	public Optional<User> saveUser(final User user) {
		if(isExistingUser(user.getUsername(), this.users)) {
			throw new ExistingUserException("User with username " + user.getUsername() + " already exists");
		}
		
		final String creationTime = this.getCreationTime();
		final User savedUser = User.builder().withUsername(user.getUsername())
				 							 .withAccessToken(user.getAccessToken())
				 							 .withCreationTime(creationTime)
				 							 .withNumOfNotificationsPushed(new Integer(0))
				 							 .build();
		this.users.add(savedUser);
		
		return Optional.ofNullable(savedUser);
	}

	@Override
	public Optional<User> findByUsername(final String username) {
		List<User> filteredUsers = users.stream().filter(u -> u.getUsername().equals(username))
					 							 .collect(Collectors.toList());
		
		if(!filteredUsers.isEmpty()) {
			return Optional.of(filteredUsers.get(0));
		}
		
		return Optional.empty();
	}
	
	@Override
	public synchronized Optional<User> updateUser(User user) {
		if(!this.users.contains(user)) {
			throw new InvalidUserException("User not found");
		}
		User existingUser = this.findByUsername(user.getUsername()).get();
		User toUpdateUser = User.builder().withAccessToken((isBlank(user.getAccessToken()) ? 
																existingUser.getAccessToken() : user.getAccessToken()))
										  .withCreationTime(isBlank(user.getCreationTime()) ? 
												  				existingUser.getCreationTime() : user.getCreationTime())
										  .withNumOfNotificationsPushed((user.getNumOfNotificationsPushed() == null) ? 
												  				existingUser.getNumOfNotificationsPushed() : user.getNumOfNotificationsPushed())
										  .withUsername(existingUser.getUsername())
									      .build();
		
		this.users.remove(existingUser);
		this.users.add(toUpdateUser);
		
		return Optional.ofNullable(toUpdateUser);
	}

	@Override
	public void validate(final User user) {
		if(user == null) {
			throw new InvalidUserException("User is invalid");
		}
		
		if(StringUtils.isBlank(user.getUsername())) {
			throw new InvalidUserException("User's username is invalid");
		}
		
		if(StringUtils.isBlank(user.getAccessToken())) {
			throw new InvalidUserException("User's access token is invalid");
		}

		if(isExistingUser(user.getUsername(), this.users)) {
			throw new ExistingUserException("User with username " + user.getUsername() + " already exists");
		}
	}
	
	protected boolean isExistingUser(final String username, final Set<User> existingUsers) {
		Set<User> filteredUsers = existingUsers.stream().filter(u -> u.getUsername().equals(username))
				  										 .collect(Collectors.toSet());

		return !filteredUsers.isEmpty();
	}
	
	protected String getCreationTime() {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return now.format(formatter);
	}
}
