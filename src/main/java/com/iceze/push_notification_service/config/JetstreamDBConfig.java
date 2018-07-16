package com.iceze.push_notification_service.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iceze.push_notification_service.database.RootData;
import com.jetstreamdb.JetstreamDBInstance;

@Configuration
public class JetstreamDBConfig {
	public static final Logger LOG = LoggerFactory.getLogger(JetstreamDBConfig.class);
	
	private JetstreamDBInstance<RootData> db;
	
	@Bean
    public JetstreamDBInstance<RootData> jetstreamDB() {
		db = JetstreamDBInstance.New("notification-service-db ", RootData.class);
	    Path databaseDirectory = Paths.get("").resolve("notification-db");
	    LOG.info("creating database directory in " + databaseDirectory.toAbsolutePath());
	    db.configuration().properties().setStorageDirectory(databaseDirectory.toFile());
	    
	    return db;
	}
}
