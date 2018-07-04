package com.iceze.push_notification_service.service;

import java.util.Optional;
import java.util.Set;

import com.iceze.push_notification_service.model.User;

public interface UserService {
	Optional<Set<User>> findAllUsers();
	Optional<User> saveUser(final User user);
	Optional<User> findByUsername(final String username);
	void validate(final User user);
	Optional<User> updateUser(final User user);
}
