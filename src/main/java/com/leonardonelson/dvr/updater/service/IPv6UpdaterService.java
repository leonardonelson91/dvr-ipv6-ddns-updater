package com.leonardonelson.dvr.updater.service;

import java.io.IOException;

public interface IPv6UpdaterService {
    public boolean updateDvrIpv6() throws IOException, InterruptedException;
}
