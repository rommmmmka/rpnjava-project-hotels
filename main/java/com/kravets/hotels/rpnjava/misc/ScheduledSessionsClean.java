package com.kravets.hotels.rpnjava.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledSessionsClean {
    private final Services services;

    @Autowired
    public ScheduledSessionsClean(Services services) {
        this.services = services;
    }

    @Scheduled(cron = "0 * 0 ? * *")
    public void clean() {
        services.session.removeOutdatedSessions();
        services.order.removeOutdatedOrders();
    }
}
