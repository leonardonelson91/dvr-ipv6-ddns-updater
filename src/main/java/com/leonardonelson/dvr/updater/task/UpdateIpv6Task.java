package com.leonardonelson.dvr.updater.task;


import com.leonardonelson.dvr.updater.service.IPv6UpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateIpv6Task {

    private static final Logger log = LoggerFactory.getLogger(UpdateIpv6Task.class);

    private IPv6UpdaterService updateService;

    public UpdateIpv6Task(IPv6UpdaterService updateService) {
        this.updateService = updateService;
    }

    @Scheduled(fixedDelay = 5000)
    public void updateIpv6() throws IOException, InterruptedException {
        log.info("Starting DVR IPV6 Update...");
        updateService.updateDvrIpv6();
        log.info("Finished DVR IPV6 Update");
    }
}
