package com.iceze.push_notification_service.database;

import java.util.HashSet;
import java.util.Set;

import com.iceze.push_notification_service.model.User;

public class RootData { 
    private final Set<User> users = new HashSet<>();
 
    public Set<User> getUsers() {
        return this.users;
    }
}
