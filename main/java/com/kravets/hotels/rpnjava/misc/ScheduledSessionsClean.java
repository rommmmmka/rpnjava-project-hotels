package com.kravets.hotels.rpnjava.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledSessionsClean {
    private final DatabaseServices databaseServices;

    @Autowired
    public ScheduledSessionsClean(DatabaseServices databaseServices) {
        this.databaseServices = databaseServices;
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void clean() {
        databaseServices.session.removeOutdatedSessions();
    }
}
