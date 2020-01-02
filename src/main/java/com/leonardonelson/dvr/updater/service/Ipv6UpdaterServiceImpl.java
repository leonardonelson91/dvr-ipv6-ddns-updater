package com.leonardonelson.dvr.updater.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.leonardonelson.dvr.updater.page.DvrConfigPage;
import com.leonardonelson.dvr.updater.page.DvrLoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class Ipv6UpdaterServiceImpl implements IPv6UpdaterService {

    private static final Logger log = LoggerFactory.getLogger(Ipv6UpdaterServiceImpl.class);

    private WebClient webClient;
    private RestOperations restOperations;
    @Value("${ipv6.server.endpoint}")
    private String endpoint;
    @Value("${ipv6.server.hostname}")
    private String hostname;
    @Value("${ipv6.server.token}")
    private String token;
    @Value("${dvr.url}")
    private String dvrHomeUrl;
    @Value("${dvr.username}")
    private String dvrUsername;
    @Value("${dvr.password}")
    private String dvrPassword;

    public Ipv6UpdaterServiceImpl(WebClient webClient, RestOperations restOperations) {
        this.webClient = webClient;
        this.restOperations = restOperations;
    }

    @Override
    public boolean updateDvrIpv6() throws IOException, InterruptedException {
        DvrLoginPage dvrLoginPage = new DvrLoginPage(webClient);
        DvrConfigPage dvrConfigPage = dvrLoginPage.open(dvrHomeUrl)
                .login(dvrUsername, dvrPassword
                ).navigateToConfigPage();

        String ipv6Address = dvrConfigPage.openNetworkConfig().getIpv6Address();

        log.info("Current DVR IPV6 address: {}", ipv6Address);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint)
                .queryParam("hostname", hostname)
                .queryParam("token", token)
                .queryParam("ipv6", ipv6Address);

        log.info("Sending request to update DVR Ipv6 address to DDNS Provider...");
        ResponseEntity<String> response = restOperations.getForEntity(builder.build().encode().toUri(), String.class);
        log.info("IPV6 API Response: {}", response.getBody());

        dvrConfigPage.logout();
        return HttpStatus.OK.equals(response.getStatusCode());
    }
}
