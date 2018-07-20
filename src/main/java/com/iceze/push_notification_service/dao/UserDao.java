package com.iceze.push_notification_service.dao;

import java.util.Optional;
import java.util.Set;

import com.iceze.push_notification_service.model.User;


public interface UserDao {
	Optional<Set<User>> findAllUsers();
	Optional<User> saveUser(final User user);
}
