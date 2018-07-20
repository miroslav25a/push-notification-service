package com.iceze.push_notification_service.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iceze.push_notification_service.database.RootData;
import com.iceze.push_notification_service.model.User;
import com.jetstreamdb.JetstreamDBInstance;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private JetstreamDBInstance<RootData> db;
	
	@Override
	public Optional<Set<User>> findAllUsers() {
		Set<User> users = this.db.root().getUsers();
		
		if(users != null) {
			return Optional.of(users);
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<User> saveUser(User user) {
		Set<User> users = this.db.root().getUsers();
		users.add(user);
		db.store(users);
		
		return Optional.of(user);
	}
}
